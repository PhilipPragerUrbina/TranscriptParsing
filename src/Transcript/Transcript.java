package Transcript;

import java.text.ParseException;
import java.util.ArrayList;

//a transcript
public class Transcript {
    private ArrayList<Segment> data = new ArrayList<Segment>();
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

    public void printData(){
        for (Segment segment: data ) {
            System.out.println(segment);
        }
    }
}
