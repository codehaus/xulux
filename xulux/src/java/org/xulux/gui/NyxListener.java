/*
 $Id: NyxListener.java,v 1.3 2004-01-28 12:24:03 mvdb Exp $

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.context.ApplicationContext;
import org.xulux.context.PartRequest;
import org.xulux.rules.impl.PartRequestImpl;
import org.xulux.rules.impl.WidgetRequestImpl;
import org.xulux.swing.widgets.Button;
import org.xulux.swing.widgets.Entry;
import org.xulux.swing.widgets.MenuItem;
import org.xulux.swing.widgets.TextArea;

/**
 * An abstract to which all listeners must obey.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxListener.java,v 1.3 2004-01-28 12:24:03 mvdb Exp $
 */
public abstract class NyxListener {
    /**
     * The widget
     */
    protected Widget widget;
    /**
     * Is the listener still processing ?
     */
    private static boolean processing = false;
    /**
     * The log instanace
     */
    private static Log log = LogFactory.getLog(NyxListener.class);

    /**
     * The default constructor
     */
    public NyxListener() {
    }
    /**
     * Constructor
     * @param widget thw widget to use this listener for
     */
    public NyxListener(Widget widget) {
        this.widget = widget;
    }

    /**
     * Should be called when a field is completed
     */
    public void completed() {
        completed(false);
    }

    /**
     * The completed which calls
     *
     * @param postOnly if true, it will fire of only a post event on the rules
     *         that are part of the current caller
     *
     */
    protected void completed(boolean postOnly) {
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.POST_REQUEST);
        // preform all pre rules if postOnly is false
        if (!postOnly) {
            ApplicationContext.fireFieldRequests(impl, ApplicationContext.PRE_REQUEST);
        }
        processing = false;
    }

    /**
     * Call this when the part has completed processing.
     * It will fire the post rules of the registered part rules
     */
    public void completedPart() {
        processing = true;
        PartRequestImpl req = new PartRequestImpl(widget.getPart(), PartRequest.ACTION_OK_REQUEST);
        ApplicationContext.fireRequest(req, ApplicationContext.POST_REQUEST);
        processing = false;
    }

    /**
     * Refreshes the fields that have dependencies on the
     * caller widget
     *
     * @param widget the widget that is is the caller of refresh
     */
    private void refreshFields(Widget widget) {
        widget.getPart().refreshFields(widget);
        widget.getPart().updateDependandWidgets(widget);
    }

    /**
     * If a value is "accepted" (eg entry filled in
     * , button clicked, etc preform this code.
     *
     * @param widget the widget to accept
     * @return true if the accepted went ok, false if not.
     */
    public boolean accepted(Widget widget) {
        if (widget instanceof NyxCombo) {
            // set the value, but do not refresh the gui.
            ((NyxCombo) widget).setValue(widget.getGuiValue(), false);
            // refresh fields that use the same functionality
            refreshFields(widget);
            return true;
        }

        if (widget instanceof Entry || widget instanceof TextArea) {
            // if the widget is required, a value must
            // exist.
            //            if (widget.isRequired()) {
            //                Object guiValue = widget.getGuiValue();
            // @todo Make an option to check for immidiate required fields
            //                if (guiValue instanceof String || guiValue == null) {
            //                    if (guiValue == null || ((String)guiValue).trim().equals("")) {
            //                        NYXToolkit.getInstance().beep();
            //                        widget.focus();
            //                        return false;
            //                    }
            //                }
            //            }
            widget.setValue(widget.getGuiValue());
            // refresh the all widgets who references this field
            refreshFields(widget);
            return true;
        }

        if (widget instanceof Button || widget instanceof MenuItem) {
            String defAction = widget.getProperty("defaultaction");
            if (defAction != null) {
                if (defAction.equalsIgnoreCase("save")) {
                    Iterator it = widget.getPart().getWidgets().iterator();
                    while (it.hasNext()) {
                        Widget w = (Widget) it.next();
                        boolean process = false;
                        if (w.isRequired() && (w.canContainValue() && w.isValueEmpty()) || !w.isValidValue()) {
                            NYXToolkit.getInstance().beep();
                            w.focus();
                            return false;
                        }
                    }
                    // we handle the completed ourselves,
                    // since otherwise the widgets are already destroyed.
                    completed();
                    // process the post rules of the form.
                    completedPart();
                    // set the current bean in the session of the parent
                    Object bean = widget.getPart().getBean();
                    if (widget.getPart().getParentPart() != null) {
                        widget.getPart().getParentPart().getSession().setValue("nyx.childbean", bean);
                    }
                    Widget parent = (Widget) widget.getPart().getSession().getValue("nyx.callerwidget");
                    if (parent instanceof IContentWidget) {
                        Object objData = ((IContentWidget) parent).getContent();
                        // @todo now only supports content with a list.. Needs to be improved..
                        if (objData instanceof List) {
                            List data = (List) objData;
                            if (data == null) {
                                data = new ArrayList();
                            }
                            Object dataBean = widget.getPart().getBean();
                            int dataIndex = data.indexOf(dataBean);
                            if (dataIndex != -1) {
                                data.set(dataIndex, dataBean);
                            } else {
                                data.add(widget.getPart().getBean());
                            }
                            ((IContentWidget) parent).setContent(data);
                        }
                        parent.setValue(widget.getPart().getBean());
                    }
                    if (parent != null) {
                        parent.refresh();
                    }
                    widget.getPart().destroy();
                    return false;
                }
//                } else if (defAction.equalsIgnoreCase("cancel")) {
//                    // only process the post cancel rule.
//                    completed(true);
//                    // exit window without processing anything.
//                    widget.getPart().destroy();
//                    return false;
//                }

            }
        }
        refreshFields(widget);
        return true;
    }

    /**
     * Should be called when a field is entered
     */
    public void started() {
        processing = true;
        WidgetRequestImpl impl = new WidgetRequestImpl(widget, PartRequest.ACTION_VALUE_CHANGED);
        ApplicationContext.fireFieldRequest(widget, impl, ApplicationContext.PRE_REQUEST);
        processing = false;
    }

    /**
     * Returns the widget.
     * @return Widget
     */
    public Widget getWidget() {
        return widget;
    }

    /**
     * Sets the widget.
     * @param widget The widget to set
     */
    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    /**
     * Returns the processing.
     * @return boolean
     */
    public static boolean isProcessing() {
        return processing;
    }

    /**
     * Override this if you want to handle events.
     *
     * @param event the XuluxEvent to process
     */
    public void processEvent(XuluxEvent event) {
    }

}
