package net.sf.opensftp.impl;

import net.sf.opensftp.*;

/**
 * If you want to plug in <code>ProgressListener</code> module to
 * <code>net.sf.opensftp.impl.SftpUtil</code>, your
 * <code>ProgressListener</code> must extend this class.
 * 
 * @author BurningXFlame@gmail.com
 * 
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
