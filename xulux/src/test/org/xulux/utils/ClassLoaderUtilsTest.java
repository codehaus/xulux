/*
   $Id: ClassLoaderUtilsTest.java,v 1.6 2004-03-25 00:48:09 mvdb Exp $
   
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SimpleLog;
import org.xulux.dataprovider.AnotherRecursiveBean;
import org.xulux.dataprovider.Dictionary;

/**
 * Test for the classLoaderUtils
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ClassLoaderUtilsTest.java,v 1.6 2004-03-25 00:48:09 mvdb Exp $
 */
public class ClassLoaderUtilsTest extends TestCase {
    /**
     * Set the logging property, so we can test if no logging is turned on
     */
    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    }

    /**
     * Constructor for ClassLoaderUtilsTest.
     * @param name the name of the test
     */
    public ClassLoaderUtilsTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ClassLoaderUtilsTest.class);
        return suite;
    }

    /**
     * Test the objectFromClass
     * and the objectFromClassString
     */
    public void testObjectFromClass() throws Exception {
        System.out.println("testObjectFromClass");
        assertEquals(new String(), ClassLoaderUtils.getObjectFromClassString("java.lang.String"));
        assertEquals(new String(), ClassLoaderUtils.getObjectFromClass(String.class));
        assertNull(ClassLoaderUtils.getObjectFromClassString("bogus.class.name"));
        assertNull(ClassLoaderUtils.getObjectFromClass(null, null));
        // Test Logging of classloader failures.
        assertNull(ClassLoaderUtils.getObjectFromClass(PrivateInner.class));
        assertNull(ClassLoaderUtils.getObjectFromClass(ExceptionInner.class));
        assertNull(ClassLoaderUtils.getObjectFromClass(AbstractInner.class));
        assertNull(ClassLoaderUtils.getObjectFromClass(Dictionary.class));
        assertNull(ClassLoaderUtils.getObjectFromClass(PrivateInnerClassObject.InnerClass.class));
        List list = new ArrayList();
        list.add(null);
        assertEquals(PublicInnerClassObject.class,
            ClassLoaderUtils.getObjectFromClass(PublicInnerClassObject.class, list).getClass());
        ((SimpleLog) LogFactory.getLog(ClassLoaderUtils.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        assertEquals(PublicInnerClassObject.class,
            ClassLoaderUtils.getObjectFromClass(PublicInnerClassObject.class, list).getClass());
        ((SimpleLog) LogFactory.getFactory().getInstance(ClassLoaderUtils.class)).setLevel(SimpleLog.LOG_LEVEL_WARN);
    }
    
    public URL[] getClassPathUrl() {
        String jcp = System.getProperty("java.class.path");
        StringTokenizer stn = new StringTokenizer(jcp, File.pathSeparator);
        URL[] urls = new URL[stn.countTokens()];
        int i = 0;
        while (stn.hasMoreTokens()) {
            try {
                urls[i] = new URL("file", null, -1, stn.nextToken());
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    /**
     * Test the getClass() method
     * @throws Exception an exception
     */
    public void testGetClass() throws Exception {
        System.out.println("testGetClass");
        assertEquals(String.class, ClassLoaderUtils.getClass("java.lang.String"));
        assertEquals(AnotherRecursiveBean.class, ClassLoaderUtils.getClass("org.xulux.dataprovider.AnotherRecursiveBean"));
        assertNull(ClassLoaderUtils.getClass(null));
        assertNull(ClassLoaderUtils.getClass("bogus.class.name"));
        Log log = LogFactory.getLog(ClassLoaderUtils.class);
        ((SimpleLog) LogFactory.getLog(ClassLoaderUtils.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        assertNull(ClassLoaderUtils.getClass("bogus.class.name"));
    }

    /**
     * test the getParentObjectForInnerClass
     */
    public void testgetParentObjectForInnerClass() {
        System.out.println("testgetParentObjectForInnerClass");
        Object parent = ClassLoaderUtils.getParentObjectForInnerClass(NormalInner.class);
        assertNotNull(parent);
        assertEquals(this.getClass(), parent.getClass());
        parent = ClassLoaderUtils.getParentObjectForInnerClass(AnotherNormalInner.class);
        assertNotNull(parent);
        assertEquals(this.getClass(), parent.getClass());
        assertNull(ClassLoaderUtils.getParentObjectForInnerClass(String.class));
        assertNull(ClassLoaderUtils.getParentObjectForInnerClass(PrivateInnerClassObject.InnerClass.class));
        assertEquals(PublicInnerClassObject.class,
               ClassLoaderUtils.getParentObjectForInnerClass(PublicInnerClassObject.InnerClass.class).getClass());
    }

    /**
     * Test instantiation of innerclasses...
     */
    public void testInnerClassInstantiation() {
        System.out.println("testInnerClassInstantation");
        // test static inner classes
        assertEquals(StaticInner.class, ClassLoaderUtils.getObjectFromClass(StaticInner.class).getClass());
        // test non static inner classes
        Object object = ClassLoaderUtils.getObjectFromClass(NormalInner.class);
        assertNotNull(object);
        assertEquals(NormalInner.class, object.getClass());
        ArrayList parms = new ArrayList();
        parms.add("test");
        object = ClassLoaderUtils.getObjectFromClass(NormalInner.class, parms);
        assertNotNull(object);
        assertEquals(NormalInner.class, object.getClass());
        assertEquals("test", ((NormalInner) object).getName());
    }

    /**
     * Test isInner() method
     */
    public void testIsInner() {
        assertFalse(ClassLoaderUtils.isInner(null));
        assertFalse(ClassLoaderUtils.isInner(String.class));
        assertTrue(ClassLoaderUtils.isInner(StaticInner.class));
        assertTrue(ClassLoaderUtils.isInner(NormalInner.class));
    }

    /**
     * Test the constructor
     */
    public void testConstructor() {
        System.out.println("testConstructor");
        new ClassLoaderUtils();
    }

    
    /**
     * Test the scenario where there is an object constructor
     * It should check the clas it's hierarchy when finding
     * the constructor..
     */
    public void testObjectContstructor() {
        System.out.println("testObjectConstructor");
        List list = new ArrayList(1);
        list.add("Test");
        Object obj = ClassLoaderUtils.getObjectFromClass(OC.class, list);
        assertNotNull("Object should be instanceof OC", obj);
        assertTrue("Object should be instanceof OC", obj instanceof OC);
        // since it is an inner class, the parm list, will have an instance
        // of the testcases loaded, it should be removed from the list after
        // processing. That is the thing being tested here..
        assertEquals(1, list.size());
        list.add("Test1");
        obj = ClassLoaderUtils.getObjectFromClass(OC2.class, list);
        assertNotNull("Object should be instanceof OC2", obj);
        assertTrue("Object should be instanceof OC2", obj instanceof OC2);
        assertEquals(2, list.size());
        list.set(1, new Object());
        obj = ClassLoaderUtils.getObjectFromClass(OC3.class, list);
        assertNull(obj);
        obj = ClassLoaderUtils.getObjectFromClass(ClassLoaderTestObject.class, list);
        assertNotNull("Object should be instanceof ClassLoaderTestObject", obj);
        assertTrue("Object should be instanceof ClassLoaderTestObject", obj instanceof ClassLoaderTestObject);
        assertEquals(2, list.size());
        list.set(0, new Object());
        obj = ClassLoaderUtils.getObjectFromClass(ClassLoaderTestObject.class, list);
        assertNull(obj);
    }

    /**
     * Test class for the ObjectConstructor test
     */
    public class OC {
        public OC(Object object) {
        }
    }
    /**
     * Test class for the ObjectConstructor test
     */
    public class OC2 {
        public OC2(Object object1, Object object2) {
        }
    }
    /**
     * Test class for the ObjectConstructor test
     */
    public class OC3 {
        public OC3(Object object, String string) {
        }
    }
     
    /**
     * The static inner class
     */
    public static class StaticInner {
        /**
         * constructor
         */
        public StaticInner() {
        }
    }

    /**
     * Normal inner class
     */
    public class NormalInner {
        /**
         * Constructor
         */
        public NormalInner() {
        }

        /**
         * the name
         */
        private String name;

        /**
         * @param name the name
         */
        public NormalInner(String name) {
            this.name = name;
        }

        /**
         * @return the name
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * Another normal inner class
     */
    public class AnotherNormalInner {
        /**
         * constructor
         */
        public AnotherNormalInner() {
        }
    }

    /**
     * Another inner class with private constructor
     */
    public final class PrivateInner {
        /**
         * private constructor
         */
        private PrivateInner() {
        }

        /**
         * public constructor
         * @param string string
         */
        public PrivateInner(String string) {
        }
    }

    /**
     * inner class that throws exception
     */
    public class ExceptionInner {
        /**
         * constructor that throws exception
         */
        public ExceptionInner() {
            throw new IllegalArgumentException();
        }
    }
    /**
     * Inner interface
     */
    public abstract class AbstractInner {
        /**
         * Abstract constructor
         */
        public AbstractInner() {
        }
    }

}
