/*
   $Id: SwingToolkit.java,v 1.4 2004-05-11 11:50:00 mvdb Exp $
   
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
package org.xulux.swing.util;

import java.awt.Toolkit;

import org.xulux.gui.XuluxToolkit;

/**
 * The swing toolkit...
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingToolkit.java,v 1.4 2004-05-11 11:50:00 mvdb Exp $
 */
public class SwingToolkit extends XuluxToolkit {
    /**
     * Holds the event queue for nyx..
     */
    private NyxEventQueue eventQueue;
    /**
     * Is the toolkit initialized
     */
    private static boolean initialized = false;

    /**
     *
     */
    public SwingToolkit() {
    }

    /**
     * @see org.xulux.nyx.gui.XuluxToolkit#beep()
     */
    public void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * @see org.xulux.nyx.gui.XuluxToolkit#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        this.eventQueue = new NyxEventQueue();
        initialized = true;
    }

    /**
     * @see org.xulux.nyx.gui.XuluxToolkit#destroy()
     */
    public void destroy() {
    }

}
