/*
   $Id: BeanMapping.java,v 1.1 2004-04-14 14:16:10 mvdb Exp $
   
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
package org.xulux.dataprovider.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;

/**
 * Contains the the Bean to Name mapping
 * Every field in the mapping is represented by a BeanField
 *
 * @todo Probably should use some kind of discovery / bean
 *       package to handle basic bean patterns. Just for Proof
 *       of concept I am reinventing the wheel a bit..;)
 * @todo Also fix the set when realField is used.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanMapping.java,v 1.1 2004-04-14 14:16:10 mvdb Exp $
 */
public class BeanMapping implements IMapping
{

    /**
     * the logging instance
     */
    private static Log log = LogFactory.getLog(BeanMapping.class);

    /**
     * The name of the mapping
     */
    private String name;
    /**
     * the bean to use for the mapping
     */
    private Class bean;
    /**
     * use discvovery for this mapping
     */
    private boolean discovery;
    /**
     * is this bean already discovered
     */
    private boolean isDiscovered;
    /**
     * the fields of the mapping
     */
    private FieldList fields;

    /**
     * Constructor for BeanMapping.
     */
    public BeanMapping() {
    }
    /**
     * Creates a BeanMapping with the specified name
     *
     * @param name - the beanMapping name
     */
    public BeanMapping(String name)
    {
        setName(name);
    }

    /**
     *
     * @return the name of the beanMapping
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the BeanMapping name.
     *
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the bean.
     *
     * @return Class
     */
    public Class getBean()
    {
        return bean;
    }

    /**
     * Sets the bean.
     *
     * @param bean The bean to set
     */
    public void setBean(Class bean)
    {
        this.bean = bean;
    }

    /**
     * Returns the discovery.
     *
     * @return boolean
     */
    public boolean isDiscovery()
    {
        return discovery;
    }

    /**
     * Sets the discovery.
     *
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
     * @return the beanfield with the specified name
     */
    public BeanField createBeanField(String name)
    {
        Method method = findMethod(name, false);
        Method setMethod = findMethod(name, true);
        if (method != null) {
            BeanField field = new BeanField(method);
            if (setMethod != null) {
                field.setChangeMethod(setMethod);
            }
            return field;
        }
        return null;
    }

    /**
     *
     * @param name the name the is contained in the method..getXXX/setXXX where XXX is the name
     *                (case insensitive..)
     * @param setMethod discover the setMethod (true) or the getMethod (false)
     * @return the method found
     */
    private Method findMethod(String name, boolean setMethod)
    {
        if (getBean() == null) {
            if (log.isWarnEnabled()) {
                log.warn("No Bean specified");
            }
            return null;
        }
        Method[] methods = getBean().getMethods();
        String pre = null;
        if (setMethod) {
            pre = "set";
        } else {
            pre = "get";
        }
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equalsIgnoreCase(pre + name)) {
                return method;
            } else if (!setMethod && method.getName().equalsIgnoreCase("is" + name)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Adds a field to the mapping
     *
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
            Class clazz = field.getMethod().getReturnType();
            Class baseClass = Dictionary.getInstance().getBaseClass();
            boolean discoverNestedBean = false;
            boolean isBaseTypeField = false;
            if (baseClass != null)
            {
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
                    // discover if the baseclass is the superclazz...
                    if (baseClass == tmpClass)
                    {
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
                Dictionary d = Dictionary.getInstance();
                if (d.getMapping(d.getPossibleMappingName(clazz)) == null
                    && d.getMapping(d.getPlainBeanName(clazz)) == null
                    && field.getMethod().getDeclaringClass() != clazz)
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
            // other fields not yet supported.
            return;
        }
        fields.add(f);
    }

    /**
     * @return all the fields in an arraylist
     */
    public List getFields()
    {
        return fields;
    }

    /**
     * This method will also search aliases
     * of the field.
     * @see org.xulux.dataprovider.IMapping#getField(java.lang.Object)
     */
    public IField getField(Object f)
    {
        if (fields == null || f == null) {
            return null;
        }
        String name = f.toString();
        // we by default strip out the widget pointer
        if (name.startsWith("?")) {
            int dotIndex = name.indexOf('.');
            if (dotIndex != -1) {
                name = name.substring(dotIndex + 1);
            }
        }

        int dotIndex = name.indexOf(".");
        String realField = null;
        if (dotIndex != -1) {
            String field = name.substring(0, dotIndex);
            realField = name.substring(dotIndex + 1);
            name = field;
        }
        int index = fields.indexOf(name);
        if (index == -1) {
            name = name+"."+realField;
            dotIndex = name.lastIndexOf(".");
            if (dotIndex != -1) {
                String field = name.substring(0, dotIndex);
                realField = name.substring(dotIndex + 1);
                name = field;
            } 
            // try a last indexOf to see if other conventions can work
            index = fields.indexOf(name);
        }
        if (index != -1)
        {
            BeanField bf =  (BeanField) fields.get(index);
            // set the real field if there is one..
            if (realField != null) {
                bf.setTempRealField(realField);
            } else if (bf.hasTempRealField()) {
                // if the beanfield had a temporary real field
                // and not having a new realfield, remove it.
                bf.removeTempRealField();
            }

            return bf;
        }
        return null;
    }

    /**
     * Will discover the fields in the bean.
     * if discovery is set and discovery has not yet taken
     * place. Discovery will ignore getters which are private
     * or protected.
     * It will also try to discover the set method that is connected
     * to the getter (assuming it is not a read only field).
     *
     * @todo What to do with collections ?
     */
    public void discover()
    {
        if (!isDiscovered && isDiscovery())
        {
            Method[] methods = bean.getMethods();
            for (int i = 0; i < methods.length; i++)
            {
                Method method = methods[i];
                if ((method.getName().toLowerCase().startsWith("get")
                     || method.getName().toLowerCase().startsWith("is"))
                    && !method.getName().equals("getClass")
                    && method.getModifiers() != Modifier.PRIVATE
                    && method.getModifiers() != Modifier.PROTECTED)
                {
                    BeanField field = new BeanField(method);
                    // try to find the setter..
                    String fieldName = field.getName();
                    if (fieldName.length() > 0) {
                        fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    }
                    Method setMethod = findMethod(fieldName, true);
                    field.setChangeMethod(setMethod);
                    addField(field);
                }
            }
            isDiscovered = true;
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object)
    {
        if (object instanceof BeanMapping) {
            String mappingName = ((BeanMapping) object).getName();
            return mappingName.equals(this.getName());
        }
        return false;
    }


    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getName();
    }

    /**
     * Inner ArrayList with an overriden indexOf
     * Which checks equals on the object In
     * the arraylist instead of the object passed
     *
     * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
     */
    public class FieldList extends ArrayList
    {
        /**
         * The fieldlist constructor
         */
        public FieldList() {
            super();
        }

        /**
         * Override the indexOf, since the java one is
         * calling equals on elem and we want to call
         * equals on the alement in the list.
         * Null will always return -1.
         *
         * @param elem the element to find
         * @return int - the position or -1 when not found
         */
        public int indexOf(Object elem) {
            if (elem == null) {
                return -1;
            }
            for (int i = 0; i < size(); i++) {
                Object data = get(i);
                if (data != null && data.equals(elem)) {
                    return i;
                }
            }
            return -1;
        }
    }

}
