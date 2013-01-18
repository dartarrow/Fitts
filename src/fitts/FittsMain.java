package fitts;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.text.NumberFormat;
import java.io.*;
import javax.swing.JOptionPane;


public class FittsMain extends JPanel {
	static final long serialVersionUID = 0;
	static final String NEWLINE = System.getProperty("line.separator");
    static final int RANDOM = 0;
    static final int PRESET = 1;
    static JFrame frame;
    private AttemptData attempt = null;
	private static JMenuBar menuBar;
	
	int Repetition = 0;
	int MaxRepetitions = 0;
	int Task = 0;
	int MaxTask = 0;
	int[] Order;
	
	IniFile iniFile = new IniFile();
	UserProfile profile;
	BlankArea blankArea;
    JTextArea textArea;
    
    NumberFormat nf = NumberFormat.getInstance();
    
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame();
        frame.setTitle("Fitt's law");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        //Create and set up the content pane.
        FittsMain newContentPane = new FittsMain();
        menuBar = newContentPane.createMenuBar();
        frame.setJMenuBar(menuBar);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        System.out.println(frame.getWidth()+" "+frame.getHeight());
        frame.setVisible(true);
        frame.setResizable(false);
        
        try
        {	newContentPane.iniFile.load();
			if(newContentPane.iniFile.id!=null) 
			{	UserProfile n = new UserProfile();
				n.loadProfile(frame, newContentPane.iniFile.id);
				newContentPane.setProfile(n);
			}
        }
        catch(IOException e)
        {	JOptionPane.showMessageDialog(frame, "Error loading ini file!");
        	
        }
        
    }
    
    public FittsMain() {
    	super(new SpringLayout());
    	SpringLayout sl = (SpringLayout) this.getLayout();
    	
        blankArea = new BlankArea(this, Color.YELLOW);
        blankArea.setMinimumSize(new Dimension(900,650));
        blankArea.setPreferredSize(new Dimension(900,650));
        blankArea.setMaximumSize(new Dimension(900,650));
        add(blankArea);
        
        JFrame tFrame = new JFrame();
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setMinimumSize(new Dimension(400, 150));
        scrollPane.setPreferredSize(new Dimension(400, 650));
        scrollPane.setMaximumSize(new Dimension(400,650));
        tFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        tFrame.add(scrollPane);
        tFrame.pack();
        tFrame.setVisible(true);
        tFrame.setTitle("Fitts' text area");
        sl.putConstraint(SpringLayout.WEST, blankArea, 9, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, blankArea, 9, SpringLayout.NORTH, this);
        //sl.putConstraint(SpringLayout.WEST, scrollPane, 9, SpringLayout.EAST, blankArea);
        //sl.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, blankArea);
        sl.putConstraint(SpringLayout.EAST, this, 9, SpringLayout.EAST, blankArea);
        sl.putConstraint(SpringLayout.SOUTH, this, 9, SpringLayout.SOUTH, blankArea);
        
        //Initialize movement storage
        //newAttempt();
        
        //Register for mouse events on blankArea and the panel.
        MouseListener fml = new MouseListener(this);
        blankArea.addMouseListener(fml);
        blankArea.addMouseMotionListener(fml);
        KeyListener fkl = new KeyListener(this);
        blankArea.addKeyListener(fkl);
        blankArea.setFocusTraversalPolicyProvider(true);
        //addMouseListener(this);
        //setMinimumSize(new Dimension(506, 659));
        //setPreferredSize(new Dimension(506, 659));
        //setMaximumSize(new Dimension(506, 659));
        //setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        nf.setMinimumFractionDigits(3);
        nf.setMaximumFractionDigits(3);
        
        
    }
    
    public void newProfile()
    {	new NewProfileDialog(this);
    	    	
    }
          
    /**
     * Resets the current attempt and resets all variables.
     */
    public void resetAttempt()
    {	
	attempt = new AttemptData();
	if(menuBar != null)
		menuBar.getMenu(0).getMenuComponent(4).setEnabled(false);
    	
    }
    
    /**
     * open a window to select a UserProfile file to load
     */
    public void loadProfile()
    {	UserProfile n = new UserProfile();
    	n.loadProfile(frame);
    	setProfile(n);
    }
    
    /**
     * Save current UserProfile
     */
    public void saveProfile()
    {	profile.saveProfile();
    }
    
    /**
     * Get the current UserProfile
     * @return profile
     */
    public UserProfile getProfile()
    {	return profile;
    }
    
    /**
     * open the NewAttemptDialog
     */
    public void newAttempt()
    {	new NewAttemptDialog(this);
    }
    
    /**
     * Set the attempt data to the current session
     */
    public void setAttempt(AttemptData n)
    {	attempt = n;
    	
    }
    
    /**
     * Save the attempt
     */
    public void saveAttempt(AttemptData n)
    {	attempt.saveAttempt();
    	
    }
    
    /**
     * Clears the text area. 
     */
    public void clearTextArea()
    {	textArea.setText(null);
    }
    
        
    /**
     * Creates and fills the menu bar
     * @return menuBar
     */
    private JMenuBar createMenuBar() {
    	menuBar = new JMenuBar();
    	JMenuItem menuItem;
    	
    	JMenu menu = new JMenu("File");
    	Action[] actions = {new MenuAction("New Profile",
								new Runnable() { public void run(){ newProfile(); } }),
							new MenuAction("Load Profile",
								new Runnable() { public void run(){ loadProfile(); } }),
    						new MenuAction("New Attempt",
    							new Runnable() { public void run(){ newAttempt(); } }),
    						new MenuAction("Clear Text Area",
    							new Runnable() { public void run(){ clearTextArea(); } }),
    	};
    	for(int i=0; i<actions.length; i++) {
    		menuItem = new JMenuItem(actions[i]);
    		menuItem.setIcon(null);
    		menu.add(menuItem);
    	}
    	menuBar.add(menu);
    	return menuBar;
    }
    
    /**
     * Prints to the textArea
     * @param eventDescription message to be printed 
     * 
     */
   void eventOutput(String eventDescription) {
        /*textArea.append(eventDescription + " detected on "
                + e.getComponent().getClass().getName()
                + "." + NEWLINE);*/
    	textArea.append(eventDescription + "." + NEWLINE);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    

   /**
    * Finalizes an Attempt. Logs the statistics, outputs the result to the textArea
    * saves information in current profile and saves the attempt data. Updates the
    * repetition number and if its not the final task, begins a new attempt
    * 
    */
    public void finishAttempt()
    {	//Point2D ab = attempt.countAB();
    	//Point2D rab = attempt.refineCountAB();
    	
    	/*for(int i=0;i<attempt.maxTargets;i++)
    		if(attempt.outliers[i]) outls+=i+",";
    	eventOutput(" **** Summary **** " + NEWLINE
        	+ " Length :" + NEWLINE 
        	+ nf.format(attempt.cum_length) + "(tot) distance" + NEWLINE
        	+ nf.format(attempt.cum_length/attempt.maxTargets) + "(avg) distance" + NEWLINE 
        	+ nf.format(attempt.cum_travel) + "(tot) travel" + NEWLINE
        	+ nf.format(attempt.cum_travel/attempt.maxTargets) + "(avg) travel" + NEWLINE
        	+ " Time : " + NEWLINE
        	+ nf.format(attempt.cum_time) +"(tot) " + NEWLINE
        	+ nf.format(attempt.cum_time/attempt.maxTargets) + "(avg) " + NEWLINE 
        	+ nf.format(attempt.cum_time/attempt.cum_length) +"(tpp dist) " + NEWLINE
        	+ nf.format(attempt.cum_time/attempt.cum_travel) +"(tpp trav) " + NEWLINE
        	+ "A :"+ab.getX()+" B :"+ab.getY()+" "+attempt.maxTargets+ NEWLINE
        	+ "refined A :"+rab.getX()+" B :"+rab.getY()+" outliers "+ outls, e);
        	*/
    	blankArea.done();
    	attempt.logStatistics();
    	eventOutput(attempt.log);
        profile.attempt++;
        profile.saveProfile();
        attempt.saveAttempt();
        if(iniFile.inType==AttemptData.LEARN)
        {	Repetition++;
        	if(Repetition >= MaxRepetitions)
        	{	Task++;
        	    Repetition = 0;
        	}
        	//System.out.println(Order[Task]);
        	if(Task<MaxTask)
        	{	setAttempt(new AttemptData(getProfile().id,
        				getProfile().attempt+1,
        				iniFile.inType,
        				"none",
        				blankArea.getWidth(),blankArea.getHeight(),
        				Order[Task],
        				"learn", 
        				30,
        				iniFile.coords
        		));
        		blankArea.createFirstLearnTarget(getAttempt());
        		blankArea.setFocusTraversalPolicyProvider(true);
        	}
        }
    	
    }
    
    /**
     * Sets the current user profile 
     * 
     */
        public void setProfile(UserProfile u)
    {	profile = u;
    	iniFile.id = profile.id;
    	frame.setTitle("Fitt's law - "+u.name+" "+u.surname);
    	
    	
    }
    
    /**
     * Returns the current attempt
     * @return attempt 
     * 
     */
    public AttemptData getAttempt()
    {	if(attempt == null) return null;
    	return attempt;
    	
    }
    
    /**
     * ???
     * @return ret 
     * 
     */
    int[] createOrder(int min,int max, int seed)
    {	int sel = max-min+1;
    	int m;
    	double [] h = new double[sel];
    	int [] ret = new int[sel];
    	Random rnd = new Random(seed);
    	for(int i=0; i<max-min+1; i++)
    	{	h[i]=rnd.nextDouble();
    	}
    	for(int i=0;i<sel; i++)
       	{	m = 0;
    		for(int j=0;j<sel; j++)
    		{	if(h[j]>h[m]) 
    			{	m=j;
    			}
    		}
    		h[m]=0;
    		ret[i]=m+min;
    		System.out.print(" "+ret[i]);
    	}
      	
    	return ret;
    }
    
}

/**
 * class to filter .fp files
 * 
 */
class pfFilter extends javax.swing.filechooser.FileFilter
{	 
/**
 * Filters user profile (.fp) files
 * @return true for directories and .fp files, false otherwise 
 * 
 */
	public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".fp");
    }

/**
 * Return the description of the file filter
 * @return "Fitts profile (.fp) files"
 * 
 */	
    public String getDescription() {
        return "Fitts profile (.fp) files";
    }
}



