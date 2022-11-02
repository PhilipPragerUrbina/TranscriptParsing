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
        ArrayList<String> captures = new ArrayList<>();

        //keep current character in characters to match
        int match_id = 0;
        String capture_so_far = ""; //data for recent capture group
        int current_char_id = 0;  //current char in input id
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            char current = input.substring(current_char_id, current_char_id+1).toCharArray()[0]; //get current input char
            switch (instruction){
                case NUMERIC:
                    if(Character.isDigit(current)){
                        capture_so_far += current;
                        current_char_id++;
                        break;
                    }
                        return null;
                case WORD:
                    if(Character.isAlphabetic(current)){
                        capture_so_far += current;
                        current_char_id++;
                        break;
                    }
                    return null;
                case WHITESPACE:
                    if(Character.isWhitespace(current)){
                        capture_so_far += current;
                        current_char_id++;
                        break;
                    }
                    return null;
                case NEXT_CHAR:
                    if(current == characters.get(match_id).charAt(0)){
                        capture_so_far += current;
                        current_char_id++;
                        match_id++;
                        break;
                    }
                    return null;
                case ANY:
                        capture_so_far += current;
                        current_char_id++;
                        break;
                case OPEN_CAPTURE:
                    capture_so_far = "";
                case CLOSE_CAPTURE:
                    captures.add(capture_so_far);
                case REPEAT:
                    //if next does not match
                    if(true){
                        i--;
                    }
                    break;
            }

        }
        return captures;
    }



}
