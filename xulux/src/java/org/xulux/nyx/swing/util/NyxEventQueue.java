/*
 $Id: NyxEventQueue.java,v 1.1 2003-08-28 23:28:13 mvdb Exp $

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
package org.xulux.nyx.swing.util;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The nyx eventqueue..
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxEventQueue.java,v 1.1 2003-08-28 23:28:13 mvdb Exp $
 */
public class NyxEventQueue extends EventQueue implements AWTEventListener{
    
    ArrayList queue;
    /**
     * 
     */
    public NyxEventQueue() {
        super();
        System.out.println("Eventqueue created..");
        //queue = new ArrayList();
    }
    

    /**
     * @see java.awt.EventQueue#postEvent(java.awt.AWTEvent)
     */
    public void postEvent(AWTEvent theEvent) {
        System.out.println("Event : "+theEvent);
        super.postEvent(theEvent);
    }


    /**
     * @see java.awt.event.AWTEventListener#eventDispatched(java.awt.AWTEvent)
     */
    public void eventDispatched(AWTEvent event) {
        if (!(event instanceof MouseEvent)) { 
            System.out.println("dispatch : "+event);
        }
        if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent)event;
            if (ke.getKeyCode() == 40) {
                System.out.println("Keycode 40 start debugging!");
            }
        } 
    }

    /**
     * @see java.awt.EventQueue#dispatchEvent(java.awt.AWTEvent)
     */
    protected void dispatchEvent(AWTEvent event) {
        if (!(event instanceof MouseEvent)) { 
            System.out.println("dispatch : "+event);
        }
        super.dispatchEvent(event);
    }

}
