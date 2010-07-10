package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import net.sf.opensftp.SftpResult;

public class LoggingInterceptor implements Interceptor {
	
	private boolean logCommand;
	
	/**
	 * Determine whether to log commands or not.
	 * @param logCommand log commands if true
	 */
	public void logCommand(boolean logCommand) {
		this.logCommand = logCommand;
	}

	/**
	 * Method invoked upon completion of execution of the given method.
	 * @param method the intercepted method
	 * @param args the arguments passed in the intercepted method
	 * @param result the result returned by the intercepted method 
	 */
	public void afterMethod(Method method, Object[] args, SftpResult result) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method invoked prior to executing the given method.
	 * @param method the intercepted method
	 * @param args the arguments passed in the intercepted method
	 */
	public void beforeMethod(Method method, Object[] args) {
		// TODO Auto-generated method stub
		
	}

	
}
