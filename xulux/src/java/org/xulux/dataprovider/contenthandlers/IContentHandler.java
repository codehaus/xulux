/*
   $Id: IContentHandler.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
   
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

/**
 * The content handler interface. A contenthandler is used for widgets
 * that shows content from a eg a list, a tree, DOM, etc.
 * The passed in object will be processed to the type it returns when using getType()
 *
 * Eventually this will move to be the generic contenthandler for all widgets.
 * Contenthandlers are registered in a widget (eg guidefaults), so the developer
 * of a widget can specify which content is supported.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IContentHandler.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
 */
public interface IContentHandler {

    /**
     * Sets the content to the specified object.
     *
     * @param content the content to use on the handler
     */
    void setContent(Object content);

    /**
     *
     * @return the content that is previously set
     */
    Object getContent();

    /**
     *
     * @return the type of content this handler supports.
     */
    Class getType();

    /**
     * Notifies the contenthandler that a refresh needs to take place
     * since some content was changed WITHOUT calling setContent.
     *
     */
    void refresh();
    
    /**
     * Set the view of this contenthandler
     * @param view the view class of the contenthandler
     */
    void setView(Class view);
    
    /**
     * @return the view class
     */
    Class getViewClass();

}
