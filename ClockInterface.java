import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.*;

public interface ClockInterface extends Remote{
    /**
    * @ return this clock’s estimate of the time in milliseconds since January 1, 1970
    */
    public long getTime() throws RemoteException;
    /**
    *
    * @ return a pair of timed Values, when the message was received and when it was sent
    * (time in milliseconds since January 1, 1970)
    */
    public ClockMessage getTaggedTime() throws RemoteException;
    /**
    * @return the "ground truth" time in milliseconds since January 1, 1970
    */
    public long getGroundTruth() throws RemoteException;

    public void setClockObj(Duration diff) throws RemoteException;

    public void resetClockObj() throws RemoteException;
}