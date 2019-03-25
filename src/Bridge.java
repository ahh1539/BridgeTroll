/*
 * Bridge.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log$
 */

import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 * Simulation of the bridge troll controlling the passage of
 * pedestrians on the bridge crossing the Zyxnine River.
 *
 * This bridge troll will only allow one Woolie on the bridge at
 * a time.  Waiting Woolies are selected at random and allowed to
 * cross when the bridge is free.
 *
 * @author	Jim Vallino
 */

public class Bridge {

    private static int MAX_ON_BRIDGE = 1;  // Maximum number of Woolies
    // allowed on the bridge


    private int numOnBridge;  // The number of Woolies on the bridge

    private Lock lock;

    /**
     * Constructor for the Bridge class.
     */

    public Bridge() {
        numOnBridge = 0;
    }

    /**
     *  Request permission to enter the bridge.
     */

    public void enterBridge(Woolie woolie) {
        // As long as there is no room for the Woolie on the
        // bridge, we have to wait.  Eventually the Woolie on the
        // bridge will cross and I will be notified.
        if (numOnBridge >= 1){
            try {
                woolie.wait();
            } catch (InterruptedException e){}
        }
        else {
            woolie.run();
        }


        // There is one more Woolie on the bridge
        numOnBridge++;

    }

    /**
     *   Notify the bridge troll that a Woolie is leaving the bridge.
     */

    public void leaveBridge(Woolie woolie) {
        // Someone just left the bridge

        if ( numOnBridge > 0 ) {
            // One less Woolie on the bridge
            numOnBridge--;
        if (numOnBridge == 0){
            woolie.notify();
        }
            // Wake up all the waiting Woolies and let the race
            // begin.  One of them will get the lock on enterBridge()
            // first.

        }
    }

} // Bridge