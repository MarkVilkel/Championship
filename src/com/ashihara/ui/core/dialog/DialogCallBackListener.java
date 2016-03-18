/**
 * The file DialogCallBackListener.java was created on 2009.27.1 at 15:45:57
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.dialog;

import java.util.Map;

public interface DialogCallBackListener {
	void dialogClosed(Class<?> dialog, Map<String, Object> dialogParams);
}
