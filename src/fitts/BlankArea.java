package fitts;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Point;
import java.awt.event.*;

/**
 * Class to control the workspace behavior - appearance of targets etc..
 * @author tzeentch
 *
 */

public class BlankArea extends JPanel {
	//Dimension minSize = new Dimension(500, 500);
	static final long serialVersionUID = 0;
    Ellipse2D.Double circle = null;
    static final int X_SIZE = 900;
    static final int Y_SIZE = 450;
    FittsMain root;

    /**
     * Creator of the workspace
     * @param m parent
     * @param color color of the orkspace (Color)
     */
    public BlankArea(FittsMain m, Color color) {
        setBackground(color);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));
        root = m;
        //circle = new Ellipse2D.Double(5, 5, 10, 10);
        
    }

    /**
     * focus traversing
     */
    public boolean isFocusTraversable() {
        return true;
      }
    /*
    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }
    
    public Dimension getMaximumSize() {
        return minSize;
    }*/
    
    /**
     * Paint a component to the workspace
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2d = (Graphics2D)g;
    	if(circle != null) g2d.draw(circle);
    }
    
    /**
     * Set parameters of a new target
     * @param x X coordinates
     * @param y Y coordinates
     * @param d diameter
     */
    public void setNewCircle(double x, double y, double d) {
    	circle = new Ellipse2D.Double(); 
    	circle.x = x;
    	circle.y = y;
    	circle.height = d;
    	circle.width = d;
    	//System.out.println(" x:"+x+"/"+(x+d)+" y:"+y+"/"+(y+d)+" d:"+d);
    }
    
    /**
     * Generate a new target for mouse clicking in the attempt according to the attempts parameters
     * @param attempt the attempt under which is the new target generated
     * @return target coordinates
     */
    public Point createNewMouseTarget(AttemptData attempt)
    {	int tx,ty,nd;
    	int att = NewAttemptDialog.generationType(attempt.type);
    	//create new target
    	
    	if(attempt.diameter < 10) nd = (int)(attempt.dGenerator.nextDouble()*40)+10;
    	else nd = attempt.diameter;
    	
    	tx = (int) (attempt.pGenerator.nextDouble()*(getWidth()-nd));
		ty = (int) (attempt.pGenerator.nextDouble()*(getHeight()-nd));
		
		if(att == NewAttemptDialog.RANDOM_2030)
		{	if(attempt.target<10) nd = 20; else nd = 30;
			
		}
		
		if(att == NewAttemptDialog.RETURN_TO_CENTER_FIXED || 
				att == NewAttemptDialog.RETURN_TO_CENTER_RANDOM )
		{	if(attempt.target % 2 == 0 ) { tx = X_SIZE/2; ty = Y_SIZE/2; }
			else
			{	double ang = attempt.pGenerator.nextDouble()*2*Math.PI;
				double dist = Y_SIZE/8;
				if(attempt.type == NewAttemptDialog.generationType[NewAttemptDialog.RETURN_TO_CENTER_RANDOM])
					dist = attempt.dGenerator.nextDouble()*Y_SIZE/8*0.9+Y_SIZE/8*0.1;
				tx = (int) (Math.cos(ang)*dist);
				ty = (int) (Math.sin(ang)*dist);
				tx +=X_SIZE/2;
				ty +=Y_SIZE/2;
			}
		}
		
		if(att == NewAttemptDialog.RETURN_TO_CENTER_RANDOM)
		{	if(attempt.target % 2 == 0 ) { tx = X_SIZE/2; ty = Y_SIZE/2; nd = attempt.diameter; }
			else
			{	double ang = attempt.pGenerator.nextDouble()*2*Math.PI;
				tx = (int) (Math.cos(ang)*Y_SIZE/8);
				ty = (int) (Math.sin(ang)*Y_SIZE/8);
				tx +=X_SIZE/2;
				ty +=Y_SIZE/2;
				nd = attempt.diameter;
			}
			
		}
		
		if(att == NewAttemptDialog.HORIZONTAL_DEFINED ||	
				att == NewAttemptDialog.HORIZONTAL_SHORT_LONG ||
				att == NewAttemptDialog.HORIZONTAL_THREE_STEP ||
				att == NewAttemptDialog.HORIZONTAL_FIVE_STEP ||
				att == NewAttemptDialog.HORIZONTAL_FIVE_STEP_UNADJUSTED ||
				att == NewAttemptDialog.HORIZONTAL_INCREASING ||
				att == NewAttemptDialog.HORIZONTAL_INCREASING_LONG ||
				att == NewAttemptDialog.HORIZONTAL_DECREASING ||
				att == NewAttemptDialog.HORIZONTAL_RANDOM)
		{	if(attempt.target % 2 == 0 ) { tx = X_SIZE/45; ty = Y_SIZE*5/16; nd = attempt.diameter; }
			else
			{	double dist = attempt.pGenerator.nextDouble()*X_SIZE/4+X_SIZE/8;
				if(att == NewAttemptDialog.HORIZONTAL_DEFINED || 
					(att == NewAttemptDialog.HORIZONTAL_SHORT_LONG 
					   && attempt.target < attempt.maxTargets/2) )
					dist = root.iniFile.coords[4].x;
				if(att == NewAttemptDialog.HORIZONTAL_SHORT_LONG 
					   && attempt.target >= attempt.maxTargets/2 )
					dist = X_SIZE*7/8;
				if(att == NewAttemptDialog.HORIZONTAL_THREE_STEP)
				{	if(attempt.target < attempt.maxTargets/3) dist=X_SIZE/8;
					else if(attempt.target < attempt.maxTargets*2/3) dist=X_SIZE/2;
						else dist = X_SIZE*7/8;
					
				}
				if(att == NewAttemptDialog.HORIZONTAL_FIVE_STEP || 
					att == NewAttemptDialog.HORIZONTAL_FIVE_STEP_UNADJUSTED)
				{	dist = X_SIZE*14/16;
					if(attempt.target < attempt.maxTargets*4/5) dist=X_SIZE*11/16;
					if(attempt.target < attempt.maxTargets*3/5) dist=X_SIZE*8/16;
					if(attempt.target < attempt.maxTargets*2/5) dist=X_SIZE*5/16;
					if(attempt.target < attempt.maxTargets*1/5) dist=X_SIZE*2/16;
				}
				if(att == NewAttemptDialog.HORIZONTAL_INCREASING) 
					dist = X_SIZE/8+X_SIZE*6/8*attempt.target/attempt.maxTargets;
				if(att == NewAttemptDialog.HORIZONTAL_INCREASING_LONG) 
					dist = X_SIZE*4/8+X_SIZE*3/8*attempt.target/attempt.maxTargets;
				if(att == NewAttemptDialog.HORIZONTAL_DECREASING) 
				{	//System.out.println(X_SIZE);
					dist = X_SIZE*7/8-X_SIZE*6/8*attempt.target/attempt.maxTargets;
				}
				ty = Y_SIZE*5/16;
				tx = (int) (X_SIZE/45+dist);
				nd = attempt.diameter;
			}
			
		}
		
		if(att == NewAttemptDialog.HORIZONTAL_SCALE)
		{	if(attempt.target<attempt.maxTargets/3) nd = 10;
			else if(attempt.target<attempt.maxTargets*2/3) nd = 20;
				else nd = 40;
			
			ty=Y_SIZE/2;
			if(attempt.target%2==0) { tx = X_SIZE/16; }
			else
			{	if(attempt.target<attempt.maxTargets/3) tx = X_SIZE*3/16;
				else if(attempt.target<attempt.maxTargets*2/3) tx = X_SIZE*5/16;
					else tx = X_SIZE*9/16;
			}
		}
		
		if(att == NewAttemptDialog.SERIES || 
				att == NewAttemptDialog.RANDOM ||
				att == NewAttemptDialog.RANDOM_2030 ||
				att == NewAttemptDialog.FIXED_DIST ||
				att == NewAttemptDialog.HORIZONTAL_FIVE_STEP_UNADJUSTED || 
				att == NewAttemptDialog.RETURN_TO_CENTER_FIXED || 
				att == NewAttemptDialog.RETURN_TO_CENTER_RANDOM) ty=ty+0;
		else ty+=root.profile.height_mod;
		//System.out.println(ty+" "+med.profile.height_mod);
		setNewCircle(tx, ty, nd);
		JLabel label = new JLabel(attempt.target+"");
		Dimension d = label.getMinimumSize();
		label.setBounds(tx+nd/2-d.width/2, ty+nd/2-d.height/2, d.width, d.height);
		
		
		removeAll();
	   	repaint();
	   	add(label);
	   	
	   	return new Point(tx,ty);
    	
    }
    
    /**
     * Generate a new target for button pressing (not only keyboard but also external buttons handled via keyboard)
     * in the attempt according to the attempts parameters
     * @param attempt the attempt under which is the new target generated
     * @return key to be pressed (KeyEvent key definitions)
     */
    public char createNewKeyTarget(AttemptData attempt)
	{	if(attempt.type == NewAttemptDialog.keyGenerationType[NewAttemptDialog.KEY_TWO])
		{	if(attempt.target%2 == 0) return KeyEvent.VK_F4;
			else return KeyEvent.VK_F8;
		}
		if(attempt.type == NewAttemptDialog.keyGenerationType[NewAttemptDialog.KEY_THREE])
		{	if(attempt.target%3 == 0) return KeyEvent.VK_F4;
			if(attempt.target%3 == 1) return KeyEvent.VK_F6;
			else return KeyEvent.VK_F8;
		}
		return KeyEvent.VK_F8;
		
	}
    
    /**
     * Attempt is finished, print a big DONE on the screen
     */
    public void done()
    {	removeAll();
    	JLabel d = new JLabel("done");
    	d.setFont(new Font("Serif",Font.PLAIN,320));
    	Dimension dim = d.getPreferredSize();
    	d.setBounds(X_SIZE/2-dim.width/2, Y_SIZE/2-dim.height/2,dim.width,dim.height);
    	//d.setBounds(100,100,50,50);
    	add(d);
    	repaint();
    }
    
    /**
     * 
     * @param attempt
     * @return
     */
    public Point createFirstMouseTarget(AttemptData attempt)
    {	removeAll();
    	JLabel jl=new JLabel("Click the circle to start!");
    	setNewCircle(getWidth()/3,getHeight()/3,40);
    	jl.setBounds(getWidth()/3+25,getHeight()/3+25,150,15);
    	//attempt.width=10;
    	add(jl);
    	repaint();
    	return new Point(5,8);
    }
    
    public char createFirstKeyTarget(AttemptData attempt)
    {	circle = null;
    	removeAll();
    	JLabel jl=new JLabel("Zaèni stiskem 'F8'!");
    	jl.setBounds(X_SIZE*5/16,Y_SIZE/2,X_SIZE*3/16,15);
    	add(jl);
    	jl = new JLabel("(check language settings)");
    	jl.setBounds(X_SIZE*5/16,Y_SIZE/2+20,X_SIZE*3/16,15);
    	add(jl);
    	jl = new JLabel(KeyEvent.getKeyText(KeyEvent.VK_F8));
    	jl.setFont(new Font("Serif",Font.PLAIN,36));
		add(jl);
		jl.setBounds(X_SIZE*9/16,Y_SIZE/2,60,60);
    	repaint();
    	return KeyEvent.VK_F8;
    }
    
    public Point createFirstLearnTarget(AttemptData attempt)
    {	removeAll();
    	
    	String s2 = "Úkol "+(root.Task+1)+" z "+root.MaxTask;
    	String s="Postupnì se objeví "+attempt.maxTargets+" terèù.\n" +
    			"Snaž se je zasáhnout co nejrychleji, a zapamatovat si je.\n" +
    			"Tato kombinace se bude opakovat. Opakování "+(root.Repetition+1)+"/"+root.MaxRepetitions+"\n" +
    			"Zaèni kliknutím na koleèko.";
    	//System.out.println(med.Task+" task");
    	JTextArea jt=new JTextArea(s);
    	jt.setBounds(getWidth()/3+50,getHeight()/3,400,70);
    	jt.setFont(new Font("Serif",Font.PLAIN,12));
    	JLabel jl = new JLabel(s2);
    	jl.setBounds(getWidth()/3+50,getHeight()/3-50,100,25);
    	jl.setFont(new Font("Serif",Font.BOLD,16));
		setNewCircle(getWidth()/3,getHeight()/3,40);
		//System.out.println(" heright "+getHeight()*2/3);
		
		add(jl);
		add(jt);
		repaint();
		return new Point(5,8);
    }
    
    public Point createNewLearnTarget(AttemptData attempt)
	{	int tx,ty,nd;
		nd = attempt.diameter;
    	tx = (int) (attempt.pGenerator.nextDouble()*(getWidth()-nd));
    	ty = (int) (attempt.pGenerator.nextDouble()*(getHeight()-nd));
		
		//System.out.println(ty+" "+med.profile.height_mod);
		setNewCircle(tx, ty, nd);
		JLabel label = new JLabel(attempt.target+"");
		Dimension d = label.getMinimumSize();
		label.setBounds(tx+nd/2-d.width/2, ty+nd/2-d.height/2, d.width, d.height);
		
		
		removeAll();
	   	repaint();
	   	add(label);
	   	return new Point(tx,ty);
	}
}
