/*
   $Id: IWidgetInitializer.java,v 1.2 2004-01-28 15:00:23 mvdb Exp $
   
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
 * This interface is to be able to add custom destroyers
 * and initializers to widgets.
 * If you want to eg customize the a native combo box,
 * you should register the specific widgetinitializer in the
 * widgetconfig or the guidefaults.xml file.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IWidgetInitializer.java,v 1.2 2004-01-28 15:00:23 mvdb Exp $
 */
public interface IWidgetInitializer {

    /**
     * Initialize will be called after the widget is
     * finished it internal processed and after
     * creation of the native widget.
     *
     * @param widget the widget to proces initialization on
     */
    void initialize(Widget widget);

    /**
     * Destroy will be called before the widget starts
     * destroying itself. You should destroy everything
     * you've done on initialization in this method
     *
     * @param widget the widget to clean up after
     */
    void destroy(Widget widget);
}
