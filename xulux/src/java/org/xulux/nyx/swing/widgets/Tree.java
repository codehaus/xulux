/*
 $Id: Tree.java,v 1.16 2003-10-28 20:10:07 mvdb Exp $

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
package org.xulux.nyx.swing.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.BooleanUtils;
import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.WidgetConfig;
import org.xulux.nyx.global.contenthandlers.TreeContentHandler;
import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.IContentWidget;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.WidgetFactory;
import org.xulux.nyx.swing.listeners.PopupListener;
import org.xulux.nyx.swing.listeners.UpdateButtonsListener;
import org.xulux.nyx.swing.models.NyxTreeCellRenderer;
import org.xulux.nyx.swing.models.SwingTreeModel;
import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Tree.java,v 1.16 2003-10-28 20:10:07 mvdb Exp $
 */
public class Tree extends ContainerWidget implements IContentWidget {
    
    protected JTree jtree;
    protected JScrollPane scrollPane;
    protected Object content;
    protected boolean contentChanged;
    protected SwingTreeModel contentHandler;
    protected boolean hasChildPopups;
    protected Widget menu;
    protected NyxTreeCellRenderer cellRenderer;
    //protected TreeContentHandler contentHandler;
    
    /**
     * @param name
     */
    public Tree(String name) {
        super(name);

    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     * TODO: Destroy it better than now!
     */
    public void destroy() {
        if (!initialized) {
            return;
        }
        processDestroy();
        this.cellRenderer = null;
        if (this.scrollPane != null) {
            if (this.jtree != null) {
                this.scrollPane.remove(jtree);
                jtree = null;
            }
            this.scrollPane = null;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return this.scrollPane;
    }

    /**
     * We need to set the model on initialization and replace the root
     * node on refresh. A setmodel doesn't use the new model (???) Weird..
     * 
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
//        System.err.println("initializing");
//        System.err.println("init : "+contentHandler);
        if (this.contentHandler == null) {
            this.contentHandler = new SwingTreeModel(null);
        }
        jtree = new JTree(this.contentHandler);
        this.cellRenderer = new NyxTreeCellRenderer(this);
        jtree.setCellRenderer(this.cellRenderer);
        scrollPane = new JScrollPane(jtree);
        initializeChildren();
        initialized = true;
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
//        System.err.println("contentHandler : "+contentHandler);
//        System.err.println("contentHandler cont : "+contentHandler.getContent());
        //jtree.setModel(new SwingTreeModel(contentHandler));
        if (isRefreshing()) {
            return;
        }
        isRefreshing = true;
        initialize();
        /* Works only for Metal L&F */
        String lineStyle = getProperty("linestyle");
        if (lineStyle == null) {
            // default to none..
            lineStyle = "None";
        }
        jtree.putClientProperty("JTree.lineStyle", lineStyle);
        
        if (contentChanged) {
//            System.err.println("setting model to : "+contentHandler);
            jtree.setModel(contentHandler);
//            System.err.println("Content : "+contentHandler.getContent());
            contentChanged= false;
        }
        if (getProperty("collapse") != null) {
            setProperty("collapse", null);
            // collapsetree..
//            System.err.println("Collapsing!!");
            if (jtree != null && contentHandler != null) {
                // TODO: really make this flexible!
                String collapseUntill = getProperty("collapse-untill");
                setProperty("collapse-untill",null);
                jtree.collapsePath(new TreePath(contentHandler.getRoot()));
                if (collapseUntill != null ) {
                    if (collapseUntill != null) {
                        if (collapseUntill.toLowerCase().startsWith("level")) {
                            int level = Integer.parseInt(collapseUntill.substring("level".length()));
                            expandToLevel(null, level);
                        }
                    }
                }
            }
        }
        if (getProperty("expand") != null) {
            setProperty("expand", null);
            if (jtree != null && contentHandler != null) {
                String expandUntill = getProperty("expand-untill");
                setProperty("expand-untill", null);
                expandTree(expandUntill);
            }
        }
        // default is on, so we only process if the value is false.
        if (!BooleanUtils.toBoolean(getProperty("showicons"))) {
            if (jtree != null) {
                if (jtree.getCellRenderer() instanceof DefaultTreeCellRenderer) {
                    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)jtree.getCellRenderer();
                    renderer.setOpenIcon(null);
                    renderer.setClosedIcon(null);
                    renderer.setLeafIcon(null);
                }
            }
        }
        initializePopupMenu();
        isRefreshing = false;
    }
    
    
    /**
     * Expands the tree to the specified level.
     * Level 0 is the root of the tree.
     * If level is higher than the levels present, it will just collapse all.
     * 
     * @param level - the level 
     */
    protected void expandToLevel(Object root, int level) {
        if (level == 0) {
            // the root level is always expanded..
            return;
        }
        if (expandPath == null) {
            expandPath = new ArrayList();
        }
        if (root == null) {
            // asssume we need to set the root..
            root = contentHandler.getRoot();
        }
        expandPath.add(root);
        int childCount = contentHandler.getChildCount(root);
        for (int i = 0; i < childCount; i++) {
            Object ele = contentHandler.getChild(root, i);
            if (expandPath.size()-1 != level) {
                expandToLevel(ele, level);
            } else {
                if (expandPath != null) {
                    // collapse the current root..
                    jtree.collapsePath(new TreePath(expandPath.toArray()));
                    // remove the current root,else it will expand leafs..
                    expandPath.remove(root);
                    jtree.expandPath(new TreePath(expandPath.toArray()));
                    break;
                }
            }
        }
        expandPath.remove(root);
    }
    
