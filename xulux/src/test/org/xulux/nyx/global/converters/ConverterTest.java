/*
 $Id: ConverterTest.java,v 1.3 2003-11-25 16:25:10 mvdb Exp $

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
package org.xulux.nyx.global.converters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the default converters.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ConverterTest.java,v 1.3 2003-11-25 16:25:10 mvdb Exp $
 */
public class ConverterTest extends TestCase {

    /**
     * Constructor for ConverterTest.
     * @param name the testname
     */
    public ConverterTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ConverterTest.class);
        return suite;
    }

    /**
     * Test the integer converter
     */
    public void testIntegerConverter() {
        System.out.println("testIntegerConverter");
        IntegerConverter c = new IntegerConverter();
        Integer i = new Integer(10);
        assertEquals("10", c.getGuiValue(i));
        assertEquals(new Integer(10), c.getBeanValue("10"));
        assertNull(c.getGuiValue(null));
        assertNull(c.getGuiValue("null"));
        assertNull(c.getBeanValue(null));
        assertNull(c.getBeanValue("10a"));
    }

    /**
     * Test the integer converter
     */
    public void testDoubleConverter() {
        System.out.println("testDoubleConverter");
        DoubleConverter c = new DoubleConverter();
        Double i = new Double(10);
        assertEquals("10.0", c.getGuiValue(i));
        assertEquals(new Double(10), c.getBeanValue("10"));
        assertNull(c.getGuiValue(null));
        assertNull(c.getGuiValue("null"));
        assertNull(c.getBeanValue(null));
        assertNull(c.getBeanValue("10a"));
    }

    /**
     * Test the default converter.
     *
     */
    public void testDefaultConverter() {
        System.out.println("testDefaultConverter");
        DefaultConverter c = new DefaultConverter();
        assertEquals(c, c.getBeanValue(c));
        assertNull(c.getBeanValue(null));
        assertNull(c.getGuiValue(null));
        assertEquals(c.toString(), c.getGuiValue(c));
        assertEquals(c.getType(), c.getGuiValue(c).getClass());
    }

}
