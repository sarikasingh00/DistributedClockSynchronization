import java.io.*;
import java.util.*;

public class LocalClockEvaluator{
    // arrays to keep track of time error, as per the algorithms used
    long cristian_error[], berkeley_error[], global_error_cristian[], global_error_berkeley[];
    
    //Constructor to instantiate LocalClockEvaluator object
    public LocalClockEvaluator(){
        cristian_error = new long[200]; //tracks client time - server time for cristian algorithm
        berkeley_error = new long[200]; //tracks client time - server time for berekeley algorithm
        global_error_cristian = new long[200]; //tracks client time - ground truth time for cristian algorithm
        global_error_berkeley = new long[200]; //tracks client time - ground truth time for berkeley algorithm
    }

    public void printCristianError(){
        try{
            // error to be written to output file
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("cristian.txt"));
            for (int i = 0; i < cristian_error.length; i++) {
                outputWriter.write(cristian_error[i]+",");
            }
            outputWriter.flush();  
            outputWriter.close();
            // global time error to be written to output file
            outputWriter = new BufferedWriter(new FileWriter("cristian_global.txt"));
            for (int i = 0; i < cristian_error.length; i++) {
                outputWriter.write(global_error_cristian[i]+",");
            }
            outputWriter.flush();  
            outputWriter.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void printBerkelyError(){
        try{
            // error to be written to output file
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("berkeley.txt"));
            for (int i = 0; i < berkeley_error.length; i++) {
                outputWriter.write(berkeley_error[i]+",");
            }
            outputWriter.flush();  
            outputWriter.close();
            // global time error to be written to output file
            outputWriter = new BufferedWriter(new FileWriter("berkeley_global.txt")); 
            for (int i = 0; i < cristian_error.length; i++) {
                outputWriter.write(global_error_berkeley[i]+",");
            }
            outputWriter.flush();  
            outputWriter.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}