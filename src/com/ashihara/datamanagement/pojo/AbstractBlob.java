/**
 * The file Photo.java was created on 2010.31.1 at 14:03:39
 * by
 * @author Marks Vilkelis.
 */
package com.ashihara.datamanagement.pojo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

import org.hibernate.Hibernate;

import com.rtu.persistence.core.BaseDo;

public class AbstractBlob extends BaseDo {
	private static final long serialVersionUID = 1L;
	
	private byte[] blob;

	public byte[] getBlob() {
		return blob;
	}
	
	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
	
	/** Don't invoke this.  Used by Hibernate only. */
	public void setObjectBlob(Blob imageBlob) {
		this.blob = this.toByteArray(imageBlob);
	}
	
	/** Don't invoke this.  Used by Hibernate only. */
	public Blob getObjectBlob() {
		return Hibernate.createBlob(this.blob);
	}
	
	private byte[] toByteArray(Blob fromBlob) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			return toByteArrayImpl(fromBlob, baos);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ex) {
				}
			}
		}
	}
	
	private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)
	throws SQLException, IOException {
		byte[] buf = new byte[4000];
		InputStream is = fromBlob.getBinaryStream();
		try {
			for (;;) {
				int dataSize = is.read(buf);
				
				if (dataSize == -1)
					break;
				baos.write(buf, 0, dataSize);
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
				}
			}
		}
		return baos.toByteArray();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(blob);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractBlob other = (AbstractBlob) obj;
		if (!Arrays.equals(blob, other.blob))
			return false;
		return true;
	}
}
