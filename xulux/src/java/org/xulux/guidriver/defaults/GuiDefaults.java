/*
   $Id: GuiDefaults.java,v 1.6 2004-05-17 22:58:05 mvdb Exp $
   
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
import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.NyxListener;
import org.xulux.gui.XuluxToolkit;
import org.xulux.utils.ClassLoaderUtils;

/**
 * Just a placeholer for the default gui
 * properties.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaults.java,v 1.6 2004-05-17 22:58:05 mvdb Exp $
 */
public class GuiDefaults {

    /**
     * The default widgetType for the system
     * (eg swt, swing)
     */
    String defaultGuiLayer;

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
     * The map containing the layouts
     */
    Map layoutMap;

    /**
     * The default layout name
     */
    String defaultLayout;

    /**
     * Constructor for GuiDefaults.
     */
    public GuiDefaults() {
    }

    /**
     * @return the default gui layer.
     */
    public String getDefaultGuiLayer() {
        return this.defaultGuiLayer;
    }

    /**
     * Sets the application wide default widget type
     * (eg. swt, core, swing)
     *
     * @param type the default gui type for this application
     */
    public void setDefaultGuiLayer(String guiLayer) {
        this.defaultGuiLayer = guiLayer;
    }

    /**
     * Registers a widget that can be used to construct
     * an ui.
     * @param name the widget name (eg combo)
     * @param clazz the class that needs to be used
     * @param guiLayer the guiLayer to use
     */
    public void registerWidget(String name, String clazz, String guiLayer) {
        if (this.widgets == null) {
            widgets = new HashMap();
        }
        Class widgetClass = ClassLoaderUtils.getClass(clazz);
        if (widgetClass != null) {
            WidgetConfig config = (WidgetConfig) widgets.get(name);
            if (config == null) {
                config = new WidgetConfig();
            }
            if (guiLayer == null) {
                guiLayer = getDefaultGuiLayer();
            } else {
                config.add(guiLayer, widgetClass);
            }
            widgets.put(name, config);
        } else {
            log.warn("Could not find " + clazz + " for widget named " + name + " and guiLayer " + guiLayer);
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
     * @param guiLayer specifies the gui layer
     * @return the class for the widget
     */
    public Class getWidget(String name, String guiLayer) {
        if (name == null) {
          return null;
        }
        name = name.toLowerCase();
        WidgetConfig config = getWidgetConfig(name);
        if (config == null) {
            return null;
        }
        return config.get(guiLayer);
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
        return getWidget(name, getDefaultGuiLayer());
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
        return getXuluxToolkit(getDefaultGuiLayer());
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
     * @param guiLayer if the guiLayer is null, it defaults to the default guiLayer
     */
    public void registerXuluxToolkit(String clazz, String guiLayer) {
        if (guiLayer == null) {
            guiLayer = getDefaultGuiLayer();
        }
        Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (object instanceof XuluxToolkit) {
            if (this.xuluxToolkits == null) {
                this.xuluxToolkits = new HashMap();
            }
            this.xuluxToolkits.put(guiLayer, object);
        } else {
            log.warn("XuluxToolkit class " + clazz + " is not of type XuluxToolkit or cannot be instantiated");
        }
    }

    /**
     *
     * @param guiLayer the guilayer to register the field eventhandler for
     * @param clazz the class name of the field event handler.
     */
    public void registerFieldEventHandler(String guiLayer, String clazz) {
        if (guiLayer == null) {
            guiLayer = getDefaultGuiLayer();
        }
        Class clz = ClassLoaderUtils.getClass(clazz);
        if (clz == null || !(NyxListener.class.isAssignableFrom(clz))) {
            log.warn(clazz + " is not an instance of NyxListener");
            return;
        }
        if (fieldEventHandlerMap == null) {
            fieldEventHandlerMap = new HashMap();
        }
        fieldEventHandlerMap.put(guiLayer, clz);
    }

    /**
     * @param guiLayer specifies the guiLayer to get the eventhandler for
     * @return a new instance of the event Handler or null when not found
     */
    public NyxListener getFieldEventHandler(String guiLayer) {
        if (fieldEventHandlerMap != null) {
            if (guiLayer == null) {
                guiLayer = getDefaultGuiLayer();
            }
            return (NyxListener) ClassLoaderUtils.getObjectFromClass((Class) fieldEventHandlerMap.get(guiLayer));
        }
        return null;
    }

    /**
     * Registers the native widget handler.
     * The handler will contain all logic to be able
     * to use native widgets on top of the nyx widgets
     *
     * @param guiLayer specifies the gui type to register the native handler for
     * @param clazz the classname of the nativehandler.
     */
    public void registerNativeWidgetHandler(String guiLayer, String clazz) {
        if (nativeWidgetHandlerMap == null) {
            nativeWidgetHandlerMap = new HashMap();
        }
        if (guiLayer == null) {
            guiLayer = getDefaultGuiLayer();
        }
        Object object  = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (!(object instanceof INativeWidgetHandler)) {
            log.warn(clazz + " is not an instance of INativeWidgetHandler");
            return;
        }
        nativeWidgetHandlerMap.put(guiLayer, object);
    }

    /**
     * @param guiLayer specifies the guiLayer to get the native widget handler for
     * @return the native widget handler for the specified type
     */
    public INativeWidgetHandler getNativeWidgetHandler(String guiLayer) {
        if (nativeWidgetHandlerMap != null) {
            return (INativeWidgetHandler) nativeWidgetHandlerMap.get(guiLayer);
        }
        return null;
    }

    /**
     * @return the native widgets handler for the defaulttype
     */
    public INativeWidgetHandler getNativeWidgetHandler() {
        return getNativeWidgetHandler(getDefaultGuiLayer());
    }

    /**
     * Registers the parent widget handler.
     * This is used when cleaning up the applicationpart
     * to eg remove all widgets from the parent in one go.
     * Initially it will be read from the default GuiDefaults.xml
     * and can be overriden.
     * @param guiLayer specifies the guiLayer to register the parent handler for
     * @param clazz the class name of the parenthandler.
     */
    public void registerParentWidgetHandler(String guiLayer, String clazz) {
        if (guiLayer == null) {
            guiLayer = getDefaultGuiLayer();
        }
        Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (!(object instanceof IParentWidgetHandler)) {
            log.warn(clazz + " is not an instance of IParentWidgetHandler");
            return;
        }
        if (parentWidgetHandlerMap == null) {
            parentWidgetHandlerMap = new HashMap();
        }
        parentWidgetHandlerMap.put(guiLayer, object);
    }

    /**
     * @param guiLayer specifies the guiLayer to get the parent widget handler for
     * @return the handler of parent widgets, or null when not found
     */
    public IParentWidgetHandler getParentWidgetHandler(String guiLayer) {
        if (parentWidgetHandlerMap != null) {
            return (IParentWidgetHandler) parentWidgetHandlerMap.get(guiLayer);
        }
        return null;
    }

    /**
     * @return the handler for the current gui framework
     *          or null if not found
     */
    public IParentWidgetHandler getParentWidgetHandler() {
        return getParentWidgetHandler(getDefaultGuiLayer());
    }
    
    /**
     * Register the specified layout.
     * Setting the default layout is based on the name and will be the same across
     * all gui layers. The last one that calls this method with the field isDefault
     * as true, will be the default for the application
     *
     * @param name the name of the layout
     * @param isDefault is this layout the default
     * @param clazz the class of the layout
     * @param guiLayer the gui layer
     */
    public void registerLayout(String name, boolean isDefault, String clazz, String guiLayer) {
        if (name == null) {
            return;
        }
        if (layoutMap == null) {
            layoutMap = new HashMap();
        }
        Map map = (Map) layoutMap.get(name);
        if (map == null) {
            map = new HashMap();
        }
        if (guiLayer != null) {
            Class clz = ClassLoaderUtils.getClass(clazz);
            if (clz != null) {
              if (IXuluxLayout.class.isAssignableFrom(clz)) {
                  map.put(guiLayer, ClassLoaderUtils.getClass(clazz));
                  if (isDefault) {
                      defaultLayout = name;
                  }
              } else {
                  log.warn("Class " + clazz + "is not a IXuluxLayout");
              }
            }
        }
        if (map.size() > 0) {
            layoutMap.put(name, map);
        }
    }

    /**
     * @param guiLayer the gui layer type. If null, it will use the default gui layer.
     * @param name the name of the layout
     * @return the layout specified or null if not found
     */
    public IXuluxLayout getLayout(String guiLayer, String name) {
        if (layoutMap != null) {
            Map map = (Map) layoutMap.get(name);
            if (map != null) {
                if (guiLayer == null) {
                    guiLayer = getDefaultGuiLayer();
                }
                Class clz = (Class) map.get(guiLayer);
                return (IXuluxLayout) ClassLoaderUtils.getObjectFromClass(clz);
            }
        }
        return null;
    }
    
    /**
     * The defaultwidget type needs to be set for this method to work.
     *
     * @return the default layout if one is set
     */
    public IXuluxLayout getDefaultLayout() {
        return getLayout(getDefaultGuiLayer(), defaultLayout);
    }

    /**
     * The defaultwidget type needs to be set for this method to work.
     *
     * @param guiLayer the gui layer type
     * @return the default layout if one is set
     */
    public IXuluxLayout getDefaultLayout(String guiLayer) {
        return getLayout(guiLayer, defaultLayout);
    }

}
