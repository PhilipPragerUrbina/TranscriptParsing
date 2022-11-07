package Transcript;

import Transcript.IO.ReadFile;
import Transcript.IO.StringParser;
import Transcript.IO.WriteFile;

import java.text.ParseException;
import java.util.IllegalFormatException;

public class Main {

    public static void main(String[] args) {

        ReadFile f =  new ReadFile("transcript.vtt");


        Transcript t = new Transcript(f.getLines());
        WriteFile w  = new WriteFile("test.txt");
        w.writeLine(t.toString());
         w.close();
    }
}
