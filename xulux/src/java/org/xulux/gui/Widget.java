/*
 $Id: Widget.java,v 1.1 2003-12-18 00:17:21 mvdb Exp $

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
package org.xulux.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.rules.IRule;
import org.xulux.rules.Rule;
import org.xulux.utils.NyxCollectionUtils;

/**
 * A widget is a independ way of representing gui objects.
 * For now swing specific, we need an xml config somewhere
 * to say how we should initialize it, etc.
 *
 * @todo Use introspection, because this is getting to big and too
 * specific as a generic Widget...
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Widget.java,v 1.1 2003-12-18 00:17:21 mvdb Exp $
 */
public abstract class Widget implements Serializable
{

    private String field;
    private boolean enabled = true;
    protected boolean visible = true;
    private boolean immidiate = false;
    protected boolean initialized = false;
    private boolean skip = false;

    protected Object value;
    protected Object previousValue;

    private ArrayList rules;
    private ApplicationPart part;

    private List dependencies;

    private WidgetRectangle rectangle;

    private String name;

    private String text;

    protected HashMap properties;

    private boolean required = false;

    private boolean isRootWidget = false;
    /**
     * Valid value defaults to true!
     */
    private boolean validValue = true;

    /**
     * Ignore the use variable when setting values.
     * , not when getting values.
     */
    private boolean ignoreUse = false;
    
    protected Log log = LogFactory.getLog(Widget.class);
    
    /**
     * Is the widget initializing ? 
     */
    protected boolean initializing;


    /**
     * The parent widget, if there is one
     */
    Widget parent;

    /**
     * Specfies if the widget is refreshing
     * so you call setter from refresh
     * without getting in an infinite loop
     */
    protected boolean isRefreshing;

    /**
     * Holds the prefix of the field
     */
    private String prefix;

    /**
     * The type of widget as defined in the
     * GuiDefaults
     */
    private String widgetType;

    /**
     * A list of registered listeners.
     */
    private List listenerList;


    public Widget(String name)
    {
        setName(name);
    }

    public void setPart(ApplicationPart part)
    {
        this.part = part;
    }

    public ApplicationPart getPart()
    {
        return this.part;
    }

    /**
     * No state change should take place if the widget is already in a certain state.
     *
     * @param enable false disables it, true, enables it
     */
    public void setEnable(boolean enable)
    {
        if (this.enabled != enable)
        {
            this.enabled = enable;
            if (initialized)
            {
                refresh();
            }
        }
        this.enabled = enable;
    }

    /**
     * Is the widget disabled ?
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * No state change should take place if the widget is already in a certain state.
     * @param show true - shows the widget
     */
    public void setVisible(boolean visible) {
        if (this.visible != visible) {
            this.visible = visible;
            if (initialized) {
                refresh();
            }
        }
        this.visible = visible;
    }

    /**
     * Is the widget hidden
     */
    public boolean isVisible()
    {
        return this.visible;
    }
    /**
     * Sets if the widget is immidiate
     * Immidiate means that it fires actions on everything you do
     * in the widget
     * Eg : in an entry field typing a letter will automacially fire an event..
     */
    public void setImmidiate(boolean immidiate)
    {
        if (this.immidiate != immidiate)
        {
            this.immidiate = immidiate;
            if (initialized)
            {
                refresh();
            }
        }
        this.immidiate = immidiate;
    }

    /**
     * Is a widget immidiate  ?
     */
    public boolean isImmidiate()
    {
        return this.immidiate;
    }

    /**
     * @return if this field should be skipped
     */
    public boolean isSkip()
    {
        return this.skip;
    }

    /**
     * When tabbing to the screen, don't give focus to this
     * widget, only allow selection via mouse
     * @param skip
     */
    public void setSkip(boolean skip)
    {
        if (this.skip != skip)
        {
            this.skip = skip;
            if (initialized)
            {
                refresh();
            }
        }
        this.skip = skip;
    }

    /**
     * Destroy a widget. Frees up all resources taken by it.
     */
    public abstract void destroy();

    /**
     * Returns the "native" widget. could be a swing, swt or other type of component
     */
    public abstract Object getNativeWidget();


    /**
     * Initializes the widget, based on the current settings
     */
    public abstract void initialize();

