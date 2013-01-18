package fitts;

import java.io.*;
import java.util.Scanner;

import javax.swing.*;

/**
 * Class storing details of a user
 */

public class UserProfile {
	public static final String[] PROFICIENCY = {"none","low","medium","high"};
	String name;
	String surname;
	String id;
	int age,attempt;
	int height_mod,arm_length;
	String proficiency,comments;
	
	/**
	 * Create a new UserProfile with full set of parameters
	 * @param n name
	 * @param s surname
	 * @param i id
	 * @param a age
	 * @param hm height
	 * @param al arm length
	 * @param pro proficiency
	 * @param co comments
	 */
	public UserProfile(String n, String s, String i, int a, int hm, int al, String pro, String co)
	{	name = n;
		surname = s;
		id = i;
		age = a;
		height_mod = hm;
		arm_length = al;
		attempt =0;
		proficiency = pro;
		comments = co;
	}
	
	/**
	 * Create a blank new UserProfile
	 */
	public UserProfile()
	{	name = "none";
		surname = "none";
		id = "none";
		age = 0;
		attempt =0;
		height_mod = 0;
		arm_length = 0;
		proficiency = "none";
		comments = "";
	}
	
	
	/**
	 * Save current user profile to "id".fp file
	 */
	public void saveProfile()
    {	PrintWriter pw;
    	try
		{	File out = new File(new File(".").getCanonicalPath()+"/"+id+".fp");
		
			pw = new PrintWriter(out);
			pw.println(name);
			pw.println(surname);
			pw.println(id);
			pw.println(age);
			pw.println(height_mod);
			pw.println(arm_length);
			pw.println(proficiency);
			pw.println(attempt);
			pw.print(comments);
			pw.close();
		}
		catch(IOException ioe)
		{	System.out.println(ioe.toString());				
		}
    	
    }
	
	/**
	 * Open a dialog to load a user profile
	 * @param fr JFrame ?? parent frame
	 * @return loaded UserProfile or null when canceled
	 */
	public UserProfile loadProfile(JFrame fr)
    {	JFileChooser fc = new JFileChooser();
		try {
			fc.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
			fc.setFileFilter(new pfFilter());
			
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
		int returnVal = fc.showOpenDialog(fr);
    	if (returnVal == JFileChooser.APPROVE_OPTION) 
    	{	return loadProfile(fr,fc.getSelectedFile());
    	}
    	else return null;
    		
    }
	
	/**
	 * Load user profile from file of name s
	 * @param fr parent frame
	 * @param s file name
	 * @return loaded UserProfile
	 */
	
	public UserProfile loadProfile(JFrame fr,String s)
    {	File file = null;
		try
    	{	file = new File(new File(".").getCanonicalPath()+"/"+s+".fp");
    	
    	}
    	catch(IOException e)
    	{	JOptionPane.showMessageDialog(fr, "I/O Error!");
    		
    	}
    	return loadProfile(fr,file);
    		
    }
	
	/**
	 * Load user profile from file f
	 * @param fr parent frame
	 * @param f file to be loaded
	 * @return loaded UserProfile
	 */
	private UserProfile loadProfile(JFrame fr,File f)
    {	Scanner s = null;
    	int i = 0;
    	String[] buf = new String[8];
    	String com="";
    	System.out.println(f);
    	try 
    	{
    		s = new Scanner(new BufferedReader( new FileReader(f)));
    			
    		while (s.hasNext() && i<8) buf[i++]=s.next();
    		while (s.hasNext() ) com+=s.next();
    	}
    	
    	catch(FileNotFoundException e){
    			JOptionPane.showMessageDialog(fr, "File not found!");
    		}
    	/*catch(IOException e){
			JOptionPane.showMessageDialog(fr, "I/O Error!");
		}*/
    	
    	finally {
    			if (s != null) {
    				s.close();
            }
    	}
    	if(i<6) { JOptionPane.showMessageDialog(fr, "Profile corrupt!"); return null;}
    	try
    	{	name = buf[0];
    		surname = buf[1];
    		id = buf[2];
    		age = new Double(buf[3]).intValue();
    		height_mod = new Double(buf[4]).intValue();
    		arm_length = new Double(buf[5]).intValue();
    		proficiency = buf[6];
    		attempt = new Double(buf[7]).intValue();
    		comments = com;
    	} catch(Exception e)
    	{	
    		JOptionPane.showMessageDialog(fr, "Profile corrupt, probably old!"); 
       	
    	}
    	return this;
   	}
}
