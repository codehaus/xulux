/*
 $Id: BeanField.java,v 1.4 2002-11-03 14:29:21 mvdb Exp $

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class contains all the symantics for working
 * on a bean. 
 * TODO: investigate if this can be "static" (or registered
 * via a registry), since there aren't unlimited data beans
 * normally. Also need to check thread safety.
 * 
 * @author <a href="mailto:martin@mvdb.net>Martin van den Bemt</a>
 * @version $Id: BeanField.java,v 1.4 2002-11-03 14:29:21 mvdb Exp $
 */
public class BeanField implements IField
{
    
    /**
     * The official name of the field
     */
    private String name;
    /**
     * The method of the field
     */
    private Method method;
    /**
     * The alias of the field, 
     * This is the name to call this field.
     */
    private String alias;
    /**
     * Place holder for the setter
     * associated with the get method
     */
    private Method changeMethod;
    private boolean baseType;
    

    /**
     * Constructor for BeanField.
     */
    public BeanField()
    {
    }
    
    public BeanField(Method method)
    {
        setMethod(method);
    }
    
    /**
     * @return specifies if the field is just for display
     *          (eg doesn't have a setter method)
     */
    public boolean isReadOnly()
    {
        return (changeMethod==null);
    }
    
    /** 
     * Sets a new value in the field
     * The (should) contain all the logic for conversio
     * of primitive types.
     * 
     * @param bean - the bean to set the value on
     * @param value - the value to set to the bean
     * @return false on failure or if field is read only
     */
    public boolean setValue(Object bean, Object value)
    {
        boolean success = false;
        if (isReadOnly())
        {
            return success;
        }
        try
        {
            this.changeMethod.invoke(bean, new Object[]{value});
            success = true;
        }
        catch (IllegalAccessException e)
        {
        }
        catch (InvocationTargetException e)
        {
        }
        return success;
    }
    
    /**
     * Will return 
     * @param bean
     * @return the value of the field
     * or null when an error has happened or no value exists
     *  
     */
    public Object getValue(Object bean)
    {
        try
        {
            return this.method.invoke(bean, null);
        }
        catch (IllegalAccessException e)
        {
        }
        catch (InvocationTargetException e)
        {
        }
        return null;
    }
    
    

    /**
     * Returns the name.
     * @return String
     */
    public String getName()
    {
        return this.name.toLowerCase();
    }

    /**
     * Returns the method.
     * @return Method
     */
    public Method getMethod()
    {
        return method;
    }

    /**
     * Sets the method.
     * @param method The method to set
     */
    public void setMethod(Method method)
    {
        this.method = method;
        this.name = method.getName().substring("get".length());
    }
    
    /**
     * String representation of the beanField
     */
    public String toString()
    {
        return getMethod().getName()+"["+getAlias()+","+getName()+"]";
    }
    
    /**
     * Checks if we are taling about the same field..
     * @param object should be a beanField
     * @return 
     */
    public boolean equals(Object object)
    {
        if (object instanceof String)
        {
            if (this.getAlias().equalsIgnoreCase(object.toString()))
            {
                return true;
            }
        }
        else if (object instanceof BeanField)
        {
            Method method = ((BeanField)object).getMethod();
            if (method.getDeclaringClass().equals(this.getMethod().getDeclaringClass()) &&
                method.getName().equals(this.getMethod().getName()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the baseType.
     * @return boolean
     */
    public boolean isBaseType()
    {
        return baseType;
    }

    /**
     * Sets the baseType.
     * @param baseType The baseType to set
     */
    public void setBaseType(boolean baseType)
    {
        this.baseType = baseType;
    }
    
    public void setChangeMethod(Method method)
    {
        this.changeMethod = method;
    }

    /**
     * Returns the alias.
     * @return String
     */
    public String getAlias()
    {
        if (alias == null)
        {
            return getName();
        }
        return alias;
    }

    /**
     * Sets the alias.
     * @param alias The alias to set
     */
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

}