    /**
     * sets the position of the current widget
     * (on the parent..)
     * @param x - the x position on the parant
     * @param y - the y position on the parent
     */
    public void setPosition(int x, int y) {
        getRectangle().setPosition(x,y);
    }
    /**
     * Sets the size of the current widget
     * @param width - the width of the widget
     * @param height - the height of the widget
     */
    public void setSize(int width, int height) {
        getRectangle().setSize(width, height);
    }

    /**
     *
     * @return the rectangle for the widget, never null.
     */
    public WidgetRectangle getRectangle() {
        if (this.rectangle == null) {
            this.rectangle = new WidgetRectangle();
        }
        return this.rectangle;
    }

    /**
     * refreshes the widget.
     */
    public abstract void refresh();

    /**
     * Returns the field.
     * @return String
     */
    public String getField() {
        return field;
    }

    /**
     * Registers a rule in the widget
     * and sets the owner if the rule extends Rule
     *
     * @param rule
     */
    public void registerRule(IRule rule) {
        if (rules == null) {
            rules = new ArrayList();
        }
        if (rule instanceof Rule) {
            ((Rule)rule).setOwner(this);
        }
        rules.add(rule);
    }

    /**
     *
     * @return a list with the rules for that widget
     */
    public ArrayList getRules() {
        return rules;
    }

    /**
     * To make equals checking a bit easier.
     * This checks to see if the passed in object
     * is the same widget as this one, based on naming
     * If you pass in a string with the correct name and prefix,
     * it will return true, else there must be an exact
     * object match.
     *
     * @param object (normally a string)
     */
    public boolean equals(Object object)
    {
        if (object == null) {
            return false;
        }
        if (object instanceof String)
        {
            String val = ((String)object).toLowerCase();
            int dotIndex = val.indexOf('.');
            String oPrefix = null;
            String oName = null;
            if (dotIndex != -1) {
                oPrefix = val.substring(0,dotIndex);
                oName = val.substring(dotIndex+1);
            } else {
                oName = val;
            }
            if (getName().toLowerCase().equals(oName)) {
                if (oPrefix != null) {
                    // if oPrefix is present,
                    // see if it is equals to.
                    if (getPrefix() != null) {
                        return oPrefix.equals(getPrefix().toLowerCase());
                    }
                } else  {
                    // if oPrefix is null getPrefix should
                    // return null too.
                    return getPrefix() == null;
                }
            }
            return false;
        }
        else
        {
            // if not a string, we want the exact same object given to us.
            return object == this;
        }
    }

    /**
     * Override this method when the widget can
     * contains child widgets.
     * You don't have to override this one when
     * the widget can be a root widgets, since that
     * assumes it can contain children.
     *
     * @return true if the widget can contain children
     */
    public boolean canContainChildren()
    {
        // prevents the override (see javadoc)
        return canBeRootWidget();
    }

    /**
     * Checks if this widgets is defined in the root
     * of the part.
     * eg those are elements that are directly under
     * the the main part.
     */
    public boolean isRootWidget()
    {
        return this.isRootWidget;
    }

    /**
     * Specifies if this widget is a part rootWidget
     *
     * @param isRootWidget
     */
    public void setRootWidget(boolean isRootWidget)
    {
        this.isRootWidget = true;
    }

    /**
     * Specifies if this widget can be a root
     * widget, which means it can live on it's
     * own without any parent.
     * Returns false by default, so you have to
     * override it when it can (normally only
     * windows can though)
     *
     * @return true when it can
     */
    public boolean canBeRootWidget()
    {
        return false;
    }

    /**
     * Doesn't do anything by default.
     * Override this when there canHaveChildren
     * returns true.
     *
     */
    public void addChildWidget(Widget widget)
    {
        return;
    }

    /**
     *
     * @return the list of childwidgets. Defaults to null
     */
    public ArrayList getChildWidgets()
    {
        return null;
    }

    /**
     * Set the name of the widget.
     *
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *
     * @return the name of the widget
     */
    public String getName()
    {
        return this.name;
    }

