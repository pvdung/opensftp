package net.sf.opensftp.impl;

/**
 * 
 * @author BurningXFlame@gmail.com
 */
public class SftpResultImpl implements net.sf.opensftp.SftpResult {
	private boolean successFlag = false;
	private int errorCode = 0;
	private String errorMsg = null;
	private Object extension = null;

	/**
	 * Construct a new <code>SftpResult</code> with successFlag being set to
	 * <code>false</code>, and other properties remaining null or 0.
	 */
	public SftpResultImpl() {
	}

	/**
	 * Construct a new <code>SftpResult</code> using the specified values. The
	 * unspecified properties remain null or 0.
	 */
	public SftpResultImpl(boolean successFalg, Object extension) {
		this.successFlag = successFalg;
		this.extension = extension;

	}

	/**
	 * Construct a new <code>SftpResult</code> using the specified values. The
	 * unspecified properties remain null.
	 */
	public SftpResultImpl(boolean successFalg, int errorCode, String errorMsg) {
		this.successFlag = successFalg;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * Construct a new <code>SftpResult</code> using the specified values .
	 */
	public SftpResultImpl(boolean successFalg, int errorCode, String errorMsg,
			Object extension) {
		this.successFlag = successFalg;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.extension = extension;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMsg;
	}

	public void setErrorMessage(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFalg(boolean successFalg) {
		this.successFlag = successFalg;
	}

	public Object getExtension() {
		return extension;
	}

	public void setExtension(Object extension) {
		this.extension = extension;
	}
}