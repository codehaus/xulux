/*
   $Id: BeanDataProviderTest.java,v 1.3 2004-10-20 17:28:59 mvdb Exp $
   
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
package org.xulux.dataprovider.bean;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.AnotherRecursiveBean;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.DictionaryBaseBean;
import org.xulux.dataprovider.DictionaryBean;
import org.xulux.dataprovider.DictionarySubBean;
import org.xulux.dataprovider.DictionarySubSubBean;
import org.xulux.dataprovider.IDataProvider;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.ParameteredBean;
import org.xulux.dataprovider.RecursiveBean;

/**
 * Tests the BeanDataProvider
 * Note: the warnings you get during the test are anticipated, it is just to test
 * how xulux handles bogus entry in the dictionary xml.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanDataProviderTest.java,v 1.3 2004-10-20 17:28:59 mvdb Exp $
 */
public class BeanDataProviderTest extends TestCase {

    /**
     * Constructor for BeanDataProviderTest.
     * @param name the test name
     */
    public BeanDataProviderTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BeanDataProviderTest.class);
        return suite;
    }

    public void testConstructor() {
        System.out.println("testConstructor");
        Dictionary dictionary = new Dictionary();
      
    }
    /**
     * Tests the initialization of the dictaionary from an dictionary
     * file
     * @todo Fix this.. There needs to be 4, but 18 are returned!
     */
    public void testInitialize() {
        System.out.println("testInitialize");
        XuluxContext.getDictionary().initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        IDataProvider provider = XuluxContext.getDictionary().getDefaultProvider();
        assertEquals("Test", provider.getMapping("Test").getName());
        //assertEquals(DictionaryBean.class, provider.getMapping("Test").getBean());
        List list = provider.getMapping("Test").getFields();
        assertEquals(4, list.size());
        assertNotNull(list.get(list.indexOf("name")));
        assertNotNull(list.get(list.indexOf("city")));
        assertNotNull(list.get(list.indexOf("street")));
        assertNotNull(list.get(list.indexOf("subBean")));
    }

    /**
     * Test getMapping
     */
    public void testGetMapping() {
        System.out.println("testGetMapping");
        // just a null test..
        BeanDataProvider provider = new BeanDataProvider();
        IMapping mapping = provider.getMapping((Class)null);
        assertNull(mapping);
        mapping = provider.getMapping((Object) null);
        assertNull(mapping);
    }

    /**
     * Tests for dynamic mapping
     */
    public void testEasyMapping() {
        System.out.println("testEasyMapping");
        BeanDataProvider provider = new BeanDataProvider();
        DictionaryBean bean = new DictionaryBean();
        IMapping mapping = provider.getMapping(bean.getClass());
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
        mapping = provider.getMapping(bean.getClass(), true);
        assertEquals("DictionaryBean1", mapping.getName());
        mapping = provider.getMapping(bean.getClass(), true);
        assertEquals("DictionaryBean2", mapping.getName());
        // this one is here, since I got a nullpointer exception
        // which wasn't handled..
        mapping = provider.getMapping("Idontexist");
        assertNull(mapping);
    }

    /**
     * Tests if everything works ok when overriding
     * of a bean is used.
     */
    public void testNestedDataBean() {
        System.out.println("testNestedDataBean");
        BeanDataProvider provider = new BeanDataProvider();
        provider.setBaseClass(DictionaryBaseBean.class);
        DictionaryBean bean = new DictionaryBean();
        IMapping mapping = provider.getMapping(bean.getClass());
        // @todo provider.getMapping("DictionarySubSubBean") seem to work too though..
        IMapping subBean = provider.getMapping(DictionarySubSubBean.class);
        System.out.println("provider mappings : " + provider.getMappings());
        IField field = subBean.getField("nice");
    }

    /**
     * Test if boolean data is handled correctly
     */
    public void testBooleanData() {
        System.out.println("testBooleanData");
        BeanDataProvider provider = new BeanDataProvider();
        provider.setBaseClass(DictionaryBaseBean.class);
        IMapping subBean = provider.getMapping(DictionarySubBean.class, true);
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
        XuluxContext.getDictionary().initialize((Object) this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        IDataProvider provider = XuluxContext.getDictionary().getProvider("bean");
        IMapping mapping = provider.getMapping("Manual");
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
        BeanDataProvider provider = new BeanDataProvider();
        IMapping mb = provider.getMapping(AnotherRecursiveBean.class);
        // cache should be cleared by dictionary.
        assertTrue(!provider.isInCache(AnotherRecursiveBean.class));
        assertTrue(!provider.isInCache(RecursiveBean.class));
        System.out.println("Fields : " +mb.getFields());
        assertEquals(7, mb.getFields().size());
        IMapping mbmain = provider.getMapping(RecursiveBean.class);
        assertEquals(3, mbmain.getFields().size());
    }

    /**
     * Test parameter processing
     */
    public void testParameters() {
        System.out.println("testParameters");
        XuluxContext.getDictionary().initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        IDataProvider provider = XuluxContext.getDictionary().getProvider("bean");
        ParameteredBean bean = new ParameteredBean();
        IMapping mapping = provider.getMapping("pb");
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
        XuluxContext.getDictionary().initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        IDataProvider provider = XuluxContext.getDictionary().getProvider("bean");
        ParameteredBean bean = new ParameteredBean();
        IMapping mapping = provider.getMapping("db");
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
        XuluxContext.getDictionary().initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/dataprovider/dictionary.xml"));
        IDataProvider provider = XuluxContext.getDictionary().getProvider("bean");
        ParameteredBean bean = new ParameteredBean();
        IMapping mapping = provider.getMapping("set");
        IField fieldno1 = mapping.getField("no1");
        IField fieldno2 = mapping.getField("no2");
        IField fieldno3 = mapping.getField("no3");
        assertEquals(bean.getDouble(ParameteredBean.NO1), fieldno1.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO2), fieldno2.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO3), fieldno3.getValue(bean));
    }

    /**
     * If the bean (in this case a list) contains the method get(int) or any
     * other get, it would crash.
     */
    public void testGetMappingWithGetMethod() {
        System.out.println("testGetMappingWithGetMethod");
        BeanDataProvider provider = new BeanDataProvider();
        ArrayList list = new ArrayList();
        IMapping mapping = provider.getMapping(list.getClass());
        assertNotNull(mapping.getField("get"));
        mapping = provider.getMapping(new InnerBean());
        assertNotNull(mapping.getField("x"));
    }

    public void testSetAndGetValue() {
        System.out.println("testSetAndGetValue");
        BeanDataProvider provider = new BeanDataProvider();
        Object setValue = provider.setValue("mapping", "field", "Object", "value");
        Object getValue = provider.getValue("mapping", "field", "Object");
    }
    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
      XuluxContext.getDictionary().reset();
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
