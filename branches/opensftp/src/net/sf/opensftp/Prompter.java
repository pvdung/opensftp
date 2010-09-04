package net.sf.opensftp;

/**
 * <code>Prompter</code> is used to retrieve the user's choice when he must make
 * one. It's a classic scenario that when the StrictHostKeyChecking property
 * values 'ask', and the user attempts to connect to a host which has not been
 * registered in the known_hosts file, the user must decide whether to continue
 * or not.
 * 
 * @author BurningXFlame
 */
public interface Prompter {

	/**
	 * Retrieve the user's decision.
	 * 
	 * @param str
	 *            The prompting message.
	 * @return The user's choice. True if yes, false otherwise.
	 */
	public boolean promptYesNo(String str);
}
