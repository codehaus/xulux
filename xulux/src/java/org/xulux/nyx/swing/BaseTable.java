package org.xulux.nyx.swing;

import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.JTable;

/**
 * The basetable to use for all table implementation
 * (eg Observation table, SimpleTable, etc).
 * Most of the logic is contained in here to adjust the tables
 * gui.
 * 
 * @author Martin van den Bemt
 * @version $Id: BaseTable.java,v 1.2 2002-10-23 22:34:38 mvdb Exp $
 */
public abstract class BaseTable implements IComponent
{

    /**
     * Constructor for BaseTable.
     */
    public BaseTable()
    {
        super();
    }


    /**
     * @see org.xulux.nyx.swing.IComponent#addToComponent(JComponent)
     */
    public void addToComponent(JComponent component)
    {
    }

    /**
     * @see org.xulux.nyx.swing.IComponent#addToWindow(Window)
     */
    public void addToWindow(Window window)
    {
    }

    /**
     * @see org.xulux.nyx.swing.IComponent#getComponent()
     */
    public JComponent getComponent()
    {
        return null;
    }

}
