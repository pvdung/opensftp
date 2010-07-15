package net.sf.opensftp.impl;

/**
 * <p>
 * An <code>SftpResult</code> object represents an SFTP operation result.
 * </p>
 * 
 * @version
 * @author BurningXFlame
 */
public class SftpResultImpl implements net.sf.opensftp.SftpResult {
	private boolean successFalg;
	private String errorCode;
	private String errorMsg;
	private Object extension;

	public SftpResultImpl() {
	}

	/**
	 * Construct a new <code>SftpResult</code> using the specified values .
	 */
	public SftpResultImpl(boolean successFalg, String errorCode, String errorMsg,
			Object extension) {
		this.successFalg = successFalg;
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMsg;
	}

	public void setErrorMessage(String errorMsg) {
		this.errorMsg = errorMsg;
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