/*
 $Id: ClassLoaderUtilsTest.java,v 1.3 2003-11-25 22:14:10 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.utils;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.global.AnotherRecursiveBean;

/**
 * Test for the classLoaderUtils
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ClassLoaderUtilsTest.java,v 1.3 2003-11-25 22:14:10 mvdb Exp $
 */
public class ClassLoaderUtilsTest extends TestCase {
    
    /**
     * Constructor for ClassLoaderUtilsTest.
     * @param name
     */
    public ClassLoaderUtilsTest(String name) {
        super(name);
    }
    
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
        assertEquals(new String(),ClassLoaderUtils.getObjectFromClassString("java.lang.String"));
        assertEquals(new String(),ClassLoaderUtils.getObjectFromClass(String.class));
        assertNull(ClassLoaderUtils.getObjectFromClassString("bogus.class.name"));
        assertNull(ClassLoaderUtils.getObjectFromClass(null,null));
        
    }
    
    public void testGetClass() throws Exception {
        System.out.println("testGetClass");
        assertEquals(String.class,ClassLoaderUtils.getClass("java.lang.String"));
        assertEquals(AnotherRecursiveBean.class, ClassLoaderUtils.getClass("org.xulux.nyx.global.AnotherRecursiveBean"));
        assertNull(ClassLoaderUtils.getClass("bogus.class.name"));
        assertNull(ClassLoaderUtils.getClass(null));
    }

    public void testgetParentObjectForInnerClass() {
        System.out.println("testgetParentObjectForInnerClass");
        Object parent = ClassLoaderUtils.getParentObjectForInnerClass(NormalInner.class);
        assertNotNull(parent);
        assertEquals(this.getClass(), parent.getClass());
        parent = ClassLoaderUtils.getParentObjectForInnerClass(AnotherNormalInner.class);
        assertNotNull(parent);
        assertEquals(this.getClass(), parent.getClass());
        assertNull(ClassLoaderUtils.getParentObjectForInnerClass(String.class));
    }
    
    
    /**
     * Test instantiation of innerclasses...
     *
     */
    public void testInnerClassInstantiation() {
        System.out.println("testInnerClassInstantation");
        // test static inner classes
        assertEquals(StaticInner.class,ClassLoaderUtils.getObjectFromClass(StaticInner.class).getClass());
        // test non static inner classes
        Object object = ClassLoaderUtils.getObjectFromClass(NormalInner.class);
        assertNotNull(object);
        assertEquals(NormalInner.class,object.getClass());
        ArrayList parms = new ArrayList();
        parms.add("test");
        object = ClassLoaderUtils.getObjectFromClass(NormalInner.class,parms);
        assertNotNull(object);
        assertEquals(NormalInner.class, object.getClass());
        assertEquals("test", ((NormalInner)object).getName());
    }
    
    public void testIsInner() {
        assertFalse(ClassLoaderUtils.isInner(null));
        assertFalse(ClassLoaderUtils.isInner(String.class));
        assertTrue(ClassLoaderUtils.isInner(StaticInner.class));
        assertTrue(ClassLoaderUtils.isInner(NormalInner.class));
    }
    
    public void testConstructor() {
        new ClassLoaderUtils();
    }
    
    public static class StaticInner {
        public StaticInner() {
        }
    }
    
    public class NormalInner {
        public NormalInner() {
        }
        
        private String name;
        
        public NormalInner(String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public class AnotherNormalInner {
        public AnotherNormalInner() {
        }
    }

}
