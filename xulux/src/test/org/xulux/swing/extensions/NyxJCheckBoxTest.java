/*
 $Id: NyxJCheckBoxTest.java,v 1.1 2003-12-18 00:17:35 mvdb Exp $

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
package org.xulux.swing.extensions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the NyxJCheckBox where possible
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJCheckBoxTest.java,v 1.1 2003-12-18 00:17:35 mvdb Exp $
 */
public class NyxJCheckBoxTest extends TestCase {

    /**
     * Constructor for NyxJCheckBoxTest.
     * @param name the name of the test
     */
    public NyxJCheckBoxTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxJCheckBoxTest.class);
        return suite;
    }

    /**
     * Test the realbackground
     *
     */
    public void testRealBackground() {
        System.out.println("testRealBackground");
        NyxJCheckBox check = new NyxJCheckBox();
        check.setRealBackground(Color.cyan);
        assertEquals(Color.cyan, check.getRealBackground());
    }

    /**
     * Test the iconstub
     */
    public void testIconStub() {
        System.out.println("testIconStub");
        NyxJCheckBox check = new NyxJCheckBox();
        check.setRealBackground(Color.cyan);
        NyxJCheckBox.IconStub stub = check.new IconStub(null);
        assertEquals(0, stub.getIconHeight());
        assertEquals(0, stub.getIconWidth());
        TestIcon icon = new TestIcon();
        stub = check.new IconStub(icon);
        FakeGraphics g = new FakeGraphics();
        stub.paintIcon(check, g, 0, 0);
        assertEquals(Color.cyan, g.getColor());
        g = new FakeGraphics();
        check.setRealBackground(null);
        stub.paintIcon(check, g, 0, 0);
        assertNull(g.getColor());
    }

    /**
     * Test the setting of icons
     */
    public void testSetIcons() {
        System.out.println("testSetIcons");
        NyxJCheckBox check = new NyxJCheckBox();
        Icon icon = new TestIcon();
        Icon iconSelected = new TestIcon();
        check.setIcon(icon);
        check.setSelectedIcon(iconSelected);
        assertNotSame(icon, check.getIcon());
        assertNotSame(iconSelected, check.getSelectedIcon());
        assertEquals(NyxJCheckBox.IconStub.class, check.getIcon().getClass());
        assertEquals(NyxJCheckBox.IconStub.class, check.getSelectedIcon().getClass());
    }
    /**
     * A test icon stub, so we can assert on what happens.
     */
    public class TestIcon implements Icon {

        /**
         * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
        }

        /**
         * @see javax.swing.Icon#getIconWidth()
         */
        public int getIconWidth() {
            return 10;
        }

        /**
         * @see javax.swing.Icon#getIconHeight()
         */
        public int getIconHeight() {
            return 10;
        }
    }
}
