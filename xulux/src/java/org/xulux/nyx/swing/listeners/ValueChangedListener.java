package org.xulux.nyx.swing.listeners;

import javax.swing.JTable;

import org.xulux.nyx.gui.NyxEvent;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.events.NyxValueChangedEvent;
import org.xulux.nyx.swing.util.NyxEventQueue;
import org.xulux.nyx.swing.widgets.Table;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ValueChangedListener.java,v 1.2 2003-11-06 19:53:12 mvdb Exp $
 */
public class ValueChangedListener extends NyxListener {

    private boolean isProcessing = false;
    /**
     *
     */
    public ValueChangedListener() {
        super();
    }

    /**
     * @param widget
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
            Object value = ((NyxValueChangedEvent)event).getValue();
            JTable table = (JTable)((Table)widget).getJTable();
            if (table.isEditing()) {
//                System.out.println("setting value in changelistener: "+value.getClass());
                table.getModel().setValueAt(value, table.getEditingRow(), table.getEditingColumn());
//                System.out.println("After setting : "+table.getModel().getValueAt(table.getEditingRow(), table.getEditingColumn()));
            }
            //widget.refresh();
        }
        isProcessing = false;
    }

}
