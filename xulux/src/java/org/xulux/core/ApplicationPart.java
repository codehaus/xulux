/*
   $Id: ApplicationPart.java,v 1.5 2004-04-15 00:05:04 mvdb Exp $
   
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.dataprovider.Dictionary;
import org.xulux.gui.INativeWidgetHandler;
import org.xulux.gui.IParentWidgetHandler;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.rules.DefaultPartRule;
import org.xulux.rules.IRule;
import org.xulux.rules.impl.PartRequestImpl;
import org.xulux.rules.impl.WidgetRequestImpl;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.utils.BooleanUtils;
import org.xulux.utils.Translation;

/**
 * An Application is a part of the application
 * which will contain a "block" of data/gui  that is in some
 * way related with each other (eg a form, table, window)
 *
 * It will maintain 2 beans for every bean : the original
 * and the copy on the screen.
 *
 *
 * @todo Add refresh possibilities to the application parts,
 * so your are able to replace the original bean and determine if
 * the "bean refresh" changed the bean or not.
 * Esp. usefull when concurrent changes of beans occur by different
 * people (although this would be merely convenience, since other code
 * should handle these kind of situation..).
 * @todo Fix naming of field. It is used everywhere with different meanings.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPart.java,v 1.5 2004-04-15 00:05:04 mvdb Exp $
 */
public class ApplicationPart {

    /**
     * Is the part destroyed
     */
    private boolean destroyed;

    /**
     * the log instance
     */
    private static Log log = LogFactory.getLog(ApplicationPart.class);

    /**
     * The original bean
     */
    private Object bean;

    /**
     * The mapping of the bean
     */
//    private IMapping mapping;

    /**
     * The parentwidget of this applicationpart
     */
    private Object parentWidget;

    /**
     * The widgets contained in this part.
     */
    private WidgetList widgets;

    /**
     * The partrules.
     */
    private ArrayList partRules;

    /**
     * If the part is activated or not
     */
    private boolean activated;

    /**
     * If all rules should be stopped or not
     */
    private boolean stopRules = false;

    /**
     * Specifies if a window is currently
     * activating itself.
     */
    private boolean isActivating = false;

    /**
     * The name of the part
     */
    private String partName;

    /**
     * The session of the part.
     */
    private SessionPart session;

    /**
     * Not used
     */
    public static final int NO_STATE = 0;
    /**
     * Not used
     */
    public static final int INVALID_STATE = 1;
    /**
     * Not used
     */
    public static final int OK_STATE = 2;
    /**
     * Not used
     */
    private int state = NO_STATE;
    /**
     * The fieldeventhandler
     */
    private NyxListener fieldEventHandler;

    /**
     * The list of translation files.
     */
    private ArrayList translationList;

    /**
     * Contains the parentpart if there is one
     *
     */
    private ApplicationPart parentPart;

    /**
     * The provider for the part
     */
    private String provider;

    /**
     * Constructor for GuiPart.
     */
    public ApplicationPart() {
    }

    /**
     * Used for single bean representation
     * @param bean the bean to be used in the part
     * @todo make this a dataprovider class!
     */
    public ApplicationPart(Object bean) {
        this();
        this.bean = bean;
        if (bean != null) {
            // @todo remove this line, since it should be useless.
            Dictionary.getInstance().getMapping(bean.getClass());
        }
    }

    /**
     * @return Are there any changes in the part
     */
    public boolean isDirty() {
        return isDirty();
    }

    /**
     * Currently not functional.
     * It should compare the bean value and the
     * current value in the field. Some introspection
     * should be done to turn the field value into a
     * correct bean (string to string is not a problem
     * though).
     * @todo isDirty() need to move to dataprovider
     * @param field is ignored
     * @return always false
     */
    public boolean isDirty(String field) {
        return false;
    }

    /**
     * Returns if the field specified is the one currently
     * being processed. This way you can quicly determine in
     * your code if the rule
     * @todo remove this method ?
     * @param field is ignored
     * @return always returns false.
     */
    public boolean isCurrentField(String field) {
        return false;
    }


