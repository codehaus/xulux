/*
   $Id: BeanParameter.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
   
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
package org.xulux.global;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.xulux.utils.ClassLoaderUtils;

/**
 * Place holder and utility class for the parameter list
 * that a method may need to get or set appropiate data.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanParameter.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
 */
public class BeanParameter {

    /**
     * the type of parameter
     */
    private String type;
    /**
     * the value
     */
    private String value;
    /**
     * the object
     */
    private Object object;

    /**
     *
     */
    public BeanParameter() {
    }

    /**
     * Convenient constructor
     *
     * @param type the type of parameter
     * @param value the value of the type
     */
    public BeanParameter(String type, String value) {
        setType(type);
        setValue(value);
    }

    /**
     * @return the type of parameter
     */
    public String getType() {
        return this.type;
    }

    /**
     * @return the value of the type
     *          (eg string, className or className+field)
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the type.
     * String or static
     * @param type the type of parameter
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Set the value of the type
     * eg "M" or eg StaticClass.M
     * @param value the value of the type
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return the object that contains the value
     *          we need to pass as the parameter
     */
    public Object getObject() {
        if (this.object == null) {
            if (getType().equalsIgnoreCase("string")) {
                this.object = value;
            } else if (getType().equalsIgnoreCase("static")) {
                // static format is like package.Class.Field, so first
                // the class needs processing.
                int lastIndex = getValue().lastIndexOf(".");
                String field = getValue().substring(getValue().lastIndexOf('.') + 1);
                String classString = null;
                if (lastIndex != -1) {
                    classString = getValue().substring(0, getValue().lastIndexOf("."));
                } else {
                    // fall back to the value..
                    classString = getValue();
                }
                Class clazz = ClassLoaderUtils.getClass(classString);
                if (clazz != null) {
                    try {
                        Field f = clazz.getDeclaredField(field);
                        // we can pass in null, since it (should) be static
                        if (Modifier.isStatic(f.getModifiers())) {
                            this.object = f.get(null);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace(System.out);
                    }
                }
            }
        }
        return this.object;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getType() + ":" + getValue();
    }

}
