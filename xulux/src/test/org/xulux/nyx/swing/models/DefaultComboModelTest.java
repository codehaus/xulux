/*
 $Id: DefaultComboModelTest.java,v 1.6 2003-11-27 19:39:19 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
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

import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.swing.widgets.Combo;

/**
 * Tests the swing defaultcombomodel
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultComboModelTest.java,v 1.6 2003-11-27 19:39:19 mvdb Exp $
 */
public class DefaultComboModelTest extends TestCase {

    /**
     * List with persons
     */
    private ArrayList persons;
    /**
     * The combo
     */
    private Combo combo;

    /**
     * Constructor for DefaultComboModelTest.
     * @param name the name of the test
     */
    public DefaultComboModelTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DefaultComboModelTest.class);
        return suite;
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        persons = new ArrayList();
        persons.add(new Person("Martin", "van den Bemt", "Teteringen"));
        persons.add(new Person("Kermit", "the Frogg", "MuppetCity"));
        persons.add(new Person("Doe", "John", "Nowhere"));
    }

    /**
     * Test the constructor
     */
    public void testConstructor() {
        System.out.println("testConstructor");
        combo = new Combo("comboTest");
        DefaultComboModel model = new DefaultComboModel(persons, "firstName", combo);
        assertEquals(3, model.getSize());
        assertEquals(-1, model.getSelectedIndex());
        assertNull(model.getSelectedItem());
        assertEquals(DefaultComboModel.ComboShowable.class, model.getElementAt(0).getClass());
        assertEquals(((Person) persons.get(0)).firstName, model.getElementAt(0).toString());
        // set the selecteditem to 0
        model.setSelectedItem(0);
        assertEquals("Martin", model.getSelectedItem().toString());
        assertEquals(0, model.getSelectedIndex());

        model.setSelectedItem(persons.get(1));
        assertEquals(1, model.getSelectedIndex());
        assertEquals(persons.get(1), model.getRealSelectedValue());
        DefaultComboModel.ComboShowable show = (DefaultComboModel.ComboShowable) model.getElementAt(0);
        assertEquals(0, show.getIndex());
        assertEquals("Martin", show.toString());

        model.destroy();
        assertEquals(-1, model.getSelectedIndex());
        assertEquals(0, model.getSize());
        assertNull(model.getSelectedItem());

        // test the default constructor
        model = new DefaultComboModel();
        assertEquals(0, model.getSize());
    }

    /**
     * Test the getRealSelectedValue method
     * and the setRealSelectedValue method
     */
    public void testGetRealSelectedValue() {
        System.out.println("testGetRealSelectedValue");
        DefaultComboModel model = new DefaultComboModel();
        assertNull(model.getRealSelectedValue());
        model.setRealSelectedValue(null);
        assertNull(model.getRealSelectedValue());
        combo = new Combo("combo");
        model = new DefaultComboModel(persons, "firstName", combo);
        assertEquals(persons.get(0), model.getRealSelectedValue());
        model.setRealSelectedValue(persons.get(2));
        assertEquals(persons.get(2), model.getRealSelectedValue());
        model.setRealSelectedValue(null);
        assertEquals(persons.get(0), model.getRealSelectedValue());
        Person person = new Person("first", "last", "city");
        model.setRealSelectedValue(persons.get(1));
        model.setRealSelectedValue(person);
        assertEquals(persons.get(0), model.getRealSelectedValue());
        assertEquals(combo.getValue(), model.getRealSelectedValue());
    }

    /**
     * Test the getSelectedIndex method
     */
    public void testGetSelectedIndex() {
        System.out.println("testGetSelectedIndex");
        DefaultComboModel model = new DefaultComboModel();
        model.setSelectedItem(100);
        assertEquals(-1, model.getSelectedIndex());
        combo = new Combo("combo");
        model = new DefaultComboModel(persons, "firstName", combo);
        assertEquals(-1, model.getSelectedIndex());
        model.setSelectedItem(1);
        assertEquals(1, model.getSelectedIndex());
        model.setSelectedItem(1000);
        assertEquals(1, model.getSelectedIndex());
    }

    /**
     * Test the initialization process
     */
    public void testInitialize() {
        System.out.println("testInitialize");
        combo = new Combo("combo");
        combo.setPart(new ApplicationPart("testInitialize"));
        DefaultComboModel model = new DefaultComboModel(persons, null, combo);
        model.setSelectedItem(persons.get(0));
        assertEquals(persons.get(0).toString(), model.getSelectedItem().toString());
        combo.setContent(persons);
        combo.setNotSelectedValue("notselected");
        combo.refresh();
        assertEquals("notselected", model.getRealSelectedValue());
    }

    /**
     * A Person object to be used in the tests
     */
    public class Person {

        /**
         * the lastname
         */
        private String lastName;
        /**
         * the firstname
         */
        private String firstName;
        /**
         * the city
         */
        private String city;

        /**
         * the constructor
         * @param firstName firstName
         * @param lastName lastName
         * @param city city
         */
        public Person(String firstName, String lastName, String city) {
            setFirstName(firstName);
            setLastName(lastName);
            setCity(city);
        }

        /**
         * Returns the city.
         * @return String
         */
        public String getCity() {
            return city;
        }

        /**
         * Returns the firstName.
         * @return String
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Returns the lastName.
         * @return String
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Sets the city.
         * @param city The city to set
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * Sets the firstName.
         * @param firstName The firstName to set
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * Sets the lastName.
         * @param lastName The lastName to set
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

    }
}
