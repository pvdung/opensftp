package net.sf.opensftp.impl;

import static org.junit.Assert.*;

import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;
import net.sf.opensftp.SftpUtilFactoryTest;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SftpUtilTest {
	private static Logger log = Logger.getLogger(SftpUtilTest.class);
	private static SftpUtil util = SftpUtilFactory.getSftpUtil();
	private static SftpSession session;
	private static String host = "";
	private static int port = 22;
	private static String user = "";
	private static String identityFile = "";
	private static String passphrase = "";
	private static int timeout = 0;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
		util.disconnect(session);
	}

	@Test
	public void testConnect() {
		String UTName = "testConnect";

		try {
			int[] options = { SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_YES,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_ASK };

			int i = 1;
			for (int strictHostKeyChecking : options) {
				log.info(UTName + " - case " + i++);
				setUp();
				session = util.connect(host, port, user, passphrase,
						identityFile, strictHostKeyChecking, timeout);
				assertNotNull(session);
				tearDown();
			}

		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
