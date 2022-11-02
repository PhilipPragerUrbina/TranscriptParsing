package Transcript.IO;

import java.util.ArrayList;

//my own mini regex like language
public class StringParser {
    //represent instructions
    private enum Instruction{
        NEXT_CHAR, //a specific char that was specified
        NUMERIC, //any numeric character
        WHITESPACE, //whitespace
        WORD, //any word
        ANY, //any character
        REPEAT, //repeat until other type
        OPEN_CAPTURE, //open a capture group
        CLOSE_CAPTURE //close a capture group
    }

    //create a string parser, to match a pattern to a string
    //will only match once
    //syntax:
    /*
    normal character = match that character
    \w = any word character
    \n = any number chracter
    \s =  any space
    \* = anything
    \+ = repeat last until other is encountered
    \(anything\) = capture groups
     */
    public  StringParser( String pattern) throws IllegalArgumentException {
        characters = new ArrayList<>();
        instructions = new ArrayList<>();
        //parse pattern
        for (int i = 1; i < pattern.length(); i++) {
            String current = pattern.substring(i, i+1); //ap complaint code
            String last = pattern.substring(i-1, i);;
            if(current.equals("\\")){
                //is a command, move on to next char to find command type
                continue;
            }else if(last.equals("\\")){
                //a command, add it
                instructions.add(getInstruction(current));
            }else{
                // a normal character
                instructions.add(Instruction.NEXT_CHAR); //add instruction
                characters.add(current); //add character to match
            }

        }

    }

    private static Instruction getInstruction(String character) throws IllegalArgumentException {
        switch (character){
            case "w":
                return Instruction.WORD;
            case "n":
                return Instruction.NUMERIC;
            case "s":
                return Instruction.WHITESPACE;
            case "*":
                return Instruction.ANY;
            case "+":
                return Instruction.REPEAT;
            case "(":
                return Instruction.OPEN_CAPTURE;
            case ")":
                return Instruction.CLOSE_CAPTURE;

        }
        throw new IllegalArgumentException("Unknown instruction: " +character);
    }

    private ArrayList<Instruction> instructions; //actual program/pattern
    private ArrayList<String> characters; //characters to specifically match

    //parse a string using the pattern, and return the capture groups
    //if it does not match, return null
    public ArrayList<String> parse(String input){
        //

        for (Instruction instruction : instructions) {

        }
    }



}
