/*
 $Id: NyxComboTest.java,v 1.4 2003-12-15 20:17:49 mvdb Exp $

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
package org.xulux.nyx.gui;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the NyxCombo box.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxComboTest.java,v 1.4 2003-12-15 20:17:49 mvdb Exp $
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
