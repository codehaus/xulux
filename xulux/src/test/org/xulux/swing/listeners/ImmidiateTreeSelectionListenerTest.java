/*
   $Id: ImmidiateTreeSelectionListenerTest.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
   
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

import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.rules.Rule;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImmidiateTreeSelectionListenerTest.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
 */
public class ImmidiateTreeSelectionListenerTest extends TestCase {

    /**
     * Constructor for NewSelectionListenerTest.
     * @param name the name of the test
     */
    public ImmidiateTreeSelectionListenerTest(String name) {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ImmidiateTreeSelectionListenerTest.class);
        return suite;
    }

    /**
     * Test the newselection listener
     */
    public void testNewSelection() {
        System.out.println("testNewSelection");
        MockWidget widget = new MockWidget("mockwidget");
        ApplicationPart part = new ApplicationPart();
        InternalRule rule = new InternalRule();
        widget.registerRule(rule);
        widget.setPart(part);
        ImmidiateTreeSelectionListener listener = new ImmidiateTreeSelectionListener(null);
        listener.valueChanged((ListSelectionEvent) null);
        listener.valueChanged((TreeSelectionEvent) null);
        TreeSelectionEvent treeEvent = new TreeSelectionEvent(widget, null, true, null, null);
        ListSelectionEvent listEvent = new ListSelectionEvent(widget, 0, 0, false);
        listener.valueChanged(treeEvent);
        listener.valueChanged(listEvent);
        assertEquals(0, rule.getCallCount());
        listener = new ImmidiateTreeSelectionListener(widget);
        listener.valueChanged((ListSelectionEvent) null);
        listener.valueChanged((TreeSelectionEvent) null);
        assertEquals(0, rule.getCallCount());
        listener.valueChanged(treeEvent);
        assertEquals(1, rule.getCallCount());
        listener.valueChanged(listEvent);
        assertEquals(2, rule.getCallCount());
        // don't call the execute method when there is no change..
        treeEvent = new TreeSelectionEvent(widget, null, false, null, null);
        listener.valueChanged(treeEvent);
        assertEquals(2, rule.getCallCount());
    }

    /**
     * Fake some methods..
     */
    public class InternalRule extends Rule {
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
         * @see org.xulux.nyx.rules.Rule#pre(org.xulux.nyx.context.PartRequest)
         */
        public void pre(PartRequest request) {
        }
        /**
         * @see org.xulux.nyx.rules.Rule#post(org.xulux.nyx.context.PartRequest)
         */
        public void post(PartRequest request) {
        }
        /**
         * @see org.xulux.nyx.rules.IRule#execute(org.xulux.nyx.context.PartRequest)
         */
        public void execute(PartRequest request) {
            callCount++;
        }

    }
}
