package net.sf.opensftp.interceptor;

import java.lang.reflect.Method;
import net.sf.opensftp.SftpResult;

public interface Interceptor {
	
	public void beforeMethod(Method method, Object[] args);

	public void afterMethod(Method method, Object[] args, SftpResult result);
	
}
