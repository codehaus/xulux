package org.xulux.nyx.gui.rules;

import java.util.ArrayList;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.Combo;
import org.xulux.nyx.gui.Entry;
import org.xulux.nyx.gui.PersonBean;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: MainEntryComboRule.java,v 1.1 2002-12-03 17:12:01 mvdb Exp $
 */
public class MainEntryComboRule extends Rule
{

    /**
     * Constructor for MainEntryComboTest.
     */
    public MainEntryComboRule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request)
    {
        Entry entry = (Entry) request.getPart().getWidget("PersonEntry");
        System.out.println("Setting value to : "+request.getPart().getBean());
        entry.setValue(request.getPart().getBean());
        Combo combo = (Combo)request.getPart().getWidget("PersonList");
        ArrayList list = createContent();
        combo.setContent(list);
        combo.setValue(list.get(2));
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
    }
    
    public ArrayList createContent()
    {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("John", "Doe"));
        list.add(new PersonBean("Jane", "Tarzan"));
        list.add(new PersonBean("Martin", "van den Bemt"));
        return list;
    }

}
