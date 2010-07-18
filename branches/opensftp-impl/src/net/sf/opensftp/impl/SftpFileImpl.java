package net.sf.opensftp.impl;

import java.security.KeyStore.Entry;

import net.sf.opensftp.SftpFile;
import net.sf.opensftp.SftpFileAttribute;

import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SftpFileImpl implements SftpFile {
	private String filename;
	private String fullname;
	private SftpFileAttribute attr;

	private LsEntry entry;
	
	public SftpFileImpl(LsEntry entry) {
		this.entry = entry;
		filename = entry.getFilename();
		// fullname=
		attr = new SftpFileAttributeImpl(entry.getAttrs());
	}

	public String getName() {
		return filename;
	}

	public String getFullName() {
		return fullname;
	}

	public SftpFileAttribute getAttribute() {
		return attr;
	}
	
	public String toString(){
		return entry.toString();
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setAttr(SftpFileAttribute attr) {
		this.attr = attr;
	}
	
}
