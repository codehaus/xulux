/*
   $Id: IContentWidget.java,v 1.2 2004-01-28 15:00:23 mvdb Exp $
   
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
package org.xulux.gui;

/**
 * Interface that widgets must implement if they use content.
 * Content is a collection of objects that needs to be shown
 * in eg a list, table or combo.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IContentWidget.java,v 1.2 2004-01-28 15:00:23 mvdb Exp $
 */
public interface IContentWidget {

    /**
     * Set the content for the widget
     * @param object the object containing the content
     */
    void setContent(Object object);

    /**
     *
     * @return the current content of the widget
     */
    Object getContent();

    /**
     * This method needs to be called when the content has changed
     * without calling setContent.
     */
    void contentChanged();

}