    /**
     * Set the gui value
     * @param name the widget name
     * @param value the value to set
     */
    public void setGuiValue(String name, Object value) {
        Widget widget = (Widget) widgets.get(name);
        if (widget == null) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot find widget " + name + " to set value on");
            }
        }
        else {
            widget.setValue(value);
        }
    }

    /**
     * @param widgetName the name of the widget to get the value from
     * @return the current value of the specified field
     */
    public Object getGuiValue(String widgetName) {
        Widget widget = (Widget) widgets.get(widgetName);
        if (widget == null) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot find widget " + widgetName + " to get value from");
            }
            return null;
        }
        return widget.getValue();
    }

    /**
     * @param name the name of the widget
     * @return the previousvalue of the widget
     */
    public Object getPreviousGuiValue(String name) {
        Widget widget = (Widget) widgets.get(name);
        if (widget == null) {
            if (log.isWarnEnabled()) {
                log.warn("Cannot find widget " + name + " to get previous value from");
            }
            return null;
        }
        return widget.getPreviousValue();
    }

    /**
     * @param widgetName the name of the widget to get.
     * @return the widget that is connected to the field,
     * null if not found or no widgets are present.
     */
    public Widget getWidget(String widgetName) {
        if (widgets != null) {
            return (Widget) widgets.get(widgetName);
        }
        return null;

    }

    /**
     * @return the plain bean
     */
    public Object getBean() {
        return this.bean;
    }

    /**
     * @return the name of the part
     */
    public String getName() {
        return this.partName;
    }

    /**
     * Sets the name and registers the part..
     * @param name the name of the part
     * @todo This is buggy, since a part doesn't have to have a unique name..
     */
    public void setName(String name) {
        this.partName = name;
        //ApplicationContext.getInstance().registerPart(this);
    }

    /**
     * The parent widget for all child widgets to be added
     * @param widget the widget to set the parent for
     */
    public void setParentWidget(Object widget) {
        this.parentWidget = widget;
    }

    /**
     * Adds a widget to the parent
     * Also replaces it, but it is cleaner to have a seperate
     * call for that (that is assuming the widgets are already there
     * by default..)
     *
     * @param widget the widget to add to the part
     */
    public void addWidget(Widget widget)
    {
        if (widget == null) {
            return;
        }
        if (widgets == null) {
            widgets = new WidgetList();
        }
        ((Widget) widget).setPart(this);
        ((Widget) widget).setRootWidget(true);
        widgets.add(widget);
    }

    /**
     * Removes a widget. It will call the destroy
     * method on the widget and if the widget
     * is calling this, it will remove it from
     * the application part widget registry.
     * TODO : this method should automatically be called
     * @param widget the widget to remove.
     * @param caller - the caller of the object. Always pass
     *         <code>this</code>.
     */
    public void removeWidget(Widget widget, Object caller) {
        if (widget == null) {
          return;
        }
        if (caller instanceof Widget) {
            widgets.remove(widget);
            return;
        }
        // set the parent to null..
        widget.setParent(null);
        widget.destroy();
        widgets.remove(widget);
    }
    /**
     * Replaces the field with the specified widget
     *
     * @param widget the new widget
     * @param widgetName the widget to replace
     */
    public void setWidget(Object widget, String widgetName) {
        int index = widgets.indexOf(widgetName);
        if (index != -1) {
            widgets.set(index, widget);
        }
        else {
            widgets.add(widget);
        }
    }
    /**
     * If the root widget is null,
     * the part is contained in itself
     * @return null when the part is standalone, else the rootwidget (which is
     *          normally a native widget!
     */
    public Object getRootWidget() {
        return parentWidget;
    }

    /**
     * NOTE : Is actually not ding anything
     *
     * Initialize the part, which makes the gui visible,
     * processes fields, etc.
     * Can only be called from the DefaultPartRule
     * @todo This is pretty horrible way of doing things
     *       Better ideas appreciated..
     * @param caller - the caller
     */
    public void initialize(Object caller) {
        if (log.isDebugEnabled()) {
            log.debug("Initializing...");
        }
        if (!(caller instanceof DefaultPartRule)) {
            return;
        }
    }

    /**
     * Refreshes all widgets
     */
    public void refreshAllWidgets() {
        Iterator it = widgets.iterator();
        while (it.hasNext()) {
            Widget widget = (Widget) it.next();
            widget.refresh();
        }
    }

    /**
     * Registers an ApplicationPart rule
     * @param rule the rule to register for the part
     */
    public void registerRule(IRule rule) {
        // the defaultrule should stay last..
        initializePartRules();
        partRules.add(partRules.size() - 1, rule);
        rule.registerPartName(this.getName());
    }

    /**
     * Registers a field rule in the field.
     * @param rule the rule to register
     * @param widgetName the name of the widget
     */
    public void registerFieldRule(IRule rule, String widgetName) {
        Object tmp = widgets.get(widgetName);
        if (tmp instanceof Widget) {
            Widget widget = (Widget) tmp;
            widget.registerRule(rule);
        }
    }

    /**
     * @return if the part is currently activating or not
     */
    public boolean isActivating() {
        return this.isActivating;
    }
    /**
     * Activates the part
     * Which means setting starting rule processing
     * The defaultrule should initialize the view.
     */
    public void activate() {
        if (activated) {
            return;
        }
        activated = true;
        isActivating = true;
        // initialize the gui system.
        XuluxContext.getInstance().getNYXToolkit().initialize();
        if (getRules() == null) {
            if (log.isDebugEnabled()) {
                log.debug("No part rules to process");
            }
        }
        else {
            PartRequestImpl req = new PartRequestImpl(this, PartRequest.NO_ACTION);
            XuluxContext.fireRequest(req, XuluxContext.PRE_REQUEST);
        }
        if (widgets != null) {
            Iterator it = widgets.iterator();
            while (it.hasNext()) {
                Widget widget = (Widget) it.next();
                // initialize the parent first!
                if (widget.getParent() != null) {
                    widget.getParent().initialize();
                }
                //widget.initialize();
                if (getRootWidget() != null) {
                    // only widgets without parents should be added
                    if (widget.getParent() == null) {
                        XuluxContext.getInstance().getNativeWidgetHandler().addWidgetToParent(widget, getRootWidget());
                    }
                }
                //                if (widget.canBeRootWidget() ||
                //                     widget.canContainChildren())
                //                {
                widget.initialize();
                WidgetRequestImpl req = new WidgetRequestImpl(widget, PartRequest.NO_ACTION);
                XuluxContext.fireFieldRequest(widget, req, XuluxContext.PRE_REQUEST);
                //                }
            }
        }
        if (getRootWidget() != null) {
          XuluxContext.getInstance().getNativeWidgetHandler().refresh(getRootWidget());
        }
        isActivating = false;
    }

    /**
     * Runs all the pre rules again..
     */
    public void runPreRules() {
        if (getRules() != null) {
            PartRequestImpl req = new PartRequestImpl(this, PartRequest.NO_ACTION);
            XuluxContext.fireRequest(req, XuluxContext.PRE_REQUEST);
        }
        if (widgets != null) {
            Iterator it = widgets.iterator();
            while (it.hasNext()) {
                Widget widget = (Widget) it.next();
                if (widget.canBeRootWidget() || widget.canContainChildren()) {
                    widget.initialize();
                    WidgetRequestImpl req = new WidgetRequestImpl(widget, PartRequest.NO_ACTION);
                    XuluxContext.fireFieldRequest(widget, req, XuluxContext.PRE_REQUEST);
                }
            }
        }
    }

    /**
     * @return the part rules
     */
    public ArrayList getRules() {
        initializePartRules();
        return partRules;
    }

    /**
     * Initializes the partrules
     * and adds the defaultrule.
     */
    private void initializePartRules() {
        if (partRules == null) {
            partRules = new ArrayList();
            // add the default rule..
            partRules.add(new DefaultPartRule());
        }
    }

    /**
     * @param widgetName the name of the widget
     * @return the rules of the widget specified in the name
     */
    public List getRules(String widgetName) {
        Widget widget = getWidget(widgetName);
        if (widget != null) {
            return widget.getRules();
        }
        return null;
    }

    /**
     * Override arraylist so equals actually works..
     */
    public class WidgetList extends ArrayList {

        /**
         *
         * @param widgetName the widget that other fields depend on
         * @return a collection with widgets that reference the
         *          specified field or null when no fields are found
         */
        public Collection getWidgetsWithField(String widgetName) {
            Collection col = new ArrayList();
            Iterator it = iterator();
            while (it.hasNext()) {
                Widget w = (Widget) it.next();
                if (w.getField() != null) {
                    if (w.getField().equalsIgnoreCase(widgetName)
                        || w.getField().startsWith("?" + widgetName)) {
                        col.add(w);
                    }
                }
            }
            if (col != null && col.size() == 0) {
                col = null;
            }
            return col;
        }

        /**
         * @see java.util.List#indexOf(Object)
         */
        public int indexOf(Object elem) {
            if (elem == null) {
                return -1;
            }
            for (int i = 0; i < size(); i++) {
                Object data = get(i);
                if (data instanceof Widget && elem instanceof String) {
                    if (data != null && ((Widget) data).equals(elem)) {
                        return i;
                    }
                }
                else if (data != null && data.equals(elem)) {
                    return i;
                }
            }
            return -1;

        }

        /**
         * @param name the name of the widget
         * @return the widget with the specified name
         */
        public Widget get(String name) {
            int index = indexOf(name);
            if (index != -1) {
                return (Widget) get(index);
            }
            return null;
        }
    }

    /**
     * @return the list of widgets of this part
     */
    public WidgetList getWidgets() {
        return widgets;
    }

    /**
     * Returns the session.
     * @return SessionPart
     */
    public SessionPart getSession() {
        if (session == null) {
            session = new SessionPart();
        }
        return session;
    }

    /**
     * Clears all fields
     */
    public void clear() {
        Iterator it = widgets.iterator();
        while (it.hasNext()) {
            clear(((Widget) it.next()).getName());
        }
    }

    /**
     * Clears the specified field
     * @param widgetName the name of the widget to clear
     */
    public void clear(String widgetName) {
        Widget widget = widgets.get(widgetName);
        if (widget != null) {
            widget.clear();
        }
        else {
            if (log.isWarnEnabled()) {
                log.warn("Widget " + widgetName + " does not exist");
            }
        }
    }

    /**
     * Resets all fields to the original value
     */
    public void reset() {
        Iterator it = widgets.iterator();
        while (it.hasNext()) {
            reset(((Widget) it.next()).getName());
        }
    }

    /**
     * Resets the specified field to the original value
     * @param widgetName the widget to reset the value for
     */
    public void reset(String widgetName) {
        Widget widget = widgets.get(widgetName);
        widget.setValue(null);
    }

    /**
     * Destroys the applicationPart
     * (does a lot of cleanups)
     * After the destroy the object is not usable anymore.
     * Maybe a lot of unecessary code, but you never know
     * when swing stuff still stays in memory..
     */
    public void destroy() {
        destroyed = true;
        if (NyxEventQueue.getInstance() != null) {
            NyxEventQueue.getInstance().clearAccepted();
            NyxEventQueue.getInstance().holdEvents(false);
        }
        if (session != null) {
            getSession().clear();
            session = null;
        }
        bean = null;
        activated = false;
        if (widgets != null) {
            ArrayList widgetList = (ArrayList) widgets.clone();
            Iterator it = widgetList.iterator();
            while (it.hasNext()) {
                ((Widget) it.next()).destroy();
            }
            widgets.clear();
            widgetList.clear();
            widgetList = null;
            widgets = null;
        }
        if (partRules != null) {
            Iterator it = partRules.iterator();
            while (it.hasNext()) {
                ((IRule) it.next()).destroy();
            }
            partRules.clear();
            partRules = null;
        }
        System.out.println("ParentWidget : " + this.parentWidget);
        System.out.println("Root widget : " + this.getRootWidget());
        if (getRootWidget() != null) {
            IParentWidgetHandler handler = XuluxContext.getInstance().getParentWidgetHandler();
            handler.destroy(parentWidget);
            INativeWidgetHandler h = XuluxContext.getInstance().getNativeWidgetHandler();
            System.out.println("Refreshing widget...");
            h.refresh(getRootWidget());
        }
        parentWidget = null;
        XuluxContext.getInstance().removePart(getName());
        this.parentPart = null;
    }

    /**
     * Sets focus to the field specified
     * @param name - the name of the fiel
     */
    public void select(String name) {
        Widget widget = getWidget(name);
        if (widget != null) {
            widget.focus();
        }
    }

    /**
     * Stops all the rules that are currently processing and
     * doesn't process any rules that are waiting to be processed
     */
    public void stopAllRules() {
        stopRules = true;
        getSession().setValue("stopAllRules", String.valueOf(stopRules));
    }

    /**
     * Checks to see if all rules should stop processing.
     * @param caller the caller of needToStopAllRules.
     *         This prevents the applicationcontext from stopping all rules.
     * @return true if all rules need to be stopped
     */
    public boolean needToStopAllRules(Object caller) {
        boolean retValue = stopRules;
        if (caller instanceof XuluxContext) {
            stopRules = false;
        }
        else {
            stopRules = false;
            retValue = BooleanUtils.toBoolean(getSession().getValue("stopAllRules"));
            getSession().remove("stopAllRules");
        }
        return retValue;
    }

    /**
     * Returns the state.
     * @return int
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the state.
     * @param state The state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Returns a new instance of the fieldEventHandler.
     * You can override this in the applicationpart
     * xml by adding the listener tag with the class
     * specified.
     * @param widget the widget to get the fieldeventhandler for
     * @return PrePostFieldListener
     */
    public NyxListener getFieldEventHandler(Widget widget) {
        NyxListener listener = null;
        if (fieldEventHandler != null) {
            try {
                listener = (NyxListener) fieldEventHandler.getClass().newInstance();
                listener.setWidget(widget);
            }
            catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("Field event Handler for widget " + widget + " cannot be initialized");
                }
            }
        }
        else {
            // we try the one in GuiDefaults (if used that is..)
            listener = XuluxContext.getInstance().getFieldEventHandler(null);
            listener.setWidget(widget);
        }
        return listener;
    }

    /**
     * Sets the fieldEventHandler.
     * @param fieldEventHandler The fieldEventHandler to set
     */
    public void setFieldEventHandler(NyxListener fieldEventHandler) {
        this.fieldEventHandler = fieldEventHandler;
    }

    /**
     * Add a translation object to the list of possible
     * resources where translations can be fetched from.
     * This is used mainly when including parts in a part,
     * so you don't have to put translations in more than
     * one property file, if already defined somwehere else.
     *
     * @param url - if the url already exists it will not
     *               add it to the translationlist
     * @param type - not supported yet, although should be handled by url.
     */
    public void addTranslation(String url, String type) {
        if (translationList == null) {
            translationList = new ArrayList();
        }
        Translation translation = new Translation(url, type);
        if (translationList.contains(translation)) {
            if (log.isDebugEnabled()) {
                log.debug("Already have translationlist in my cache, skipping " + url);
            }
            return;
        }
        translationList.add(new Translation(url, type));
    }

    /**
     * @return The list of translation urls.
     */
    public List getTranslationList() {
        return this.translationList;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Name : " + getName() + " " + super.toString();
    }

    /**
     * Refreshes all fields that reference the field
     * used by the passed in widget
     * When field is null nothing will be done.
     * @param widget the widget to refresh the fields for
     */
    public void refreshFields(Widget widget) {
        String field = widget.getField();
        refreshFields(field , widget);
    }

    /**
     * Refreshes widgets which have a pointer to the caller in their use.
     *
     * @param caller the caller of refreshwidgets (normally pass in this)
     */
    public void refreshWidgets(Widget caller) {
        if (caller == null) {
            return;
        }
        refreshFields(caller.getName(), caller);
    }

    /**
     * Utility method that refreshes all fields that have pointers to
     * a field or depend on a field
     *
     * @param name the widget name to find
     * @param widget - the source widget that called the refresh
     */
    protected void refreshFields(String widgetName, Widget widget) {
        if (widgetName == null) {
            return;
        }
        Collection col = widgets.getWidgetsWithField(widgetName);
        if (col == null) {
            return;
        }
        Iterator it = col.iterator();
        while (it.hasNext()) {
            Widget w = (Widget) it.next();
            if (w != widget) {
                w.refresh();
            }
        }
    }

    /**
     * Updates the widgets that depends on the widget
     * passed in.
     * @param widget the widget that other depends on
     */
    public void updateDependandWidgets(Widget widget) {
        if (widget == null || getWidgets() == null) {
            return;
        }
        Iterator it = getWidgets().iterator();
        while (it.hasNext()) {
            Widget w = (Widget) it.next();
            if (w != widget) {
                w.updateWidget(widget);
            }
        }
    }

    /**
     * Set the parent part. This way a part knows that
     * the bean provided should be passed to the parent.
     * @param part the applicationpart that serves as a parent
     */
    public void setParentPart(ApplicationPart part) {
        this.parentPart = part;
    }

    /**
     *
     * @return the parentpart or null when there is none
     */
    public ApplicationPart getParentPart() {
        return this.parentPart;
    }

    /**
     * @return if the part has been destroyed
     */
    public boolean isPartDestroyed() {
        return destroyed;
    }
    /**
     * Set the provider by name
     * @param provider the provider name
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    /**
     * @return the provider name
     */
    public String getProvider() {
        return this.provider;
    }
}
