/*
   $Id: NyxWindowListener.java,v 1.4 2004-07-07 17:43:42 mvdb Exp $
   
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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.GuiUtils;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;

/**
 * A WindowListener to make sure we pass control
 * back to the main application when someone hits
 * the X button.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxWindowListener.java,v 1.4 2004-07-07 17:43:42 mvdb Exp $
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
        System.err.println("window listener constructor called");
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
        System.err.println("Window closed : " + e);
    }

    /**
     * Specifies that the window should be destroyed, if followed
     * by a windowDeactivated event.
     *
     * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
     */
    public void windowClosing(WindowEvent e) {
        System.err.println("window closing : " + e);
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
        this.widget = null;
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
