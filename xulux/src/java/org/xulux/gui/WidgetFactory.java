/*
   $Id: WidgetFactory.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
   
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
package org.xulux.gui;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.xulux.core.ApplicationContext;
import org.xulux.rules.IRule;

/**
 * Factory to create the widget class..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetFactory.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
 */
public class WidgetFactory {

    /**
     * Constructor for WidgetFactory.
     */
    protected WidgetFactory() {
    }

    /**
     * Creates a widget from the widget Registry.
     *
     * @param type the type of widget
     * @param name the name of the widget
     * @return the widget or null when no widget could be created
     */
    public static Widget getWidget(String type, String name) {
        Class clazz = ApplicationContext.getInstance().getWidget(type);
        Widget instance = null;
        if (clazz != null) {
            try {
                Constructor constructor = clazz.getConstructor(new Class[] { String.class });
                instance = (Widget) constructor.newInstance(new String[] { name });
                instance.setWidgetType(type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Creates a popmenu from a list of buttons.
     *
     * @param list the list
     * @param name the name of the menu
     * @return a popmenu from a list with button widgets
     */
    public static Widget getPopupFromButtons(List list, String name) {
        Widget menu = null;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (menu == null) {
                menu = getWidget("popupmenu", name);
            }
            Widget widget = (Widget) it.next();
            Class clazz = ApplicationContext.getInstance().getWidget("button");
            if (widget.getClass().isAssignableFrom(clazz)) {
                Widget item = getWidget("menuitem", "menuItem:" + widget.getName());
                cloneWidget(widget, item);
                menu.addChildWidget(item);
            }
        }
        return menu;
    }

    /**
     * Clones all settings of a widget into the target widget
     * @param source the source widget
     * @param target the target widgets
     */
    public static void cloneWidget(Widget source, Widget target) {
        target.setPart(source.getPart());
        target.setName(target.getWidgetType() + ":" + source.getName());
        target.setImmidiate(source.isImmidiate());
        target.setEnable(source.isEnabled());
        target.setField(source.getField());
        target.setParent(source.getParent());
        target.setPosition(source.getRectangle().getX(), target.getRectangle().getX());
        target.setSize(source.getRectangle().getWidth(), target.getRectangle().getHeight());
        target.setPrefix(source.getPrefix());
        target.setRequired(source.isRequired());
        target.setSkip(source.isSkip());
        target.setValue(source.getValue());
        target.setVisible(source.isVisible());
        HashMap map = source.getProperties();
        if (map != null) {
            for (Iterator keys = map.keySet().iterator(); keys.hasNext();) {
                String key = (String) keys.next();
                String value = (String) map.get(key);
                target.setProperty(key, value);
            }
        }
        if (source.getRules() != null) {
            for (Iterator rules = source.getRules().iterator(); rules.hasNext();) {
                target.registerRule((IRule) rules.next());
            }
        }
        if (source.getChildWidgets() != null) {
            for (Iterator children = source.getChildWidgets().iterator(); children.hasNext();) {
                target.addChildWidget((Widget) children.next());
            }
        }
        if (source.getDependencies() != null) {
            for (Iterator deps = source.getChildWidgets().iterator(); deps.hasNext();) {
                target.addDependency((String) deps.next());
            }
        }

    }

}
