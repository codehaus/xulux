/*
   $Id: BeanField.java,v 1.1 2004-03-16 14:35:14 mvdb Exp $
   
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
package org.xulux.dataprovider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.utils.ClassLoaderUtils;

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
 * @version $Id: BeanField.java,v 1.1 2004-03-16 14:35:14 mvdb Exp $
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
     * Parameterlist for the setmethod.
     * is null when equal to the parameterList
     */
    private List setParameterList;

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
     * Temporary real field when processing strange naming
     */
    private boolean tempRealField;

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
    public boolean isReadOnly() {
        if (changeMethod == null) {
            return true;
        }
        return false;
    }

    /**
     * Sets a new value in the field
     * Exceptions will be eaten.
     * @param bean - the bean to set the value on
     * @param value - the value to set to the bean
     * @return false on failure or if field is read only
     * @todo add some more intensive testing
     */
    public boolean setValue(Object bean, Object value)
    {
        boolean success = false;
        if (isReadOnly())
        {
            if (log.isWarnEnabled()) {
                log.warn("Field " + getName() + " is readonly or couldn't find the set Method");
            }
            return success;
        }
        try
        {
            if (getRealField() != null) {
                Class retType = getReturnType();
                BeanMapping mapping = Dictionary.getInstance().getMapping(retType);
//                log.warn("Mapping : "+mapping.getFields());
                Object childObject = getMethod().invoke(bean, getArgs());
//                log.warn("ChildObject ; "+childObject);
                if (childObject == null) {
                    // try to create the object!
                    childObject = ClassLoaderUtils.getObjectFromClass(retType, getBeanParameterValues(parameterList));
//                    log.warn("ChildObject ; "+childObject);
                    getChangeMethod().invoke(bean, getSetMethodArgs(childObject));

                    // @todo Preform some magic to set the object to the bean!!
                    if (childObject == null) {
                        if (log.isWarnEnabled()) {
                            log.warn("Cannot set value on " + toString() + "  Please set the value in a rule "
                            +  "or provide an empty constructor");
                        }
                        return false;
                    }
                }
                BeanMapping childMapping = Dictionary.getInstance().getMapping(childObject);
//                log.warn("childMapping : "+childMapping.getFields());
                IField field = childMapping.getField(getRealField());
//                log.warn("cf : "+field);
                success = field.setValue(childObject, value);
                success = true;
                return success;
            }
            try {
//                log.warn("Change method : "+this.changeMethod);
//                log.warn("args : "+Arrays.asList(getSetMethodArgs(value)));
                this.changeMethod.invoke(bean, getSetMethodArgs(value));
                realField = null;
            } catch (IllegalArgumentException iae) {
                if (log.isWarnEnabled()) {
                    log.warn("Invalid argument " + value.getClass().getName() + " for method " + this.changeMethod, iae);
                }
                success = false;
            }
            success = true;
        }
        catch (IllegalAccessException e)
        {
            if (log.isTraceEnabled()) {
                log.trace("Exception occured ", e);
            }
        }
        catch (InvocationTargetException e)
        {
            if (log.isTraceEnabled()) {
                log.trace("Exception occured ", e);
            }
        }
        catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Unexcpected exception ", e);
            }
        }
        return success;
    }

    /**
     * @param list the list of objects
     * @return a list of values from a list with beanparameter objects.
     */
    private List getBeanParameterValues(List list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        ArrayList valueList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            if (object instanceof BeanParameter) {
                valueList.add(((BeanParameter) object).getObject());
            }
        }
