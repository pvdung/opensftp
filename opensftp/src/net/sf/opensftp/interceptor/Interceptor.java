package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;
import net.sf.opensftp.SftpResult;

/**
 * <code>Interceptor</code>s intercept method invocations ( except method calls
 * to <code>connect</code> and <code>disconnect</code> ) on
 * {@link net.sf.opensftp.SftpUtil} objects to provide various customized
 * functionality.
 * 
 * @author BurningXFlame@gmail.com
 */
public interface Interceptor {

	/**
	 * A callback method which will be called before calling the given method.
	 * 
	 * @param method
	 *            the intercepted method
	 * @param args
	 *            the arguments passed in the intercepted method
	 */
	public void beforeMethod(Method method, Object[] args);

	/**
	 * A callback method which will be called after calling the given method.
	 * 
	 * @param method
	 *            the intercepted method
	 * @param args
	 *            the arguments passed in the intercepted method
	 * @param result
	 *            the result returned by the intercepted method
	 */
	public void afterMethod(Method method, Object[] args, SftpResult result);

}
