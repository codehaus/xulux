/*
   $Id: ClassLoaderUtils.java,v 1.5 2004-03-25 00:48:09 mvdb Exp $
   
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
 * @version $Id: ClassLoaderUtils.java,v 1.5 2004-03-25 00:48:09 mvdb Exp $
 */
public class ClassLoaderUtils {

    /**
     * the log instance
     */
    private static Log log = LogFactory.getLog(ClassLoaderUtils.class);

    /**
     * Make it possible to extend..
     */
    protected ClassLoaderUtils() {
    }

    /**
     *
     * @param classString the class
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
     * @param clazz the class
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
            log.warn("Cannot invocate target on " + clazz.getName());
        }
        catch (NoSuchMethodException e) {
            log.warn("Cannot find method on " + clazz.getName());
        }
        catch (InstantiationException e) {
            log.warn("Cannot instantiate class " + clazz.getName());
        }
        catch (IllegalAccessException e) {
            log.warn("Cannot access class " + clazz.getName());
        } catch (NoClassDefFoundError e) {
            log.warn("Cannot find class definition for class " + clazz.getName());
        }
        return null;
    }

    /**
     * Instantiates the parent object of an inner class.
     * @param clazz the class
     * @return the instance of the inner class
     */
    protected static Object getParentObjectForInnerClass(Class clazz) {
        String name = clazz.getName();
        int index = name.indexOf("$");
        if (index != -1 ) {
            name = name.substring(0, index);
            // cannot think of a scenario when this is null,
            Class clz = getClass(name.substring(0, index));
            ArrayList parms = new ArrayList();
            boolean hasEmptyConstructor = false;
            Constructor[] css = clz.getConstructors();
            for (int i = 0; i < css.length; i++) {
                Constructor cs = css[i];
                if (cs.getParameterTypes().length == 0) {
                    hasEmptyConstructor = true;
                    parms = new ArrayList();
                    break;
                } else {
                    for (int j = 0; j < cs.getParameterTypes().length; j++) {
                        Class c = cs.getParameterTypes()[j];
                        Object object = null;
                        try {
                            object = c.newInstance();
                        }
                        catch (Exception e) {
                            // eat it..
                        } finally {
                            parms.add(object);
                        }
                    }
                }
            }
            return getObjectFromClass(getClass(name.substring(0, index)), parms);
        }
        return null;
    }

    /**
     *
     * @param clazz the class
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
     * @param clazz the class
     * @param parms the list of parameters as classes
     * @return the instantiated object
     */
    public static Object getObjectFromClass(Class clazz, List parms) {
        try {
            if (parms != null && parms.size() > 0) {
                boolean cleanUp = false;
                if (isInner(clazz) && !Modifier.isStatic(clazz.getModifiers())) {
                    parms.add(0, getParentObjectForInnerClass(clazz));
                    cleanUp = true;
                }
                Class[] clzList = new Class[parms.size()];
                for (int i = 0; i < parms.size(); i++) {
                    clzList[i] = parms.get(i).getClass();
                }
                try {
                    Constructor constructor = clazz.getConstructor(clzList);
                    // clean up list..
                    Object retValue = constructor.newInstance(parms.toArray());
                    if (cleanUp) {
                        parms.remove(0);
                    }
                    return retValue;
                } catch (NoSuchMethodException nsme) {
                    // we should check alternative constructors
                    // eg new ObjectImpl(Object object) is an ok constructor
                    // when there is a String parameter.
                    Constructor[] constructors = clazz.getConstructors();
                    for (int c = 0; c < constructors.length; c++) {
                        Constructor constructor = constructors[c];
                        Class[] cclz = constructor.getParameterTypes();
                        boolean cStatus = true;
                        for (int cc = 0; cc < cclz.length; cc++) {
                            if (!cclz[cc].isAssignableFrom(clzList[cc])) {
                                cStatus = false;
                            }
                        }
                        if (cStatus) {
                            Object retValue = constructor.newInstance(parms.toArray());
                            if (cleanUp) {
                                parms.remove(0);
                            }
                            return retValue;
                        }
                    }
                    if (cleanUp) {
                        parms.remove(0);
                    }
                    return null;
                }
            }
        }
        catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Unknown error in getting object", e);
            }
        }
        return getObjectFromClass(clazz);
    }

    /**
     *
     * @param clazzString the class
     * @return the clazz created from the specified String or null
     *          when it could not be created
     */
    public static Class getClass(String clazzString) {
        if (clazzString == null) {
            return null;
        }
        try {
            Class clazz = null;
            try {
                clazz = Thread.currentThread().getContextClassLoader().loadClass( clazzString );
            }
            catch (ClassNotFoundException e) {
                clazz = new ClassLoaderUtils().getClass().getClassLoader().loadClass( clazzString );
            }
            return clazz;
        }
        catch (ClassNotFoundException e) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot find class " + clazzString);
            }
        }
        return null;
    }

}
