/*
   $Id: NyxJCheckBoxTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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
 * @version $Id: NyxJCheckBoxTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
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
