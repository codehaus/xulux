/*
 $Id: GuiField.java,v 1.3 2002-10-31 01:44:26 mvdb Exp $

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
package org.xulux.nyx.swing.factories;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.utils.Resources;


import org.xulux.nyx.swing.masks.IMask;
import org.xulux.nyx.swing.masks.StringMask;
import org.xulux.nyx.examples.datamodel.DefaultBase;
import org.xulux.nyx.global.BeanField;

/**
 * This contains specifics for the GuiFields.
 * NOTE: Current problem is that there is way too much method exposure.
 * Should investigate collaberation between BeanField and GuigField..
 * Lot of cleaning up to do, since I've started to move config over
 * to xml, since property files are (ehh can get) messy if not
 * solely used for translations ;)
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiField.java,v 1.3 2002-10-31 01:44:26 mvdb Exp $
 */
public class GuiField
{

    private Log log = LogFactory.getLog(GuiField.class);
    public static String MASK = "mask"; //$NON-NLS-1$
    public static String DEFAULT_FIELD_MASK = "default.fieldMask."; //$NON-NLS-1$
    public static String REQUIRED = "required"; //$NON-NLS-1$
    public static String LABEL = "label"; //$NON-NLS-1$
    public static String DOT = "."; //$NON-NLS-1$
    public static String TYPE = "type"; //$NON-NLS-1$
    public static String DEFAULT_GET = "get"; //$NON-NLS-1$
    public static String DEFAULTMASKTYPE = "String"; //$NON-NLS-1$
    public static String TRUE = "true"; //$NON-NLS-1$
    public static String EMPTYSTRING = ""; //$NON-NLS-1$
    public static String DEFAULTLABELPREFIX = EMPTYSTRING;
    public static String DEFAULTLABELPOSTFIX = EMPTYSTRING;
    public static String FIELD_PREFIX = "field.prefix"+DOT;
    
    /**
     * Holds the BeanField
     */
    private BeanField beanField;
    
    /** 
     * if this field is required
     */
    private boolean required;
    
    /**
     * Specifies if this field is a baseType
     */
    private boolean baseType;

    /**
     * The label of the field
     */
    private String label;

    /**
     * The actual field
     * format : <code>tcbase.fieldname</code>
     */
    private String field;

    /**
     * the clazz to get the field information from (from .properties)
     */
    private Class resourceClass;

    /**
     * the prefix of the class
     * which normally is the form currently being processed
     */
    private String prefix;

    private Class returnType;
    private Method method;
    
    
    public GuiField(BeanField beanField)
    {
        setBeanField(beanField);
        this.method = beanField.getMethod();
    }
    /**
     * Constructor for Field.
     * @param field - the field name
     * @param resourceClass - the class to get the resources from
     */
    public GuiField(String prefix, String field, Class resourceClass)
    {
        log.trace("Field : "+field);
        setField(field);
        this.resourceClass = resourceClass;
        this.prefix = prefix;
        setConstraints();
    }

    /**
     */
    public String getCurrentValue(Object base)
    {
        Object object = getCurrentObject(base);
        if (object != null)
        {
            return object.toString();
        }
        return null;
    }
    
    /** 
     * Returns the value that is associ
     */
    public Object getCurrentObject(Object base)
    {
        try
        {
            //System.out.println("invoking method on : "+base.getClass());
            //System.out.println("method : "+method.getDeclaringClass().getName());
            return this.method.invoke(base, null);
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
     * Returns the bean.
     * Normally only used when you implemented the IPersistRule interface..
     */
    public Object getBean()
    {
        return null;
    }
    
    /** 
     * @return if the field has changed in regard to the bean.
     */
    public boolean isDirty()
    {
        return false;
    }
    
    /**
     * Resets the value to the original bean value
     */
    public void reset()
    {
        // TODO
    }
    
    /**
     * Returns the field.
     * @return String
     */
    public String getField()
    {
        return field;
    }

    /**
     * Returns the label.
     * @return String
     */
    public String getLabel()
    {
        if (label == null || label.equals(EMPTYSTRING)) 
        {
            return DEFAULTLABELPREFIX + getField() + DEFAULTLABELPOSTFIX;
        }
        return DEFAULTLABELPREFIX + label + DEFAULTLABELPOSTFIX;
    }

    /**
     * Returns the plain label of the field
     * 
     * @return String
     */
    public String getPlainLabel()
    {
        return label;
    }

    /**
     * Returns the required.
     * @return boolean
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * Sets the field.
     * @param field The field to set
     */
    public void setField(String field)
    {
        this.field = field;
    }

    /**
     * Sets the label. 
     * @param label The label to set
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Sets the required.
     * @param required The required to set
     */
    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public static void setDefaultLabelPrefix(String prefix)
    {
        DEFAULTLABELPREFIX = prefix;
    }

    public static void setDefaultLabelPostfix(String postfix)
    {
        DEFAULTLABELPOSTFIX = postfix;
    }

    public static String getDefaultLabelPrefix()
    {
        return DEFAULTLABELPREFIX;
    }

    public static String getDefaultLabelPostfix()
    {
        return DEFAULTLABELPOSTFIX;
    }

    private void setRequired(String required)
    {
        setRequired(TRUE.equalsIgnoreCase(required));
    }


    private String getMethodField()
    {
        int dotIndex = getField().lastIndexOf('.');
        String mf = getField();
        if (dotIndex != -1)
        {
            mf = getField().substring(dotIndex+1);
        }
        if (log.isTraceEnabled())
        {
            log.trace("Clean method field : "+mf);
        }
        return mf.substring(0, 1).toUpperCase()
            + mf.substring(1);
    }
    
    /**
     * Returns the class type of the field
     */
    public Class getFieldClass()
    {
        return this.method.getReturnType();
    }

    /** 
     * Sets the constraints of this field
     * NOTE: Should be XML!!
     */
    private void setConstraints()
    {
        setLabel(
            Resources.getResource(
                resourceClass,
                prefix + DOT + field + DOT + LABEL));
        setRequired(
            Resources.getResource(
                resourceClass,
                prefix + DOT + field + DOT + REQUIRED));
    }
    
    /**
     * Returns the prefix.
     * @return String
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Sets the prefix.
     * NOTE: Should be read initialy from the GuiDefaults.xml..
     * @param prefix The prefix to set
     */
    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
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
    
    protected void setBeanField(BeanField beanField)
    {
        this.beanField = beanField;
    }
    
    protected BeanField getBeanField()
    {
        return this.beanField;
    }

}
