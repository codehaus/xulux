/*
   $Id: WidgetFocusListenerTest.java,v 1.1 2004-07-19 22:07:32 mvdb Exp $
   
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

import java.awt.event.FocusEvent;

import javax.swing.JLabel;

import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.util.NyxEventQueue;

import junit.framework.TestCase;

/**
 * Test the widgetFoculistener
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetFocusListenerTest.java,v 1.1 2004-07-19 22:07:32 mvdb Exp $
 */
public class WidgetFocusListenerTest extends TestCase {

    /**
     * Constructor for WidgetFocusListenerTest.
     * @param test the name of the test
     */
    public WidgetFocusListenerTest(String name) {
        super(name);
    }
    
    public void testFocusGained() {
        System.out.println("testFocusGained");
        new NyxEventQueue();
        NyxEventQueue q = NyxEventQueue.getInstance();
        q.holdEvents(true);
        InternalNyxListener il = new InternalNyxListener();
        q.holdAccepted(il);
        WidgetFocusListener listener = new WidgetFocusListener(null);
        // the isProcessing() checks are there to see if the internal listener
        // does a great job and the state is correct after / before testing
        assertEquals(null, listener.getWidget());
        assertEquals(false, WidgetFocusListener.isProcessing());
        il.setProcessing(true);
        assertEquals(true, WidgetFocusListener.isProcessing());
        listener.focusGained(null);
        assertEquals(0, il.acceptedCount);
        il.setProcessing(false);
        assertEquals(false, WidgetFocusListener.isProcessing());
        FocusEvent event = new FocusEvent(new JLabel(), FocusEvent.FOCUS_LAST, false);
        listener.focusGained(event);
        assertEquals(0, il.acceptedCount);
        event = new FocusEvent(new JLabel(), FocusEvent.FOCUS_GAINED, true);
        listener.focusGained(event);
        assertEquals(0, il.acceptedCount);
        event = new FocusEvent(new JLabel(), FocusEvent.FOCUS_GAINED, false);
        listener.focusGained(event);
        assertEquals(1, il.acceptedCount);
        
        
    }
    
    public void testFocusLost() {
        System.out.println("testFocusLost");
        WidgetFocusListener listener = new WidgetFocusListener(null);
        listener.focusLost(null);
    }

    public class InternalNyxListener extends NyxListener {
        public int acceptedCount = 0;
        /**
         * @see org.xulux.gui.NyxListener#accepted(org.xulux.gui.Widget)
         */
        public boolean accepted(Widget widget) {
            acceptedCount++;
            return true;
        }
        /**
         * We shouldn't do anything on completed..
         * @see org.xulux.gui.NyxListener#completed(boolean)
         */
        protected void completed(boolean postOnly) {
        }
        
        public void setProcessing(boolean processing) {
            NyxListener.processing = processing;
        }

    }

}
