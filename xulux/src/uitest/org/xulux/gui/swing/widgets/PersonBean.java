/*
   $Id: PersonBean.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

/**
 * A bean to test the gui functionality
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PersonBean.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class PersonBean {

    /**
     * the firstname
     */
    private String firstName;
    /**
     * the lastname
     */
    private String lastName;

    /**
     * @param firstName the firstname
     * @param lastName the lastname
     */
    public PersonBean(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return the firstname
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the lastname
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return the fullname
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * @param lastName the lastname
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getFirstName() + " : " + super.toString();
    }
}
