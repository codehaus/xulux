/*
   $Id: PopupListener.java,v 1.5 2004-04-14 14:16:12 mvdb Exp $
   
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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import org.xulux.core.XuluxContext;
import org.xulux.core.PartRequest;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.rules.impl.WidgetRequestImpl;

/**
 * A popuplistener. Shows the popup when the right mousebutton is clicked
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PopupListener.java,v 1.5 2004-04-14 14:16:12 mvdb Exp $
 */
public class PopupListener extends NyxListener implements MouseListener {

    /**
     *
     */
    public PopupListener() {
        super();
    }

    /**
     * @param widget the widget
     */
    public PopupListener(Widget widget) {
        super(widget);
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        // fire pre rules of menuitems..
        WidgetRequestImpl impl = new WidgetRequestImpl(getWidget().getParent(), PartRequest.ACTION_VALUE_CHANGED);
        //        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.POST_REQUEST);
        //        // preform all pre rules if postOnly is false
        //            ApplicationContext.fireFieldRequests(impl, ApplicationContext.PRE_REQUEST);
        ////        fi
        XuluxContext.fireFieldRequest(getWidget(), impl, XuluxContext.PRE_REQUEST);
        List list = getWidget().getChildWidgets();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Widget widget = (Widget) list.get(i);
                XuluxContext.fireFieldRequest(widget, impl, XuluxContext.PRE_REQUEST);
            }
        }
        if (e.isPopupTrigger()) {
            getWidget().setPosition(e.getX(), e.getY());
            getWidget().setVisible(true);
        }
    }
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {

    }

}
