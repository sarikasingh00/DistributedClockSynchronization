import java.time.*;
import java.rmi.*;
import java.io.*; 
import java.rmi.server.*;

public class ClockServer extends UnicastRemoteObject implements ClockInterface{
    
    Clock clientClock; // Clock object that is used by the server to return the time to its clients.
    
    public ClockServer() throws RemoteException{
        clientClock = Clock.systemUTC(); // initializing to UTC system time

        try{ //creating clock server binded to path "ClockServer"
            String objPath = "ClockServer"; //name of server location
            Naming.bind(objPath, this); //binding to name on rmiregistry
            System.out.println("Remote Server is running now."); 
        }
        catch(Exception e){
            System.out.println("Exception" + e);
        }
    }
    
    // returns servers time in milliseconds
    public long getTime() throws RemoteException{
        return clientClock.instant().toEpochMilli();
    }
    
    //return a pair of timed Values, when the message was received and when it was sent (time in milliseconds since January 1, 1970)
    public ClockMessage getTaggedTime() throws RemoteException{
        long in = clientClock.instant().toEpochMilli();
        long time = clientClock.instant().toEpochMilli();
        return new ClockMessage(in,time,clientClock.instant().toEpochMilli());
    }

    //return the "ground truth" time in milliseconds since January 1, 1970  
    public long getGroundTruth() throws RemoteException{
        return Instant.now().toEpochMilli();
    }

    // returns the clock object of the server according to synchronization algorithm adjustment
    public void setClockObj(Duration diff) throws RemoteException{
        clientClock = clientClock.offset(clientClock, diff);
    }

    // resets the clock object of the server that is used to provide ground truth and adjusted server timings
    public void resetClockObj() throws RemoteException{
        this.clientClock = Clock.systemUTC();
    }

    // creates and starts running the remote clock server
    public static void main(String args[]) throws RemoteException{
        ClockServer clockServer = new ClockServer();
    }
}