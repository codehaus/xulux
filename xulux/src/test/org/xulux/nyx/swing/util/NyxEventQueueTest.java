/*
 $Id: NyxEventQueueTest.java,v 1.2 2003-12-12 02:47:33 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
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

import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Testcase for the nyxeventqueue
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxEventQueueTest.java,v 1.2 2003-12-12 02:47:33 mvdb Exp $
 */
public class NyxEventQueueTest extends TestCase {

    /**
     * Constructor for NyxEventQueueTest.
     * @param name the name of the test
     */
    public NyxEventQueueTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxEventQueueTest.class);
        return suite;
    }

    /**
     * Test the nyx eventqueue
     */
    public void testNyxEventQueue() {
        System.out.println("NyxEventQueue");
        // by default the NyxEventQueue is null
        assertNull(NyxEventQueue.getInstance());
        NyxEventQueue q = new NyxEventQueue();
        assertEquals(q, NyxEventQueue.getInstance());
        q.holdEvents(false);
        q.holdEvents(true);
        q.holdEvents(false);
        q.clearAccepted();
        InternalListener listener = new InternalListener(null);
        q.holdAccepted(null);
        q.processAccepted();
        q.holdAccepted(listener);
        q.processAccepted();
        assertEquals(1, listener.getCompletedCount());
        assertEquals(1, listener.getAcceptedCount());
        listener.resetCount();
        // just test if the internallistener is actually listening
        // to the restCount :)
        assertEquals(0, listener.getCompletedCount());
        assertEquals(0, listener.getAcceptedCount());
        q.holdEvents(true);
        q.holdAccepted(listener);
        q.holdAccepted(listener);
        q.holdAccepted(listener);
        q.holdEvents(false);
        assertEquals(3, listener.getCompletedCount());
        assertEquals(3, listener.getAcceptedCount());
        listener.resetCount();
        // test when accepted doesn't return true, it shouldn't process
        // completed at all
        InternalListener listener2 = new InternalListener(null);
        listener2.setSuccessCompleted(false);
        q.holdEvents(true);
        q.holdAccepted(listener2);
        q.holdEvents(false);
        assertEquals(1, listener2.getAcceptedCount());
        assertEquals(0, listener2.getCompletedCount());
    }

    /**
     * An internal test listener..
     */
    public class InternalListener extends NyxListener {
        /**
         * The accepted counter
         */
        private int acceptedCount;
        /**
         * The completedCounter
         */
        private int completedCount;
        
        /**
         * Is the completed a success ? 
         */
        private boolean succesCompleted = true;
        
        /**
         * @param widget the widget the listener is for
         */
        public InternalListener(Widget widget) {
            super(widget);
        }
        
        /**
         * Override completed, since we don't want to actually
         * complete something, just to see if it gets called..
         * @see org.xulux.nyx.gui.NyxListener#completed()
         */
        public void completed() {
            completedCount++;
        }

        /**
         * Override accepted, since we don't want to accept anything
         * @return always true;
         */
        public boolean accepted(Widget widget) {
            acceptedCount++;
            return succesCompleted;
        }
        /**
         * @return the accepted count..
         */
        public int getAcceptedCount() {
            return acceptedCount;
        }

        /**
         * @return the completed count..
         */
        public int getCompletedCount() {
            return completedCount;
        }

        /**
         * Reset the count to zero
         */        
        public void resetCount() {
            acceptedCount = 0;
            completedCount = 0;
        }
        
        /**
         * @param success true or false
         */
        public void setSuccessCompleted(boolean succes) {
            this.succesCompleted = succes;
        }
    }
}
