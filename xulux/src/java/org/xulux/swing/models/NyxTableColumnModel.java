/*
 $Id: NyxTableColumnModel.java,v 1.1 2003-12-18 00:17:22 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
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
 * @version $Id: NyxTableColumnModel.java,v 1.1 2003-12-18 00:17:22 mvdb Exp $
 */
public class NyxTableColumnModel extends DefaultTableColumnModel
{

    protected Table table;
    protected NyxTableColumnModel lockedModel;
    protected int lockedColumnWidth;
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

    public void setTable(Table table) {
        this.table = table;
    }

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
            column.setModel(this);
            column.setModelIndex(i);
            column.setCellRenderer(new NyxTableCellRenderer(widget,table));
            //column.setPreferredWidth(100);
            //System.out.println("Setting width to : "+widget.getRectangle().getWidth());
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
            if (((NyxTableColumn)getColumn(i)).isLocked()) {
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
            NyxTableColumn column = (NyxTableColumn)lockedModel.getColumn(i);
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
            NyxTableColumn column = (NyxTableColumn)getColumn(i);
            if (column.isLocked()) {
                lockedColumnWidth+=column.getPreferredWidth();
                removeColumn(column); 
                i--;
            }
        }
    }
    
    /**
     * 
     * @return the locked columnWidth. This is set when removing columns
     */    
    public Dimension getLockedColumnWidth() {
        return new Dimension(lockedColumnWidth,0);
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
        //System.out.println("Index : "+columnIndex+","+super.getColumn(columnIndex).getModelIndex());
        return super.getColumn(columnIndex);
    }
    
    /**
     * 
     * @return true if this component is still initializing..
     */
    public boolean isInitializing() {
        return initializing;
    }

}