//        log.warn("valueList : "+valueList);
        return valueList;
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
                Class retType = getReturnType();
                if (retType == Object.class) {
                    // we need to figure out which returntype we REALLY have..
                    Object retBean = getMethod().invoke(bean, getArgs());
                    retType = retBean.getClass();
                }
                BeanMapping mapping = Dictionary.getInstance().getMapping(retType);
                IField field = mapping.getField(realField);
                Object fieldValue = field.getValue(getMethod().invoke(bean, getArgs()));
                return fieldValue;
            }
            else {
                if (bean != null) {
                    return this.method.invoke(bean, getArgs());
                } else {
                    // try to see if it is static..

                    if (Modifier.isStatic(getMethod().getModifiers())) {
                        return getMethod().invoke(null, getArgs());
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("no data found for " + getName() + ", alias " + getAlias() + ", realfield " + realField);

                    }
                }
            }
        }
        catch (IllegalAccessException e)
        {
            if (log.isTraceEnabled())
            {
                log.trace("Exception occured ", e);
            }
        }
        catch (InvocationTargetException e)
        {
            if (log.isWarnEnabled())
            {
                log.warn("Exception occured ", e.getTargetException());
            }
        }
        return null;
    }

    /**
     * @todo for now we assume all is hardcoded data need to fix that..
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
     * No nullchecking is done in this method,
     * the caller should check if it can correctly
     * call this method.
     * (eg isReadOnly should be false)
     *
     * @param value get the setmethod for the specified value
     * @return the list of methods to pass into a
     *          setmethod to correctly set a value.
     */
    protected Object[] getSetMethodArgs(Object value) {
        ArrayList parms = new ArrayList();
        Class[] clz = this.changeMethod.getParameterTypes();
        int parmSize = 0;
        if (parameterList != null) {
            parmSize = parameterList.size();
        }
        int clzSize = clz.length;
        // actually always the case I guess??
       if (clzSize == 1) {
            Class parmType = clz[0];
            if (value != null) {
                if (parmType == value.getClass()) {
                    return new Object[] {value};
                }
            }
        }
        if (parmSize <= clzSize && clzSize != 0) {
            if (parmSize == 0 && clzSize == 1) {
                if (clz[0] == String.class) {
                    if (value == null) {
                        return new Object[] {
                            value
                            };
                    } else {
                        return new Object[] {
                            value.toString()
                            };
                    }
                } else {
                    IConverter converter = Dictionary.getConverter(clz[0]);
                    if (converter != null) {
                        Object newValue = converter.getBeanValue(value);
                        if (newValue != null) {
                            value = newValue;
                        }
                    }
                    return new Object[] {
                        value
                        };
                }
            }
            /* simple logistics :
             * eg getXXX(String) should have a setXXX(Strint, Value);
             * @todo Make more advanced, or look at external package
             *        to handle this.
             */

            if (parmSize == 1 && clzSize == 2) {
                Object[] retValue = new Object[clzSize];
                int currentParm = 1;
                retValue[0] = ((BeanParameter) parameterList.get(0)).getObject();
                if (clz[0] != retValue[0].getClass()) {
                    retValue[1] = retValue[0];
                    currentParm = 0;
                }
                if (clz[currentParm] == String.class) {
                    retValue[currentParm] = value.toString();
                } else {
                    /* We should try to make the type the same
                     * so if the value is a string and the type
                     * an integer, some conversion needs to take
                     * place
                     * @todo Fix this :)
                     */
                     if (clz[currentParm] == value.getClass()) {
                        retValue[currentParm] = value;
                     } else {
                         log.warn("Cannot set value of type " + value.getClass().getName() + " for the parameter " + clz[1]);
                         // we are hardheaded and set it anyway, so we can
                         // get some kind of exception.
                         // @todo FIX!!
                         retValue[currentParm] = value;
                     }
                }
                return retValue;
            }

        }
        return parms.toArray();
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
        this.name = method.getName().toLowerCase();
        if (this.name.startsWith("get")) {
            this.name = this.name.substring("get".length());
        } else if (this.name.startsWith("is")) {
            this.name = this.name.substring("is".length());
        }
    }

    /**
     *
     * @return String representation of the beanField
     */
    public String toString()
    {
        return getMethod().getName() + "[" + getAlias() + "," + getName() + ",realField=" + getRealField() + "]";
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
            } else if (getMethod().getName().equalsIgnoreCase(object.toString())) {
                // if the method name is the same as this
                // return true..
                return true;
            }
        }
        else if (object instanceof BeanField) {
            Method tmpMethod = ((BeanField) object).getMethod();
            if (tmpMethod.getDeclaringClass().equals(this.getMethod().getDeclaringClass())
                 && tmpMethod.getName().equals(this.getMethod().getName())) {
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
     * Convenience method to set the changemethod.
     * It will figure out which changemethod it will
     * be..
     * @param clazz the class to investigate
     * @param name the name of the change method
     */
    public void setChangeMethod(Class clazz, String name) {
        Method[] methods = clazz.getMethods();
        //System.out.println("methods : "+Arrays.asList(methods));
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if (m.getName().equalsIgnoreCase(name)) {
                //System.out.println("Found changeMethod :"+m);
                setChangeMethod(m);
                break;
            }
        }
        if (!name.startsWith("set")) {
            setChangeMethod(clazz, "set" + name);
        }
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
     * @param parameters the parameters to use
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
     * @param parameter the parameter to add
     */
    public void addParameter(BeanParameter parameter) {
        if (parameterList == null) {
            parameterList = new ArrayList();
        }
        parameterList.add(parameter);
    }

    /**
     * @param realField the real field to use
     */
    public void setRealField(String realField) {
        this.realField = realField;
    }

    /**
     * Set a temporary realfield, which is not dictionary
     * based.
     * @param realField the temporary realfield
     */
    public void setTempRealField(String realField) {
        this.realField = realField;
        this.tempRealField = true;
    }

    /**
     * @return if this field has a temporary realfield
     */
    public boolean hasTempRealField() {
        return this.tempRealField;
    }

    /**
     * Remove the temporaray real field
     */
    public void removeTempRealField() {
        this.realField = null;
        this.tempRealField = false;
    }

    /**
     *
     * @return the real underlying field.
     */
    public String getRealField() {
        return this.realField;
    }

    /**
     *
     * @return the returntype of the getmethod.
     */
    public Class getReturnType() {
        return this.method.getReturnType();
    }

    /**
     * @return the changemethod
     */
    protected Method getChangeMethod() {
        return this.changeMethod;
    }

}
