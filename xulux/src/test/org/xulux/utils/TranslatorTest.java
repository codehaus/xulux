/*
   $Id: TranslatorTest.java,v 1.3 2004-01-28 15:22:02 mvdb Exp $
   
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
package org.xulux.utils;

import java.util.ArrayList;



import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test class for Translation and Translator.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TranslatorTest.java,v 1.3 2004-01-28 15:22:02 mvdb Exp $
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
        Translation trans1 = new Translation("org/xulux/utils/TranslatorTest1", null);
        Translation trans2 = new Translation("org/xulux/utils/TranslatorTest2", null);
        Translation trans3 = new Translation("org/xulux/utils/TranslatorTest3", null);
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
