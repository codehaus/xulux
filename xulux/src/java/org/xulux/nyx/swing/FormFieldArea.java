package org.xulux.nyx.swing;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * The area where the fields of the form reside
 * 
 * @author Martin van den Bemt
 * @version $Id: FormFieldArea.java,v 1.1 2002-10-23 00:28:43 mvdb Exp $
 */
public class FormFieldArea extends JPanel
{

    /**
     * Constructor for FormFieldArea.
     * @param layout
     * @param isDoubleBuffered
     */
    public FormFieldArea(LayoutManager layout, boolean isDoubleBuffered)
    {
        super(layout, isDoubleBuffered);
    }

    /**
     * Constructor for FormFieldArea.
     * @param layout
     */
    public FormFieldArea(LayoutManager layout)
    {
        super(layout);
    }

    /**
     * Constructor for FormFieldArea.
     * @param isDoubleBuffered
     */
    public FormFieldArea(boolean isDoubleBuffered)
    {
        super(isDoubleBuffered);
    }

    /**
     * Constructor for FormFieldArea.
     */
    public FormFieldArea()
    {
        super();
    }
    
    /**
     * Removes all fields from the area
     */
    public void removeAllFields()
    {
        Component[] children = getComponents();
        for (int i=0; i < children.length; i++)
        {
            remove(children[i]);
        }
    }

}
