package org.xulux.nyx.swing.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xulux.nyx.examples.datamodel.DefaultBase;

/**
 * A placeholder object for the fields in a form
 * 
 * @author Martin van den Bemt
 * @version $Id: FieldCollection.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
 */
public class FieldCollection
{
    private ArrayList fields;
    private DefaultBase base;
    
    
    public FieldCollection()
    {
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
    public DefaultBase getBase()
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
    public void setBase(DefaultBase base)
    {
        this.base = base;
    }

}
