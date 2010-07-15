package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import net.sf.opensftp.SftpFile;
import net.sf.opensftp.SftpResult;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtilFactory;

/**
 * <code>LoggingInterceptor</code>s intercept method invocations on
 * {@link net.sf.opensftp.SftpUtil} objects to provide logging functionality.
 * 
 * @version
 * @author BurningXFlame
 */
public class LoggingInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(LoggingInterceptor.class);

	private boolean logCommand = true;

	/**
	 * Determine whether to log commands or not.
	 * 
	 * @param logCommand
	 *            log commands if true. The default value is <code>true</code>.
	 */
	public void logCommand(boolean logCommand) {
		this.logCommand = logCommand;
	}

	public void afterMethod(Method method, Object[] args, SftpResult result) {
		StringBuilder s = new StringBuilder();
		boolean success = result.getSuccessFalg();

		if (success) {// output
			String methodName = method.getName();

			if (methodName.equals("ls")) {
				List<SftpFile> l = (List<SftpFile>) result.getExtension();
				Iterator<SftpFile> i = l.iterator();
				while (i.hasNext()) {
					s.append(i.next());
					s.append("\n");
				}

			} else if (methodName.equals("pwd")) {
				s.append(((SftpFile) result.getExtension()).getFullName());
			} else if (methodName.equals("put")) {

			} else if (methodName.equals("get")) {

			} else if (methodName.equals("lpwd")) {

			} else if (methodName.equals("lls")) {

			}
			log.info(s);
		} else {// error message
			log.info(result.getErrorMessage());
		}

	}

	public void beforeMethod(Method method, Object[] args) {
		if (logCommand) {
			SftpSession session = (SftpSession) args[0];
			StringBuilder s = new StringBuilder("[");
			s.append(session.getUser());
			s.append("@");
			s.append(session.getHost());
			s.append(" ");
			s.append(session.getCurrentPath().getName());
			s.append("]$ ");
			s.append(method.getName());
			for (int i = 1; i < args.length; i++) {
				s.append(" ");
				s.append(args[i]);
			}
			log.info(s);
		}
	}
}
