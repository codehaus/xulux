/*
   $Id: ContentView.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
   
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

import java.util.ArrayList;
import java.util.List;

import org.xulux.utils.ClassLoaderUtils;

/**
 * The contentview is a view modifier for contenthandler.
 * Normally eg the Objec.toString() is sufficient to show content to the 
 * user, but sometimes more magic is needed to represent the content
 * in a correct way to the user. Eg in case of a DOM ContentHandler, a text
 * should be handled different than eg an attribute.
 * This mechanisme also allows for different representation of the same data.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContentView.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
 */
public abstract class ContentView {

    /**
     * Container for the data
     */
    protected Object data;

    /**
     * This constructor accepts the object that makes up the data
     * that needs to be rendered for the user.
     *
     * @param data the data that needs to be rendered.
     */
    public ContentView(Object data) {
        this.data = data;
    }

    /**
     * @return the data
     */
    public Object getSource() {
        return this.data;
    }

    /**
     * @return the string that will be displayed to the user
     */
    public abstract String toString();

    public static ContentView createView(Class view, Object data) {
        List list = new ArrayList(1);
        list.add(data);
        if (view == null) {
            return (ContentView) ClassLoaderUtils.getObjectFromClass(ToStringView.class, list);
        } else {
            return (ContentView) ClassLoaderUtils.getObjectFromClass(view, list);
        }
    }

}
