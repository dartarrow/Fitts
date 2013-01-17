package fitts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;

public class NewProfileDialog extends JDialog implements ActionListener {
	JTextField[] tfields;
	JTextArea textArea;
	int hm = 0;
	MouseEventDemo med;	
	public NewProfileDialog(MouseEventDemo m)
	{	JPanel fields = new JPanel();
		JPanel buttons = new JPanel();
		med = m;
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
		
		
		BlankArea ba = med.blankArea;
		ba.setNewCircle(ba.X_SIZE/16, ba.Y_SIZE*5/16, 30);
		ba.repaint();
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{	//System.out.println("ksa");
		if(e.getActionCommand()=="cancel")
		{	//System.out.println("kilil");
			setVisible(false);
			return;
		}
		if(e.getActionCommand()=="plus")
		{	//System.out.println("kilil");
			med.blankArea.circle.y-=5;
			hm -= 5;
			med.setEnabled(true);
			med.blankArea.repaint();
			setEnabled(true);
			tfields[6].setText(hm+"");
			tfields[6].repaint();
			return;
		}
		if(e.getActionCommand()=="minus")
		{	//System.out.println("kilil");
			med.blankArea.circle.y+=5;
			hm += 5;
			med.setEnabled(true);
			med.blankArea.repaint();
			setEnabled(true);
			tfields[6].setText(hm+"");
			tfields[6].repaint();
			return;
		}
		if(e.getActionCommand()=="create")
		{	
			med.setProfile(new UserProfile(tfields[0].getText(),
										   tfields[1].getText(),
										   tfields[2].getText(),
										   new Double(tfields[3].getText()).intValue(),
										   new Double(tfields[6].getText()).intValue(),
										   new Double(tfields[4].getText()).intValue(),
										   tfields[5].getText(),
										   textArea.getText()));
			med.saveProfile();
			setVisible(false);
			
			
		}
		
	}

}