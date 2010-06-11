package net.sf.opensftp;

/**
 * @author BurningXFlame
 */
public interface SftpUtil {
	/**
	 * connect to a remote sftp through the default port(22) using publickey
	 * authentication with empty passphrase
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpSession connect(String host, String user, String identityFile) throws Exception;

	/**
	 * connect to a remote sftp through the default port(22) using publickey
	 * authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpSession connect(String host, String user, String passphrase,
			String identityFile) throws Exception;

	/**
	 * connect to a remote sftp through publickey authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpSession connect(String host, int port, String user,
			String passphrase, String identityFile) throws Exception;

	/**
	 * connect to a remote sftp through the default port(22) using password
	 * authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpSession connectByPasswdAuth(String host, String user,
			String password) throws Exception;

	/**
	 * connect to a remote sftp through password authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpSession connectByPasswdAuth(String host, int port, String user,
			String password) throws Exception;

	public void disconnect(SftpSession session);

	public SftpResult ls(SftpSession session);

	public SftpResult ls(SftpSession session, String path);

	public SftpResult put(SftpSession session, String localFilename);

	public SftpResult put(SftpSession session, String localFilename,
			String remoteFilename);

	public SftpResult get(SftpSession session, String remoteFilename);

	public SftpResult get(SftpSession session, String remoteFilename,
			String localFilename);

	public SftpResult cd(SftpSession session, String path);

	public SftpResult lcd(SftpSession session, String path);

	public SftpResult mkdir(SftpSession session, String path);

	public SftpResult rename(SftpSession session, String oldpath, String newpath);

	public SftpResult rm(SftpSession session, String filename);

	public SftpResult rmdir(SftpSession session, String path);

	public SftpResult pwd(SftpSession session);

	public SftpResult lpwd(SftpSession session);

}
