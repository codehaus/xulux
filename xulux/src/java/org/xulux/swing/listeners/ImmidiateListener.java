/*
   $Id: ImmidiateListener.java,v 1.5 2004-04-14 14:16:12 mvdb Exp $
   
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
package org.xulux.swing.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.xulux.core.XuluxContext;
import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.gui.Widget;
import org.xulux.rules.impl.WidgetRequestImpl;

/**
 * The immidiate listeners fires events based on typed keys.
 * For now only used for checkbox.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImmidiateListener.java,v 1.5 2004-04-14 14:16:12 mvdb Exp $
 */
public class ImmidiateListener extends KeyAdapter {
    /**
     * the widget
     */
    private Widget widget;
    /**
     * the part
     */
    private ApplicationPart part;

    /**
     * the constructor
     */
    public ImmidiateListener() {
    }

    /**
     * Constructor for ImmidiateListener.
     * @param widget the widget
     */
    public ImmidiateListener(Widget widget) {
        this.widget = widget;
        this.part = widget.getPart();
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        XuluxContext.fireFieldRequests(impl, XuluxContext.EXECUTE_REQUEST);
    }

    /**
     * Returns the widget.
     * @return Widget
     */
    public Widget getWidget() {
        return widget;
    }

    /**
     * Sets the widget.
     * @param widget The widget to set
     */
    public void setWidget(Widget widget) {
        this.widget = widget;
    }

}
