/*
 $Id: ValueChangedListener.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $

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
package org.xulux.swing.listeners;

import javax.swing.JTable;

import org.xulux.gui.NyxEvent;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.gui.events.NyxValueChangedEvent;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.swing.widgets.Table;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ValueChangedListener.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $
 */
public class ValueChangedListener extends NyxListener {

    /**
     * Is the changelistener processing ?
     */
    private boolean isProcessing = false;
    /**
     *
     */
    public ValueChangedListener() {
        super();
    }

    /**
     * @param widget the widget
     */
    public ValueChangedListener(Widget widget) {
        super(widget);
    }

    /**
     * @see org.xulux.nyx.gui.NyxListener#processEvent(org.xulux.nyx.gui.NyxEvent)
     */
    public void processEvent(NyxEvent event) {
        if (isProcessing) {
            isProcessing = false;
            return;
        }
        isProcessing = true;
        if (event instanceof NyxValueChangedEvent) {
            // free the event queue..
            NyxEventQueue.getInstance().holdEvents(false);
            Object value = ((NyxValueChangedEvent) event).getValue();
            JTable table = (JTable) ((Table) widget).getJTable();
            if (table.isEditing()) {
                //                System.out.println("setting value in changelistener: "+value.getClass());
                table.getModel().setValueAt(value, table.getEditingRow(), table.getEditingColumn());
            }
            //widget.refresh();
        }
        isProcessing = false;
    }

}
