/**
 * The file DataManagementUtils was created on 2008.27.6 at 15:08:58
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ashihara.datamanagement.core.persistence.exception.AKBusinessException;
import com.rtu.persistence.core.BaseDo;
import com.rtu.persistence.core.Do;

public class DataManagementUtils {
	public static <T extends Do> List<T> clone(List<T> list) throws AKBusinessException {
		if (list == null)
			return new ArrayList<T>();
		
		List<T> result = new ArrayList<T>();
		for (T t: list)
			result.add(clone(t));
			
		return result;
	}
	
	public static <T extends Do> T clone(T object) throws AKBusinessException{
		if (object == null) {
			return null;
		}
		
		T clone = deepCopy(object);
		
		clone.setId(null);
		clone.setCreationDate(null);
		clone.setLastModifiedDate(null);
		clone.setCreatedUserId(null);
		clone.setLastModifiedUserId(null);
			
		return clone;
	}
	
	public static byte[] toByteArray(Object object) throws AKBusinessException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			bos.close();
			byte [] data = bos.toByteArray();
			return data;
		}
        catch(IOException e) {
        	throw new AKBusinessException(e);
        }
	}
	
	public static Object toObject(byte[] byteArray) throws AKBusinessException {
		try {
			ByteArrayInputStream fbos = new ByteArrayInputStream (byteArray);
			ObjectInputStream ois = new ObjectInputStream (fbos);

            return ois.readObject();
		}
        catch(IOException e) {
        	throw new AKBusinessException(e);
        }
        catch(ClassNotFoundException cnfe) {
        	throw new AKBusinessException(cnfe);
        }
	}
	
	public static <T extends Object> T deepCopy(T orig) throws AKBusinessException {
        T obj = null;
        try {
            // Write the object out to a byte array
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
            obj = (T)in.readObject();
        }
        catch(IOException e) {
        	throw new AKBusinessException(e);
        }
        catch(ClassNotFoundException cnfe) {
        	throw new AKBusinessException(cnfe);
        }
        return obj;
    }
	
	public static <T extends BaseDo> List<T> removeDublicatedObjects(List<T> list){
		if (list == null)
			return null;
		
		List<T> result = new ArrayList<T>();
		List<Long> idList = new ArrayList<Long>();
		for (T t : list){
			if (!idList.contains(t.getId())){
				idList.add(t.getId());
				result.add(t);
			}
		}
		
		return result;
	}
	
	public static <T extends BaseDo> void removeAll(List<T> from, List<T> what){
		List<T> duplicated = new ArrayList<T>();
		for (T fr : from){
			for (T wh : what){
				if (fr.getId().equals(wh.getId()) && !duplicated.contains(wh)){
					duplicated.add(fr);
				}
			}
		}
		from.removeAll(duplicated);
	}
	
	public static <K,V> Map<K,V> sortByValue(Map<K,V> map) {
		List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<K, V>>() {
				public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (((Comparable)o1.getValue()).compareTo(((Comparable)o2.getValue())));
			}
		});
		
		Map<K,V> result = new LinkedHashMap<K,V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K,V> Map<K,V> sortByKey(Map<K,V> map, Comparator<Entry<K, V>> comparator) {
		List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, comparator);
		
		Map<K,V> result = new LinkedHashMap<K,V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	public static <K,V> Map<K,V> sortByKey(Map<K,V> map) {
		return sortByKey(map, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (((Comparable)o1.getKey()).compareTo(((Comparable)o2.getKey())));
			}
		});
	}
	
	public static <K,V> List<V> getValuesBySortedKeys(Map<K,V> map) {
		List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<K, V>>() {
				public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (((Comparable)o1.getKey()).compareTo(((Comparable)o2.getKey())));
			}
		});
		
		List<V> result = new ArrayList<V>();
		for (Map.Entry<K, V> entry : list) {
			result.add(entry.getValue());
		}
		return result;
	}

	public static <K,V> List<K> getKeysBySortedValues(Map<K,V> map) {
		List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<K, V>>() {
				public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (((Comparable)o1.getValue()).compareTo(((Comparable)o2.getValue())));
			}
		});
		
		List<K> result = new ArrayList<K>();
		for (Map.Entry<K, V> entry : list) {
			result.add(entry.getKey());
		}
		return result;
	}
	
}
