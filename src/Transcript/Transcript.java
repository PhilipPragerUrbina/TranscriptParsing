package Transcript;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

//a transcript
public class Transcript {
    private ArrayList<Segment> data = new ArrayList<Segment>(); //contain transcript data
    private HashMap<String,Person> people = new HashMap<String, Person>(); //keep track of people

    //create a transcript from files lines
    public Transcript(ArrayList<String > lines){
       int index = 0;
       while (index < lines.size()-2){ //go through lines
           String[] three_lines = {lines.get(index), lines.get(index+1), lines.get(index+2) }; //get three lines at a time
           try {
               Segment segment = new Segment(three_lines); //try to parse a segments
               //segment was parsed
               data.add(segment);
               index+=3; //segment was valid,so we can move forward 3 lines
           } catch (ParseException e) {
               //could not parse segment, skip line
               index++;
           }
       }
    }

    //get information about participants
    private void calculatePeople(){
        for (Segment segment : data) {
            String name = segment.getName(); //get the persons name
            if (people.containsKey(name)) {
                people.get(name).addSegment(segment); //exists update record
            }else{
                people.put(name, new Person(name)); //does not exist create new record
            }
        }
    }

    //get a line by line overview of each segment
    @Override
    public String toString() {
        String out = "";
        for (Segment segment: data ) {
            out +=segment +"\n";
        }
        return out;
    }
}
