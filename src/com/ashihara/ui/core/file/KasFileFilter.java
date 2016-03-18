/**
 * The file KasFileFilter.java was created on 2008.3.2 at 12:18:52
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.file;

import javax.swing.filechooser.FileFilter;

public interface KasFileFilter {
	public String getFileExtension();
	public FileFilter getFileFilter();
}
