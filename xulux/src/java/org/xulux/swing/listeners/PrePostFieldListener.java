/*
   Copyright 2002-2003 The Xulux Project

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.GuiUtils;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.swing.widgets.Button;
import org.xulux.swing.widgets.CheckBox;
import org.xulux.swing.widgets.MenuItem;
import org.xulux.swing.widgets.RadioButton;
import org.xulux.swing.widgets.ToggleButton;

/**
 * Maybe use some kind of cache to see what next event comes through??
 * Functionality like hasWaitingRequests() or something like that.
 * Also if a user closes the window, widget.destroy should be called
 * 
 * @todo Find a better way to handle the concel button.
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PrePostFieldListener.java,v 1.2 2004-01-28 12:24:03 mvdb Exp $
 */
public class PrePostFieldListener extends NyxListener implements FocusListener, ActionListener, ItemListener {

    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(PrePostFieldListener.class);

    /**
     * The constructor
     */
    public PrePostFieldListener() {
        super();
    }
    /**
     * Constructor for PrePostFieldListener.
     * @param widget the widget
     */
    public PrePostFieldListener(Widget widget) {
        super(widget);
    }

    /**
     * now call pre..
     * @see java.awt.event.FocusListener#focusGained(FocusEvent)
     */
    public void focusGained(FocusEvent e) {
        if (isProcessing()) {
            return;
        }
        if (e.getID() != FocusEvent.FOCUS_GAINED || e.isTemporary()) {
            return;
        }
        NyxEventQueue q = NyxEventQueue.getInstance();
        q.holdEvents(false);
        started();
    }

    /**
     * now call post..
     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
     */
    public void focusLost(FocusEvent e) {
        if (isProcessing()) {
            return;
        }
        if (e.getID() != FocusEvent.FOCUS_LOST || e.isTemporary()) {
            return;
        }
        NyxEventQueue q = NyxEventQueue.getInstance();
        q.holdEvents(true);
        q.holdAccepted(this);
    }

    /**
     * @todo Make required check for combo with the notselectedValue..
     *
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (isProcessing()) {
            return;
        }
        if (widget.isRefreshing()) {
            return;
        }
        NyxEventQueue q = NyxEventQueue.getInstance();
        if (widget instanceof Button || widget instanceof MenuItem) {
            boolean isCancel = GuiUtils.processCancel(widget);
            if (isCancel) {
                // drop all events and accepted in the event queue..
                q.clearAccepted();
            }
            // free event queue.
            q.holdEvents(false);
        }
        if (accepted(widget)) {
            completed();
        }
    }

    /**
     * Sets the correct value when a checkbox is
     * clicked. It will call the post after the
     * value is adjusted.
     *
     * @todo optimize this using native boolean ??
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent e) {
        // make sure we don't end up in a loop by checking
        // the fact if the widget is currently refreshing or not..
        //        if (isProcessing()) {
        //            return;
        //        }
        if (widget.isRefreshing()) {
            return;
        }
        boolean refresh = false;
        // reset the hold events to process previous events..
        if (widget instanceof CheckBox || widget instanceof RadioButton || widget instanceof ToggleButton) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                widget.setValue("true");
                refresh = true;

            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                widget.setValue("false");
                refresh = true;
            }
            if (log.isTraceEnabled()) {
                log.trace("Checkbox or RadioButton clicked on Widget : " + widget.getName() + " value: " + widget.getValue());
            }
        }
        if (refresh) {
            NyxEventQueue.getInstance().holdEvents(false);
            widget.getPart().refreshFields(widget);
            widget.getPart().updateDependandWidgets(widget);
        }
        completed();
    }
}
