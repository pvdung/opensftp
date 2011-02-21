package net.sf.opensftp;

/**
 * <code>ProgressListener</code>s listen progresses of put/get operations to
 * provide progress meter functionality.
 * 
 * @author BurningXFlame@gmail.com
 */
public interface ProgressListener extends Cloneable {
	/**
	 * Represent the put operation.
	 */
	public static final int PUT = 0;
	/**
	 * Represent the get operation.
	 */
	public static final int GET = 1;

	/**
	 * Invoked when a new transfer is about to be initiated.
	 * 
	 * @param op
	 *            the operation type. Candidate values are {@link #PUT} and
	 *            {@link #GET}.
	 * @param src
	 *            the source file of the operation
	 * @param dest
	 *            the target file of the operation
	 * @param total
	 *            the size of the file measured with a certain unit.
	 */
	public void init(int op, String src, String dest, long total);

	/**
	 * Invoked when more data has been processed.
	 * 
	 * @param delta
	 *            an increment, i.e. the newly completed size, measured with the
	 *            same unit with the <code>total</code> param of the init
	 *            method.
	 */
	public void progress(long delta);

	/**
	 * Invoked when the transfer is complete.
	 */
	public void complete();

	/**
	 * Invoking this method to cancel the transfer. <br>
	 * This method, togethe with {@link #isCancelled()}, provides a
	 * functionality that a user can cancel a transfer. To accomplish this
	 * function, the concrete implementation of opensftp-api should invoke the
	 * <code>isCancelled()</code> method to determine whether to continue the
	 * transfer or not.<br>
	 * NOTE: please make <code>cancel()</code> and <code>isCancelled()</code> be
	 * a pair of synchronized methods.
	 */
	public void cancel();

	/**
	 * Check whether the user has cancelled the transfer.<br>
	 * Please refer to the description of the {@link #cancel()} method for more
	 * details.
	 * 
	 * @return true if the transfer has been cancelled; false otherwise.
	 */
	public boolean isCancelled();

	/**
	 * Set the status of this <code>ProgressListener</code>, idle or not.<br>
	 * Being idle means being ready for use.<br>
	 * NOTE:
	 * <ul>
	 * <li>
	 * Before using a <code>ProgressListener</code>, call setIdle(false) first.</li>
	 * <li>
	 * The complete() method should call setIdle(true) at its very end.</li>
	 * <li>setIdle(true) should be called when the transfer is actually
	 * cancelled (not going to be cancelled).</li>
	 * </ul>
	 * 
	 * @return true if the transfer has been cancelled; false otherwise.
	 */
	public void setIdle(boolean idle);

	/**
	 * Check whether this <code>ProgressListener</code> is idle or not.<br>
	 * Being idle means being ready for use.<br>
	 * 
	 * @return true if this <code>ProgressListener</code> is idle; false
	 *         otherwise.
	 */
	public boolean isIdle();

	/**
	 * Reset this <code>ProgressListener</code>.<br>
	 * This method should reset the operation type, the source file, the target
	 * file, the total size of the file and the current progress.<br>
	 * Before reusing a <code>ProgressListener</code>, please make sure it's
	 * completed or cancelled, and then call <code>reset()</code> on it.
	 */
	public void reset();

	/**
	 * Creates and returns a clean copy of this <code>ProgressListener</code>.<br>
	 * NOTE: Not to clone the operation type, the source file, the target file,
	 * the total size of the file and the current progress. Conversely, reset
	 * them.
	 * 
	 * @return a clean copy
	 */
	public ProgressListener clone();

	/**
	 * Returns an instance of <code>ProgressListener</code>.<br>
	 * The algorithm is as follows:<br>
	 * <ul>
	 * <li>Return this object itself if it's idle. Reset it before return.</li>
	 * <li>Return a clean clone of this object.</li>
	 * </ul>
	 * 
	 * To make this method work, make sure implement the following methods
	 * correctly:<br>
	 * <code>isIdle(), setIdle(boolean), reset(), clone()</code>.<br>
	 * And make sure <code>setIdle()</code> is called where it should be.
	 * 
	 * @return a clean instance
	 * @see {@link #isIdle()}, {@link #setIdle(boolean)}, {@link #reset()},
	 *      {@link #clone()}
	 */
	public ProgressListener newInstance();
}
