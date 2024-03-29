package net.sf.opensftp.prompter;

/**
 * <code>Prompter</code> is used to retrieve the user's choice when he must make
 * one. A typical scenario is when the StrictHostKeyChecking property values
 * 'ask', and the user attempts to connect to a host which has not been
 * registered in the known_hosts file, the user must decide whether to continue
 * or not.
 * 
 * @author BurningXFlame@gmail.com
 */
public interface Prompter {

	/**
	 * Retrieve the user's choice.
	 * 
	 * @param str
	 *            The prompt message.
	 * @return The user's choice. True if yes, false otherwise.
	 */
	public boolean promptYesNo(String str);
}
