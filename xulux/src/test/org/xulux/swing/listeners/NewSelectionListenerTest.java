/*
   $Id: NewSelectionListenerTest.java,v 1.3 2004-01-28 15:22:02 mvdb Exp $
   
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
package org.xulux.swing.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;

import org.xulux.context.ApplicationPart;
import org.xulux.gui.Widget;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NewSelectionListenerTest.java,v 1.3 2004-01-28 15:22:02 mvdb Exp $
 */
public class NewSelectionListenerTest extends TestCase {

    /**
     * Constructor for NewSelectionListenerTest.
     * @param name the name of the test
     */
    public NewSelectionListenerTest(String name) {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NewSelectionListenerTest.class);
        return suite;
    }

    /**
     * Test the newselection listener
     */
    public void testNewSelection() {
        System.out.println("testNewSelection");
        MockWidget widget = new MockWidget("mockwidget");
        InternalPart part = new InternalPart();
        widget.setPart(part);
        NewSelectionListener listener = new NewSelectionListener(null);
        listener.valueChanged((ListSelectionEvent) null);
        listener.valueChanged((TreeSelectionEvent) null);
        assertEquals(0, part.getCallCount());
        listener = new NewSelectionListener(widget);
        listener.valueChanged((ListSelectionEvent) null);
        listener.valueChanged((TreeSelectionEvent) null);
        assertEquals(2, part.getCallCount());
    }

    /**
     * Fake some methods..
     */
    public class InternalPart extends ApplicationPart {
        /**
         * The call count
         */
        private int callCount;

        /**
         * @return the callcount
         */
        public int getCallCount() {
            return callCount;
        }
        /**
         * Reset the callcount
         */
        public void resetCallCount() {
            callCount = 0;
        }
        /**
         * @see org.xulux.nyx.context.ApplicationPart#refreshWidgets(org.xulux.nyx.gui.Widget)
         */
        public void refreshWidgets(Widget caller) {
            callCount++;
        }

    }
}
