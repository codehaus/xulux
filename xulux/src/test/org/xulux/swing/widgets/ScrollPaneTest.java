/*
   $Id: ScrollPaneTest.java,v 1.1 2004-06-28 13:11:08 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import junit.framework.TestCase;

import org.xulux.gui.WidgetFactory;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ScrollPaneTest.java,v 1.1 2004-06-28 13:11:08 mvdb Exp $
 */
public class ScrollPaneTest extends TestCase {

    /**
     * Constructor for ScrollPaneTest.
     * @param name the name of the test
     */
    public ScrollPaneTest(String name) {
        super(name);
    }

    public void testNativeWidget() {
        System.out.println("testNativeWidget");
        ScrollPane widget = (ScrollPane) WidgetFactory.getWidget("scrollpane", "scrollpane");
        assertNotNull(widget);
        widget.initialize();
        assertNotNull(widget.pane);
        assertNotNull(widget.getNativeWidget());
        widget = (ScrollPane) WidgetFactory.getWidget("scrollpane", "scrollpane");
        assertNotNull(widget.getNativeWidget());
        widget.destroy();
        assertEquals(null, widget.pane);
    }
}
