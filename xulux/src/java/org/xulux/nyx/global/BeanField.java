/*
 $Id: BeanField.java,v 1.15 2003-07-17 02:50:13 mvdb Exp $

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

package org.xulux.nyx.global;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class contains all the symantics for working
 * on a bean.
 * 
 * @todo investigate if this can be "static" (or registered
 *       via a registry), since there aren't unlimited data beans
 *       normally. 
 * @todo Need to check thread safety.
 * @todo This class should contain all the logic for conversion
 *       to primitive types.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanField.java,v 1.15 2003-07-17 02:50:13 mvdb Exp $
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
    
    /**
     * Specifies if this beanField is derived form a
     * baseType or not
     */
    private boolean baseType;
    
    /**
     * The parameterlist
     */
    private List parameterList;
    
    /**
     * Temporary container for args.
     * Needs to be removed when we switch
     * to non fixed arguments.
     */
    private Object[] args;
    
    /**
     * Holds the real field.
     */
    private String realField;
    
    /**
     * the logger
     */
    private Log log = LogFactory.getLog(BeanField.class);
    

    /**
     * Constructor for BeanField.
     */
    public BeanField()
    {
    }
    
    /**
     * Contructor that takes a method
     * 
     * @param method - the method to use for reading the value
     */
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
     * Exceptions will be eaten.
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
            if (log.isWarnEnabled()) {
                log.warn("Field "+getName()+" is readonly or couldn't find the set Method");
            }
            return success;
        }
        try
        {
            System.err.println("changeMethod : "+changeMethod);
            //System.err.println("Value : "+value.getClass());
            if (this.realField != null) {
                // set the real value..
                Class retType = getMethod().getReturnType();
                BeanMapping mapping = Dictionary.getInstance().getMapping(retType);
                Object realBean = getMethod().invoke(bean, getArgs());
                IField field = mapping.getField(realField);
                field.setValue(realBean, value);
            } else {
                System.err.println("changeMethod : "+changeMethod);
                System.err.println("Value : "+value.getClass());
                Class valClass = value.getClass();
                List parmList = Arrays.asList(changeMethod.getParameterTypes());
                // TODO: Support multiple parameters when setting..
                if (parmList.size() > 0 ) {
                    Class cl = (Class) parmList.get(0);
                    // TODO: Error prone probably...
                    // What this does is if the object set as value
                    // is not a String and the parameter that needs
                    // to be used is a string, it will try to  
                    // invoke the toString method on the value.
                    if (cl == String.class) {
                        // try a toString..
                        if (value != null) {
                            value = value.toString();
                        }
                    }
                }
                try {
                    this.changeMethod.invoke(bean, new Object[]{value});
                }catch(IllegalArgumentException iae) {
                    if (log.isWarnEnabled()) {
                        log.warn("Invalid argument "+value.getClass().getName()+" for method "+this.changeMethod);
                    }
                    success = false;
                }
                    
            }
            success = true;
        }
        catch (IllegalAccessException e)
        {
            if (log.isTraceEnabled()) {
                log.trace("Exception occured ",e);
            }
        }
        catch (InvocationTargetException e)
        {
            if (log.isTraceEnabled()) {
                log.trace("Exception occured ",e);
            }
        }
        return success;
    }
    
    /**
     * Get the value from the specified bean.
     * Exceptions will be eaten.
     * 
     * @param bean - the bean to get the value from
     * @return the value of the field
     * or null when an error has happened or no value exists
     *  
     */
    public Object getValue(Object bean)
    {
        try
        {
            if (this.realField != null) {
                // gets the real value to get
                // since this field is just the parent..
                Class retType = getMethod().getReturnType();
                BeanMapping mapping = Dictionary.getInstance().getMapping(retType);
                IField field = mapping.getField(realField);
                return field.getValue(getMethod().invoke(bean, getArgs()));
            }
            else {
//                System.err.println("field : "+getName());
//                System.err.println("bean :"+bean);
                if (bean != null) {
//                    System.err.println("Method "+getMethod().getName());
//                    System.err.println("method parameters : "+getParameters()); 
//                    System.err.println("Args "+Arrays.asList(getMethod().getParameterTypes()));
                    return this.method.invoke(bean, getArgs());
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("no data found for "+getName()+", alias "+getAlias()+", realfield "+realField);
                        
                    }
                }
            }
        }
        catch (IllegalAccessException e)
        {
            if (log.isTraceEnabled())
            {
                log.trace("Exception occured ",e);
            }
        }
        catch (InvocationTargetException e)
        {
            if (log.isWarnEnabled())
            {
                log.warn("Exception occured ",e.getTargetException());
            }
        }
        return null;
    }
    
    /**
     * TODO: for now we assume all is hardcoded data fix that..
     * @return the list of parameters to pass into
     *          the method.
     */
    protected Object[] getArgs() {
        if (this.args != null) {
            return this.args;
        }
        if (parameterList == null) {
            return null;
        }
        int listCounter = parameterList.size();
        this.args = new Object[listCounter];
        Iterator it = parameterList.iterator();
        listCounter = 0;
        while (it.hasNext()) {
            BeanParameter parm = (BeanParameter) it.next();
            this.args[listCounter] = parm.getObject();
            listCounter++;
        }
        return this.args;
    }
    

    /**
     * Returns the name.
     * 
     * @return String
     */
    public String getName()
    {
        return this.name.toLowerCase();
    }

    /**
     * Returns the method.
     * 
     * @return Method
     */
    public Method getMethod()
    {
        return method;
    }

    /**
     * Sets the method.
     * 
     * @param method The method to set
     */
    public void setMethod(Method method)
    {
        this.method = method;
        this.name = method.getName().substring("get".length());
    }
    
    /**
     * 
     * @return String representation of the beanField
     */
    public String toString()
    {
        return getMethod().getName()+"["+getAlias()+","+getName()+"]";
    }
    
    /**
     * Checks if we are talking about the same field..
     * 
     * @param object - the object to perform the equals on
     * @return If the object is a String it will compare it with
     *          the Alias (case insensitive).
     *          If the object is another BeanField, it will see
     *          if the declaring class and the method name is the same
     *          In all other cases it will return false
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
            if (method.getDeclaringClass().equals(this.getMethod().getDeclaringClass()) 
                 && method.getName().equals(this.getMethod().getName()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the baseType.
     * 
     * @return boolean
     */
    public boolean isBaseType()
    {
        return baseType;
    }

    /**
     * Sets the baseType.
     * 
     * @param baseType The baseType to set
     */
    public void setBaseType(boolean baseType)
    {
        this.baseType = baseType;
    }
    
    /**
     * @param method - the change method for this field.
     */
    public void setChangeMethod(Method method)
    {
        this.changeMethod = method;
    }

    /**
     * Returns the alias.
     * 
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
     * 
     * @param alias The alias to set
     */
    public void setAlias(String alias)
    {
        this.alias = alias;
    }
    
    /** 
     * Sets the parameters of the beanfield.
     * This will overwrite all previous values.
     * Use addParameter instead if you want to preserve them
     * 
     * @param parameters
     */
    public void setParameters(List parameters) {
        this.parameterList = parameters;
    }
    
    /**
     * 
     * @return a list with currently know parameters or null
     *          when no parameters are known.
     */
    public List getParameters() {
        return this.parameterList;
    }
    
    /**
     * Add a single parameter to the parameter list
     * @param parameter
     */
    public void addParameter(BeanParameter parameter) {
        if (parameterList == null) {
            parameterList = new ArrayList();
        }
        parameterList.add(parameter);
    }

    /**
     * @param realField
     */
    public void setRealField(String realField) {
        this.realField = realField;
    }
    
    /**
     * 
     * @return the returntype of the getmethod.
     */
    public Class getReturnType() {
        return this.method.getReturnType();
    }

}
