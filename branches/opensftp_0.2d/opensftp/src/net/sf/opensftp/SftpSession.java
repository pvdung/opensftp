package net.sf.opensftp;

import java.util.Hashtable;

/**
 * An <code>SftpSession</code> object represents the context information of a
 * communication between a client and an SFTP server.
 * 
 * @author BurningXFlame@gmail.com
 */
public interface SftpSession {

	/**
	 * Return the Date Time Pattern of the server.
	 * 
	 * @return the Date Time Pattern of the server.
	 * 
	 * @deprecated
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
	
	/**
	 * A bundle of any additional/customized information.
	 * 
	 * @return the bundle
	 */
	public Hashtable getExtras();
	
}
