/*
 $Id: NyxListener.java,v 1.1 2003-06-17 12:53:29 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.gui;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.context.impl.WidgetRequestImpl;

/**
 * An abstract to which all listeners must obey.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxListener.java,v 1.1 2003-06-17 12:53:29 mvdb Exp $
 */
public abstract class NyxListener
{
    protected Widget widget;
    private static boolean processing = false;
    
    public NyxListener()
    {
    }
    /**
     * Constructor
     * @param widget
     */
    public NyxListener(Widget widget)
    {
        this.widget = widget;
    }

    /**
     * Should be called when a field is completed
     */
    public void completed()
    {
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.POST_REQUEST);
        // preform all pre rules.
        ApplicationContext.fireFieldRequests(impl, ApplicationContext.PRE_REQUEST);
        processing = false;
    }
    
    /**
     * Should be called when a field is entered
     */
    public void started()
    {
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.PRE_REQUEST);
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
