package fitts;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

public class MyMenuAction extends AbstractAction {
	private Runnable runnable;
	
	public MyMenuAction(String s, Runnable r) {
		super(s,null);
		runnable = r;
	}
	
	public void actionPerformed(ActionEvent e) {
		runnable.run();

	}
	
		
}
