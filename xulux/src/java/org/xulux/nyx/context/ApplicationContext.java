/*
 $Id: ApplicationContext.java,v 1.36 2003-11-24 10:51:48 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.INativeWidgetHandler;
import org.xulux.nyx.gui.IParentWidgetHandler;
import org.xulux.nyx.gui.IWidgetInitializer;
import org.xulux.nyx.gui.NYXToolkit;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.guidefaults.GuiDefaultsHandler;
import org.xulux.nyx.rules.IRule;
import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * The context contains all the components currently
 * known to the system.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationContext.java,v 1.36 2003-11-24 10:51:48 mvdb Exp $
 */
public class ApplicationContext {
    /**
     * The default GuiDefaults (can be overridden);
     */
    public static final String GUIDEFAULTS_XML = "org/xulux/nyx/guidefaults/GuiDefaults.xml";

    /**
     * The applicationcontext instance
     */
    private static ApplicationContext instance;

    /**
     * The log system.
     */
    private static Log log = LogFactory.getLog(ApplicationContext.class);

    /**
     * This is the component registery
     * Which contains all components in the application
     * that aren't disposed
     */
    private ArrayList registry;

    /**
     * placeholder to know if we are in testmode
     * so exceptions shouldn't be caught
     */
    private static boolean test = false;

    /**
     * contains all the known parts
     */
    private HashMap parts;

    /**
     * contains all the known widgets
     */
    private HashMap widgets;

    /**
     * Map of parentWidgetHandlers
     * The key is the type
     */
    private HashMap parentWidgetHandlerMap;

    /**
     * Map of the nativeWidgetHandlers
     * They key is the type
     */
    private HashMap nativeWidgetHandlerMap;

    /**
     * Map of the fieldEventHandlers.
     * The key is the type
     */
    private HashMap fieldEventHandlerMap;

    /**
     * Container for the nyx toolkits
     * Normal situations just have 1 toolkit..
     */
    private HashMap nyxToolkits;

    /**
     * Map contains widget Initializers and
     * destroyers.
     */
    private HashMap widgetInitMap;

    /**
     * The currently registered rules
     */
    private ArrayList rules;

    /**
     * is nyx the complete application ?
     */
    private ApplicationPart application;

    /**
     * The request must fire pre in rule
     */
    public static final int PRE_REQUEST = 0;
    /**
     * The request must fire execute in rule
     */
    public static final int EXECUTE_REQUEST = 1;
    /**
     * The request must fire post in rule
     */
    public static final int POST_REQUEST = 2;
    /**
     * The request must fire init in rule
     */
    public static final int INIT_REQUEST = 3;
    /**
     * The request must fire destroy in rule
     */
    public static final int DESTROY_REQUEST = 4;

    /**
     * The default widgetType for the system
     * (eg swt, swing)
     */
    private String defaultType;

    /**
     * Constructor for GuiContext.
     */
    public ApplicationContext() {
        super();
    }

    /**
     * @param part - the applicationpart
     * @return if this applicationpart is an application by itself
     */
    public static boolean isPartApplication(ApplicationPart part) {
        return (part == getInstance().application);
    }

    /**
     * Exits the application
     */
    public static void exitApplication() {
        System.exit(0);
    }

