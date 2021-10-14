import java.util.Timer;
import java.util.TimerTask;

public class LocalClockRunner {
    Timer timer;
    LocalClock clock;

    public LocalClockRunner(){
        timer = new Timer();
        clock = new LocalClock();
        
        timer.schedule(clock, clock.getTick(), clock.getTick());
    }
    public static void main(String args[]){
        new LocalClockRunner();
        System.out.println("Task scheduled.");
    }
}