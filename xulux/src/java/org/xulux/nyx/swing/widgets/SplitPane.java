/*
 $Id: SplitPane.java,v 1.1 2003-12-16 02:27:50 mvdb Exp $

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
package org.xulux.nyx.swing.widgets;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.Widget;

/**
 * The splitpane
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SplitPane.java,v 1.1 2003-12-16 02:27:50 mvdb Exp $
 */
public class SplitPane extends ContainerWidget {

    /**
     * the splitpane
     */
    private JSplitPane pane;

    /**
     * @param name the name of the widget
     */
    public SplitPane(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {

    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return this.pane;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        initializeChildren();
        //pane.setOneTouchExpandable(true);
        pane.setContinuousLayout(true);
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
            return;
        }
        if (!initialized) {
            initialize();
        }
        isRefreshing = true;
        String orientation = getProperty("orientation");
        if (orientation != null) {
            if ("horizontal".equalsIgnoreCase(orientation)) {
                pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            } else if ("vertical".equalsIgnoreCase(orientation)) {
                pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            }
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
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
        return false;
    }

    /**
     * Specifies if the bottom component was processed
     */
    private boolean bottomProcessed;
    /**
     * Specified if the top component was processed
     */
    private boolean topProcessed;
    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {
        String position = widget.getProperty("pane");
        JComponent comp = (JComponent) widget.getNativeWidget();
        System.out.println("min size : " + comp.getMinimumSize());
        comp.setMinimumSize(new Dimension(10, 10));
        if (position == null) {
            if (!topProcessed) {
                topProcessed = true;
                pane.setTopComponent((Component) widget.getNativeWidget());
            } else if (!bottomProcessed) {
                bottomProcessed = true;
                pane.setBottomComponent((Component) widget.getNativeWidget());
            } else {
                System.out.println("Ignoring widget " + widget
                     + " since we already have a bottom and top component");
            }
        } else if ("bottom".equalsIgnoreCase(position)) {
            pane.setBottomComponent((Component) widget.getNativeWidget());
        } else if ("top".equalsIgnoreCase(position)) {
            pane.setTopComponent((Component) widget.getNativeWidget());
        }
        widget.refresh();
    }

}
