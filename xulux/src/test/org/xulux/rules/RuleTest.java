/*
   $Id: RuleTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
   
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
package org.xulux.rules;

import org.xulux.context.PartRequest;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The testcase for the rule abstract
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RuleTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
 */
public class RuleTest extends TestCase {

    /**
     * Constructor for RuleTest.
     * @param name the name of the test
     */
    public RuleTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(RuleTest.class);
        return suite;
    }

    /**
     * Test the rule abstract.
     *
     */
    public void testRule() {
        System.out.println("testRule");
        InternalRule rule = new InternalRule();
        rule.pre(null);
        rule.post(null);
        rule.deregisterPartName(null);
        Rule.debug();
        rule.destroy();
        rule.init();
        rule.execute(null);
        assertNull(rule.getOwner());
        assertEquals(0, rule.getUseCount());
        assertFalse(rule.isRegistered(null));
    }

    /**
     * Test the set and getowner
     */
    public void testOwner() {
        System.out.println("testOwner");
        InternalRule rule = new InternalRule();
        MockWidget widget = new MockWidget("owner");
        rule.setOwner(widget);
        assertEquals(widget, rule.getOwner());
    }

    /**
     * Test the partname registration
     * deregistration and handling.
     */
    public void testPartNames() {
        System.out.println("testPartNames");
        InternalRule rule = new InternalRule();
        rule.registerPartName("martin");
        assertEquals(1, rule.initCalled);
        rule.registerPartName("martin");
        assertEquals(1, rule.initCalled);
        assertTrue(rule.isRegistered("martin"));
        assertEquals(1, rule.getUseCount());
        rule.registerPartName("stacy");
        assertTrue(rule.isRegistered("stacy"));
        assertEquals(2, rule.getUseCount());
        Rule.debug();
        rule.deregisterPartName("martin");
        assertEquals(1, rule.getUseCount());
        assertFalse(rule.isRegistered("martin"));
        rule.deregisterPartName("martin");
        rule.deregisterPartName("stacy");
        assertEquals(0, rule.getUseCount());
        assertEquals(1, rule.destroyCalled);
        assertEquals(1, rule.initCalled);
    }

    /**
     * The rule to test.
     */
    public class InternalRule extends Rule {

        /**
         * nr of times init got called
         */
        public int initCalled = 0;
        /**
         * number of times destroy got called
         */
        public int destroyCalled = 0;

        /**
         * @see org.xulux.nyx.rules.Rule#pre(org.xulux.nyx.context.PartRequest)
         */
        public void pre(PartRequest request) {
        }

        /**
         * @see org.xulux.nyx.rules.Rule#post(org.xulux.nyx.context.PartRequest)
         */
        public void post(PartRequest request) {
        }
        /**
         * @see org.xulux.nyx.rules.IRule#destroy()
         */
        public void destroy() {
            destroyCalled++;
            super.destroy();
        }

        /**
         * @see org.xulux.nyx.rules.IRule#init()
         */
        public void init() {
            initCalled++;
            super.init();
        }

    }
}
