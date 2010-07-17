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
	
	/**
	 * Return the user.
	 * 
	 * @return the user
	 */	
	public String getUser();

	/**
	 * Return the host.
	 * 
	 * @return the host
	 */
	public String getHost();
	
	/**
	 * Return the current path.
	 * 
	 * @return the current path
	 */
	public String getCurrentPath();
}
