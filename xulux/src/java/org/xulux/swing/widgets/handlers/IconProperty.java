/*
   $Id: IconProperty.java,v 1.1 2004-03-23 16:16:21 mvdb Exp $
   
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

import javax.swing.Icon;
import javax.swing.JLabel;

import org.xulux.gui.IPropertyHandler;
import org.xulux.gui.Widget;
import org.xulux.swing.util.SwingUtils;

/**
 * Handles the setting of an icon.
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IconProperty.java,v 1.1 2004-03-23 16:16:21 mvdb Exp $
 */
public class IconProperty implements IPropertyHandler {

    /**
     * The Icon
     */
    private Icon icon;

    /**
     * @see org.xulux.gui.IPropertyHandler#init()
     */
    public void init() {

    }

    /**
     * @see org.xulux.gui.IPropertyHandler#handleProperty(org.xulux.gui.Widget, java.lang.String, java.util.List)
     */
    public boolean handleProperty(Widget widget, String property) {
        // clear the icon first
        destroy();
    	Object nativeComponent = widget.getNativeWidget();
    	if (widget.getNativeWidget() instanceof JLabel) {
    		JLabel label = (JLabel) widget.getNativeWidget();
            this.icon = SwingUtils.getIcon(widget.getProperty(property), widget);
    		label.setIcon(icon);
    		return true;
    	}
        return false;
    }

    /**
     * @see org.xulux.gui.IPropertyHandler#destroy()
     */
    public void destroy() {
        this.icon = null;
    }

    /**
     * @see org.xulux.gui.IPropertyHandler#getValue()
     */
    public Object getValue() {
        return icon;
    }

}
