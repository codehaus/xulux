package org.xulux.nyx.examples.rules;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: ComboValueChangedRule.java,v 1.2 2002-11-11 09:49:22 mvdb Exp $
 */
public class ComboValueChangedRule extends Rule
{

    /**
     * Constructor for ComboValueChangedRule.
     */
    public ComboValueChangedRule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request)
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
        System.err.println("Widget : "+request.getWidget().getName());
        System.err.println("Value : "+request.getValue());
        request.getPart().select("SpecialismeCode");
    }

}
