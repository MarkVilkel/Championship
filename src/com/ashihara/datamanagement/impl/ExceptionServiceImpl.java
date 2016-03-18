/**
 * The file ExceptionServiceImpl.java was created on 2008.28.5 at 10:54:20
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.impl;

import java.util.ArrayList;
import java.util.List;

import com.ashihara.datamanagement.interfaces.ExceptionService;
import com.ashihara.datamanagement.pojo.ExceptionHeaderDo;
import com.ashihara.datamanagement.pojo.ExceptionStackTraceDo;
import com.ashihara.datamanagement.pojo.User;
import com.rtu.exception.PersistenceException;

public class ExceptionServiceImpl extends AbstractAKServiceImpl implements ExceptionService {
	public List<ExceptionHeaderDo> filterExceptions(ExceptionHeaderDo exceptionHeaderDo) throws PersistenceException {
		List<ExceptionHeaderDo> result = new ArrayList<ExceptionHeaderDo>();
		if (exceptionHeaderDo == null) {
			return result;
		}
		
//		HqlQuery<ExceptionHeaderDo> query = getHelper().createHqlQuery(ExceptionHeaderDo.class, getCmExceptionHeaderDo());
//		result = getHelper().loadByHqlQuery(query);
		
		return result;
	}

	public <T extends Throwable> ExceptionHeaderDo saveException(T exception, User user) throws PersistenceException {
		if (exception == null) {
			return null;
		}
		
		ExceptionHeaderDo exceptionHeaderDo = new ExceptionHeaderDo();
		exceptionHeaderDo.setDescription("User Name - '" + user.getName() + "' User Role - '" + user.getRole());
		exceptionHeaderDo.setHeaderCause(exception.getClass().getSimpleName());
		exceptionHeaderDo = getHelper().save(exceptionHeaderDo);
		
		Throwable t = exception;
		while (t != null) {
			saveExceptionStackTraceDo(exceptionHeaderDo, "-----<"+ (t.getMessage() == null ? t.getClass().getSimpleName() : t.getMessage())+">-----");
			for (StackTraceElement element : t.getStackTrace()){
				saveExceptionStackTraceDo(exceptionHeaderDo, element.toString());
			}
			
			t = t.getCause();
		}
		
		return exceptionHeaderDo;
	}
	
	private ExceptionStackTraceDo saveExceptionStackTraceDo(ExceptionHeaderDo exceptionHeaderDo, String traceLine)throws PersistenceException {
		ExceptionStackTraceDo exceptionStackTraceDo = new ExceptionStackTraceDo();
		exceptionStackTraceDo.setExceptionHeaderDo(exceptionHeaderDo);
		exceptionStackTraceDo.setOneTraceLine(traceLine);
		return getHelper().save(exceptionStackTraceDo);
	}

	public void deleteExceptions(List<ExceptionHeaderDo> list) throws PersistenceException {
		getHelper().deleteAll(list);
	}

	public List<ExceptionStackTraceDo> loadStackTrace(ExceptionHeaderDo exceptionHeaderDo) throws PersistenceException {
		List<ExceptionStackTraceDo> result = new ArrayList<ExceptionStackTraceDo>();
		if (exceptionHeaderDo == null || exceptionHeaderDo.getId() == null) {
			return result;
		}
		
//		HqlQuery<ExceptionStackTraceDo> query = getHelper().createHqlQuery(ExceptionStackTraceDo.class, getCmExceptionStackTraceDo());
//		if (exceptionHeaderDo != null) {
//			query.addExpression(ExpressionHelper.eq(getCmExceptionStackTraceDo().getExceptionHeaderDo(), exceptionHeaderDo));
//		}
//		result = getHelper().loadByHqlQuery(query);

		return result;
	}
}
