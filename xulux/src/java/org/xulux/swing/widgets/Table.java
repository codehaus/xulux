/*
   $Id: Table.java,v 1.9 2004-09-30 21:25:39 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.gui.ContainerWidget;
import org.xulux.gui.IContentWidget;
import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.swing.extensions.NyxJTable;
import org.xulux.swing.listeners.NewSelectionListener;
import org.xulux.swing.listeners.PopupListener;
import org.xulux.swing.listeners.UpdateButtonsListener;
import org.xulux.swing.listeners.ValueChangedListener;
import org.xulux.swing.models.NyxTableCellEditor;
import org.xulux.swing.models.NyxTableColumnModel;
import org.xulux.swing.models.NyxTableModel;
import org.xulux.swing.rules.DefaultTableRule;
import org.xulux.utils.ClassLoaderUtils;
import org.xulux.utils.NyxCollectionUtils;

/**
 * A nyx table..
 * A nyx table can do without a popupmenu field and can add
 * a menuitem directly to its table.
 *
 * @todo Redo this completely! It sucks big time!!
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Table.java,v 1.9 2004-09-30 21:25:39 mvdb Exp $
 */
public class Table extends ContainerWidget implements IContentWidget {

    /**
     * The native swing table
     */
    protected NyxJTable table;

    /**
     * The native locked table
     */
    protected NyxJTable lockedTable;

    /**
     * This is the native widget
     */
    protected JScrollPane scrollPane;
    /**
     * The table context menu
     */
    protected Widget menu;
    /**
     * does the table have childpopups
     */
    protected boolean hasChildPopups;
    /**
     * are the child popups checked
     */
    private boolean childPopupsChecked;

    /**
     * The content of the table
     */
    protected Object content;
    /**
     * has the contentchanged ?
     */
    protected boolean contentChanged;
    /**
     * The log class
     */
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
     * the celleditor
     */
    protected NyxTableCellEditor editor;
    /**
     * the new selection listener
     */
    protected NewSelectionListener newSelectionListener;
    /**
     * the oldsize list
     */
    private int oldListSize = 0;
    /**
     * the current list size
     */
    private int listSize = 0;

    /**
     * @param name the name of the table
     */
    public Table(String name) {
        super(name);
    }

    /**
     * Destroys the current table
     */
    public void destroyTable() {
        if (!initialized) {
            return;
        }
        if (newSelectionListener != null) {
            table.getSelectionModel().removeListSelectionListener(newSelectionListener);
        }
        if (this.columnModel != null) {
            this.columnModel.destroy();
            this.columnModel = null;
        }
        if (this.model != null) {
            this.model.destroy();
            this.model = null;
        }
        if (this.editor != null) {
            this.editor.destroy();
            this.editor = null;
        }
        if (this.scrollPane != null) {
            if (this.table != null) {
                this.scrollPane.remove(this.table);
            }
            if (this.lockedTable != null) {
                this.scrollPane.remove(lockedTable);
            }
        }
        this.table = null;
        this.lockedTable = null;
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
        if (container != null) {
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
        initializing = true;
        contentChanged = true;
        this.scrollPane = new JScrollPane();
        // always add the default table rule.
        registerRule(new DefaultTableRule());
        processIgnoreUse();
        refresh();
        processInit();
        initializing = false;
    }

    /**
     * Sets all children of the table to ignore their use
     * variable when setting values..
     * @todo Add documentation about this. The widget should
     * possibly do something with this if it wants to live
     * in a table
     */
    private void processIgnoreUse() {
        List list = getChildWidgets();
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        ValueChangedListener vcl = new ValueChangedListener(this);
        while (it.hasNext()) {
            Widget widget = (Widget) it.next();
            widget.ignoreUse(true);
            widget.addNyxListener(vcl);
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing) {
            return;
        }
        initialize();
        isRefreshing = true;
        initializeContent();
        if (contentChanged) {
            //this.destroyTable();
            if (this.columnModel == null) {
                this.columnModel = new NyxTableColumnModel(this);
                //                System.out.println("hasLockedColumns : "+this.columnModel.hasLockedColumns());
            }
            boolean refreshModel = false;
            if (this.model == null) {
                if (content instanceof TableModel) {
                    this.model = new NyxTableModel((TableModel) content, this);
                } else {
                    this.model = new NyxTableModel(this);
                }
            } else {
                refreshModel = true;
            }
                
            
            if (this.editor == null) {
                this.editor = new NyxTableCellEditor(this);
            }
            boolean hasLockedColumns = columnModel.hasLockedColumns();
            if (hasLockedColumns && lockedTable == null) {
                lockedTable = new NyxJTable(model, columnModel.getLockedColumnModel());
                lockedTable.setCellEditor(editor);
                lockedTable.getSelectionModel().addListSelectionListener(new UpdateButtonsListener(this));
            }
            if (table == null) {
                table = new NyxJTable(this.model, this.columnModel);
                table.setCellEditor(this.editor);
                table.getSelectionModel().addListSelectionListener(new UpdateButtonsListener(this));
                table.getSelectionModel().addListSelectionListener(new NewSelectionListener(this));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                scrollPane.setViewportView(table);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            }
            if (hasLockedColumns && table.getSiblingTable() == null) {
                table.setSiblingTable(lockedTable);
                lockedTable.setSiblingTable(table);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                lockedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                lockedTable.getTableHeader().setReorderingAllowed(false);
                ((NyxTableColumnModel) table.getColumnModel()).removeLockedColumns();
                ((NyxTableColumnModel) table.getColumnModel()).removeUnlockedColumns();
                lockedTable.setPreferredScrollableViewportSize(columnModel.getLockedColumnWidth());
                //                System.out.println("ColumnModel : "+columnModel.getLockedColumnWidth());
                scrollPane.setRowHeaderView(lockedTable);
                scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, lockedTable.getTableHeader());
            }
            scrollPane.setVisible(true);
            table.setVisible(true);
            table.setVisible(false);
            table.setVisible(true);
            if (refreshModel) {
                // null means everything changed..
                table.tableChanged(null);
                if (lockedTable != null) {
                    lockedTable.tableChanged(null);
                }
//                table.tableChanged(new TableModelEvent(this.model, TableModelEvent.ALL_COLUMNS));
//                lockedTable.tableChanged(new TableModelEvent(this.model, TableModelEvent.ALL_COLUMNS));
            }
            getPart().refreshWidgets(this);
            contentChanged = false;
        }
        initializeUpdateButtons();
        refreshUpdateButtons();
        String height = getProperty("rowHeight");
        if (height != null) {
            int rowHeight = Integer.valueOf(height).intValue();
            if (rowHeight > 0) {
                table.setRowHeight(Integer.valueOf(height).intValue());
                if (lockedTable != null) {
                  lockedTable.setRowHeight(Integer.valueOf(height).intValue());
                }
            }
        }
        // we want to set some things on initialization of the widget..
        // @todo Move this to propertyhandlers..
        if (content != null) {
            String initialSelection = getProperty("initialselection");
            if (initialSelection != null) {
                System.out.println("initialSelection : " + initialSelection);
                if ("last".equalsIgnoreCase(initialSelection)) {
                    table.changeSelection(table.getRowCount() - 1, 1, false, false);
                } else if ("first".equalsIgnoreCase(initialSelection)) {
                    table.changeSelection(0, 1, false, false);
                } else if ("all".equalsIgnoreCase(initialSelection)) {
                    table.selectAll();
                }
                // and remove this property once processed!
                setProperty("initialselection", null);
                // with none, we don't have to do anything.
                // else if ("none".equalsIgnoreCase(initialSelection)) {
                //}
            }
        }
        scrollPane.setVisible(isVisible());
        //scrollPane.invalidate();
        isRefreshing = false;
    }

