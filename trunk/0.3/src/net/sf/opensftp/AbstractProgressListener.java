package net.sf.opensftp;

/**
 * An easy approach to work with <code>ProgressListener</code>. It's more
 * advisable to extend this class than to implement
 * <code>ProgressListener</code> directly.
 * 
 * @author BurningXFlame@gmail.com
 * @since 0.2
 */
public abstract class AbstractProgressListener implements ProgressListener {
	protected int op = 0;
	protected String src = null;
	protected String dst = null;
	protected long total = 0;
	protected long progress = 0;

	private boolean isIdle = false;
	private boolean isCancelled = false;
	private volatile Object lock = new Object();
	private volatile Object idleLock = new Object();

	public abstract void init(int op, String src, String dest, long total);

	public abstract void progress(long delta);

	public abstract void complete();

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

	public void setIdle(boolean idle) {
		synchronized (idleLock) {
			isIdle = idle;
		}
	}

	public boolean isIdle() {
		synchronized (idleLock) {
			return isIdle;
		}
	}

	public void reset() {
		op = 0;
		src = null;
		dst = null;
		total = 0;
		progress = 0;
	}

	public abstract ProgressListener clone();

	public ProgressListener newInstance() {
		synchronized (idleLock) {
			if (isIdle) {
				this.reset();
				setIdle(false);
				return this;
			} else {
				return this.clone();
			}
		}
	}
}
