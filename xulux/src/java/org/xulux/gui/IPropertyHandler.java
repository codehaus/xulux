/*
   $Id: IPropertyHandler.java,v 1.4 2004-03-23 16:16:22 mvdb Exp $
   
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
 * The interface for propertyhandlers..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IPropertyHandler.java,v 1.4 2004-03-23 16:16:22 mvdb Exp $
 */
public interface IPropertyHandler {

    /**
     * call property on refresh (which is essntially always the case)
     */
    String REFRESH = "refresh";
    /**
     * Only call the property handler when the property is set.
     */
    String NORMAL = "normal";
    /**
     * Wait for initialisation of the widget and then call the handler
     */
    String DELAYED = "delayed";
	/**
	 * Initialises the propertyhandler
	 */
	void init();
	
    /**
     * Handle the property for the widget specified.
     * You can choose to use the registered property name(s) or
     * just figure out the naming yourself.
     *
     * @param widget - the widget to handle the property for.
     * @param property - the property to handle
     * @return true when the property has been succesfully handled
     */
    boolean handleProperty(Widget widget, String property);

    /**
     * @return the value that is associated with propertyhandler.
     *         If this is not used, it will just return null. Normally
     *         this is a value that is needed across properties and within the widget
     */
    Object getValue();
    /**
     * Destroys the property handler and all it's resources
     */
	void destroy();
}
