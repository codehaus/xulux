/*
   $Id: LocalField.java,v 1.2 2004-04-22 12:59:03 mvdb Exp $
   
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
package org.xulux.dataprovider.local;

import org.xulux.dataprovider.IField;

/**
 * A localfield.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LocalField.java,v 1.2 2004-04-22 12:59:03 mvdb Exp $
 */
public class LocalField implements IField
{

    /**
     * The name of the local field
     */
    private String name;
    /**
     * the field
     */
    private Object field;
    /**
     * the value of the field
     */
    private Object value;

    /**
     * Constructor for LocalField.
     * @param name - the name of the localfield
     */
    public LocalField(String name) {
        setName(name);
    }

    /**
     * Returns the field.
     * @return Object
     */
    public Object getField()
    {
        return field;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the field.
     * @param field The field to set
     */
    public void setField(Object field)
    {
        this.field = field;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * The alias is alaways the name with localfields.
     *
     * @see org.xulux.nyx.global.IField#getAlias()
     */
    public String getAlias()
    {
        return getName();
    }

    /**
     * We ignore the passed in value (could be null)
     *
     * @see org.xulux.nyx.global.IField#getValue(Object)
     */
    public Object getValue(Object bean)
    {
        return this.value;
    }

    /**
     * @see org.xulux.nyx.global.IField#setValue(Object, Object)
     */
    public Object setValue(Object bean, Object value)
    {
        this.value = value;
        return this.value;
    }

    /**
     * @see org.xulux.nyx.global.IField#isReadOnly()
     */
    public boolean isReadOnly()
    {
        return false;
    }

    /**
     * @see org.xulux.nyx.global.IField#getReturnType()
     */
    public Class getType() {
        return null;
    }

}
