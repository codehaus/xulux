/*
 $Id: DictionaryTest.java,v 1.12 2003-07-24 01:20:03 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.global;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the initialization of the dictionary.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryTest.java,v 1.12 2003-07-24 01:20:03 mvdb Exp $
 */
public class DictionaryTest extends TestCase
{

    /**
     * Constructor for DictionaryTest.
     * @param name
     */
    public DictionaryTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(DictionaryTest.class);
        return suite;
    }

    /**
     * Tests the initialization of the dictaionary from an dictionary 
     * file
     */
    public void xtestInitialize()
    {
        System.out.println("testInitialize");
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/nyx/global/dictionary.xml"));
        assertEquals("Test", Dictionary.getInstance().getMapping("Test").getName());
        assertEquals(DictionaryBean.class, dictionary.getMapping("Test").getBean());
        ArrayList list = dictionary.getMapping("Test").getFields();
        assertEquals(4, list.size());
        assertNotNull(list.get(list.indexOf("name")));
        assertNotNull(list.get(list.indexOf("city")));
        assertNotNull(list.get(list.indexOf("street")));
        assertNotNull(list.get(list.indexOf("subBean")));
    }
    
    /** 
     * Tests for dynamic mapping 
     */
    public void xtestEasyMapping()
    {
        System.out.println("testEasyMapping");
        Dictionary d = Dictionary.getInstance();
        DictionaryBean bean = new DictionaryBean();
        BeanMapping mapping = d.getMapping(bean.getClass());
        assertEquals("DictionaryBean", mapping.getName());
        // Testing setting a new value on the bean..
        IField field = mapping.getField("name");
        field.setValue(bean, "name");
        assertEquals("name",field.getValue(bean));
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
    public void xtestNestedDataBean()
    {
        System.out.println("testNestedDataBean");
        Dictionary d = Dictionary.getInstance();
        d.setBaseClass(DictionaryBaseBean.class);
        DictionaryBean bean = new DictionaryBean();
        BeanMapping mapping = d.getMapping(bean.getClass());
        BeanMapping subBean = d.getMapping("DictionarySubSubBean");
        IField field = subBean.getField("nice");
    }
    
    public void xtestBooleanData() {
        System.out.println("testBooleanData");
        Dictionary d = Dictionary.getInstance();
        d.setBaseClass(DictionaryBaseBean.class);
        BeanMapping subBean = d.getMapping(DictionarySubBean.class,true);
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
    public void xtestFieldElements()
    {
        System.out.println("testFieldElements");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/nyx/global/dictionary.xml"));
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
    
    public void xtestInfiniteLoop()
    {
        System.out.println("testInfiniteLoop");
        Dictionary d = Dictionary.getInstance();
        BeanMapping mb = d.getMapping(AnotherRecursiveBean.class);
        // cache should be cleared by dictionary.
        assertTrue(!d.isInCache(AnotherRecursiveBean.class));
        assertTrue(!d.isInCache(RecursiveBean.class));
        assertEquals(6,mb.getFields().size());
        BeanMapping mbmain = d.getMapping(RecursiveBean.class);
        assertEquals(3, mbmain.getFields().size());
    }
    
    public void xtestParameters() {
        System.out.println("testParameters");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/nyx/global/dictionary.xml"));
        ParameteredBean bean = new ParameteredBean();
        BeanMapping mapping = d.getMapping("pb");
        assertEquals("pb", mapping.getName());
        // Testing setting a new value on the bean..
        assertNull(bean.getParameter("BOGUS"));
        assertEquals("parameter",mapping.getField("tp").getName());
        assertEquals("sp",mapping.getField("sp").getAlias());
        assertEquals("fp",mapping.getField("fp").getAlias());
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
    public void xtestDoubleParameters() {
        System.out.println("testDoubleParameters");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/nyx/global/dictionary.xml"));
        ParameteredBean bean = new ParameteredBean();
        BeanMapping mapping = d.getMapping("db");
        IField fieldno1 = mapping.getField("no1");
        IField fieldno2 = mapping.getField("no2");
        IField fieldno3 = mapping.getField("no3");
        assertEquals(bean.getDouble(ParameteredBean.NO1), fieldno1.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO2), fieldno2.getValue(bean));
        assertEquals(bean.getDouble(ParameteredBean.NO3), fieldno3.getValue(bean));
        fieldno1.setValue(bean,"NO1NewValue");
        assertEquals("NO1NewValue",fieldno1.getValue(bean));
        fieldno2.setValue(bean,"NO2NewValue");
        assertEquals("NO2NewValue",fieldno2.getValue(bean));
        fieldno3.setValue(bean,"NO3NewValue");
        assertEquals("NO3NewValue",fieldno3.getValue(bean));
    }
    
    
    /**
     * Test the setting of the setmethod in the 
     * dictionary.
     */
    public void testSetMethod() {
        System.out.println("testSetMethod");
        Dictionary d = Dictionary.getInstance();
        d.initialize(this.getClass().getClassLoader().getResourceAsStream("org/xulux/nyx/global/dictionary.xml"));
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
     * Clean up the dictionary..
     */
    protected void tearDown() throws Exception
    {
        Dictionary.getInstance().clearMappings();
    }

}
