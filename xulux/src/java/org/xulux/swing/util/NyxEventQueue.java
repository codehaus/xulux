/*
   $Id: NyxEventQueue.java,v 1.7 2004-08-31 23:00:24 mvdb Exp $
   
   Copyright 2002-2004 The Xulux Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.xulux.swing.util;

import java.util.ArrayList;

import org.xulux.gui.NyxListener;

/**
 * The nyx eventqueue..
 * It is there to store events when the queue is on hold
 * (for now no events have been seen yet, but you never know).
 * It also fires of the accepted and completed methods in the listener
 * so we are eg able to process the cancel button without actually
 * doing rule processing..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxEventQueue.java,v 1.7 2004-08-31 23:00:24 mvdb Exp $
 */
public class NyxEventQueue { // extends EventQueue {

    /**
     * The queue
     */
    private ArrayList queue;
    /**
     * Hold events ?
     */
    private boolean holdEvents;
    /**
     * The queue instance
     */
    private static NyxEventQueue instance;
    /**
     * The accepted queue
     */
    private ArrayList accepted;
    /**
     * Is currently accepted being processed ?
     */
    private boolean processingAccepted;
    /**
     * Should we currently reset the queue ?
     */
    private boolean shouldResetQueue;
    
    /**
     * Creates an instance of the event queue.
     */
    public NyxEventQueue() {
        super();
        instance = this;
    }

    /**
     * @return the instance created by the SwingToolkit.
     */
    public static NyxEventQueue getInstance() {
        return instance;
    }

    /**
     * Holds or frees up the eventqueue
     * @param hold if true it will start holding events, false it will
     *         process the events that are in the queue
     */
    public void holdEvents(boolean hold) {
        if (this.holdEvents && !hold) {
            processAccepted();
        }
        this.holdEvents = hold;
    }

    /**
     * Process the accepted event queue
     */
    protected void processAccepted() {
        if (accepted == null || accepted.size() == 0) {
            return;
        }
        if (processingAccepted) {
            return;
        }
        processingAccepted = true;
        for (int i = 0; i < accepted.size(); i++) {
            NyxListener listener = ((NyxListener) accepted.get(i));
            if (listener != null) {
                try {
	                if (listener.accepted(listener.getWidget())) {
	                    listener.completed();
	                }
                } catch(Exception e) {
                    // todo : need to reproduce what's going on here!
                    e.printStackTrace();
                }
            }
            if (shouldResetQueue) {
                new Exception().printStackTrace();
            }
        }
        processingAccepted = false;
        clearAccepted();
        shouldResetQueue = false;
    }

    /**
     * @param listener the listener to hold.
     */
    public void holdAccepted(NyxListener listener) {
        if (listener == null) {
            return;
        }
        if (accepted == null) {
            accepted = new ArrayList();
        }
        accepted.add(listener);
    }

    /**
     * Clears out the accepted queue.
     */
    public void clearAccepted() {
        if (processingAccepted) {
            shouldResetQueue = true;
        } else {
          accepted = null;
        }
    }

}
