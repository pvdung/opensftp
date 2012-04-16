package net.sf.opensftp;

/**
 * <p>
 * An <code>SftpFile</code> object represents a file or directory.
 * </p>
 * It covers the following points of interest:
 * <ul>
 * <li><code>name</code>, the short name, for example, readme.txt</li>
 * <li><code>fullname</code>, the full name, for example, /home/guest/readme.txt
 * </li>
 * <li><code>attribute</code>, represents the attributes of this file, where
 * attributes cover type, permissions, owner, group, size, atime (last access
 * time) and mtime (last modification time).</li>
 * </ul>
 * 
 * @see SftpFileAttribute
 * @author BurningXFlame@gmail.com
 */
public interface SftpFile {

	/**
	 * Returns the <code>name</code> (short name) of this file or directory.
	 */
	public String getName();

	/**
	 * Returns the <code>fullname</code> of this file or directory.
	 */
	public String getFullName();

	/**
	 * Returns the <code>attribute</code> of this file.
	 */
	public SftpFileAttribute getAttribute();

	/**
	 * Returns a string consisting of the <code>attribute</code> and the
	 * <code>name</code> of this file. It's the same as an entry that you get
	 * when executing the ls command on an SFTP server.
	 */
	public String toString();
}