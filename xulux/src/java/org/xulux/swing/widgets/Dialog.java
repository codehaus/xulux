/*
   $Id: Dialog.java,v 1.2 2004-05-18 00:01:14 mvdb Exp $
   
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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;

import org.xulux.core.XuluxContext;
import org.xulux.gui.IShowChildWidgets;
import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.NyxWindow;
import org.xulux.gui.Widget;
import org.xulux.swing.listeners.NyxWindowListener;
import org.xulux.utils.BooleanUtils;

/**
 * A dialog
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Dialog.java,v 1.2 2004-05-18 00:01:14 mvdb Exp $
 */
public class Dialog extends NyxWindow {
  
  protected JDialog dialog;
  protected WindowListener windowListener;

  /**
   * @param name
   */
  public Dialog(String name) {
    super(name);
  }

  /**
   * @see org.xulux.gui.Widget#destroy()
   */
  public void destroy() {
    processDestroy();
    ArrayList children = getChildWidgets();
    if (children != null) {
        Iterator it = children.iterator();
        while (it.hasNext()) {
            Widget cw = (Widget) it.next();
            cw.destroy();
        }
        children.clear();
        children = null;
    }
    // remove all child widgets
    // so we don't have any leftovers.
    if (dialog != null) {
      dialog.removeAll();
      dialog.removeWindowListener(windowListener);
      windowListener = null;
      dialog.setVisible(false);
      Container container = dialog.getParent();
      if (container != null) {
          container.remove(dialog);
      }
      dialog.dispose();
      dialog = null;
    }

  }

  /**
   * @see org.xulux.gui.Widget#getNativeWidget()
   */
  public Object getNativeWidget() {
    if (!initialized) {
      initialize();
    }
    return dialog;
  }

  /**
   * @see org.xulux.gui.Widget#initialize()
   */
  public void initialize() {
    if (this.initialized) {
        return;
    }
    initialized = true;
    String title = getProperty("title");
    if (title == null) {
        title = "";
    }
    if (getPart().getParentPart() != null) {
      Widget parentWindow = getPart().getParentPart().getWidget(getProperty("parentWindow"));
      if (parentWindow != null) {
        Object nativeParent = parentWindow.getNativeWidget();
        if (nativeParent instanceof Frame) {
          dialog = new JDialog((Frame) nativeParent);
        } else if (nativeParent instanceof JDialog) {
          dialog = new JDialog((JDialog) nativeParent);
        }
      }
    }
    if (dialog == null) {    
      dialog = new JDialog();
    }
    dialog.setTitle(title);
    IXuluxLayout layout = XuluxContext.getGuiDefaults().getLayout(null, getProperty("layout"));
    if (layout == null) {
        layout = XuluxContext.getGuiDefaults().getDefaultLayout();
    }
    layout.setParent(this);
    dialog.getContentPane().setLayout((LayoutManager) layout);
    this.windowListener = new NyxWindowListener(this);
    dialog.addWindowListener(this.windowListener);
    initializeChildren();
    boolean autoSize = BooleanUtils.toBoolean(getProperty("autosize"));
    if (autoSize) {
        Dimension dim = dialog.getContentPane().getLayout().preferredLayoutSize(dialog.getContentPane());
        dialog.pack();
    } else {
        dialog.setSize(getRectangle().getWidth(), getRectangle().getHeight());
    }
    if (getProperty("resizable") != null) {
        boolean resize = BooleanUtils.toBoolean(getProperty("resizable"));
        dialog.setResizable(resize);
    }
    if (!isRefreshing()) {
        refresh();
    }
    processInit();
    dialog.setModal(BooleanUtils.toBoolean(getProperty("modal")));
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
    dialog.requestFocus();
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
                dialog.getContentPane().add((JComponent) w.getNativeWidget(), w);
            }
        }
    } else {
//        System.out.println("Window widget : " + widget.getNativeWidget().getClass());
        if (widget.getNativeWidget() instanceof JMenuBar) {
            dialog.getRootPane().setJMenuBar((JMenuBar) widget.getNativeWidget());
        } else {
            dialog.getContentPane().add((JComponent) widget.getNativeWidget(), widget);
        }
    }
  }

  /**
   * @see org.xulux.gui.Widget#refresh()
   */
  public void refresh() {
    isRefreshing = true;
    initialize();
    dialog.setTitle(getProperty("title"));
    dialog.setEnabled(isEnabled());
    dialog.setVisible(isVisible());
    isRefreshing = false;
  }

}
