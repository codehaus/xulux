/*
   $Id: BeanFieldTest.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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

import java.lang.reflect.Method;
import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the beanfield class intensively, since it sucks to
 * debug too much..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van dn Bemt</a>
 * @version $Id: BeanFieldTest.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class BeanFieldTest extends TestCase {

    /**
     * Constructor for BeanFieldTest.
     * @param name the name of the test
     */
    public BeanFieldTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BeanFieldTest.class);
        return suite;
    }
    /**
     * Test the setting and discovery of the changemethod.
     * @throws Exception just in case
     */
    public void testChangeMethod() throws Exception {
        System.out.println("testChangeMethod");
        /*
         * test the simple String getXXX()
         * and the void setXXX(String)
         */
        Method method = ParameteredBean.class.getMethod("getValue", new Class[] {
        });
        BeanField field = new BeanField(method);
        field.setChangeMethod(ParameteredBean.class, "value");
        Method setMethod = ParameteredBean.class.getMethod("setValue", new Class[] { String.class });
        assertEquals(setMethod, field.getChangeMethod());
        /*
         * Test the String getXX(String)
         * and the setXXX(String, String)
         */
        method = ParameteredBean.class.getMethod("getDouble", new Class[] { String.class });
        field = new BeanField(method);
        field.setChangeMethod(ParameteredBean.class, "double");
        setMethod = ParameteredBean.class.getMethod("setDouble", new Class[] { String.class, String.class });
        assertEquals(setMethod, field.getChangeMethod());

        /*
         * Test the Parameter getParameter(String)
         * and the setParameter(Paramater parm)
         */
        method = ParameteredBean.class.getMethod("getParameter", new Class[] { String.class });
        field = new BeanField(method);
        field.setChangeMethod(ParameteredBean.class, "parameter");
        setMethod = ParameteredBean.class.getMethod("setParameter", new Class[] { Parameter.class });
        assertEquals(setMethod, field.getChangeMethod());

        /*
         * Test the Parameter getParameter(String)
         * and the setParameter(Paramater parm)
         */
        method = ParameteredBean.class.getMethod("getParameter", new Class[] { String.class });
        field = new BeanField(method);
        field.setChangeMethod(ParameteredBean.class, "setSomethingDifferent");
        setMethod = ParameteredBean.class.getMethod("setSomethingDifferent", new Class[] { Parameter.class });
        assertEquals(setMethod, field.getChangeMethod());

    }

    /**
     * test the simple String getXXX()
     * and the void setXXX(String)
     * @throws Exception just in case
     */
    public void testGetSetMethodArgs() throws Exception {
        System.out.println("testGetSetMethodArgs");
        ParameteredBean bean = new ParameteredBean();
        bean.setValue("test");
        Method method = ParameteredBean.class.getMethod("getValue", new Class[] {
        });
        BeanField field = new BeanField(method);
        field.setChangeMethod(bean.getClass(), "value");
        Object[] object = field.getSetMethodArgs("test1");
        assertEquals("test", field.getValue(bean));
        field.setValue(bean, "test1");
        assertEquals("test1", field.getValue(bean));
    }

    /**
     * Test the String getXX(String)
     * and the setXXX(String, String)
     * @throws Exception just in case
     */
    public void testGetSetMethodArgs1() throws Exception {
        ParameteredBean bean = new ParameteredBean();
        bean.setDouble(ParameteredBean.NO1, "No1Value");
        Method method = ParameteredBean.class.getMethod("getDouble", new Class[] { String.class });
        BeanField field = new BeanField(method);
        BeanParameter parm = new BeanParameter("static", "org.xulux.dataprovider.ParameteredBean.NO1");
        ArrayList list = new ArrayList();
        list.add(parm);
        field.setParameters(list);
        field.setChangeMethod(ParameteredBean.class, "double");
        assertEquals("No1Value", field.getValue(bean));
        field.setValue(bean, "NewValue");
        assertEquals("NewValue", field.getValue(bean));
    }

    /**
     * Test the Parameter getParameter(String)
     * and the setParameter(Paramater parm)
     * @throws Exception just in case
     */
    public void testGetSetMethodArgs2() throws Exception {

        ParameteredBean bean = new ParameteredBean();
        Parameter p = bean.getParameter(ParameterType.FIRST);
        Method method = ParameteredBean.class.getMethod("getParameter", new Class[] { String.class });
        BeanField field = new BeanField(method);
        field.setAlias("first");
        BeanParameter parm = new BeanParameter("static", "org.xulux.dataprovider.ParameterType.FIRST");
        ArrayList list = new ArrayList();
        list.add(parm);
        field.setParameters(list);
        field.setChangeMethod(ParameteredBean.class, "parameter");
        Parameter firstP = new Parameter(new ParameterType(ParameterType.FIRST), "first parm");
        assertEquals(firstP, field.getValue(bean));
        Parameter beanParm = (Parameter) field.getValue(bean);
        firstP.setValue("Testing");
        field.setValue(bean, firstP);
        assertEquals("Testing of type first", field.getValue(bean).toString());
    }

    /**
     * Test the Parameter getParameter(String)
     * and the setParameter(Paramater parm)
     * @throws Exception just in case
     */
    public void xtestGetSetMethodArgs2() throws Exception {

        ParameteredBean bean = new ParameteredBean();
        Parameter p = bean.getParameter(ParameterType.FIRST);
        Method method = ParameteredBean.class.getMethod("getParameter", new Class[] { String.class });
        BeanField field = new BeanField(method);
        field.setAlias("first");
        BeanParameter parm = new BeanParameter("static", "org.xulux.nyx.dataprovider.ParameterType.FIRST");
        ArrayList list = new ArrayList();
        list.add(parm);
        field.setParameters(list);
        field.setChangeMethod(ParameteredBean.class, "parameter");
        Parameter firstP = new Parameter(new ParameterType(ParameterType.FIRST), "first parm");
        assertEquals(firstP, field.getValue(bean));
        Parameter beanParm = (Parameter) field.getValue(bean);
        firstP.setValue("Testing");
        field.setValue(bean, firstP);
        assertEquals("Testing of type first", field.getValue(bean).toString());
    }

    /**
     * Test the normal set get behaviour eg
     * String getXXX()
     * void setXXX(String)
     * @throws Exception just in case
     */
    public void testNormalSetGet() throws Exception {
        System.out.println("testGetValue");
        ParameteredBean bean = new ParameteredBean();
        bean.setValue("test");
        Method method = bean.getClass().getMethod("getValue", new Class[] {
        });
        //Method changeMethod = bean.getClass().getMethod("setValue", new Class[] { String.class });
        BeanField field = new BeanField(method);
        field.setChangeMethod(bean.getClass(), "value");
        assertEquals(field.getMethod(), method);
        assertEquals("test", field.getValue(bean));
        field.setValue(bean, "test1");
        assertEquals("test1", field.getValue(bean));
    }

}
