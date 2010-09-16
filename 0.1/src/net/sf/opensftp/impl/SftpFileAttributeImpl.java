package net.sf.opensftp.impl;

import java.util.Date;
import net.sf.opensftp.SftpFileAttribute;
import com.jcraft.jsch.SftpATTRS;

/**
 * 
 * @author BurningXFlame@gmail.com
 *
 */
public class SftpFileAttributeImpl implements SftpFileAttribute {
	private char type;
	private int permissions;
	private String permissionsString;
	private int links;
	private String owner;
	private int uid;
	private String group;
	private int gid;
	private long size;
	private Date aTime;
	private Date mTime;

	private SftpATTRS attrs;

	public SftpFileAttributeImpl(SftpATTRS attrs) {
		setAttrs(attrs);
	}

	public void setAttrs(SftpATTRS attrs) {
		this.attrs = attrs;
		type = attrs.getPermissionsString().charAt(0);
		permissions = attrs.getPermissions();
		permissionsString = attrs.getPermissionsString().substring(1);
		// links=
		// owner=
		uid = attrs.getUId();
		// group=
		gid = attrs.getGId();
		size = attrs.getSize();
		aTime = new Date(attrs.getATime());
		mTime = new Date(((long) attrs.getMTime()) * 1000);
	}

	public char getType() {
		return type;
	}

	public int getPermissions() {
		return permissions;
	}

	public int getLinks() {
		return links;
	}

	public String getOwner() {
		return owner;
	}

	public String getGroup() {
		return group;
	}

	public long getSize() {
		return size;
	}

	public Date getATime() {
		return aTime;
	}

	public Date getMTime() {
		return mTime;
	}

	public int getGID() {
		return gid;
	}

	public String getPermissionsString() {
		return permissionsString;
	}

	public int getUID() {
		return uid;
	}

}
