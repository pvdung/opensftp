package net.sf.opensftp.impl;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import net.sf.opensftp.ProgressListener;
import net.sf.opensftp.Prompter;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpResult;

public class SftpUtil implements net.sf.opensftp.SftpUtil {
	private Prompter prompter;

	public void setPrompter(Prompter prompter) {
		this.prompter = prompter;
	}

	private static Logger log = Logger.getLogger(SftpUtil.class);
	private static Logger logger4LoggingInterceptor = Logger
			.getLogger(net.sf.opensftp.interceptor.LoggingInterceptor.class);
	private final static String known_hosts_file = System

	.getProperty("user.home") + "/.ssh/known_hosts";

	static {
		JSch.setLogger(new MyLogger());
	}

	public SftpResult cd(SftpSession session, String path) {
		SftpResultImpl result = new SftpResultImpl();
		SftpSessionImpl sessionImpl = (SftpSessionImpl) session;
		ChannelSftp channelSftp = sessionImpl.getChannelSftp();
		try {
			channelSftp.cd(path);
			result.setSuccessFalg(true);
			// update session
			sessionImpl.setDirChanged(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command cd failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult chgrp(SftpSession session, String grp, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult chgrp(SftpSession session, int gid, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.chgrp(gid, path);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command chgrp failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult chmod(SftpSession session, int mode, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.chmod(mode, path);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command chmod failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult chown(SftpSession session, String own, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult chown(SftpSession session, int uid, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.chmod(uid, path);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command chown failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpSession connect(String host, String user, String identityFile,
			int strictHostKeyChecking) throws SftpException {
		return connect(host, 22, user, "", identityFile, strictHostKeyChecking,
				0);
	}

	public SftpSession connect(String host, String user, String passphrase,
			String identityFile, int strictHostKeyChecking)
			throws SftpException {
		return connect(host, 22, user, passphrase, identityFile,
				strictHostKeyChecking, 0);
	}

	public SftpSession connect(String host, int port, String user,
			String passphrase, String identityFile, int strictHostKeyChecking,
			int timeout) throws SftpException {
		ChannelSftp channel = null;
		try {
			JSch jsch = new JSch();
			// private key
			if (identityFile.startsWith("~/") || identityFile.startsWith("~\\")) {
				identityFile = System.getProperty("user.home")
						+ identityFile.substring(identityFile.indexOf("~") + 1);
			}
			jsch.addIdentity(new File(identityFile).getAbsolutePath());

			// known hosts
			jsch.setKnownHosts(new File(known_hosts_file).getAbsolutePath());

			// print known hosts -- for debugging purpose
			HostKeyRepository hkr = jsch.getHostKeyRepository();
			HostKey[] hks = hkr.getHostKey();
			if (hks != null) {
				StringBuilder str = new StringBuilder();
				str.append("Host keys in " + hkr.getKnownHostsRepositoryID()
						+ ":\n");
				for (int i = 0; i < hks.length; i++) {
					HostKey hk = hks[i];
					str.append(hk.getHost() + " " + hk.getType() + " "
							+ hk.getFingerPrint(jsch) + "\n");
				}
				log.debug(str);
			}

			Session session = jsch.getSession(user, host, port);
			UserInfo4PubkeyAuth userinfo = new UserInfo4PubkeyAuth();
			userinfo.setPassphrase(passphrase);
			userinfo.setStrictHostKeyChecking(strictHostKeyChecking);
			session.setUserInfo(userinfo);
			session.connect();

			// print the current host -- for debugging purpose
			HostKey hk = session.getHostKey();
			log.debug("HostKey: " + hk.getHost() + " " + hk.getType() + " "
					+ hk.getFingerPrint(jsch));

			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect(timeout);

			//initialize SftpSessionImpl
			SftpSessionImpl sftpSessionImpl = new SftpSessionImpl(channel);
			sftpSessionImpl.setHost(host);
			sftpSessionImpl.setUser(user);
			sftpSessionImpl.setCurrentPath(channel.pwd());
			sftpSessionImpl.setDirChanged(false);
			return sftpSessionImpl;

		} catch (JSchException e) {
			String error = "Failed to login. " + user + "@" + host + ":" + port;
			log.error(error, e);
			throw new SftpException(error);
		} catch (com.jcraft.jsch.SftpException e) {
			String error = "Failed to retrieve the current working path.";
			log.error(error, e);
			throw new SftpException(error);
		}
	}

	public SftpSession connectByPasswdAuth(String host, String user,
			String password, int strictHostKeyChecking) throws SftpException {
		return connectByPasswdAuth(host, 22, user, password,
				strictHostKeyChecking, 0);
	}

	public SftpSession connectByPasswdAuth(String host, int port, String user,
			String password, int strictHostKeyChecking, int timeout)
			throws SftpException {
		ChannelSftp channel = null;
		try {
			JSch jsch = new JSch();

			jsch.setKnownHosts(new File(known_hosts_file).getAbsolutePath());
			Session session = jsch.getSession(user, host, port);
			UserInfo4PasswdAuth userinfo = new UserInfo4PasswdAuth();
			userinfo.setPassword(password);
			userinfo.setStrictHostKeyChecking(strictHostKeyChecking);
			session.setUserInfo(userinfo);
			session.connect();
			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect(timeout);
			
			//initialize SftpSessionImpl
			SftpSessionImpl sftpSessionImpl = new SftpSessionImpl(channel);
			sftpSessionImpl.setHost(host);
			sftpSessionImpl.setUser(user);
			sftpSessionImpl.setCurrentPath(channel.pwd());
			sftpSessionImpl.setDirChanged(false);
			return sftpSessionImpl;
		} catch (JSchException e) {
			String error = "Failed to login. " + user + "@" + host + ":" + port;
			log.error(error, e);
			throw new SftpException(error);
		} catch (com.jcraft.jsch.SftpException e) {
			String error = "Failed to retrieve the current working path.";
			log.error(error, e);
			throw new SftpException(error);
		}
	}

	public void disconnect(SftpSession session) {
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			// session.disconnect() closes this session and also all channels
			// derived from it.
			channelSftp.getSession().disconnect();
		} catch (JSchException e) {
			log.error("Failed to disconnect.", e);
		}
	}

	public SftpResult get(SftpSession session, String remoteFilename) {
		return get(session, remoteFilename, ".", null);
	}

	/**
	 * <p>
	 * Represent the get command.
	 * </p>
	 * NOTE: This method doesn't completely implement the
	 * {@link net.sf.opensftp.SftpUtil#get(SftpSession, String, String, ProgressListener)}
	 * . The <code>progressListener</code> param must be an
	 * {@link AbstractProgressListener}. Otherwise, an
	 * <code>UnsupportedOperationException</code> will be thrown.
	 * 
	 * @param progressListener
	 *            An {@link AbstractProgressListener} listening the progresses
	 *            of this get operation to provide progress meter functionality.
	 * 
	 */
	public SftpResult get(SftpSession session, String remoteFilename,
			String localFilename, ProgressListener progressListener) {
		AbstractProgressListener convertedProgressListener = null;
		// throws UnsupportedOperationException if the given progressListener is
		// not an AbstractProgressListener
		if (progressListener != null) {
			if (progressListener instanceof AbstractProgressListener) {
				convertedProgressListener = (AbstractProgressListener) progressListener;
			} else {
				throw new UnsupportedOperationException();
			}
		}

		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.get(remoteFilename, localFilename,
					convertedProgressListener);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command get failed", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult help(SftpSession session) {
		String help = "Available commands:\n"
				+ "* means unimplemented command.\n"
				+ "bye                           Quit sftp\n"
				+ "cd path                       Change remote directory to 'path'\n"
				+ "chgrp grp path                Change group of file 'path' to 'grp'\n"
				+ "chmod mode path               Change permissions of file 'path' to 'mode'\n"
				+ "chown own path                Change owner of file 'path' to 'own'\n"
				+ "exit                          Quit sftp\n"
				+ "get remote-path [local-path]  Download file\n"
				+ "help                          Display this help text\n"
				+ "lcd path                      Change local directory to 'path'\n"
				+ "*lls [ls-options [path]]      Display local directory listing\n"
				+ "*lmkdir path                  Create local directory\n"
				+ "ln oldpath newpath            Symlink remote file\n"
				+ "lpwd                          Print local working directory\n"
				+ "ls [path]                     Display remote directory listing\n"
				+ "*lumask umask                 Set local umask to 'umask'\n"
				+ "mkdir path                    Create remote directory\n"
				+ "put local-path [remote-path]  Upload file\n"
				+ "pwd                           Display remote working directory\n"
				+ "quit                          Quit sftp\n"
				+ "rename oldpath newpath        Rename remote file\n"
				+ "rm path                       Delete remote file\n"
				+ "rmdir path                    Remove remote directory\n"
				+ "symlink oldpath newpath       Symlink remote file\n"
				+ "version                       Show SFTP version\n"
				+ "?                             Synonym for help";
		return new SftpResultImpl(true, help);
	}

	public SftpResult lcd(SftpSession session, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.lcd(path);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command lcd failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult lls(SftpSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lls(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lmkdir(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult ln(SftpSession session, String src, String link) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.symlink(src, link);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command ln failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult lpwd(SftpSession session) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		String currentPath = channelSftp.lpwd();
		result.setExtension(currentPath);
		result.setSuccessFalg(true);
		return result;
	}

	public SftpResult ls(SftpSession session) {
		return ls(session, ".");
	}

	/**
	 * Return the absolute path of the given path.
	 */
	private String remoteAbsolutePath(SftpSession session, String path) {
		if (path.charAt(0) == '/')
			return path;
		String cwd = session.getCurrentPath();
		if (cwd.endsWith("/"))
			return cwd + path;
		return cwd + "/" + path;
	}

	public SftpResult ls(SftpSession session, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			String absoultPath = remoteAbsolutePath(session, path);

			List<SftpFileImpl> fileList = new Vector<SftpFileImpl>();
			Vector<ChannelSftp.LsEntry> vv = channelSftp.ls(path);
			Iterator<ChannelSftp.LsEntry> it = vv.iterator();
			while (it.hasNext()) {
				fileList.add(new SftpFileImpl(it.next(), absoultPath));
			}
			result.setExtension(fileList);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command ls failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult lumask(SftpSession session, String umask) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult mkdir(SftpSession session, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.mkdir(path);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command mkdir failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult put(SftpSession session, String localFilename) {
		return put(session, localFilename, ".", null);
	}

	/**
	 * <p>
	 * Represent the put command.
	 * </p>
	 * NOTE: This method doesn't completely implement the
	 * {@link net.sf.opensftp.SftpUtil#get(SftpSession, String, String, ProgressListener)}
	 * . The <code>progressListener</code> param must be an
	 * {@link AbstractProgressListener}. Otherwise, an
	 * <code>UnsupportedOperationException</code> will be thrown.
	 * 
	 * @param progressListener
	 *            An {@link AbstractProgressListener} listening the progresses
	 *            of this put operation to provide progress meter functionality.
	 * 
	 */
	public SftpResult put(SftpSession session, String localFilename,
			String remoteFilename, ProgressListener progressListener) {
		AbstractProgressListener convertedProgressListener = null;
		// throws UnsupportedOperationException if the given progressListener is
		// not an AbstractProgressListener
		if (progressListener != null) {
			if (progressListener instanceof AbstractProgressListener) {
				convertedProgressListener = (AbstractProgressListener) progressListener;
			} else {
				throw new UnsupportedOperationException();
			}
		}

		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.put(localFilename, remoteFilename,
					convertedProgressListener);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command get failed", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult pwd(SftpSession session) {
		SftpResultImpl result = new SftpResultImpl();
		SftpSessionImpl sessionImpl = (SftpSessionImpl) session;
		if (!sessionImpl.getDirChanged()) {
			result.setExtension(sessionImpl.getCurrentPath());
			result.setSuccessFalg(true);
		} else {
			ChannelSftp channelSftp = sessionImpl.getChannelSftp();
			try {
				String currentPath = channelSftp.pwd();
				result.setExtension(currentPath);
				result.setSuccessFalg(true);
				// update session
				sessionImpl.setCurrentPath(currentPath);
			} catch (com.jcraft.jsch.SftpException e) {
				log.error("command pwd failed.", e);
				result.setErrorMessage(e.toString());
				result.setErrorCode(e.id);
			}
		}
		return result;
	}

	public SftpResult rename(SftpSession session, String oldpath, String newpath) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.rename(oldpath, newpath);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command mkdir failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult rm(SftpSession session, String filename) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.rm(filename);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command mkdir failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult rmdir(SftpSession session, String path) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		try {
			channelSftp.rmdir(path);
			result.setSuccessFalg(true);
		} catch (com.jcraft.jsch.SftpException e) {
			log.error("command mkdir failed.", e);
			result.setErrorMessage(e.toString());
			result.setErrorCode(e.id);
		}
		return result;
	}

	public SftpResult version(SftpSession session) {
		SftpResultImpl result = new SftpResultImpl();
		ChannelSftp channelSftp = ((SftpSessionImpl) session).getChannelSftp();
		result.setExtension(channelSftp.version());
		result.setSuccessFalg(true);
		return result;
	}

	private class UserInfo4PubkeyAuth extends BaseUserInfo {
		String passphrase;

		public String getPassphrase() {
			log.info("Passphrase retrieved.");
			logger4LoggingInterceptor.info("Passphrase retrieved.");
			return passphrase;
		}

		public void setPassphrase(String passphrase) {
			this.passphrase = passphrase;
		}

	}

	private class UserInfo4PasswdAuth extends BaseUserInfo {
		String passwd;

		public String getPassword() {
			log.info("Password retrieved.");
			logger4LoggingInterceptor.info("Password retrieved.");
			return passwd;
		}

		public void setPassword(String password) {
			this.passwd = password;
		}
	}

	private class BaseUserInfo implements UserInfo {
		private int strictHostKeyChecking;

		public void setStrictHostKeyChecking(int strictHostKeyChecking) {
			this.strictHostKeyChecking = strictHostKeyChecking;
		}

		public String getPassword() {
			return null;
		}

		public String getPassphrase() {
			return null;
		}

		public void showMessage(String message) {
			log.info(message);
			logger4LoggingInterceptor.info(message);
		}

		public boolean promptYesNo(String str) {
			boolean flag = false;
			switch (strictHostKeyChecking) {
			case net.sf.opensftp.SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES:
				flag = true;
				break;

			case net.sf.opensftp.SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO:
				break;

			case net.sf.opensftp.SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_ASK:
				if (prompter == null) {
					log
							.warn("No prompter configured. Use the default one - net.sf.opensftp.impl.SwingPrompter.");
					prompter = new SwingPrompter();
				}
				flag = prompter.promptYesNo(str);
				break;
			}

			log.info(str + flag);
			logger4LoggingInterceptor.info(str + flag);
			return flag;
		}

		public boolean promptPassword(String message) {
			log.info("Asking for password...");
			logger4LoggingInterceptor.info("Asking for password...");
			return true;
		}

		public boolean promptPassphrase(String message) {
			log.info("Asking for passphrase...");
			logger4LoggingInterceptor.info("Asking for passphrase...");
			return true;
		}
	}

	private static class MyLogger implements com.jcraft.jsch.Logger {
		private static Logger log = Logger.getLogger(JSch.class);

		static Hashtable<Integer, Level> levels = new Hashtable<Integer, Level>();
		static {
			levels.put(DEBUG, Level.DEBUG);
			levels.put(INFO, Level.INFO);
			levels.put(WARN, Level.WARN);
			levels.put(WARN, Level.WARN);
			levels.put(FATAL, Level.FATAL);
		}

		public boolean isEnabled(int level) {
			return log.isEnabledFor(levels.get(level))
					|| logger4LoggingInterceptor
							.isEnabledFor(levels.get(level));
		}

		public void log(int level, String message) {
			log.log(levels.get(level), message);
			logger4LoggingInterceptor.log(levels.get(level), message);
		}
	}
}
