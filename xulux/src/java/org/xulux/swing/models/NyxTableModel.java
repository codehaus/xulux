/*
   $Id: NyxTableModel.java,v 1.5 2004-03-23 08:42:23 mvdb Exp $
   
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

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.xulux.dataprovider.BeanMapping;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.widgets.Table;

/**
 * The nyx tablemodel contains all magic for tables.
 * @todo Assumes lists right now.. Should support more probably..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableModel.java,v 1.5 2004-03-23 08:42:23 mvdb Exp $
 */
public class NyxTableModel extends NyxListener implements TableModel {

    /**
     * the table
     */
    protected Table table;
    /**
     * the tablemodel
     */
    protected TableModel model;

    /**
     *
     */
    public NyxTableModel() {
        super();
    }

    /**
     * @param table the table
     */
    public NyxTableModel(Table table) {
        setTable(table);
    }

    /**
     * @param model the model
     * @param table the table
     */
    public NyxTableModel(TableModel model, Table table) {
        setTable(table);
        setModel(model);
    }

    /**
     *
     * @param model the model
     */
    public void setModel(TableModel model) {
        this.model = model;
    }

    /**
     * @return if the model has a model already
     */
    public boolean hasModel() {
        return this.model != null;
    }

    /**
     * Set the table to be used in this tablemodel
     * @param table the table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        if (hasModel()) {
            return model.getRowCount();
        }
        int rowCount = 0;
        if (table != null) {
            if (table.getContent() != null) {
                rowCount = ((List) table.getContent()).size();
            }
        }
        return rowCount;
    }

    /**
     * Not used,we use a columnmodel.
     *
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        if (hasModel()) {
            return model.getColumnCount();
        }
        return 0;
    }

    /**
     * Not used, we use a columnmodel for that...
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {
        if (hasModel()) {
            return "" + model.getColumnName(columnIndex);
        }
        return "" + columnIndex;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex) {
        if (hasModel()) {
            return model.getColumnClass(columnIndex);
        }
        return String.class;
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (hasModel()) {
            return model.isCellEditable(rowIndex, columnIndex);
        }
        String editable = ((Widget) table.getChildWidgets().get(columnIndex)).getProperty("editable");

        return "true".equalsIgnoreCase(editable);
    }

    /**
     * Returns the row object if the columnIndex is -1.
     *
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (hasModel()) {
            return model.getValueAt(rowIndex, columnIndex);
        }
        if (columnIndex == -1) {
            return ((List) table.getContent()).get(rowIndex);
        }
        Widget w = (Widget) table.getChildWidgets().get(columnIndex);
        if (w.getField() != null) {
            BeanMapping map = Dictionary.getInstance().getMapping(((List) table.getContent()).get(rowIndex));
            IField field = map.getField(w.getField());
            if (field != null) {
                Object value = field.getValue(((List) table.getContent()).get(rowIndex));
                if (value == null) {
                    value = "";
                }
                IConverter converter = Dictionary.getConverter(value);
                if (converter != null) {
                    value = converter.getGuiValue(value);
                }
                return value;
            }
        }
        return "";
    }

    /**
     * Sets the value of a bean..
     *
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (hasModel()) {
            model.setValueAt(aValue, rowIndex, columnIndex);
            return;
        }
        Widget w = (Widget) table.getChildWidgets().get(columnIndex);
        if (w.getField() != null) {
            BeanMapping map = Dictionary.getInstance().getMapping(((List) table.getContent()).get(rowIndex));
            IField field = map.getField(w.getField());
            if (field != null) {
                field.setValue(((List) table.getContent()).get(rowIndex), aValue);
            }
        }
        // process rules, etc..
        this.widget = w;
        // fire post rule if any are present..
        completed();
    }

    /**
     * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
     */
    public void addTableModelListener(TableModelListener l) {
        if (hasModel()) {
            model.addTableModelListener(l);
            return;
        }
    }

    /**
     * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
     */
    public void removeTableModelListener(TableModelListener l) {
        if (hasModel()) {
            model.removeTableModelListener(l);
            return;
        }
    }

    /**
     * Destroy the tablemodel..
     *
     */
    public void destroy() {
        this.table = null;
        this.model = null;
    }

    /**
     * refreshes the table, since there is new content
     */
    public void refresh() {
    }

}