    /**
     * Checkes through other means if the content is changed.
     * It could be that someone adjusted the content..
     *
     */
    private void checkContentChanged() {
        log.warn("Checking content");
        if (!contentChanged && listSize != oldListSize) {
            log.warn("Content changed");
            contentChanged = true;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (table == null) {
            return null;
        }
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        if (table.getRowSelectionAllowed() && !table.getCellSelectionEnabled()) {
            return table.getModel().getValueAt(selectedRow, -1);
        } else if (!table.getRowSelectionAllowed() && table.getCellSelectionEnabled()) {
            return table.getModel().getValueAt(selectedRow, table.getSelectedColumn());
        }
        return null;
    }
    
    public Object getMouseOverValue() {
       if (table == null) {
           return null;
       }
       int selectedRow = table.getSelectedRow();
       if (selectedRow == -1) {
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
        return getGuiValue() == null;
    }

    /**
     * Initializes the content when the property
     * content and content.type is present
     */
    protected void initializeContent() {
        String contentProp = getProperty("content");
        String contentType = getProperty("content.type");
        if (contentType == null) {
            return;
        }
        if (contentProp == null && !contentType.equalsIgnoreCase("bean")) {
            return;
        }
        if (contentType.equalsIgnoreCase("string")) {
            this.content = NyxCollectionUtils.getListFromCSV(contentProp);
            contentChanged = true;
        } else if (contentType.equalsIgnoreCase("field")) {
            int index = contentProp.lastIndexOf(".");
            IMapping mapping = null;
            IField field = null;
            if (index == -1) {
                mapping = XuluxContext.getDictionary().getMapping(getPart().getBean());
                field = mapping.getField(contentProp);
            } else {
                mapping = XuluxContext.getDictionary().getMapping(ClassLoaderUtils.getClass(contentProp.substring(0, index)));
                field = mapping.getField(contentProp.substring(index + 1));
            }
            if (field != null) {
                //System.out.println("Field : "+field);
                this.content = NyxCollectionUtils.getList(field.getValue(getPart().getBean()));
                //                System.err.println("CONTENT : "+this.content);
                //                System.err.println("field : "+((List)field.getValue(getPart().getBean())).size());
                contentChanged = true;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Field " + content + " for table " + getName() + " could not be found");
                }
            }
        } else if (contentType.equalsIgnoreCase("bean")) {
            if ("raw".equalsIgnoreCase(contentProp)) {
              this.content = getPart().getBean();
            } else {
              this.content = NyxCollectionUtils.getList(getPart().getBean());
            }
            contentChanged = true;
        }
    }

    /**
     * Refreshes the update buttons.
     * The buttons update and delete are disabled when no
     * row is selected.
     *
     */
    public void refreshUpdateButtons() {
        Object value = getGuiValue();
        boolean buttonState = value != null;
        // we need to disable update and delete
        String buttons = getProperty("updatebuttons");
        if (buttons != null) {
            List strList = NyxCollectionUtils.getListFromCSV(buttons);
            Iterator it = strList.iterator();
            while (it.hasNext()) {
                Widget widget = getPart().getWidget((String) it.next());
                if (widget != null) {
                    String actionType = widget.getProperty("action.type");
                    if (actionType != null && (actionType.equals("update") || actionType.equals("delete"))) {
                        widget.setEnable(buttonState);
                    }
                }
            }
        }
        if (menu != null) {
            if (menu.getChildWidgets() != null) {
                for (Iterator it = menu.getChildWidgets().iterator(); it.hasNext();) {
                    Widget cw = (Widget) it.next();
                    if (cw instanceof MenuItem) {
                        String actionType = cw.getProperty("action.type");
                        if (actionType != null && (actionType.equals("update") || actionType.equals("delete"))) {
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
     * @return if initialize update did some work
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
                String wName = (String) it.next();
                Widget widget = getPart().getWidget(wName);
                if (widget == null) {
                    if (log.isWarnEnabled()) {
                        log.warn("Button " + wName + " does not exist, check your part xml");
                        continue;
                    }
                }
                if (updateAction != null) {
                    widget.setProperty("action", updateAction);
                    widget.addNyxListener(new UpdateButtonsListener(this, widget));
                }
                list.add(widget);
            }
            menu = WidgetFactory.getPopupFromButtons(list, "Popup:" + getName());
            menu.setParent(this);
            List children = menu.getChildWidgets();
            if (children != null) {
                for (Iterator cit = children.iterator(); cit.hasNext();) {
                    Widget cw = (Widget) cit.next();
                    cw.addNyxListener(new UpdateButtonsListener(this, cw));
                }
            }
            addChildWidget(menu);
            table.addMouseListener(new PopupListener(this.menu));
        }
        // we also add UpdateButtonsListener here..
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
            Widget item = WidgetFactory.getWidget("menuitem", "Separator:" + getName());
            item.setProperty("type", "separator");
            item.setParent(menu);
            menu.addChildWidget(item);
        }
        if (hasChildPopups) {
            if (menu == null) {
                menu = WidgetFactory.getWidget("popupmenu", "PopupMenu:" + getName());
            }
            if (getChildWidgets() != null) {
                for (Iterator it = getChildWidgets().iterator(); it.hasNext();) {
                    Widget cw = (Widget) it.next();
                    if (cw instanceof MenuItem) {
                        cw.addNyxListener(new UpdateButtonsListener(this, cw));
                        cw.setParent(menu);
                        menu.addChildWidget(cw);
                    }
                }
            }
        }
        if (menu != null) {
            if (menu.getParent() == null) {
                menu.setParent(this);
            }
            menu.initialize();
        }
        return true;
    }

    /**
     *
     * @return the content of the table or null when no content is present
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * Set the content of the table
     * @param object the content
     */
    public void setContent(Object object) {
        contentChanged = true;
        this.content = object;
        if (object instanceof List) {
            this.oldListSize = this.listSize;
            listSize = ((List) content).size();
        }
        if (initialized) {
            refresh();
        }
    }

    /**
     * Remove this one..
     * @return the native JTable
     */
    public JTable getJTable() {
        return table;
    }

    /**
     * @return the native locked JTable.
     */
    public JTable getLockedJTable() {
        return lockedTable;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
    }

    /**
     * Sets the current selected value in the table.
     * @see org.xulux.nyx.gui.Widget#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        if (getField() == null) {
            this.previousValue = getGuiValue();
            this.value = value;
        } else {
            IMapping map = XuluxContext.getDictionary().getMapping(getPart().getBean());
            IField field = map.getField(getField());
            if (field != null) {
                Object currentValue = field.getValue(getPart().getBean());
                if (currentValue != null) {
                    this.previousValue = currentValue;
                }
                field.setValue(getPart().getBean(), value);
                this.value = value;
            }
            if (this.value == null) {
                this.value = value;
            }
        }
        initialize();
        // @todo Add getting the index of the current value in the table,
        // so we can select that one (mostly used on initial activation of
        // table or after adding an entry to the table.
        if (content instanceof List) {
            int index = ((List) content).indexOf(value);
            if (index != -1) {
                // select the row found
                table.setRowSelectionInterval(index, index);
            }
        }
        getPart().refreshFields(this);
        getPart().updateDependandWidgets(this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        return getGuiValue();
    }

    /**
     * Stop editing the table if currently editing.
     */
    public void stopEditing() {
        editor.stopCellEditing(table);
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#contentChanged()
     */
    public void contentChanged() {
        contentChanged = true;
    }

}
