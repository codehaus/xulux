package org.xulux.nyx.swing;

import javax.swing.JTable;

/**
 * The basetable to use for all table implementation
 * (eg Observation table, SimpleTable, etc).
 * Most of the logic is contained in here to adjust the tables
 * gui.
 * 
 * @author Martin van den Bemt
 * @version $Id: BaseTable.java,v 1.1 2002-10-23 00:28:43 mvdb Exp $
 */
public abstract class BaseTable extends JTable
{

    /**
     * Constructor for BaseTable.
     */
    public BaseTable()
    {
        super();
    }


}
