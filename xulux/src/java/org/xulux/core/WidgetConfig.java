/*
   $Id: WidgetConfig.java,v 1.4 2004-04-01 16:15:08 mvdb Exp $
   
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
package org.xulux.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xulux.dataprovider.contenthandlers.ContentView;
import org.xulux.dataprovider.contenthandlers.IContentHandler;
import org.xulux.gui.IPropertyHandler;
import org.xulux.utils.ClassLoaderUtils;

/**
 * The widget config contains the main class
 * of the widget (the abstract) and the
 * core sytem specific declerations
 * (eg swt, swing)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfig.java,v 1.4 2004-04-01 16:15:08 mvdb Exp $
 */
public class WidgetConfig {

    /**
     * The map containg gui layer implementations of the widgettype
     */
    private Map map;
    /**
     * The initializers
     */
    private Map initializers;
    /**
     * The coreclass
     */
    private Class coreClass;
    /**
     * the map of contenthandlers
     */
    private Map contentHandlers;

    /**
     * The map of contenthandler views.
     * It only registers the default one!
     */
    private HashMap contentHandlerViews;

    /**
     * The widget defaults that can be copied
     * in when initializing a widget.
     */
    private Map widgetDefaults;

  	/**
  	 * the list Property handlers
  	 */
  	private Map propertyHandlers;

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
     * Adds a contenthandler to a widgetconfiguration.
     * @param clz the clazz of the contenthandler
     * @param view the clazz of the view. If none specified toString handling will be used
     */
    public void addContentHandler(String clz, String view) {
        if (clz == null) {
            return;
        }
        if (getContentHandlers() == null) {
            contentHandlers = new HashMap();
        }
        if (contentHandlerViews == null) {
            contentHandlerViews = new HashMap();
        }
        Class clazz = ClassLoaderUtils.getClass(clz);
        if (clazz == null) {
            return;
        }
        if (IContentHandler.class.isAssignableFrom(clazz)) {
            IContentHandler handler = (IContentHandler) ClassLoaderUtils.getObjectFromClass(clazz);
            if (handler == null) {
                return;
            }
            Class clzType = ((IContentHandler) ClassLoaderUtils.getObjectFromClass(clazz)).getType();
            getContentHandlers().put(clzType, clazz);
            if (view != null) {
                Class viewClass = ClassLoaderUtils.getClass(view);
                if (viewClass != null && ContentView.class.isAssignableFrom(viewClass)) {
                    contentHandlerViews.put(clzType, viewClass);
                }
            }
        }
    }
    /**
     * @param clazz the className to get the contenthandler for.
     * @return the contenthandler found.
     */
    public IContentHandler getContentHandler(Class clazz) {
        if (getContentHandlers() != null) {
            Class clz = (Class) getContentHandlers().get(clazz);
            Class finalClass = clazz;
            if (clz == null) {
                // try to find a contenthandler that is of the same basetype (except Object).
                Iterator it = getContentHandlers().keySet().iterator();
                while (it.hasNext()) {
                    Class tmpClass = (Class) it.next();
                    if (tmpClass.isAssignableFrom(clazz)) {
                        clz = (Class) getContentHandlers().get(tmpClass);
                        finalClass = tmpClass;
                        break;
                    }
                }
            }
            IContentHandler retValue = (IContentHandler) ClassLoaderUtils.getObjectFromClass(clz);
            if (contentHandlerViews != null) {
                retValue.setView((Class) contentHandlerViews.get(finalClass));
            }
            return retValue; 
        }
        return null;
    }

    /**
     * @return the contenthandlers map or null if no contenthandlers are present
     */
    public Map getContentHandlers() {
        return contentHandlers;
    }

    /**
     * Adds a propertyhandler to the widgetconfig. This will be used to initialize
     * A propertyhandler is a system to handle the widget.setProperty system and will
     * be initialized upon creation of the widget.
     * 
     * @param clz the class name
     * @param use the type of usage for this propertyhandler. Supported : normal, delayed, refresh
     * @param name the name of the property
     * @param type the type of guilayer
     */
    public void addPropertyHandler(String clz, String use, String name, String type) {
    	Class clazz = ClassLoaderUtils.getClass(clz);
    	if (clazz == null) {
    		System.out.println("cannot find class "+clz);
    		return;
    	}
    	if (IPropertyHandler.class.isAssignableFrom(clazz)) {
    		PropertyConfig pc = new PropertyConfig(clazz, use, name);
            if (propertyHandlers == null) {
                propertyHandlers = new HashMap();
            }
            List list = getPropertyHandlers(type);
            if (list == null) {
                list = new ArrayList();
            }
            list.add(pc);
            propertyHandlers.put(type, list);
    	} else {
    		System.out.println("Class "+clz+" is not of type "+IPropertyHandler.class.getName());
    	}
    }
    /**
     *
     * @param type the gui layer type
     * @return all the property handlers for this widget
     */
    public List getPropertyHandlers(String type) {
        if (propertyHandlers != null) {
            List list = new ArrayList();
            return (List) propertyHandlers.get(type);
        }
        return null;
    }
    /**
     * Register a property for this widget that needs to be set
     * on creation of the widget
     * 
     * @param key the key of the property
     * @param value the value of the property
     */
    public void registerWidgetDefault(String key, Object value) {
      if (widgetDefaults == null) {
        widgetDefaults = new HashMap();
      }
      widgetDefaults.put(key, value);
    }
    
    public Map getWidgetDefaults() {
      return widgetDefaults;
    }
    
}
