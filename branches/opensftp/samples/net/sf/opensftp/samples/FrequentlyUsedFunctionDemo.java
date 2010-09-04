package net.sf.opensftp.samples;

import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpResult;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;

/**
 * Demonstrate frequently used sftp commands
 * @author BurningFlame
 *
 */
public class FrequentlyUsedFunctionDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SftpUtil util = SftpUtilFactory.getSftpUtil();
		SftpSession session = null;
		String host = "192.168.234.132";
		String user = "guest";
		String password = "guest";

		try {
			//connect
			session = util.connectByPasswdAuth(host, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES);
		} catch (SftpException e) {
			e.printStackTrace();
		}
		if (session == null)
			return;
		util.help(session); //print the help information
		
		//OpenSftp provide a default logging interceptor to capture the print the output or error message of commands.
		//If you want to handle it by yourself, as follows is a samples.
		SftpResult result = util.help(session);
		if(result.getSuccessFlag())
			System.out.println(result.getExtension());
		else
			System.out.println(result.getErrorMessage());
		
		//here we continue
		util.pwd(session); //print the working directory (current path)
		util.version(session); //print the sftp version information
		util.ls(session); //list the current path if path not specified
		util.mkdir(session, "tmp4sftp"); //make a directory 'tmp4sftp' under current path
		util.put(session, "D:/Received/README", "tmp4sftp"); //upload a file to a folder relative to the current path
		util.put(session, "D:/Received/README", "tmp4sftp/README2"); //upload a file and rename the copy
		util.get(session, "tmp4sftp/README2", "D:/Received"); //download a file to a specified local path
		util.get(session, "tmp4sftp/README2", "D:/Received/README3"); //download a file and rename the copy
		util.rename(session, "tmp4sftp/README2", "tmp4sftp/README3"); //rename a remote file
		util.rm(session, "tmp4sftp/*"); //remove files
		util.rmdir(session, "tmp4sftp"); //remove a directory
		util.cd(session, ".."); //change directory

		util.disconnect(session); //quit
	}

}
