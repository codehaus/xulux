/*
   $Id: Widget2.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
   
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

import java.io.Serializable;

/**
 * The new Widget structure.
 * We are now using property handlers, native handlers, gui handlers
 * that construct a widget, instead of an abstract widget which is extended.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Widget2.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
 */
public class Widget2 implements Serializable {

	private String name;

	/**
	 * Default constructor
	 */	
	public Widget2() {
	}

	/**
	 * The main constructor of the widget
	 * @param name
	 */	
	public Widget2(String name) {
		setName(name);
	}

	/**
	 * Set the name of the widget
	 * @param name the name of the widget
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the name of the widget
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Sets the widget properties / functionality.
	 * @param property
	 * @param value
	 */
	public void setWidgetProperty(String property, String value) {
	}
}
