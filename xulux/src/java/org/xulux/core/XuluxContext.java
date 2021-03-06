/*
   $Id: XuluxContext.java,v 1.4 2004-05-11 11:50:00 mvdb Exp $
   
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.dataprovider.Dictionary;
import org.xulux.gui.Widget;
import org.xulux.guidriver.defaults.GuiDefaults;
import org.xulux.guidriver.defaults.GuiDefaultsHandler;
import org.xulux.rules.IRule;

/**
 * The context contains all the components currently
 * known to the system.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XuluxContext.java,v 1.4 2004-05-11 11:50:00 mvdb Exp $
 */
public class XuluxContext {
    /**
     * The default GuiDefaults (can be overridden);
     */
    public static final String GUIDEFAULTS_XML = "org/xulux/guidriver/defaults/GuiDefaults.xml";

    /**
     * The applicationcontext instance
     */
    private static XuluxContext instance;

    /**
     * The log system.
     */
    private static Log log = LogFactory.getLog(XuluxContext.class);

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

    private static Dictionary dictionary;

    private static GuiDefaults guiDefaults;
    /**
     * Constructor for GuiContext.
     */
    public XuluxContext() {
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
    public static XuluxContext getInstance() {
        if (instance == null) {
            instance = new XuluxContext();
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

    public static Dictionary getDictionary() {
      if (dictionary == null) {
        dictionary = new Dictionary();
      }
      return dictionary;
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
     * @return the guidefaults object. It will also initialize the defaults on instantiation
     */
    public static GuiDefaults getGuiDefaults() {
        if (guiDefaults == null) {
            guiDefaults = new GuiDefaults();
            XuluxContext.getInstance().initializeGuiDefaults();
        }
        return guiDefaults;
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

}
