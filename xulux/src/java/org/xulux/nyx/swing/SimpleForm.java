package org.xulux.nyx.swing;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.xulux.nyx.swing.factories.FieldCollection;

/**
 * A simpleForm which contructs a for based on the fields
 * 
 * @author Martin van den Bemt
 * @version $Id: SimpleForm.java,v 1.2 2002-10-23 14:39:39 mvdb Exp $
 */
public class SimpleForm 
extends BaseForm 
{
    private static Log log = LogFactory.getLog(SimpleForm.class);
    private FieldCollection fields;

    /**
     * Constructor for SimpleForm.
     */
    public SimpleForm()
    {
        super();
    }
    
    public SimpleForm(FieldCollection fields)
    {
        super();
        setFields(fields);
    }
    
    /** 
     * Sets the fields in the form
     * With this it will remove all currently know fields 
     * from the and rebuild the form.
     * For now only 1 field area is supported
     */
    public void setFields(FieldCollection fields)
    {
        this.fields = fields;
        FormFieldArea fieldArea = null;
/*        Component[] components = findComponent(FormFieldArea.class, this);
        for (int i=0; i < components.length; i++)
        {
            ((FormFieldArea)components[i]).removeAllFields();
        }
        */
        if (fieldArea == null)
        {
            fieldArea = new FormFieldArea();
        }
        getContentPane().add(fieldArea);
    }
    
    /**
     * Finds a certain of type of component
     * @param type - the type of component to find
     * @return Component[] array or null when not found.
     */
    private Component[] findComponent(Class type, Component component)
    {
        // initialize with on ecomponent
        Component[] cs = null;
        Component[] components = getComponents();
        for (int i=0; i < components.length; i++)
        {
            if (components[i] instanceof FormFieldArea)
            {
                if (cs == null) 
                {
                    cs = new Component[] { (FormFieldArea)components[i]};
                }
                continue;
            }
            else 
            {
                Component[] csToMerge = findComponent(type, components[i]);
                if (cs == null) 
                {
                    // just replace cs, since cs is still null
                    cs = csToMerge;
                    continue;
                }
                if (csToMerge != null)
                {
                    Component[] csTmp = cs;
                    int length = csTmp.length+csToMerge.length;
                    cs = new Component[length];
                    int dstPosition = 0;
                    System.arraycopy(csTmp, 0, cs, dstPosition, csTmp.length);
                    dstPosition+=csTmp.length;
                    System.arraycopy(csToMerge,0, cs, dstPosition, csToMerge.length);
                }
            }
        }
        return cs;
    }
}
