/*
 $Id: PrePostFieldListener.java,v 1.9.2.1 2003-04-29 16:52:45 mvdb Exp $

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.context.impl.WidgetRequestImpl;
import org.xulux.nyx.gui.Entry;
import org.xulux.nyx.gui.Widget;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PrePostFieldListener.java,v 1.9.2.1 2003-04-29 16:52:45 mvdb Exp $
 */
public class PrePostFieldListener 
implements FocusListener, ActionListener
{
    
    Widget widget;
    
    private static boolean processing = false;
    
    
    public PrePostFieldListener()
    {
    }
    
    /**
     * Constructor for PrePostFieldListener.
     */
    public PrePostFieldListener(Widget widget)
    {
        this.widget = widget;
    }

    /**
     * now call pre.. 
     * @see java.awt.event.FocusListener#focusGained(FocusEvent)
     */
    public void focusGained(FocusEvent e)
    {
        if (processing)
        {
            return;
        }
        if (e.getID() != FocusEvent.FOCUS_GAINED || e.isTemporary())
        {
            return;
        }
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.PRE_REQUEST);
        processing = false;
    }

    /**
     * now call post..
     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
     */
    public void focusLost(FocusEvent e)
    {
        if (processing)
        {
            return;
        }
        if (e.getID() != FocusEvent.FOCUS_LOST || e.isTemporary())
        {
            return;
        }
        System.out.println("Widget clazz : "+widget.getClass().getName());
        if (widget instanceof Entry)
        {
            System.out.println("Widget value : "+widget.getValue());
        }
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.POST_REQUEST);
        // preform all pre rules.
        ApplicationContext.fireFieldRequests(impl, ApplicationContext.PRE_REQUEST);
        processing = false;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        if (processing)
        {
            return;
        }
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.POST_REQUEST);
        ApplicationContext.fireFieldRequests(impl, ApplicationContext.PRE_REQUEST);
        processing = false;
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

    /**
     * Returns the processing.
     * @return boolean
     */
    public static boolean isProcessing()
    {
        return processing;
    }

}
