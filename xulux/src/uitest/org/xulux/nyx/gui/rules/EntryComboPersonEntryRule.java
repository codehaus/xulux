package org.xulux.nyx.gui.rules;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.PersonBean;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: EntryComboPersonEntryRule.java,v 1.1.2.1 2003-04-29 16:52:47 mvdb Exp $
 */
public class EntryComboPersonEntryRule extends Rule
{

    /**
     * Constructor for EntryComboPersonEntryRule.
     */
    public EntryComboPersonEntryRule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request)
    {
        PersonBean bean = (PersonBean)request.getPart().getGuiValue("PersonList");
        System.err.println("Setting value to "+bean);
        request.getPart().setGuiValue("PersonEntry", bean);
        
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
    }

}
