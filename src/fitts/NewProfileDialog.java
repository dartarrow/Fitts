package fitts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class to handle dialog for creation of new user profile
 * @author tzeentch
 *
 */

public class NewProfileDialog extends JDialog implements ActionListener {
	static final long serialVersionUID = 0;
	JTextField[] tfields;
	JTextArea textArea;
	int height_modifier = 0;
	FittsMain root;	
	
	/**
	 * constructor - sets m as the parent
	 * @param m parent
	 */
	public NewProfileDialog(FittsMain m)
	{	JPanel fields = new JPanel();
		JPanel buttons = new JPanel();
		root = m;
		setAlwaysOnTop(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		String[] l = {"name","surname","id","age","arm length","proficiency","height modifier"};
		JLabel[] labels = new JLabel[l.length];
		tfields = new JTextField[l.length];
		for(int i=0; i<l.length; i++)
		{ 	labels[i] = new JLabel(l[i]);
			tfields[i] = new JTextField();
			tfields[i].setSize(new Dimension(120,15));
			fields.add(labels[i]);
			fields.add(tfields[i]);
		}
		JPanel hButtons = new JPanel(); 
		JButton bPlus, bMinus;
		bPlus = new JButton("+");
		bPlus.setActionCommand("plus");
		bPlus.addActionListener(this);
		bMinus = new JButton("-");
		bMinus.setActionCommand("minus");
		bMinus.addActionListener(this);
		hButtons.add(bPlus);
		hButtons.add(bMinus);
		hButtons.setLayout(new BoxLayout(hButtons,BoxLayout.LINE_AXIS));
		fields.add(hButtons);
		
		fields.setLayout(new GridLayout(0,2));
		fields.setMaximumSize(new Dimension(200,100));
		fields.setMinimumSize(new Dimension(200,100));
				
		JButton bCreate,bCancel;
		bCreate = new JButton();
		bCreate.setText("Create");
		bCreate.setActionCommand("create");
		bCreate.addActionListener(this);
		
		bCancel = new JButton();
		bCancel.setText("Cancel");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);
		
		buttons.add(bCreate);
		buttons.add(bCancel);
				
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		add(new Label("New Profile"));
		add(fields);
		add(new Label("Comments"));
		textArea = new JTextArea();
		JScrollPane scP = new JScrollPane(textArea);
		scP.setMinimumSize(new Dimension(280,100));
		scP.setPreferredSize(new Dimension(280,100));
		scP.setMaximumSize(new Dimension(280,100));
		add(scP);
		add(buttons);
		
		//this.setSize(120, 120);
		setBounds(200,200,350,350);
		//pack();
		setResizable(false);
		setEnabled(true);
		
		
		BlankArea ba = root.blankArea;
		ba.setNewCircle(BlankArea.X_SIZE/16, BlankArea.Y_SIZE*5/16, 30);
		ba.repaint();
		setVisible(true);
		
	}
	
	/**
	 * Handle actions performed on the dialog
	 * currently: cancel, plus, minus, create, 
	 */
	 
	
	public void actionPerformed(ActionEvent e)
	{	if(e.getActionCommand()=="cancel")
		{	setVisible(false);
			return;
		}
		if(e.getActionCommand()=="plus")
		{	root.blankArea.circle.y-=5;
			height_modifier -= 5;
			root.setEnabled(true);
			root.blankArea.repaint();
			setEnabled(true);
			tfields[6].setText(height_modifier+"");
			tfields[6].repaint();
			return;
		}
		if(e.getActionCommand()=="minus")
		{	root.blankArea.circle.y+=5;
			height_modifier += 5;
			root.setEnabled(true);
			root.blankArea.repaint();
			setEnabled(true);
			tfields[6].setText(height_modifier+"");
			tfields[6].repaint();
			return;
		}
		if(e.getActionCommand()=="create")
		{	root.setProfile(new UserProfile(tfields[0].getText(),
										   tfields[1].getText(),
										   tfields[2].getText(),
										   new Double(tfields[3].getText()).intValue(),
										   new Double(tfields[6].getText()).intValue(),
										   new Double(tfields[4].getText()).intValue(),
										   tfields[5].getText(),
										   textArea.getText()));
			root.saveProfile();
			setVisible(false);
			
			
		}
		
	}

}
