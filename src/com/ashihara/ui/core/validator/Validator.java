/**
 * The file Validator.java was created on 2007.29.8 at 14:14:15
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.validator;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ashihara.datamanagement.core.persistence.exception.AKValidationException;
import com.ashihara.datamanagement.pojo.YearWeightCategoryLink;
import com.ashihara.enums.UIC;
import com.ashihara.enums.CM.WeightCategory;
import com.ashihara.ui.core.component.combo.AutoCompleteComboBox;
import com.ashihara.ui.core.table.StringPathHelper;
import com.ashihara.ui.tools.ApplicationManager;
import com.rtu.persistence.core.Attribute;
import com.rtu.persistence.core.Do;
import com.toedter.calendar.JDateChooser;

public class Validator {
	public static String getMandatoryMessage(String msg, String extraComments, boolean printMessageWithoutPrefix){
		if (printMessageWithoutPrefix) {
			return msg;
		}

		UIC uic = ApplicationManager.getInstance().getUic();
		String message = uic.FIELD_()+" '"+msg+"' "+uic.MUST_BE_FILLED();
		message += parseExtraComments(extraComments);
		
		return message;
	}
	
	public static String getMandatoryMessage(String msg, boolean printMessageWithoutPrefix){
		return getMandatoryMessage(msg, (String)null, printMessageWithoutPrefix);
	}
	
	private static String getPositiveMessage(String msg, boolean printMessageWithoutPrefix){
		if (printMessageWithoutPrefix) {
			return msg;
		}
		
		UIC uic = ApplicationManager.getInstance().getUic();
		String message = "'"+msg+"' "+uic.FIELD_MUST_BE_NOT_NEGATIVE();
		return message;
	}
	
	private static String getLength255Message(String msg, String extraComments, boolean printMessageWithoutPrefix){
		if (printMessageWithoutPrefix) {
			return msg;
		}
		UIC uic = ApplicationManager.getInstance().getUic();
		String message = "'"+msg+"' "+uic.FIELD_LENGTH_MUST_BE_LESS_THAN_255_SYMBOLS();
		message += parseExtraComments(extraComments);
		
		return message;
	}

	private static String getLength20Message(String msg, String extraComments, boolean printMessageWithoutPrefix){
		if (printMessageWithoutPrefix) {
			return msg;
		}
		UIC uic = ApplicationManager.getInstance().getUic();
		String message = "'"+msg+"' "+uic.FIELD_LENGTH_MUST_BE_LESS_THAN_20_SYMBOLS();
		message += parseExtraComments(extraComments);
		
		return message;
	}
	
	public static void validateMandatoryComponent(Component c, String msg, String extraComments) throws AKValidationException{
		validateMandatoryComponent(c, msg, extraComments, false);
	}
	
	public static void validateMandatoryComponent(Component c, String msg) throws AKValidationException{
		validateMandatoryComponent(c, msg, (String)null);
	}
	
	public static void validateMandatoryComponent(Component c, Attribute attribute, String extraComments) throws AKValidationException{
		validateMandatoryComponent(c, attribute.getAttributeName(), extraComments, false);
	}
	
	public static void validateMandatoryComponent(Component c, Attribute attribute) throws AKValidationException{
		validateMandatoryComponent(c, attribute, (String)null);
	}
	
	public static void validateDataIntegerTypeComponent(Component c, String attrName) throws AKValidationException {
		validateDataIntegerTypeComponent(c, attrName, (String)null);
	}
	
	public static void validateDataIntegerTypeComponent(Component c, String attrName, String extraComments) throws AKValidationException {
		if (c instanceof JTextField){
			String text = ((JTextField)c).getText();
			try {
				Integer value = Integer.parseInt(text);
			}catch(Throwable e) {
				UIC uic = ApplicationManager.getInstance().getUic();
				String message = "'"+attrName+"' "+uic.FIELD_DATA_TYPE_MUST_BE_INTEGER_NUMBER();
				message += parseExtraComments(extraComments);
				
				throw new AKValidationException(message);
			}
		}
		else {
			throw new IllegalArgumentException("Unsupported component " + c);
		}
	}
	
	public static void validateDataDoubleTypeComponent(Component c, Attribute attribute) throws AKValidationException {
		validateDataDoubleTypeComponent(c, attribute.getAttributeName(), null);	
	}
	
	public static void validateDataDoubleTypeComponent(Component c, String attribute) throws AKValidationException {
		validateDataDoubleTypeComponent(c, attribute, null);	
	}
	
	public static void validateDataDoubleTypeComponent(Component c, String attrName, String extraComments) throws AKValidationException {
		if (c instanceof JTextField){
			String text = ((JTextField)c).getText();
			try {
				Double value = Double.parseDouble(text);
			}catch(Throwable e) {
				UIC uic = ApplicationManager.getInstance().getUic();
				String message = "'"+attrName+"' "+uic.FIELD_DATA_TYPE_MUST_BE_REAL_NUMBER();
				message += parseExtraComments(extraComments);
				
				throw new AKValidationException(message);
			}
		}
		else {
			throw new IllegalArgumentException("Unsupported component " + c);
		}
	}
	
	public static void validateDataIntegerTypeComponent(Component c, Attribute attribute) throws AKValidationException{
		validateDataIntegerTypeComponent(c, attribute.getAttributeName());
	}
	
	public static void validateMandatoryComponent(Component c, String msg, String extraComments, boolean printMessageWithoutPrefix) throws AKValidationException{
		if (c instanceof JTextField){
			if(((JTextField)c).getText().trim().isEmpty())
				throw new AKValidationException(getMandatoryMessage(msg, extraComments, printMessageWithoutPrefix));
			else if (((JTextField)c).getText().length() > 255)
				throw new AKValidationException(getLength255Message(msg, extraComments, printMessageWithoutPrefix));
		}
		else if (c instanceof JPasswordField){
			if( new String(((JPasswordField)c).getPassword()).trim().isEmpty())
				throw new AKValidationException(getMandatoryMessage(msg, extraComments, printMessageWithoutPrefix));
			else if (new String(((JPasswordField)c).getPassword()).length() > 20)
				throw new AKValidationException(getLength20Message(msg, extraComments, printMessageWithoutPrefix));
		}
		else if (c instanceof JDateChooser){
			if( ((JDateChooser)c).getDate() == null)
				throw new AKValidationException(getMandatoryMessage(msg, extraComments, printMessageWithoutPrefix));
			else{
				try{
					new Date(((JDateChooser)c).getDate().getTime());
				}catch(Throwable e){
					UIC uic = ApplicationManager.getInstance().getUic();
					throw new AKValidationException(uic.WRONG_DATE_FORMAT());
				}
			}
		}
		else if (c instanceof JTextArea){
			if(((JTextArea)c).getText().trim().isEmpty())
				throw new AKValidationException(getMandatoryMessage(msg, extraComments, printMessageWithoutPrefix));
		}
		else if (c instanceof JComboBox){
			if(((JComboBox)c).getSelectedItem() == null || (((JComboBox)c).getSelectedItem() != null && ((JComboBox)c).getSelectedItem().toString().isEmpty()))
				throw new AKValidationException(getMandatoryMessage(msg, extraComments, printMessageWithoutPrefix));
		}

 
	}
	
	public static void validateLength(Component c, String msg, int count) throws AKValidationException{
		if (count <= 0) {
			throw new IllegalArgumentException("count must be > 0");
		}
		UIC uic = ApplicationManager.getInstance().getUic();
		String fullMsg = "'"+msg+"' "+uic.LENGTH_MUST_BE_GREATER_THAN()+ " "+count +" "+ uic.SYMBOLS()+"!";
		if (c instanceof JTextField)
			if (((JTextField)c).getText().length() < count)
				throw new AKValidationException(fullMsg);
			
		if (c instanceof JTextArea)
			if (((JTextArea)c).getText().length() < count)
				throw new AKValidationException(fullMsg);
	}
	
//	public static <T extends Do> void validateLengthList(List<T> list, String field, String msg) throws MVValidationException{
//		if (list == null || list.size()<=0)
//			return;
//		
//		for (Do current : list){
////			if (current instanceof Validatable && !((Validatable)current).mustValidate()) continue;
//			Object value = invokeGetMethod(current, field);
//			if (value != null && value.toString().length()>255)
//				throw new MVValidationException(getLength255Message(msg,false));
//		}
//	}
//	
	public static <T extends Object> void validateMandatoryList(List<T> list, Attribute field, String msg) throws AKValidationException {
		validateMandatoryList(list, field, msg, (String) null);
	}
	
	public static <T extends Object> void validateMandatoryList(List<T> list, Attribute field, String msg, String extraComments) throws AKValidationException {
		validateMandatoryList(list, field, msg, extraComments, false);
	}
	
	private static <T extends Object> void validateMandatoryList(List<T> list, Attribute field, String msg, String extraComments, boolean printMessageWithoutPrefix) throws AKValidationException{
		if (list == null || list.size()<=0)
			return;
		
		for (T current : list){
//			if (current instanceof Validatable && !((Validatable)current).mustValidate()) continue;
			Object value = invokeGetMethod(current, field);
			if (value == null || value.toString().trim().isEmpty()){
				AKValidationException e = new AKValidationException(getMandatoryMessage(msg, extraComments, printMessageWithoutPrefix));
				e.setUserObject(current);
				throw e;
			}
			else if (value != null && value.toString().length()>255){
				AKValidationException e = new AKValidationException(getLength255Message(msg, extraComments, printMessageWithoutPrefix));
				e.setUserObject(current);
				throw e;
			}
		}
	}
//	
	public static <T extends Object> void validateLongPositiveList(List<T> list, Attribute field, String msg) throws AKValidationException{
		for (Object current : list){
//			if (current instanceof Validatable && !((Validatable)current).mustValidate()) continue;
			Object value = invokeGetMethod(current, field);
			if (value == null || !(value instanceof Long) || ((Long)value)<0){
				AKValidationException e = new AKValidationException(getPositiveMessage(msg, false));
				e.setUserObject(current);
				throw e;
			}
		}
	}
	
	public static <T extends Object> void validate255LengthList(List<T> list, Attribute field, String msg) throws AKValidationException{
		validate255LengthList(list, field, msg, (String) null);
	}
	
	public static <T extends Object> void validate255LengthList(List<T> list, Attribute field, String msg, String extraComments) throws AKValidationException{
		for (Object current : list){
			Object value = invokeGetMethod(current, field);
			if (value instanceof String){
				String string = (String) value;
				validateString255Lentgh(string, msg, extraComments);
			}
		}
	}
	public static void validate255LengthComponent(Component c, String msg) throws AKValidationException{
		String string = getStringFromComponent(c);
		validateString255Lentgh(string, msg, (String) null);
	}
	public static void validate9LengthComponent(Component c, String msg) throws AKValidationException{
		String string = getStringFromComponent(c);
		validateStringLentgh(string, msg, (String)null, 9);
	}
	public static void validate2000LentghComponent(Component c, String msg) throws AKValidationException{
		String string = getStringFromComponent(c);
		validateString2000Lentgh(string, msg, (String) null);
	}
	
	private static String getStringFromComponent(Component c){
		String string = "";
		if (c instanceof JTextField)
			string = ((JTextField)c).getText();
		else if (c instanceof JTextArea)
			string = ((JTextArea)c).getText();
		else if (c instanceof JComboBox)
			string = String.valueOf(((JComboBox)c).getSelectedItem());
		else 
			throw new IllegalArgumentException("Not supported component - "+c);
		return string;
	}
	private static void validateStringLentgh(String string, String msg, String extraComments, int length) throws AKValidationException{
		if (string != null && string.length() > length) {
			UIC uic = ApplicationManager.getInstance().getUic();
			String error = "'"+msg+"' "+uic.FIELD_LENGTH_MUST_BE_LESS_THAN()+" '"+length+"'";
			throw new AKValidationException(error);
		}
	}
	
	private static void validateString255Lentgh(String string, String msg, String extraComments) throws AKValidationException{
		validateStringLentgh(string, msg, extraComments, 255);
	}
	private static void validateString2000Lentgh(String string, String msg, String extraComments) throws AKValidationException{
		validateStringLentgh(string, msg, extraComments, 2000);
	}
	
//	public static void validateTypeComponent(Component c, String msg, Class type) throws MVValidationException{
//		String text = null;
//		if (c instanceof JTextField)
//			text = ((JTextField)c).getText();
//		
//		if (type == Double.class) {
//			try{
//				Double value = Double.valueOf(text);
//			}catch (Throwable e){
//				throw new MVValidationException(msg);
//			}
//		}
//		
//		if (type == Integer.class) {
//			try{
//				Integer value = Integer.valueOf(text);
//			}catch (Throwable e){
//				throw new MVValidationException(msg);
//			}
//		}
//		
//		if (type == Long.class) {
//			try{
//				Long value = Long.valueOf(text);
//			}catch (Throwable e){
//				throw new MVValidationException(msg);
//			}
//		}
//	}
	
	private static <T extends Object> Object invokeGetMethod(T object, Attribute field){
		return StringPathHelper.invokeGetMethodForPath(field.getAttributePath(), object);
	}
	
	public static <T extends Object> void validateDoubleRangeList(List<T> list, Attribute field, String msg, Double left, Double right, boolean includeLeft, boolean includeRight) throws AKValidationException{
		if (list != null) {
			for (T t : list) {
				Object value = invokeGetMethod(t, field);
				Double doubleValue = null;
				try {
					doubleValue = Double.valueOf(value.toString());
				} catch(Throwable e) {
					UIC uic = ApplicationManager.getInstance().getUic();
					throw new AKValidationException("'"+field + "' " + uic.MUST_BE_DOUBLE());
				}
				validateDoubleRange(doubleValue, msg, left, right, includeLeft, includeRight);
			}
		}
	}
	
	public static <T extends Object> void validateDoubleTwoNumbers(List<T> list, Attribute fieldLeft, Attribute fieldRight, String msg, boolean canLeftBeEqualToRight) throws AKValidationException{
		if (list != null){
			for (T t : list){
				Object leftValue = invokeGetMethod(t, fieldLeft);
				Object rightValue = invokeGetMethod(t, fieldRight);
				Double doubleLeftValue = null;
				Double doubleRightValue = null;
				UIC uic = ApplicationManager.getInstance().getUic();
				try{
					doubleLeftValue = Double.valueOf(leftValue.toString());
				} catch(Throwable e){
					throw new AKValidationException("'"+doubleLeftValue + "' " + uic.MUST_BE_DOUBLE());
				}
				try{
					doubleRightValue = Double.valueOf(rightValue.toString());
				} catch(Throwable e){
					throw new AKValidationException("'"+doubleRightValue + "' " + uic.MUST_BE_DOUBLE());
				}
				
				if (canLeftBeEqualToRight && doubleLeftValue > doubleRightValue) {
					throw new AKValidationException(msg);
				}
				else if (!canLeftBeEqualToRight && doubleLeftValue >= doubleRightValue) {
					throw new AKValidationException(msg);
				}
			}
		}
	}
	private static void validateDoubleRange (Double value, String msg, Double left, Double right, boolean includeLeft, boolean includeRight)throws AKValidationException{
		boolean validationFailed = false;
		String strLeft = new String("");
		String strRight = new String("");
		if ((value <= left || value >= right) && !includeLeft && !includeRight){
			validationFailed = true;
			strLeft = "(";
			strRight = ")";
		}
		else if ((value < left || value >= right) && includeLeft && !includeRight){
			validationFailed = true;
			strLeft = "[";
			strRight = ")";
		}
		else if ((value < left || value > right) && includeLeft && includeRight){
			validationFailed = true;
			strLeft = "[";
			strRight = "]";
		}
		else if ((value <= left || value > right) && !includeLeft && includeRight){
			validationFailed = true;
			strLeft = "(";
			strRight = "]";
		}
		UIC uic = ApplicationManager.getInstance().getUic();	
		if (validationFailed) {
			throw new AKValidationException("'"+msg+"' "+uic.MUST_HIT_RANGE()+" "+strLeft+left.toString()+"; "+right.toString()+strRight+"!");
		}
	}
	
	public static void validateDoubleRangeComponent(JTextField c, String fieldName, Double left, Double right, boolean includeLeft, boolean includeRight) throws AKValidationException{
		validateDouble(c, fieldName);
		Double value = Double.valueOf(c.getText());
		validateDoubleRange(value, fieldName, left, right, includeLeft, includeRight);
	}
	
	private static void validateDouble(JTextField c, String fieldName) throws AKValidationException {
		try {
			Double.valueOf(c.getText());
		}
		catch (Throwable e) {
			UIC uic = ApplicationManager.getInstance().getUic();
			throw new AKValidationException(uic.FIELD_() + " '" + fieldName + "' " + uic.HAS_TO_HAVE_REAL_VALUE() + "!");
		}
	}
	
	public static void validateLong(JTextField c, String fieldName) throws AKValidationException {
		try {
			Long.valueOf(c.getText());
		}
		catch (Throwable e) {
			UIC uic = ApplicationManager.getInstance().getUic();
			throw new AKValidationException(uic.FIELD_() + " '" + fieldName + "' " + uic.HAS_TO_HAVE_INTEGER_VALUE() + "!");
		}
	}
	
	
//
//	public static boolean isSelectedOnlyOneRow(JTable table){
//		if (table.getSelectedRowCount()==1)
//			return true;
//		else if (table.getSelectedRowCount()==0)
//			ExceptionHandler.showErrorMessage(table, "At least one row must be selected!");
//		else if (table.getSelectedRowCount()>1)
//			ExceptionHandler.showErrorMessage(table, "Please select only one row!");
//		return false;
//	}
//	
	public static void validateMail(String email) throws AKValidationException{
		if ("none".equals(email.toLowerCase()))
			return;
	    Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	    Matcher m = p.matcher(email.trim());
	    if (!m.matches()) {
	    	UIC uic = ApplicationManager.getInstance().getUic();
	    	throw new AKValidationException(uic.EMAIL_HAS_WRONG_FORMAT());
	    }

	}
	public static void validateNoSpaceSymbolsComponent(Component c, String msg) throws AKValidationException{
		if (c instanceof JTextField){
			if(!((JTextField)c).getText().isEmpty()){
				String text = ((JTextField)c).getText();
				if (text.contains(" "))
					throw new AKValidationException(getSpaceInTextError(msg));
			}
		}
		else if (c instanceof JPasswordField){
			if(!new String(((JPasswordField)c).getPassword()).isEmpty()){
				String text = new String(((JPasswordField)c).getPassword());
				if (text.contains(" "))
					throw new AKValidationException(getSpaceInTextError(msg));
			}
		}
	}
	
	private static String getSpaceInTextError(String msg){
		UIC uic = ApplicationManager.getInstance().getUic();
		String message = "'"+msg+"' "+uic.FIELD_CAN_NOT_CONTAIN_SPACE_SYMBOLS();
		return message;
	}
	
	private static String parseExtraComments(String extraComments) {
		if (extraComments != null) {
			return extraComments;
		}
		return "";
	}
	
	private static Object doValidateInputData(Container container) throws AKValidationException {
		for (Component c : container.getComponents()) {
			if (c instanceof AutoCompleteComboBox) {
				AutoCompleteComboBox<?> combo = (AutoCompleteComboBox<?>)c;
				Object result = combo.validateSelectedValue();
				if (result != null) {
					String message = prepareMessageForInputDataValidation(result, combo.getReturnType());
					throw new AKValidationException(message);
				}
			}
			else if (c instanceof Container) {
				doValidateInputData((Container) c);
			}
		}
		return null;
	}
	
	private static String prepareMessageForInputDataValidation(Object result, Class<?> excpectedType) {
		UIC uic = ApplicationManager.getInstance().getUic();
		String message = uic.SPECIFIED_SEARCH_CRITERIA() + " '" + result + "' " + uic.IS_NOT_RECOGNIZABLE() + "!";
		return message;
	}

	public static void validateInputData(Container container) throws AKValidationException {
		doValidateInputData(container);
	}

	public static <T extends Do> void validateUniqeList(List<T> list, Attribute<?> attribute, String fieldName) throws AKValidationException {
		if (list == null || list.isEmpty()) {
			return;
		}
		
		UIC uic = ApplicationManager.getInstance().getUic();
		
		List<Object> idiesList = new ArrayList<Object>();
		for (T t  : list) {	
			Object value = invokeGetMethod(t, attribute);
			if (idiesList.contains(value)) {
				throw new AKValidationException(uic.FIELD_() + " '" + fieldName + "' " + uic.VALUE_MUST_BE_UNIQUE());
			}
			else {
				idiesList.add(value);
			}
		}
	}

	
//	public static void validateDoublePositiveComponent(JTextField textField, String msg) throws MVValidationException{
//		if (Double.valueOf(textField.getText())<0){
//			MVValidationException e = new MVValidationException(msg+" "+UIC.FIELD_VALUE_MUST_BE_GREATER_OR_EQUALS_THAN_ZERO);
//			throw e;
//		}
//	}
//	
//	public static void validateDoubleNegativeComponent(JTextField textField, String msg) throws MVValidationException{
//		if (Double.valueOf(textField.getText())>0){
//			MVValidationException e = new MVValidationException(msg+" "+UIC.FIELD_VALUE_MUST_BE_LESS_OR_EQUALS_THAN_ZERO);
//			throw e;
//		}
//	}
//
//	public static void validateLongPositiveComponent(JTextField txtSalary, String msg) throws MVValidationException{
//		if (Long.valueOf(txtSalary.getText())<0){
//			MVValidationException e = new MVValidationException(msg+" "+UIC.FIELD_VALUE_MUST_BE_GREATER_OR_EQUALS_THAN_ZERO);
//			throw e;
//		}
//	}
//
//	public static void validateDateComponentPair(JDateChooser dateFrom, JDateChooser dateTo, String msg) throws MVValidationException{
//		if (dateFrom==null || dateFrom.getDate()==null || dateTo == null || dateTo.getDate()==null)
//			return;
//		if (dateFrom.getDate().after(dateTo.getDate())){
//			MVValidationException e = new MVValidationException(UIC.WRONG_DATE_FORMAT+"\n"+UIC.FOR+" '"+msg+"' "+UIC.DATE_FROM_MUST_BE_BEFORE_DATE_TO);
//			throw e;
//		}
//	}
}
