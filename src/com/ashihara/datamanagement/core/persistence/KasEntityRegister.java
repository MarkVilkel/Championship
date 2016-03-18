/**
 * The file KasEntityRegister.java was created on 2009.11.5 at 13:47:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.core.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ashihara.datamanagement.core.session.AKServerSessionManager;
import com.ashihara.datamanagement.core.session.AKServerSessionManagerImpl;
import com.ashihara.datamanagement.pojo.wraper.FightResultReport;
import com.ashihara.datamanagement.pojo.wraper.FighterPlace;
import com.rtu.persistence.core.Attribute;

public class KasEntityRegister {
	private List<Object> kasEntities;
	private List<Class<?>> primitiveTypes;
	private List<Class> restrictedTypes;
	private List<Class> serverTypesOnly;
	private List<Object> extras;
	private final String SPLITTER = ".";
	private AKServerSessionManager aKServerSessionManager;
	
	
	private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<Class<?>, Class<?>>();
	
	static {
		PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
		PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
		PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
		PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
		PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
		PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
		PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
		PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
		PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
	}
	
	public KasEntityRegister() {
		super();
		createKasEntityRegister();
		createPrimitiveTypesRegister();
		createRestrictedTypesRegister();
		createServerTypesOnlyRegister();
		initExtras();
	}
	
	private void createServerTypesOnlyRegister() {
	}

	private void initExtras() {
		getExtras().add(new FighterPlace());
		getExtras().add(new FightResultReport());
	}

	private void createRestrictedTypesRegister() {
		getRestrictedTypes().add(Blob.class);
		getRestrictedTypes().add(byte[].class);
	}

	private void createPrimitiveTypesRegister() {
		getPrimitiveTypes().add(Integer.class);
		getPrimitiveTypes().add(String.class);
		getPrimitiveTypes().add(Double.class);
		getPrimitiveTypes().add(Boolean.class);
		getPrimitiveTypes().add(Long.class);
		getPrimitiveTypes().add(Date.class);
		
		getPrimitiveTypes().add(List.class);
		getPrimitiveTypes().add(Class.class);
	}

	private void createKasEntityRegister() {
		try {
			Map map = getKasServerSessionManager().getDefaultServerSession().getHibernateSessionFactory().getAllClassMetadata();
			for (Object key : map.keySet()) {
				Object entity = Class.forName(key.toString()).newInstance();

				getKasEntities().add(entity);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public List<Object> getExtras() {
		if (extras == null) {
			extras = new ArrayList<Object>();
		}
		return extras;
	}

	public List<Object> getKasEntities() {
		if (kasEntities == null) {
			kasEntities = new ArrayList<Object>();
		}
		return kasEntities;
	}

	private List<Class<?>> getPrimitiveTypes() {
		if (primitiveTypes == null) {
			primitiveTypes = new ArrayList<Class<?>>();
		}
		return primitiveTypes;
	}

	public List<Class> getRestrictedTypes() {
		if (restrictedTypes == null) {
			restrictedTypes = new ArrayList<Class>();
		}
		return restrictedTypes;
	}

	private String getConceptualModel(Object entity) {
		String cm = "";
		
		String entityName = entity.getClass().getSimpleName();
		String attribute = Attribute.class.getSimpleName();
		
		cm += format("public static class " +  entityName + " implements " + attribute + "<" + entity.getClass().getCanonicalName() + ">" + " { ", 2);
		cm += format("	private String parentPath;", 2);
//		default constructor
		cm += format("	public " + entityName + "(){ this(null);}", 1);
//		init constructor
		cm += format("	public " + entityName + "(String parentPath){ super(); this.parentPath = parentPath;}", 2);

		cm += format("	public String getAttributePath() { " +
							"return parentPath == null ? getAttributeName() : parentPath;}");
		
		cm += format("	public String getAttributeName() { return parentPath == null ? \"" + entityName + "\" : parentPath.substring(parentPath.lastIndexOf(\"" + SPLITTER + "\") + 1); }", 2);
		
		
		for (Method m : entity.getClass().getMethods()) {
			if (m.getName().startsWith("get")) {
				String fieldName = getStringSmallFirstLetter(m.getName().substring(3));
//				String fieldName = m.getName().substring(3);
				Class returnType = m.getReturnType();
				String returnTypeName = returnType.getSimpleName();
				
				if (getRestrictedTypes().contains(returnType) || Class.class == returnType) { //skip
					continue;
				}
				
				if (returnType.isPrimitive()) {
					returnType = PRIMITIVES_TO_WRAPPERS.get(returnType);
					returnTypeName = returnType.getSimpleName();
				}
				if (getPrimitiveTypes().contains(returnType)) {
					cm += format("	public " + attribute + "<" + returnTypeName + ">" + " " + m.getName() +
									"() {return new " + attribute + "<" + returnTypeName + ">" + "() {" +
									"public String getAttributePath(){return " + entityName + ".this.getAttributePath()+\""+SPLITTER+"\"+getAttributeName();}" +
									"public String getAttributeName(){return \""+fieldName+"\";}" +
									"}; " +
									"}");	
				}
				else {
					cm += format("	public " + returnType.getSimpleName() + " " + m.getName() + "() {return new " + returnTypeName + "(this.getAttributePath()+\"" + SPLITTER + fieldName + "\"); }");	
				}
			}
		}
		cm += format("} ", 2);
		return cm;
	}
	
	private String getStringSmallFirstLetter(String str) {
		String newStr = String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1);  
		return newStr;
	}
	
	private void createConceptualModelFile(String javaClassName, String commonJavaClassName) {
		String package_ = this.getClass().getPackage().getName();
		String path = "src\\"+package_.replace(".", "\\")+"\\";
		File file = new File(path + javaClassName+".java");
		file.delete();
		try {
			if (file.createNewFile()) {
				
				FileWriter fstream = new FileWriter(file); 
				BufferedWriter out = new BufferedWriter(fstream);
				
				String header = format("package " + package_ + ";", 2);
				header += format("import " + Attribute.class.getName()+";");
				header += format("import " + Date.class.getName()+";");
				header += format("import " + List.class.getName()+";", 2);
				
				header += format("// This is automatically generated class file by launching "+this.getClass().getName()+".createConceptualModelFile()", 1);
				header += format("// DO NOT CHANGE IT BY HANDS!!!", 2);
				header += format("@SuppressWarnings(\"serial\")", 1);
				String classDef = "public class " + javaClassName;
				if (commonJavaClassName == null) {
					header += format(classDef+ " {", 2);
				}
				else {
					header += format(classDef+ " extends " + commonJavaClassName +" {", 2);
				}
				out.write(header);
				
				List<Object> objectsForConceptualModel = new ArrayList<Object>();
				objectsForConceptualModel.addAll(getKasEntities());
				objectsForConceptualModel.addAll(getExtras());
				
				for (Object entity : objectsForConceptualModel) {
					if (commonJavaClassName == null) {
						if (getServerTypesOnly().contains(entity.getClass())) {
							continue;
						}
					}
					else {
						if (!getServerTypesOnly().contains(entity.getClass())) {
							continue;
						}
					}

					String cm = getConceptualModel(entity);
					out.write(cm);
				}
				
				out.write("}");
			    out.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void createCommonConceptualModelFile(String javaClassName) {
		createConceptualModelFile(javaClassName, null);
	}
	
	public void createServerConceptualModelFile(String javaClassName, String commonJavaClassName) {
		createConceptualModelFile(javaClassName, commonJavaClassName);
	}
	
	private String format(String s) {
		return format(s, 1);
	}
	private String format(String s, int count) {
		for (int i = 0; i < count; i++) {
			s+="\n";
		}
		return s;
	}
	
	public static void main(String[] args) {
		String commonCMFileName = "CM";
//		String serverCMFileName = "ServerCM";
		
		new KasEntityRegister().createCommonConceptualModelFile(commonCMFileName);
//		new KasEntityRegister().createServerConceptualModelFile(serverCMFileName, commonCMFileName);
	}

	public List<Class> getServerTypesOnly() {
		if (serverTypesOnly == null) {
			serverTypesOnly = new ArrayList<Class>();
		}
		return serverTypesOnly;
	}

	public AKServerSessionManager getKasServerSessionManager() {
		if (aKServerSessionManager == null) {
			aKServerSessionManager = AKServerSessionManagerImpl.getInstance();
		}
		return aKServerSessionManager;
	}
}