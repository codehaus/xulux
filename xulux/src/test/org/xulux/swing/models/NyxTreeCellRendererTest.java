/*
   $Id: NyxTreeCellRendererTest.java,v 1.1 2004-07-14 15:05:31 mvdb Exp $
   
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
package org.xulux.swing.models;

import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.swing.widgets.Tree;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTreeCellRendererTest.java,v 1.1 2004-07-14 15:05:31 mvdb Exp $
 */
public class NyxTreeCellRendererTest extends TestCase {

    /**
     * Constructor for NyxTreeCellRendererTest.
     * @param name the name of the test
     */
    public NyxTreeCellRendererTest(String name) {
        super(name);
    }
    
    public void testConstructor() {
        System.out.println("testConstructor");
        NyxTreeCellRenderer r = new NyxTreeCellRenderer(null);
        assertEquals(null, r.widget);
        Tree tree = (Tree) WidgetFactory.getWidget("tree", "tree");
        r = new NyxTreeCellRenderer(tree);
        assertEquals(r.widget, tree);
    }
    
    public void testSetChildWidget() {
        System.out.println("testChildWidget");
        Tree tree = (Tree) WidgetFactory.getWidget("tree", "tree");
        NyxTreeCellRenderer r = new NyxTreeCellRenderer(tree);
        Widget label = WidgetFactory.getWidget("label", "label");
        r.setChildWidget(label);
        assertEquals(label, r.getChildWidget());
    }
    
    public void testGetTreeCellRendererComponentWithoutChildWidget() {
        System.out.println("testGetTreeCellRendererComponentWithoutChildWidget");
        Tree tree = (Tree) WidgetFactory.getWidget("tree", "tree");
        NyxTreeCellRenderer r = new NyxTreeCellRenderer(tree);
        //Component component = r.getTreeCellRendererComponent();
    }

}
