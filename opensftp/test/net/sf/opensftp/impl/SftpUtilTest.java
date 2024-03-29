package net.sf.opensftp.impl;

import static org.junit.Assert.*;

import java.io.File;

import net.sf.opensftp.ServerConstants;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpResult;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;
import net.sf.opensftp.SftpUtilFactoryTest;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SftpUtilTest {
	private static Logger log = Logger.getLogger(SftpUtilTest.class);
	private static SftpUtil util = SftpUtilFactory.getSftpUtil();
	private static SftpSession session;
	private static String host = ServerConstants.host;
	private static int port = ServerConstants.port;
	private static String user = ServerConstants.user;
	private static String password = ServerConstants.password;
	private static String identityFile = ServerConstants.identityFile;
	private static String passphrase = ServerConstants.passphrase;
	private static int timeout = ServerConstants.timeout;

	private final static String known_hosts_file = System
			.getProperty("user.home") + "/.ssh/known_hosts";
	private final static String known_hosts_file_bak = known_hosts_file
			+ ".bak";

	@BeforeClass
	public static void setUpBeforeClass() {
		File knownHostsFile = new File(known_hosts_file);
		if (knownHostsFile.exists()) {
			knownHostsFile.renameTo(new File(known_hosts_file_bak));
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {
		File knownHostsFile_bak = new File(known_hosts_file_bak);
		if (knownHostsFile_bak.exists()) {
			knownHostsFile_bak.renameTo(new File(known_hosts_file));
		}
	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
		if (session != null)
			util.disconnect(session);
		new File(known_hosts_file).delete();
	}

	/**
	 * Test connection functions:<br>
	 * connect through Password Authentication with strictHostKeyChecking = yes.
	 */
	@Test(expected = Throwable.class)
	public void testConnectionFunctionality_passwordAuth_strictHostKeyChecking() {
		String UTName = "testConnectionFunctionality_passwordAuth_unstrictHostKeyChecking";

		int i = 1;

		log.info("-------------------" + UTName + " - case " + i++
				+ "--------------------");
		try {
			session = util.connectByPasswdAuth(host, port, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO, timeout);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNull(session);
	}

	/**
	 * Test connection functions:<br>
	 * connect through Password Authentication with strictHostKeyChecking = yes.
	 */
	@Test(expected = Throwable.class)
	public void testConnectionFunctionality_pubkeyAuth_strictHostKeyChecking() {
		String UTName = "testConnectionFunctionality_pubkeyAuth_unstrictHostKeyChecking";

		int i = 1;

		log.info("-------------------" + UTName + " - case " + i++
				+ "--------------------");
		try {
			session = util.connect(host, port, user, passphrase, identityFile,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO, timeout);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNull(session);
	}

	/**
	 * Test connection functions:<br>
	 * connect, disconnect.
	 */
	@Test//(expected = Throwable.class)
	public void testConnectionFunctionalities() {
		String UTName = "testConnect";

		int[] options = { //SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES,
				SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO,
				SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_ASK };
		int i = 1;

		// password authorization
		for (int strictHostKeyChecking : options) {
			log.info("-------------------" + UTName + " - case " + i++
					+ "--------------------");
			setUp();
			try {
				session = util.connectByPasswdAuth(host, port, user, password,
						strictHostKeyChecking, timeout);
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (strictHostKeyChecking) {
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES:
				assertNull(session);
				break;
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO:
				assertNotNull(session);
				break;
			}
			tearDown();
		}

		// public key authorization
		// empty passphrase
		for (int strictHostKeyChecking : options) {
			log.info("-------------------" + UTName + " - case " + i++
					+ "--------------------");
			setUp();
			try {
				session = util.connect(host, port, user, passphrase,
						identityFile, strictHostKeyChecking, timeout);
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (strictHostKeyChecking) {
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES:
				assertNull(session);
				break;
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO:
				assertNotNull(session);
				break;
			}
			tearDown();
		}

		// non-empty passphrase
		String identityFile2 = ServerConstants.identityFile2;
		String passphrase2 = ServerConstants.passphrase2;

		for (int strictHostKeyChecking : options) {
			log.info("-------------------" + UTName + " - case " + i++
					+ "--------------------");
			setUp();
			try {
				session = util.connect(host, port, user, passphrase2,
						identityFile2, strictHostKeyChecking, timeout);
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertNotNull(session);
			tearDown();
		}

	}

	/**
	 * Test frequently used functions:<br>
	 * cd, get, help(?), ls, mkdir, put, pwd, rename, rm, rmdir, version.
	 */
	@Test
	public void testFrequentlyUsedFunctions() {
		String UTName = "testCommonFunctions";
		int i = 1;
		log.info("-------------------" + UTName + " - case " + i++
				+ "--------------------");
		try {
			session = util.connectByPasswdAuth(host, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(session);

		SftpResult result = util.help(session);
		assertTrue(result.getSuccessFlag());

		result = util.ls(session);
		assertTrue(result.getSuccessFlag());

		result = util.pwd(session);
		assertTrue(result.getSuccessFlag());

		result = util.mkdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp/README2");
		assertTrue(result.getSuccessFlag());

		result = util.get(session, "tmp4sftp/README2", "D:/Received");
		assertTrue(result.getSuccessFlag());

		result = util.get(session, "tmp4sftp/README2", "D:/Received/README3");
		assertTrue(result.getSuccessFlag());

		result = util.rename(session, "tmp4sftp/README2", "tmp4sftp/README3");
		assertTrue(result.getSuccessFlag());

		result = util.rm(session, "tmp4sftp/*");
		assertTrue(result.getSuccessFlag());

		result = util.rmdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.cd(session, "..");
		assertTrue(result.getSuccessFlag());

		result = util.pwd(session);
		assertTrue(result.getSuccessFlag());

		result = util.version(session);
		assertTrue(result.getSuccessFlag());
	}

	/**
	 * Test local functions:<br>
	 * lcd, lls, lmkdir, lpwd.
	 */
	@Test
	public void testLocalFunctions() {
		String UTName = "testLocalFunctions";
		int i = 1;
		log.info("-------------------" + UTName + " - case " + i++
				+ "--------------------");

		try {
			session = util.connectByPasswdAuth(host, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(session);

		SftpResult result;

		// unsupported function
		// result = util.lls(session);
		// assertTrue(result.getSuccessFlag());

		result = util.lpwd(session);
		assertTrue(result.getSuccessFlag());

		// unsupported function
		// result = util.lmkdir(session, "tmp4sftp");
		// assertTrue(result.getSuccessFlag());

		result = util.lcd(session, "..");
		assertTrue(result.getSuccessFlag());

		result = util.lpwd(session);
		assertTrue(result.getSuccessFlag());
	}

	/**
	 * Test rarely used functions:<br>
	 * chgrp, chmod, chown, ln(symlink), lumask.
	 */
	@Test
	public void testRarelyUsedFunctions() {
		String UTName = "testRarelyUsedFunctions";
		int i = 1;
		log.info("-------------------" + UTName + " - case " + i++
				+ "--------------------");

		try {
			session = util.connectByPasswdAuth(host, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(session);

		int uid = 1001;
		int gid = 1001;
		int mode = 0755;
		String path = "tmp4sftp/README";
		String pathLn = "README.ln";

		SftpResult result;

		// preparations
		result = util.mkdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		// start testing
		result = util.ln(session, path, pathLn);
		assertTrue(result.getSuccessFlag());

		result = util.chmod(session, mode, path);
		assertTrue(result.getSuccessFlag());

		result = util.chgrp(session, gid, path);
		assertTrue(result.getSuccessFlag());

		result = util.chown(session, uid, path);
		assertTrue(result.getSuccessFlag());

		// unsupported function
		// String mask="";
		// result = util.lumask(session, mask);
		// assertTrue(result.getSuccessFlag());

		// site clearing
		result = util.rm(session, pathLn);
		assertTrue(result.getSuccessFlag());

		result = util.rm(session, "tmp4sftp/*");
		assertTrue(result.getSuccessFlag());

		result = util.rmdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());
	}
}
