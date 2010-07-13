package net.sf.opensftp;

/**
 * 
 * @author BurningXFlame
 * 
 */
public interface ProgressListener {
	/**
	 * Represent the put operation.
	 */
	public static final int PUT = 0;
	/**
	 * Represent the get operation.
	 */
	public static final int GET = 1;

	/**
	 * Notify the event that a new transfer is about to be initiated.
	 * 
	 * @param op
	 *            the operation type. Candidate values are {@link #PUT} and
	 *            {@link #GET}.
	 * @param src
	 *            the source file for the operation
	 * @param dest
	 *            the target file for the operation
	 * @param total
	 *            the size of the file measured with a certain unit.
	 */
	public void init(int op, String src, String dest, long total);

	/**
	 * Notify the event that more data has been processed.
	 * 
	 * @param delta
	 *            an increment, i.e. the newly completed size, measured with the
	 *            same unit with the <code>total</code> param of the init
	 *            method.
	 */
	public void progress(long delta);

	/**
	 * Notify the event that the transfer has been completed.
	 */
	public void complete();

	/**
	 * Invoking this method to cancel the transfer. <br>
	 * This method, togethe with {@link #isCancelled()}, provides a
	 * functionality that a user can cancel a transfer. To accomplish this
	 * function, the concrete implementation of opensftp-api should invoke the
	 * <code>isCancelled()</code> method to determine whether continue the
	 * transfer or not.
	 */
	public void cancel();

	/**
	 * Check whether the transfer has been cancelled.<br>
	 * Please refer to the description of the {@link #cancel()} method for more
	 * details.
	 * 
	 * @return true if the transfer has been cancelled; false otherwise.
	 */
	public boolean isCancelled();
}
