/*
   $Id: NyxWindowListenerTest.java,v 1.3 2004-03-16 15:04:16 mvdb Exp $
   
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

import java.security.Permission;

import org.xulux.core.ApplicationContext;
import org.xulux.core.ApplicationPart;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the nyxwindowlistener
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxWindowListenerTest.java,v 1.3 2004-03-16 15:04:16 mvdb Exp $
 */
public class NyxWindowListenerTest extends TestCase {

    /**
     * Constructor for NyxWindowListenerTest.
     * @param name the name of the test
     */
    public NyxWindowListenerTest(String name) {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxWindowListenerTest.class);
        return suite;
    }

    /**
     * Test the non implemented methods in the lystener
     */
    public void testNotImplemented() {
        System.out.println("testNotImplemented");
        NyxWindowListener l = new NyxWindowListener();
        l.windowActivated(null);
        l.windowClosed(null);
        l.windowClosing(null);
        l.windowDeiconified(null);
        l.windowIconified(null);
        l.windowOpened(null);
    }

    /**
     * Test deactivation of the window
     */
    public void testWindowDeactivated() {
        System.out.println("testWindowDeactivated");
        MockWidget widget = new MockWidget("mockwidget");
        widget.initialize();
        ApplicationPart part = new ApplicationPart("part");
        part.addWidget(widget);
        widget.setPart(part);
        assertNotNull(widget.getNativeWidget());
        NyxWindowListener l = new NyxWindowListener(widget);
        // don't destroy...
        l.windowDeactivated(null);
        assertNotNull(widget.getNativeWidget());
        l.windowClosing(null);
        l.windowDeactivated(null);
        assertNull(widget.getNativeWidget());
        assertNull(part.getWidget("mockwidget"));
        widget.initialize();
        part.addWidget(widget);
        ApplicationContext.getInstance().register(part, true);
        l.windowClosing(null);
        // disallow System.exit(0)
        System.setSecurityManager(new PreventSystemExit());
        try {
            l.windowDeactivated(null);
            fail("Expected system exit");
        } catch (SecurityException se) {
        }
        System.setSecurityManager(null);
        widget.setRootWidget(false);
        assertFalse(widget.isRootWidget());
        l.windowClosing(null);
        l.windowDeactivated(null);
        assertNull(part.getWidget("mockwidget"));
        assertNull(widget.getNativeWidget());
    }

    /**
     * A securitymanger to prevent a system.exit..
     * Also allows setting of securitymanager to null.
     */
    public class PreventSystemExit extends SecurityManager {
        /**
         * @see java.lang.SecurityManager#checkExit(int)
         */
        public void checkExit(int status) {
            throw new SecurityException("Prevent system exit for test");
        }
        /**
         * @see java.lang.SecurityManager#checkPermission(java.security.Permission, java.lang.Object)
         */
        public void checkPermission(Permission perm, Object context) {
            return;
        }

        /**
         * @see java.lang.SecurityManager#checkPermission(java.security.Permission)
         */
        public void checkPermission(Permission perm) {
            return;
        }

    }
}
