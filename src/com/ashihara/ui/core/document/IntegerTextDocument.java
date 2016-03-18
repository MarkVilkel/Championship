/**
 * The file IntegerTextDocument.java was created on 2009.2.11 at 23:25:24
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.document;

public class IntegerTextDocument extends AbstractNumberDocument {
	private static final long serialVersionUID = 1L;

	@Override
	protected void parseNumber(String str) {
		Integer.parseInt(str);
	}
}
