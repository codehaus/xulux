/*
 $Id: NyxFocusManager.java,v 1.1.2.5 2003-06-04 23:45:59 mvdb Exp $

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
package org.xulux.nyx.swing.layouts;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultFocusManager;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.gui.Button;
import org.xulux.nyx.gui.Widget;

/**
 * This class manages the focus of components created by nyx.
 * In the xml definition this corresponds to the order element
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxFocusManager.java,v 1.1.2.5 2003-06-04 23:45:59 mvdb Exp $
 */
public class NyxFocusManager extends DefaultFocusManager {

    private ApplicationPart part;
    private boolean emptyOrderList = false;
    
    /**
     * Set the order list to be able to specify the order 
     * in the system.
     * 
     * @param part - the applicationPart
     */
    public NyxFocusManager(ApplicationPart part) {
        this.part = part;
        Object root = part.getRootWidget();
        if (part.getWidgets() == null || 
            part.getWidgets().size() == 0) 
        {
            emptyOrderList = true;
            return;
        }
        initializeOrderList();
    }

    /**
     * Initializes the orderlist. 
     * Since the orderlist may not be completely
     * specified in the part xml, we need to add
     * the other fields to the orderlist in 
     * normal order. 
     * Also disables and invisible fields will 
     * be added to the list, since they will be checked
     * ones a request for that widget is made.
     */
    private void initializeOrderList() {
        List oldOrder = part.getTabOrder();
        if (oldOrder == null) {
            return;
        }
        ArrayList orderList = new ArrayList();
        ApplicationPart.WidgetList widgets = part.getWidgets();
        orderList.addAll(oldOrder);
        Iterator it = widgets.iterator();
        while (it.hasNext()) {
            Widget w = (Widget) it.next();
            if (oldOrder.indexOf(w.getName()) == -1) {
                if (w.isSelectable()) {
                    orderList.add(w.getName());
                }
            }
        }
        part.setTabOrder(orderList);
    }

    /** 
     * Does not yet include type support.
     * @param aComponent if this is null, the first component will be selected.
     * @see javax.swing.FocusManager#focusNextComponent(java.awt.Component)
     */
    public void focusNextComponent(Component aComponent) {
        if (emptyOrderList && aComponent != null) {
            super.focusNextComponent(aComponent);
            return;
        }
        boolean enabledWidgetFound = false;
        int widgetIndex = 0;
        if (aComponent != null) {
            Widget tmpW = part.getWidgets().findWithNative(aComponent);
            String widgetName = tmpW.getName(); 
            widgetIndex = part.getTabOrder().indexOf(widgetName);
        }   
        // we want to select the next component
        widgetIndex++;
        while (true) {
            // when we moved to the last widget,
            // we reset the widgetIndex to 0..
            if (widgetIndex >= part.getTabOrder().size()) {
                widgetIndex = 0;
            }
            Widget widget = part.getWidgets().get((String)part.getTabOrder().get(widgetIndex));
            if (widget != null) {
                if (widget.isEnabled() && ((widget.getValue() == null ||
                     widget.getValue().equals("")) || widget instanceof Button)) {
                    enabledWidgetFound = true;
                    break;
                }
            }
            widgetIndex++;
        }
        Widget widget = part.getWidgets().get((String)part.getTabOrder().get(widgetIndex));
//        System.out.println("Setting focus to widget "+widget);
//        if (widget != null) {
//            System.out.println("with name :"+widget.getName());
//        }
        setFocus(widget);
    }
    
    /**
     * Actually sets the focus to the widget.
     * @param widget
     */
    private void setFocus(Widget widget) {
        
        Object comp = widget.getNativeWidget();
//        System.out.println("comp : "+comp);
        if (comp instanceof JComponent) {
            ((JComponent)comp).grabFocus();
        }
        else if (comp instanceof Component)
        {
            ((Component)comp).requestFocus();
        } 
    }
    
    /**
     * Sets the focus to the first widget.. 
     */
    public void setFocusToFirstWidget(JComponent component) {
        new Thread(new FocusToFirstWidget(component)).start();
    }
    
    
    /**
     * A runnable to check in a seperate thread if the component
     * passed in already has a parent and if so request
     * the focus to the first field available.
     * Code example :
     * <code>
     * new Thread(new FocusToFirstWidget(component)).start().
     * </code>
     */
    private class FocusToFirstWidget implements Runnable
    {
        JComponent component;
        /**
         * The component to check for to see if it has a parent
         * @param component
         */
        public FocusToFirstWidget(JComponent component) {
            this.component = component;
        }
        
        /**  
         * Pretty messy this one. In a seperate thread it checks
         * to see if the component has a parent
         * if so, it starts another thread to request the focus
         * for that component. 
         * NOTE: If the first selectable component is on a panel,
         *       it will find the correct one, but more nesting is
         *       impossible in this version.
         * @see java.lang.Runnable#run()
         */
        public void run() {
            
            while (this.component.getParent() == null ) { }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    focusNextComponent(null);                    
                }
            });
        }
    }
    
}
