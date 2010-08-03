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

	private boolean logCommand = false;

	/**
	 * This property is used to make <code>LoggingInterceptor</code> work well
	 * even when sftp methods are nested, i.e. an sftp method is invoked by
	 * another sftp method.
	 */
	private int nestCounter = 0;

	/**
	 * Determine whether to log commands or not.
	 * 
	 * @param logCommand
	 *            log commands if true. The default value is <code>false</code>.
	 */
	public void setLogCommand(boolean logCommand) {
		this.logCommand = logCommand;
	}

	public void afterMethod(Method method, Object[] args, SftpResult result) {
		nestCounter--;
		if (nestCounter != 0) {
			return;
		}
		StringBuilder s = new StringBuilder();
		boolean success = result.getSuccessFalg();

		if (success) {// output
			String methodName = method.getName();

			if (methodName.equals("get")) {

			} else if (methodName.equals("help")) {
				s.append(result.getExtension());
			} else if (methodName.equals("lls")) {

			} else if (methodName.equals("lpwd")) {
				s.append(result.getExtension());
			} else if (methodName.equals("ls")) {
				List<SftpFile> l = (List<SftpFile>) result.getExtension();
				Iterator<SftpFile> i = l.iterator();
				while (i.hasNext()) {
					s.append(i.next());
					if (i.hasNext())
						s.append("\n");
				}
			} else if (methodName.equals("put")) {

			} else if (methodName.equals("pwd")) {
				s.append(result.getExtension());
			} else if (methodName.equals("version")) {
				s.append(result.getExtension());
			}
			if (s.length() != 0)
				log.info(s);
		} else {// error message
			log.info(result.getErrorMessage());
		}
	}

	public void beforeMethod(Method method, Object[] args) {
		nestCounter++;
		if (nestCounter != 1) {
			return;
		}

		if (logCommand) {
			SftpSession session = (SftpSession) args[0];
			StringBuilder s = new StringBuilder("[");
			s.append(session.getUser());
			s.append("@");
			s.append(session.getHost());
			s.append(" ");
			s.append(session.getCurrentPath());
			s.append("]$ ");
			s.append(method.getName());
			if (method.getName().startsWith("chmod")) {
				s.append(" ");
				s.append(Integer.toOctalString(((Integer)args[1]).intValue()));
				s.append(" ");
				s.append(args[2]);
			} else {
				for (int i = 1; i < args.length; i++) {
					if (args[i] != null) {
						s.append(" ");
						s.append(args[i]);
					}
				}
			}
			log.info(s);
		}
	}
}
