/*
 $Id: NyxWindowListenerTest.java,v 1.1 2003-12-18 00:17:37 mvdb Exp $

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

import java.security.Permission;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the nyxwindowlistener
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxWindowListenerTest.java,v 1.1 2003-12-18 00:17:37 mvdb Exp $
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
