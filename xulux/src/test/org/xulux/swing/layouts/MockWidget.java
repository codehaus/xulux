/*
   $Id: MockWidget.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
   
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
package org.xulux.swing.layouts;

import org.xulux.gui.Widget;

/**
 * A mock widget. This emulates certain behaviour needed to
 * test layout functionality
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MockWidget.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
 */
public class MockWidget extends Widget {

    /**
     * The native widget
     */
    private Object nativeWidget;
    /**
     * @param name the name of the widget
     */
    public MockWidget(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        this.nativeWidget = null;
        getPart().removeWidget(this, this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        return this.nativeWidget;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        this.nativeWidget = "nativeWidget";
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {

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
     * Set the native widget
     *
     * @param object the native widget
     */
    public void setNativeWidget(Object object) {
        this.nativeWidget = object;
    }

    /**
     * is this a rootwidget or not ?
     */
    private boolean rootWidget;

    /**
     * @see org.xulux.nyx.gui.Widget#isRootWidget()
     */
    public boolean isRootWidget() {
        return this.rootWidget;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isRootWidget()
     */
    public boolean canBeRootWidget() {
        return this.rootWidget;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#setRootWidget(boolean)
     */
    public void setRootWidget(boolean isRootWidget) {
        this.rootWidget = isRootWidget;
    }

}
