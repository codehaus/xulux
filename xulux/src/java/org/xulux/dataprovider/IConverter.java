/*
   $Id: IConverter.java,v 1.1 2004-03-16 14:35:14 mvdb Exp $
   
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
 * A converter for data.
 * You can register a converter in the dictionary
 * for certain types.
 * @todo Default converters.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IConverter.java,v 1.1 2004-03-16 14:35:14 mvdb Exp $
 */
public interface IConverter {

    /**
     * Converts the gui value to a value that can be used
     * in a bean
     *
     * @param object - the String (or other object) to convert
     * @return The object needed.
     */
    Object getBeanValue(Object object);

    /**
     * Converts the bean value to a value that can be used
     * in a gui
     *
     * @param object the object to get the gui value from
     * @return the object needed (a String)
     */
    Object getGuiValue(Object object);


    /**
     * @return the class that this converter handles
     */
    Class getType();


}
