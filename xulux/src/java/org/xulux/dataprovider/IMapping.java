/*
   $Id: IMapping.java,v 1.3 2004-04-22 12:59:02 mvdb Exp $
   
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

import java.util.List;

/**
 * The Mapping interface.
 * For now empty.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IMapping.java,v 1.3 2004-04-22 12:59:02 mvdb Exp $
 */
public interface IMapping {

    /**
     * Adds a field to the mapping
     * @param field the field to add
     */
    void addField(IField field);

    /**
     * 
     * @return a collection of fields in the mapping
     */
    List getFields();

    /**
     * Gets the field specified.
     * This will not be passed a string as it was previously, but an object,
     * so implementors can decide themselves how a field is organized.
     *
     * @param object the field to retrieve
     * @return the field or null of not found
     */
    IField getField(Object object);
    
    /**
     * @return the name of the mapping
     */
    String getName();

    /**
     * A convenience method, so you can set values on the highest possible level
     *
     * @param field the field name to use
     * @param object the object to use. If null the provider will try to create the object
     * @param value the value to set in the object
     * @return the object in the changed form, or if object was null, the newly created object.
     *         if an error occured of somekind, it should be logged and the value null should be
     *         returned.
     */
    Object setValue(String field, Object object, Object value);
  
    /**
     * A convenience method, so you can get values on the highest possible level
     *
     * @param field the field name to use
     * @param object the object to use. If null the provider will try to create the object
     * @return the object, or nul when an error occurred or the value is null.
     */
    Object getValue(String field, Object object);
    
}
