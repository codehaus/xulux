/*
   $Id: ArrayContentHandler.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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
package org.xulux.dataprovider.contenthandlers;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * A content handler for arrays.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ArrayContentHandler.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public class ArrayContentHandler extends ContentHandlerAbstract {

    /**
     *
     */
    public ArrayContentHandler() {
        super();
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getContent()
     */
    public Object getContent() {
        if (content instanceof Collection) {
            return ((Collection) content).toArray();
        } else if (content != null && content.getClass().isArray()) {
            return content;
        }
        if (log.isWarnEnabled()) {
            String contentStr = null;
            if (content != null) {
                contentStr = content.getClass().getName();
            }
            log.warn("Content of type "
                + contentStr
                + " cannot be converted to type " + getType().getName()
                + " Returning original content");
        }
        return content;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getType()
     */
    public Class getType() {
        return Array.class;
    }

}
