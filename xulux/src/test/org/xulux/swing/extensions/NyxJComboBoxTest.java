/*
   $Id: NyxJComboBoxTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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

import org.xulux.gui.NyxListener;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the NyxJCombobox added methods.
 *
 * @author Martin van den Bemt
 * @version $Id: NyxJComboBoxTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
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
