/*
   $Id: PropertyHandlerFactory.java,v 1.3 2004-03-16 14:35:14 mvdb Exp $
   
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
package org.xulux.swing.widgets.handlers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.IPropertyHandler;
import org.xulux.gui.Widget;
import org.xulux.utils.ClassLoaderUtils;

/**
 * A Handler for properties. Saves a lot of time duplicating
 * code.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PropertyHandlerFactory.java,v 1.3 2004-03-16 14:35:14 mvdb Exp $
 */
public class PropertyHandlerFactory {

    /**
     * The map that contains property handler objects.
     */
    protected static HashMap ph;

    /**
     * the log..
     */
    protected static Log log = LogFactory.getLog(PropertyHandlerFactory.class);

    /**
     * Allow overrides..
     */
    protected PropertyHandlerFactory() {
    }

    /**
     *
     * @param widget
     * @param property
     * @param properties
     * @return if the widget needs a refresh.
     */
    public static boolean handleProperty(Widget widget, String property) {
        if (ph == null) {
            return false;
        }
        IPropertyHandler h = (IPropertyHandler)ph.get(property);
        h.handleProperty(widget, property);
        return false;
    }

    public static void registerPropertyHandler(String clazz, String property) {
        if (clazz != null) {
            registerPropertyHandler(ClassLoaderUtils.getClass(clazz), property);
        }
    }
    public static void registerPropertyHandler(Class clazz, String property) {
        if (ph == null) {
            ph = new HashMap();
        }
        if (clazz.isAssignableFrom(IPropertyHandler.class)) {
            ph.put(property, clazz);
        } else {
            if (log.isWarnEnabled()) {
                log.warn("PropertyHandler "+clazz.getName()+
                  " is not of type IPropertyHandler, not registring class");
            }
        }

    }

    public static void registerPropertyHandler(Object object, String property) {
        if (object != null) {
            registerPropertyHandler(object.getClass(), property);
        }
    }

    public static Map getPropertyHandlers() {
        return ph;
    }

}
