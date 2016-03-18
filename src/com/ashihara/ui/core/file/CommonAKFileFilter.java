/**
 * The file CommonKasFileFilter.java was created on 2009.27.8 at 23:40:24
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.ui.core.file;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class CommonAKFileFilter extends FileFilter implements KasFileFilter {
	private String extension;
	
	public CommonAKFileFilter(String extension) {
		super();
		this.extension = extension;
	}
	
	@Override
	public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith("."+getFileExtension()) || file.isDirectory();
	}

	@Override
	public String getDescription() {
		return "*."+getFileExtension();
	}

	public FileFilter getFileFilter() {
		return this;
	}

	public String getFileExtension() {
		return extension;
	}
}
