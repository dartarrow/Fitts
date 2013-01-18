package fitts;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.awt.Point;
import javax.swing.*;

/**
 * Class to handle dialog to create a new attempt for current user
 * @author tzeentch
 *
 */
public class NewAttemptDialog extends JDialog implements ActionListener, FocusListener {
	// possible attempt parameters
	// TODO move to an external configuration file
	static final String[] weightTypes = { "none","0.5", "1", "1.5" ,"2","3" };
	static final String[] targetsLabels = {"3","4","5","6","7","8","9","10","20","30","50","60","100","120","200","300","500"};
	static final String[] diamLabels = {"variable","random","10","20","30","40","50"};
	static final String[] generationType = {"series","random","random - 20 & 30","fixed distance",	
	"horizontal defined",
	"horizontal scale",
	"horizontal short and long",
	"horizontal three step","horizontal five step","horizontal five step unadjusted",
	"horizontal increasing","horizontal increasing long","horizontal decreasing","horizontal variable",
	"return to center - random distance","return to center - fixed distance"};
	static final long serialVersionUID = 0;
	static final String[] keyGenerationType = {"two targets","three targets","five targets"};
	static final int SERIES = 0;
	static final int RANDOM = 1;
	static final int RANDOM_2030 = 2;
	static final int FIXED_DIST = 3;
	static final int HORIZONTAL_DEFINED = 4;
	static final int HORIZONTAL_SCALE = 5;
	static final int HORIZONTAL_SHORT_LONG = 6;
	static final int HORIZONTAL_THREE_STEP = 7;
	static final int HORIZONTAL_FIVE_STEP = 8;
	static final int HORIZONTAL_FIVE_STEP_UNADJUSTED = 9;
	static final int HORIZONTAL_INCREASING = 10;
	static final int HORIZONTAL_INCREASING_LONG = 11;
	static final int HORIZONTAL_DECREASING = 12;
	static final int HORIZONTAL_RANDOM = 13;
	static final int RETURN_TO_CENTER_RANDOM = 14;
	static final int RETURN_TO_CENTER_FIXED = 15;
	static final int KEY_TWO = 0;
	static final int KEY_THREE = 1;
	static final int KEY_FIVE = 2;
	int focX,focY = 0;
	int[] numberTargets = {3,4,5,6,7,8,9,10,20,30,50,60,100,120,200,300,500};
	int[] diameters = {0,1,10,20,30,40,50};
	// end of attempt parameters
	
	JPanel fields = new JPanel();
	FittsMain root;
	JTextField[] tfields;
	JTextField tprof;
	JTextField distanceFld;
	JComboBox<String> weightList;
	JComboBox<String> targetList;
	JComboBox<String> typeList;
	JComboBox<String> diamList;
	JComboBox<String> keyTypeList;
	JTextField[][] coords = new JTextField[5][2];
	int inType;
	
	/**
	 * Creates and opens a new attempt dialog
	 * @param m parent
	 */
	public NewAttemptDialog(FittsMain m)
	{	root = m;
		inType = root.iniFile.inType;
		createContent();
		setResizable(false);
		setEnabled(true);
		setVisible(true);
	}
	
