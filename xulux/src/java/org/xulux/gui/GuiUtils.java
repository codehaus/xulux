/*
 $Id: GuiUtils.java,v 1.3 2003-12-29 14:26:43 mvdb Exp $

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.context.PartRequest;
import org.xulux.rules.impl.WidgetRequestImpl;

/**
 * The gui utils class takes care of common things that happen in a gui.
 * Like firing rules when the cancel button is pressed or the window is closed.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiUtils.java,v 1.3 2003-12-29 14:26:43 mvdb Exp $
 */
public class GuiUtils {

    /**
     * Allow override.
     */
    protected GuiUtils() {
    }

    /**
     * Processes the generic code when the cancel button is pressed
     * or when eg a window is closed.
     *
     * @param caller the caller widget
     * @return true if there was something to process, otherwize false
     */
    public static boolean processCancel(Widget caller) {
        if (caller == null) {
            return false;
        }
        boolean cancelProcessed = false;
        String action = caller.getProperty("defaultaction");
        Widget parent = caller;
        if (action == null) {
            // the cancel button was not pressed, but some other means
            // of closing the part was used (eg a window close)
            // we assume this must be the parent of the cancel button.
            // find the cancel button if any are present..
            List list = getWidgetsWithProperty("defaultaction", caller);
            if (list != null) {
                for (Iterator it = list.iterator(); it.hasNext();) {
                    Widget w = (Widget) it.next();
                    if ("cancel".equalsIgnoreCase(w.getProperty("defaultaction"))) {
                        fireFieldPostRule(caller, w, PartRequest.ACTION_CANCEL_REQUEST);
                        cancelProcessed = true;
                        // we can have more cancel buttons in one part..
                        continue;
                    }
                }
            }
        } else {
            // fire post rules..
            if ("cancel".equalsIgnoreCase(caller.getProperty("defaultaction"))) {
                fireFieldPostRule(caller, caller, PartRequest.ACTION_CANCEL_REQUEST);
                cancelProcessed = true;
                while (parent.getParent() != null) {
                    parent = parent.getParent();
                }
            } 
        }

        if (caller.isRootWidget() && cancelProcessed) {
            if (ApplicationContext.isPartApplication(caller.getPart())) {
                ApplicationContext.exitApplication();
            }
        }
        return cancelProcessed;
    }

    /**
     * Fire the post rules of the specified widget
     *
     * @param caller the caller of the rule (request.getWidget()
     * @param widget the widget to call the post rules of
     * @param action the action
     */
    public static void fireFieldPostRule(Widget caller, Widget widget, int action) {
        fireFieldRule(caller, widget, action, ApplicationContext.POST_REQUEST);
        WidgetRequestImpl impl = new WidgetRequestImpl(caller, action);
    }

    /**
     * Fire the post rules of the specified widget
     *
     * @param caller the caller of the rule (request.getWidget()
     * @param widget the widget to call the post rules of
     * @param action the action
     */
    public static void fireFieldExecuteRule(Widget caller, Widget widget, int action) {
        fireFieldRule(caller, widget, action, ApplicationContext.EXECUTE_REQUEST);
    }

    /**
     * Fir the rules of the specified widget.
     *
     * @param caller the caller of the rule (request.getWidget()
     * @param widget the widget to call the post rules of
     * @param action the action
     * @param type the type of request (PRE, POST or EXECUTE)
     */
    public static void fireFieldRule(Widget caller, Widget widget, int action, int type) {
        WidgetRequestImpl impl = new WidgetRequestImpl(caller, action);
        ApplicationContext.fireFieldRequest(widget, impl, type);
    }
    /**
     * @param property the property to look for
     * @param caller the caller widget to get the part from
     * @return a list with widgets with certain properties or null when no widgets could be found
     */
    public static List getWidgetsWithProperty(String property, Widget caller) {
        if (property == null || caller == null) {
            return null;
        }
        List retValue = null;
        ApplicationPart part = caller.getPart();
        if (part == null) {
            return null;
        }
        ApplicationPart.WidgetList list = part.getWidgets();
        if (list != null) {
            for (Iterator it = list.iterator(); it.hasNext();) {
                Widget w = (Widget) it.next();
                if (w.getProperty(property) != null) {
                    if (retValue == null) {
                        retValue = new ArrayList();
                    }
                    retValue.add(w);
                }
            }
        }
        return retValue;
    }
}
