package net.sf.opensftp;

/**
 * <p>
 * An abstract representation of file and directory.
 * </p>
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpFile {

	/**
	 * Returns the name of the file or directory denoted by this abstract
	 * pathname. This is just the last name in the pathname's name sequence. If
	 * the pathname's name sequence is empty, then the empty string is returned.
	 * 
	 * @return The name of the file or directory denoted by this abstract
	 *         pathname, or the empty string if this pathname's name sequence is
	 *         empty.
	 */
	public String getName();
}