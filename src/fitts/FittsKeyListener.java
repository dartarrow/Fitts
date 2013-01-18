package fitts;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class FittsKeyListener extends Object implements KeyListener {

	MouseEventDemo med;
	
	public FittsKeyListener(MouseEventDemo m)
	{	med = m;
		
	}
	
	public void keyTyped(KeyEvent e) {
	  //displayInfo(e, "KEY TYPED: ");
	}

	/** Handle the key pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		AttemptData attempt = med.getAttempt();
		BlankArea blankArea = med.blankArea;
		Point m=new Point(),n = new Point();
		int s=0;
		
		System.out.println("key :"+e.getKeyCode()+" "+KeyEvent.getKeyText(e.getKeyCode()));
		
		if(attempt==null || attempt.finished()) return;
		System.out.println(" tgt :"+attempt.targetCharCode);
		if(attempt.inType == AttemptData.MOUSE) return;
		
		
		if(attempt.targetCharCode == KeyEvent.VK_F4)
		{ m=med.iniFile.coords[0]; n=m; s=1; }
		if(attempt.targetCharCode == KeyEvent.VK_F5)
		{ m=med.iniFile.coords[1]; s=1; }
		if(attempt.targetCharCode == KeyEvent.VK_F6)
		{ m=med.iniFile.coords[2]; s=1; }
		if(attempt.targetCharCode == KeyEvent.VK_F7)
		{ m=med.iniFile.coords[3]; s=1; }
		if(attempt.targetCharCode == KeyEvent.VK_F8)
		{ m=med.iniFile.coords[4]; s=1; }
		
		
		if(e.getKeyCode() == attempt.targetCharCode)
		{	attempt.addHit(m,n,s);
    		med.eventOutput(attempt.dumpInfo(attempt.target-1));
    		if(!attempt.finished()) 
    		{	int cr = blankArea.createNewKeyTarget(attempt);
    			BlankArea bla = med.blankArea;
    			JLabel jl=new JLabel(KeyEvent.getKeyText(cr));
    			Rectangle rect=new Rectangle(50,400,60,60);
    			med.getAttempt().targetCharCode = cr;
    			bla.removeAll();
    			bla.repaint();
    			if(cr==KeyEvent.VK_F4) rect.x=50;
    			if(cr==KeyEvent.VK_F8) rect.x=450;
    			if(cr==KeyEvent.VK_F6) rect.x=250;
    			jl.setFont(new Font("Serif",Font.PLAIN,36));
    			med.blankArea.add(jl);
    			jl.setBounds(rect);
    			
    		}
    		else med.finishAttempt();
    		
    		
    		System.out.println("targetChar:" +KeyEvent.getKeyText(med.getAttempt().targetCharCode)+"\n");
		}
		else 
		{	attempt.addMiss(m,n);
			med.eventOutput("Miss! "+m+"/"+n);
		}
			
		//displayInfo(e, "KEY PRESSED: ");
	
	}

	/** Handle the key released event from the text field. */
	public void keyReleased(KeyEvent e) {
	  //displayInfo(e, "KEY RELEASED: ");
	}
	
	protected void displayInfo(KeyEvent e, String s) {
	    String keyString, modString, tmpString, actionString, locationString;

	    //You should only rely on the key char if the event
	    //is a key typed event.
	    int id = e.getID();
	    if (id == KeyEvent.KEY_TYPED) {
	      char c = e.getKeyChar();
	      keyString = "key character = '" + c + "'";
	    } else {
	      int keyCode = e.getKeyCode();
	      keyString = "key code = " + keyCode + " ("
	          + KeyEvent.getKeyText(keyCode) + ")";
	    }

	    int modifiers = e.getModifiersEx();
	    modString = "modifiers = " + modifiers;
	    tmpString = KeyEvent.getModifiersExText(modifiers);
	    if (tmpString.length() > 0) {
	      modString += " (" + tmpString + ")";
	    } else {
	      modString += " (no modifiers)";
	    }

	    actionString = "action key? ";
	    if (e.isActionKey()) {
	      actionString += "YES";
	    } else {
	      actionString += "NO";
	    }

	    locationString = "key location: ";
	    int location = e.getKeyLocation();
	    if (location == KeyEvent.KEY_LOCATION_STANDARD) {
	      locationString += "standard";
	    } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
	      locationString += "left";
	    } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
	      locationString += "right";
	    } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
	      locationString += "numpad";
	    } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
	      locationString += "unknown";
	    }

	    /*med.textArea.append(s + "\n    " + keyString + "\n    "
	        + modString + "\n    " + actionString 
	        + "\n    " + locationString +"\n");*/
	    med.textArea.append(s+" "+keyString);
	    med.textArea.setCaretPosition(med.textArea.getDocument().getLength());
	  }
}