    /**
     *
     * @return The prefix of the field or null
     *          when there is no prefix
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Set the prefix of the widget
     *
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Set the property to the specified value
     * or when value is null clear the property
     * if it exists.
     * It will also refresh the widget so setProperty
     * so changes in properties will be reflected
     * on screen.
     * It will do nothing when the value it currently
     * knows is the same as the value passed in.
     * If the key is null, it will return immidiately
     *
     * @param key  - case insensitive value
     * @param value - case insensitive value
     */
    public void setProperty(String key, String value)
    {
        if (key == null) {
            return;
        }
        if (properties == null)
        {
            properties = new HashMap();
        }
        if (value == null && key != null) {
            properties.remove(key);
        }
        //System.out.println(getName()+"key : "+key+"  value : "+value);
        String oldValue = (String)properties.get(key);
        if (oldValue != null) {
            if (oldValue.equalsIgnoreCase(value)) {
                // nothing has changed, so we'll return
                return;
            }
        }
        properties.put(key.toLowerCase(), value);
        if (key.equals("depends")) {
            List depList = NyxCollectionUtils.getListFromCSV(value);
            if (this.dependencies == null) {
                this.dependencies = depList;
            } else {
                this.dependencies.add(depList);
            }
        } else if (key.equals("enabled.depends")) {
            addDependency(value);
        } else if (key.equals("required"))  {
            setRequired((value.equalsIgnoreCase("true")?true:false));
        } else if (key.equals("enabled")) {
            setEnable((value.equalsIgnoreCase("true")?true:false));
        } else if (key.equals("visible")) {
            setVisible((value.equalsIgnoreCase("true")?true:false));
        } else if (key.equals("use")) {
            setField(value);
        } else if (key.equals("position")) {
            int x = -1;
            int y = -1;
            try {
                StringTokenizer stn = new StringTokenizer(value, ",");
                String xStr = stn.nextToken().trim();
                String yStr = stn.nextToken().trim();
                x = Integer.parseInt(xStr);
                y = Integer.parseInt(yStr);
            }
            catch (Exception nse) {
                if (log.isWarnEnabled()) {
                    log.warn("Parsing error with property "+key+" with value " + value);
                    log.warn("Widget : " + this.getName());
                }
                return;
            }
            setPosition(x,y);
        } else if (key.equals("size")) {
            int width = -1;
            int height = -1;
            try {
                StringTokenizer stn = new StringTokenizer(value, ",");
                String xStr = stn.nextToken().trim();
                String yStr = stn.nextToken().trim();
                width = Integer.parseInt(xStr);
                height = Integer.parseInt(yStr);
            }
            catch (Exception nse) {
                if (log.isWarnEnabled()) {
                    log.warn("Parsing error with property "+key+" with value " + value);
                    log.warn("Widget : " + this.getName());
                }
                return;
            }
            setSize(width,height);
        }
        // refresh the widget when it is initialized
        if (initialized) {
            refresh();
        }
    }
    
    /**
     * You can not use this HashMap to change properties,
     * since it is a clone.
     * You need to use setProperty for that.
     *
     * @return the HashMap with the properties
     */
    public HashMap getProperties()
    {
        return (properties!=null)?(HashMap)properties.clone():new HashMap();
    }

    /**
     * Sets the field.
     * @param field The field to set
     */
    public void setField(String field)
    {
        this.field = field;
    }

    /**
     * Removes all rules from this widget.
     * Normally only used when cleaning up
     * after us.
     */
    protected void removeAllRules()
    {
        if (rules != null)
        {
            rules.clear();
        }
    }

    /**
     * Defaults to getName()
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }


    /**
     *
     * @return the value from the native widget
     */
    public abstract Object getGuiValue();

    /**
     *
     * @return the value associated with this widget
     */
    public Object getValue()
    {
        return this.value;
    }

    public Object getPreviousValue()
    {
        return this.previousValue;
    }

    public void setValue(Object value)
    {
        this.previousValue = this.value;
        this.value = value;
        if (initialized)
        {
            refresh();
        }
    }

    /**
     * Clears the field
     */
    public void clear()
    {
    }


    /**
     * Returns the required.
     * @return boolean
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * Sets the required.
     * @param required The required to set
     */
    public void setRequired(boolean required)
    {
        this.required = required;
        if (this.initialized)
        {
            refresh();
        }
    }

    /**
     * Return the specified property.
     * Use this method or do a getProperties().get(key.toLowerCase());
     * , since all keys are stored in lowercase.
     * @param key the property to get. This is case insensitive.
     * @return the value found or null when no value is found
     */
    public String getProperty(String key)
    {
        if (properties != null && key != null)
        {
            return (String) properties.get(key.toLowerCase());
        }
        return null;
    }

