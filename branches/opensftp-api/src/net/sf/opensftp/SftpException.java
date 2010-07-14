package net.sf.opensftp;

/**
 * An exception that provides information on an SFTP access error.
 * 
 * @version
 * @author BurningXFlame
 * 
 */
public class SftpException extends Exception {
	/**
	 * Constructs an <code>SftpException</code> with <code>null</code> as its
	 * error detail message.
	 */
	public SftpException() {
		super();
	}

	/**
	 * Constructs an <code>SftpException</code> with the specified detail
	 * message. The error message string <code>message</code> can later be
	 * retrieved by the <code>{@link java.lang.Throwable#getMessage()}</code>
	 * method of class <code>java.lang.Throwable</code>.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public SftpException(String message) {
		super(message);
	}
}
