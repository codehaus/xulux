/*
   $Id: ImmidiateTreeSelectionListener.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
   
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

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.xulux.core.PartRequest;
import org.xulux.gui.GuiUtils;
import org.xulux.gui.Widget;

/**
 * The ImmidiateTreeSelectionListeners fires of events when
 * a new tree selection is selected. The execute method in the rules
 * are being called.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImmidiateTreeSelectionListener.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
 */
public class ImmidiateTreeSelectionListener implements ListSelectionListener, TreeSelectionListener {

    /**
     * The widget
     */
    protected Widget widget;

    /**
     * @param widget the widget
     */
    public ImmidiateTreeSelectionListener(Widget widget) {
        this.widget = widget;
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent e) {
        if (e != null) {
            selectionChanged();
        }
    }

    /**
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */
    public void valueChanged(TreeSelectionEvent e) {
        if (e != null) {
            if (e.isAddedPath()) {
                selectionChanged();
            }
        }
    }

    /**
     * Refresh the value of all widgets that are pointing this widget.
     */
    public void selectionChanged() {
        if (widget != null) {
            GuiUtils.fireFieldExecuteRule(widget, widget, PartRequest.NO_ACTION);
        }
    }

}
