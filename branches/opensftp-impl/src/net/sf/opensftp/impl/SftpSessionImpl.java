package net.sf.opensftp.impl;

import net.sf.opensftp.SftpFile;
import net.sf.opensftp.SftpUtilFactory;

import com.jcraft.jsch.ChannelSftp;

/**
 * 
 * @author Stephen XIANG
 * 
 */
public class SftpSessionImpl implements net.sf.opensftp.SftpSession {
	private ChannelSftp channelSftp = null;
	private boolean dirChanged = false;
	private String host;
	private String user;
	private String serverDateTimePattern;
	private SftpFile currentPath;

	public SftpSessionImpl(ChannelSftp channelSftp) {
		this.channelSftp = channelSftp;
	}
	
	public ChannelSftp getChannelSftp() {
		return channelSftp;
	}

	public void setChannelSftp(ChannelSftp channelSftp) {
		this.channelSftp = channelSftp;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setServerDateTimePattern(String serverDateTimePattern) {
		this.serverDateTimePattern = serverDateTimePattern;
	}

	public void setCurrentPath(SftpFile currentPath) {
		this.currentPath = currentPath;
	}

	public void setDirChanged(boolean dirChanged) {
		this.dirChanged = dirChanged;
	}

	public SftpFile getCurrentPath() {
		if (currentPath == null) {
			dirChanged = true;
		}
		if (dirChanged) {
			currentPath = (SftpFile) SftpUtilFactory.getSftpUtil().pwd(this)
					.getExtension();
		}
		return currentPath;
	}

	public String getUser() {
		return user;
	}

	public String getServerDateTimePattern() {
		return serverDateTimePattern;
	}

}
