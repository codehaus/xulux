/*
 $Id: UpdateButtonsListener.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $

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
package org.xulux.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.context.ApplicationPart;
import org.xulux.context.ApplicationPartHandler;
import org.xulux.global.BeanMapping;
import org.xulux.global.Dictionary;
import org.xulux.global.IField;
import org.xulux.gui.IContentWidget;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.util.NyxEventQueue;
import org.xulux.swing.widgets.Table;
import org.xulux.utils.ClassLoaderUtils;

/**
 * A listener for update buttons. The prepostfieldlistener isn't
 * very usefull for this purpose.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: UpdateButtonsListener.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $
 */
public class UpdateButtonsListener extends NyxListener implements ActionListener, ListSelectionListener {

    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(UpdateButtonsListener.class);
    /**
     * The parent
     */
    protected Widget parent;
    /**
     * The source
     */
    protected Widget source;

    /**
     * Hack to reduce mess in table.
     */
    protected Table table;

    /**
     * @param table the table
     */
    public UpdateButtonsListener(Table table) {
        this.table = table;
    }

    /**
     *
     * @param parent the parent widget
     * @param source the source widget
     */
    public UpdateButtonsListener(Widget parent, Widget source) {
        super(source);
        this.parent = parent;
        this.source = source;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        NyxEventQueue.getInstance().holdEvents(false);
        String actionType = source.getProperty("action.type");
        if (actionType == null) {
            return;
        }
        Object partBean = parent.getGuiValue();
        if (actionType.equals("add")) {
            completed(true);
            Object value = partBean;
            if (value == null) {
                // figure out what object type to create
                if (parent instanceof IContentWidget) {
                    Object objContent = ((IContentWidget) parent).getContent();
                    // @todo Only supports content lists...
                    if (objContent instanceof List) {
                        List content = (List) objContent;
                        String classType = parent.getProperty("classType");
                        if (classType != null) {
                            partBean = ClassLoaderUtils.getObjectFromClassString(classType);
                        } else if (content != null && content.size() > 0) {
                            partBean = ClassLoaderUtils.getObjectFromClass(content.get(0).getClass());
                        }
                        if (partBean == null) {
                            if (log.isWarnEnabled()) {
                                log.warn(
                                    "Cannot determine type to create for widget "
                                        + parent.getName()
                                        + " please create a rule to call"
                                        + " the updateform or add the classType property to the table");
                            }
                            // @todo the nyx magic stops here for now
                            return;
                        }
                    }
                }
            } else {
                partBean = ClassLoaderUtils.getObjectFromClass(value.getClass());
            }
            setLocatorValue(partBean);
        } else if (actionType.equals("delete")) {
            // process post rules first..
            completed(true);
            log.warn("Deleting");
        } else if (actionType.equals("update")) {
            completed(true);
            log.warn("Updating");
        } else {
            completed(true);
            return;
        }
        String xml = source.getProperty("action");
        if (xml == null || "".equals(xml)) {
            // @todo Do some rule magic here, since rules can be part of actions!
            if (log.isWarnEnabled()) {
                log.warn("No action to preform on widget " + source.getName());
            }
            return;
        }
        ApplicationPartHandler handler = new ApplicationPartHandler();
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = handler.read(stream, partBean);
        part.getSession().setValue("nyx.callerwidget", parent);
        part.setParentPart(parent.getPart());
        part.activate();
    }

    /**
     * Set the locator value if such a locator is present
     * properties used for tables : locator and locator.field
     * @param bean the bean
     */
    private void setLocatorValue(Object bean) {
        String locator = parent.getProperty("locator");
        if (bean == null) {
            return;
        }
        if (locator != null) {
            String locatorField = parent.getProperty("locator.field");
            Widget widget = parent.getPart().getWidget(locator);
            if (widget != null && locatorField != null) {
                Object value = widget.getValue();
                String field = widget.getField();
                if (field != null && value != null) {
                    BeanMapping mapping = Dictionary.getInstance().getMapping(bean);
                    IField f = mapping.getField(locatorField);
                    f.setValue(bean, value);
                }
            }
        }

    }
    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent e) {
        if (table != null) {
            table.refreshUpdateButtons();
        }
    }

}
