/*
   $Id: DefaultConverter.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
   
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
package org.xulux.global.converters;

import org.xulux.global.IConverter;

/**
 * Just returns the object passed in.
 * Normally just used for Strings..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultConverter.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
 */
public class DefaultConverter implements IConverter {

    /**
     *
     */
    public DefaultConverter() {
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getBeanValue(java.lang.Object)
     */
    public Object getBeanValue(Object object) {
        return object;
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getGuiValue(java.lang.Object)
     */
    public Object getGuiValue(Object object) {
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.global.IConverter#getType()
     */
    public Class getType() {
        return String.class;
    }

}
