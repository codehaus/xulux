/*
 $Id: DefaultComboModelTest.java,v 1.3 2003-05-21 10:00:14 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.
 
 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.
 
 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.
 
 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project.  For written permission,
    please contact martin@mvdb.net.
 
 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.
 
 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).
 
 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.swing.models;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.gui.swing.Combo;

/**
 * Tests the swing defaultcombomodel
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultComboModelTest.java,v 1.3 2003-05-21 10:00:14 mvdb Exp $
 */
public class DefaultComboModelTest extends TestCase
{
    ArrayList persons;
    Combo combo;

    /**
     * Constructor for DefaultComboModelTest.
     * @param arg0
     */
    public DefaultComboModelTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(DefaultComboModelTest.class);
        return suite;
    }

    public void testConstructor()
    {
        combo = new Combo("comboTest");
        DefaultComboModel model =
            new DefaultComboModel(persons, "firstName", combo);
        assertEquals(3, model.getSize());
        assertEquals(-1, model.getSelectedIndex());
        assertNull(model.getSelectedItem());
        // set the selecteditem to 0
        model.setSelectedItem(0);
        assertEquals("Martin", model.getSelectedItem().toString());
        assertEquals(0, model.getSelectedIndex());

        model.setSelectedItem(persons.get(1));
        assertEquals(1, model.getSelectedIndex());
        assertEquals(persons.get(1), model.getRealSelectedValue());
        DefaultComboModel.ComboShowable show =
            (DefaultComboModel.ComboShowable) model.getComboObject(0);
        assertEquals(0, show.getIndex());
        assertEquals("Martin", show.toString());

        model.destroy();
        assertEquals(-1,model.getSelectedIndex());
        assertEquals(0,model.getSize());
        assertNull(model.getSelectedItem());
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        persons = new ArrayList();
        persons.add(new Person("Martin", "van den Bemt", "Teteringen"));
        persons.add(new Person("Kermit", "the Frogg", "MuppetCity"));
        persons.add(new Person("Doe", "John", "Nowhere"));
    }

    /**
     * A Person object to be used in the tests
     */
    public class Person
    {

        private String lastName;
        private String firstName;
        private String city;

        public Person(String firstName, String lastName, String city)
        {
            setFirstName(firstName);
            setLastName(lastName);
            setCity(city);
        }

        /**
         * Returns the city.
         * @return String
         */
        public String getCity()
        {
            return city;
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
         * Sets the city.
         * @param city The city to set
         */
        public void setCity(String city)
        {
            this.city = city;
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
