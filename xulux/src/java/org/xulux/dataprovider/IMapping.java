/*
   $Id: IMapping.java,v 1.2 2004-04-01 16:15:09 mvdb Exp $
   
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
 * @version $Id: IMapping.java,v 1.2 2004-04-01 16:15:09 mvdb Exp $
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
}
