/*
 $Id: BeanMapping.java,v 1.7 2002-12-22 23:32:05 mvdb Exp $

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
 * Contains the the Bean to Name mapping
 * Every field in the mapping is represented by a BeanField
 * 
 * TODO: Probably should use some kind of discovery / bean 
 * package to handle basic bean patterns. Just for Proof
 * of concept I am reinventing the wheel a bit..;)
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanMapping.java,v 1.7 2002-12-22 23:32:05 mvdb Exp $
 */
public class BeanMapping
{

    private String name;
    private Class bean;
    private boolean discovery;
    private boolean isDiscovered;
    private FieldList fields;
    private static int fail = 0;

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
    
    /**
     * Creates a beanfield based on the passed name
     * If no beanField can be created based on the name
     * null will be returned.
     * 
     * @param name - the name of the field methods to discover.
     */
    public BeanField createBeanField(String name)
    {
        Method method = findMethod(name, false);
        Method setMethod = findMethod(name, true);
        if (method != null)
        {
            BeanField field = new BeanField(method);
            if (setMethod != null)
            {
                field.setChangeMethod(setMethod);
            }
            return field;
        }
        return null;
    }
    
    /**
     * @param name - the name the is contained in the method..getXXX/setXXX where XXX is the name 
     *                (case insensitive..)
     * @param setMethod - discover the setMethod (true) or the getMethod (false)
     */
    private Method findMethod(String name, boolean setMethod)
    {
        Method[] methods = getBean().getMethods();
        String pre = (setMethod)?"set":"get";
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equalsIgnoreCase(pre+name))
            {
                return method;
            }
        }
        return null;
    }
    
    /**
     * Adds a field to the mapping
     * @param f - the field
     */
    public void addField(IField f)
    {
        if (fields == null)
        {
            fields = new FieldList();
        }
        
        if (f instanceof BeanField)
        {
            BeanField field = (BeanField) f;
            //System.out.println("Name : "+f.getName());

            Class clazz = field.getMethod().getReturnType();
            Class baseClass = Dictionary.getInstance().getBaseClass();
            boolean discoverNestedBean = false;
            boolean isBaseTypeField = false;
            //System.out.println("clazz : "+clazz);
            //System.out.println("baseClass : "+baseClass);
            
            //System.exit(0);
            if (baseClass != null)
            {
                //System.out.println("baseclass is interface ? "+baseClass.isInterface());
                if (baseClass.isInterface())
                {
                    Class[] ifaces = clazz.getInterfaces();
                    for (int i = 0; i < ifaces.length; i++)
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
                    Class tmpClass = clazz;
                    //System.err.println("tmpClass : "+tmpClass);
                    // discover if the baseclass is the superclazz...
                    if (baseClass == tmpClass)
                    {
                        //System.err.println("baseclass is tmpClass");
                        discoverNestedBean = false;
                    }
                    else
                    {
                        while (tmpClass != null && tmpClass != Object.class)
                        {
                            if (baseClass == tmpClass.getSuperclass())
                            {
                                discoverNestedBean = true;
                                isBaseTypeField = true;
                                break;
                            }
                            tmpClass = tmpClass.getSuperclass();
                        }
                    }
                }
            }
            if (discoverNestedBean)
            {
                //System.out.println("WE need to discover nestedbean!");
                Dictionary d = Dictionary.getInstance();
                //System.out.println("PlainbeanName : "+d.getPlainBeanName(clazz));
//                System.exit(0);
                //System.err.println("Mapping : "+d.getMapping("Qualifier")+ " for class "+clazz.getName());
                //System.out.println("Possible mapping : "+d.getPossibleMappingName(clazz));
                //System.out.println("f "+field.getMethod().getDeclaringClass());
                if (d.getMapping(d.getPossibleMappingName(clazz)) == null &&
                    d.getMapping(d.getPlainBeanName(clazz)) == null  &&
                    field.getMethod().getDeclaringClass() != clazz)
                {
                    if (!d.isInCache(clazz))
                    {
                        d.getMapping(clazz);
                    }
                }
            }
            field.setBaseType(isBaseTypeField);
        }
        else
        {
            return;
        }
        fields.add(f);
    }
    
    /**
     * Returns all the fields in an arraylist
     */
    public ArrayList getFields()
    {
        return fields;
    }
    
    /**
     * @param name
     * @return the beanfield for the specified 
     * field or null when no field is present
     */
    public IField getField(String name)
    {
        int index = fields.indexOf(name);
        if (index != -1)
        {
            return (BeanField) fields.get(index);
        }
        return null;
    }

    /**
     * Will discover the fields in the bean.
     * if discovery is set and discovery has not yet taken
     * place. Discovery will ignore getters which are private
     * or protected.
     * It will also discover the set method that is connected
     * to the getter (assuming it is not a read only field.
     * 
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
                    // try to find the setter..
                    try
                    {
                        String fieldName = field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                        //System.out.print("Looking up :"+fieldName);
                        //System.out.println(" with type : "+method.getReturnType());
                        Method setMethod = bean.getMethod("set"+fieldName, new Class[]{method.getReturnType()});
                        //System.out.println("setMethod : "+setMethod.getName());
                        field.setChangeMethod(setMethod);
                    }
                    catch (NoSuchMethodException e)
                    {
                    }
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
    
    /**
     * Inner ArrayList with an overriden indexOf
     * Which checks equals on the object In 
     * the arraylist instead of the object passed
     * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
     */
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
    
    public String toString()
    {
        return getName();
    }
}
