import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.rmi.*;
import java.rmi.server.*;

public class LocalClock extends TimerTask {
    private long time; //current time in microseconds since the epoch
    private long tick = 100; //how much the clock ticks per interrupt
    
    //Create a new local clock 
    // initializes local clock to the instant (in milliseconds) the clients local clock started running (i.e., when object was created)
    public LocalClock(){
        time = Instant.now().toEpochMilli();
    }
    //access the clock’s time
    //return this clock’s estimate of the time in milliseconds since January 1, 1970
    public synchronized long getTime(){
        return this.time;
    }
    //set the clock’s time
    //param timeInMS: the time in milliseconds since January 1, 1970
    public synchronized void setTime(long timeInMS){
        this.time = timeInMS;
    }
    
    //param t: the tick size used to increment the time
    public synchronized void setTick(long t){
        this.tick = t;
    }
    
    //return: the tick size used to increment the time
    public synchronized long getTick(){
        return this.tick;
    }

    //@override
    //Time parent class's run function is used to schedule tasks to run periodically at specified intervals
    // this method is overridden to define was task has to be run periodically
    // here, we increment the local clocks time =local clock + tick
    // this method is scheduled to run as per the tick value.
    public void run() {
        
        this.setTime(this.time+tick); //udpdating the local clock time
        // try{
        //     ClockInterface cs= (ClockInterface) Naming.lookup("ClockServer");
        //     long serverTime = cs.getTime();
        // }
        // catch(Exception e){
        //     System.out.println("Not bound" + e);
        // }
    }
    
    //Return a string represetation of the clock including the local time in microseconds and the tick value
    public String toString(){
        return "Clock time = " + this.time +"\nTick Time = " + this.tick;
    }
}