	/**
	 * Setup the dialog layout
	 */
	private void createContent()
	{	this.getContentPane().removeAll();
		//JPanel fields = new JPanel();
		JPanel profile = new JPanel();
		JPanel buttons = new JPanel();
		JPanel atFields = new JPanel();
		JPanel typebuttons = new JPanel();
		setAlwaysOnTop(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		tprof = new JTextField();
		tprof.setText(root.getProfile().id);
		tprof.setEditable(false);
		tprof.setSize(new Dimension(100,15));
		JButton bprof = new JButton("Change");
		bprof.setActionCommand("change");
		bprof.addActionListener(this);
		profile.add(new JLabel("profile"));
		profile.add(tprof);
		profile.add(bprof);
		profile.setMaximumSize(new Dimension(200,65));
	    profile.setMinimumSize(new Dimension(200,65));
	    profile.setPreferredSize(new Dimension(200,65));
		
		JRadioButton mouse = new JRadioButton("Mouse");
		mouse.setActionCommand("mouse");
	    //mouse.setSelected(true);
	    mouse.addActionListener(this);
	    JRadioButton key = new JRadioButton("Key");
		key.setActionCommand("key");
		key.addActionListener(this);
		JRadioButton learn = new JRadioButton("Learning");
		learn.setActionCommand("learn");
		learn.addActionListener(this);
	    ButtonGroup group = new ButtonGroup();
	    group.add(mouse);
	    group.add(key);
	    group.add(learn);
	    switch(inType)
	    { 	case AttemptData.MOUSE:	mouse.setSelected(true); break;
	    	case AttemptData.KEY: key.setSelected(true); break;
	    	case AttemptData.LEARN: learn.setSelected(true); break;
	    
	    }
	    typebuttons.add(mouse);
	    typebuttons.add(key);
	    typebuttons.add(learn);
	    typebuttons.setMaximumSize(new Dimension(200,35));
	    typebuttons.setMinimumSize(new Dimension(200,35));
	    profile.add(typebuttons);
	    
		String[] l = {"number","x_size","y_size"};
		JLabel[] labels = new JLabel[l.length];
		tfields = new JTextField[l.length];
		for(int i=0; i<l.length; i++)
		{ 	labels[i] = new JLabel(l[i]);
			tfields[i] = new JTextField();
			tfields[i].setSize(new Dimension(180,15));
			atFields.add(labels[i]);
			atFields.add(tfields[i]);
			tfields[i].setEditable(false);
		}
		tfields[0].setText(""+(root.getProfile().attempt+1));
		tfields[1].setText(""+root.blankArea.getWidth());
		tfields[2].setText(""+root.blankArea.getHeight());
		atFields.setLayout(new GridLayout(0,2));
		atFields.setMinimumSize(new Dimension(280,60));
		atFields.setMaximumSize(new Dimension(280,60));
		
		JButton bCreate,bCancel;
		bCreate = new JButton();
		bCreate.setText("Start");
		bCreate.setActionCommand("start");
		bCreate.addActionListener(this);
		
		bCancel = new JButton();
		bCancel.setText("Cancel");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);
		
		buttons.add(bCreate);
		buttons.add(bCancel);
		buttons.setMinimumSize(new Dimension(280,40));
		buttons.setMaximumSize(new Dimension(280,40));
		
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS ));
		JLabel label = new JLabel("New Attempt");
		label.setAlignmentX(RIGHT_ALIGNMENT);
		add(label);
		add(profile);
		//add(typebuttons);
		//add(new JSeparator());
		add(atFields);
		switch(inType)
	    { 	case AttemptData.MOUSE:	add(createMouseFields()); break;
	    	case AttemptData.KEY: add(createKeyFields()); break;
	    	case AttemptData.LEARN: add(createLearnFields()); break;
	    
	    }
		add(buttons);
		
		
		//this.setSize(120, 120);
		
		setBounds(new Rectangle(200,200,400,402));
		//this.getContentPane().setMinimumSize(new Dimension(200,200));
		//this.getContentPane().setMaximumSize(new Dimension(200,200));
	}
	/**
	 * Create parameter selection fields for mouse attempts
	 * @return JPanel with mouse fields
	 */
	private JPanel createMouseFields()
	{	JPanel fl = new JPanel();
		JPanel fields = new JPanel();
		JPanel f2 = new JPanel();
		fields.add(new JLabel("weights"));
		weightList = new JComboBox<String>(weightTypes);
		weightList.setSelectedIndex(root.iniFile.weightList);
		fields.add(weightList);
		fields.add(new JLabel("targets"));
		targetList = new JComboBox<String>(targetsLabels);
		targetList.setSelectedIndex(root.iniFile.targetList);
		fields.add(targetList);
		fields.add(new JLabel("diameter"));
		diamList = new JComboBox<String>(diamLabels);
		diamList.setSelectedIndex(root.iniFile.diamList);
		fields.add(diamList);
		fields.add(new JLabel("distance"));
		distanceFld = new JTextField();
		distanceFld.setText(""+root.iniFile.coords[4].x);
		fields.add(distanceFld);
		
		fields.setLayout(new GridLayout(0,2));
		fields.setMaximumSize(new Dimension(280,60));
		fields.setMinimumSize(new Dimension(280,60));
		
		f2.setLayout(new BoxLayout(f2,BoxLayout.LINE_AXIS));
		f2.add(new JLabel("generation"));
		typeList = new JComboBox<String>(generationType);
		typeList.setSelectedIndex(root.iniFile.typeList);
		f2.add(typeList);
		f2.setMaximumSize(new Dimension(280,20));
		f2.setMinimumSize(new Dimension(280,20));
		//fields.add(f2);
		
		fl.setLayout(new BoxLayout(fl,BoxLayout.PAGE_AXIS));
		fl.add(fields);
		fl.add(f2);
		fl.setMaximumSize(new Dimension(380,120));
		fl.setMinimumSize(new Dimension(380,120));
		return fl;
	}
	
	/**
	 * Create parameter selection fields for learn attempts
	 * @return JPanel with learn fields
	 */
	private JPanel createLearnFields()
	{	JPanel f1 = new JPanel();
		f1.setLayout(new BoxLayout(f1,BoxLayout.PAGE_AXIS));
		JPanel fields = new JPanel();
		fields.setLayout(new GridLayout(2,0));
		JPanel f2 = new JPanel();
		f2.setLayout(new BoxLayout(f2,BoxLayout.LINE_AXIS));
		
		for(int i=0; i<2; i++)
		{	coords[i][0] = new JTextField();
			coords[i][0].setText(""+root.iniFile.coords[i].x);
			coords[i][1] = new JTextField();
			coords[i][1].setText(""+root.iniFile.coords[i].y);
		}
		fields.add(new JLabel("Seed"));
		fields.add(coords[0][0]);
		fields.add(new JLabel("NoAttempts"));
		fields.add(coords[0][1]);
		fields.setMaximumSize(new Dimension(280,40));
		fields.setMinimumSize(new Dimension(280,40));
		
		f2.add(new JLabel("NoTargets from "));
		f2.add(coords[1][0]);
		f2.add(new JLabel("to "));
		f2.add(coords[1][1]);
		f2.setMaximumSize(new Dimension(280,20));
		f2.setMinimumSize(new Dimension(280,20));
		
		f1.add(fields);
		f1.add(f2);
		f1.setMaximumSize(new Dimension(280,60));
		f1.setMinimumSize(new Dimension(280,60));
		return f1;
	}
	
	/**
	 * Create parameter selection fields for key attempts
	 * @return JPanel with key fields
	 */
	private JPanel createKeyFields()
	{	JPanel fields = new JPanel();
		fields.add(new JLabel("weights"));
		weightList = new JComboBox<String>(weightTypes);
		weightList.setSelectedIndex(root.iniFile.weightList);
		fields.add(weightList);
		fields.add(new JLabel("targets"));
		targetList = new JComboBox<String>(targetsLabels);
		targetList.setSelectedIndex(root.iniFile.targetList);
		fields.add(targetList);
		fields.add(new JLabel("generation"));
		keyTypeList = new JComboBox<String>(keyGenerationType);
		keyTypeList.setSelectedIndex(root.iniFile.keyTypeList);
		fields.add(keyTypeList);
		
		JPanel coordsField = new JPanel();
		coordsField.setLayout(new GridLayout(0,3));
		coordsField.add(new JLabel("key"));
		coordsField.add(new JLabel("X"));
		coordsField.add(new JLabel("Y"));
		JLabel[] keyLabels = {new JLabel("F4"),new JLabel("F5"),new JLabel("F6"),new JLabel("F7"),new JLabel("F8") };
		for(int i=0; i<5; i++)
		{	coords[i] = new JTextField[2];
			coords[i][0] = new JTextField();
			coords[i][0].setText(""+root.iniFile.coords[i].x);
			coords[i][0].setName("coord"+i+",0");
			coords[i][0].addFocusListener(this);
			coords[i][1] = new JTextField();
			coords[i][1].setText(""+root.iniFile.coords[i].y);
			coords[i][1].setName("coord"+i+",1");
			coords[i][1].addFocusListener(this);
			
			coordsField.add(keyLabels[i]);
			coordsField.add(coords[i][0]);
			coordsField.add(coords[i][1]);
		}
				
		JPanel vycpavka = new JPanel();
		vycpavka.setMinimumSize(new Dimension(30,60));
		vycpavka.setMaximumSize(new Dimension(30,60));
		
		JPanel coordsButton = new JPanel();
		coordsButton.setLayout(new BoxLayout(coordsButton,BoxLayout.PAGE_AXIS));
		coordsButton.setMinimumSize(new Dimension(30,60));
		coordsButton.setMaximumSize(new Dimension(30,60));
		JButton bCPlus,bCMinus,bCPlus2,bCMinus2;
		bCPlus = new JButton();
		bCPlus.setText("+");
		bCPlus.setActionCommand("cplus");
		bCPlus.addActionListener(this);
		bCPlus.setMaximumSize(new Dimension(48, 20));
		bCPlus.setMinimumSize(new Dimension(48, 20));
				
		bCMinus = new JButton();
		bCMinus.setText("-");
		bCMinus.setActionCommand("cminus");
		bCMinus.addActionListener(this);
		bCMinus.setMaximumSize(new Dimension(48, 20));
		bCMinus.setMinimumSize(new Dimension(48, 20));
		
		bCPlus2 = new JButton();
		bCPlus2.setText("++");
		bCPlus2.setActionCommand("cplus2");
		bCPlus2.addActionListener(this);
		bCPlus2.setMaximumSize(new Dimension(48, 20));
		bCPlus2.setMinimumSize(new Dimension(48, 20));
				
		bCMinus2 = new JButton();
		bCMinus2.setText("--");
		bCMinus2.setActionCommand("cminus2");
		bCMinus2.addActionListener(this);
		bCMinus2.setMaximumSize(new Dimension(48, 20));
		bCMinus2.setMinimumSize(new Dimension(48, 20));
		
		coordsButton.add(bCPlus2);
		coordsButton.add(bCPlus);
		coordsButton.add(bCMinus);
		coordsButton.add(bCMinus2);
				
		fields.setLayout(new GridLayout(0,2));
		fields.setMaximumSize(new Dimension(380,60));
		fields.setMinimumSize(new Dimension(380,60));
		JPanel ufields = new JPanel();
		ufields.add(fields);
		ufields.add(vycpavka);	
		ufields.add(coordsField);
		ufields.add(coordsButton);
		ufields.setMaximumSize(new Dimension(380,125));
		ufields.setMinimumSize(new Dimension(380,125));
		return ufields;
	}
	
	/**
	 * Reload user data and workspace data from parent
	 */
	public void reloadData()
	{	tfields[0].setText(""+(root.getProfile().attempt+1));
		tfields[1].setText(""+root.blankArea.getWidth());
		tfields[2].setText(""+root.blankArea.getHeight());
		tprof.setText(root.getProfile().id);
	}
	
	/**
	 * Handle actions in dialog
	 * @param e action performed
	 */
	public void actionPerformed(ActionEvent e)
	{	if(e.getActionCommand() == "change")
		{	setVisible(false);
			root.loadProfile();
			reloadData();
			setVisible(true);
		
		}
		if(e.getActionCommand()=="cancel")
		{	setVisible(false);
			return;
		}
		if(e.getActionCommand()=="start")
		{	setVisible(false);
			root.blankArea.requestFocus();
			if(inType == AttemptData.MOUSE || inType == AttemptData.KEY)
			{	root.iniFile.weightList = weightList.getSelectedIndex();
				root.iniFile.targetList = targetList.getSelectedIndex();
			}
			if(inType == AttemptData.MOUSE) 
			{	root.iniFile.diamList = diamList.getSelectedIndex();
				root.iniFile.typeList = typeList.getSelectedIndex();
			}
			if(inType == AttemptData.KEY) 
			{	root.iniFile.keyTypeList = keyTypeList.getSelectedIndex();
				for(int i=0;i<5;i++)
				{	int x=new Double(coords[i][0].getText()).intValue();
					int y=new Double(coords[i][1].getText()).intValue();
					root.iniFile.coords[i] = new Point(x,y);
				}
			}
			if(inType == AttemptData.LEARN) 
			{	//med.iniFile.keyTypeList = keyTypeList.getSelectedIndex();
				for(int i=0;i<2;i++)
				{	int x=new Double(coords[i][0].getText()).intValue();
					int y=new Double(coords[i][1].getText()).intValue();
					root.iniFile.coords[i] = new Point(x,y);
				}
			}
			root.iniFile.inType = inType;
			try
			{	root.iniFile.save();
			
			}
			catch(IOException ex)
			{	JOptionPane.showMessageDialog(root, "Couldn't save preferences to ini file.");
				
			}
			if(inType == AttemptData.MOUSE) 
			{	root.iniFile.coords[4].x=new Double(distanceFld.getText()).intValue();
				root.setAttempt(new AttemptData(root.getProfile().id,
					new Double(tfields[0].getText()).intValue(),
					inType,
					weightTypes[weightList.getSelectedIndex()],
					root.blankArea.getWidth(),root.blankArea.getHeight(),
					numberTargets[targetList.getSelectedIndex()]+1,
					generationType[typeList.getSelectedIndex()], 
					diameters[diamList.getSelectedIndex()],
					root.iniFile.coords
					));
				root.blankArea.createFirstMouseTarget(root.getAttempt());
				root.blankArea.setFocusTraversalPolicyProvider(true);
			}
			if(inType == AttemptData.KEY) 
			{	root.setAttempt(new AttemptData(root.getProfile().id,
					new Double(tfields[0].getText()).intValue(),
					inType,
					weightTypes[weightList.getSelectedIndex()],
					root.blankArea.getWidth(),root.blankArea.getHeight(),
					numberTargets[targetList.getSelectedIndex()+1],
					keyGenerationType[keyTypeList.getSelectedIndex()],
					0,
					root.iniFile.coords));
				root.getAttempt().targetCharCode = KeyEvent.VK_F8;
				root.blankArea.createFirstKeyTarget(root.getAttempt());
				root.blankArea.setEnabled(true);
				root.blankArea.setFocusTraversalPolicyProvider(true);
				//System.out.println(med.blankArea.getKeyListeners());
					
			}
			if(inType == AttemptData.LEARN) 
			{	root.Order = root.createOrder(root.iniFile.coords[1].x,root.iniFile.coords[1].y,root.iniFile.coords[0].x);
				root.Task = 0;
				root.setAttempt(new AttemptData(root.getProfile().id,
					new Double(tfields[0].getText()).intValue(),
					inType,
					weightTypes[0],
					root.blankArea.getWidth(),root.blankArea.getHeight(),
					root.Order[root.Task],
					"learn", 
					30,
					root.iniFile.coords
					));
				root.MaxRepetitions = root.iniFile.coords[0].y;
				root.MaxTask = root.iniFile.coords[1].y-root.iniFile.coords[1].x+1;
				root.Repetition = 0;
				root.blankArea.createFirstLearnTarget(root.getAttempt());
				root.blankArea.setFocusTraversalPolicyProvider(true);
				
			}
		}
		if(e.getActionCommand()=="mouse")
		{	inType = AttemptData.MOUSE;
			createContent();
			setResizable(false);
			setEnabled(true);
			setVisible(true);
			System.out.println("mouse");
			this.repaint();
			return;
		}
		if(e.getActionCommand()=="key")
		{	inType = AttemptData.KEY;
			createContent();
			setResizable(false);
			setEnabled(true);
			setVisible(true);
			System.out.println("key");
			this.repaint();
			return;
		}
		if(e.getActionCommand()=="learn")
		{	inType = AttemptData.LEARN;
			createContent();
			setResizable(false);
			setEnabled(true);
			setVisible(true);
			System.out.println("learn");
			this.repaint();
			return;
		}
		if(e.getActionCommand()=="cplus")
		{	updateCoords(1);	
		}
		if(e.getActionCommand()=="cminus")
		{	updateCoords(-1);	
		}
		if(e.getActionCommand()=="cplus2")
		{	updateCoords(10);	
		}
		if(e.getActionCommand()=="cminus2")
		{	updateCoords(-10);	
		}
	}
	
	/**
	 * Change physical coordinates (distance) of the keys
	 * (used for external keys handled by keyboard input) 
	 * @param up
	 */
	void updateCoords(int up)
	{	JTextField jt = coords[focX][focY];
		int i=new Double(jt.getText()).intValue();
		i+=up;
		jt.setText(""+i);
		jt.requestFocusInWindow();
		jt.setSelectionStart(0);
		jt.setSelectionEnd(1);
		jt.repaint();
		
	}
	
	/**
	 * @param s generationType name
	 * @return generationType index
	 */
	public static int generationType(String s)
	{	int i=0;
		for(i=0;i<generationType.length; i++)
		{	if(s == generationType[i]) return i;
			
		}
		return 0;
	}
	
	/**
	 * Handle gain of focus
	 * @param e focus event
	 */
	public void focusGained(FocusEvent e) 
	{	String s=e.getComponent().getName();
		focX=new Double(s.substring(5,6)).intValue();
		focY=new Double(s.substring(7,8)).intValue();
		JTextField jt = (JTextField) e.getComponent();
		int len=jt.getText().length();
		jt.setSelectionStart(0);
		jt.setSelectionEnd(len);
		
	}
	
	
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
