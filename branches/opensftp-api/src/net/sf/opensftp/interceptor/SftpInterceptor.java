package net.sf.opensftp.interceptor;

import java.util.Map;
import net.sf.opensftp.SftpResult;

public interface SftpInterceptor {
	public void beforeMethod(String methodId, Object[] args);

	public void afterMethod(String methodId, Object[] args, SftpResult result,
			Map attrs);
}
