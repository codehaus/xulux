/*
 $Id: NyxJRadioButtonTest.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $

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
package org.xulux.nyx.swing.extensions;

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
 * @version $Id: NyxJRadioButtonTest.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $
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
