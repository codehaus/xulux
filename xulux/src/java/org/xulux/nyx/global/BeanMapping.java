/*
 $Id: BeanMapping.java,v 1.1 2002-10-29 00:10:03 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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

package org.xulux.nyx.global;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: BeanMapping.java,v 1.1 2002-10-29 00:10:03 mvdb Exp $
 */
public class BeanMapping
{

    private String name;
    private Class bean;
    private boolean discovery;
    private boolean isDiscovered;
    private FieldList fields;

    /**
     * Constructor for BeanMapping.
     */
    public BeanMapping()
    {
    }

    public BeanMapping(String name)
    {
        setName(name);
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the bean.
     * @return Class
     */
    public Class getBean()
    {
        return bean;
    }

    /**
     * Sets the bean.
     * @param bean The bean to set
     */
    public void setBean(Class bean)
    {
        this.bean = bean;
    }

    /**
     * Returns the discovery.
     * @return boolean
     */
    public boolean isDiscovery()
    {
        return discovery;
    }

    /**
     * Sets the discovery.
     * @param discovery The discovery to set
     */
    public void setDiscovery(boolean discovery)
    {
        this.discovery = discovery;
    }

    public void addField(BeanField field)
    {
        if (fields == null)
        {
            fields = new FieldList();
        }
        Class clazz = field.getMethod().getReturnType();
        Class baseClass = Dictionary.getInstance().getBaseClass();
        boolean discoverNestedBean = false;
        boolean isBaseTypeField = false;
        if (baseClass != null)
        {
            //System.out.println("baseclass is interface ? "+baseClass.isInterface());
            if (baseClass.isInterface())
            {
                Class[] ifaces = clazz.getInterfaces();
                for (int i=0; i < ifaces.length;i++)
                {
                    if (ifaces[i] == baseClass)
                    {
                        discoverNestedBean = true;
                        isBaseTypeField = true;
                    }
                }
            }
            else
            {
                //System.out.println("superclass : "+clazz.getSuperclass());
                //System.out.println("clazz : "+clazz);
                if (baseClass == clazz.getSuperclass())
                {
                    discoverNestedBean = true;
                    isBaseTypeField = true;
                }
            }
        }
        //System.out.println("WE need to discover nestedbean ? "+discoverNestedBean);
        if (discoverNestedBean)
        {
            Dictionary d = Dictionary.getInstance();
            System.out.println("PlaingbeanName : "+d.getPlainBeanName(clazz));
            if (d.getMapping(d.getPossibleMappingName(clazz)) == null)
            {
                d.getMapping(clazz);
            }
        }
        field.setBaseType(isBaseTypeField);
        fields.add(field);
    }

    public ArrayList getFields()
    {
        return fields;
    }

    /**
     * Will discover the fields in the bean.
     * if discovery is set and discovery has not yet taken
     * place. Discovery will ignore getters which are private
     * or protected.
     */
    public void discover()
    {
        if (!isDiscovered && isDiscovery())
        {
            Method[] methods = bean.getMethods();
            for (int i = 0; i < methods.length; i++)
            {
                Method method = methods[i];
                if (method.getName().toLowerCase().startsWith("get")
                    && !method.getName().equals("getClass")
                    && method.getModifiers() != Modifier.PRIVATE
                    && method.getModifiers() != Modifier.PROTECTED)
                {

                    BeanField field = new BeanField(method);
                    addField(field);
                }
            }
            isDiscovered = true;
        }
    }

    public boolean equals(Object object)
    {
        if (object instanceof BeanMapping)
        {
            String name = ((BeanMapping) object).getName();
            return name.equals(this.getName());
        }
        return false;
    }

    public class FieldList extends ArrayList
    {
        public FieldList()
        {
            super();
        }

        /**
         * Override the indexOf, since the java one is 
         * calling equals on elem and we want to call
         * equals on the alement in the list.
         * Null will always return -1.
         * 
         * @param elem
         * @return int - the position or -1 when not found
         */
        public int indexOf(Object elem)
        {
            if (elem == null)
            {
                return -1;
            }
            for (int i = 0; i < size(); i++)
            {
                Object data = get(i);
                if (data != null && data.equals(elem))
                {
                    return i;
                }
            }
            return -1;
        }

    }
}
