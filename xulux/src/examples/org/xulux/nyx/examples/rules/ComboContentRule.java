package org.xulux.nyx.examples.rules;

import java.util.ArrayList;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.Combo;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: ComboContentRule.java,v 1.3 2002-11-12 17:16:42 mvdb Exp $
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
        data.add(new Person("martin", "van den Bemt"));
        data.add(new Person("misja","Alma"));
        data.add(new Person("maarten", "Spook"));
        combo.setContent(data);
        combo.setNotSelectedValue("<nothing selected>");
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
    }
    
    public class Person
    {
        private String firstName;
        private String lastName;
        
        public Person(String firstName, String lastName)
        {
            setFirstName(firstName);
            setLastName(lastName);
        }
        /**
         * Returns the firstName.
         * @return String
         */
        public String getFirstName()
        {
            return firstName;
        }

        /**
         * Returns the lastName.
         * @return String
         */
        public String getLastName()
        {
            return lastName;
        }

        /**
         * Sets the firstName.
         * @param firstName The firstName to set
         */
        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        /**
         * Sets the lastName.
         * @param lastName The lastName to set
         */
        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }
        
    }
}
