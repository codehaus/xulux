/*
   $Id: SwingWidgetTest.java,v 1.1 2004-04-15 00:05:04 mvdb Exp $
   
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
package org.xulux.swing;

import javax.swing.JComponent;

import org.xulux.core.ApplicationPart;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingWidgetTest.java,v 1.1 2004-04-15 00:05:04 mvdb Exp $
 */
public class SwingWidgetTest extends TestCase {

    /**
     * Constructor for SwingWidgetTest.
     * @param name the name of the test
     */
    public SwingWidgetTest(String name) {
        super(name);
    }
/**
        JComponent j = (JComponent) getNativeWidget();
        j.requestFocus();
        // if widget is not showing we have
        // to make it showing..
        if (!j.isShowing() && getParent() != null) {
            // set the session variable, so controls
            // can look who requested focus..
            getPart().getSession().setValue("nyx.focusrequest", this);
            getParent().focus();
        }
        // remove session variable again.
        getPart().getSession().remove("nyx.focusrequest");
        j.requestFocus();
    }

    */
    public void testSwingWidget() {
        System.out.println("testSwingWidget");
        ApplicationPart part = new ApplicationPart();
        SwingWidgetInner inner = new SwingWidgetInner("widget");
        inner.setPart(part);
        SwingWidgetInner parent = new SwingWidgetInner("parent");
        parent.setPart(part);
        inner.focus();
        assertEquals(1, inner.jc.focusCount);
        inner.setParent(inner);
        inner.setParent(parent);
        inner.jc.focusCount = 0;
        inner.focus();
        assertEquals(1, parent.jc.focusCount);
        assertEquals(2, inner.jc.focusCount);
        // make sure the temp session value is removed..
        assertNull(part.getSession().getValue("nyx.focusrequeset"));
        // if widget has a parent and is showing, it should just request the focus..
        // and the parent should not be called..
        inner.jc.focusCount = 0;
        parent.jc.focusCount = 0;
        inner.jc.setShowing(true);
        inner.focus();
        assertEquals(0, parent.jc.focusCount);
        assertEquals(1, inner.jc.focusCount);
    }

    public class SwingJComponent extends JComponent {
      private boolean showing;
      private int focusCount;
      public void setShowing(boolean showing) {
          this.showing = showing;
      }
      /**
       * @see java.awt.Component#isShowing()
       */
      public boolean isShowing() {
        return showing;
      }
      
      public void requestFocus() {
        focusCount++;
      }

    }
    
    public class SwingWidgetInner extends SwingWidget {

      private SwingJComponent jc;
      
      public SwingWidgetInner(String name) {
          super(name);
          this.jc = new SwingJComponent();
      }
      /**
       * @see org.xulux.swing.SwingWidget#destroy()
       */
      public void destroy() {
      }

      public void setShowing(boolean showing) {
        jc.setShowing(showing);
      }
        
      /**
       * @see org.xulux.swing.SwingWidget#getNativeWidget()
       */
      public Object getNativeWidget() {
        return this.jc;
      }

      /**
       * @see org.xulux.swing.SwingWidget#initialize()
       */
      public void initialize() {
      }

      /**
       * @see org.xulux.swing.SwingWidget#refresh()
       */
      public void refresh() {
      }

      /**
       * @see org.xulux.gui.Widget#getGuiValue()
       */
      public Object getGuiValue() {
        return null;
      }

      /**
       * @see org.xulux.gui.Widget#isValueEmpty()
       */
      public boolean isValueEmpty() {
        return false;
      }

      /**
       * @see org.xulux.gui.Widget#canContainValue()
       */
      public boolean canContainValue() {
        return false;
      }
    }
}
