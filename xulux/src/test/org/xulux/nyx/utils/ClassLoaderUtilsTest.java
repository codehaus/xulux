/*
 $Id: ClassLoaderUtilsTest.java,v 1.4 2003-11-26 00:45:17 mvdb Exp $

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
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SimpleLog;
import org.xulux.nyx.global.AnotherRecursiveBean;
import org.xulux.nyx.global.Dictionary;

/**
 * Test for the classLoaderUtils
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ClassLoaderUtilsTest.java,v 1.4 2003-11-26 00:45:17 mvdb Exp $
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
        assertEquals(AnotherRecursiveBean.class, ClassLoaderUtils.getClass("org.xulux.nyx.global.AnotherRecursiveBean"));
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
