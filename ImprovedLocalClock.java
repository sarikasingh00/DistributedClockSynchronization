import java.util.Timer;
import java.util.TimerTask;
import java.rmi.*;
import java.io.*; 
import java.time.*;

public class ImprovedLocalClock extends TimerTask {
    Timer timer, improve_timer;
    LocalClock clock;
    int ctr, arr_ctr, algo_ctr;
    long sum;
    LocalClockEvaluator evaluate_obj;
    // int arr[];

    public ImprovedLocalClock(){
        timer = new Timer();
        improve_timer = new Timer();
        clock = new LocalClock();
        ctr = 0;
        arr_ctr = 0;
        algo_ctr = 0;

        evaluate_obj = new LocalClockEvaluator();
        // sum = 0;
        // arr = new int[20];
        
        timer.schedule(clock, clock.getTick(), clock.getTick());
        // for(int i=0; i<3; i++){
        improve_timer.schedule(this, 500, 500);
        // }
    }

    public void run() {
        if(algo_ctr ==0){
            this.cristian();
        }
        else if(algo_ctr ==1){
            this.berkeley();
        }
        else{
            // this.arr_ctr = 0;
            // this.ctr = 0;
            this.ntp();
        }
    }


    public void cristian(){
        long old_time = this.clock.getTime();
        // ClockInterface cs;
        // long exec_start = Instant.now().toEpochMilli();
        if(ctr<200){
            try{
                ClockInterface cs = (ClockInterface) Naming.lookup("ClockServer");
                // long start = this.clock.getTime(); //t1
                
                ClockMessage taggedTime = cs.getTaggedTime();
                long start = taggedTime.in;
                long end = taggedTime.out;
                long serverTime = taggedTime.time;
                // long end = Instant.now().toEpochMilli();

                long rtt = (end-start)/2;
                long updatedTime = serverTime + rtt;
                long diff = updatedTime - old_time;

                this.clock.setTime(old_time + diff);
                
                // long exec_end = Instant.now().toEpochMilli();
                // long exec_time = exec_end - exec_start;
                // sum += exec_time;
                long error = old_time-serverTime;
                evaluate_obj.cristian_error[arr_ctr++] = error;

                System.out.println("Fixing local clock " + old_time + " new time " + (old_time+diff) + " server time " + serverTime + " Error " + error);
                ctr++;
                if(ctr>=200){
                    cs.resetClockObj();
                }
            }

            catch(Exception e){
                System.out.println("" + e);
            }
        }
        else{
            this.algo_ctr++;
            this.arr_ctr = 0;
            this.ctr = 0;
            // this.timer.cancel();
            // this.improve_timer.cancel();
            // for(int i=0; i<3000;i++){
            //     System.out.print(this.evaluate_obj.cristian_error[i] + ",");
            // }
            this.evaluate_obj.print_error();
        }
    }

    public void berkeley(){
        System.out.println("Berkeley");
         long old_time = this.clock.getTime();
        // long exec_start = Instant.now().toEpochMilli();
        if(ctr<200){
            try{
                ClockInterface cs= (ClockInterface) Naming.lookup("ClockServer");
                // ClockServer cs = (ClockServer) cs;
                long serverTime = cs.getTime();
                long average = (serverTime + old_time)/2;
                this.clock.setTime(average);

                
                // System.out.println("old  cs time " + serverTime);
                cs.setClockObj(Duration.ofMillis(average - serverTime));
                // cs.setInstantObj();
                // System.out.println("New cs time " + cs.getTime());

                long error = old_time-serverTime;
                evaluate_obj.berkeley_error[arr_ctr++] = error;
                ctr++;
                
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else{
            this.algo_ctr++;
            // this.timer.cancel();
            // this.improve_timer.cancel();
            for(int i=0; i<3000;i++){
                System.out.print(this.evaluate_obj.berkeley_error[i] + ",");
            }
            // this.evaluate_obj.print_error();
        }
    }

    public void ntp(){
        System.out.println("NTP");
    }

    public static void main(String args[]){
        new ImprovedLocalClock();
        System.out.println("Task scheduled.");
    }
}