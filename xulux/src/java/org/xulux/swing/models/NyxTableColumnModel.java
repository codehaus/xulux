/*
   $Id: NyxTableColumnModel.java,v 1.4 2004-09-23 07:41:26 mvdb Exp $
   
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

import java.awt.Dimension;
import java.util.List;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.xulux.gui.Widget;
import org.xulux.swing.widgets.MenuItem;
import org.xulux.swing.widgets.PopupMenu;
import org.xulux.swing.widgets.Table;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableColumnModel.java,v 1.4 2004-09-23 07:41:26 mvdb Exp $
 */
public class NyxTableColumnModel extends DefaultTableColumnModel {

    /**
     * the table
     */
    protected Table table;
    /**
     * the model
     */
    protected NyxTableColumnModel lockedModel;
    /**
     * the lockedwidth
     */
    protected int lockedColumnWidth;
    /**
     * is the model initializing?
     */
    protected boolean initializing;
    /**
     *
     */
    public NyxTableColumnModel() {
        super();
    }

    /**
     * Initializes the columnModel and sets the table for later reference
     * @param table - the table that is using the columnModel.
     */
    public NyxTableColumnModel(Table table) {
        setTable(table);
        initializeColumns();
        //        addColumnModelListener(new TableColumnModelListener() {
        //
        //            public void columnAdded(TableColumnModelEvent e) {
        //                System.out.println("columnAdded : "+e);
        //            }
        //
        //            public void columnRemoved(TableColumnModelEvent e) {
        //                System.out.println("columnRemoved : "+e);
        //            }
        //
        //            public void columnMoved(TableColumnModelEvent e) {
        //                System.out.println("columnMoved : "+e);
        //            }
        //
        //            public void columnMarginChanged(ChangeEvent e) {
        //                System.out.println("columnMarginChanged : "+e);
        //            }
        //
        //            public void columnSelectionChanged(ListSelectionEvent e) {
        //                System.out.println("columnSelectionChanged : "+e);
        //            }
        //
        //        });
    }

    /**
     * @param table the table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Initializes the columns
     */
    private void initializeColumns() {
        initializing = true;
        int maxHeight = 0;
        List list = table.getChildWidgets();
        if (list == null) {
            return;
        }
        lockedModel = new NyxTableColumnModel();
        lockedModel.setTable(table);
        for (int i = 0; i < list.size(); i++) {
            Widget widget = (Widget) list.get(i);
            if (widget instanceof PopupMenu || widget instanceof MenuItem) {
                continue;
            }
            NyxTableColumn column = new NyxTableColumn(widget);
            String headerText = widget.getProperty("text");
            if (headerText != null && headerText.indexOf("\\n") != -1) {
              column.setHeaderRenderer(new MultipleLineHeaderRenderer(null));
            }
            column.setModel(this);
            column.setModelIndex(i);
            column.setCellRenderer(new NyxTableCellRenderer(widget, table));
            int height = widget.getRectangle().getHeight();
            if (height > maxHeight) {
                maxHeight = height;
            }
            addColumn(column);
            lockedModel.addColumn(column);
        }
        //System.out.println("columns : "+getColumnCount());
        table.setProperty("rowHeight", String.valueOf(maxHeight));
        initializing = false;
    }

    /**
     *
     * @return if table has columns that are locked.
     */
    public boolean hasLockedColumns() {
        boolean lockedColumns = false;
        for (int i = 0; i < getColumnCount(); i++) {
            if (((NyxTableColumn) getColumn(i)).isLocked()) {
                lockedColumns = true;
                break;
            }
        }
        return lockedColumns;
    }

    /**
     * Refresh the columns especially when widgets have been refreshed.
     * @todo Espcially needed when adding or removing widgets.
     */
    public void refresh() {
        // first remove all columns
        removeAllColumns();
        // and reinitialize the columns.
        initializeColumns();
    }

    /**
     * Removes all columns from the column list.
     * This removes also all columns from the locked
     * table.
     */
    protected void removeAllColumns() {
        for (int i = 0; i < getColumnCount(); i++) {
            removeColumn(getColumn(i));
        }
        // lockedModel gets rebuild anyway, so just destroy it..
        if (lockedModel != null) {
            lockedModel.destroy();
            lockedModel = null;
        }
    }

    /**
     * Removes the unlocked columns from the main table.
     * Call this one with care, since when there are no locks
     * all rows will be deleted.
     * Also wize to call this AFTER the removeLockedColumns.
     */
    public void removeUnlockedColumns() {
        for (int i = 0; i < lockedModel.getColumnCount(); i++) {
            if (i >= getColumnCount()) {
                // we are finished..
                return;
            }
            NyxTableColumn column = (NyxTableColumn) lockedModel.getColumn(i);
            if (!column.isLocked()) {
                lockedModel.removeColumn(column);
                i--;
            }
        }
    }
    /**
     * Removes locked columns from the current list of columns.
     */
    public void removeLockedColumns() {
        for (int i = 0; i < getColumnCount(); i++) {
            NyxTableColumn column = (NyxTableColumn) getColumn(i);
            if (column.isLocked()) {
                lockedColumnWidth += column.getPreferredWidth();
                removeColumn(column);
                i--;
            }
        }
    }

    /**
     * @return the locked columnWidth. This is set when removing columns
     */
    public Dimension getLockedColumnWidth() {
        return new Dimension(lockedColumnWidth, 0);
    }

    /**
     * Set the columnwidth to the new value.
     * @param width - the width
     */
    public void setLockedColumnWidth(int width) {
        lockedColumnWidth = width;
    }

    /**
     *
     * @return The columnModel to lock columns.
     */
    public NyxTableColumnModel getLockedColumnModel() {
        return lockedModel;
    }

    /**
     * destroy the instance variables
     * Just in case..
     */
    public void destroy() {
        table = null;
        if (lockedModel != null) {
            lockedModel.destroy();
            lockedModel = null;
        }
    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumn(int)
     */
    public TableColumn getColumn(int columnIndex) {
//        System.out.println("Index : "+columnIndex+","+super.getColumn(columnIndex).getModelIndex());
        return super.getColumn(columnIndex);
    }

    /**
     * @return true if this component is still initializing..
     */
    public boolean isInitializing() {
        return initializing;
    }

}
