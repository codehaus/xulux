/*
   $Id: NyxWindowTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.swing.util;

import org.xulux.gui.NyxWindow;
import org.xulux.gui.Widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Contains the test for the nyxwindow
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxWindowTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
 */
public class NyxWindowTest extends TestCase {

    /**
     * Constructor for NyxWindowTest.
     * @param name the test name
     */
    public NyxWindowTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxWindowTest.class);
        return suite;
    }

    /**
     * Test the methods that were dis-abstracted from containerwidget
     */
    public void testAll() {
        NyxWindow window = new NyxWindow(null) {

            public void addToParent(Widget widget) {
            }

            public Object getNativeWidget() {
                return null;
            }

            public void initialize() {
            }

            public void refresh() {
            }

            public Object getGuiValue() {
                return null;
            }

            public void focus() {
            }

            public boolean isValueEmpty() {
                return false;
            }

            public boolean canContainValue() {
                return false;
            }
        };
        assertTrue(window.canBeRootWidget());
    }

}
