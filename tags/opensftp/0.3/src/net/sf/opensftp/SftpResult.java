package net.sf.opensftp;

/**
 * <p>
 * An <code>SftpResult</code> object represents an SFTP operation result.
 * </p>
 * It covers the following points of interest:
 * <ul>
 * <li><code>successFlag</code>, tells whether the operation succeeds or fails</li>
 * <li><code>errorCode</code>, represents the integer standard error code of
 * sftp, if any</li>
 * <li><code>errorMessage</code>, represents the error message, if any</li>
 * <li><code>extension</code>, represents the output of the operation, if any</li>
 * </ul>
 * 
 * @author BurningXFlame@gmail.com
 */
public interface SftpResult {

	/**
	 * Returns the <code>successFlag</code>.
	 */
	public boolean getSuccessFlag();

	/**
	 * Returns the <code>errorCode</code>.
	 */
	public int getErrorCode();

	/**
	 * Returns the <code>errorMessage</code>.
	 * 
	 * @return the <code>errorMessage</code>.
	 */
	public String getErrorMessage();

	/**
	 * Returns the <code>extension</code>.
	 */
	public Object getExtension();
}