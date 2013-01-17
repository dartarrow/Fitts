package fitts;
/*
    Copyright (c) 2005, Corey Goldberg

    StopWatch.java is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
*/

public class StopWatch {
    
    private long startTime = 0;
    private long stopTime = 0;
    private long savedTime = 0;
    private boolean running = false;

    /**
     * Reset internal counter and start the stopwatch.
     */
    public void start() {
    	savedTime = 0;
    	running = true;
    	startTime = System.currentTimeMillis();
       
    }

    /**
     * Stop the stopwatch. Time measured can by read by getElapsedTime()
     * and getElapsedTimeSeconds()
     */
    public void stop() {
        stopTime = System.currentTimeMillis();
        running = false;
    }
    
    /**
     * Pauses the running stopwatch and remembers time measured this far.
     */
    public void pause() {
    	stopTime = System.currentTimeMillis();
    	savedTime = savedTime + (stopTime - startTime); 
    	running = false;
    }
    
    /**
     * Resumes a paused stopwatch.
     */
    public void resume() {
    	running = true;
    }

    
    /**
     * @return Measured time in milliseconds.
     */
    public long getElapsedTime() {
        long elapsed;
        if (running) {
             elapsed = (System.currentTimeMillis() - startTime + savedTime);
        }
        else {
            elapsed = (stopTime - startTime + savedTime);
        }
        return elapsed;
    }
    
    
    /**
     * @return Measured time in seconds (int division).
     */
    public long getElapsedTimeSecs() {
        return getElapsedTime()/1000;
    }
}

