/*
   $Id: DefaultButtonChangeListenerTest.java,v 1.1 2004-05-04 12:04:42 mvdb Exp $
   
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

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;

import junit.framework.TestCase;

/**
 * Test the defaultbutton change listener.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultButtonChangeListenerTest.java,v 1.1 2004-05-04 12:04:42 mvdb Exp $
 */
public class DefaultButtonChangeListenerTest extends TestCase {

    /**
     * Constructor for DefaultButtonChangeListenerTest.
     * @param name the name of the test
     */
    public DefaultButtonChangeListenerTest(String name) {
        super(name);
    }
    
    public void testListener() {
        System.out.println("testListener");
        DefaultButtonChangeListener l = new DefaultButtonChangeListener();
        ChangeEvent nullEvent = new ChangeEvent("");
        l.stateChanged(nullEvent);
        MockButton button = new MockButton();
        ChangeEvent event = new ChangeEvent(button);
        l.stateChanged(event);
        assertEquals(0, button.requestFocusCount);
        button.getModel().setArmed(true);
        button.getModel().setPressed(true);
        l.stateChanged(event);
        assertEquals(1, button.requestFocusCount);
    }
    
    public class MockButton extends JButton {
        public int requestFocusCount;
        public void requestFocus() {
          requestFocusCount++;
        }
    }

}
