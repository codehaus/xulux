/*
   $Id: ContentHandlerAbstract.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
   
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
package org.xulux.global.contenthandlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.global.IContentHandler;

/**
 * An absract for the content handler.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContentHandlerAbstract.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
 */
public abstract class ContentHandlerAbstract implements IContentHandler {

    /**
     * Contains the conent
     */
    protected Object content;

    /**
     * the shared log instance
     */
    protected static Log log = LogFactory.getLog(ContentHandlerAbstract.class);

    /**
     *
     */
    public ContentHandlerAbstract() {
        super();
    }

    /**
     * @see org.xulux.nyx.global.IContentHandler#setContent(java.lang.Object)
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * @see org.xulux.nyx.global.IContentHandler#getContent()
     */
    public abstract Object getContent();

    /**
     * @see org.xulux.nyx.global.IContentHandler#getType()
     */
    public abstract Class getType();



    /**
     * Please override this if your contenthandler can have
     * content changed without a call to setConent
     *
     * @see org.xulux.nyx.global.IContentHandler#refresh()
     */
    public void refresh() {

    }

}
