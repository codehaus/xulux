/*
 $Id: BeanFieldTest.java,v 1.2 2003-12-15 23:37:14 mvdb Exp $

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
package org.xulux.nyx.global;

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
 * @version $Id: BeanFieldTest.java,v 1.2 2003-12-15 23:37:14 mvdb Exp $
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
        BeanParameter parm = new BeanParameter("static", "org.xulux.nyx.global.ParameteredBean.NO1");
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
        BeanParameter parm = new BeanParameter("static", "org.xulux.nyx.global.ParameterType.FIRST");
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
        BeanParameter parm = new BeanParameter("static", "org.xulux.nyx.global.ParameterType.FIRST");
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
