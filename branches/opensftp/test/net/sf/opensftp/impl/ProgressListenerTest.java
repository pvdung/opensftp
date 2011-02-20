package net.sf.opensftp.impl;

import static org.junit.Assert.*;

import java.io.File;

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

public class ProgressListenerTest {
	private static Logger log = Logger.getLogger(ProgressListenerTest.class);
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

	@Test
	public void testProgressListenerFunction() {
		String UTName = "testProgressListnerFunction";
		int i = 1;
		log.info(UTName + " - case " + i++);

		try {
			session = util.connectByPasswdAuth(host, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(session);

		SftpResult result = util.mkdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README", "tmp4sftp/README2");
		assertTrue(result.getSuccessFlag());

		result = util.get(session, "tmp4sftp/README2", "D:/Received");
		assertTrue(result.getSuccessFlag());

		result = util.get(session, "tmp4sftp/README2", "D:/Received/README3");
		assertTrue(result.getSuccessFlag());

		result = util.rm(session, "tmp4sftp/*");
		assertTrue(result.getSuccessFlag());

		result = util.put(session, "D:/Received/README*", "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		result = util.rm(session, "tmp4sftp/*");
		assertTrue(result.getSuccessFlag());

		result = util.rmdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());
	}

	/**
	 * Test whether ProgressLister works in multi-threading environment.
	 */
	@Test
	public void testProgressListenerFunctionInMultiThreadingEnvironment() {
		String UTName = "testProgressListnerFunction";
		int i = 1;
		log.info(UTName + " - case " + i++);

		try {
			session = util.connectByPasswdAuth(host, user, password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(session);

		SftpResult result = util.mkdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());
		//

		Thread t = new Thread(new Runnable() {

			public void run() {
				SftpResult result = util.put(session, "D:/Received/README",
						"tmp4sftp/README2");
				assertTrue(result.getSuccessFlag());
			}
		});

		t.start();

		result = util.put(session, "D:/Received/README", "tmp4sftp");
		assertTrue(result.getSuccessFlag());

		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// end
		result = util.rm(session, "tmp4sftp/*");
		assertTrue(result.getSuccessFlag());

		result = util.rmdir(session, "tmp4sftp");
		assertTrue(result.getSuccessFlag());
	}
}
