/*
   $Id: DictionaryTest.java,v 1.5 2004-04-15 00:05:04 mvdb Exp $
   
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
package org.xulux.dataprovider;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.bean.BeanDataProvider;
import org.xulux.dataprovider.converters.DoubleConverter;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.dataprovider.converters.IntegerConverter;

/**
 * Tests the initialization of the dictionary.
 * Note: the warnings you get during the test are anticipated, it is just to test
 * how nyx handles bogus entry in the dictionary xml.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryTest.java,v 1.5 2004-04-15 00:05:04 mvdb Exp $
 */
public class DictionaryTest extends TestCase {

    /**
     * Constructor for BeanDataProviderTest.
     * @param name the test name
     */
    public DictionaryTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DictionaryTest.class);
        return suite;
    }

    public void testConstructor() {
        System.out.println("testConstructor");
        Dictionary dictionary = new Dictionary();
      
    }
    /**
     * Tests the initialization of the dictaionary from an dictionary
     * file
     */
    public void testInitialize() {
        System.out.println("testInitialize");
        Dictionary dictionary = new Dictionary();
        dictionary.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        assertEquals("Test", dictionary.getMapping("Test").getName());
        assertEquals(DictionaryBean.class, dictionary.getMapping("Test").getBean());
        List list = dictionary.getMapping("Test").getFields();
        assertEquals(4, list.size());
        assertNotNull(list.get(list.indexOf("name")));
        assertNotNull(list.get(list.indexOf("city")));
        assertNotNull(list.get(list.indexOf("street")));
        assertNotNull(list.get(list.indexOf("subBean")));
        dictionary.initialize((Object) new Integer(0));
        dictionary.initialize((Object) null);
        dictionary.initialize((InputStream) null);
    }

    /**
     * Test resetting the dictionary
     */
    public void testReset() {
        System.out.println("testReset");
        Dictionary dictionary = new Dictionary();
        dictionary.reset();
        dictionary.reset();
    }
        
    /**
     * Test the converter functionality in the registry.
     */
    public void testConverters() {
        System.out.println("testConverters");
        Dictionary.addConverter(IntegerConverter.class);
        Integer i = new Integer(10);
        IConverter converter = Dictionary.getConverter(i);
        assertEquals(Integer.class, converter.getType());
        assertEquals("10", converter.getGuiValue(i));
        assertEquals(i, converter.getBeanValue("10"));
        assertNull(Dictionary.getConverter(DictionaryTest.class));
    }

    /**
     * Test the converter by means of parsing the dictionary.xml
     *
     */
    public void testConverterXml() {
        System.out.println("testConvertersXml");
        Dictionary dictionary = new Dictionary();
        dictionary.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        Map converters = Dictionary.getConverters();
        assertEquals(2, converters.size());
        assertEquals(IntegerConverter.class, Dictionary.getConverter(Integer.class).getClass());
        assertEquals(DoubleConverter.class, Dictionary.getConverter(Double.class).getClass());
    }

    /**
     * Test provider functionality.
     */
    public void testProviders() {
        System.out.println("testProviders");
        assertNotNull(Dictionary.DEFAULT_PROVIDER);
        Dictionary dictionary = new Dictionary();
        IDataProvider provider = dictionary.getDefaultProvider();
        assertNotNull(provider);
        assertEquals(true, provider instanceof BeanDataProvider);
        dictionary.registerProvider("test", null);
        assertNull(dictionary.getProvider("test"));
        assertEquals(1, dictionary.getProviders().size());
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        XuluxContext.getDictionary().reset();
    }

}
