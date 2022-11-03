package Transcript;

import Transcript.IO.StringParser;

public class Main {

    public static void main(String[] args) {
	// email parse test
        StringParser s = new StringParser("^+~(&+#+)@(&+.&+)^+");
        String test = "     berp123@gmail.com ";
        System.out.println(s.parse(test));
    }
}
