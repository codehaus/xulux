/*
 $Id: RuleTest.java,v 1.1 2003-12-18 00:17:32 mvdb Exp $

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
 * @version $Id: RuleTest.java,v 1.1 2003-12-18 00:17:32 mvdb Exp $
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
