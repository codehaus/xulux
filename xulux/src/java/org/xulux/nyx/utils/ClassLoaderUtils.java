/*
 $Id: ClassLoaderUtils.java,v 1.7 2003-11-06 16:57:54 mvdb Exp $

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
package org.xulux.nyx.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This util class contains classloader utils
 * so we can do actual code reuse.
 *  
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ClassLoaderUtils.java,v 1.7 2003-11-06 16:57:54 mvdb Exp $
 */
public class ClassLoaderUtils {

    private static Log log = LogFactory.getLog(ClassLoaderUtils.class);
    
    /**
     * Make it possible to extend..
     */
    public ClassLoaderUtils() {
    }
    
    /**
     * 
     * @param classString
     * @return an object from the specified classString or null when errors occur
     */
    public static Object getObjectFromClassString(String classString) {
        Class clazz = getClass(classString);
        return getObjectFromClass(clazz);
    }
    /**
     * Also instantiates static AND non static innerclasses. 
     * The parent class needs to have an empty constructor! 
     * You can overcome this problem by adding this to the paramlist! 
     * @param clazz
     * @return an object from the specified class or null when errors occur
     */
    public static Object getObjectFromClass(Class clazz) {
        if (clazz == null) {
            return null;
        }
        Object object = null;
        try {
            if (isInner(clazz) && !Modifier.isStatic(clazz.getModifiers())) {
                Object parent = getParentObjectForInnerClass(clazz);
                if (parent != null) {
                    Constructor constructor = clazz.getConstructor(new Class[] {parent.getClass()});
                    object = constructor.newInstance(new Object[] {parent} );
                } 
            } else {
                // static inner classes can be instantiated without a problem.. 
                object = clazz.newInstance();
            }
            return object;
        }
        catch (InvocationTargetException e) {
            log.warn("Cannot invocate target on "+clazz.getName());
        }
        catch (NoSuchMethodException e) {
            log.warn("Cannot find method on "+clazz.getName());
        }
        catch (InstantiationException e) {
            log.warn("Cannot instantiate class "+clazz.getName());
        }
        catch (IllegalAccessException e) {
            log.warn("Cannot access class "+clazz.getName());
        }
        return null;
    }
    
    /**
     * Instantiates the parent object of an inner class.
     * @param class
     * @return the instance of the inner class
     */
    protected static Object getParentObjectForInnerClass(Class clazz) {
        String name = clazz.getName();
        int index = name.indexOf("$");
        if (index != -1 ) {
            name = name.substring(0,index);
            Class clz = getClass(name.substring(0,index));
            if (clz != null) {
                ArrayList parms = new ArrayList();
                boolean hasEmptyConstructor = false;
                Constructor[] css = clz.getConstructors();
                for (int i = 0; i < css.length;i++) {
                    Constructor cs = css[i];
                    if (cs.getParameterTypes().length == 0) {
                        hasEmptyConstructor = true;
                        parms = new ArrayList();
                        break;
                    } else {
                        for (int j = 0; j < cs.getParameterTypes().length ; j++) {
                            Class c = cs.getParameterTypes()[j];
                            Object object = null;
                            try {
                                object = c.newInstance();
                            }
                            catch(Exception e) {
                            } finally { 
                                parms.add(object);
                            }
                        }
                    }
                }
                return getObjectFromClass(getClass(name.substring(0,index)),parms);
            }
            
        }
        return null;
    }
    
    /**
     * 
     * @param clazz
     * @return if the class is an inner class
     */
    public static boolean isInner(Class clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.getName().indexOf("$") != -1;
    } 
    
    /**
     * Tries to find a constructor with the parameters specified in the list
     * If it cannot it will return the empty constructor.
     * 
     * @param clazz
     * @param parms the list of parameters as classes
     * @return the instantiated object
     */
    public static Object getObjectFromClass(Class clazz, List parms) {
        try {
            if (parms != null && parms.size() > 0) {
                if (isInner(clazz) && !Modifier.isStatic(clazz.getModifiers())) {
                    parms.add(0, getParentObjectForInnerClass(clazz));
                }
                Class[] clzList = new Class[parms.size()];
                for (int i = 0; i < parms.size(); i++) {
                    clzList[i] = parms.get(i).getClass();
                }
                try {
                    Constructor constructor = clazz.getConstructor(clzList);
                    return constructor.newInstance(parms.toArray());
                }catch(NoSuchMethodException nsme) {
                    // eat me
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return getObjectFromClass(clazz);
    }
    
    /**
     * 
     * @param clazzString
     * @return the clazz created from the specified String or null 
     *          when it could not be created
     */
    public static Class getClass(String clazzString) {
        if (clazzString == null) {
            return null;
        }
        try {
            Class clazz = Class.forName(clazzString);
            return clazz;
        }
        catch (ClassNotFoundException e) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot find class "+clazzString);
            }
        }
        catch(NoClassDefFoundError ncdfe) {
            if (log.isWarnEnabled()) {
                log.warn("Using wrong name for class "+clazzString+ "\n" +                    ncdfe.getLocalizedMessage());
            }
        }
        return null;
    }

}
