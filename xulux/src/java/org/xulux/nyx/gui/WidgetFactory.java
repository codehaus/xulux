/*
 $Id: WidgetFactory.java,v 1.8 2003-11-06 19:53:11 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.gui;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.rules.IRule;

/**
 * Factory to create the widget class..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetFactory.java,v 1.8 2003-11-06 19:53:11 mvdb Exp $
 */
public class WidgetFactory
{

    /**
     * Constructor for WidgetFactory.
     */
    public WidgetFactory()
    {
    }

    /**
     * Creates a widget from the widget Registry.
     *
     * @param name - the name of the widget
     * @param field - the field to use on the widget
     */
    public static Widget getWidget(String type, String name)
    {
        Class clazz = ApplicationContext.getInstance().getWidget(type);
        Widget instance = null;
        if (clazz != null)
        {
            try
            {
                Constructor constructor =
                    clazz.getConstructor(new Class[] { String.class });
                instance =
                    (Widget) constructor.newInstance(new String[] { name });
                instance.setWidgetType(type);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Creates a popmenu from a list of buttons.
     *
     * @param list
     * @param the name of the menu
     * @param properties to be set in the widget.
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
                Widget item = getWidget("menuitem", "menuItem:"+widget.getName());
                cloneWidget(widget, item);
                menu.addChildWidget(item);
            }
        }
        return menu;
    }


    /**
     * Clones all settings of a widget into the target widget
     * @param source
     * @param target
     */
    public static void cloneWidget(Widget source, Widget target) {
        target.setPart(source.getPart());
        target.setName(target.getWidgetType()+":"+source.getName());
        target.setImmidiate(source.isImmidiate());
        target.setEnable(source.isEnabled());
        target.setField(source.getField());
        target.setParent(source.getParent());
        target.setPosition(source.getRectangle().getX(),target.getRectangle().getX());
        target.setSize(source.getRectangle().getWidth(),target.getRectangle().getHeight());
        target.setPrefix(source.getPrefix());
        target.setRequired(source.isRequired());
        target.setSkip(source.isSkip());
        target.setValue(source.getValue());
        target.setVisible(source.isVisible());
        HashMap map = source.getProperties();
        if (map != null) {
            for (Iterator keys = map.keySet().iterator(); keys.hasNext();) {
                String key = (String) keys.next();
                String value = (String)map.get(key);
                target.setProperty(key, value);
            }
        }
        if (source.getRules() != null) {
            for (Iterator rules = source.getRules().iterator(); rules.hasNext();) {
                target.registerRule((IRule)rules.next());
            }
        }
        if (source.getChildWidgets() != null) {
            for (Iterator children = source.getChildWidgets().iterator(); children.hasNext();) {
                target.addChildWidget((Widget)children.next());
            }
        }
        if (source.getDependencies() != null) {
            for (Iterator deps = source.getChildWidgets().iterator(); deps.hasNext();) {
                target.addDependency((String)deps.next());
            }
        }

    }

}
