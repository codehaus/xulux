/*
   $Id: NyxJTable.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
   
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
package org.xulux.swing.extensions;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * The nyx replacement for a table.
 * Trying to prevent a lot of unnecessary processing of
 * things we'll never use..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJTable.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
 */
public class NyxJTable extends JTable {

    /**
     * the sibling table
     */
    private NyxJTable siblingTable;
    /**
     * is the table changing
     */
    private boolean changing;

    /**
     *
     */
    public NyxJTable() {
        super();
    }

    /**
     * @param dm the tablemodel
     * @param cm the columnmodel
     */
    public NyxJTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    /**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     */
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        if (getSiblingTable() != null && !getSiblingTable().isChanging()) {
            changing = true;
            getSiblingTable().changeSelection(rowIndex, columnIndex, toggle, extend);
            changing = false;
        }
    }

    /**
     * @return the sublingtable or null if non present.
     */
    public NyxJTable getSiblingTable() {
        return siblingTable;
    }

    /**
     * @param siblingTable - the table that should behave exactly like the other..
     */
    public void setSiblingTable(NyxJTable siblingTable) {
        this.siblingTable = siblingTable;
    }

    /**
     * @return if the table is currently changing.
     */
    public boolean isChanging() {
        return changing;
    }

    /**
     * @see javax.swing.JTable#setColumnSelectionInterval(int, int)
     */
    public void setColumnSelectionInterval(int index0, int index1) {
        // siblingtable can have a different number of columns,
        // so check to see if this is the case and set the index1 to
        // the current number of columns-1 (starts from 0).
        if (index1 >= getColumnCount()) {
            index1 = getColumnCount() - 1;
        }
        super.setColumnSelectionInterval(index0, index1);
        if (getSiblingTable() != null && !getSiblingTable().isChanging()) {
            changing = true;
            getSiblingTable().setColumnSelectionInterval(index0, index1);
            changing = false;
        }
    }

    /**
     * @see javax.swing.JTable#setRowSelectionInterval(int, int)
     */
    public void setRowSelectionInterval(int index0, int index1) {
        super.setRowSelectionInterval(index0, index1);
        if (getSiblingTable() != null && !getSiblingTable().isChanging()) {
            changing = true;
            getSiblingTable().setRowSelectionInterval(index0, index1);
            changing = false;
        }
    }

}
