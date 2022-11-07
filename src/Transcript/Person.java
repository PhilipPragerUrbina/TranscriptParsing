package Transcript;

import java.util.InputMismatchException;

//stores info about a person
public class Person {
    private String text = "";    //all of what they spoke
   private Time talk_time = new Time(0,0,0); //total time talking
    private String name; //their name
    private int num_segments = 0; //how many times they spoke

    //create a new person with a name
    public Person(String name){
        this.name = name;
    }

    //register a segment corresponding to the person, to update their values
    void addSegment(Segment segment){
        //verify that this segment belongs to the person
        if(!segment.getName().equals(name)){
            throw new IllegalArgumentException("Name " + name + " does not match " + segment.getName());
        }

        text += " " + segment.getText(); //add text
        //calculate and add duration
        Time duration = segment.getStart().duration(segment.getEnd());
        talk_time = talk_time.add(duration);

        //increment segments
        num_segments++;
    }

    public String getText() {
        return text;
    }

    public Time getTalkTime() {
        return talk_time;
    }

    public String getName() {
        return name;
    }

    public int getNumSegments() {
        return num_segments;
    }


    @Override
    public String toString() {
        return  name + "{ talk time=" + talk_time +
                ", num=" + num_segments +
                "} " ;
    }
}
