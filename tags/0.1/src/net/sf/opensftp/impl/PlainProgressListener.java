package net.sf.opensftp.impl;

import net.sf.opensftp.ProgressListener;

import org.apache.log4j.Logger;

/**
 * A plain implementation of
 * <code>net.sf.opensftp.impl.AbstractProgressListener</code>.
 * <code>PlainProgressListener</code> outputs file uploading or downloading
 * status in the format of plain text into the LoggingInterceptor log.
 * 
 * @author BurningXFlame
 * 
 */
public class PlainProgressListener extends AbstractProgressListener {
	// private static Logger log = Logger.getLogger(SftpUtil.class);
	private static Logger logger4LoggingInterceptor = Logger
			.getLogger(net.sf.opensftp.interceptor.LoggingInterceptor.class);

	private long total = 0;
	private long progress = 0;
	private String src = null;
	private String dst = null;
	private int op = 0;
	private long percent = 0;

	@Override
	public void complete() {
		String msg = "Completed!";
		// log.info(msg);
		logger4LoggingInterceptor.info(msg);
		clear();
	}

	private void clear() {
		total = 0;
		progress = 0;
		src = null;
		dst = null;
		op = 0;
		percent = 0;
	}

	@Override
	public void init(int op, String src, String dest, long total) {
		this.op = op;
		this.src = src;
		this.dst = dest;
		this.total = total;
		String opStr = (op == ProgressListener.GET) ? "Fetching" : "Uploading";
		String msg = String.format("%3$s %1$s to %2$s", this.src, this.dst,
				opStr);
		// log.info(msg);
		logger4LoggingInterceptor.info(msg);
	}

	@Override
	public void progress(long delta) {
		progress += delta;
		long tmp = progress * 100 / total;
		if (percent < tmp) {
			percent = tmp;
		}

		String msg = String.format("Completed %1$d ( %2$d%% ) out of %3$d.",
				progress, percent, total);
		// log.info(msg);
		logger4LoggingInterceptor.info(msg);
	}
}
