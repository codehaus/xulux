/*
 $Id: NyxEventQueue.java,v 1.6 2003-11-25 19:23:54 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.swing.util;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import org.xulux.nyx.gui.NyxListener;

/**
 * The nyx eventqueue..
 * It is there to store events when the queue is on hold
 * (for now no events have been seen yet, but you never know).
 * It also fires of the accepted and completed methods in the listener
 * so we are eg able to process the cancel button without actually
 * doing rule processing..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxEventQueue.java,v 1.6 2003-11-25 19:23:54 mvdb Exp $
 */
public class NyxEventQueue extends EventQueue {

    private ArrayList queue;
    private boolean holdEvents;
    private static NyxEventQueue instance;
    private ArrayList accepted;

    /**
     * Creates an instance of the event queue.
     */
    public NyxEventQueue() {
        super();
        instance = this;
    }

    /**
     *
     * @return the instance created by the SwingToolkit.
     */
    public static NyxEventQueue getInstance() {
        return instance;
    }

    /**
     * @see java.awt.EventQueue#postEvent(java.awt.AWTEvent)
     */
    public void postEvent(AWTEvent theEvent) {
        super.postEvent(theEvent);
    }

    /**
     * @see java.awt.EventQueue#dispatchEvent(java.awt.AWTEvent)
     */
    protected void dispatchEvent(AWTEvent event) {
        if (holdEvents) {
            // queue the event..
            if (queue == null) {
                queue = new ArrayList();
            }
            queue.add(event);
        } else {
            // do a normal dispatch
            super.dispatchEvent(event);
        }
    }

    /**
     * Process the events that are queued when the hold events
     * status changes..
     * This will call super.dispatchEvent to prevent misunderstandig
     * of holdEventsCalled..
     */
    private void processQueuedEvents() {
        if (queue == null || queue.size() == 0) {
            return;
        }
        for (int i = 0; i < queue.size(); i++) {
            super.dispatchEvent((AWTEvent)queue.get(i));
        }
        queue = null;
    }

    /**
     *
     * @return the events that are hold up to now..
     */
    public List getEventHoldQueue() {
        return queue;
    }

    /**
     * Holds the events so we can see what events are
     * @param hold
     */
    public void holdEvents(boolean hold) {

        if (this.holdEvents && !hold) {
            processAccepted();
            processQueuedEvents();
        }
        this.holdEvents = hold;
    }

    private void processAccepted() {
        if (accepted == null || accepted.size() == 0) {
            return;
        }
        for (int i = 0; i < accepted.size(); i++) {
            NyxListener listener = ((NyxListener)accepted.get(i));
            if (listener.accepted(listener.getWidget())) {
                listener.completed();
            }
        }
        accepted = null;
    }



    /**
     * @param listener
     */
    public void holdAccepted(NyxListener listener) {
        if (accepted == null) {
            accepted = new ArrayList();
        }
        accepted.add(listener);
    }

    /**
     * Clears out the accepted queue.
     *
     */
    public void clearAccepted() {
        accepted = null;
    }

    public void clearQueue() {
        queue = null;
    }

}
