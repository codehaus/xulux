/*
 $Id: NyxJComboBoxTest.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $

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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import org.xulux.nyx.gui.NyxListener;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the NyxJCombobox added methods.
 *
 * @author Martin van den Bemt
 * @version $Id: NyxJComboBoxTest.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $
 */
public class NyxJComboBoxTest extends TestCase {

    /**
     * Constructor for NyxJComboBoxTest.
     * @param name the name of the test
     */
    public NyxJComboBoxTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxJComboBoxTest.class);
        return suite;
    }

    /**
     * Test for void setModel(ComboBoxModel)
     */
    public void testSetModelComboBoxModel() {
        System.out.println("testSetModelComboBoxModel");
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        final NyxJComboBox combo = new NyxJComboBox();
        // check if the newModelIsSet is set to true.
        combo.addPropertyChangeListener("model", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                assertTrue(combo.isNewModelIsSet());
            }
        });
        combo.setModel(model);
        assertFalse(combo.isNewModelIsSet());
    }

    /**
     * Test for void setSelectedItem(Object)
     */
    public void testSetSelectedItemObject() {
        System.out.println("testSetSelectedItemObject");
        Vector vector = new Vector();
        vector.add("test");
        vector.add("test1");
        DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
        final NyxJComboBox combo = new NyxJComboBox();
        combo.addPropertyChangeListener("model", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                assertTrue(combo.isNewModelIsSet());
                combo.setSelectedItem("test1");
                assertNotSame("test1", combo.getSelectedItem());
            }

        });
        combo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                assertFalse(combo.isNewModelIsSet());
            }
        });
        combo.setModel(model);
        assertFalse(combo.isNewModelIsSet());
        combo.setSelectedItem("test1");
        assertEquals("test1", combo.getSelectedItem());
    }

    /**
     * Test for void addFocusListener(FocusListener)
     * Test for void removeFocusListener(FocusListener)
     */
    public void testFocusListeners() {
        System.out.println("testFocusListeners");
        NyxJComboBox combo = new NyxJComboBox();
        FocusListener fl = new NyxFocusListener();
        int listenerLength = combo.getListeners(FocusListener.class).length;
        FocusListener noNyx = new FocusListener() {

            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
            }
        };
        combo.addFocusListener(noNyx);
        listenerLength += 1;
        combo.addFocusListener(fl);
        List list = Arrays.asList(combo.getListeners(FocusListener.class));
        assertEquals(listenerLength + 1, list.size());
        assertTrue(list.contains(fl));
        for (int i = 0; i < combo.getComponentCount(); i++) {
            Component comp = combo.getComponent(i);
            List fList = Arrays.asList(comp.getListeners(FocusListener.class));
            assertTrue(fList.contains(fl));
            assertFalse(fList.contains(noNyx));
        }
        combo.removeFocusListener(fl);
        combo.removeFocusListener(noNyx);
        listenerLength -= 1;
        list = Arrays.asList(combo.getListeners(FocusListener.class));
        assertEquals(listenerLength, list.size());
        assertFalse(list.contains(fl));
        for (int i = 0; i < combo.getComponentCount(); i++) {
            Component comp = combo.getComponent(i);
            List fList = Arrays.asList(comp.getListeners(FocusListener.class));
            assertFalse(fList.contains(fl));
            assertFalse(fList.contains(noNyx));
        }
    }

    /**
     * The listener to use for testing
     */
    public class NyxFocusListener extends NyxListener implements FocusListener {

        /**
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
        public void focusGained(FocusEvent e) {
        }

        /**
         * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
         */
        public void focusLost(FocusEvent e) {
        }
    }
}
