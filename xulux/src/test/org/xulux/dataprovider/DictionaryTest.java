/*
   $Id: DictionaryTest.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xulux.dataprovider.converters.DoubleConverter;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.dataprovider.converters.IntegerConverter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the initialization of the dictionary.
 * Note: the warnings you get during the test are anticipated, it is just to test
 * how nyx handles bogus entry in the dictionary xml.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryTest.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public class DictionaryTest extends TestCase {

    /**
     * Constructor for DictionaryTest.
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

    /**
     * Tests the initialization of the dictaionary from an dictionary
     * file
     */
    public void testInitialize() {
        System.out.println("testInitialize");
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        assertEquals("Test", Dictionary.getInstance().getMapping("Test").getName());
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
        Dictionary.reset();
        Dictionary.reset();
    }
        
    /**
     * Test getMapping
     */
    public void testGetMapping() {
        System.out.println("testGetMapping");
        // just a null test..
        BeanMapping mapping = Dictionary.getInstance().getMapping((Class)null);
        assertNull(mapping);
        mapping = Dictionary.getInstance().getMapping((Object) null);
        assertNull(mapping);
    }
    /**
     * Tests for dynamic mapping
     */
    public void testEasyMapping() {
        System.out.println("testEasyMapping");
        Dictionary d = Dictionary.getInstance();
        DictionaryBean bean = new DictionaryBean();
        BeanMapping mapping = d.getMapping(bean.getClass());
        assertEquals("DictionaryBean", mapping.getName());
        // Testing setting a new value on the bean..
        IField field = mapping.getField("name");
        field.setValue(bean, "name");
        assertEquals("name", field.getValue(bean));
        // test if the widget pointer is actually stripped..
        field = mapping.getField("?Prefix:Table.name");
        assertNotNull(field);
        // a bad name, we shouldn't crash!
        field = mapping.getField("?Prefix:Table.");
        assertNull(field);
        // another bad name, we shouldn't crash!
        field = mapping.getField("?Prefix:Table");
        assertNull(field);
        mapping = d.getMapping(bean.getClass(), true);
        assertEquals("DictionaryBean1", mapping.getName());
        mapping = d.getMapping(bean.getClass(), true);
        assertEquals("DictionaryBean2", mapping.getName());
        // this one is here, since I got a nullpointer exception
        // which wasn't handled..
        mapping = d.getMapping("Idontexist");
        assertNull(mapping);
    }

    /**
     * Tests if everything works ok when overriding
     * of a bean is used.
     * Also test if booleans are working correctly.
     */
    public void testNestedDataBean() {
        System.out.println("testNestedDataBean");
        Dictionary d = Dictionary.getInstance();
        d.setBaseClass(DictionaryBaseBean.class);
        DictionaryBean bean = new DictionaryBean();
        BeanMapping mapping = d.getMapping(bean.getClass());
        BeanMapping subBean = d.getMapping("DictionarySubSubBean");
        IField field = subBean.getField("nice");
    }

    /**
     * Test if boolean data is handled correctly
     */
    public void testBooleanData() {
        System.out.println("testBooleanData");
        Dictionary d = Dictionary.getInstance();
        d.setBaseClass(DictionaryBaseBean.class);
        BeanMapping subBean = d.getMapping(DictionarySubBean.class, true);
        IField field = subBean.getField("nice");
        assertNotNull(field);
        DictionarySubBean bean = new DictionarySubBean();
        bean.setNice(true);
        assertTrue(bean.isNice());
        bean.setNice(false);
        assertFalse(bean.isNice());
    }

    /**
     * Test the Fields/field (non autodiscovery) mechanisme
     */
    public void testFieldElements() {
        System.out.println("testFieldElements");
        Dictionary d = Dictionary.getInstance();
        d.initialize((Object) this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        BeanMapping mapping = d.getMapping("Manual");
        assertNotNull(mapping.getField("straat"));
        assertNull(mapping.getField("street"));
        assertNotNull(mapping.getField("plaats"));
        assertNull(mapping.getField("city"));
        assertEquals("name", mapping.getField("name").getAlias());
        assertEquals("name", mapping.getField("name").getName());
        assertEquals("straat", mapping.getField("straat").getAlias());
        assertEquals("street", mapping.getField("straat").getName());
        assertEquals("plaats", mapping.getField("plaats").getAlias());
        assertEquals("city", mapping.getField("plaats").getName());
    }

    /**
     * Test to see if infinite looping is preventing
     */
    public void testInfiniteLoop() {
        System.out.println("testInfiniteLoop");
        Dictionary d = Dictionary.getInstance();
        BeanMapping mb = d.getMapping(AnotherRecursiveBean.class);
        // cache should be cleared by dictionary.
        assertTrue(!d.isInCache(AnotherRecursiveBean.class));
        assertTrue(!d.isInCache(RecursiveBean.class));
        System.out.println("Fields : " +mb.getFields());
        assertEquals(7, mb.getFields().size());
        BeanMapping mbmain = d.getMapping(RecursiveBean.class);
        assertEquals(3, mbmain.getFields().size());
    }

    /**
     * Test parameter processing
     */
    public void testParameters() {
        System.out.println("testParameters");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        ParameteredBean bean = new ParameteredBean();
        BeanMapping mapping = d.getMapping("pb");
        assertEquals("pb", mapping.getName());
        // Testing setting a new value on the bean..
        assertNull(bean.getParameter("BOGUS"));
        assertEquals("parameter", mapping.getField("tp").getName());
        assertEquals("sp", mapping.getField("sp").getAlias());
        assertEquals("fp", mapping.getField("fp").getAlias());
    }

    /**
     * Test if setter like (String, String)
     * work under nyx.
     * Basic assumption is that if there is a
     * getXXX(String), there will be a setXX(String,String)
     * where the first String is the string passed into
     * the getter.
     *
     */
    public void testDoubleParameters() {
        System.out.println("testDoubleParameters");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        ParameteredBean bean = new ParameteredBean();
        BeanMapping mapping = d.getMapping("db");
        IField fieldno1 = mapping.getField("no1");
        IField fieldno2 = mapping.getField("no2");
        IField fieldno3 = mapping.getField("no3");
        assertEquals(bean.getDouble(ParameteredBean.NO1), fieldno1.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO2), fieldno2.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO3), fieldno3.getValue(bean));
        fieldno1.setValue(bean, "NO1NewValue");
        assertEquals("NO1NewValue", fieldno1.getValue(bean));
        fieldno2.setValue(bean, "NO2NewValue");
        assertEquals("NO2NewValue", fieldno2.getValue(bean));
        fieldno3.setValue(bean, "NO3NewValue");
        assertEquals("NO3NewValue", fieldno3.getValue(bean));
    }

    /**
     * Test the setting of the setmethod in the
     * dictionary.
     */
    public void testSetMethod() {
        System.out.println("testSetMethod");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        ParameteredBean bean = new ParameteredBean();
        BeanMapping mapping = d.getMapping("set");
        IField fieldno1 = mapping.getField("no1");
        IField fieldno2 = mapping.getField("no2");
        IField fieldno3 = mapping.getField("no3");
        assertEquals(bean.getDouble(ParameteredBean.NO1), fieldno1.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO2), fieldno2.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO3), fieldno3.getValue(bean));
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
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        Map converters = Dictionary.getConverters();
        assertEquals(2, converters.size());
        assertEquals(IntegerConverter.class, Dictionary.getConverter(Integer.class).getClass());
        assertEquals(DoubleConverter.class, Dictionary.getConverter(Double.class).getClass());
    }

    /**
     * If the bean (in this case a list) contains the method get(int) or any
     * other get, it would crash.
     */
    public void testGetMappingWithGetMethod() {
        System.out.println("testGetMappingWithGetMethod");
        ArrayList list = new ArrayList();
        BeanMapping mapping = Dictionary.getInstance().getMapping(list.getClass());
        assertNotNull(mapping.getField("get"));
        mapping = Dictionary.getInstance().getMapping(new InnerBean());
        assertNotNull(mapping.getField("x"));
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        Dictionary.reset();
    }

    /**
     * The innerbean
     */
    public class InnerBean {

        /**
         * Constructor
         */
        public InnerBean() {
        }

        /**
         * @return X
         */
        public String getX() {
            return "X";
        }

        /**
         * @param x x
         */
        public void setX(String x) {
        }
    }

}
