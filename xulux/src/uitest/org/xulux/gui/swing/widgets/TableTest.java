/*
   $Id: TableTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
   
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

import java.io.InputStream;
import java.util.ArrayList;

import org.xulux.core.ApplicationPart;
import org.xulux.gui.PartCreator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Testcase for the table..
 * We keep it simple for now.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TableTest.java,v 1.4 2004-03-16 15:04:15 mvdb Exp $
 */
public class TableTest extends TestCase {

    /**
     * Constructor for TableTest.
     * @param name the name of the test
     */
    public TableTest(String name) {
        super(name);
    }
    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TableTest.class);
        return suite;
    }

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        new TableTest("TableTest").showSimpleTable();
    }

    /**
     * Show the simple table
     */
    public void showSimpleTable() {
        PersonCollection persons = new PersonCollection();
        persons.setPersonList(getData());
        String xml = "org/xulux/gui/swing/widgets/TableTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(persons, stream);
        part.activate();
    }

    /**
     * @return data
     */
    public ArrayList getData() {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("Martin", "van den Bemt"));
        list.add(new PersonBean("Misja", "Alma"));
        list.add(new PersonBean("Arthur", "Scrhijer"));
        list.add(new PersonBean("Remko", "Nienhuis"));
        list.add(new PersonBean("Maarten", "Spook"));
        return list;
    }
}
