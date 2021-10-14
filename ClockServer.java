import java.time.*;
import java.rmi.*;
import java.io.*; 
import java.rmi.server.*;

public class ClockServer extends UnicastRemoteObject implements ClockInterface{
    // Instant instant;
    Clock clientClock;
    public ClockServer() throws RemoteException{
        // instant = Instant.now();
        clientClock = Clock.systemUTC();
        try{
            String objPath = "ClockServer";
            // ClockServer stub = (ClockServer) UnicastRemoteObject.exportObject(this,0);
            Naming.bind(objPath, this);
            System.out.println("Remote Server is running now.");
        }
        catch(Exception e){
            System.out.println("Exception" + e);
        }
    }
    
    public long getTime() throws RemoteException{
        // return Instant.now().toEpochMilli();
        return clientClock.instant().toEpochMilli();
    }
    /**
    *
    * @ return a pair of timed Values, when the message was received and when it was sent
    * (time in milliseconds since January 1, 1970)
    */
    public ClockMessage getTaggedTime() throws RemoteException{
        // long in = Instant.now().toEpochMilli();
        // long time = Instant.now().toEpochMilli();
        long in = clientClock.instant().toEpochMilli();
        long time = clientClock.instant().toEpochMilli();
        return new ClockMessage(in,time,clientClock.instant().toEpochMilli());
    }
    /**
    * @return the "ground truth" time in milliseconds since January 1, 1970
    */  
    public long getGroundTruth() throws RemoteException{
        return Instant.now().toEpochMilli();
    }

    // public Instant getInstantObj() throws RemoteException{
    //     return this.instant;
    // }

    public void setClockObj(Duration diff) throws RemoteException{
        // this.instant = newInstant;
        clientClock = clientClock.offset(clientClock, diff);
    }

    public void resetClockObj() throws RemoteException{
        this.clientClock = Clock.systemUTC();
    }

    public static void main(String args[]) throws RemoteException{
        ClockServer clockServer = new ClockServer();
    }
}