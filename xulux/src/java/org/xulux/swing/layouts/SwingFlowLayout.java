/*
 $Id: SwingFlowLayout.java,v 1.1 2004-10-18 14:10:47 mvdb Exp $
 
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JComponent;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.Widget;

/**
 * The flow layout. This is a wrapper around the swing border layout
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt </a>
 * @version $Id: SwingFlowLayout.java,v 1.1 2004-10-18 14:10:47 mvdb Exp $
 */
public class SwingFlowLayout extends SwingLayoutAbstract implements
    LayoutManager {

  boolean widgetsRefreshed = false;

  protected FlowLayout layout;

  public SwingFlowLayout() {
  }

  /**
   * @see org.xulux.gui.IXuluxLayout#addWidget(org.xulux.gui.Widget)
   * @widgetProprety layout-orientation
   */
  public void addWidget(Widget widget) {
  }

  /**
   * Not used.
   * 
   * @see org.xulux.gui.IXuluxLayout#removeWidget(org.xulux.gui.Widget)
   */
  public void removeWidget(Widget widget) {

  }

  /**
   * @see org.xulux.gui.IXuluxLayout#destroy()
   */
  public void destroy() {

  }

  /**
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
   */
  public void addLayoutComponent(String name, Component comp) {
  }

  /**
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  public void removeLayoutComponent(Component comp) {
    layout.removeLayoutComponent(comp);
  }

  /**
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  public Dimension preferredLayoutSize(Container parent) {
    if (!widgetsRefreshed) {
      setWidgetsPreferredSize();
    }
    return layout.preferredLayoutSize(parent);
  }

  /**
   * Make sure the preferredsize is set
   */
  public void setWidgetsPreferredSize() {
    if (widgetsRefreshed) {
      return;
    }
    List list = getParent().getChildWidgets();
    if (list != null && list.size() > 0) {
      for (int j = 0; j < list.size(); j++) {
        Widget w = (Widget) list.get(j);
        if (w instanceof ContainerWidget) {
          List childList = w.getChildWidgets();
          for (int i = 0; i < childList.size(); i++) {
            Widget w2 = (Widget) childList.get(i);
            Dimension dim =  w2.getRectangle().getRectangle().getSize();
            ((JComponent) w2.getNativeWidget()).setPreferredSize(dim);
          }
        } else if (w.getNativeWidget() instanceof JComponent) {
          ((JComponent) w.getNativeWidget()).setPreferredSize(w.getRectangle()
              .getRectangle().getSize());
        }
      }
    }
    widgetsRefreshed = true;
  }

  /**
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  public Dimension minimumLayoutSize(Container parent) {
    if (!widgetsRefreshed) {
      setWidgetsPreferredSize();
    }
    return layout.minimumLayoutSize(parent);
  }

  /**
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  public void layoutContainer(Container target) {
    if (!widgetsRefreshed) {
      setWidgetsPreferredSize();
    }
    layout.layoutContainer(target);
  }

  /**
   * When setting the parent, we already create the layout
   * 
   * @see org.xulux.gui.IXuluxLayout#setParent(org.xulux.gui.Widget)
   */
  public void setParent(Widget widget) {
    super.setParent(widget);
    if (layout == null) {
      String alignment = getParent().getProperty("layout-alignment");
      // default to left..
      int align = FlowLayout.LEFT;
      if ("right".equalsIgnoreCase(alignment)) {
        align = FlowLayout.RIGHT;
      } else if ("center".equalsIgnoreCase(alignment)) {
        align = FlowLayout.CENTER;
      }
      layout = new FlowLayout(align,0,0);
    }
  }
}