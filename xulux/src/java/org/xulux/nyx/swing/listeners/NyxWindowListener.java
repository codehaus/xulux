/*
 $Id: NyxWindowListener.java,v 1.6 2003-12-17 00:59:55 mvdb Exp $

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
package org.xulux.nyx.swing.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.GuiUtils;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;

/**
 * A WindowListener to make sure we pass control
 * back to the main application when someone hits
 * the X button.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxWindowListener.java,v 1.6 2003-12-17 00:59:55 mvdb Exp $
 */
public class NyxWindowListener extends NyxListener implements WindowListener {
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(NyxWindowListener.class);

    /**
     * Specifies if the window should be destroyed when
     * the window is deactived
     */
    private boolean shouldDestroy = false;

    /**
     * Constructor for NyxWindowListener.
     */
    public NyxWindowListener() {
        super();
    }

    /**
     * Constructor for NyxWindowListener.
     * @param widget the widget
     */
    public NyxWindowListener(Widget widget) {
        super(widget);
    }

    /**
     * @see java.awt.event.WindowListener#windowActivated(WindowEvent)
     */
    public void windowActivated(WindowEvent e) {
        log.trace("Window activated : " + e);
    }

    /**
     * @see java.awt.event.WindowListener#windowClosed(WindowEvent)
     */
    public void windowClosed(WindowEvent e) {
        log.trace("Window closed : " + e);
    }

    /**
     * Specifies that the window should be destroyed, if followed
     * by a windowDeactivated event.
     *
     * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
     */
    public void windowClosing(WindowEvent e) {
        log.trace("Window closing : " + e);
        shouldDestroy = true;
    }

    /**
     * If the window was previously closing, complete and destroy
     * the widget
     *
     * @see java.awt.event.WindowListener#windowDeactivated(WindowEvent)
     */
    public void windowDeactivated(WindowEvent e) {
        if (!shouldDestroy) {
            return;
        }
        GuiUtils.processCancel(widget);
        completed();
        getWidget().destroy();
    }

    /**
     * @see java.awt.event.WindowListener#windowDeiconified(WindowEvent)
     */
    public void windowDeiconified(WindowEvent e) {
        log.trace("Window deIconified : " + e);
    }

    /**
     * @see java.awt.event.WindowListener#windowIconified(WindowEvent)
     */
    public void windowIconified(WindowEvent e) {
        log.trace("Window iconified: " + e);
    }

    /**
     * @see java.awt.event.WindowListener#windowOpened(WindowEvent)
     */
    public void windowOpened(WindowEvent e) {
        log.trace("Window opened : " + e);
    }

}
