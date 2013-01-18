package fitts;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Menu action handling class
 * @author tzeentch
 *
 */
public class MenuAction extends AbstractAction {
	static final long serialVersionUID = 0;
	private Runnable runnable;
	
	/**
	 * Create a new menu command
	 * @param s command name
	 * @param r function to be executed
	 */
	public MenuAction(String s, Runnable r) {
		super(s,null);
		runnable = r;
	}
	
	/**
	 * Runs the runnable function
	 * @param e action performed (unused)
	 */
	public void actionPerformed(ActionEvent e) {
		runnable.run();

	}
	
		
}
