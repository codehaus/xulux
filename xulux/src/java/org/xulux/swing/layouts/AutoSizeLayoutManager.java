package org.xulux.swing.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.xulux.gui.Widget;

/**
 * The autosize layoutmanager autosizes the components based on the preferred size.
 * It also makes sure when resizing the component can stay on the specified position
 * (eg LEFT means, stay left, do not center).
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: AutoSizeLayoutManager.java,v 1.1 2004-10-19 13:46:55 mvdb Exp $
 */
public class AutoSizeLayoutManager extends SwingLayoutAbstract implements LayoutManager2 {


  /**
   * A map containing widgets and their components
   */
  private Map map;

  public AutoSizeLayoutManager() {
    map = new HashMap();
  }
  /**
   * The constructor that imidiately sets the parent
   * @param parent the parent widget.
   */
  public AutoSizeLayoutManager(Widget parent) {
    this();
    setParent(parent);
  }

  /**
   * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
   */
  public float getLayoutAlignmentX(Container target) {
    return 0;
  }

  /**
   * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
   */
  public float getLayoutAlignmentY(Container target) {
    return 0;
  }

  /**
   * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
   */
  public void invalidateLayout(Container target) {
  }

  /**
   * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
   */
  public Dimension maximumLayoutSize(Container target) {
    return null;
  }

  /**
   * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
   */
  public void addLayoutComponent(Component comp, Object constraints) {
    map.put(comp, constraints);
  }

  /**
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  public void removeLayoutComponent(Component comp) {
  }

  /**
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  public void layoutContainer(Container parent) {
//    System.out.println("Parent : " + parent);
    int compCount = parent.getComponentCount();
    Insets insets = parent.getInsets();
    System.out.println("*****************************************");
    System.out.println("parent : " + parent);
    for (int i = 0; i < compCount; i++) {
      Component component = parent.getComponent(i);
      System.out.println("component : "+ component);
      Widget widget = null;
      Object obj = map.get(component);
      if (obj instanceof Widget) {
        widget = (Widget) obj;
      }
      Dimension prefSize = null;
      Point location = new Point(0,0);
      if (widget != null) {
        System.out.println("widget : " + widget);
        prefSize = widget.getRectangle().getRectangle().getSize();
        System.err.println("Getting location : " + widget.getRectangle().getRectangle());
        location = widget.getRectangle().getRectangle().getLocation();
      } else {
        prefSize = component.getPreferredSize();
        if (obj instanceof String) {
          String newValue = ((String) obj).toLowerCase();
          if (newValue.indexOf("width=auto") != -1) {
            prefSize.width = parent.getSize().width;
          }
        }
      }
      System.out.println("PrefSize : " + prefSize);
      int width = prefSize.width+insets.left;
      if (widget != null && widget.getProperty("width") != null) {
        String widthProp = widget.getProperty("width");
        if ("auto".equalsIgnoreCase(widthProp)) {
	        int newX =  parent.getSize().width;
	        if (newX > width) {
	          width = newX;
	        }
        }
      }
      System.out.println("*****************************************");
      int height = prefSize.height+insets.top+insets.bottom;
      System.out.println("location : " + location);
      component.setBounds(location.x, location.y, width, height);
    }
  }

  /**
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
   */
  public void addLayoutComponent(String name, Component comp) {
  }

  /**
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  public Dimension minimumLayoutSize(Container parent) {
    return null;
  }

  /**
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  public Dimension preferredLayoutSize(Container parent) {
//    System.out.println("Parent : " + parent);
    int compCount = parent.getComponentCount();
    Insets insets = parent.getInsets();
    int width = insets.left;
    int height = insets.top=insets.bottom;
    for (int i = 0; i < compCount; i++) {
      Component component = parent.getComponent(i);
      Object obj = map.get(component);
      Widget widget = null; 
      if (obj instanceof Widget) {
        widget = (Widget) obj;
      }
      Dimension prefSize = null;
      if (widget != null) {
        prefSize = widget.getRectangle().getRectangle().getSize();
      } else {
        prefSize = component.getPreferredSize();
      }
      width+=prefSize.width;
      height+=prefSize.height;
    }
//    System.out.println("returning : " + width + " height " + height);
    return new Dimension(width, height);
  }

  /**
   * @see org.xulux.swing.layouts.SwingLayoutAbstract#addWidget(org.xulux.gui.Widget)
   */
  public void addWidget(Widget widget) {
    Object nativeWidget = widget.getNativeWidget();
    if (nativeWidget instanceof Component) {
      addLayoutComponent((Component) nativeWidget, widget);
    }
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
    if (map != null) {
      map.clear();
      map = null;
    }
  }

}
