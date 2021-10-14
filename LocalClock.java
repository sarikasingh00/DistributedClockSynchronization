import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.rmi.*;
import java.rmi.server.*;

public class LocalClock extends TimerTask {
    /**
    * current time in microseconds since the epoch
    */
    private long time;
    /**
    * how much the clock ticks per interrupt
    */
    private long tick = 100;
    /**
    * Create a new local clock
    */
    // long arr[];
    // int ctr;
    
    public LocalClock(){
        time = Instant.now().toEpochMilli();
        // arr = new long[3000];
        
        // ctr = 0;
    }
    /**
    * access the clock’s time
    * @ return this clock’s estimate of the time in milliseconds since January 1, 1970
    */
    public synchronized long getTime(){
        return this.time;
    }
    /**
    * set the clock’s time
    * @param timeInMS: the time in milliseconds since January 1, 1970
    */
    public synchronized void setTime(long timeInMS){
        this.time = timeInMS;
    }
    /**
    * @param t: the tick size used to increment the time
    */
    public synchronized void setTick(long t){
        this.tick = t;
    }
    /**
    * @return: the tick size used to increment the time
    */
    public synchronized long getTick(){
        return this.tick;
    }

     public void run() {
        this.setTime(this.time+tick);
        try{
            ClockInterface cs= (ClockInterface) Naming.lookup("ClockServer");
            long serverTime = cs.getTime();
            // long error = this.getTime()-serverTime;
            System.out.println(this.getTime() + " " + serverTime);
            // this.arr[ctr++] = error;
            // evaluate_obj.cristian_error[ctr++] = error;
        }
        catch(Exception e){
            System.out.println("Not bound" + e);
        }
    }
    /**
    * Return a string represetation of the clock including the local time in
    * microseconds and the tick value
    */
    public String toString(){
        return "Clock time = " + this.time +"\nTick Time = " + this.tick;
    }

}
