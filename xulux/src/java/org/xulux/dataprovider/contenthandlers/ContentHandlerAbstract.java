/*
   $Id: ContentHandlerAbstract.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An absract for the content handler.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContentHandlerAbstract.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public abstract class ContentHandlerAbstract implements IContentHandler {

    /**
     * The view class to use for this instance
     */
    protected Class view;

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
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#setContent(java.lang.Object)
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getContent()
     */
    public abstract Object getContent();

    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getType()
     */
    public abstract Class getType();



    /**
     * Please override this if your contenthandler can have
     * content changed without a call to setConent
     *
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#refresh()
     */
    public void refresh() {

    }
    
    public void setView(Class view) {
        this.view = view;
    }
    
    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getViewClass()
     */
    public Class getViewClass() {
        return this.view;
    }
        

}
