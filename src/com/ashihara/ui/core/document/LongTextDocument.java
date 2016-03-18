/**
 * The file LongTextDocument.java was created on 2009.2.11 at 22:26:02
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.document;

public class LongTextDocument extends AbstractNumberDocument {
	private static final long serialVersionUID = 1L;

	@Override
	protected void parseNumber(String str) {
		Long.parseLong(str);
	}
}