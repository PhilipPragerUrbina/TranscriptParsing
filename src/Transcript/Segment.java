package Transcript;

import Transcript.IO.StringParser;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//represent a transcript data point
public class Segment {
    private int index; //what order it happened
    private String name;
    private String text; //who spoke and what they said
    private Time start, end; //when it happened

    public Segment(int index, String name, String text, Time start, Time end) {
        this.index = index;
        this.name = name;
        this.text = text;
        this.start = start;
        this.end = end;
    }

    //create a segment from 3 lines
    /*
    index
    h:m:s --> h:m:s
    name : text
     */
    final static StringParser index_parser = new StringParser("^+~(#+)^+~");//validate and capture int
    final static StringParser time_parser = new StringParser("^+~(*+) --> (*+)^+~"); //validate and capture two times, that will be parsed later
    final static StringParser name_parser = new StringParser("^+~(*+)^~:^~(*+)^+~"); //validate and capture name + text
    public Segment(String[] lines ) throws ParseException {
        if(lines.length != 3){
            throw new ParseException("Requires 3 lines of data" + lines, 0);
        }
        //parse
        ArrayList<String> index_parsed = index_parser.parse(lines[0]);
        ArrayList<String> time_parsed = time_parser.parse(lines[1]);
        ArrayList<String> name_parsed = name_parser.parse(lines[2]);
        //validate
        if(index_parsed == null || time_parsed == null || name_parsed == null){
            throw new ParseException("Invalid formatting" + lines, 0);
        }
        //get values
        this.index = Integer.parseInt(index_parsed.get(0));
        this.name = name_parsed.get(0);
        this.text = name_parsed.get(1);
        this.start = new Time(time_parsed.get(0));
        this.end = new Time(time_parsed.get(1));
    }


    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
