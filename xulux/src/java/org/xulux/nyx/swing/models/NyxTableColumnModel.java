/*
 $Id: NyxTableColumnModel.java,v 1.1 2003-07-31 13:00:28 mvdb Exp $

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
package org.xulux.nyx.swing.models;

import java.util.Enumeration;

import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableColumnModel.java,v 1.1 2003-07-31 13:00:28 mvdb Exp $
 */
public class NyxTableColumnModel implements TableColumnModel {

    /**
     * 
     */
    public NyxTableColumnModel() {
        super();
    }

    /**
     * @see javax.swing.table.TableColumnModel#addColumn(javax.swing.table.TableColumn)
     */
    public void addColumn(TableColumn aColumn) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#removeColumn(javax.swing.table.TableColumn)
     */
    public void removeColumn(TableColumn column) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#moveColumn(int, int)
     */
    public void moveColumn(int columnIndex, int newIndex) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#setColumnMargin(int)
     */
    public void setColumnMargin(int newMargin) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumnCount()
     */
    public int getColumnCount() {
        return 0;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumns()
     */
    public Enumeration getColumns() {
        return null;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumnIndex(java.lang.Object)
     */
    public int getColumnIndex(Object columnIdentifier) {
        return 0;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumn(int)
     */
    public TableColumn getColumn(int columnIndex) {
        return null;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumnMargin()
     */
    public int getColumnMargin() {
        return 0;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumnIndexAtX(int)
     */
    public int getColumnIndexAtX(int xPosition) {
        return 0;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getTotalColumnWidth()
     */
    public int getTotalColumnWidth() {
        return 0;
    }

    /**
     * @see javax.swing.table.TableColumnModel#setColumnSelectionAllowed(boolean)
     */
    public void setColumnSelectionAllowed(boolean flag) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#getColumnSelectionAllowed()
     */
    public boolean getColumnSelectionAllowed() {
        return false;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getSelectedColumns()
     */
    public int[] getSelectedColumns() {
        return null;
    }

    /**
     * @see javax.swing.table.TableColumnModel#getSelectedColumnCount()
     */
    public int getSelectedColumnCount() {
        return 0;
    }

    /**
     * @see javax.swing.table.TableColumnModel#setSelectionModel(javax.swing.ListSelectionModel)
     */
    public void setSelectionModel(ListSelectionModel newModel) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#getSelectionModel()
     */
    public ListSelectionModel getSelectionModel() {
        return null;
    }

    /**
     * @see javax.swing.table.TableColumnModel#addColumnModelListener(javax.swing.event.TableColumnModelListener)
     */
    public void addColumnModelListener(TableColumnModelListener x) {

    }

    /**
     * @see javax.swing.table.TableColumnModel#removeColumnModelListener(javax.swing.event.TableColumnModelListener)
     */
    public void removeColumnModelListener(TableColumnModelListener x) {

    }

}
