/*
   $Id: IField.java,v 1.1 2004-03-16 14:35:14 mvdb Exp $
   
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

/**
 * The generic field interface..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IField.java,v 1.1 2004-03-16 14:35:14 mvdb Exp $
 */
public interface IField
{

    /**
     * @return the name of the field
     */
    String getName();

    /**
     * @return the alias of the field
     */
    String getAlias();

    /**
     * Sets the value of the object
     * @param object the object to set the value on
     * @param value the value to set
     * @return true on success, false on failure
     */
    boolean setValue(Object object, Object value);

    /**
     * @param object the object to get the value from
     * @return the value of the field
     */
    Object getValue(Object object);

    /**
     * @return if a field is readonly or not
     */
    boolean isReadOnly();

    /**
     * @return the returntype of the field
     */
    Class getReturnType();


}
