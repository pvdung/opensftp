package net.sf.opensftp.impl;

import javax.swing.JOptionPane;

import net.sf.opensftp.Prompter;

/**
 * A Swing version of {@link Prompter}.
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
