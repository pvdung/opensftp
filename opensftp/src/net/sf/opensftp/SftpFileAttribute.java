package net.sf.opensftp;

import java.util.Date;

/**
 * <p>
 * An <code>SftpFileAttribute</code> object represents the attribute of a file
 * or directory represented by an {@link SftpFile} object.
 * </p>
 * It covers the following points of interest:
 * <ul>
 * <li><code>type</code>, regular file | directory | character device | block
 * device | socket | symbolic link | named pipe . Each static field beginning
 * with "TYPE_" of this interface represents a file type.</li>
 * <li><code>permissions</code></li>
 * <li><code>links</code>, the number of directory entries that refer to the
 * file.</li>
 * <li><code>owner</code></li>
 * <li><code>group</code></li>
 * <li><code>size</code>, the size of the file in bytes.</li>
 * <li><code>atime</code>, last access time, the time when the file was last
 * read.</li>
 * <li><code>mtime</code>, last modification time. the time when the file was
 * last modified.</li>
 * </ul>
 * 
 * @author BurningXFlame@gmail.com
 */
public interface SftpFileAttribute {
	/**
	 * Represents the regular file type.
	 */
	public final static char TYPE_REGULAR = '-';
	/**
	 * Represents the directory type.
	 */
	public final static char TYPE_DIRECTORY = 'd';
	/**
	 * Represents the character device type.
	 */
	public final static char TYPE_CHAR_DEVICE = 'c';
	/**
	 * Represents the block device type.
	 */
	public final static char TYPE_BLOCK_DEVICE = 'b';
	/**
	 * Represents the socket file type.
	 */
	public final static char TYPE_SOCKET = 's';
	/**
	 * Represents the symbolic link type.
	 */
	public final static char TYPE_SYMLINK = 'l';

	/**
	 * Returns the <code>type</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public char getType();

	/**
	 * Returns the <code>permissions</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. The return value is a
	 * nine-character String, "rwxr-x-r-x", for instance.
	 */
	public String getPermissionsString();

	/**
	 * Returns the <code>permissions</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs. The return value is a
	 * three-digit octal number, 0755, for instance.
	 */
	public int getPermissions();

	/**
	 * Returns the <code>links</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public int getLinks();

	/**
	 * Returns the <code>owner</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public String getOwner();

	/**
	 * Returns the UID of <code>owner</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public int getUID();

	/**
	 * Returns the <code>group</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public String getGroup();

	/**
	 * Returns the GID of the <code>group</code> attribute of the file or
	 * directory represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public int getGID();

	/**
	 * Returns the <code>size</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public long getSize();

	/**
	 * Returns the <code>atime</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public Date getATime();

	/**
	 * Returns the <code>mtime</code> attribute of the file or directory
	 * represented by the {@link SftpFile} object to which this
	 * <code>SftpFileAttribute</code> object belongs.
	 */
	public Date getMTime();
}
