/*
 $Id: DefaultTableRuleTest.java,v 1.1 2003-12-28 23:34:57 mvdb Exp $

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
 * @version $Id: DefaultTableRuleTest.java,v 1.1 2003-12-28 23:34:57 mvdb Exp $
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
