/*
   $Id: SwingLayoutAbstractTest.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
   
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
package org.xulux.swing.layouts;

import org.xulux.gui.Widget;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingLayoutAbstractTest.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
 */
public class SwingLayoutAbstractTest extends TestCase {

  /**
   * Constructor for SwingLayoutAbstractTest.
   * @param name the name of the test
   */
  public SwingLayoutAbstractTest(String name) {
    super(name);
  }

  public void testSetAndGetParentWidget() {
      System.out.println("testSetANdGetParentWidget");
      TempLayout layout = new TempLayout();
      MockWidget widget = new MockWidget("widget");
      assertEquals(null, layout.getParent());
      layout.setParent(widget);
      assertEquals(widget, layout.getParent());
  }
  
  public class TempLayout extends SwingLayoutAbstract {

    /**
     * @see org.xulux.swing.layouts.SwingLayoutAbstract#addWidget(org.xulux.gui.Widget)
     */
    public void addWidget(Widget widget) {
    }

    /**
     * @see org.xulux.swing.layouts.SwingLayoutAbstract#removeWidget(org.xulux.gui.Widget)
     */
    public void removeWidget(Widget widget) {
    }

    /**
     * @see org.xulux.swing.layouts.SwingLayoutAbstract#destroy()
     */
    public void destroy() {
    }
  }
}