    /**
     * Adds the fields that depend on this field. If it already is registered,
     * no action is taken.
     * It is used to optimize the rules engine so it
     * only fires rules when needed.
     * It will only fire the pre rules of the fields mentioned
     * here.
     * If no dependencies are specified, it will just call all pre rules in the current part..
     *
     * @param name - the name of the widget. If you just specify one name, it will
     *                assume the current part to receive the events. If you want a field
     *                to be updated in another part, use the format partname.fieldname
     */
    public void addDependency(String name)
    {
        boolean add = false;
        if (dependencies == null)
        {
            dependencies = new ArrayList();
            add = true;
        }
        int index = -1;
        if (!add)
        {
            index = dependencies.indexOf(name);
            if (index == -1)
            {
                add = true;
            }
        }
        if (add)
        {
            dependencies.add(name);
        }
        else
        {
            dependencies.set(index, name);
        }
    }

    /**
     * @return if the widget has dependencies..
     */
    public boolean hasDependencies()
    {
        return (dependencies != null && dependencies.size() > 0);
    }

    /**
     * Returns the dependencies
     */
    public List getDependencies()
    {
        return dependencies;
    }


    /**
     * Updates the widget because another widget
     * has been updated.
     * @param widget
     */
    public void updateWidget(Widget widget) {
        if (!hasDependencies()) {
            return;
        }
        if (getDependencies().contains(widget.getName())) {
            refresh();
        }
    }

    /**
     * Specifies if the widget is currently
     * refreshing. Check this to prevent
     * infinite loops when another field
     * is calling this widget again.
     *
     * @return true when currently refreshing and false when not
     */
    public boolean isRefreshing()
    {
        return isRefreshing;
    }

    /**
     * Set the focus to this widget
     */
    public abstract void focus();

    /**
     *
     * @return the parent widget or null when there is
     *          no parent.
     */
    public Widget getParent() {
        return this.parent;
    }

    /**
     * Sets the parent of this widget.
     * @param parent
     */
    public void setParent(Widget parent) {
        this.parent = parent;
    }
    /**
     * @return if the GUI value of the current field is empty or not.
     */
    public abstract boolean isValueEmpty();

    /**
     *
     * @return if the widget CAN contain a value
     */
    public abstract boolean canContainValue();

    /**
     * Set the widget type as defined in GuiDefaults.xml
     *
     * @param widgetType the widgetType
     */
    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    /**
     *
     * @return - the widgetType as defined in GuiDefaults.xml
     */
    public String getWidgetType() {
        return this.widgetType;
    }

    /**
     * process custom initialization classes
     * This should ONLY be called after the widget has
     * finished initializing
     */
    protected void processInit() {
        List list = ApplicationContext.getInstance().getWidgetInitializers(getWidgetType());
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((IWidgetInitializer) it.next()).initialize(this);
        }
    }

    /**
     * process custom destroy classes.
     * This should only be called before the widget gets
     * destroyed.
     */
    protected void processDestroy() {
        List list = ApplicationContext.getInstance().getWidgetInitializers(getWidgetType());
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((IWidgetInitializer)it.next()).destroy(this);
        }
    }

    /**
     *
     * @return if the widget contains a valid value
     *          it defaults to true!
     */
    public boolean isValidValue() {
        return this.validValue;
    }

    /**
     * Call this from your code when you think there is an
     * invalid value specified in a widget.
     * Normally just called from rules.
     *
     * @param validValue
     */
    public void setValidValue(boolean validValue) {
        this.validValue = validValue;
    }

    /**
     * Add a nyx listener to the widget.
     *
     * @param listener
     */
    public void addNyxListener(NyxListener listener) {
        if (listenerList == null) {
            listenerList = new ArrayList();
        }
        listenerList.add(listener);
    }

    /**
     * Notifies the listeners that an event has occured.
     */
    public void notifyListeners(NyxEvent event) {
        if (listenerList == null) {
            return;
        }
        Iterator it = listenerList.iterator();
        while (it.hasNext()) {
            NyxListener listener = (NyxListener)it.next();
            listener.processEvent(event);
        }

    }


    /**
     * Set if the use should be ignored. Defaults
     * to false.
     * @param ignoreUse
     */
    public void ignoreUse(boolean ignoreUse) {
        this.ignoreUse = ignoreUse;
    }

    /**
     *
     * @return if the use should be ignored or not
     */
    public boolean isUseIgnored() {
        return this.ignoreUse;
    }
    
    /**
     * Use this method if you want eg initial data set
     * 
     * @return if the widget is initializing or not.
     */
    public boolean isInitializing() {
        return this.initializing;
    }
}
