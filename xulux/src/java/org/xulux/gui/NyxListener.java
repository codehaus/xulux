/*
   $Id: NyxListener.java,v 1.11 2004-11-18 23:34:03 mvdb Exp $
   
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.core.XuluxContext;
import org.xulux.guidriver.XuluxGuiDriver;
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
 * @version $Id: NyxListener.java,v 1.11 2004-11-18 23:34:03 mvdb Exp $
 */
public abstract class NyxListener {
    /**
     * The widget
     */
    protected Widget widget;
    /**
     * Is the listener still processing ?
     */
    protected static boolean processing = false;
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
        XuluxContext.fireFieldRequest(widget, impl, XuluxContext.POST_REQUEST);
        // preform all pre rules if postOnly is false
        if (!postOnly) {
            XuluxContext.fireFieldRequests(impl, XuluxContext.PRE_REQUEST);
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
        XuluxContext.fireRequest(req, XuluxContext.POST_REQUEST);
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
            //((NyxCombo) widget).setValue(widget.getGuiValue(), false);
            // refresh fields that use the same functionality
            refreshFields(widget);
            return true;
        }

        if (widget instanceof Entry || widget instanceof TextArea) {
            IInvalidValueStrategy strategy = widget.getPart().getInvalidValueStrategy();
            if (strategy != null) {
                if (strategy.checkWidget(widget) == false) {
                    return false;
                }
            }
            widget.setValue(widget.getGuiValue());
            // refresh the all widgets who references this field
            refreshFields(widget);
            return true;
        }

        if (widget instanceof Button || widget instanceof MenuItem) {
            String xml = widget.getProperty("action");
            if (xml != null) { 
	            XuluxGuiDriver handler = new XuluxGuiDriver();
	            InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
	            ApplicationPart part = handler.read(stream, widget.getPart().getBean());
	            part.getSession().setValue("nyx.callerwidget", widget);
	            part.setParentPart(widget.getPart());
	            part.activate();
            }
            String defAction = widget.getProperty("defaultaction");
            if (defAction != null) {
                if (defAction.equalsIgnoreCase("save")) {
                    IInvalidValueStrategy strategy = widget.getPart().getInvalidValueStrategy();
                    if (strategy != null) {
                        if (strategy.checkForm(widget.getPart()) == false) {
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
        XuluxContext.fireFieldRequest(widget, impl, XuluxContext.PRE_REQUEST);
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
