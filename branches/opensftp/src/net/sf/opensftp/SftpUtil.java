package net.sf.opensftp;

/**
 * <code>SftpUtil</code> provides a handy set of utilities designed to ease your
 * work with SFTP.
 * 
 * @author BurningXFlame@gmail.com
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
	 * Set the <code>prompter</code> property. If the specified value is valid,
	 * any implementation of opensftp must use this prompter in any scenario
	 * where a <code>Prompter</code> should be used.
	 * 
	 * @see net.sf.opensftp.Prompter
	 */
	public void setPrompter(Prompter prompter);

	/**
	 * Set the <code>progressListener</code> property. If the specified value is
	 * valid, The specified progressListener will be used in
	 * any scenario where a <code>ProgressListener</code> should be used.
	 * 
	 * @see net.sf.opensftp.ProgressListener
	 */
	public void setProgressListener(ProgressListener progressListener);

	/**
	 * Connect to an SFTP server through the default port(22) using publickey
	 * authentication with empty passphrase. Invoking this method is equivalent
	 * to:
	 * 
	 * <pre>
	 * connect(host, 22, user, &quot;&quot;, identityFile, strictHostKeyChecking)
	 * </pre>
	 * 
	 * @see #connect(String, int, String, String, String, int, int)
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
	 * @see #connect(String, int, String, String, String, int, int)
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
	 *            The path of the identityFile. for security, it's strongly
	 *            recommended to place your identityFile under the
	 *            <code>.ssh</code> folder of your home folder,
	 *            /home/<i>yourusername</i>/.ssh for Linux for instance. If you
	 *            specify a value begining with '~' and a following directory
	 *            separator ('/' or '\\'), the '~' is treated as your home
	 *            folder.
	 * @param strictHostKeyChecking
	 *            The strictHostKeyChecking option. Valid values for this param
	 *            are {@link #STRICT_HOST_KEY_CHECKING_OPTION_ASK},
	 *            {@link #STRICT_HOST_KEY_CHECKING_OPTION_YES} and
	 *            {@link #STRICT_HOST_KEY_CHECKING_OPTION_NO}.
	 * @param timeout
	 *            the timeout in milliseconds. A timeout of zero is interpreted
	 *            as an infinite timeout.
	 * 
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connect(String host, int port, String user,
			String passphrase, String identityFile, int strictHostKeyChecking,
			int timeout) throws SftpException;

	/**
	 * Connect to an SFTP server through the default port(22) using password
	 * authentication. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * connectByPasswdAuth(host, 22, user, password, strictHostKeyChecking)
	 * </pre>
	 * 
	 * @see #connectByPasswdAuth(String, int, String, String, int, int)
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
	 * @param timeout
	 *            the timeout in milliseconds. A timeout of zero is interpreted
	 *            as an infinite timeout.
	 * 
	 * @return a {@link SftpSession} object representing the context information
	 *         of the communication established between the client and the
	 *         specified SFTP server.
	 */
	public SftpSession connectByPasswdAuth(String host, int port, String user,
			String password, int strictHostKeyChecking, int timeout)
			throws SftpException;

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
	 *            The path to be listed.
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
	 */
	public SftpResult put(SftpSession session, String localFilename);

	/**
	 * Represent the put command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param localFilename
	 *            The path of the local file to be uploaded.
	 * @param remoteFilename
	 *            The remote path where to place the local file.
	 * 
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
	 */
	public SftpResult get(SftpSession session, String remoteFilename);

	/**
	 * Represent the get command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param remoteFilename
	 *            The path of the remote file to be downloaded.
	 * @param localFilename
	 *            The local path where to place the remote file.
	 * 
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
	 *            The new path
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
	 *            The new path
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
	 *            The path of the directory to be created.
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
	 *            The path of the file or directory to be renamed.
	 * @param newpath
	 *            the desired new file name
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
	 *            The path of the file to be deleted.
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
	 *            The path of the directory to be deleted.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult rmdir(SftpSession session, String path);

	/**
	 * Represent the pwd command.
	 * 
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>String</code> object representing the current path, if this
	 *         pwd operation succeed.
	 */
	public SftpResult pwd(SftpSession session);

	/**
	 * Represent the lpwd command.
	 * 
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>String</code> object representing the current path, if this
	 *         lpwd operation succeed.
	 */
	public SftpResult lpwd(SftpSession session);

	/**
	 * Represent the chgrp command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param grp
	 *            the new group
	 * @param path
	 *            The path representing the file or directory the group of which
	 *            is about to be changed.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 * @deprecated
	 */
	// public SftpResult chgrp(SftpSession session, String grp, String path);

	/**
	 * Represent the chgrp command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param gid
	 *            The GID of the new group
	 * @param path
	 *            The path representing the file or directory the group of which
	 *            is about to be changed.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult chgrp(SftpSession session, int gid, String path);

	/**
	 * Represent the chown command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param own
	 *            the new owner
	 * @param path
	 *            The path representing the file or directory the owner of which
	 *            is about to be changed.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 * @deprecated
	 */
	// public SftpResult chown(SftpSession session, String own, String path);

	/**
	 * Represent the chown command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param uid
	 *            The UID of the new owner
	 * @param path
	 *            The path representing the file or directory the owner of which
	 *            is about to be changed.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult chown(SftpSession session, int uid, String path);

	/**
	 * Represent the chmod command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param mode
	 *            an three-digit octal number, 0755 for instance, representing
	 *            the new permissions
	 * @param path
	 *            The path representing the file or directory the permission of
	 *            which is about to be changed.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult chmod(SftpSession session, int mode, String path);

	/**
	 * Represent the ln/symlink command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param src
	 *            The path of the file to be symlinked.
	 * @param link
	 *            The path of the link file
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult ln(SftpSession session, String src, String link);

	/**
	 * Represent the lumask command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param umask
	 *            The new umask.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult lumask(SftpSession session, String umask);

	/**
	 * Represent the help command.
	 * 
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult help(SftpSession session);

	/**
	 * Represent the lmkdir command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The path of the folder to be created.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation
	 */
	public SftpResult lmkdir(SftpSession session, String path);

	/**
	 * Represent the lls command. Invoking this method is equivalent to:
	 * 
	 * <pre>
	 * lls(session, &quot;.&quot;)
	 * </pre>
	 * 
	 * @see #lls(SftpSession, String)
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>List<{@link SftpFile}></code> object, if this lls operation
	 *         succeed.
	 */
	public SftpResult lls(SftpSession session);

	/**
	 * Represent the lls command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @param path
	 *            The local path to be listed.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>List<{@link SftpFile}></code> object, if this lls operation
	 *         succeed.
	 */
	public SftpResult lls(SftpSession session, String path);

	/**
	 * Represent the version command.
	 * 
	 * @param session
	 *            The {@link SftpSession} object you previously got when
	 *            connecting.
	 * @return an {@link SftpResult} object representing the result of this
	 *         operation. Invoking <code>getExtension()</code> on the returned
	 *         <code>SftpResult</code> object should return a
	 *         <code>String</code> object, if this operation succeed.
	 */
	public SftpResult version(SftpSession session);
}
