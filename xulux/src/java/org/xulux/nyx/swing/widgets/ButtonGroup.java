/*
 $Id: ButtonGroup.java,v 1.2 2003-10-23 01:43:08 mvdb Exp $

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
package org.xulux.nyx.swing.widgets;

import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;

import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.IShowChildWidgets;
import org.xulux.nyx.gui.Widget;

/**
 * Combine a number of checkboxes, buttons or radiobuttons
 * into one group, which contain 1 field value.
 * This implements IShowChildWidgets, which says to the parent
 * that this widget cannot be shown on screen, but it's children do..
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ButtonGroup.java,v 1.2 2003-10-23 01:43:08 mvdb Exp $
 */
public class ButtonGroup extends ContainerWidget
implements IShowChildWidgets
{
    
    protected javax.swing.ButtonGroup group;
    
    /**
     * @param name
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
            this.group.add((AbstractButton)widget.getNativeWidget());
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
                AbstractButton button = (AbstractButton)w.getNativeWidget();
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
