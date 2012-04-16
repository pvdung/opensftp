package net.sf.opensftp.interceptor;

import net.sf.opensftp.ServerConstants;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;

import org.apache.log4j.Logger;
import org.junit.Test;

public class InterceptorTest {
	private static Logger log = Logger.getLogger(InterceptorTest.class);
	private static SftpUtil util = SftpUtilFactory.getSftpUtil();
	
	@Test
	public void testInterceptorFunctionality() {
		String UTName = "testInterceptorFunctionality";
		int i = 1;
		log.info(UTName + " - case " + i++);
		try {
			SftpSession session = util.connectByPasswdAuth(
					ServerConstants.host, ServerConstants.user,
					ServerConstants.password,
					SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
			util.cd(session, "..");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
