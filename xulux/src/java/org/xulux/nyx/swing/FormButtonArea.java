package org.xulux.nyx.swing;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * The area where the buttons of the form reside
 * 
 * @author Martin van den Bemt
 * @version $Id: FormButtonArea.java,v 1.1 2002-10-23 00:28:43 mvdb Exp $
 */
public class FormButtonArea extends JPanel
{

    /**
     * Constructor for FormButtonArea.
     * @param layout
     * @param isDoubleBuffered
     */
    public FormButtonArea(LayoutManager layout, boolean isDoubleBuffered)
    {
        super(layout, isDoubleBuffered);
    }

    /**
     * Constructor for FormButtonArea.
     * @param layout
     */
    public FormButtonArea(LayoutManager layout)
    {
        super(layout);
    }

    /**
     * Constructor for FormButtonArea.
     * @param isDoubleBuffered
     */
    public FormButtonArea(boolean isDoubleBuffered)
    {
        super(isDoubleBuffered);
    }

    /**
     * Constructor for FormButtonArea.
     */
    public FormButtonArea()
    {
        super();
    }

}
