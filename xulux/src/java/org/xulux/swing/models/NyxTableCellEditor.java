/*
   $Id: NyxTableCellEditor.java,v 1.5 2004-06-24 21:49:20 mvdb Exp $
   
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

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import org.xulux.gui.Widget;

/**
 * A table cell editor for Xulux
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableCellEditor.java,v 1.5 2004-06-24 21:49:20 mvdb Exp $
 */
public class NyxTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    /**
     * The widget
     */
    private Widget widget;

    /**
     * @param widget the widget
     */
    public NyxTableCellEditor(Widget widget) {
        this.widget = widget;
    }
    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        return widget.getGuiValue();
    }
    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return (Component) this.widget.getNativeWidget();
    }

    /**
     * Stops the editing of cells.
     * @param table the table
     */
    public void stopCellEditing(JTable table) {
        Component component = table.getEditorComponent();
        if (component instanceof JTextField) {
            table.getModel().setValueAt(((JTextField) component).getText(), table.getEditingRow(), table.getEditingColumn());
            stopCellEditing();
        }
    }

    /**
     * @return the widget that is the editor.
     */
    public Widget getWidget() {
        return this.widget;
    }

    /**
     * Destroy the editor.
     */
    public void destroy() {
        this.widget = null;
    }

}
