/*
   $Id: NyxTableColumn.java,v 1.4 2004-11-18 23:24:46 mvdb Exp $
   
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.xulux.gui.NyxCombo;
import org.xulux.gui.Widget;
import org.xulux.swing.widgets.Table;
import org.xulux.utils.BooleanUtils;

/**
 * Override the standard TableColumn, so we can use instances of widgets to set columns
 * , instead of dynamically creating them all the time.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableColumn.java,v 1.4 2004-11-18 23:24:46 mvdb Exp $
 */
public class NyxTableColumn extends TableColumn {

    /**
     * The widget
     */
    private Widget widget;
    /**
     * The columnmodel
     */
    private NyxTableColumnModel model;

    /**
     *
     */
    public NyxTableColumn() {
        super();
    }

    /**
     * @param widget - the widget the create the column for.
     * @widgetproperty resizable - specifies if the widget is resizable in a table or not
     */
    public NyxTableColumn(Widget widget) {
        this.widget = widget;
        addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                String property = evt.getPropertyName();
                if ("preferredWith".equals(property) || "width".equals(property)) {
                    // do something..
                    if (isLocked()) {
                        if (getModel() != null && !getModel().isInitializing()) {
                            int chgValue = ((Integer) evt.getNewValue()).intValue() - ((Integer) evt.getOldValue()).intValue();
                            int resultValue = (int) getModel().getLockedColumnWidth().getWidth() + chgValue;
                            getModel().setLockedColumnWidth(resultValue);
                            Table table = (Table) NyxTableColumn.this.widget.getParent();
                            table.getLockedJTable().setPreferredScrollableViewportSize(getModel().getLockedColumnWidth());
                        }
                    }
                }
            }

        });
        String resizable = widget.getProperty("resizable");
        if ("false".equals(resizable)) {
            setResizable(false);
        } else {
            // we default to resizable..
            setResizable(true);
        }
        setHeaderValue(widget.getProperty("text"));
        int width = widget.getRectangle().getWidth();
        if (width == 0) {
            width = ((JComponent)widget.getNativeWidget()).getPreferredSize().width;
        }
        setPreferredWidth(width);
        setWidth(width);
        if (widget instanceof NyxCombo) {
            setCellEditor(new NyxTableCellEditor(widget));
        }
    }

    /**
     * @param modelIndex the modelIndex
     */
    public NyxTableColumn(int modelIndex) {
        super(modelIndex);
    }

    /**
     * @param modelIndex modelIndex
     * @param width width
     */
    public NyxTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
    }

    /**
     * @param modelIndex modelIndex
     * @param width width
     * @param cellRenderer cellRenderer
     * @param cellEditor cellEditor
     */
    public NyxTableColumn(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
    }

    /**
     * @return is the column is locked or not.
     */
    public boolean isLocked() {
        return BooleanUtils.toBoolean(widget.getProperty("locked"));
    }

    /**
     * Set the model for easy access to the model.
     * @param model - the nyx columnmodel.
     */
    public void setModel(NyxTableColumnModel model) {
        this.model = model;
    }

    /**
     * @return - the nyx column model.
     */
    public NyxTableColumnModel getModel() {
        return this.model;
    }

}
