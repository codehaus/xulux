package org.xulux.nyx.gui.rules;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.PersonBean;
import org.xulux.nyx.gui.swing.Entry;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: PersonEntryRule.java,v 1.2 2003-01-08 02:37:06 mvdb Exp $
 */
public class PersonEntryRule extends Rule
{

    /**
     * Constructor for PersonEntryRule.
     */
    public PersonEntryRule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request)
    {
        Object personBeanValue = request.getPart().getWidget("PersonBeanEntry").getValue();
        System.out.println("personBeanValue : "+personBeanValue);
        if (!(personBeanValue instanceof PersonBean))
        {
            System.out.println("Invalid object returned");
        }
        Object stringValue = request.getPart().getWidget("String").getValue();
        System.out.println("stringValue : "+stringValue);
        if (!(stringValue instanceof String))
        {
            System.out.println("invalid object returned");
        }
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
    }

}
