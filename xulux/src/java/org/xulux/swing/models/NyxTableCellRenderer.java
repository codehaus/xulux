/*
   $Id: NyxTableCellRenderer.java,v 1.3 2004-01-28 15:09:23 mvdb Exp $
   
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
import javax.swing.table.DefaultTableCellRenderer;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.context.PartRequest;
import org.xulux.context.SessionPart;
import org.xulux.gui.Widget;
import org.xulux.swing.widgets.Table;

/**
 * The cellrenderer takes care of the look and content of a cell.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableCellRenderer.java,v 1.3 2004-01-28 15:09:23 mvdb Exp $
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
        if (widget.getNativeWidget() instanceof Component) {
            request.setValue(value);
            ApplicationContext.fireFieldRequest(widget, request, ApplicationContext.PRE_REQUEST);
            widget.refresh();
            // refresh widget so gui changes can propegate.
            Component comp = (Component) widget.getNativeWidget();
            //            System.err.println("fg : "+table.getForeground());
            //            System.err.println("sfg : "+table.getSelectionForeground());
            if (!isSelected) {
                String bgColor = widget.getProperty("background-color-enabled");
                if (bgColor == null) {
                    setBackground(table.getBackground());
                } else {
                    setBackground(comp.getBackground());
                }
            }
            //if (column == 1) System.err.println("bg : "+comp.getBackground());
            String fgColor = widget.getProperty("foreground-color-enabled");
            //System.out.println("fg color : "+fgColor);
            //System.out.println("Font  : "+getFont());
            //            if (fgColor == null) {
            //                setForeground(table.getForeground());
            //            } else {
            //                setForeground(comp.getForeground());
            //            }
            //new Exception().printStackTrace(System.err);
            //(Compotable.getSelectionForeground()
            //widget.refresh();
            setValue(value);
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

    /**
     * The cellPartRequest
     */
    public class CellPartRequest implements PartRequest {

        /**
         * the widget
         */
        private Widget widget;
        /**
         * the value
         */
        private Object value;

        /**
         * @param widget the widget
         */
        public CellPartRequest(Widget widget) {
            this.widget = widget;
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getName()
         */
        public String getName() {
            return widget.getName();
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getPart()
         */
        public ApplicationPart getPart() {
            return widget.getPart();
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getSession()
         */
        public SessionPart getSession() {
            return getPart().getSession();
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getType()
         */
        public int getType() {
            return PartRequest.NO_ACTION;
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getValue()
         */
        public Object getValue() {
            return value;
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getValue(java.lang.String)
         */
        public Object getValue(String field) {
            return getPart().getWidget(field).getValue();
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getWidget()
         */
        public Widget getWidget() {
            return widget;
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#getWidget(java.lang.String)
         */
        public Widget getWidget(String name) {
            return getPart().getWidget(name);
        }

        /**
         * @see org.xulux.nyx.context.PartRequest#setValue(java.lang.Object)
         */
        public void setValue(Object value) {
            this.value = value;
        }

        /**
         * @see java.lang.Object#clone()
         */
        public Object clone() {
            return null;
        }

    }

}
