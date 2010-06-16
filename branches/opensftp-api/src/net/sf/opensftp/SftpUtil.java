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
			int strictHostKeyChecking) throws SftpException;

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
			String identityFile, int strictHostKeyChecking)
			throws SftpException;

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
			throws SftpException;

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
			String password, int strictHostKeyChecking) throws SftpException;

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
			String password, int strictHostKeyChecking) throws SftpException;

	/**
	 * Disconnect from an SFTP server.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 */
	public void disconnect(SftpSession session);

	/**
	 * Represent the ls command. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * ls(session, &quot;.&quot;)
	 * </pre>
	 * 
	 * @see #ls(SftpSession, String)
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>List<{@link SftpFile}></code> object, if this ls operation succeed.
	 */
	public SftpResult ls(SftpSession session);

	/**
	 * Represent the ls command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The path to ls.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>List<{@link SftpFile}></code> object, if this ls operation succeed.
	 */
	public SftpResult ls(SftpSession session, String path);

	/**
	 * Represent the put command. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * put(session, localFilename, &quot;.&quot;)
	 * </pre>
	 * 
	 * @see #put(SftpSession, String, String)
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param localFilename
	 *            The path of the local file to put.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult put(SftpSession session, String localFilename);

	/**
	 * Represent the put command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param localFilename
	 *            The path of the local file to put.
	 * @param remoteFilename
	 *            The path (on the server) where to put the local file.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult put(SftpSession session, String localFilename,
			String remoteFilename);

	/**
	 * Represent the get command. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * get(session, remoteFilename, &quot;.&quot;)
	 * </pre>
	 * 
	 * @see #get(SftpSession, String, String)
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param remoteFilename
	 *            The path of the remote file to get.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult get(SftpSession session, String remoteFilename);

	/**
	 * Represent the get command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param remoteFilename
	 *            The path of the remote file to get.
	 * @param localFilename
	 *            The local path where to place the remote file.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult get(SftpSession session, String remoteFilename,
			String localFilename);

	/**
	 * Represent the cd command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The path to cd.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult cd(SftpSession session, String path);

	/**
	 * Represent the lcd command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The local path to lcd.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult lcd(SftpSession session, String path);

	/**
	 * Represent the mkdir command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The path of the folder to create.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult mkdir(SftpSession session, String path);

	/**
	 * Represent the rename command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param oldpath
	 *            The path of the file or directory to rename.
	 * @param newpath
	 *            The path to rename the <code>oldpath</code> to.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult rename(SftpSession session, String oldpath, String newpath);

	/**
	 * Represent the rm command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param filename
	 *            The path of the file to delete.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult rm(SftpSession session, String filename);

	/**
	 * Represent the rmdir command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The path of the directory to delete.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult rmdir(SftpSession session, String path);

	/**
	 * Represent the pwd command.
	 * 
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return an
	 *         <code>{@link SftpFile}</code> object, if this pwd operation succeed.
	 */
	public SftpResult pwd(SftpSession session);

	/**
	 * Represent the lpwd command.
	 * 
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult lpwd(SftpSession session);

}
