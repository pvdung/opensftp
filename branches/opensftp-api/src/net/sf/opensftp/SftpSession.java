package net.sf.opensftp;

/**
 * An <code>SftpSession</code> object represents the context information of a
 * communication between a client and an SFTP server.
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpSession {

	/**
	 * Return the Date Time Pattern of the server.
	 * 
	 * @return the Date Time Pattern of the server.
	 */
	public String getServerDateTimePattern();
}
