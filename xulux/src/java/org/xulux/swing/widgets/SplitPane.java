/*
   $Id: SplitPane.java,v 1.5 2004-11-15 20:53:10 mvdb Exp $
   
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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import org.xulux.gui.ContainerWidget;
import org.xulux.gui.Widget;

/**
 * The splitpane
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SplitPane.java,v 1.5 2004-11-15 20:53:10 mvdb Exp $
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
        int divLocation = pane.getDividerLocation();
        try {
          divLocation = Integer.parseInt(getProperty("dividerlocation"));
        } catch(NumberFormatException nfe) {
        }
        pane.setDividerLocation(divLocation);
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
        int divLocation = 10;
        try {
          divLocation = Integer.parseInt(getProperty("dividerlocation"));
        } catch(NumberFormatException nfe) {
        }
        pane.setDividerLocation(divLocation);
        comp.setMinimumSize(new Dimension(divLocation, 10));
        if (position == null) {
            if (!topProcessed) {
                topProcessed = true;
                pane.setTopComponent((Component) widget.getNativeWidget());
            } else if (!bottomProcessed) {
                bottomProcessed = true;
                pane.setBottomComponent((Component) widget.getNativeWidget());
            } else {
                System.out.println("Ignoring widget " + widget + " since we already have a bottom and top component");
            }
        } else if ("bottom".equalsIgnoreCase(position)) {
            pane.setBottomComponent((Component) widget.getNativeWidget());
        } else if ("top".equalsIgnoreCase(position)) {
            pane.setTopComponent((Component) widget.getNativeWidget());
        }
        widget.refresh();
    }

}
