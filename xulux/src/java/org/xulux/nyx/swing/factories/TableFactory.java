package org.xulux.nyx.swing.factories;

import java.util.ArrayList;

import javax.swing.JTable;

import org.xulux.nyx.guidefaults.GuiDefaults;
import org.xulux.nyx.swing.BaseTable;

/**
 * This factory creates a table based on the data provided
 * (in tha arraylist, which contains records from the 
 * tcBase classes)
 * 
 * @author Martin van den Bemt
 * @version $Id: TableFactory.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
 */
public class TableFactory
{

    /**
     * Constructor for TableFactory.
     */
    public TableFactory()
    {
        super();
    }
    
    /**
     * Will use the settings from the GuiDefaults, for all other
     * @see getTable(ArrayList, String, Class)
     */
    public static synchronized BaseTable getTable(ArrayList data, String resource)
    {
        return getTable(data, resource, GuiDefaults.class);
    }
    /**
     * Creates an appropiate table based on the specified parameters
     * Currently it is .properties oriented
     * @param data
     * @param resource
     * @param clazz - from which class 
     */
    public static synchronized BaseTable getTable(ArrayList data, String resource, Class clazz)
    {
        return null;
    }

}
