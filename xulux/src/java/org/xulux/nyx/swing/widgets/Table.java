/*
 $Id: Table.java,v 1.9 2003-08-07 16:41:14 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.swing.widgets;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.IContentWidget;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.WidgetFactory;
import org.xulux.nyx.swing.listeners.PopupListener;
import org.xulux.nyx.swing.models.NyxTableColumnModel;
import org.xulux.nyx.swing.models.NyxTableModel;
import org.xulux.nyx.swing.util.SwingUtils;
import org.xulux.nyx.utils.ClassLoaderUtils;
import org.xulux.nyx.utils.NyxCollectionUtils;

/**
 * A nyx table.. 
 * A nyx table can do without a popupmenu field and can add 
 * a menuitem directly to its table.
 * 
 * TODO: Redo this completely! It sucks big time!!
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Table.java,v 1.9 2003-08-07 16:41:14 mvdb Exp $
 */
public class Table extends ContainerWidget
implements IContentWidget
{


    protected JTable table;
    
    /** 
     * This is the native widget
     */
    protected JScrollPane scrollPane;
    protected Widget menu;
    protected boolean hasChildPopups;
    private boolean childPopupsChecked;
    
    protected List content;
    protected boolean contentChanged;
    protected static Log log = LogFactory.getLog(Table.class);
    
    /**
     * The columnModel
     */
    protected NyxTableColumnModel columnModel;
    
    /**
     * The tablemodel
     */
    protected NyxTableModel model;
    
    /**
     * @param name
     */
    public Table(String name) {
        super(name);
    }

    public void destroyTable() {
        if (!initialized) {
            return;
        }
        if (this.columnModel != null) {
            this.columnModel.destroy();
            this.columnModel = null;
        }
        if (this.model != null) {
            this.model.destroy();
            this.model = null;
        }
        if (this.scrollPane != null) {
            try {
                this.scrollPane.remove(this.table);
            }catch(NullPointerException npe) { 
                // eat it..
            }
        }
        this.table = null;
    }
    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        if (!initialized) {
            return;
        }
        processDestroy();
        destroyTable();
        if (this.scrollPane == null) {
            return;
        }
        Container container = this.scrollPane.getParent();
        this.scrollPane.setVisible(false);
        this.scrollPane.removeAll();
        if (container != null)
        {
            container.remove(this.scrollPane);
        }
        this.scrollPane = null;
        this.table = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return this.scrollPane;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        contentChanged = true;
        this.scrollPane = new JScrollPane();
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        initializeContent();
        if (contentChanged) {
            this.destroyTable();
            if (this.columnModel == null) {
                this.columnModel = new NyxTableColumnModel(this);
            }
            if (this.model == null) {
                this.model = new NyxTableModel(this);
            }
            table = new JTable(this.model,this.columnModel);
            table.getSelectionModel().addListSelectionListener(new SelectionListener());
            table.setPreferredSize(SwingUtils.getDimension(getRectangle()));
            scrollPane.setViewportView(this.table);
            scrollPane.setVisible(true);
            table.setVisible(true);
            contentChanged = false;
        }
        initializeUpdateButtons();
        refreshUpdateButtons();

    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (table == null) {
            return null;
        }
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1 ) {
            return null;
        }
        if (table.getRowSelectionAllowed() && !table.getCellSelectionEnabled()) {
            return table.getModel().getValueAt(selectedRow, -1);
        } else if (!table.getRowSelectionAllowed() && table.getCellSelectionEnabled()) {
            return table.getModel().getValueAt(selectedRow, table.getSelectedColumn());
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        this.scrollPane.requestFocus();
        // if widget is not showing we have
        // to make it showing..
        if (!this.scrollPane.isShowing() && getParent() != null) {
            // set the session variable, so controls
            // can look who requested focus..
            getPart().getSession().setValue("nyx.focusrequest", this);
            getParent().focus();            
        }
        // remove session variable again.
        getPart().getSession().remove("nyx.focusrequest");
        this.scrollPane.requestFocus();
    }

    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {
        if (widget instanceof PopupMenu || widget instanceof MenuItem) {
            hasChildPopups = true;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return true;
    }
    
    /**
     * Initializes the content when the property
     * content and content.type is present
     */
    protected void initializeContent() {
        String content = getProperty("content");
        String contentType = getProperty("content.type");
        if (content == null || contentType == null) {
            return;
        }
        if (contentType.equalsIgnoreCase("string")) {
            this.content = NyxCollectionUtils.getListFromCSV(content);
            contentChanged = true;
        }else if (contentType.equalsIgnoreCase("field")) {
            int index = content.lastIndexOf(".");
            BeanMapping mapping = null;
            IField field = null;
            if (index == -1 ) {
                mapping = Dictionary.getInstance().getMapping(getPart().getBean());
                field = mapping.getField(content);
            } else {
                mapping = Dictionary.getInstance().getMapping(ClassLoaderUtils.getClass(content.substring(0,index)));
                field = mapping.getField(content.substring(index+1));
            }
            if (field != null) {
                System.out.println("Field : "+field);
                this.content = NyxCollectionUtils.getList(field.getValue(getPart().getBean()));
                contentChanged = true;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Field "+content+" for table "+getName()+" could not be found");
                }
            }
        }
    }
    
    /**
     * Refreshes the update buttons.
     * The buttons update and delete are disabled when no 
     * row is selected.
     *
     */
    protected void refreshUpdateButtons() {
        Object value = getGuiValue();
        boolean buttonState = value!=null;
        // we need to disable update and delete
        String buttons = getProperty("updatebuttons");
        if (buttons != null) {
            List strList = NyxCollectionUtils.getListFromCSV(buttons);
            Iterator it = strList.iterator();
            while (it.hasNext()) {
                Widget widget = getPart().getWidget((String)it.next());
                String actionType = widget.getProperty("action.type");
                if (actionType != null && ( 
                      actionType.equals("update") ||
                        actionType.equals("delete"))) {
                    widget.setEnable(buttonState);
                }
            }
        }
        if (menu != null) {
            if (menu.getChildWidgets() != null) {
                for (Iterator it = menu.getChildWidgets().iterator(); it.hasNext();) {
                    Widget cw = (Widget) it.next();
                    if (cw instanceof MenuItem) {
                        String actionType = cw.getProperty("action.type");
                        if (actionType != null && ( 
                              actionType.equals("update") ||
                                actionType.equals("delete"))) {
                            cw.setEnable(buttonState);
                        }
                    }
                }
            }
        }
    } 
    /**
     * Initializes the popupmenus of the table.
     * 
     * @return
     */
    protected boolean initializeUpdateButtons() {
        if (this.menu != null) {
            return false;
        }
        String buttons = getProperty("updatebuttons");
        if (buttons != null) {
            String updateAction = getProperty("updateaction");
            List list = new ArrayList();
            List strList = NyxCollectionUtils.getListFromCSV(buttons);
            Iterator it = strList.iterator();
            while (it.hasNext()) {
                String wName =(String)it.next();
                Widget widget = getPart().getWidget(wName);
                if (widget == null) {
                    if (log.isWarnEnabled()) {
                        log.warn("Button "+wName+" does not exist, check your part xml");
                        continue;
                    }
                }
                if (updateAction != null) {
                    widget.setProperty("action", updateAction);
                }
                list.add(widget);
            }
            menu = WidgetFactory.getPopupFromButtons(list,"Popup:"+getName());
            menu.setParent(this);
            addChildWidget(menu);
            table.addMouseListener(new PopupListener(this.menu));
        }
        if (!childPopupsChecked) {
            childPopupsChecked = true;
            List list = getChildWidgets();
            if (list != null && list.size() > 0) {
                for (Iterator it = getChildWidgets().iterator(); it.hasNext();) {
                    Widget cw = (Widget) it.next();
                    if (cw instanceof MenuItem) {
                        hasChildPopups = true;
                        break;
                    }
                }
            }
        }
        if (hasChildPopups && buttons != null) {
            // just add a seperator by default
            Widget item = WidgetFactory.getWidget("menuitem", "Separator:"+getName());
            item.setProperty("type", "separator");
            item.setParent(menu);
            menu.addChildWidget(item);
        }
        if (hasChildPopups) {
            if (menu == null) {
                menu = WidgetFactory.getWidget("popupmenu", "PopupMenu:"+getName());
            }
            if (getChildWidgets() != null) {
                for (Iterator it = getChildWidgets().iterator(); it.hasNext();) {
                    Widget cw = (Widget) it.next();
                    if (cw instanceof MenuItem) {
                        cw.setParent(menu);
                        menu.addChildWidget(cw);
                    }
                }
            }
        }
        if (menu != null) {
            menu.initialize();
        }
        return true;
    }
    
    
    /**
     * 
     * @return the content of the table or null when no content is present
     */
    public List getContent() {
        return this.content;
    }
    
    /**
     * Set the content of the table
     * @param list
     */
    public void setContent(List list) {
        contentChanged = true;
        this.content = list;
        refresh();
    }
    
    /**
     * Remove this one..
     * @return
     */
    public JTable getJTable() {
        return this.table;
    }
    
    public class SelectionListener implements ListSelectionListener {
        /**
         * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            refreshUpdateButtons();
        }
    }
    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
        // TODO
    }

}
