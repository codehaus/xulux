/*
   $Id: PopupListener.java,v 1.9 2004-11-25 09:49:48 mvdb Exp $
   
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
package org.xulux.swing.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.xulux.core.PartRequest;
import org.xulux.core.XuluxContext;
import org.xulux.gui.GuiUtils;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.rules.impl.WidgetRequestImpl;

/**
 * A popuplistener. Shows the popup when the right mousebutton is clicked
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PopupListener.java,v 1.9 2004-11-25 09:49:48 mvdb Exp $
 */
public class PopupListener extends NyxListener implements MouseListener {

    /**
     * The widget that instantiated the popuplistener
     */
    private Widget parent;
    /**
     *
     */
    public PopupListener() {
        super();
    }

    /**
     * @param widget the widget
     * @param parent the widget that instantiated the popuplistener.
     */
    public PopupListener(Widget widget, Widget parent) {
        super(widget);
        this.parent = parent;
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
      showPopup(e);
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        showPopup(e);
    }
    
    public void showPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        Object value = null;
        Component component = e.getComponent();
        if (component instanceof JTable) {
          JTable table = (JTable) component;
          int row = table.rowAtPoint(e.getPoint());
          table.setRowSelectionInterval(row, row);
          value = table.getValueAt(row, -1);
        }
        parent.setLazyProperty("rowValue", value);
        System.out.println("Comp : " + e.getComponent().getComponentAt(e.getPoint()).getComponentAt(e.getPoint()));
        WidgetRequestImpl impl = new WidgetRequestImpl(getWidget().getParent(), PartRequest.ACTION_VALUE_CHANGED);
        XuluxContext.fireFieldRequest(getWidget(), impl, XuluxContext.PRE_REQUEST);
        List list = getWidget().getChildWidgets();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Widget widget = (Widget) list.get(i);
                XuluxContext.fireFieldRequest(widget, impl, XuluxContext.PRE_REQUEST);
            }
        }
//        parent.setLazyProperty("rowValue", null);
        JComponent comp = (JComponent) getWidget().getNativeWidget();
        if (comp instanceof JPopupMenu) {
          ((JPopupMenu) comp).show((Component) e.getSource(), e.getX(), e.getY());
        }
        
      }
    }
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
        // if detected a doubleclick and item is doubleclick item.
        String dblName = widget.getProperty("doubleclick");
        if (dblName == null) {
          // try to get it at the parent
          dblName = widget.getParent().getProperty("doubleclick");
        }
        if (dblName != null) {
          Component component = e.getComponent();
          if (component instanceof JTable) {
            JTable jt = (JTable) component;
            widget.getParent().setLazyProperty("rowValue", jt.getValueAt(jt.getSelectedRow(), -1));
          }
          Widget widget = (Widget) getWidget().getPart().getWidget(dblName);
          GuiUtils.fireFieldPostRule(widget, widget, PartRequest.NO_ACTION);
          // clear the rowValue..
          widget.setLazyProperty("rowValue", null);
          return;
        }
      }
      showPopup(e);
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {

    }

}
