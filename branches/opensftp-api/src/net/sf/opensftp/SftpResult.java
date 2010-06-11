package net.sf.opensftp;

/**
 * <p>
 * An <code>SftpResult</code> object represents an SFTP operation result
 * covering the following points of interest:
 * </p>
 * <ul>
 * <li><code>successFlag</code>, tells whether the operation succeeds or fails</li>
 * <li><code>error</code>, represents the error message, if any</li>
 * <li><code>extension</code>, represents the output of the operation, if any</li>
 * </ul>
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpResult {
	
	/**
	 * Retrieve the <code>successFlag</code>.
	 * @return the <code>successFlag</code>.
	 */
	public boolean getSuccessFalg();
	
	/**
	 * Retrieve the <code>error</code>.
	 * @return the <code>error</code>.
	 */
	public String getError();

	/**
	 * Retrieve the <code>extension</code>.
	 * @return the <code>extension</code>.
	 */
	public Object getExtension();
}