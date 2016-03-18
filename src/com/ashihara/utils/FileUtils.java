/**
 * The file FileUtils was created on 2008.15.5 at 11:16:32
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.ashihara.enums.UIC;
import com.ashihara.ui.core.component.KASFileChooser;
import com.ashihara.ui.core.file.KasFileFilter;
import com.ashihara.ui.core.file.PngFileFilter;
import com.ashihara.ui.tools.ApplicationManager;
import com.ashihara.ui.tools.MessageHelper;

public class FileUtils {
	
	private static File lastChoosenOpenFile;
	private static File lastChoosenSaveFile;
	
	public static void openFileWithSystemEditor(File file) {
		if (file != null) {
			openFileWithSystemEditor(file.getAbsolutePath());
		}
	}
	
	public static void openFileWithSystemEditor(String fileName) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static File getOpenPath(KasFileFilter fileFilter){
		KASFileChooser fc = KASFileChooser.createKASOpenFileChooser();
		fc.setFileFilter(fileFilter.getFileFilter());
		if (lastChoosenOpenFile != null) {
			fc.setCurrentDirectory(lastChoosenOpenFile.getParentFile());
		}
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			lastChoosenOpenFile = f;
			return f;
		}
		
		return null;
	}
	
	public static File getSavePath(KasFileFilter fileFilter, String initialFileName){
		KASFileChooser fc = KASFileChooser.createKASSaveFileChooser();
		
		fc.setFileFilter(fileFilter.getFileFilter());
		if (lastChoosenSaveFile != null) {
			fc.setCurrentDirectory(lastChoosenSaveFile.getParentFile());
		}

		fc.setSelectedFile(new File(initialFileName+"."+fileFilter.getFileExtension()));
		int returnVal;
		do {
			returnVal = fc.showSaveDialog(null);
			
			if (returnVal != JFileChooser.APPROVE_OPTION)
				break;
			
			File file = fc.getSelectedFile();
			if (!file.getAbsolutePath().toLowerCase().endsWith("."+fileFilter.getFileExtension().toLowerCase())){
				file = new File(file.getAbsolutePath()+"."+fileFilter.getFileExtension());
			}
			
			UIC uic = ApplicationManager.getInstance().getUic();
			
			if (file != null && file.exists()) {
				if (JOptionPane.YES_OPTION == MessageHelper.showConfirmationMessage(null, uic.THE_FILE() + " '"
						+ file.getName() + "'" + uic.ALREADY_EXISTS()
						+ "\n" + uic.DO_YOU_WANT_TO_OVERWRITE_IT())) {
					lastChoosenSaveFile = file;
					return file;
				}
			} else {
				lastChoosenSaveFile = file;
				return file;
			}
		} while (returnVal == JFileChooser.APPROVE_OPTION);
		
		return null;
	}
	
	 public static File writeToFile(String fileName, String stringToWrite) throws IOException {
		 File file = new File(fileName);
		 return writeToFile(file, stringToWrite);
	 }
	 
	 public static File writeToFile(File file, String stringToWrite) throws IOException {
		 if (file == null) {
			 return file;
		 }
		 file.createNewFile();
		 FileWriter fstream = new FileWriter(file);
		 BufferedWriter out = new BufferedWriter(fstream);
		 out.write(stringToWrite);
		 out.close();
		 
		 return file;
	 }
	 
	 public static void writeToFile(File file, byte[] bytes) throws IOException {
		 OutputStream out = new FileOutputStream(file);
		 out.write(bytes);
		 out.close();
	 }
	 
	 public static byte[] readFromFile(File file) throws IOException {
		 InputStream is = new FileInputStream(file);
		 
		 long length = file.length();
		 
		 if (length > Integer.MAX_VALUE) {
			 throw new IOException("File is too large");
		 }
		 
		 byte[] bytes = new byte[(int)length];
		 
		 // Read in the bytes
		 int offset = 0;
		 int numRead = 0;
		 while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			 offset += numRead;
		 }
		 
		 if (offset < bytes.length) {
			 throw new IOException("Could not completely read file "+file.getName());
		 }
		 
		 is.close();
		 return bytes;
	 }
	 
	 
	 public static void panelToImageFile(JComponent panel, String initialFileName, boolean open) throws IOException {
		 PngFileFilter filter = new PngFileFilter();
		 File file = getSavePath(filter, initialFileName);
//		 File file = File.createTempFile(initialFileName, "." + filter.getFileExtension());
		 if (file != null) {
			 int w = panel.getWidth();
			 int h = panel.getHeight();
			 BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			 Graphics2D g = bi.createGraphics();
			 panel.paint(g);
			 
			 ImageIO.write(bi, filter.getFileExtension(), file);
			 if (open) {
				 openFileWithSystemEditor(file);
			 }
		 }
		 
	 }

}