package net.sf.opensftp.impl;

import net.sf.opensftp.*;

/**
 * If you want to plug a <code>ProgressListener</code> module into
 * <code>net.sf.opensftp.impl.SftpUtil</code>, your
 * <code>ProgressListener</code> must extend this class.
 * 
 * @author BurningXFlame@gmail.com
 * @since 0.2 
 */
public abstract class BaseProgressListener extends AbstractProgressListener
		implements com.jcraft.jsch.SftpProgressMonitor {

	public boolean count(long count) {
		progress(count);
		if (isCancelled()) {
			setIdle(true);
		}
		return !isCancelled();
	}

	public void end() {
		complete();
	}

}
