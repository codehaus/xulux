/*
   $Id: DefaultComboModelTest.java,v 1.5 2004-05-17 16:30:21 mvdb Exp $
   
   Copyright 2002-2004 The Xulux Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.xulux.swing.models;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.core.ApplicationPart;
import org.xulux.swing.widgets.Combo;

/**
 * Tests the swing defaultcombomodel
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultComboModelTest.java,v 1.5 2004-05-17 16:30:21 mvdb Exp $
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
        DefaultComboModel model = new DefaultComboModel(persons, "firstName,!", combo);
        assertEquals(3, model.getSize());
        assertEquals(-1, model.getSelectedIndex());
        assertNull(model.getSelectedItem());
        assertEquals(DefaultComboModel.ComboShowable.class, model.getElementAt(0).getClass());
        assertEquals(((Person) persons.get(0)).firstName + "!", model.getElementAt(0).toString());
        // set the selecteditem to 0
        model.setSelectedItem(0);
        assertEquals("Martin!", model.getSelectedItem().toString());
        assertEquals(0, model.getSelectedIndex());

        model.setSelectedItem(persons.get(1));
        assertEquals(1, model.getSelectedIndex());
        assertEquals(persons.get(1), model.getRealSelectedValue());
        DefaultComboModel.ComboShowable show = (DefaultComboModel.ComboShowable) model.getElementAt(0);
        assertEquals(0, show.getIndex());
        assertEquals("Martin!", show.toString());

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
        // combo needs to be initialized before it can set content
        combo.initialize();
        DefaultComboModel model = new DefaultComboModel(persons, null, combo);
        model.setSelectedItem(persons.get(0));
        assertEquals(persons.get(0).toString(), model.getSelectedItem().toString());
        combo.setContent(persons);
        System.out.println("Not selected value : " + combo.getNotSelectedValue());
        combo.setNotSelectedValue("notselected");
        System.out.println("Not selected value : " + combo.getNotSelectedValue());
        System.out.println("Persons : " + persons);
        System.out.println("Model : " + model.getRealSelectedValue());
        model.initialize();
        System.out.println("m : " + model.getList());
        System.out.println("o : " + model.getOriginal());
        assertEquals("notselected", ((List) combo.getContent()).get(0));
        combo.setValue(null);
        assertEquals("notselected", combo.getModel().getSelectedItem().toString());
        assertEquals(0, combo.getModel().getSelectedIndex());
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
