/*
 $Id: NewSelectionListenerTest.java,v 1.2 2003-12-18 01:17:35 mvdb Exp $

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
import org.xulux.gui.Widget;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NewSelectionListenerTest.java,v 1.2 2003-12-18 01:17:35 mvdb Exp $
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
