package org.xulux.nyx.swing;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * This overrides the default JComboBox.
 * The problem it solves is that when setting
 * a new model in the Combo class, it would fire
 * an action event, which would trigger another
 * firing of all pre requests (and possibly nulling values!)
 * This prevents that situation.
 * 
 * @author Martin van den Bemt
 * @version $Id: NyxComboBox.java,v 1.1 2002-12-03 17:12:00 mvdb Exp $
 */
public class NyxComboBox extends JComboBox
{

    private static boolean newModelIsSet = false;

    /**
     * Constructor for NyxComboBox.
     */
    public NyxComboBox()
    {
        super();
    }

    /**
     * Set a setting so that we now a new model is set
     * and no action events should be fired
     * @param model - the model
     */
    public void setModel(ComboBoxModel model)
    {
        newModelIsSet = true;
        super.setModel(model);
        newModelIsSet = false;
    }
    /**
     * Only calls the selectedItem when we are not
     * setting a new model
     * @param object - the selectedItem
     */
    public void setSelectedItem(Object object)
    {
        if (!newModelIsSet)
        {
            super.setSelectedItem(object);
        }
    }

}
