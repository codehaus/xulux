/*
   $Id: ClassLoaderUtilsTest.java,v 1.4 2004-03-16 14:35:13 mvdb Exp $
   
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
import java.util.List;

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
 * @version $Id: ClassLoaderUtilsTest.java,v 1.4 2004-03-16 14:35:13 mvdb Exp $
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
    public void testObjectFromClass() {
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
        new ClassLoaderUtils();
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
