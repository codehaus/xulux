/*
   $Id: NewSelectionListener.java,v 1.3 2004-01-28 15:09:24 mvdb Exp $
   
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

import org.xulux.gui.Widget;

/**
 * The selection listener refreshes all widgets when a new entry has been
 * selected. It only refreshes when a widget actually needs data from
 * the calling widget.
 *
 * eg have a pointer in the field like ?Table:Person.name
 *
 * This works only within parts.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NewSelectionListener.java,v 1.3 2004-01-28 15:09:24 mvdb Exp $
 */
public class NewSelectionListener implements ListSelectionListener, TreeSelectionListener {

    /**
     * the widget
     */
    protected Widget widget;

    /**
     * @param widget the widget
     */
    public NewSelectionListener(Widget widget) {
        this.widget = widget;
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent e) {
        valueChanged();
    }

    /**
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */
    public void valueChanged(TreeSelectionEvent e) {
        valueChanged();
    }

    /**
     * Refresh the value of all widgets that are pointing this widget.
     */
    public void valueChanged() {
        if (widget != null) {
            widget.getPart().refreshWidgets(widget);
        }
    }

}
