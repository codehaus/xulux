/*
 $Id: TranslatorTest.java,v 1.3 2003-11-25 16:25:10 mvdb Exp $

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
package org.xulux.nyx.utils;

import java.util.ArrayList;



import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test class for Translation and Translator.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TranslatorTest.java,v 1.3 2003-11-25 16:25:10 mvdb Exp $
 */
public class TranslatorTest extends TestCase {

    /**
     * Constructor for TranslatorTest.
     * @param name the test name
     */
    public TranslatorTest(String name) {
        super(name);
    }

    /**
     * @return the suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite(TranslatorTest.class);
        return suite;
    }

    /**
     * Test the translation
     */
    public void testTranslate() {
        Translation trans1 = new Translation("org/xulux/nyx/utils/TranslatorTest1", null);
        Translation trans2 = new Translation("org/xulux/nyx/utils/TranslatorTest2", null);
        Translation trans3 = new Translation("org/xulux/nyx/utils/TranslatorTest3", null);
        ArrayList list = new ArrayList();
        list.add(trans1);
        list.add(trans2);
        list.add(trans3);
        assertEquals("donottranslate", Translator.translate(list, "donottranslate"));
        assertEquals("Value 11", Translator.translate(list, "%value11"));
        assertEquals("Value 12", Translator.translate(list, "%value12"));
        assertEquals("Value 13", Translator.translate(list, "%value13"));
        assertEquals("Value 21", Translator.translate(list, "%value21"));
        assertEquals("Value 22", Translator.translate(list, "%value22"));
        assertEquals("Value 23", Translator.translate(list, "%value23"));
        assertEquals("%value23", Translator.translate(list, "%%value23"));
        assertEquals("%value", Translator.translate(new ArrayList(), "%value"));
        assertEquals("%shitty", Translator.translate(list, "%shitty"));
    }

    /**
     * Tests to see if equals is working correctly.
     *
     */
    public void testEquals() {
        Translation trans1 = new Translation("org/xulux/nyx/utils/TranslatorTest1", null);
        Translation trans2 = new Translation("org/xulux/nyx/utils/TranslatorTest2", null);
        Translation trans3 = new Translation("org/xulux/nyx/utils/TranslatorTest1", null);
        Translation trans4 = new Translation("org/xulux/nyx/utils/TranslatorTest2", null);
        Translation trans5 = new Translation("org/xulux/nyx/utils/TranslatorTest5", null);
        ArrayList list = new ArrayList();
        list.add(trans1);
        list.add(trans2);
        assertTrue(list.contains(trans3));
        assertTrue(list.contains(trans4));
        assertFalse(list.contains(trans5));
        assertFalse(list.contains("bogus"));
        assertFalse(trans1.equals(null));
        assertFalse(trans1.equals("null"));
        Translation trans6 = new Translation("org/xulux/nyx/utils/TranslatorTest1", "http");
        Translation trans7 = new Translation("org/xulux/nyx/utils/TranslatorTest1", "http");
        Translation trans8 = new Translation("org/xulux/nyx/utils/TranslatorTest2", "http");
        Translation trans9 = new Translation("org/xulux/nyx/utils/TranslatorTest2", "rmi");
        assertTrue(trans6.equals(trans6));
        assertTrue(trans6.equals(trans7));
        assertFalse(trans6.equals(trans8));
        assertFalse(trans8.equals(trans7));
        assertFalse(trans8.equals(trans1));
        Translation trans10 = new Translation(null, null);
        assertFalse(trans10.equals(trans8));
    }

    /**
     * Test the translator
     */
    public void testTranslator() {
        Translator t = Translator.getInstance();
        assertNotNull(t);
        assertEquals(t, Translator.getInstance());
    }
}
