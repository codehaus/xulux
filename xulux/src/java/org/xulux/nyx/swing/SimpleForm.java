/*
 $Id: SimpleForm.java,v 1.4.2.1 2003-04-29 16:52:45 mvdb Exp $

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

package org.xulux.nyx.swing;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JRootPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.swing.factories.FieldCollection;

/**
 * A simpleForm which contructs a for based on the fields
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SimpleForm.java,v 1.4.2.1 2003-04-29 16:52:45 mvdb Exp $
 */
public class SimpleForm 
extends BaseForm 
{
    private static Log log = LogFactory.getLog(SimpleForm.class);
    private FieldCollection fields;
    private int modifiers =0;

    public SimpleForm()
    {
    }
    
    public SimpleForm(Object bean, int modifiers)
    {
        BeanMapping mapping = Dictionary.getInstance().getMapping(bean.getClass());
        //System.out.println("mapping : "+mapping.getFields());
        this.modifiers = modifiers;
        setFields(new FieldCollection(mapping, bean));
    }
    /**
     * Constructor for SimpleForm.
     * @param component
     */
    public SimpleForm(JComponent component)
    {
        super(component);
    }

    public SimpleForm(FieldCollection fields)
    {
        setFields(fields);
    }
    
    public SimpleForm(JComponent component, FieldCollection fields)
    {
        setFields(fields);
    }
    
    /** 
     * Sets the fields in the form
     * With this it will remove all currently know fields 
     * from the form and rebuild the form.
     * For now only 1 field area is supported
     * @param fields - the fields to use in the form
     */
    public void setFields(FieldCollection fields)
    {
        this.fields = fields;
        FormFieldArea fieldArea = null;
        Component[] components = findComponent(FormFieldArea.class, this.getComponent());
        if (components != null)
        {
            for (int i=0; i < components.length; i++)
            {
                ((FormFieldArea)components[i]).removeAllFields();
            }
        }
        if (fieldArea == null)
        {
            fieldArea = new FormFieldArea(fields, (modifiers == BaseForm.USEINTERNALFORM));
        }
        getComponent().add(fieldArea);
    }
    
    /**
     * Finds a certain of type of component
     * @param type - the type of component to find
     * @return Component[] array or null when not found.
     */
    private Component[] findComponent(Class type, Component component)
    {
        // initialize with on ecomponent
        Component[] cs = null;
        if (getComponent() == null)
        {
            return null;
        }
        Component[] components = getComponent().getComponents();
        log.trace("components length : "+components.length);
        for (int i=0; i < components.length; i++)
        {
            if (components[i] instanceof FormFieldArea)
            {
                if (cs == null) 
                {
                    cs = new Component[] { (FormFieldArea)components[i]};
                }
                continue;
            }
            else if (components[i] instanceof JRootPane)
            {
                // every component has a rootpane, so we will end
                // up in an infinite loop
                continue;
            }
            else 
            {
                log.trace("component is instanceof "+components[i]);
                
                Component[] csToMerge = findComponent(type, components[i]);
                if (cs == null) 
                {
                    // just replace cs, since cs is still null
                    cs = csToMerge;
                    continue;
                }
                if (csToMerge != null)
                {
                    Component[] csTmp = cs;
                    int length = csTmp.length+csToMerge.length;
                    cs = new Component[length];
                    int dstPosition = 0;
                    System.arraycopy(csTmp, 0, cs, dstPosition, csTmp.length);
                    dstPosition+=csTmp.length;
                    System.arraycopy(csToMerge,0, cs, dstPosition, csToMerge.length);
                }
            }
        }
        return cs;
    }
    
}
