/*
   $Id: PropertyConfig.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
   
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
package org.xulux.context;

import org.xulux.gui.IPropertyHandler;

/**
 * The property config object contains the data needed to initialize the
 * property configuration.. You can get the propertyconfig and change the defaults
 * using the setters.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PropertyConfig.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
 */
public class PropertyConfig {

    /**
     * The class
     */
	private Class className;
    /**
     * The usage scenario
     */
    private String use;
    /**
     * the name of the property
     */
    private String name;
    
    /**
     * Restricted constructor
     *
     * @param className the name of the class
     * @param use the use scenario of this property
     * @param name the name of the property
     */
	public PropertyConfig(Class className, String use, String name) {
        setClassName(className);
        setUse(use);
        setName(name);
	}

    /**
     * @return
     */
    public Class getClassName() {
        return className;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getUse() {
        if (getName() == null && getClassName() != null) {
            return IPropertyHandler.REFRESH;
        }
        return use;
    }

    /**
     * @param className the propertyhandler class
     */
    public void setClassName(Class className) {
        this.className = className;
    }

    /**
     * @param name the name of the property. If the name is null, the use will
     *        be set to refresh!
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * @param use when to use this propertyhandler
     */
    public void setUse(String use) {
        this.use = use;
    }

}