    /**
     * @return the applicationcontext instance
     */
    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
            instance.initializeGuiDefaults();
        }
        return instance;
    }

    /**
     * Register applicationpart
     *
     * @param part registers a part in the context. The part will not be treated
     *         as an application.
     */
    public void register(ApplicationPart part) {
        register(part, false);
    }

    /**
     * Register an applicationpart.
     *
     * @param part the applicationpart
     * @param isApplication is this part the main entry for the application
     */
    public void register(ApplicationPart part, boolean isApplication) {
        if (registry == null) {
            registry = new ArrayList();
        }
        registry.add(part);
        if (isApplication) {
            this.application = part;
        }
    }

    /**
     * Register a certain rule to a certain part.
     * If the rulecount is zero, it will add it by default..
     *
     * @param partName can be eg TestForm or TestForm.fieldname If the emapping already exists,
     *         this will add it to the new mapping. Need to do some work here, since 2 identical
     *         rules will be called on processing.
     * @param rule - the rule to register. If there is already an instance of a rule present,
     *                the new rule will be ignored.
     */
    public void register(String partName, IRule rule) {
        if (rules == null) {
            rules = new ArrayList();
        }
        if (rule.getUseCount() == 0) {
            // we need to register it..
            rules.add(rule);
            // and ad that we have a "user"
            rule.registerPartName(partName);
        }
    }

    /**
     * Deregister everything connected to the partname.
     * It will remove the rule when the useCount is 0.
     * @todo We probably should add some kind of cacheSetting
     *       so heavily used rules will never be deregistered.when they are zero.
     * @param partName the partName to deregister.
     */
    public void deregister(String partName) {
        Iterator it = rules.iterator();
        while (it.hasNext()) {
            IRule rule = (IRule) it.next();
            rule.deregisterPartName(partName);
        }
    }

    /**
     * Fires a request of a certain type.
     *
     * @param request the requeset object to pass to the rule
     * @param type the type of request to fire in the rule
     */
    public static void fireRequest(PartRequest request, int type) {
        ApplicationPart part = request.getPart();
        ArrayList tmpRules = part.getRules();
        if (tmpRules == null || tmpRules.size() == 0) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Rules : " + tmpRules);
        }
        ArrayList currentRules = (ArrayList) tmpRules.clone();
        Iterator it = currentRules.iterator();
        fireRequests(it, request, type);
    }

    /**
     * Fires the rules on the specified field
     *
     * @param widget The field to fire the rules for
     * @param request the requeset object to pass to the rule
     * @param type the type of request to fire in the rule
     */
    public static void fireFieldRequest(Widget widget, PartRequest request, int type) {
        ArrayList tmpRules = widget.getRules();
        if (tmpRules == null || tmpRules.size() == 0) {
            return;
        }
        ArrayList currentRules = (ArrayList) tmpRules.clone();
        Iterator it = currentRules.iterator();
        fireRequests(it, request, type);
        currentRules.clear();
        currentRules = null;
    }

    /**
     * Fires an fieldrequest of the type specfied
     *
     * @param request the requeset object to pass to the rule
     * @param type the type of request to fire in the rule
     */
    public static void fireFieldRequests(PartRequest request, int type) {
        ArrayList requestWidgets = request.getPart().getWidgets();
        if (requestWidgets == null) {
            return;
        }
        ArrayList currentWidgets = (ArrayList) requestWidgets.clone();
        Iterator wit = currentWidgets.iterator();
        boolean stopAllRules = false;
        while (wit.hasNext() && !stopAllRules) {
            Widget widget = (Widget) wit.next();
            if (request.getWidget() != null && widget.equals(request.getWidget())) {
                // don't process the caller again..
                continue;
            }
            stopAllRules = request.getPart().needToStopAllRules(getInstance());
            if (stopAllRules) {
                return;
            }
            ArrayList tmpRules = widget.getRules();
            if (tmpRules == null || tmpRules.size() == 0) {
                continue;
            }
            ArrayList currentRules = (ArrayList) tmpRules.clone();
            Iterator it = currentRules.iterator();
            stopAllRules = fireRequests(it, request, type);
        }
    }

    /**
     * Convenience method so I don't have to replicate
     * code
     *
     * @param it an iterator containing all the rules to fire.
     * @param request the request object to pass to the rule
     * @param type the type of request to fire in the rule
     * @return true if all rules need to be stopped..
     */
    private static boolean fireRequests(Iterator it, PartRequest request, int type) {
        boolean stopAllRules = false;
        while (it.hasNext() && !stopAllRules) {
            IRule rule = (IRule) it.next();
            stopAllRules = request.getPart().needToStopAllRules(getInstance());
            if (stopAllRules) {
                return true;
            }
            try {
                switch (type) {
                    case PRE_REQUEST :
                        if (log.isTraceEnabled()) {
                            log.trace("Processing pre rule : " + rule.getClass().getName());
                        }
                        rule.pre(request);
                        continue;
                    case EXECUTE_REQUEST :
                        if (log.isTraceEnabled()) {
                            log.trace("Processing execute rule : " + rule.getClass().getName());
                        }
                        rule.execute(request);
                        continue;
                    case POST_REQUEST :
                        if (log.isTraceEnabled()) {
                            log.trace("Processing post rule : " + rule.getClass().getName());
                        }
                        rule.post(request);
                        continue;
                    default:
                        continue;
                }
            }
            catch (Exception e) {
                log.warn("Exception during Processing of rule : " + rule.getClass().getName() + "\n", e);
            }
        }
        return false;
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
        try {
            Class widgetClass = Class.forName(clazz);
            WidgetConfig config = (WidgetConfig) widgets.get(name);
            if (config == null) {
                config = new WidgetConfig();
            }
            if (type == null) {
                type = defaultType;
            }
            if ("core".equals(type)) {
                config.setCoreClass(widgetClass);
            }
            else {
                config.add(type, widgetClass);
            }
            widgets.put(name, config);
        }
        catch (ClassNotFoundException e) {
            log.warn("Could not find " + clazz + " for widget named " + name + " and type " + type);
        }
    }

    /**
     * Registers the parent widget handler.
     * This is used when cleaning up the applicationpart
     * to eg remove all widgets from the parent in one go.
     * Initially it will be read from the default GuiDefaults.xml
     * and can be overriden.
     * @param type specifies the gui type to register the parent handler for
     * @param clazz the class name of the parenthandler.
     * @deprecated No replacement yet.
     */
    public void registerParentWidgetHandler(String type, String clazz) {
        if (parentWidgetHandlerMap == null) {
            parentWidgetHandlerMap = new HashMap();
        }
        try {
            if (type == null) {
                type = defaultType;
            }
            Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
            if (!(object instanceof IParentWidgetHandler)) {
                if (log.isWarnEnabled()) {
                    log.warn(clazz + " is not an instance of IParentWidgetHandler");
                }
            }

            parentWidgetHandlerMap.put(type, object);
        }
        catch (Exception e) {
            log.warn("Could not find " + clazz + " for parentWidgetHandler named for " + type);
        }
    }

    /**
     * Registers the native widget handler.
     * The handler will contain all logic to be able
     * to use native widgets on top of the nyx widgets
     *
     * @param type specifies the gui type to register the native handler for
     * @param clazz the classname of the nativehandler.
     * @deprecated No replacement yet
     */
    public void registerNativeWidgetHandler(String type, String clazz) {
        if (nativeWidgetHandlerMap == null) {
            nativeWidgetHandlerMap = new HashMap();
        }
        try {
            if (type == null) {
                type = defaultType;
            }
            Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
            if (!(object instanceof INativeWidgetHandler)) {
                if (log.isWarnEnabled()) {
                    log.warn(clazz + " is not an instance of INativeWidgetHandler");
                }
            }

            nativeWidgetHandlerMap.put(type, object);
        }
        catch (Exception e) {
            log.warn("Could not find the nativeWidgetHandler " + clazz + " for type " + type);
        }
    }

    /**
     *
     * @param type specifies the gui type to register the field event handler for
     * @param clazz the class name of the field event handler.
     * @deprecated No replacement yet
     */
    public void registerFieldEventHandler(String type, String clazz) {
        if (fieldEventHandlerMap == null) {
            fieldEventHandlerMap = new HashMap();
        }
        try {
            if (type == null) {
                type = defaultType;
            }
            Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
            if (!(object instanceof NyxListener)) {
                if (log.isWarnEnabled()) {
                    log.warn(clazz + " is not an instance of NyxListener");
                }
            }
            fieldEventHandlerMap.put(type, object.getClass());
        }
        catch (Exception e) {
            log.warn("Could not find the fieldEventHandler " + clazz + " for type " + type);
        }
    }

    /**
     * Returns a new instance of the event Handler
     *
     * @param type specifies the gui type to get the eventhandler for
     * @return the fieldEventHandler.
     * @deprecated No replacement yet.
     */
    public NyxListener getFieldEventHandler(String type) {
        if (fieldEventHandlerMap != null) {
            if (type == null) {
                type = defaultType;
            }
            return (NyxListener) ClassLoaderUtils.getObjectFromClass((Class) fieldEventHandlerMap.get(type));
        }
        return null;
    }

    /**
     *
     * @param type specifies the gui type to get the parent widget handler for
     * @return the handler of parent widgets, or null when not found
     * @deprecated No replacement yet
     */
    public IParentWidgetHandler getParentWidgetHandler(String type) {
        if (parentWidgetHandlerMap != null) {
            return (IParentWidgetHandler) parentWidgetHandlerMap.get(type);
        }
        return null;
    }

    /**
     *
     * @return the handler for the current gui framework
     *          or null if not found
     * @deprecated No replacement yet
     */
    public IParentWidgetHandler getParentWidgetHandler() {
        return getParentWidgetHandler(getDefaultWidgetType());
    }

    /**
     *
     * @param type specifies the gui type to get the native widget handler for
     * @return the native widget handler for the specified type
     * @deprecated No replacement yet
     */
    public INativeWidgetHandler getNativeWidgetHandler(String type) {
        if (nativeWidgetHandlerMap != null) {
            return (INativeWidgetHandler) nativeWidgetHandlerMap.get(type);
        }
        return null;
    }

    /**
     *
     * @return the native widgets handler for the defaulttype
     * @deprecated No replacement yet.
     */
    public INativeWidgetHandler getNativeWidgetHandler() {
        return getNativeWidgetHandler(getDefaultWidgetType());
    }

    /**
     * Initializes the default GuiDefaults in the system
     *
     * @see org.xulux.nyx.guidefaults.GuiDefaultsHandler#read for more info
     * to override the current guidefaults..
     */
    private void initializeGuiDefaults() {
        initializeGuiDefaults(GUIDEFAULTS_XML);
    }

    /**
     * Initializes the gui defaults from the specified xmlfile.
     * This overrides the system defaults
     *
     * @param xmlFile the guidefaults xml file.
     */
    public void initializeGuiDefaults(String xmlFile) {
        GuiDefaultsHandler handler = new GuiDefaultsHandler();
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(xmlFile);
        handler.read(stream);
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
        name = name.toLowerCase();
        WidgetConfig config = (WidgetConfig) widgets.get(name);
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
     * Registers a part in the applicationcontext.
     * This is needed for part interoperability.
     * Advice is not to use it yet..
     *
     * @param part the applicationpart to register
     */
    public void registerPart(ApplicationPart part) {
        if (parts == null) {
            parts = new HashMap();
        }

        parts.put(part.getName(), part);
    }

    /**
     *
     * @param name the name of the applicationPart.
     * @return the requested part or null when not found.
     */
    public ApplicationPart getPart(String name) {
        if (parts == null) {
            return null;
        }
        ApplicationPart part = (ApplicationPart) parts.get(name);
        return part;
    }

    /**
     * Removes parts from the context
     *
     * @param name the name of the applicationpart
     */
    public void removePart(String name) {
        if (parts != null) {
            parts.remove(name);
        }
    }

    /**
     *
     * @return a collection of all the parts.
     */
    public Collection getParts() {
        if (parts == null) {
            return null;
        }
        return parts.values();
    }

    /**
     * Enable test mode.
     * There is no testmode in the system yet, just setting it is
     * possible.
     *
     * @param testMode true enables the testmode.
     */
    public static void setTest(boolean testMode) {
        test = testMode;
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
     *
     * @return the default widget type for the context instance
     */
    public String getDefaultWidgetType() {
        return this.defaultType;
    }

    /**
     * @return a map with widgets.
     */
    public HashMap getWidgets() {
        return this.widgets;
    }

    /**
     * @return the NYX toolkit of the default type or null
     *          when not present
     * @deprecated Use getWidgetConfig
     */
    public NYXToolkit getNYXToolkit() {
        return getNYXToolkit(getDefaultWidgetType());
    }

    /**
     *
     * @param type the toolkit type (eg swt, swing)
     * @return the NYX toolkit type specified or null
     *          when not present
     * @deprecated Use getWidgetConfig
     */
    public NYXToolkit getNYXToolkit(String type) {
        if (this.nyxToolkits != null) {
            return (NYXToolkit) this.nyxToolkits.get(type);
        }
        return null;
    }

    /**
     * Add a toolkit of specified type
     *
     * @param clazz the toolkit class
     * @param type if the type is null, it deaults to getDefaultWidgetType
     * @deprecated Use registerWidgetTool.
     */
    public void registerNYXToolkit(String clazz, String type) {
        if (this.nyxToolkits == null) {
            this.nyxToolkits = new HashMap();
        }
        if (type == null) {
            type = getDefaultWidgetType();
        }
        Object object = ClassLoaderUtils.getObjectFromClassString(clazz);
        if (object instanceof NYXToolkit) {
            this.nyxToolkits.put(type, object);
        }
        else {
            log.warn("NYXToolkit class " + clazz + " is not of type NYXToolkit or cannot be instantiated");
        }
    }

    /**
     * Register a widget tool for the specified widget.
     *
     * @param clazz the clazzname of the widget tool
     * @param widgetName the name of the widget
     * @param type the guilayer type to register it for.
     * @deprecated No replacement yet.
     */
    public void registerWidgetTool(String clazz, String widgetName, String type) {
        WidgetConfig config = getWidgetConfig(widgetName);
        if (config != null) {
            config.addWidgetTool(clazz, type);
        }
    }

    /**
     * Register a widgettool or the  specified widget.
     * It registers this or the defaulttype.
     *
     * @param clazz the clazzname of the widget tool
     * @param widgetName the name of the widget
     * @deprecated No replacement yet.
     */
    public void registerWidgetTool(String clazz, String widgetName) {
        registerWidgetTool(clazz, widgetName, getDefaultWidgetType());
    }

    /**
     *
     * @param widgetName the name of the widget to get the config for
     * @return the widgetconfig for the widget
     */
    public WidgetConfig getWidgetConfig(String widgetName) {
        return (WidgetConfig) widgets.get(widgetName);
    }

    /**
     * Add a widget initializer
     *
     * @param initializerClass - the class that implements IWidgetInitializer
     * @param widgetName the name of the widget to register the initializer for
     * @param type the type of gui to register the initializer for
     * @deprecated Use registerWidgetTool now.
     */
    public void registerWidgetInitializer(String initializerClass, String widgetName, String type) {
        if (type == null) {
            type = getDefaultWidgetType();
        }
        Class clz = ClassLoaderUtils.getClass(initializerClass);
        if (clz != null) {
            if (!IWidgetInitializer.class.isAssignableFrom(clz)) {
                if (log.isWarnEnabled()) {
                    log.warn("Widget initializer " + initializerClass + " is not of type IWidgetInitializer");
                }
                return;
            }
            WidgetConfig config = (WidgetConfig) widgets.get(widgetName);
            if (config != null) {
                config.addWidgetInitializer(type, clz);
            }
            else {
                if (log.isWarnEnabled()) {
                    log.warn(
                        "Cannot register widget initializer "
                            + initializerClass
                            + " since there is no widget with the name "
                            + widgetName);
                }
            }
        }
        else {
            if (log.isWarnEnabled()) {
                log.warn("Widget initializer " + initializerClass + " cannot be initialized");
            }
        }
    }

    /**
     *
     * @param widgetType the type of widget to get the widgetinitializer for
     * @return a new instance of the widget initializer that is part of this widget
     *          or null when not present
     * @deprecated use getWidgetConfig
     */
    public List getWidgetInitializers(String widgetType) {
        WidgetConfig config = (WidgetConfig) widgets.get(widgetType.toLowerCase());
        List clzs = config.getWidgetInitializers(getDefaultWidgetType());
        if (clzs == null) {
            return null;
        }
        Iterator it = clzs.iterator();
        ArrayList list = new ArrayList();
        while (it.hasNext()) {
            Class clz = (Class) it.next();
            list.add((IWidgetInitializer) ClassLoaderUtils.getObjectFromClass(clz));
        }
        if (list.size() == 0) {
            return null;
        }
        return list;
    }
}
