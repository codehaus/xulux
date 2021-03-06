/*
   $Id: ContenViewProperty.java,v 1.1 2004-03-23 16:16:21 mvdb Exp $
   
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
package org.xulux.swing.widgets.handlers;

import org.xulux.gui.IPropertyHandler;
import org.xulux.gui.Widget;

/**
 * Handles contentview properties.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContenViewProperty.java,v 1.1 2004-03-23 16:16:21 mvdb Exp $
 */
public class ContenViewProperty implements IPropertyHandler {

    /**
     * 
     */
    public ContenViewProperty() {
        super();
    }

    /**
     * @see org.xulux.gui.IPropertyHandler#init()
     */
    public void init() {

    }

    /**
     * @see org.xulux.gui.IPropertyHandler#handleProperty(org.xulux.gui.Widget, java.lang.String)
     */
    public boolean handleProperty(Widget widget, String property) {
        String contentView = widget.getProperty("contentview");
        if (contentView != null) {
            // get the contenthandler for the tree.
            
            return true;
        }
        return false;
    }

    /**
     * @see org.xulux.gui.IPropertyHandler#destroy()
     */
    public void destroy() {

    }

    /**
     * @see org.xulux.gui.IPropertyHandler#getValue()
     */
    public Object getValue() {
        return null;
    }

}
