/*
   $Id: NyxComboTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.gui;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the NyxCombo box.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxComboTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
 */
public class NyxComboTest extends TestCase {

    /**
     * Constructor for NyxComboTest.
     * @param name the name of the test
     */
    public NyxComboTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxComboTest.class);
        return suite;
    }

    /**
     * Test the notSelectedvalue
     */
    public void testNotSelectedValue() {
        System.out.println("testNotSelectedValue");
        C combo = new C("Combo");
        combo.initialize();
        // content should stay null
        combo.initializeNotSelectedValue();
        assertNull(combo.getContent());
        assertNull(combo.getNotSelectedValue());
        String nsv = "notselectedvalue";
        assertEquals(0, combo.refreshCount);
        // if we just set the not selected value
        // we get an arraylist with only the nsv back..
        combo.setNotSelectedValue(nsv);
        assertEquals(1, combo.refreshCount);
        assertNotNull(combo.getContent());
        assertEquals(nsv, ((List) combo.getContent()).get(0));
        // although we only have one not selectedvalue in the list
        // that gets removed now, we will keep the list, since people
        // can use it as a reference in their code..
        combo.setNotSelectedValue(null);
        assertEquals(2, combo.refreshCount);
        assertEquals(0, ((List) combo.getContent()).size());
        assertNull(combo.getNotSelectedValue());
        combo.setNotSelectedValue(nsv);
        assertEquals(nsv, combo.getNotSelectedValue());
        // the list is now 1 bigger again..
        assertEquals(3, combo.refreshCount);
        assertEquals(nsv, combo.getNotSelectedValue());
        ArrayList list = new ArrayList();
        list.add("choice1");
        list.add("choice2");
        list.add("choice3");
        combo.setContent(list);
        assertEquals(4, combo.refreshCount);
        assertEquals(list, combo.getContent());
        assertEquals(4, ((List) combo.getContent()).size());
        // set the notselectedvalue to some differentvalue.
        // it should replace the old one
        combo.setNotSelectedValue(nsv);
        // nothing should happen...
        assertEquals(4, combo.refreshCount);
        combo.setNotSelectedValue("different");
        assertEquals(4, ((List) combo.getContent()).size());
        assertEquals("different", ((List) combo.getContent()).get(0));
        assertEquals(5, combo.refreshCount);
        // reset the notselected value..
        combo.setNotSelectedValue(null);
        assertEquals(6, combo.refreshCount);
        assertEquals(3, ((List) combo.getContent()).size());
    }

    /**
     * A mock implementation of the NyxCombo
     */
    public class C extends NyxCombo {

        /**
         * the refreshcount
         */
        public int refreshCount = 0;
        /**
         * The main constructor
         * @param name the widget name
         */
        public C(String name) {
            super(name);
        }
        /**
         * @see org.xulux.nyx.gui.NyxCombo#getNativeWidget()
         */
        public Object getNativeWidget() {
            return null;
        }

        /**
         * @see org.xulux.nyx.gui.NyxCombo#initialize()
         */
        public void initialize() {
            initialized = true;
        }

        /**
         * @see org.xulux.nyx.gui.NyxCombo#refresh()
         */
        public void refresh() {
            refreshCount++;
            initializeNotSelectedValue();
        }

        /**
         * @see org.xulux.nyx.gui.IContentWidget#contentChanged()
         */
        public void contentChanged() {
        }
    }

}
