/*
 $Id: NyxTableModel.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $

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

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.xulux.global.BeanMapping;
import org.xulux.global.Dictionary;
import org.xulux.global.IConverter;
import org.xulux.global.IField;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.widgets.Table;

/**
 * The nyx tablemodel contains all magic for tables.
 * @todo Assumes lists right now.. Should support more probably..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableModel.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $
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