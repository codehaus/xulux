/*
   $Id: ContainerWidgetTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.Widget;

import junit.framework.TestCase;

/**
 * Test the dis-abstracted container widget methods.
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContainerWidgetTest.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
 */
public class ContainerWidgetTest extends TestCase {

    /**
     * Constructor for ContainerWidgetTest.
     * @param name the name of the test.
     */
    public ContainerWidgetTest(String name) {
        super(name);
    }

    public void testDestroy() {
    }

    public void testCanContainChildren() {
    }

    public void testCanBeRootWidget() {
    }

    public void testAddChildWidget() {
    }

    public void testGetChildWidgets() {
    }

    public void testContainerWidget() {
    }

    public void testInitializeChildren() {
    }

    public void testAddToParent() {
    }

    /**
     * @return a new containerwidget
     */
    public ContainerWidget getContainerWidget() {
        return new ContainerWidget("container") {

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
    }

}
