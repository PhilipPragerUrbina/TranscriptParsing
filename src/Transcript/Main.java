package Transcript;

import Transcript.IO.StringParser;

import java.text.ParseException;
import java.util.IllegalFormatException;

public class Main {

    public static void main(String[] args) {
	// email parse test
        StringParser s = new StringParser("^+~(&+#+)@(&+.&+)^+~");
        String test = "  berp123@gmail.com  ";
        System.out.println(s.parse(test));

        try {
            Time time = new Time("0:2:2");
            System.out.println(time);
            System.out.println(time.toSeconds());
        }catch (ParseException a){
            a.printStackTrace();;
        }

    }
}
