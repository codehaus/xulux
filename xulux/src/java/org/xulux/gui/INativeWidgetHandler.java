/*
   $Id: INativeWidgetHandler.java,v 1.3 2004-03-31 09:37:59 mvdb Exp $
   
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
package org.xulux.gui;

/**
 * Interface to handle native widgets.
 * You should register the implementation
 * via eg the guidefaults xml file.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: INativeWidgetHandler.java,v 1.3 2004-03-31 09:37:59 mvdb Exp $
 */
public interface INativeWidgetHandler {

    /**
     * Returns the widget passed in (as convenience)
     * and adds the native widget to the parent.
     * This is normally used for automated processing
     * of native widgets.
     *
     * @param clazz - the className of the native widget
     * @param parent - the nyx parent widget
     * @return the nyx parent widget.
     */
    Widget getWidget(String clazz, Widget parent);

    /**
     * Returns the widget passed in (as convenience)
     * and adds the native widget to the parent.
     * Use this for hand programmed addition of native widgets.
     *
     * @param object - the native widget
     * @param parent - the nyx parent widget
     * @return - the nyx parent widget.
     */
    Widget getWidget(Object object, Widget parent);

    /**
     * Set the location on the last native widget added
     *
     * @param parent the parent widget to set the location on
     * @param x the x position
     * @param y the y position
     */
    void setLocationOnWidget(Widget parent, int x, int y);

    /**
     * Set the location on the last native widget added
     *
     * @param widget the native widget to set the location on
     * @param x the x position
     * @param y the y position
     */
    void setLocationOnWidget(Object widget, int x, int y);

    /**
     * Adds a widget to the parent, which is a native. (getParentWidget())
     * @param widget the widget to add
     * @param parentWidget the parent to add the widget to
     */
    void addWidgetToParent(Widget widget, Object parentWidget);
    
    /**
     * Should refresh the gui when needed. Eg Swing doesn't pick up on
     * new widgets very well. 
     *
     */
    void refresh(Object widget);

}
