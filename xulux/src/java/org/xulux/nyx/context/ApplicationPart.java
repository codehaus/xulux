/*
 $Id: ApplicationPart.java,v 1.4 2002-11-02 13:38:50 mvdb Exp $

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
package org.xulux.nyx.context;

import java.util.ArrayList;

import org.xulux.nyx.global.BeanField;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.swing.factories.GuiField;

/**
 * An Application is a part of the application
 * which will contain a "block" of data/gui  that is in some 
 * way related with each other (eg a form, table, window)
 * 
 * It will maintain 2 beans for every bean : the original
 * and the copy on the screen. 
 * 
 * 
 * NOTE: Add refresh possibilities to the application parts,
 * so your are able to replace the original bean and determine if
 * the "bean refresh" changed the bean or not.
 * Esp. usefull when concurrent changes of beans occur by different
 * people (although this would be merely convenience, since other code
 * should handle these kind of situation..).
 *  
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPart.java,v 1.4 2002-11-02 13:38:50 mvdb Exp $
 */
public class ApplicationPart
{
    
    /**
     * The original bean
     */
    private Object bean;
    
    private BeanMapping mapping;
    
    /**
     * Constructor for GuiPart.
     */
    public ApplicationPart()
    {
    }
    
    /**
     * Used for single bean representation
     */
    public ApplicationPart(Object bean)
    {
        this();
        this.bean = bean;
        this.mapping = Dictionary.getInstance().getMapping(bean.getClass());
    }
        
    
    /** 
     * @return Are there any changes in the part
     */
    public boolean isDirty()
    {
        return isDirty();
    }
    
    /**
     * @return if the field specified has been changed..
     */
    public boolean isDirty(String field)
    {
        return false;
    }
    
    /**
     * Returns if the field specified is the one currently
     * being processed. This way you can quicly determine in
     * your code if the rule 
     */
    public boolean isCurrentField(String field)
    {
        return false;
    }
    
    /**
     * No primitive support yet.. sorry ;)
     * This is pretty ignoarant.. If a field does not exists 
     * in the apppart, it will do nothing
     * @param field
     * @param value
     */
    public void setBeanValue(String field, Object value)
    {
        BeanField bField = mapping.getField(field);
        if (bField.isReadOnly())
        {
            // we cannot change the value,
            // so let's not try it..
            return;
        }
        
        if (!bField.setValue(this.bean, value))
        {
            System.err.println("Could not set value");
        }
    }
    
    
    public void setGuiValue(String field, Object value)
    {
    }
    
    /** 
     * Returns the current value of the specified field
     * @param field
     */
    public Object getGuiValue(String field)
    {
        return null;
    }
    
    /**
     * @return the current bean value
     */
    public Object getBeanValue(String field)
    {
        return null;
    }
    
    /**
     * Specifies if this application is gui related.
     * For now all parts are.
     */
    public boolean isGui()
    {
        return true;
    }
    
    public BeanField getField(String name)
    {
        return null;
    }
    
    public Object getBean()
    {
        return this.bean;
    }
    
    /** 
     * Returns the name of the part
     */
    public String getName()
    {
        return null;
    }
    
    /** 
     * Adds lookup data to the specified field.
     * 
     * @param field - the field to connect the data to.
     * @param data - the arraylist containing the data.
     * @param type - what type of lookup component it will be, (see GuiDefaults)
     * @param whichFields - which fields to show in the lookup type.
     */
    public void addLookupData(String field, ArrayList data, 
                                int type, String whichFields)
    {
        
    }
    
}
