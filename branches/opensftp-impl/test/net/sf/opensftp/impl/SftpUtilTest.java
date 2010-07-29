package net.sf.opensftp.impl;

import static org.junit.Assert.*;

import java.io.File;

import net.sf.opensftp.SftpException;
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
	private static String host = "192.168.234.129";
	private static int port = 22;
	private static String user = "guest";
	private static String password = "guest";
	private static String identityFile = "~/.ssh/sxiang_rsa";
	private static String passphrase = "";
	private static int timeout = 0;

	private final static String known_hosts_file = System
			.getProperty("user.home")
			+ "/.ssh/known_hosts";
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

	@Test//(expected = Throwable.class)
	public void testConnect() {
		String UTName = "testConnect";

		int[] options = { //SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO,
				SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES,
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
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO:
				assertNull(session);
				break;
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES:
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
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO:
				assertNull(session);
				break;
			case SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES:
				assertNotNull(session);
				break;
			}
			tearDown();
		}

		// non-empty passphrase
		String identityFile2 = "~/.ssh/sxiang_rsa_passphrase";
		String passphrase2 = "a sample passphrase";

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
}
