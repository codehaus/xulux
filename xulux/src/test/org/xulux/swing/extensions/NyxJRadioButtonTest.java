/*
   $Id: NyxJRadioButtonTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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
 * Test the NyxJRadioButton where possible
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJRadioButtonTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
 */
public class NyxJRadioButtonTest extends TestCase {

    /**
     * The callbackcomponent
     */
    private NyxJRadioButton callBackComponent;
    /**
     * Expected Color.
     */
    private Color expectedColor;

    /**
     * Constructor for NyxJRadioButtonTest.
     * @param name the name of the test
     */
    public NyxJRadioButtonTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxJRadioButtonTest.class);
        return suite;
    }

    /**
     * Test the realbackground
     */
    public void testRealBackground() {
        System.out.println("testRealBackground");
        NyxJRadioButton radio = new NyxJRadioButton();
        radio.setRealBackground(Color.cyan);
        assertEquals(Color.cyan, radio.getRealBackground());
    }

    /**
     * Test the iconstub
     */
    public void testIconStub() {
        System.out.println("testIconStub");
        NyxJRadioButton radio = new NyxJRadioButton();
        radio.setRealBackground(Color.cyan);
        NyxJRadioButton.IconStub stub = radio.new IconStub(null);
        assertEquals(0, stub.getIconHeight());
        assertEquals(0, stub.getIconWidth());
        TestIcon icon = new TestIcon();
        stub = radio.new IconStub(icon);
        stub.paintIcon(radio, null, 0, 0);
        radio.setRealBackground(null);
        stub.paintIcon(null, null, 0, 0);
        assertEquals(icon.getIconHeight(), stub.getIconHeight());
        assertEquals(icon.getIconWidth(), stub.getIconWidth());
    }

    /**
     * Test the setting of icons
     */
    public void testSetIcons() {
        System.out.println("testSetIcons");
        NyxJRadioButton radio = new NyxJRadioButton();
        TestIcon icon = new TestIcon();
        TestIcon iconSelected = new TestIcon();
        // callback setting.
        this.callBackComponent = radio;
        icon.setCallBack(this);
        iconSelected.setCallBack(this);
        radio.setIcon(icon);
        radio.setSelectedIcon(iconSelected);
        assertNotSame(icon, radio.getIcon());
        assertNotSame(iconSelected, radio.getSelectedIcon());
        assertEquals(NyxJRadioButton.IconStub.class, radio.getIcon().getClass());
        assertEquals(NyxJRadioButton.IconStub.class, radio.getSelectedIcon().getClass());
        expectedColor = radio.getBackground();
        radio.getIcon().paintIcon(radio, null, 0, 0);
        radio.getSelectedIcon().paintIcon(radio, null, 0, 0);
        radio.setRealBackground(Color.cyan);
        expectedColor = Color.cyan;
        radio.getIcon().paintIcon(radio, null, 0, 0);
        radio.getSelectedIcon().paintIcon(radio, null, 0, 0);
    }

    /**
     * Since the radio button creates a new component,
     * we have to check parameters for the correct value
     * Please see the testSetIcons for the correct values
     * that the component should have
     * @param c the component from the callback
     */
    public void setIconsCallBack(Component c) {
        System.out.println("testSetIcons callback");
        assertNotSame(callBackComponent, c);
        assertEquals(expectedColor, c.getBackground());
    }

    /**
     * A test icon stub, so we can assert on what happens.
     */
    public class TestIcon implements Icon {

        /**
         * the testcase
         */
        private NyxJRadioButtonTest test;
        /**
         * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (test != null) {
                test.setIconsCallBack(c);
            }
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

        /**
         * Set the callback
         * @param test the testcase.
         */
        public void setCallBack(NyxJRadioButtonTest test) {
            this.test = test;
        }
    }
}
