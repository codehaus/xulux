package org.xulux.nyx.swing.masks;

import org.xulux.nyx.utils.Resources;

/**
 * A simple example implementation of an IMask.
 * It checks if a value is a String and if not will generate a message.
 * 
 * @author Martin van den Bemt
 * @version $Id: StringMask.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
 */
public class StringMask implements IMask
{
    private static String preMessage = Resources.getResource(StringMask.class, "defaultmessage"); //$NON-NLS-1$
    private boolean valueOk = false;

    /**
     * Constructor for StringMask.
     */
    public StringMask()
    {
    }

    /**
     * @see nl.tocure.swing.masks.IMask#getMessage(String)
     */
    public String getMessage(String label)
    {
        if (!valueOk)
        {
            return preMessage+" "+label; //$NON-NLS-1$
        }
        return "";
    }

    /**
     * @see nl.tocure.swing.masks.IMask#isValueOk(Object)
     */
    public boolean isValueOk(Object value)
    {
        valueOk = (value instanceof String);
        return valueOk;
    }

}
