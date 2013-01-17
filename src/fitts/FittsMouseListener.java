package fitts;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.geom.Point2D;

public class FittsMouseListener extends Object implements MouseListener, MouseMotionListener {

	MouseEventDemo med;
	
	public FittsMouseListener(MouseEventDemo m)
	{	med = m;
		
	}
	
	public void mouseReleased(MouseEvent e) {
	      /*  eventOutput("Mouse released (# of clicks: "
	                + e.getClickCount() + ")", e);*/
	    }
	    
	    /**
	     * Define actions on mouse press.
	     */
		public void mousePressed(MouseEvent e) 
	    {  	AttemptData attempt = med.getAttempt();
			BlankArea blankArea = med.blankArea;
			
			//System.out.println("mouse");
			if(attempt==null || attempt.finished()) return;
			if(attempt.inType == AttemptData.KEY) return;
			
			//System.out.println("mouse2");
	    	
	    	Point m,n;
	        int s = (int)blankArea.circle.width;
	        n = new Point((int)blankArea.circle.x+s/2,(int)blankArea.circle.y+s/2);
	        m = new Point((int)e.getX(),(int)e.getY());
	        //System.out.println("mouse3");
	        if( m.distance(n) <= s/2 ) 
	        {// hit the target
	        	//System.out.println("mouse4");
	        	attempt.addHit(m,n,s);
	        	//System.out.println("mouse5");
	        	med.eventOutput(attempt.dumpInfo(attempt.target-1));
	        	if(!attempt.finished()) 
	        	{	
	        		if(attempt.inType == AttemptData.MOUSE)
	        			n = blankArea.createNewMouseTarget(attempt);
	        		if(attempt.inType == AttemptData.LEARN)
	        			n = blankArea.createNewLearnTarget(attempt);
	        	
	        	}
	        	else med.finishAttempt();
	        	
	        }
	        else 
	        {	attempt.addMiss(m,n);
	        	med.eventOutput("Miss !"+m.x+" "+m.y);
	        }
	        
	    }
	    
	    public void mouseMoved(MouseEvent e) {
	    	AttemptData attempt = med.getAttempt();
				    	
	    	if(attempt != null && attempt.target > -1 && attempt.target < attempt.maxTargets) 
	    		attempt.addMove(new Point(e.getX(),e.getY()));
	    	//eventOutput(count + "a",e);
	    	    	
	      }
	    /**
	     * Counts length of travel between two target i and i-1.
	     * @param i target to which the length is counted.
	     * @return length of the travel in pixels.
	     */
	       
	    public void mouseDragged(MouseEvent e) {
	    }
	    public void mouseEntered(MouseEvent e) {
	    }
	    public void mouseExited(MouseEvent e) {
	    }
	    public void mouseClicked(MouseEvent e) {
	    }

}
