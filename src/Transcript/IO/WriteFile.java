package Transcript.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//a write-only file
public class WriteFile {
    private FileWriter file;
    private BufferedWriter writer;

    //create a new file that can be written to
    public WriteFile(String filename){
        try {
            file = new FileWriter(filename);
            writer = new BufferedWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //write a string to the file and create a new line
    public void writeLine(String line){
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //finalize and save file
    public void close(){
        try {
            writer.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
