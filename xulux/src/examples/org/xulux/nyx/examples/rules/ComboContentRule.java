package org.xulux.nyx.examples.rules;

import java.util.ArrayList;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.Combo;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: ComboContentRule.java,v 1.2 2002-11-12 00:55:42 mvdb Exp $
 */
public class ComboContentRule extends Rule
{

    /**
     * Constructor for ComboContentRule.
     */
    public ComboContentRule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request)
    {
        System.err.println("request : "+request.getWidget().getName());
        Combo combo = (Combo)request.getWidget();
        String name = "("+combo.getName()+")";
        ArrayList data = new ArrayList();
        data.add("martin "+name);
        data.add("misja "+name);
        data.add("maarten "+name);
        combo.setContent(data);
        combo.setNotSelectedValue("<nothing selected>");
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
    }

}
