/*
 $Id: FieldCollection.java,v 1.2.2.1 2003-04-29 16:52:45 mvdb Exp $

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

import java.util.ArrayList;
import java.util.Iterator;

import org.xulux.nyx.examples.datamodel.DefaultBase;
import org.xulux.nyx.global.BeanField;
import org.xulux.nyx.global.BeanMapping;

/**
 * A placeholder object for the fields in a form
 * 
 * @author <a href="mailt:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: FieldCollection.java,v 1.2.2.1 2003-04-29 16:52:45 mvdb Exp $
 */
public class FieldCollection
{
    private ArrayList fields;
    private Object base;
    
    
    public FieldCollection()
    {
    }
    
    public FieldCollection(BeanMapping mapping, Object base)
    {
        setBase(base);
        ArrayList values = mapping.getFields();
        for (Iterator it = values.iterator(); it.hasNext();)
        {
            
            GuiField field = new GuiField((BeanField)it.next());
            field.setPrefix(mapping.getName());
            addField(field);
        }
    }
    /**
     * Constructor for Fields.
     */
    public FieldCollection(ArrayList fields)
    {
        setFields(fields);
    }

    /**
     * Constructor for Fields.
     */
    public FieldCollection(ArrayList fields, DefaultBase base)
    {
        setFields(fields);
        setBase(base);
    }
    
    public FieldCollection(FieldCollection fieldCollection)
    {
        this.fields = fieldCollection.getFields();
    }
    
    public void addField(GuiField field)
    {
        if (fields == null)
        {
            fields = new ArrayList();
        }
        fields.add(field);
    }
    
    public void addFields(ArrayList fields)
    {
        this.fields.addAll(fields);
    }
    
    public void addFields(FieldCollection fieldCollection)
    {
        this.fields.addAll(fieldCollection.getFields());
    }

    /**
     * Sets the fields. It will overwrite the already known
     * fields
     * @param fields The fields to set
     */
    public void setFields(ArrayList fields)
    {
        this.fields = fields;
    }
    
    /**
     * Returns the base.
     * @return DefaultBase
     */
    public Object getBase()
    {
        return base;
    }

    /**
     * Returns the fields. 
     * Changing the arrayList returned, means you 
     * change the internal arraylist, so don't forget to 
     * clone first when you don't want to change the collection
     * 
     * @return ArrayList
     */
    public ArrayList getFields()
    {
        return fields;
    }

    /**
     * Sets the base.
     * @param base The base to set
     */
    public void setBase(Object base)
    {
        this.base = base;
    }

}
