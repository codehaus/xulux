/*
   $Id: WidgetConfig.java,v 1.2 2004-01-28 12:22:45 mvdb Exp $
   
   Copyright 2002-2003 The Xulux Project

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xulux.global.IContentHandler;
import org.xulux.utils.ClassLoaderUtils;

/**
 * The widget config contains the main class
 * of the widget (the abstract) and the
 * core sytem specific declerations
 * (eg swt, swing)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfig.java,v 1.2 2004-01-28 12:22:45 mvdb Exp $
 */
public class WidgetConfig {

    /**
     * The map containg gui layer implementations of the widgettype
     */
    private HashMap map;
    /**
     * The initializers
     */
    private HashMap initializers;
    /**
     * The coreclass
     */
    private Class coreClass;
    /**
     * the map of contenthandlers
     */
    private HashMap contentHandlers;

    /**
     * Constructor for WidgetConfig.
     */
    public WidgetConfig() {
    }

    /**
     * Adds a specific implementation to the widgetConfig
     * @param type the type of gui layer
     * @param widgetClass the widget class
     */
    public void add(String type, Class widgetClass) {
        if (map == null) {
            map = new HashMap();
        }
        map.put(type, widgetClass);
    }

    /**
     *
     * @return the coreClass of the widget
     */
    public Class getCoreClass() {
        return coreClass;
    }

    /**
     * Set the coreClass of the widget
     *
     * @param coreClass the coreclass of the widget
     */
    public void setCoreClass(Class coreClass) {
        this.coreClass = coreClass;
    }
    /**
     *
     * @param type the type of gui layer
     * @return the Class of the specified widget type
     */
    public Class get(String type) {
        if (map == null) {
            return null;
        }
        return (Class) map.get(type);
    }

    /**
     * Adds a widgetInitializer.
     * It checks it the same initializer already exist or
     * not.
     *
     * @param type the type of gui layer
     * @param initializerClass the initializer class
     * @deprecated Please use addWidgetTool
     */
    public void addWidgetInitializer(String type, Class initializerClass) {
        if (initializers == null) {
            initializers = new HashMap();
        }
        ArrayList inits = (ArrayList) initializers.get(type);
        if (inits == null) {
            inits = new ArrayList();
        }
        if (!inits.contains(initializerClass)) {
            inits.add(initializerClass);
        }
        initializers.put(type, inits);
    }

    /**
     *
     * @param type - eg swing, swt, awt, html, etc.
     * @return the widgetHandler classlist for the specified type
     *
     */
    public List getWidgetInitializers(String type) {
        if (initializers == null) {
            return null;
        }
        return (List) initializers.get(type);
    }

    /**
     * Adds a widgettool to the widgetconfig
     * @param type the type of gui layer
     * @param clazzName the widgettools class
     */
    public void addWidgetTool(String type, String clazzName) {
        Class clazz = ClassLoaderUtils.getClass(clazzName);
        addWidgetTool(type, clazz);
    }

    /**
     * Adds a widgettool to the widgetconfig
     * @param type the type of gui layer
     * @param clazz the widgettools class
     */
    public void addWidgetTool(String type, Class clazz) {
        if (clazz == null) {
            return;
        }
        if (getContentHandlers() == null) {
            contentHandlers = new HashMap();
        }
        if (IContentHandler.class.isAssignableFrom(clazz)) {
            Class clzType = ((IContentHandler) ClassLoaderUtils.getObjectFromClass(clazz)).getType();
            getContentHandlers().put(clzType, clazz);
        }
    }

    /**
     * @param clazz the className to get the contenthandler for.
     * @return the contenthandler found.
     */
    public IContentHandler getContentHandler(Class clazz) {
        if (getContentHandlers() != null) {
            Class clz = (Class) getContentHandlers().get(clazz);
            if (clz == null) {
                // try to find a contenthandler that is of the same basetype (except Object).
                Iterator it = getContentHandlers().keySet().iterator();
                while (it.hasNext()) {
                    Class tmpClass = (Class) it.next();
                    if (tmpClass.isAssignableFrom(clazz)) {
                        clz = (Class) getContentHandlers().get(tmpClass);
                        break;
                    }
                }
            }
            return (IContentHandler) ClassLoaderUtils.getObjectFromClass(clz);
        }
        return null;
    }

    /**
     * @return the contenthandlers map or null if no contenthandlers are present
     */
    public Map getContentHandlers() {
        return contentHandlers;
    }
}
