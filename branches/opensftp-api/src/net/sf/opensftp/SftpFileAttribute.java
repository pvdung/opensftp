package net.sf.opensftp;

import java.util.Date;

/**
 * <p>
 * An <code>SftpFileAttribute</code> object represents the attribute of a file
 * or directory represented by an {@link SftpFile} object. It covers the
 * following points of interest:
 * </p>
 * <ul>
 * <li><code>type</code>, regular file | directory | character device | block
 * device | socket | symbolic link | named pipe . Each static field begining
 * with "TYPE_" of this interface represents a file type.</li>
 * <li><code>permissions</code></li>
 * <li><code>links</code>, the number of directory entries that refer to the
 * file.</li>
 * <li><code>owner</code></li>
 * <li><code>group</code></li>
 * <li><code>size</code>, the size of the file in bytes.</li>
 * <li><code>atime</code>, namely, last access time</li>
 * <li><code>mtime</code>, namely, last modification time. If the file is
 * recent, the date and time is shown. If the file is not in the current year,
 * the year is shown rather than time.</li>
 * </ul>
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpFileAttribute {
	/**
	 * Represent the regular file type.
	 */
	public final static char TYPE_FILE = '-';
	/**
	 * Represent the directory type.
	 */
	public final static char TYPE_DIR = 'd';
	/**
	 * Represent the character device type.
	 */
	public final static char TYPE_CDEV = 'c';
	/**
	 * Represent the block device type.
	 */
	public final static char TYPE_BDEV = 'b';
	/**
	 * Represent the socket file type.
	 */
	public final static char TYPE_SOCK = 's';
	/**
	 * Represent the symbolic link type.
	 */
	public final static char TYPE_LINK = 'l';

	/**
	 * Return the <code>type</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 * 
	 * @return the <code>type</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public char getType();

	/**
	 * Return the <code>permissions</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. The return value is a
	 * nine-character String, "rwxr-x-r-x", for instance.
	 * 
	 * @return the <code>permissions</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public String getPermissionsString();
	
	/**
	 * Return the <code>permissions</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. The return value is a
	 * three-digit octal number, 0755, for instance.
	 * 
	 * @return the <code>permissions</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public int getPermissions();

	/**
	 * Return the <code>links</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>links</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public int getLinks();

	/**
	 * Return the <code>owner</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>owner</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public String getOwner();

	/**
	 * Return the UID of <code>owner</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>owner</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public int getUID();
	
	/**
	 * Return the <code>group</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>group</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public String getGroup();
	
	/**
	 * Return the GID of the <code>group</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>group</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public String getGID();	
	
	/**
	 * Return the <code>size</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>size</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public long getSize();
	
	/**
	 * Return the <code>atime</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>atime</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public Date getATime();
	
	/**
	 * Return the <code>mtime</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. 
	 * 
	 * @return the <code>mtime</code> attribute of the file or directory
	 *         represented by the {@link SftpFile} object to which this
	 *         <code>SftpFileAttribute</code> object belongs.
	 */
	public Date getMTime();
}
