package net.sf.opensftp;

/**
 * <p>
 * An <code>SftpFileAttribute</code> object represents the attribute of a file
 * or directory represented by an {@link SftpFile} object. It covers the
 * following points of interest:
 * </p>
 * <ul>
 * <li><code>inode</code>, index node</li>
 * <li><code>type</code>, ordinary file | directory | character device | block
 * device | socket | link. Each static field begining with "TYPE_" of this
 * interface represents a file type.</li>
 * <li><code>permissions</code>,</li>
 * <li><code>owner</code>,</li>
 * <li><code>group</code>,</li>
 * <li><code>size</code>,</li>
 * <li><code>atime</code>, namely, last access time</li>
 * <li><code>mtime</code>, namely, last modification time</li>
 * </ul>
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpFileAttribute {
	/**
	 * Represent the ordinary file type.
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
	 * Represent the link file type.
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

}
