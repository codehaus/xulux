/*
   $Id: NyxTableCellRenderer.java,v 1.10 2004-09-23 07:55:27 mvdb Exp $
   
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

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.xulux.core.PartRequest;
import org.xulux.core.XuluxContext;
import org.xulux.gui.Widget;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.widgets.Table;

/**
 * The cellrenderer takes care of the look and content of a cell.
 * We should make our own cellRenderer probably, so we can use the functionalily of widgets..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableCellRenderer.java,v 1.10 2004-09-23 07:55:27 mvdb Exp $
 */
public class NyxTableCellRenderer extends DefaultTableCellRenderer {

    /**
     * the widget
     */
    protected Widget widget;
    /**
     * the parent table
     */
    protected Table parent;
    /**
     * the request
     */
    protected PartRequest request;

    /**
     * @param widget the widget
     * @param parent the parent table
     */
    public NyxTableCellRenderer(Widget widget, Table parent) {
        super();
        this.widget = widget;
        this.parent = parent;
        request = new CellPartRequest(widget);
    }

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
     *                       boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        request.setValue(value);
        widget.getPart().getSession().setValue("cellvalue", value);
        XuluxContext.fireFieldRequest(widget, request, XuluxContext.PRE_REQUEST);
        // refresh widget so gui changes can propegate.
        //Component comp = (Component) widget.getNativeWidget();
        if (!isSelected) {
            String bgColor = widget.getProperty("background-color-enabled");
            if (bgColor != null) {
                // make sure that it gets painted ok!
                setOpaque(true);
                // should be a lot easier in the new widget structure..
                setBackground(ColorUtils.getSwingColor(bgColor));
            }
        }
        String border = widget.getProperty("border");
        if (border != null) {
          int thickness = 1;
          try {
            thickness = Integer.parseInt(widget.getProperty("border-thickness"));
          } catch(NumberFormatException nfe) {
          }
          setBorder(new LineBorder(ColorUtils.getSwingColor(border),thickness));
        }
        String tooltipText = widget.getProperty("tooltip");
        if (tooltipText != null) {
          	setToolTipText(tooltipText+"-"+row+":"+column);
        }
        return this;
    }

    /**
     * Destroys all data know to the cellrenderer.
     *
     */
    public void destroy() {
        widget = null;
    }

}
