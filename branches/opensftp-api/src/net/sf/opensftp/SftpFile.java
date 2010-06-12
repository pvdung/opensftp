package net.sf.opensftp;

/**
 * <p>
 * An <code>SftpFile</code> object represents a file or directory. It covers the
 * following points of interest:
 * </p>
 * <ul>
 * <li><code>name</code>, namely, the last name in the pathname's name sequence.
 * </li>
 * <li><code>attribute</code>, represents the attributes of this file, where
 * attributes cover type, permissions, owner, group, size, atime (namely, last
 * access time) and mtime (namely, last modification time).</li>
 * </ul>
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpFile {

	/**
	 * Return the <code>name</code> of this file or directory. This is just the
	 * last name in the pathname's name sequence. If the pathname's name
	 * sequence is empty, then the empty string is returned.
	 * 
	 * @return The name of this file or directory, or the empty string if this
	 *         pathname's name sequence is empty.
	 */
	public String getName();

	/**
	 * Return the <code>attribute</code> of this file.
	 * 
	 * @return the <code>attribute</code> of this file.
	 */
	public SftpFileAttribute getAttribute();

	/**
	 * Return a string consisting of the <code>attribute</code> and the
	 * <code>name</code> of this file. It's just a line that you get when
	 * executing the ls command when accessing an SFTP server.
	 * 
	 * @return a string consisting of the <code>attribute</code> and
	 *         <code>name</code> of the file.
	 */
	public String toString();
}