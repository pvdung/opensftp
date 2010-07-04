package net.sf.opensftp.impl;

import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpResult;

public class SftpUtil implements net.sf.opensftp.SftpUtil{

	public SftpResult cd(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult chgrp(SftpSession session, String grp, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult chgrp(SftpSession session, int gid, String path) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	public SftpSession connect(String host, String user, String passphrase,
			String identityFile, int strictHostKeyChecking)
			throws SftpException {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpSession connect(String host, int port, String user,
			String passphrase, String identityFile, int strictHostKeyChecking)
			throws SftpException {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpSession connectByPasswdAuth(String host, String user,
			String password, int strictHostKeyChecking) throws SftpException {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpSession connectByPasswdAuth(String host, int port, String user,
			String password, int strictHostKeyChecking) throws SftpException {
		// TODO Auto-generated method stub
		return null;
	}

	public void disconnect(SftpSession session) {
		// TODO Auto-generated method stub
		
	}

	public SftpResult get(SftpSession session, String remoteFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult get(SftpSession session, String remoteFilename,
			String localFilename) {
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

	public SftpResult mkdir(SftpSession session, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult put(SftpSession session, String localFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult put(SftpSession session, String localFilename,
			String remoteFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult pwd(SftpSession session) {
		// TODO Auto-generated method stub
		return null;
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

}
