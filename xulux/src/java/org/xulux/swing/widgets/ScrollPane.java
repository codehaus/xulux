/*
   $Id: ScrollPane.java,v 1.7 2005-01-13 08:32:59 mvdb Exp $
   
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.IShowChildWidgets;
import org.xulux.gui.Widget;

/**
 * The scrollpane adds a scrollpane to the gui.
 * You can use the scrollpane in the xml or if you define a widget
 * to being scrollable, it will inject the scrollpane as the parent
 * of the widget.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ScrollPane.java,v 1.7 2005-01-13 08:32:59 mvdb Exp $
 */
public class ScrollPane extends ContainerWidget {

    protected JScrollPane pane;

    /**
     * @param name the name of the widget
     */
    public ScrollPane(String name) {
      super(name);
    }
  
    /**
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {
        super.destroy();
        if (pane == null) {
            return;
        }
        Container container = pane.getParent();
        pane.setVisible(false);
        pane.removeAll();
        if (container != null) {
            container.remove(pane);
        }
        pane = null;
    }
  
    /**
     * @see org.xulux.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return pane;
    }
  
    /**
     * @see org.xulux.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        this.pane = new JScrollPane();
        // @todo make scrollpane properties to set policies..
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        pane.setPreferredSize(getRectangle().getRectangle().getSize());
//        System.out.println("init port : " + pane.getViewport());
        if (pane.getViewport() != null) {
            pane.getViewport().setPreferredSize(pane.getPreferredSize());
        }
        initializeChildren();
    }
  
    /**
     * @see org.xulux.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
      return null;
    }
  
    /**
     * @see org.xulux.gui.Widget#focus()
     */
    public void focus() {
  
    }
  
    /**
     * @see org.xulux.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return true;
    }
  
    /**
     * @see org.xulux.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.gui.ContainerWidget#addToParent(org.xulux.gui.Widget)
     */
    public void addToParent(Widget widget) {
      if (widget instanceof IShowChildWidgets) {
          List children = widget.getChildWidgets();
          if (children != null && children.size() > 0) {
              Iterator it = children.iterator();
              while (it.hasNext()) {
                  Widget w = (Widget) it.next();
                  pane.add((JComponent) w.getNativeWidget(), w);
                  w.refresh();
              }
          }
      } else {
          pane.setViewportView((Component) widget.getNativeWidget());
          pane.setPreferredSize(getRectangle().getRectangle().getSize());
      }
    }

    /**
     * @see org.xulux.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
          return;
        }
        isRefreshing = true;
        if (!initialized) {
          initialize();
        }
        pane.setVisible(isVisible());
        pane.setViewportView(pane.getViewport().getView());
        Dimension viewSize = (Dimension) getRealProperty("viewSize");
        if (viewSize != null) {
          pane.getViewport().setViewSize(viewSize);
        }
        isRefreshing = false;
        pane.revalidate();
        isRefreshing = false;
    }
}
