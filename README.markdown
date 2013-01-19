# Fitts

Fitts is a GUI application for acquiring user input data to be analysed with the Fitt's law and learning curve theory through predefined tasks. Originaly developed to be used with an interactive blackboard and custom hacked keyboard (allowing keys to be a few meters apart) to get input for longer than usual movements.
<<<<<<< HEAD

## External library usage

The StopWatch.java by Corey Goldberg is used for measuring time
=======
>>>>>>> 45b19f7d92071895a7f40b110698f0464d57dc4f
For a simple on the go analysis it uses public domain MINPACK routines translated from FORTRAN to Java by Steve Verrill

## Features

<<<<<<< HEAD
- User profiles for individual test subjects
- Various predefined, customizable and randomizable tasks for mouse/touchscreen and keyboard input
- Repeating tasks for learning curve data
- Data saved in human readable files

## Installation and running

Put all the files in a directory.
Compile
Get in the bin directory and enter: java fitts.FittsMain

You'll get an arror message (the fitts.ini file is missing)
In the menu -> File -> New Profile fill all the parameters (leave the height modifier at zero, but enter a value in there) <Create>
In the menu -> File -> New Attempt <Start>, click the circles
Look in the Fitts' text area (a separate window underneath for some fancy numbers)
Look in the Fitts directory for some fancy files for later use

## Data generated
- fitts.ini store the last session info
- .fp userprofile
- .att attempt parameters
- .clk click data: id, x distance, y distance, target size, timestamp
- .mov mouse movement data: target id, x coordinate, y coordinate, timestamp
=======
	- User profiles for individual test subjects
	- Various predefined, customizable and randomizable tasks for mouse/touchscreen and keyboard input
	- Repeating tasks for learning curve data

## Installation

Just put all the files in a directory and run FittsMain.java
>>>>>>> 45b19f7d92071895a7f40b110698f0464d57dc4f

## Copyright

Copyright &copy; 2012 Jan Sourek