/*
 $Id: NyxTableCellRenderer.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

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
 * @version $Id: NyxTableCellRenderer.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $
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