package net.sf.opensftp;

/**
 * <code>SftpUtil</code> provides a handy set of utilities designed to ease your
 * work with SFTP.
 * 
 * @version
 * @author BurningXFlame
 */
public interface SftpUtil {
	/**
	 * Represent the StrictHostKeyChecking option 'ask'.
	 */
	public final static int STRICT_HOST_KEY_CHECKING_OPTION_ASK = 0;
	/**
	 * Represent the StrictHostKeyChecking option 'yes'.
	 */
	public final static int STRICT_HOST_KEY_CHECKING_OPTION_YES = 1;
	/**
	 * Represent the StrictHostKeyChecking option 'no'.
	 */
	public final static int STRICT_HOST_KEY_CHECKING_OPTION_NO = 2;

	/**
	 * Connect to an SFTP server through the default port(22) using publickey
	 * authentication with empty passphrase. Invoking this method is equivalent
	 * to:
	 * 
	 * <pre>
	 * connect(host, 22, user, &quot;&quot;, identityFile, strictHostKeyChecking)
	 * </pre>
	 * 
	 * @see #connect(String, int, String, String, String, int)
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connect(String host, String user, String identityFile,
			int strictHostKeyChecking) throws Exception;

	/**
	 * Connect to an SFTP server through the default port(22) using publickey
	 * authentication. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * connect(host, 22, user, passphrase, identityFile, strictHostKeyChecking)
	 * </pre>
	 * 
	 * @see #connect(String, int, String, String, String, int)
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connect(String host, String user, String passphrase,
			String identityFile, int strictHostKeyChecking) throws Exception;

	/**
	 * Connect to an SFTP server through publickey authentication.
	 * 
	 * @param host
	 *            Host name
	 * @param port
	 *            Port number
	 * @param user
	 *            User name
	 * @param passphrase
	 *            Passphrase. An empty string ("") indicates no passphrase.
	 * @param identityFile
	 *            The path of the identityFile. It's strongly recommended to
	 *            place your identityFile under your home folder,
	 *            /home/<i>yourusername</i> for Linux for instance, for
	 *            security. If you specify a value begining with '~' and a
	 *            following directory separator ('/' or '\\'), the '~' is
	 *            treated as your home folder.
	 * @param strictHostKeyChecking
	 *            The strictHostKeyChecking option. Valid values for this param
	 *            are {@link #STRICT_HOST_KEY_CHECKING_OPTION_ASK},
	 *            {@link #STRICT_HOST_KEY_CHECKING_OPTION_YES} and
	 *            {@link #STRICT_HOST_KEY_CHECKING_OPTION_NO}.
	 * 
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connect(String host, int port, String user,
			String passphrase, String identityFile, int strictHostKeyChecking)
			throws Exception;

	/**
	 * Connect to an SFTP server through the default port(22) using password
	 * authentication. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * connectByPasswdAuth(host, 22, user, password, strictHostKeyChecking)
	 * </pre>
	 * 
	 * @see #connectByPasswdAuth(String, int, String, String, int)
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connectByPasswdAuth(String host, String user,
			String password, int strictHostKeyChecking) throws Exception;

	/**
	 * Connect to an SFTP server through password authentication.
	 * 
	 * @param host
	 *            Host name
	 * @param port
	 *            Port number
	 * @param user
	 *            User name
	 * @param password
	 *            Password. An empty string ("") indicates no password.
	 * @param strictHostKeyChecking
	 *            The strictHostKeyChecking option. Valid values for this param
	 *            are {@link #STRICT_HOST_KEY_CHECKING_OPTION_ASK},
	 *            {@link #STRICT_HOST_KEY_CHECKING_OPTION_YES} and
	 *            {@link #STRICT_HOST_KEY_CHECKING_OPTION_NO}.
	 * 
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connectByPasswdAuth(String host, int port, String user,
			String password, int strictHostKeyChecking) throws Exception;

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
