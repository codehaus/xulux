/*
   $Id: GuiDefaults.java,v 1.3 2004-05-11 11:50:01 mvdb Exp $
   
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
package org.xulux.guidriver.defaults;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.WidgetConfig;
import org.xulux.gui.INativeWidgetHandler;
import org.xulux.gui.IParentWidgetHandler;
import org.xulux.gui.NyxListener;
import org.xulux.gui.XuluxToolkit;
import org.xulux.utils.ClassLoaderUtils;

/**
 * Just a placeholer for the default gui
 * properties.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaults.java,v 1.3 2004-05-11 11:50:01 mvdb Exp $
 */
public class GuiDefaults {

    /**
     * The default widgetType for the system
     * (eg swt, swing)
     */
    String defaultType;

    /**
     * Container for the nyx toolkits
     * Normal situations just have 1 toolkit..
     */
    Map xuluxToolkits;
    
    /**
     * The static log instance
     */
    static Log log = LogFactory.getLog(GuiDefaults.class);

    /**
     * contains all the known widgets
     */
    Map widgets;

    /**
     * Map of the fieldEventHandlers.
     * The key is the type
     */
    Map fieldEventHandlerMap;

    /**
     * Map of the nativeWidgetHandlers
     * They key is the type
     */
    Map nativeWidgetHandlerMap;

    /**
     * Map of parentWidgetHandlers
     * The key is the type
     */
    Map parentWidgetHandlerMap;

    /**
     * Constructor for GuiDefaults.
     */
    public GuiDefaults() {
    }

    /**
     * @return the default widget type.
     */
    public String getDefaultWidgetType() {
        return this.defaultType;
    }

    /**
     * Sets the application wide default widget type
     * (eg. swt, core, swing)
     *
     * @param type the default gui type for this application
     */
    public void setDefaultWidgetType(String type) {
        this.defaultType = type;
    }

    /**
     * Registers a widget that can be used to construct
     * an ui.
     * @param name - the widget name (eg combo)
     * @param clazz - the class that needs to be used
     * @param type - the widget type (eg swt, core, swing)
     *                core is used for later. Most of the time
     *                the coretype represent an generic extension
     *                of widget for eg a combo)
     */
    public void registerWidget(String name, String clazz, String type) {
        if (this.widgets == null) {
            widgets = new HashMap();
        }
        Class widgetClass = ClassLoaderUtils.getClass(clazz);
        if (widgetClass != null) {
            WidgetConfig config = (WidgetConfig) widgets.get(name);
            if (config == null) {
                config = new WidgetConfig();
            }
            if (type == null) {
                type = getDefaultWidgetType();
            } else {
                config.add(type, widgetClass);
            }
            widgets.put(name, config);
        } else {
            log.warn("Could not find " + clazz + " for widget named " + name + " and type " + type);
        }
    }

    /**
     *
     * @param widgetName the name of the widget to get the config for
     * @return the widgetconfig for the widget or null if not found
     */
    public WidgetConfig getWidgetConfig(String widgetName) {
        if (widgetName == null) {
            return null;
        }
        widgetName = widgetName.toLowerCase();
        return (WidgetConfig) widgets.get(widgetName);
    }

    /**
     * Returns the class that corresponds to the name or null
     * when not found
     * See GuiDefaults.xml for more info on defining widgets or
     * call registerWidget(name, clazz) to register one yourself..
     *
     * @param name the widget
     * @param type specifies the gui type to get widgetclass for.
     * @return the class for the widget
     */
    public Class getWidget(String name, String type) {
        if (name == null) {
          return null;
        }
        name = name.toLowerCase();
        WidgetConfig config = getWidgetConfig(name);
        if (config == null) {
            return null;
        }
        return config.get(type);
    }

    /**
     * Returns the class that corresponds to the name or null
     * when not found
     * See GuiDefaults.xml for more info on defining widgets or
     * call registerWidget(name, clazz) to register one yourself..
     * This will return the widget from the set defaulttype
     * (system default is swing).
     *
     * @param name - the widget name
     * @return the class for the widget
     */
    public Class getWidget(String name) {
        return getWidget(name, getDefaultWidgetType());
    }

    /**
     * @return a map with widgets.
     */
    public Map getWidgets() {
        return this.widgets;
    }
  
    /**
     * @return the NYX toolkit of the default type or null
     *          when not present
     */
    public XuluxToolkit getXuluxToolkit() {
        return getXuluxToolkit(getDefaultWidgetType());
    }
  