    protected void expandToLevel(Object root) {
        
    }
    
    /**
     * Expands the tree.
     * @param untill - a string representation untill wath kind of object
     *         the tree should be expanded. 
     */
    protected void expandTree(String untill) {
        boolean expandDefault = false;
        Object root = contentHandler.getRoot();
        if (untill == null) {
            expandDefault(root);
            return; 
        }
        if (untill.equalsIgnoreCase("leaf")) {
            expand(root,true);
            return;
        }
        if (untill.equalsIgnoreCase("all")) {
            expand(root, false);
            return;
        }
        Class untillClazz = ClassLoaderUtils.getClass(untill);
        if (untillClazz == null) {
            expandDefault(root);
            return;
        }
        expandFrom(root, untillClazz);
    }
    /**
     * The private expandPath array. Used in expandToLeaf..
     */
    private List expandPath;
    /**
     * TODO: Document this... or move to treecontenthandler!
     * @param root
     * @param leaf true or false. If true it will NOT show the leaf, otherwise it will
     */
    protected void expand(Object root, boolean leaf) {
        if (expandPath == null) {
            expandPath = new ArrayList();
        }
        expandPath.add(root);
        int childCount = contentHandler.getChildCount(root);
        for (int i = 0; i < childCount; i++) {
            Object ele = contentHandler.getChild(root, i);
            if (!contentHandler.isLeaf(ele)) {
                expand(ele, leaf);
            } else {
                if (expandPath != null) {
                    // collapse the current root..
                    // remove the current root,else it will expand leafs..
                    if (leaf) {
                        expandPath.remove(root);
                        jtree.collapsePath(new TreePath(expandPath.toArray()));
                    }
                    jtree.expandPath(new TreePath(expandPath.toArray()));
                    break;
                }
            }
        }
        expandPath.remove(root);
    }
        
    /**
     * 
     * @param root - the root to expand from
     * @param untill - the class to expand untill.
     */
    protected void expandFrom(Object root, Class untill) {
    }
    
    /**
     * The default method to expand.
     * @param root
     */
    protected void expandDefault(Object root) {
        jtree.expandPath(new TreePath(root));
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {

    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#setContent(java.util.List)
     */
    public void setContent(Object object) {
//        System.err.println("setContent called");
        this.content = object;
        if (object != null) {
//            System.err.println("Content object : "+object.getClass());
            WidgetConfig config = ApplicationContext.getInstance().getWidgetConfig(getWidgetType());
            System.err.println("Object getClass : "+object.getClass());
            TreeContentHandler handler = (TreeContentHandler)config.getContentHandler(object.getClass());
            if (handler == null) {
                System.err.println("Handler for content "+object.getClass()+" not found");
            } else {
                handler.setWidget(this);
                handler.setContent(object);
    //            System.err.println("handler content "+handler.getContent());
                this.contentHandler = new SwingTreeModel(handler);
                this.contentHandler.setWidget(this);
                this.contentHandler.setContent(object);
    //            System.err.println("contentHandler content : "+contentHandler.getContent());
            }
        }
        contentChanged = true;
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#getContent()
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#contentChanged()
     */
    public void contentChanged() {
        if (contentHandler != null) {
            contentHandler.refresh();
        }
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
     * Initializes the popupmenus of the tree.
     *
     */
    protected void initializePopupMenu() {
//        System.out.println("childPopupinit");
        if (hasChildPopups) {
//            System.err.println("hasChildPopups !!!");
//            System.err.println("Childwidgets : "+getChildWidgets());
            if (menu == null) {
                menu = WidgetFactory.getWidget("popupmenu", "PopupMenu:"+getName());
                menu.setPart(getPart());
            }
            if (getChildWidgets() != null) {
                for (Iterator it = getChildWidgets().iterator(); it.hasNext();) {
                    Widget cw = (Widget) it.next();
                    if (cw instanceof MenuItem) {
                        cw.addNyxListener(new UpdateButtonsListener(this, cw));
                        cw.setParent(menu);
                        cw.setPart(getPart());
                        menu.addChildWidget(cw);
//                        System.err.println("Adding childwidget : "+cw);
                    }
                }
            }
        }
        if (menu != null) {
            menu.setParent(this);
            menu.initialize();
            jtree.addMouseListener(new PopupListener(this.menu));
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (jtree != null) {
            if (jtree.getSelectionPath() != null) {
                return jtree.getSelectionPath().getLastPathComponent();
            }
        }
        return null;
    }
    
    /**
     * 
     * @return the tree model. It is a convenience method for internal use only. 
     */
    public SwingTreeModel getSwingModel() {
        return this.contentHandler;
    }

}
