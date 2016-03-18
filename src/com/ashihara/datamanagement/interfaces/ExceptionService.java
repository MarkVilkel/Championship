/**
 * The file ExceptionService was created on 2008.28.5 at 10:49:54
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.interfaces;

import java.util.List;

import com.ashihara.datamanagement.core.service.AKService;
import com.ashihara.datamanagement.pojo.ExceptionHeaderDo;
import com.ashihara.datamanagement.pojo.ExceptionStackTraceDo;
import com.ashihara.datamanagement.pojo.User;
import com.rtu.exception.PersistenceException;

public interface ExceptionService extends AKService {
	public <T extends Throwable> ExceptionHeaderDo saveException(T exception, User user) throws PersistenceException;
	
	public List<ExceptionHeaderDo> filterExceptions(ExceptionHeaderDo exceptionHeaderDo) throws PersistenceException;
	public List<ExceptionStackTraceDo> loadStackTrace(ExceptionHeaderDo exceptionHeaderDo) throws PersistenceException;
	
	public void deleteExceptions(List<ExceptionHeaderDo> list) throws PersistenceException;
}