    /**
     * @param type the toolkit type (eg swt, swing)
     * @return the NYX toolkit type specified or null
     *          when not present
     */
    public XuluxToolkit getXuluxToolkit(String type) {
        if (this.xuluxToolkits != null) {
            return (XuluxToolkit) this.xuluxToolkits.get(type);
        }
        return null;
    }

    /**
     * Add a toolkit of specified type
     *
     * @param clazz the toolkit class
     * @param type if the type is null, it deaults to getDefaultWidgetType
     */
    public void registerXuluxToolkit(String clazz, String type) {
        if (type == null) {
            type = getDefaultWidgetType();
        }
        Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (object instanceof XuluxToolkit) {
            if (this.xuluxToolkits == null) {
                this.xuluxToolkits = new HashMap();
            }
            this.xuluxToolkits.put(type, object);
        } else {
            log.warn("XuluxToolkit class " + clazz + " is not of type XuluxToolkit or cannot be instantiated");
        }
    }

    /**
     *
     * @param type specifies the gui type to register the field event handler for
     * @param clazz the class name of the field event handler.
     */
    public void registerFieldEventHandler(String type, String clazz) {
        try {
            if (type == null) {
                type = getDefaultWidgetType();
            }
            Class clz = ClassLoaderUtils.getClass(clazz);
            if (!(NyxListener.class.isAssignableFrom(clz))) {
                log.warn(clazz + " is not an instance of NyxListener");
                return;
            }
            if (fieldEventHandlerMap == null) {
                fieldEventHandlerMap = new HashMap();
            }
            fieldEventHandlerMap.put(type, clz);
        }
        catch (Exception e) {
            log.warn("Could not find the fieldEventHandler " + clazz + " for type " + type);
        }
    }

    /**
     * @param type specifies the gui type to get the eventhandler for
     * @return a new instance of the event Handler or null when not found
     */
    public NyxListener getFieldEventHandler(String type) {
        if (fieldEventHandlerMap != null) {
            if (type == null) {
                type = getDefaultWidgetType();
            }
            return (NyxListener) ClassLoaderUtils.getObjectFromClass((Class) fieldEventHandlerMap.get(type));
        }
        return null;
    }

    /**
     * Registers the native widget handler.
     * The handler will contain all logic to be able
     * to use native widgets on top of the nyx widgets
     *
     * @param type specifies the gui type to register the native handler for
     * @param clazz the classname of the nativehandler.
     */
    public void registerNativeWidgetHandler(String type, String clazz) {
        if (nativeWidgetHandlerMap == null) {
            nativeWidgetHandlerMap = new HashMap();
        }
        if (type == null) {
            type = getDefaultWidgetType();
        }
        Object object  = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (!(object instanceof INativeWidgetHandler)) {
            log.warn(clazz + " is not an instance of INativeWidgetHandler");
            return;
        }
        nativeWidgetHandlerMap.put(type, object);
    }

    /**
     * @param type specifies the gui type to get the native widget handler for
     * @return the native widget handler for the specified type
     */
    public INativeWidgetHandler getNativeWidgetHandler(String type) {
        if (nativeWidgetHandlerMap != null) {
            return (INativeWidgetHandler) nativeWidgetHandlerMap.get(type);
        }
        return null;
    }

    /**
     * @return the native widgets handler for the defaulttype
     */
    public INativeWidgetHandler getNativeWidgetHandler() {
        return getNativeWidgetHandler(getDefaultWidgetType());
    }

    /**
     * Registers the parent widget handler.
     * This is used when cleaning up the applicationpart
     * to eg remove all widgets from the parent in one go.
     * Initially it will be read from the default GuiDefaults.xml
     * and can be overriden.
     * @param type specifies the gui type to register the parent handler for
     * @param clazz the class name of the parenthandler.
     */
    public void registerParentWidgetHandler(String type, String clazz) {
        if (type == null) {
            type = getDefaultWidgetType();
        }
        Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (!(object instanceof IParentWidgetHandler)) {
            log.warn(clazz + " is not an instance of IParentWidgetHandler");
            return;
        }
        if (parentWidgetHandlerMap == null) {
            parentWidgetHandlerMap = new HashMap();
        }
        parentWidgetHandlerMap.put(type, object);
    }

    /**
     * @param type specifies the gui type to get the parent widget handler for
     * @return the handler of parent widgets, or null when not found
     */
    public IParentWidgetHandler getParentWidgetHandler(String type) {
        if (parentWidgetHandlerMap != null) {
            return (IParentWidgetHandler) parentWidgetHandlerMap.get(type);
        }
        return null;
    }

    /**
     * @return the handler for the current gui framework
     *          or null if not found
     */
    public IParentWidgetHandler getParentWidgetHandler() {
        return getParentWidgetHandler(getDefaultWidgetType());
    }

}
