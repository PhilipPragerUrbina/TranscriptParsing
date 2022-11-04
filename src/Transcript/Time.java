package Transcript;

import Transcript.IO.StringParser;


import java.text.ParseException;
import java.util.ArrayList;



//represent a time
public class Time {
    //data
    final private int hours;
    final private int minutes;
    final private double seconds;

    //create a time stamp from known values
    public Time(int hours, int minutes, double seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    //create time from seconds
    public Time(double seconds){
        //todo fix
        this.hours =(int)( seconds / 60.0 * 60.0); //get hours
        this.minutes = (int) (seconds / 60) - hours * 60; //get minutes, and subtract hours
        this.seconds = seconds - minutes * 60 - hours*60*60; //get remaining seconds
    }

    final static StringParser time_pattern = new StringParser("^+~(#+):(#+):(#+.~#+)^+~");
    //parse a timestamp from a string h:m:s
public Time(String hms) throws ParseException {
    //split would be faster, but provide no verification
    ArrayList<String> numbers = time_pattern.parse(hms); //guarantees parse-able numbers
    if(numbers == null){
        throw new ParseException("Invalid time: " + hms,0); //wrong format
    }
    //set values from 3 capture groups
    hours = Integer.parseInt(numbers.get(0));
    minutes = Integer.parseInt(numbers.get(1));
    seconds = Double.parseDouble(numbers.get(2));
}

    //get the duration between two timestamps
    Time duration(Time other){
        double seconds_diff = Math.abs(other.toSeconds() - this.toSeconds()); //get seconds between
        return new Time(seconds_diff); //return new time
    }

    //get length in seconds
    double toSeconds(){
    double total_seconds = seconds;
    total_seconds+= minutes*60;
    total_seconds+= hours * 60*60;
    return total_seconds;
    }

    //get length in minutes
    double toMinutes(){
        return toSeconds()/60.0;
    }

    @Override
    public String toString() {
        return hours + ":" + minutes + ":" + seconds;
    }
}
