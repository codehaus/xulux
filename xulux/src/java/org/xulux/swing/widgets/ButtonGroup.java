/*
   $Id: ButtonGroup.java,v 1.3 2004-01-28 15:09:23 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.IShowChildWidgets;
import org.xulux.gui.Widget;

/**
 * Combine a number of checkboxes, buttons or radiobuttons
 * into one group, which contain 1 field value.
 * This implements IShowChildWidgets, which says to the parent
 * that this widget cannot be shown on screen, but it's children do..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ButtonGroup.java,v 1.3 2004-01-28 15:09:23 mvdb Exp $
 */
public class ButtonGroup extends ContainerWidget implements IShowChildWidgets {

    /**
     * The native buttongroup
     */
    private javax.swing.ButtonGroup group;

    /**
     * @param name the name of the buttongroup
     */
    public ButtonGroup(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {
        initialize();
        if (widget.getNativeWidget() instanceof AbstractButton) {
            this.group.add((AbstractButton) widget.getNativeWidget());
            widget.setParent(this);
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return this.group;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        this.group = new javax.swing.ButtonGroup();
        initialized = true;
        initializeChildren();
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
            return;
        }
        isRefreshing = true;
        setEnabledAllChildren();
        isRefreshing = false;
    }

    /**
     * Enables or disables all children of this group
     */
    protected void setEnabledAllChildren() {
        List list = getChildWidgets();
        if (list == null || list.size() == 0) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Widget w = (Widget) it.next();
            if (w instanceof RadioButton) {
                w.setEnable(isEnabled());
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (getChildWidgets() == null) {
            return null;
        }
        Iterator it = getChildWidgets().iterator();
        while (it.hasNext()) {
            Widget w = (Widget) it.next();
            if (w.getNativeWidget() instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) w.getNativeWidget();
                if (button.isSelected()) {
                    w.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {

    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return true;
    }

}
