package org.xulux.nyx.swing;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.xulux.nyx.swing.factories.FieldCollection;

/**
 * A simpleForm which contructs a for based on the fields
 * 
 * @author Martin van den Bemt
 * @version $Id: SimpleForm.java,v 1.3 2002-10-23 22:34:38 mvdb Exp $
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
        //super();
    }
    
    public SimpleForm(FieldCollection fields)
    {
        //super();
        setFields(fields);
    }
    
    /** 
     * Sets the fields in the form
     * With this it will remove all currently know fields 
     * from the form and rebuild the form.
     * For now only 1 field area is supported
     * @param fields - the fields to use in the form
     */
    public void setFields(FieldCollection fields)
    {
        this.fields = fields;
        FormFieldArea fieldArea = null;
        Component[] components = findComponent(FormFieldArea.class, this.getComponent());
        if (components != null)
        {
            for (int i=0; i < components.length; i++)
            {
                ((FormFieldArea)components[i]).removeAllFields();
            }
        }
        if (fieldArea == null)
        {
            fieldArea = new FormFieldArea();
        }
        getComponent().add(fieldArea);
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
        if (getComponent() == null)
        {
            return null;
        }
        Component[] components = getComponent().getComponents();
        log.trace("components length : "+components.length);
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
            else if (components[i] instanceof JRootPane)
            {
                // every component has a rootpane, so we will end
                // up in an infinite loop
                continue;
            }
            else 
            {
                log.trace("component is instanceof "+components[i]);
                
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
