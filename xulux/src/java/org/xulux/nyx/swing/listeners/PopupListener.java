/*
 $Id: PopupListener.java,v 1.3 2003-11-06 19:53:12 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project.  For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.swing.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.rules.impl.WidgetRequestImpl;

/**
 * A popuplistener. Shows the popup when the right mousebutton is clicked
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PopupListener.java,v 1.3 2003-11-06 19:53:12 mvdb Exp $
 */
public class PopupListener extends NyxListener
implements MouseListener {

    /**
     *
     */
    public PopupListener() {
        super();
    }

    public PopupListener(Widget widget) {
        super(widget);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        // fire pre rules of menuitems..
        WidgetRequestImpl impl = new WidgetRequestImpl(getWidget().getParent(), PartRequest.ACTION_VALUE_CHANGED);
//        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.POST_REQUEST);
//        // preform all pre rules if postOnly is false
//            ApplicationContext.fireFieldRequests(impl, ApplicationContext.PRE_REQUEST);
////        fi
        ApplicationContext.fireFieldRequest(getWidget(), impl, ApplicationContext.PRE_REQUEST);
        List list = getWidget().getChildWidgets();
        if (list != null) {
            for (int i=0; i < list.size(); i++) {
                Widget widget = (Widget)list.get(i);
                ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.PRE_REQUEST);
            }
        }
        if (e.isPopupTrigger()) {
            getWidget().setPosition(e.getX(),e.getY());
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
