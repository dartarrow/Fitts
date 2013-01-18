package fitts;

import java.io.File;
import java.io.PrintWriter;
import java.awt.geom.Point2D;
import java.awt.Point;
import java.util.Random;
import optimization.*;
import java.text.NumberFormat;

public class AttemptData {
	String profile;
	String weights;
	String log;
	int targetCharCode;
	Point[] coords = new Point[5];
	public static final int KEY = 1;
	public static final int MOUSE = 2;
	public static final int LEARN = 3;
	int inType;
	int width,height;
	int diameter;
	int maxTargets;
	int attempt;
	String type;
	int target;
	Point old = null;
	long otime = 0;
	long score = 0;
	double cum_length = 0;
	double cum_time = 0;
	double cum_travel = 0; 
	double length = 0;
	Random pGenerator,dGenerator; 
	StopWatch stopWatch = new StopWatch();
	NumberFormat nf = NumberFormat.getInstance();
	
	MouseData mouseData[];
	double[] lenghts;
	double[] times;
	int[] sizes;
	boolean[] outliers;
	
	public AttemptData()
	{	profile = "none";
		attempt = 0;
		weights = "none";
		width = 0;
		height = 0;
		diameter = 0;
		inType = 2;
		maxTargets = 0;
		type = NewAttemptDialog.generationType[NewAttemptDialog.RANDOM];
		target = -1;
		lenghts = new double[maxTargets];
		times = new double[maxTargets]; 
		sizes = new int[maxTargets];
		outliers = new boolean[maxTargets];
		mouseData = new MouseData[maxTargets];
		for(int i=0; i<maxTargets; i++)
		{	mouseData[i] = new MouseData();
			outliers[i] = false;
		}
		for(int i=0;i<5;i++)
		{	coords[i]=new Point();
			
		
		}
	}
	/**
	 * Creates a new AttemptData object.
	 * @param profile UserProfile used during this attempt.
	 * @param attempt Ordinal number of this attempt.
	 * @param weights Weights applied during this attempt.
	 * @param height Height of the field.
	 * @param width Width of the field.
	 * @param maxTargets Number of targets.
	 * @param type Mode of target generation.
	 * @param diameter Diameter of targets (used only with some modes).
	 */
	public AttemptData(String profile, int attempt, int inType, String weights, int height, int width, int maxTargets, String type, int diameter, Point[] coords)
	{	this.profile = profile;
		this.attempt = attempt;
		this.weights = weights;
		this.width = width;
		this.height = height;
		this.maxTargets = maxTargets;
		this.type = type;
		this.inType = inType;
		this.diameter = diameter;
		for(int i=0;i<5;i++)
		{	this.coords[i]=coords[i];
		}
		target = -1;
		mouseData = new MouseData[maxTargets];
		times = new double[maxTargets]; 
		lenghts = new double[maxTargets];
		sizes = new int[maxTargets];
		outliers = new boolean[maxTargets];
		for(int i=0; i<maxTargets; i++)
		{	mouseData[i] = new MouseData();
			lenghts[i] = 0;
			times[i] = 0;
			outliers[i] = false;
		}
		if(type == NewAttemptDialog.generationType[NewAttemptDialog.SERIES])
			 pGenerator = new Random(maxTargets*100);
		else pGenerator = new Random();
		
		if(inType == LEARN)
		{	pGenerator = new Random(maxTargets*100+coords[0].x);
			
		}
		
		if(diameter == 0) dGenerator = new Random();
		else dGenerator = new Random(maxTargets*100);
	}
	
	public boolean running()
	{	if(target>-1 && target<maxTargets) return true;
	 	return false;
	}
	
	public boolean finished()
	{	if(target>= maxTargets) return true;
	 	return false;
	}
	
	public void addLenght(double lenght)
	{	lenghts[target]=lenght;
	}
	
	public void addTime(double time)
	{	times[target]=time;
	}
	
	public void addHit(Point click, Point tgt, int size)
	{	long time = stopWatch.getElapsedTime();
		if(target==-1)
		{	target++;
			old = click;
			stopWatch.start();
			return;
		}
		mouseData[target].addClick(click, time);
		/*if(target>0) times[target] = time-times[target-1];
		else times[target] = time;*/
		sizes[target] = size;
		times[target] = time - otime;
		otime = time;
		lenghts[target] = old.distance(click);
		
		old = click;
		cum_travel = cum_travel + countTravel(target);
		cum_length = cum_length+lenghts[target];
		if(target > 0) cum_time = cum_time + times[target];
		target ++;
	}
	
	public void addMiss(Point click, Point tgt)
	{	if(!running() || finished()) return;
		long time = stopWatch.getElapsedTime();
		mouseData[target].addClick(click, time);
	}
	
	public void addMove(Point point)
	{	mouseData[target].addMove(point, stopWatch.getElapsedTime());
	}
	
	public double countTravel(int i)
    {	double cnt=0;
    	for(int j=1; j<mouseData[i].moves.size(); j++){
    		cnt+=mouseData[i].moves.get(j).point.distance(mouseData[i].moves.get(j-1).point);
    	}
    	return cnt;
    }
	
	public double ID(double distance, double size)
	{	return Math.log(distance/size +1)/Math.log(2);
		
	}
	
