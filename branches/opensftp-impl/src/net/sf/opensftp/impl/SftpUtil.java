package net.sf.opensftp.impl;

import java.io.File;
import java.util.Hashtable;

import javax.swing.JOptionPane;

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
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpResult;

public class SftpUtil implements net.sf.opensftp.SftpUtil {
	private static Logger log = Logger.getLogger(SftpUtil.class);

	private final static String known_hosts_file = System
			.getProperty("user.home")
			+ "/.ssh/known_hosts";

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
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult chown(SftpSession session, String own, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult chown(SftpSession session, int uid, String path) {
		// TODO Auto-generated method stub
		return null;
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
			jsch.addIdentity(new File(System.getProperty("user.home")
					+ "/.ssh/" + identityFile).getAbsolutePath());

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
			return new SftpSessionImpl(channel);

		} catch (JSchException e) {
			String error = "Failed to login. " + user + "@" + host + ":" + port;
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
			return new SftpSessionImpl(channel);
		} catch (JSchException e) {
			String error = "Failed to login. " + user + "@" + host + ":" + port;
			log.error(error, e);
			throw new SftpException(error);
		}
	}

	public void disconnect(SftpSession session) {
		// TODO Auto-generated method stub

	}

	public SftpResult get(SftpSession session, String remoteFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult get(SftpSession session, String remoteFilename,
			String localFilename, ProgressListener progressListener) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult help(SftpSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lcd(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lpwd(SftpSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult ls(SftpSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult ls(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lumask(SftpSession session, String umask) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult mkdir(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult put(SftpSession session, String localFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult put(SftpSession session, String localFilename,
			String remoteFilename, ProgressListener progressListener) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult rm(SftpSession session, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult rmdir(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	private class UserInfo4PubkeyAuth extends BaseUserInfo {
		String passphrase;

		public String getPassphrase() {
			log.debug("Passphrase retrieved.");
			return passphrase;
		}

		public void setPassphrase(String passphrase) {
			this.passphrase = passphrase;
		}

	}

	private class UserInfo4PasswdAuth extends BaseUserInfo {
		String passwd;

		public String getPassword() {
			log.debug("Password retrieved.");
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
			log.debug(message);
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
				Object[] options = { "yes", "no" };
				int foo = JOptionPane.showOptionDialog(null, str, "Warning",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				flag = (foo == 0);
				break;
			}

			log.debug(str + flag);
			return flag;
		}

		public boolean promptPassword(String message) {
			log.debug("Asking for password...");
			return true;
		}

		public boolean promptPassphrase(String message) {
			log.debug("Asking for passphrase...");
			return true;
		}
	}

	private static class MyLogger implements com.jcraft.jsch.Logger {
		private static Logger log = Logger.getLogger(JSch.class);
		private static Logger logger4LoggingInterceptor = Logger
				.getLogger(net.sf.opensftp.interceptor.LoggingInterceptor.class);

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
