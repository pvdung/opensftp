package net.sf.opensftp;

public class SftpUtilMock implements SftpUtil {

	public SftpResult cd(SftpChannel channel, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpChannel connect(String host, String user, String identityFile)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpChannel connect(String host, String user, String passphrase,
			String identityFile) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpChannel connect(String host, int port, String user,
			String passphrase, String identityFile) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpChannel connectByPasswdAuth(String host, String user,
			String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpChannel connectByPasswdAuth(String host, int port, String user,
			String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void disconnect(SftpChannel channel) {
		// TODO Auto-generated method stub

	}

	public SftpResult get(SftpChannel channel, String remoteFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult get(SftpChannel channel, String remoteFilename,
			String localFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lcd(SftpChannel channel, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult lpwd(SftpChannel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult ls(SftpChannel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult ls(SftpChannel channel, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult mkdir(SftpChannel channel, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult put(SftpChannel channel, String localFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult put(SftpChannel channel, String localFilename,
			String remoteFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult pwd(SftpChannel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult rename(SftpChannel channel, String oldpath, String newpath) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult rm(SftpChannel channel, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	public SftpResult rmdir(SftpChannel channel, String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
