/*
 $Id: INativeWidgetHandler.java,v 1.4 2003-09-23 12:42:28 mvdb Exp $

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
package org.xulux.nyx.gui;

/**
 * Interface to handle native widgets.
 * You should register the implementation
 * via eg the guidefaults xml file.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: INativeWidgetHandler.java,v 1.4 2003-09-23 12:42:28 mvdb Exp $
 */
public interface INativeWidgetHandler {
    
    /**
     * Returns the widget passed in (as convenience) 
     * and adds the native widget to the parent.
     * This is normally used for automated processing
     * of native widgets.
     * 
     * @param clazz - the className of the native widget
     * @param parent - the nyx parent widget
     * @return the nyx parent widget.
     */
    public Widget getWidget(String clazz, Widget parent);
    
    /**
     * Returns the widget passed in (as convenience) 
     * and adds the native widget to the parent.
     * Use this for hand programmed addition of native widgets.
     * 
     * @param object - the native widget
     * @param parent - the nyx parent widget
     * @return - the nyx parent widget.
     */
    public Widget getWidget(Object object, Widget parent);
    
    /**
     * Set the location on the native widget
     * 
     * @param x
     * @param y
     */
    public void setLocationOnWidget(Widget parent, int x, int y);
    
    /**
     * Adds a widget to the parent, which is a native. (getParentWidget())
     * @param widget
     * @param nativeWidget
     */
    public void addWidgetToParent(Widget widget, Object parentWidget);

}
