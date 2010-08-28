package net.sf.opensftp.impl;

import org.apache.log4j.Logger;

public class PlainProgressListener extends AbstractProgressListener {
	private static Logger log = Logger.getLogger(SftpUtil.class);
	private static Logger logger4LoggingInterceptor = Logger
			.getLogger(net.sf.opensftp.interceptor.LoggingInterceptor.class);

	private long total = 0;
	private long progress = 0;
	private String src;
	private String dst;
	private int op;
	private long percent = 0;

	@Override
	public void complete() {
		String msg = "Completed!";
		log.info(msg);
		logger4LoggingInterceptor.info(msg);
	}

	@Override
	public void init(int op, String src, String dest, long total) {
		this.op = op;
		this.src = src;
		this.dst = dst;
		this.total = total;
		String msg = String.format("Fetching %1$ to %2$", src, dst);
		log.info(msg);
		logger4LoggingInterceptor.info(msg);
	}

	@Override
	public void progress(long delta) {
		progress += delta;
		long tmp = progress * 100 / total;
		if (percent < tmp) {
			percent = tmp;
		}

		String msg = String.format("Completed %1$d (%2$d%) out of %3$d",
				progress, percent, total);
		log.info(msg);
		logger4LoggingInterceptor.info(msg);
	}
}
