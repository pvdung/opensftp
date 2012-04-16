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

public class EncodingTest {
	private static Logger log = Logger.getLogger(EncodingTest.class);
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
	 * Test encoding
	 */
	@Test
	public void testEncoding() {
		String UTName = "testEncoding";
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

		SftpResult result = util.version(session);
		assertTrue(result.getSuccessFlag());
		
		result = util.mkdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp/README");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp/README-读我");
		assertTrue(result.getSuccessFlag());

		result = util.get(session, "tmp4sftp/README", "D:/Received/README2");
		assertTrue(result.getSuccessFlag());

		result = util.get(session, "tmp4sftp/README-读我", "D:/Received");
		assertTrue(result.getSuccessFlag());

		result = util.rename(session, "tmp4sftp/README-读我", "tmp4sftp/读我");
		assertTrue(result.getSuccessFlag());

		result = util.ls(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.rm(session, "tmp4sftp/*");
		assertTrue(result.getSuccessFlag());

		result = util.rmdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());
	}

}
