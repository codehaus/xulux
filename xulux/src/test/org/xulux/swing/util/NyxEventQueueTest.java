/*
   $Id: NyxEventQueueTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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

import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Testcase for the nyxeventqueue
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxEventQueueTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
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
         * @param widget the widget
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
         * @param succes true or false
         */
        public void setSuccessCompleted(boolean succes) {
            this.succesCompleted = succes;
        }
    }
}
