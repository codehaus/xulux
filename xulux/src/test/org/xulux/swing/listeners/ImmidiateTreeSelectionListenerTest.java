/*
 $Id: ImmidiateTreeSelectionListenerTest.java,v 1.2 2003-12-18 01:17:35 mvdb Exp $

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
package org.xulux.swing.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;

import org.xulux.context.ApplicationPart;
import org.xulux.context.PartRequest;
import org.xulux.rules.Rule;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImmidiateTreeSelectionListenerTest.java,v 1.2 2003-12-18 01:17:35 mvdb Exp $
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
