/*
 $Id: ImmidiateListener.java,v 1.5.2.1 2003-04-29 16:52:45 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.
 
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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.context.impl.WidgetRequestImpl;
import org.xulux.nyx.gui.Widget;

/**
 * The immidiate listeners fires events based on typed keys.
 * For now unsupported
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImmidiateListener.java,v 1.5.2.1 2003-04-29 16:52:45 mvdb Exp $
 */
public class ImmidiateListener extends KeyAdapter
{
    Widget widget;
    ApplicationPart part;

    public ImmidiateListener()
    {
    }
    /**
     * Constructor for ImmidiateListener.
     */
    public ImmidiateListener(Widget widget)
    {
        this.widget = widget;
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(KeyEvent)
     */
    public void keyTyped(KeyEvent e)
    {
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequests(impl, ApplicationContext.EXECUTE_REQUEST);
    }

    /**
     * Returns the widget.
     * @return Widget
     */
    public Widget getWidget()
    {
        return widget;
    }

    /**
     * Sets the widget.
     * @param widget The widget to set
     */
    public void setWidget(Widget widget)
    {
        this.widget = widget;
    }

}
