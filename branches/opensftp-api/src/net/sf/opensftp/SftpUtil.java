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
	public SftpChannel connect(String host, String user, String identityFile) throws Exception;

	/**
	 * connect to a remote sftp through the default port(22) using publickey
	 * authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpChannel connect(String host, String user, String passphrase,
			String identityFile) throws Exception;

	/**
	 * connect to a remote sftp through publickey authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpChannel connect(String host, int port, String user,
			String passphrase, String identityFile) throws Exception;

	/**
	 * connect to a remote sftp through the default port(22) using password
	 * authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpChannel connectByPasswdAuth(String host, String user,
			String password) throws Exception;

	/**
	 * connect to a remote sftp through password authentication
	 * 
	 * @return a sesion-like or channel-like object representing the connection
	 *         between the server and client
	 */
	public SftpChannel connectByPasswdAuth(String host, int port, String user,
			String password) throws Exception;

	public void disconnect(SftpChannel channel);

	public SftpResult ls(SftpChannel channel);

	public SftpResult ls(SftpChannel channel, String path);

	public SftpResult put(SftpChannel channel, String localFilename);

	public SftpResult put(SftpChannel channel, String localFilename,
			String remoteFilename);

	public SftpResult get(SftpChannel channel, String remoteFilename);

	public SftpResult get(SftpChannel channel, String remoteFilename,
			String localFilename);

	public SftpResult cd(SftpChannel channel, String path);

	public SftpResult lcd(SftpChannel channel, String path);

	public SftpResult mkdir(SftpChannel channel, String path);

	public SftpResult rename(SftpChannel channel, String oldpath, String newpath);

	public SftpResult rm(SftpChannel channel, String filename);

	public SftpResult rmdir(SftpChannel channel, String path);

	public SftpResult pwd(SftpChannel channel);

	public SftpResult lpwd(SftpChannel channel);

}
