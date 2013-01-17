package fitts;

import java.util.ArrayList;
import java.awt.Point;

/**
 * Stores all data regarding a single target, 
 * i.e. movement and clicks (misses and the final hit)
 * @author Tzeentch
 *
 */
public class MouseData {
	ArrayList<PointTime> moves = null;
	ArrayList<PointTime> clicks = null;
	
	public MouseData() {
		moves = new ArrayList<PointTime>();
		clicks = new ArrayList<PointTime>();
	}
	/**
	 * Add a click to the data.
	 * @param p Coordinates of the place (Point)
	 * @param t Time in milliseconds when the click occured
	 */
	public void addClick(Point p,long t) {
		clicks.add(new PointTime(p,t));
	}
	
	public void addMove(Point p,long t) {
		moves.add(new PointTime(p,t));
	}
	
	public class PointTime {
		Point point;
		long time;
		
		public PointTime() {
			point = new Point(0,0);
			time = 0;
		}
		
		public PointTime(Point p,long t) {
			point = p;
			time = t;
		}
		
	}
}