	void logStatistics()
	{	log +="avg target Speed : "+cum_length/cum_time+"\n";
		log +="avg travel Speed : "+cum_travel/cum_time+"\n";
		log +="travel per distance : "+cum_travel/cum_length+"\n";
		
		
	}
	
	public Point2D refineCountAB()
	{	double[] diffs = new double[maxTargets];
		double sum=0;
		NumberFormat nfi = NumberFormat.getInstance();
		NumberFormat nfd = NumberFormat.getInstance();
		nfi.setMaximumFractionDigits(0);
		nfi.setMinimumIntegerDigits(2);
		nfd.setMaximumFractionDigits(3);
		nfd.setMinimumFractionDigits(3);
		Point2D ab = countAB();
		for(int i=0; i<maxTargets; i++)
		{	diffs[i] = ab.getX()+ID(lenghts[i],sizes[i])*ab.getY()-times[i];
			diffs[i]=diffs[i]*diffs[i];
			sum+= diffs[i]; 
		}
		for(int i=0;i<maxTargets; i++)
			if(diffs[i]>=2*sum/maxTargets) outliers[i]=true;
		log = "A :"+nfd.format(ab.getX())+" B : "+nfd.format(ab.getY())+"\n";
		for(int i=0;i<maxTargets;i++)
		{	log += nfi.format(i)+" d :"+nfd.format(lenghts[i])+" s :"+nfi.format(sizes[i])+" ID :"+nfd.format(ID(lenghts[i],sizes[i]))+" t :"+nfd.format(times[i])+" d2 :"+nfd.format(diffs[i]);
			if(outliers[i]) log += " o";
			log += "\n";
		}
		log+= " 					sum : "+sum+"\n";	
		ab = countAB();
		log+= "A :"+nfd.format(ab.getX())+" B : "+nfd.format(ab.getY())+"\n";
				
		return countAB();
	}
		
	public Point2D countAB(int start, int end)
	{	double ret[] = {0,0};
		int outl=0;
		if(end >= maxTargets || start > end) return null;
		
		for(int i=start;i<end;i++)	if(outliers[i]) outl++;
		int maxi = end-start-outl;
		if(maxi-outl<1) return null;
		double A[][]=new double[maxi-outl+1][3];
		
		int i=0,j=start;
		while(i<=maxi-outl && j<maxTargets)
		{	if(!outliers[j])
			{	A[i][0]=1;
				A[i][1]=ID(lenghts[j],sizes[j]);
				A[i][2]= -times[j];
				i++;
			}
			j++;
		}	
		MinSquare ms = new MinSquare();
		//ms.output = MinSquare.FULL_OUTPUT;
		ms.setA(A);
		ms.solve(ret);
		return new Point2D.Double(ret[0],ret[1]);
	}
	
	public Point2D countAB()
	{	return countAB(0,maxTargets-1);
		
	}
	
	
	public String dumpInfo(int tg)
	{	String out;
			
		if(tg<0) return "Nothing!";
		out = tg+"#"+target+" d :" + nf.format(lenghts[tg])+" t :" + nf.format(times[tg]) + " s :"+sizes[tg];
		out+= " ID :" + nf.format(ID(lenghts[tg],sizes[tg]));
		/*
		if(tg > 0)
		{	Point2D ab = countAB(0,tg);
			out+= " A :" + ab.getX() + " B :" + ab.getY() + " "+tg;
		}*/
		return out;
	}
	
	public void saveAttempt()
    {	PrintWriter out_c = null,out_m=null,out_a=null;
    	try {
    		File pwd = new File(new File(".").getCanonicalPath()+"/"+profile+attempt);
    		File file_c = new File(pwd+".clk");
    		File file_m = new File(pwd+".mov");
    		File file_a = new File(pwd+".att");
            out_c = new PrintWriter(file_c);
            out_m = new PrintWriter(file_m);
            out_a = new PrintWriter(file_a);
        }
        catch(Exception e){
            	e.printStackTrace();
        }
      	
    	// write data about moves
    	for(int i=0; i<mouseData.length; i++)
    	{	
    		for(int j=0; j<mouseData[i].moves.size(); j++)
    		{	MouseData.PointTime pt;
    			pt = mouseData[i].moves.get(j);
    			out_m.println(i+","+pt.point.x+","+pt.point.y+","+pt.time);
    		}
    	}
    	// write data about clicks
    	for(int i=0; i<mouseData.length; i++)
    	{	
    		for(int j=0; j<mouseData[i].clicks.size(); j++)
    		{	MouseData.PointTime pt;
    			pt = mouseData[i].clicks.get(j);
    			out_c.println(i+","+pt.point.x+","+pt.point.y+","+sizes[i]+","+pt.time);
    		}
    	}
    	// write data about attempt
    	out_a.println(profile);
    	out_a.println(attempt);
    	out_a.println(weights);
    	out_a.println(width);
    	out_a.println(height);
    	out_a.println(maxTargets);
    	out_a.println(type);
    	out_a.println(inType);
    	for(int i=0; i<5; i++)
    	{	out_a.println(coords[i].x+" "+coords[i].y);
    	}	
    	    	
    	out_c.close();
    	out_m.close();
    	out_a.close();
    	
    	
    }
}
