/*
   $Id: ValueChangedListener.java,v 1.4 2004-01-28 15:09:24 mvdb Exp $
   
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

import javax.swing.JTable;

import org.xulux.gui.XuluxEvent;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.gui.events.NyxValueChangedEvent;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.swing.widgets.Table;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ValueChangedListener.java,v 1.4 2004-01-28 15:09:24 mvdb Exp $
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
     * @see org.xulux.nyx.gui.NyxListener#processEvent(org.xulux.nyx.gui.XuluxEvent)
     */
    public void processEvent(XuluxEvent event) {
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
