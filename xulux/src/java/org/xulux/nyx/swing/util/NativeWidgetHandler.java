/*
 $Id: NativeWidgetHandler.java,v 1.11 2003-12-11 20:03:18 mvdb Exp $

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
package org.xulux.nyx.swing.util;

import java.awt.Component;

import javax.swing.JComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.INativeWidgetHandler;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * The native widgets handler for swing.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeWidgetHandler.java,v 1.11 2003-12-11 20:03:18 mvdb Exp $
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
