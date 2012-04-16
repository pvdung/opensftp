package net.sf.opensftp;

/**
 * <code>ProgressListener</code>s monitor progresses of put/get operations to
 * provide progress meter functionality.
 * 
 * @author BurningXFlame@gmail.com
 */
public interface ProgressListener extends Cloneable {
	/**
	 * Represents the put operation.
	 */
	public static final int PUT = 0;
	/**
	 * Represents the get operation.
	 */
	public static final int GET = 1;

	/**
	 * A callback method which will be called when a new transfer is about to be
	 * initiated.
	 * 
	 * @param op
	 *            the operation type. Valid values are {@link #PUT} and
	 *            {@link #GET}.
	 * @param src
	 *            the path of the source file
	 * @param dest
	 *            the destination path
	 * @param total
	 *            the size of the file measured with a certain unit.
	 */
	public void init(int op, String src, String dest, long total);

	/**
	 * A callback method which will be called when more data has been
	 * transferred.
	 * 
	 * @param delta
	 *            an increment, i.e. the newly transferred size, measured with
	 *            the same unit with the <code>total</code> param of
	 *            {@link #init(int, String, String, long)}.
	 */
	public void progress(long delta);

	/**
	 * A callback method which will be called when the transfer is completed.
	 */
	public void complete();

	/**
	 * Call this method to cancel the transfer which this
	 * <code>ProgressListener</code> is monitoring. <br>
	 * This method, together with {@link #isCancelled()}, enables you to cancel
	 * a transfer. To accomplish this function, the concrete implementation of
	 * {@link SftpUtil} should repeatedly call {@link #isCancelled()} during a
	 * transfer by a certain frequency to determine whether to continue the
	 * transfer or not.<br>
	 * NOTE: please make {@link #cancel()} and {@link #isCancelled()} be a pair
	 * of synchronized methods.
	 */
	public void cancel();

	/**
	 * Checks whether the transfer has been cancelled.<br>
	 * Please refer to method {@link #cancel()} for more details.
	 * 
	 * @return true if the transfer has been cancelled; false otherwise.
	 */
	public boolean isCancelled();

	/**
	 * Sets the status of this <code>ProgressListener</code>, idle or not.<br>
	 * Idle means ready for use.<br>
	 * NOTE:
	 * <ul>
	 * <li>
	 * Before using a <code>ProgressListener</code>, call
	 * <code>setIdle(false)</code> first.</li>
	 * <li>
	 * The method {@link #complete()} should call <code>setIdle(true)</code>
	 * at its very end.</li>
	 * <li>setIdle(true) should be called when the transfer which this
	 * <code>ProgressListener</code> is monitoring has been cancelled (not going
	 * to be cancelled).</li>
	 * </ul>
	 */
	public void setIdle(boolean idle);

	/**
	 * Checks whether this <code>ProgressListener</code> is idle or not.<br>
	 * Idle means ready for use.<br>
	 * 
	 * @return true if this <code>ProgressListener</code> is idle; false
	 *         otherwise.
	 */
	public boolean isIdle();

	/**
	 * Resets this <code>ProgressListener</code>.<br>
	 * This method should reset the operation type, the source path, the
	 * destination path, the total size of the file and the current progress.<br>
	 * Before resetting and reusing a <code>ProgressListener</code>, please make
	 * sure the transfer which this <code>ProgressListener</code> is monitoring
	 * has been completed or cancelled.
	 */
	public void reset();

	/**
	 * Creates and returns a clean copy of this <code>ProgressListener</code>.<br>
	 * NOTE: Don't clone the operation type, the source path, the destination
	 * path, the total size of the file and the current progress. Conversely,
	 * reset them.
	 * 
	 * @return a clean copy of this <code>ProgressListener</code>
	 */
	public ProgressListener clone();

	/**
	 * Returns an instance of <code>ProgressListener</code>.<br>
	 * The algorithm is as follows:<br>
	 * <ul>
	 * <li>Returns this object itself if it's idle. Reset it before return.</li>
	 * <li>Returns a clean copy of this object.</li>
	 * </ul>
	 * 
	 * To make this method work, make sure to implement the following methods
	 * correctly:<br>
	 * {@link #isIdle()}, {@link #setIdle(boolean)}, {@link #reset()},
	 * {@link #clone()}.<br>
	 * And make sure {@link #setIdle(boolean)} is called in any scenario where
	 * it should be.
	 * 
	 * @return a clean instance
	 * @see #isIdle()
	 * @see #setIdle(boolean)
	 * @see #reset()
	 * @see #clone()
	 */
	public ProgressListener newInstance();
}
