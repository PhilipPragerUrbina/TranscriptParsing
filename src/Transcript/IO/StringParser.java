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
        CLOSE_CAPTURE, //close a capture group
        OPTIONAL //make last command optional
    }

    final private ArrayList<Instruction> instructions; //actual program/pattern
    final private ArrayList<String> characters; //characters to specifically match
    int num_captures = 0; //number of capture groups

    //create a string parser, to match a pattern to a string
    //will only match once.
    //backslash instructions to treat them as characters
    //make sure to escape your escape backslash for java. Example: \\+
    //syntax:
    /*
    normal character = match that character
    & = any word character
    # = any number character
    ^ =  any space
    * = anything
    + = repeat last until other is encountered
    ~ make last command optional
    (something) = capture groups
    / = escape an instruction to treat it like a normal character
     */
    //email parsing example:  "  berp123@gmail.com "  into the pattern "^+~(&+#+)@(&+.&+)^+~" outputs [berp123, gmail.com]
    public  StringParser( String pattern) {
        characters = new ArrayList<>();
        instructions = new ArrayList<>();
        //parse pattern
        for (int i = 0; i < pattern.length(); i++) {
            String current = pattern.substring(i, i+1); //ap complaint code
            String last = " ";
            if(i > 0){
                last = pattern.substring(i-1, i); //get last character
            }

            if(current.equals("\\")){
                //is an escape, move on to find what it is
                continue;
            }else if(last.equals("\\")){
                characters.add(current); //add escaped character
                instructions.add(Instruction.NEXT_CHAR); //add character instruction
            }else{
                // is it a command?
                Instruction instruction = getInstruction(current);
                if(instruction != null){
                    //it is an instruction, add it
                    instructions.add(instruction);
                }else {
                    //not an instruction, it is a character
                    instructions.add(Instruction.NEXT_CHAR); //add character instruction
                    characters.add(current); //add character to match
                }
            }

        }

    }

    private Instruction getInstruction(String character) {
        switch (character){
            case "&":
                return Instruction.WORD;
            case "#":
                return Instruction.NUMERIC;
            case "^":
                return Instruction.WHITESPACE;
            case "*":
                return Instruction.ANY;
            case "+":
                return Instruction.REPEAT;
            case "(":
                return Instruction.OPEN_CAPTURE;
            case ")":
                num_captures++;
                return Instruction.CLOSE_CAPTURE;
            case "~":
                return Instruction.OPTIONAL;

        }
       return null;
    }

    //get the number of capture groups
    public int getNumCaptures() {
        return num_captures;
    }

    //parse a string using the pattern, and return the capture groups
    //if it does not match, return null
    public ArrayList<String> parse(String input){
        ArrayList<String> captures = new ArrayList<>();

        int match_idx = 0;   //keep track current character in characters to match
        int input_idx = 0;  //index of current char in input
        String capture_so_far = ""; //data for recent capture group
        //iterate through instructions
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i); //get instruction
            //get character to match, if exists
            char to_match = ' ';
            if(match_idx < characters.size()){
                to_match = characters.get(match_idx).charAt(0);
            }
            char current = 0x03;//end of file
            if(input_idx < input.length()){ //is not end
                current = input.substring(input_idx, input_idx+1).toCharArray()[0]; //get current input char
            }

            //special instructions
            if(instruction.equals(Instruction.OPTIONAL)){
                continue;
            }
            if(instruction.equals(Instruction.OPEN_CAPTURE)){
                capture_so_far = ""; //new capture group, clear whatever is there
                continue;
            }
            if(instruction.equals( Instruction.CLOSE_CAPTURE)){
                captures.add(capture_so_far); //finished capture group, add
                continue;
            }
            if(instruction.equals( Instruction.REPEAT)){
                Instruction next_instruction = getNextMatchableInstruction(i);//get the next instruction to match
                //if the next instruction exists, and it does not match, and the last instruction does match
                if( input_idx < input.length() && !matches(current,next_instruction,to_match) && matches(current,instructions.get(i-1),to_match)){
                    i-=2; //repeat last instruction
                }
                continue;
            }


            if(matches(current,instruction,to_match)){ //check for match
                capture_so_far += current; //add to capture
                input_idx++;  //move onto next input
                if(instruction.equals(Instruction.NEXT_CHAR)){
                    match_idx++; //match next character next time
                }
                continue;
            }

            //does not match anything
            if(!hasOptional(i)){
                return null;//not optional
            }
        }
        if(input_idx != input.length() || input.isEmpty()){
           return null; //extra stuff left over in the input, does not match
        }
        return captures;
    }

    //check if a character matches a certain instruction. Also provide next char in matching data.
    private boolean matches(char character, Instruction instruction, char next_match_char){
        if(instruction == null){
            return false;
        }

        switch (instruction){
            case NUMERIC:
                return Character.isDigit(character);
            case WORD:
                return Character.isAlphabetic(character);
            case WHITESPACE:
                return Character.isWhitespace(character);
            case NEXT_CHAR:
               return character == next_match_char;
            case ANY:
                return true;
        }
        return false;
    }

    //check if an instruction is special(non-matchable)
    private static boolean isMatchable(Instruction instruction){
         final Instruction[] special_instructions = {Instruction.WHITESPACE, Instruction.OPEN_CAPTURE, Instruction.CLOSE_CAPTURE, Instruction.REPEAT, Instruction.OPTIONAL};//all non-matchable instructions
        for (Instruction special : special_instructions) {
            if(instruction.equals(special)){
                return false;
            }
        }
        return true;
    }

    //get the next non-special instruction.
    //return null if none found
    private Instruction getNextMatchableInstruction(int current){
        for (int i = current + 1; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            //check if it does not match special types
            if(isMatchable(instruction)){
                return instruction;
            }
        }
        return null; //none found
    }

    //check if there is an optional following the current instruction that corresponds to it
    private boolean hasOptional(int current){
        for (int i = current + 1; i < instructions.size(); i++) { //for the following instructions
            Instruction instruction = instructions.get(i);
            if(isMatchable(instruction)){
                return false; //there is a matchable instruction before, so there is no corresponding optional
            }
            if(instruction.equals(Instruction.OPTIONAL)){
                return true; //optional found
            }

        }
        return false; //no optional found
    }


}
