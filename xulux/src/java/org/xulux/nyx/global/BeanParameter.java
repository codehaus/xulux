/*
 $Id: BeanParameter.java,v 1.8 2003-12-01 02:08:21 mvdb Exp $

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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * Place holder and utility class for the parameter list
 * that a method may need to get or set appropiate data.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanParameter.java,v 1.8 2003-12-01 02:08:21 mvdb Exp $
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
