import java.io.*;

public class ClockMessage implements Serializable{
    public long in; // when the request was received
    public long time; // the time at the server
    public long out; // when the reply was sent
    public ClockMessage(long in, long time, long out)
    {
        this.in = in;
        this.time = time;
        this.out = out;
    }
}