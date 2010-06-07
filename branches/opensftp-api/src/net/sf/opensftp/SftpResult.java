package net.sf.opensftp;

/**
 * <code>SftpResult</code> represents an SFTP operation result covering serveral interesting points:
 * <code>successFalg</code>, whether the operation succeeds or fails
 * <code>error</code>, represents the error message if any
 * <code>extension</code>, represents the output of the operation, if any
 * 
 * @author BurningXFlame
 * 
 */
public class SftpResult {

	public SftpResult(boolean successFalg, String error, Object extension) {
		this.successFalg = successFalg;
		this.error = error;
	}

	public SftpResult() {
		successFalg = false;
		error = "";
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

	private boolean successFalg;
	private String error;
	private Object extension;
}