import java.util.Timer;
import java.util.TimerTask;
import java.rmi.*;
import java.io.*; 
import java.time.*;
import java.lang.Math;

public class ImprovedLocalClock extends TimerTask {
    Timer timer, improve_timer;
    LocalClock clock;
    int ctr, arr_ctr, algo_ctr;
    LocalClockEvaluator evaluate_obj;

    public ImprovedLocalClock(){
        timer = new Timer(); // Timer object to schedule update of LocalCLock
        improve_timer = new Timer(); // Timer object to schedule synchronization algorithm
        
        clock = new LocalClock(); // initialise LocalClock
        ctr = 0; // counter for number of synchronization iterations
        arr_ctr = 0; // array counter for index of error array
        algo_ctr = 0; // counter to check which algorithm should be executed

        evaluate_obj = new LocalClockEvaluator(); // evaluates errors periodically and stores them

        timer.schedule(clock, clock.getTick(), clock.getTick()); // Schedule update of LocalCLock at period = tick
        improve_timer.schedule(this, 500, 500); // Schedule synchronization algorithm at every 500ms interval.
    }

    //scheduled function, runs every 500ms and executes algorithm based on algo_ctr
    public void run() { 
        if(algo_ctr ==0){
            this.cristian();
        }
        else if(algo_ctr ==1){
            this.berkeley();
        }
    }

    
    //Cristian Synchronization algorithm implementation
    public void cristian(){

        long old_time = this.clock.getTime(); // gets local clock time that needs to be adjusted
        if(ctr<200){ // synchronization run 200 times
            try{
                ClockInterface cs = (ClockInterface) Naming.lookup("ClockServer"); // getting Clock Server RMI object
                
                // get a pair of timed Values, when the message was received and when it was sent 
                ClockMessage taggedTime = cs.getTaggedTime(); 
                long start = taggedTime.in;
                long end = taggedTime.out;
                long serverTime = taggedTime.time;

                long rtt = (end-start)/2; //calculate round trip time
                long updatedTime = serverTime + rtt; // new Local Clock Time
                long diff = updatedTime - old_time; // the difference between old and new time

                this.clock.setTime(old_time + diff); // set the time to new time based on algorithm
                
                long error = old_time-serverTime; // error from server time at start of synchronization
                evaluate_obj.cristian_error[arr_ctr] = Math.abs(error);
                evaluate_obj.global_error_cristian[arr_ctr++] = Math.abs(old_time - cs.getGroundTruth()); // error from ground truth
                

                System.out.println("Cristian : Local clock time- " + old_time  + ", Server time- " + serverTime + ", New time- " + (old_time+diff) + ", Error- " + error);
                ctr++;
                if(ctr==200){ // resets Clock servers clock to ground truth UTC time before next algorithm is run
                    System.out.println("Reset clock");
                    cs.resetClockObj();
                }
            }

            catch(Exception e){
                System.out.println("" + e);
            }
        }
        else{
            this.algo_ctr++; //move to next algorithm
            this.arr_ctr = 0; // reset array index counter
            this.ctr = 0; // reset iteration counter
            this.evaluate_obj.printCristianError(); // write errors to output file
        }
    }

    //Berkeley Synchronization algorithm implementation
    public void berkeley(){
        long old_time = this.clock.getTime();
        if(ctr<200){
            try{
                ClockInterface cs= (ClockInterface) Naming.lookup("ClockServer"); // getting Clock Server RMI object
                
                long serverTime = cs.getTime(); //get the server time 
                long average = (serverTime + old_time)/2; // Calculate the average of server time and local clock time
                this.clock.setTime(average); //set the time to average time based on the algorithm

                cs.setClockObj(Duration.ofMillis(average - serverTime)); //set the time of clock server = average time

                long error = old_time-serverTime; // error from server time at start of synchronization
                evaluate_obj.berkeley_error[arr_ctr] = Math.abs(error);
                evaluate_obj.global_error_berkeley[arr_ctr++] = Math.abs(old_time - cs.getGroundTruth()); // error from ground truth
                System.out.println("Berkeley : Local clock time- " + old_time +  ", Server time- " + serverTime + ", New time: " + (average) + ", Error- " + error);
                ctr++;
                
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else{
            this.evaluate_obj.printBerkelyError(); //To print the Berkeley errors
            this.timer.cancel(); // To stop the schedule updtae of local Clock
            this.improve_timer.cancel(); // To stop the schedule synchronization algorithm
        }
    }

    //Creates and starts running the Improved Local clock
    public static void main(String args[]){
        new ImprovedLocalClock();
        System.out.println("Task scheduled.");
    }
}