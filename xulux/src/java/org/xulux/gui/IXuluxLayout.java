/*
   $Id: IXuluxLayout.java,v 1.1 2004-05-10 14:05:50 mvdb Exp $
   
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
 * The Xulux Layout interface.
 * If you want to make a layoutmanager available to xulux or
 * make a specific xulux layoutmanager. Since layout is normally
 * an internal gui layer process, mostly this will be used to make
 * layoutmanagers xulux aware.. 
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IXuluxLayout.java,v 1.1 2004-05-10 14:05:50 mvdb Exp $
 */
public interface IXuluxLayout {

    /**
     * Set the parent widget
     * @param widget the parent widget
     */
    void setParent(Widget widget);
  
    /**
     * Get the parent widget
     * @return the widget
     */
    Widget getParent();
  
    /**
     * Add a child widget to layout
     * @param widget the child widget
     */
    void addWidget(Widget widget);
  
    /**
     * Removes a widget from the layout.
     * This generally is also the place (at least in case of swing)
     * to do some magic with contraints. Through eg widget.getProperty() you can determine
     * what the contraints should be and pass it to the (native) layoutmanager.
     * @param widget the widget
     */
    void removeWidget(Widget widget);
  
    /**
     * Destroy the layout manager and it's resources
     */
    void destroy();

}
