package net.sf.opensftp.impl;

import java.io.File;

import net.sf.opensftp.ServerConstants;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultProgressListenerAndPrompterTest {
	private static Logger log = Logger
			.getLogger(DefaultProgressListenerAndPrompterTest.class);
	private static SftpUtil util = SftpUtilFactory.getSftpUtil();

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

	@Test
	public void testDefaultProgressListenerAndPrompter() {
		String UTName = "testDefaultProgressListenerAndPrompter";
		int i = 1;
		log.info(UTName + " - case " + i++);
		try {
			SftpSession session = util.connectByPasswdAuth(
					ServerConstants.host, ServerConstants.user,
					ServerConstants.password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_ASK);
			util.cd(session, "..");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
