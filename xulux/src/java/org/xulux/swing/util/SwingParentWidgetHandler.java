/*
 $Id: SwingParentWidgetHandler.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.swing.util;

import java.awt.Component;
import java.awt.Container;

import org.xulux.gui.IParentWidgetHandler;

/**
 * Destroys/Initializes all components created by nyx.
 * This is only used in scenarios where nyx is used
 * on top of an already existing application
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingParentWidgetHandler.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $
 */
public class SwingParentWidgetHandler implements IParentWidgetHandler {

    /**
     *
     */
    public SwingParentWidgetHandler() {
    }

    /**
     * @see org.xulux.nyx.gui.IParentWidgetHandler#initialize(java.lang.Object)
     */
    public void initialize(Object parent) {

    }

    /**
     * @see org.xulux.nyx.gui.IParentWidgetHandler#destroy(java.lang.Object)
     */
    public void destroy(Object parent) {
        if (parent == null) {
            return;
        }
        if (parent instanceof Component) {
            Container container = ((Component) parent).getParent();
            if (container != null) {
                container.removeAll();
                container.remove((Component) parent);
            }
        }
    }

}
