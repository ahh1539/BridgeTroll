
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Simulation of the bridge troll controlling the passage of
 * pedestrians on the bridge crossing the Zyxnine River.
 * <p>
 * This bridge troll will only allow one Woolie on the bridge at
 * a time.  Waiting Woolies are selected at random and allowed to
 * cross when the bridge is free.
 *
 * @author Jim Vallino && Alex Hurley
 */

public class Bridge {

    private static int MAX_ON_BRIDGE = 1;  // Maximum number of Woolies
    // allowed on the bridge


    private int numOnBridge;  // The number of Woolies on the bridge


    /**
     * Constructor for the Bridge class.
     */

    public Bridge() {
        this.numOnBridge = 0;
    }

    /**
     * Request permission to enter the bridge.
     */


    public synchronized void enterBridge(Woolie woolie) {
        // As long as there is no room for the Woolie on the
        // bridge, we have to wait.  Eventually the Woolie on the
        // bridge will cross and I will be notified.
        if (getNumOnBridge() >= 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        addNumOnBridge();
        // There is one more Woolie on the bridge
    }

    /**
     * Notify the bridge troll that a Woolie is leaving the bridge.
     */

    public synchronized void leaveBridge(Woolie woolie) {
        // Someone just left the bridge
        if (getNumOnBridge() > 0) {
            // One less Woolie on the bridge
            subNumOnBridge();
            notify();
            // Wake up all the waiting Woolies and let the race
            // begin.  One of them will get the lock on enterBridge()
            // first.
        }
    }

    public synchronized int getNumOnBridge() {
        return numOnBridge;
    }

    public synchronized void addNumOnBridge() {
        numOnBridge++;
    }

    public synchronized void subNumOnBridge() {
        numOnBridge--;
    }

} // Bridge