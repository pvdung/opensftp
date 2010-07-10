package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import net.sf.opensftp.SftpResult;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtilFactory;

public class LoggingInterceptor implements Interceptor {
	private static Logger log = Logger.getLogger(LoggingInterceptor.class);

	private boolean logCommand;

	/**
	 * Determine whether to log commands or not.
	 * 
	 * @param logCommand
	 *            log commands if true
	 */
	public void logCommand(boolean logCommand) {
		this.logCommand = logCommand;
	}

	/**
	 * Method invoked upon completion of execution of the given method.
	 * 
	 * @param method
	 *            the intercepted method
	 * @param args
	 *            the arguments passed in the intercepted method
	 * @param result
	 *            the result returned by the intercepted method
	 */
	public void afterMethod(Method method, Object[] args, SftpResult result) {
		if(logCommand){
			SftpSession session = (SftpSession)args[0];
			StringBuilder s= new StringBuilder("[");
			s.append(session.getUser());
			s.append("@");
			s.append(session.getHost());
			s.append(" ");
			s.append(session.getCurrentPath().getName());
			s.append("]$ ");
			s.append(method.getName());
			for(Object obj : args){
				s.append(" ");
				s.append(obj);
			}

			log.info(s);
		}
		
	}

	/**
	 * Method invoked prior to executing the given method.
	 * 
	 * @param method
	 *            the intercepted method
	 * @param args
	 *            the arguments passed in the intercepted method
	 */
	public void beforeMethod(Method method, Object[] args) {


	}

}
