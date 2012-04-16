package net.sf.opensftp.prompter;

import javax.swing.JOptionPane;


/**
 * A Swing style of {@link Prompter}.
 * 
 * @author BurningXFlame@gmail.com
 * 
 */
public class SwingPrompter implements Prompter {

	public boolean promptYesNo(String str) {
		int foo = JOptionPane.showOptionDialog(null, str, "Warning",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
				null, null);
		return foo == JOptionPane.YES_OPTION;
	}

}
