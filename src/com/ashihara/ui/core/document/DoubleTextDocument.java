/**
 * The file DoubleTextDocument.java was created on 2009.2.11 at 22:27:43
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.document;

public class DoubleTextDocument extends AbstractNumberDocument {
	private static final long serialVersionUID = 1L;

	@Override
	protected void parseNumber(String str) {
		Double.parseDouble(str);
	}
}