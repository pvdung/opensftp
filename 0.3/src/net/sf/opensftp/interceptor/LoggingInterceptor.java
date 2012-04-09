package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import net.sf.opensftp.SftpFile;
import net.sf.opensftp.SftpResult;
import net.sf.opensftp.SftpSession;

/**
 * <code>LoggingInterceptor</code>s intercept method invocations on
 * {@link net.sf.opensftp.SftpUtil} objects to provide logging functionality.
 * <p>
 * The class use a customized session-specific attribute
 * <code>nestCounter</code> to eliminate duplicate logs caused by nested method
 * call, for example, <code>put(SftpSession, String)</code> invokes
 * <code>put(SftpSession, String,
 * remoteFilename)</code> for implementation.
 * 
 * @author BurningXFlame@gmail.com
 */
public class LoggingInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(LoggingInterceptor.class);

	private boolean logCommand = false;
	private static final String NEST_COUNTER = "nestCounter";

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
		int nestCounter = 0;
		SftpSession session = (SftpSession) args[0];
		Hashtable extras = session.getExtras();
		Integer counter = (Integer) extras.get(NEST_COUNTER);
		if (counter != null) {
			nestCounter = counter.intValue();
		}
		nestCounter--;
		extras.put(NEST_COUNTER, Integer.valueOf(nestCounter));
		
		if (nestCounter != 0) {
			return;
		}
		StringBuilder s = new StringBuilder();
		if (result == null)
			return;

		boolean success = result.getSuccessFlag();

		if (success) {// output
			String methodName = method.getName();

			if (methodName.equals("get")) {
				// Now ProgressListener is in charge of this.
			} else if (methodName.equals("help")) {
				s.append(result.getExtension());
			} else if (methodName.equals("lpwd")) {
				s.append("Local working directory: " + result.getExtension());
			} else if (methodName.equals("ls") || methodName.equals("lls")) {
				List<SftpFile> l = (List<SftpFile>) result.getExtension();
				Iterator<SftpFile> i = l.iterator();
				while (i.hasNext()) {
					s.append(i.next());
					if (i.hasNext())
						s.append("\n");
				}
			} else if (methodName.equals("put")) {
				// Now ProgressListener is in charge of this.
			} else if (methodName.equals("pwd")) {
				s.append("Remote working directory: " + result.getExtension());
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
		int nestCounter = 0;
		SftpSession session = (SftpSession) args[0];
		Hashtable extras = session.getExtras();
		Integer counter = (Integer) extras.get(NEST_COUNTER);
		if (counter != null) {
			nestCounter = counter.intValue();
		}
		nestCounter++;
		extras.put(NEST_COUNTER, Integer.valueOf(nestCounter));
		
		if (nestCounter != 1) {
			return;
		}

		if (logCommand) {
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
				s.append(Integer.toOctalString(((Integer) args[1]).intValue()));
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
