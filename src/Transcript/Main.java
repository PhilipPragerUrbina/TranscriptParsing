package Transcript;

import Transcript.IO.ReadFile;
import Transcript.IO.StringParser;
import Transcript.IO.WriteFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.IllegalFormatException;

public class Main {

    public static void main(String[] args) {
        //get filename
        String filename = "transcript.vtt"; //default
        if(args.length > 0){
            filename = args[0]; //command argument
            //if you want to process a whole folder, you can use command line pipes and tools to do so using this program
        }

    //read transcript
        ReadFile transcript_file =  new ReadFile(filename);
        //parse transcript
        Transcript transcript = new Transcript(transcript_file.getLines());
        outputSummary(transcript,filename);
        outputCondensed(transcript,filename);
    }

    //output the summary statistics file
    public static void  outputSummary(Transcript transcript, String original_filename){
        WriteFile summary  = new WriteFile(original_filename + ".summary"); //create summary file
        //output title
        summary.writeLine("Summary Statistics file for " + original_filename);
        summary.writeLine(""); //spacing

        //get # of people
        ArrayList<Person> people = transcript.getPeople();
        summary.writeLine("Total # of people: " + people.size());
        //output total length, start to finish
        summary.writeLine("Total length of session: " + transcript.getStart().duration(transcript.getEnd()));
        //output speaker switches
        summary.writeLine("Total speaker switches: " + transcript.getSpeakerSwitches());
        summary.writeLine("");//spacing

        //output total talk time for each person
        summary.writeLine("Total talk time");
        for (Person person : people) {
            summary.writeLine(person.getName() + ": " +person.getTalkTime());
        }
        summary.writeLine(""); //spacing

        //output average talk time for each person
        summary.writeLine("Average talk time");
        for (Person person : people) {
            summary.writeLine(person.getName() + ": " +  person.getTalkTime().divide(person.getNumSegments())); //output total time / number times they spoke
        }
        summary.close(); //save file
    }

    //output the minimized transcript
    public static void  outputCondensed(Transcript transcript, String original_filename){
        WriteFile file  = new WriteFile(original_filename + ".condensed"); //create file
        //output title
        file.writeLine("Condensed  transcript for " + original_filename);
        file.writeLine(""); //spacing

        //output name and duration
        for (Segment segment: transcript.getData()) {
            file.writeLine(segment.getName()+": "+segment.getStart().duration(segment.getEnd())); //calculate segment duration
        }

        file.close(); //save file
    }


}
