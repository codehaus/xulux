/*
   $Id: DoubleConverter.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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
package org.xulux.dataprovider.converters;


/**
 * Converts a double to a representable string
 * and converts a string to a double.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DoubleConverter.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public class DoubleConverter implements IConverter {

    /**
     *
     */
    public DoubleConverter() {
        super();
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getBeanValue(java.lang.Object)
     */
    public Object getBeanValue(Object object) {
        if (object instanceof String) {
            try {
                return new Double((String) object);
            } catch (NumberFormatException nfe) {
                return null;
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getGuiValue(java.lang.Object)
     */
    public Object getGuiValue(Object object) {
        if (object instanceof Double) {
            return ((Double) object).toString();
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getType()
     */
    public Class getType() {
        return Double.class;
    }

}
