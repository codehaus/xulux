/*
   $Id: SimpleDOMView.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
   
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

import org.dom4j.Attribute;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Text;

/**
 * A simple representation of a dom.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SimpleDOMView.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
 */
public class SimpleDOMView extends ContentView {

    /**
     * @param data
     */
    public SimpleDOMView(Object data) {
        super(data);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (data instanceof Element) {
            return ((Element) data).getName();
        } else if (data instanceof Document) {
            String name = ((Document) data).getName();
            if (name == null || "null".equals(name)) {
                return "Unkown document type";
            }
        } else if (data instanceof Comment) {
            return "Comment: " + ((Comment) data).getText();
            //return ((Comment)object).getName();
        } else if (data instanceof Text) {
            return "Text: " + ((Text) data).getText();
        } else if (data instanceof Attribute) {
            Attribute attribute = (Attribute) data;
            return attribute.getName() + "=" + attribute.getValue();
        }
        return data.toString();
    }

}
