/*
   $Id: NativeWidgetHandler.java,v 1.2 2004-01-28 15:09:24 mvdb Exp $
   
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
package org.xulux.swing.util;

import java.awt.Component;

import javax.swing.JComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.INativeWidgetHandler;
import org.xulux.gui.Widget;
import org.xulux.utils.ClassLoaderUtils;

/**
 * The native widgets handler for swing.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeWidgetHandler.java,v 1.2 2004-01-28 15:09:24 mvdb Exp $
 */
public class NativeWidgetHandler implements INativeWidgetHandler {

    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(INativeWidgetHandler.class);

    /**
     *
     */
    public NativeWidgetHandler() {
    }

    /**
     * Adds the specified object (if it is an instanceof JComponent)
     * to the specified parent.
     *
     * @see org.xulux.nyx.gui.INativeWidgetHandler#getWidget(java.lang.Object, org.xulux.nyx.gui.Widget)
     */
    public Widget getWidget(Object nativeWidget, Widget parent) {
        if (nativeWidget != null && nativeWidget instanceof JComponent) {
            if (parent.canContainChildren()) {
                Object nativeParent = parent.getNativeWidget();
                if (nativeParent instanceof JComponent) {
                    ((JComponent) nativeParent).add((JComponent) nativeWidget);
                    return parent;
                }
            }
        } else {
            log.warn("Native widget cannot be added, since it is not of type JComponent or null");
        }
        return null;
    }

    /**
     * Adds a JComponent to the widget specified as parent
     * @see org.xulux.nyx.gui.INativeWidgetHandler#getWidget(java.lang.String, org.xulux.nyx.gui.Widget)
     */
    public Widget getWidget(String clazz, Widget parent) {
        Object nativeWidget = ClassLoaderUtils.getObjectFromClassString(clazz);
        return getWidget(nativeWidget, parent);
    }

    /**
     * @see org.xulux.nyx.gui.INativeWidgetHandler#setLocationOnWidget(org.xulux.nyx.gui.Widget, int, int)
     */
    public void setLocationOnWidget(Widget parent, int x, int y) {
        if (parent != null && parent.getNativeWidget() != null) {
            JComponent comp = (JComponent) parent.getNativeWidget();
            // set the location on the last component added..
            if (comp.getComponentCount() > 0) {
                Component childComp = comp.getComponent(comp.getComponentCount() - 1);
                setLocationOnWidget(childComp, x, y);
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.INativeWidgetHandler#setLocationOnWidget(java.lang.Object, int, int)
     */
    public void setLocationOnWidget(Object widget, int x, int y) {
        if (!(widget instanceof Component)) {
            return;
        }
        Component comp = (Component) widget;
        comp.setLocation(x, y);
    }

    /**
     * @see org.xulux.nyx.gui.INativeWidgetHandler#addWidgetToParent(org.xulux.nyx.gui.Widget, java.lang.Object)
     */
    public void addWidgetToParent(Widget widget, Object parentWidget) {
        if (!(parentWidget instanceof JComponent) || widget == null) {
            return;
        }
        JComponent comp = (JComponent) parentWidget;
        Object nativeWidget = widget.getNativeWidget();
        if (nativeWidget instanceof Component) {
            comp.add((Component) widget.getNativeWidget(), widget);
        }
    }
}
