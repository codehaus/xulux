/*
   $Id: PersonCollection.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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

import java.util.ArrayList;
import java.util.List;

/**
 * A collection class for eg a table.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PersonCollection.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class PersonCollection {

    /**
     * The list with persons
     */
    private List list;

    /**
     *
     */
    public PersonCollection() {
    }

    /**
     * @param person the person to add
     */
    public void addPerson(PersonBean person) {
        if (list == null) {
            list = new ArrayList();
        }
        list.add(person);
    }

    /**
     * @return the persons
     */
    public List getPersons() {
        return this.list;
    }

    /**
     * @param list the list with persons
     */
    public void setPersonList(List list) {
        this.list = list;
    }

}
