/*
   $Id: DefaultPartRuleTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
   
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

import org.xulux.context.ApplicationPart;
import org.xulux.context.PartRequest;
import org.xulux.rules.impl.PartRequestImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the defaultpartrule
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultPartRuleTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
 */
public class DefaultPartRuleTest extends TestCase {

    /**
     * Constructor for DefaultPartRuleTest.
     * @param name the name of the test
     */
    public DefaultPartRuleTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DefaultPartRuleTest.class);
        return suite;
    }

    /**
     * The defaultpartrule test
     *
     */
    public void testDefaultPartRule() {
        System.out.println("testDefaultPartRule");
        Part part = new Part();
        PartRequestImpl impl = new PartRequestImpl(part, PartRequest.NO_ACTION);
        DefaultPartRule rule = new DefaultPartRule();
        rule.pre(impl);
        rule.post(impl);
        assertEquals(1, part.count);

    }
    /**
     * The extended part
     */
    public class Part extends ApplicationPart {
        /**
         * The initialize count
         */
        public int count = 0;
        /**
         * @see org.xulux.nyx.context.ApplicationPart#initialize(java.lang.Object)
         */
        public void initialize(Object caller) {
            count++;
        }
    }
}
