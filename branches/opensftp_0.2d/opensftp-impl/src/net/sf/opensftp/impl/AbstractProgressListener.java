package net.sf.opensftp.impl;

import net.sf.opensftp.*;

public abstract class AbstractProgressListener implements ProgressListener,
		com.jcraft.jsch.SftpProgressMonitor {

	protected boolean isCancelled = false;
	private volatile Object lock = new Object();

	public abstract void complete();

	public abstract void init(int op, String src, String dest, long total);

	public abstract void progress(long delta);

	public void cancel() {
		synchronized (lock) {
			isCancelled = true;
		}
	}

	public boolean isCancelled() {
		synchronized (lock) {
			return isCancelled;
		}
	}

	public boolean count(long count) {
		progress(count);
		return !isCancelled();
	}

	public void end() {
		complete();
	}

}
