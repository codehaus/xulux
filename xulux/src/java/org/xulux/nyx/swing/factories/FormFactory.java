/*
 $Id: FormFactory.java,v 1.2.2.1 2003-04-29 16:52:45 mvdb Exp $

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

package org.xulux.nyx.swing.factories;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.examples.datamodel.DefaultBase;
import org.xulux.nyx.guidefaults.GuiDefaults;
import org.xulux.nyx.swing.BaseForm;
import org.xulux.nyx.swing.SimpleForm;
import org.xulux.nyx.utils.Resources;

/**
 * Returns a form based on the specified model
 * The form will be constructed based on the 
 * design guidelines
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: FormFactory.java,v 1.2.2.1 2003-04-29 16:52:45 mvdb Exp $
 */
public class FormFactory
{

    private static Log log = LogFactory.getLog(FormFactory.class);
    private static FormFactory instance = null;
    public static String DEFAULT_FORM_CONTROLS = "default.form.controls"; //$NON-NLS-1$
    public static String FIELDS = "fields"; //$NON-NLS-1$
    public static String DOT = "."; //$NON-NLS-1$

    /**
     * Constructor for FormFactory.
     * 
     */
    private FormFactory()
    {
    }

    public static synchronized BaseForm getForm(
        DefaultBase base,
        String formName)
    {
        return getForm(base, formName, GuiDefaults.class);
    }
    
    public static synchronized BaseForm getDefaultForm(Object bean)
    {
        System.out.println("Bean instanceof : "+bean.getClass());
        return getDefaultForm(bean, 0);
    }
    
    public static synchronized BaseForm getDefaultForm(Object bean, int modifiers)
    {
        System.out.println("Bean instanceof : "+bean.getClass());
        SimpleForm simple = new SimpleForm(bean, modifiers);
        return simple;
    }
    
    public static synchronized BaseForm getForm(
        DefaultBase base,
        String formName,
        Class clazz)
    {
        String fieldNames =
            Resources.getResource(clazz, formName + DOT + FIELDS);
        if (log.isTraceEnabled())
        {
            log.trace("fieldNames : "+fieldNames);
        }
        StringTokenizer stn = new StringTokenizer(fieldNames, ",");
        FieldCollection fc = new FieldCollection();
        fc.setBase(base);
        while (stn.hasMoreTokens())
        {
            String fieldName = stn.nextToken();
            GuiField field = new GuiField(formName, fieldName, clazz);
            if (log.isTraceEnabled())
            {
                log.trace("Adding field "+field.getField());
            }
            fc.addField(field);
        }
        // Should change this to instantiating the form specified 
        // in the config..
        BaseForm form = null;
        try
        {
            Class formClazz = Class.forName(Resources.getResource(GuiDefaults.class, "default.form.type"));
            form = (BaseForm) formClazz.newInstance();
            if (form instanceof SimpleForm)
            {
                ((SimpleForm)form).setFields(fc);
            }
                
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace(System.out);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace(System.out);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace(System.out);
        }
        return form;
    }

    /**
     * returns the singleton instance of the formFactory
     * Mainly used for internal factory use.
     * @return the instance of formFactory
     */
    public static FormFactory getInstance()
    {
        if (instance == null)
        {
            instance = new FormFactory();
        }
        return instance;
    }

}
