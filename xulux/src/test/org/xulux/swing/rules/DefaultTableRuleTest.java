/*
   $Id: DefaultTableRuleTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
   
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
package org.xulux.swing.rules;

import org.xulux.context.PartRequest;
import org.xulux.rules.impl.WidgetRequestImpl;
import org.xulux.swing.widgets.Table;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The defaultTableRule test.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultTableRuleTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
 */
public class DefaultTableRuleTest extends TestCase {

    /**
     * Constructor for DefaultTableRuleTest.
     * @param name the name of the test
     */
    public DefaultTableRuleTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DefaultTableRuleTest.class);
        return suite;
    }

    /**
     * Test the pre method.
     */
    public void testPre() {
        System.out.println("testPre");
        InnerTable table = new InnerTable("innertable");
        // action is null
        WidgetRequestImpl impl = new WidgetRequestImpl(table, PartRequest.NO_ACTION);
        DefaultTableRule rule = new DefaultTableRule();
        rule.setOwner(table);
        rule.pre(impl);
        assertEquals(0, table.stopCount);
        // invalid action
        table.setProperty("defaultaction", "bogus");
        rule.pre(impl);
        assertEquals(0, table.stopCount);
        // save action
        table.setProperty("defaultaction", "save");
        rule.pre(impl);
        assertEquals(1, table.stopCount);
    }

    /**
     * Test the post method.
     */
    public void testPost() {
        System.out.println("testPost");
        new DefaultTableRule().post(null);
    }

    public class InnerTable extends Table {

        public int stopCount = 0;
        /**
         * @param name
         */
        public InnerTable(String name) {
            super(name);
        }
        /**
         * @see org.xulux.swing.widgets.Table#stopEditing()
         */
        public void stopEditing() {
            stopCount++;
        }

    }
}
