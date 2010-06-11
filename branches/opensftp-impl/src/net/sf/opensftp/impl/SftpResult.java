package net.sf.opensftp.impl;

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
public class SftpResult {

	/**
	 * <code>successFlag</code> tells whether the operation succeeds or fails.
	 */
	private boolean successFalg;
	/**
	 * <code>error</code> represents the error message, if any.
	 */
	private String error;
	/**
	 * <code>extension</code> represents the output of the operation, if any.
	 */
	private Object extension;

	/**
	 * Construct a new <code>SftpResult</code> with <code>successFlag</code> set
	 * to false and <code>error</code> set to an empty string (namely "").
	 */
	public SftpResult() {
		successFalg = false;
		error = "";
	}

	/**
	 * Construct a new <code>SftpResult</code> using the specified values .
	 * @param successFalg
	 * @param error
	 * @param extension
	 */
	public SftpResult(boolean successFalg, String error, Object extension) {
		this.successFalg = successFalg;
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean getSuccessFalg() {
		return successFalg;
	}

	public void setSuccessFalg(boolean successFalg) {
		this.successFalg = successFalg;
	}

	public Object getExtension() {
		return extension;
	}

	public void setExtension(Object extension) {
		this.extension = extension;
	}
}