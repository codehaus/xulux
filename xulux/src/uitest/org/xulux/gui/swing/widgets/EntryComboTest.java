/*
   $Id: EntryComboTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
   
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.context.ApplicationPart;
import org.xulux.gui.PartCreator;

/**
 * Tests combination of entry and combo
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: EntryComboTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
 */
public class EntryComboTest extends TestCase {

    /**
     * Constructor for EntryComboTest.
     * @param name the name of the test
     */
    public EntryComboTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(EntryTest.class);
        return suite;
    }

    /**
     * Test a simple entry combo combination
     */
    public void testSimpleEntryCombo() {
        PersonBean person = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/gui/swing/widgets/EntryComboTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(person, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new EntryComboTest("EntryComboTest").testSimpleEntryCombo();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
