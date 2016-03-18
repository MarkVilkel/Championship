/**
 * The file AllFileFilter.java was created on 2008.18.5 at 17:26:33
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class AllFileFilter extends FileFilter implements KasFileFilter{
	public static final String EXTENSION = "*";
	@Override
	public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith("") || file.isDirectory();
	}

	@Override
	public String getDescription() {
		return "*."+EXTENSION;
	}

	public String getFileExtension() {
		return EXTENSION;
	}

	public FileFilter getFileFilter() {
		return this;
	}
}

