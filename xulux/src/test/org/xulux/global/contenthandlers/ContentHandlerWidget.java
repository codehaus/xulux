/*
   $Id: ContentHandlerWidget.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
   
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

import org.xulux.gui.IContentWidget;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContentHandlerWidget.java,v 1.3 2004-01-28 15:22:08 mvdb Exp $
 */
public class ContentHandlerWidget implements IContentWidget {

    private Object content;

    /**
     * 
     */
    public ContentHandlerWidget() {
        super();
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#setContent(java.lang.Object)
     */
    public void setContent(Object object) {
        this.content = object;
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#getContent()
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#contentChanged()
     */
    public void contentChanged() {

    }

}
