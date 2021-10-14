import java.io.*;
import java.util.*;

public class LocalClockEvaluator{
    long cristian_error[], berkeley_error[], ntp_error[];
    public LocalClockEvaluator(){
        cristian_error = new long[200];
        berkeley_error = new long[200];
    }

    public void printCristianError() throws IOException{
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter("cristian.txt"));
        for (int i = 0; i < cristian_error.length; i++) {
            outputWriter.write(cristian_error[i]+",");
        }
        outputWriter.flush();  
        outputWriter.close();
    }
    
    public void printBerkelyError(){
        try{
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("berkeley.txt"));
            for (int i = 0; i < berkeley_error.length; i++) {
                outputWriter.write(berkeley_error[i]+",");
            }
            outputWriter.flush();  
            outputWriter.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}