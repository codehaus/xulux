/*
   $Id: ContainerWidget.java,v 1.4 2004-11-29 17:34:26 mvdb Exp $
   
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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Specifies a container widget.
 * It makes overriding a bit easier.
 *
 * @author Martin van den Bemt
 * @version $Id: ContainerWidget.java,v 1.4 2004-11-29 17:34:26 mvdb Exp $
 */
public abstract class ContainerWidget extends Widget {
    /**
     * the widgets
     */
    protected ArrayList widgets;

    /**
     * Constructor for ContainerWidget.
     * @param name the name of the widget
     */
    public ContainerWidget(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addChildWidget(Widget)
     */
    public void addChildWidget(Widget widget) {
        if (widgets == null) {
            widgets = new ArrayList();
        }
        if (widget != null) {
            widgets.add(widget);
            widget.setRootWidget(false);
            // add to the parent if the parent is already
            // initialized..
            if (initialized) {
                widget.setParent(this);
                addToParent(widget);
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canBeRootWidget()
     */
    public boolean canBeRootWidget() {
        return super.canBeRootWidget();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainChildren()
     */
    public boolean canContainChildren() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getChildWidgets()
     */
    public ArrayList getChildWidgets() {
        return widgets;
    }
    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initializeChildren() {
        ArrayList widgets = getChildWidgets();
        if (widgets == null) {
            return;
        }
        Iterator iterator = widgets.iterator();
        while (iterator.hasNext()) {
            Widget widget = (Widget) iterator.next();
            addToParent(widget);
        }
        // only refresh "root" widgets.
        if (getPart() != null && getParent() == null) {
            getPart().refreshAllWidgets();
        }
    }

    /**
     * Adds a childwidget to the parent
     * @param widget - the child widget
     */
    public abstract void addToParent(Widget widget);

    /**
     * You probably have to override this,
     * since it only destroys the children
     * and doesn't cleanup the parent
     * object, since it doesn't know about it.
     *
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
      destroyChildren();
    }
    
    /**
     * Destroys all children.
     */
    public void destroyChildren() {
      ArrayList children = getChildWidgets();
      if (children != null) {
          Iterator it = children.iterator();
          while (it.hasNext()) {
              Widget cw = (Widget) it.next();
              cw.destroy();
          }
          children.clear();
          children = null;
      }
    }

}
