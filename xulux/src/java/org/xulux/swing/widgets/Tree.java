/*
   $Id: Tree.java,v 1.19 2005-01-10 18:16:33 mvdb Exp $
   
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.xulux.core.WidgetConfig;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.contenthandlers.ContentView;
import org.xulux.dataprovider.contenthandlers.TreeContentHandler;
import org.xulux.gui.ContainerWidget;
import org.xulux.gui.IContentWidget;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.swing.listeners.ImmidiateTreeSelectionListener;
import org.xulux.swing.listeners.MouseDoubleClickTreeListener;
import org.xulux.swing.listeners.NewSelectionListener;
import org.xulux.swing.listeners.PopupListener;
import org.xulux.swing.listeners.UpdateButtonsListener;
import org.xulux.swing.models.NyxTreeCellRenderer;
import org.xulux.swing.models.SwingTreeModel;
import org.xulux.utils.BooleanUtils;
import org.xulux.utils.ClassLoaderUtils;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Tree.java,v 1.19 2005-01-10 18:16:33 mvdb Exp $
 */
public class Tree extends ContainerWidget implements IContentWidget {

    /**
     * The native jtree
     */
    protected JTree jtree;
    /**
     * the native scrollpane
     */
    protected JScrollPane scrollPane;
    /**
     * the content
     */
    protected Object content;
    /**
     * the content has changed
     */
    protected boolean contentChanged;
    /**
     * the contenthandler
     */
    protected SwingTreeModel contentHandler;
    /**
     * has childpopups
     */
    protected boolean hasChildPopups;
    /**
     * the menu
     */
    protected Widget menu;
    /**
     * the cell renderer
     */
    protected NyxTreeCellRenderer cellRenderer;
    /**
     * the selectionlistener
     */
    protected NewSelectionListener selectionListener;
    /**
     * the immidiatelistener
     */
    protected ImmidiateTreeSelectionListener immidiateListener;
    
    protected MouseDoubleClickTreeListener mdcListener;

    /**
     * @param name the name of the tree
     */
    public Tree(String name) {
        super(name);
    }

    /**
     * @see org.xulux.gui.Widget#destroy()
     * @todo Destroy it better than now!
     */
    public void destroy() {
        if (!initialized) {
            return;
        }
        super.destroy();
        cellRenderer = null;
        if (mdcListener != null) {
        	jtree.removeMouseListener(mdcListener);
        	mdcListener = null;
        }
        if (selectionListener != null) {
            jtree.removeTreeSelectionListener(selectionListener);
            selectionListener = null;
        }
        if (immidiateListener != null) {
            jtree.removeTreeSelectionListener(immidiateListener);
            immidiateListener = null;
        }
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
        if (this.contentHandler == null) {
            this.contentHandler = new SwingTreeModel(null);
        }
        jtree = new JTree(this.contentHandler);
        if (selectionListener == null) {
            selectionListener = new NewSelectionListener(this);
        }
        jtree.addTreeSelectionListener(selectionListener);
        if (immidiateListener == null) {
            immidiateListener = new ImmidiateTreeSelectionListener(this);
        }
        jtree.addTreeSelectionListener(immidiateListener);
        cellRenderer = new NyxTreeCellRenderer(this);
        // @todo add support for more than one node selection
        jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jtree.setCellRenderer(this.cellRenderer);
        scrollPane = new JScrollPane(jtree);
        initializeChildren();
        initialized = true;
        contentChanged();
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
        if (getChildWidgets() != null) {
          // we need to set the cellrenderer..
          Widget child = (Widget) getChildWidgets().get(0);
        }
        boolean showRoot = true;
        if (getProperty("showRoot") != null) {
            showRoot = BooleanUtils.toBoolean(getProperty("showRoot"));
        }
        jtree.setRootVisible(showRoot);

        if (contentChanged) {
            if (getProperty("contentview") != null) {
                contentHandler.setView(ClassLoaderUtils.getClass((String)getProperty("contentview")));
            }
            contentHandler.refresh();
            jtree.setModel(contentHandler);
            contentChanged = false;
        }
        if (getProperty("collapse") != null) {
            if (getContent() != null) {
              setProperty("collapse", null);
            }
            // collapsetree..
            //            System.err.println("Collapsing!!");
            if (jtree != null && contentHandler != null) {
                // @todo really make this flexible!
                String collapseUntill = getProperty("collapse-untill");
                if (getContent() != null) {
                  setLazyProperty("collapse-untill", null);
                }
                jtree.collapsePath(new TreePath(contentHandler.getRoot()));
                if (collapseUntill != null) {
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
            if (getContent() != null) {
              setLazyProperty("expand", null);
            }
            if (jtree != null && contentHandler != null) {
                String expandUntill = getProperty("expand-untill");
                if (getContent() != null) {
                  setProperty("expand-untill", null);
                }
                expandTree(expandUntill);
            }
        }
        jtree.setShowsRootHandles(true);
        
        // default is on, so we only process if the value is false.
        if (!BooleanUtils.toBoolean(getProperty("showicons"))) {
            if (jtree != null) {
                if (jtree.getCellRenderer() instanceof DefaultTreeCellRenderer) {
                    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jtree.getCellRenderer();
                    renderer.setOpenIcon(null);
                    renderer.setClosedIcon(null);
                    renderer.setLeafIcon(null);
                }
            }
        }
        
        if (mdcListener == null && getProperty("doubleclick") != null) {
        	mdcListener = new MouseDoubleClickTreeListener(this);
        	jtree.addMouseListener(mdcListener);
        }
        jtree.setEnabled(isEnabled());
        jtree.setVisible(isVisible());
        scrollPane.setPreferredSize(getRectangle().getRectangle().getSize());
        initializePopupMenu();
        isRefreshing = false;
    }

    /**
     * Expands the tree to the specified level.
     * Level 0 is the root of the tree.
     * If level is higher than the levels present, it will just collapse all.
     *
     * @param root the root level
     * @param level the level
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
            if (expandPath.size() - 1 != level) {
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

    /**
     * Does not do anything atm.
     * @param root the root object
     */
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
            expand(root, true);
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
     * @todo Document this... or move to treecontenthandler!
     * @param root the root object
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
     * @param root the root object
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
     * @see org.xulux.nyx.gui.IContentWidget#setContent(java.lang.Object)
     */
    public void setContent(Object object) {
        //        System.err.println("setContent called");
        //setProperty("content", content);
        this.content = object;
        if (object != null) {
            String cHProp = getProperty("contenthandler");
            TreeContentHandler handler = null;
            if (cHProp != null) {
              handler = (TreeContentHandler) ClassLoaderUtils.getObjectFromClassString(cHProp);
            } else {
                WidgetConfig config = XuluxContext.getGuiDefaults().getWidgetConfig(getWidgetType());
                handler = (TreeContentHandler) config.getContentHandler(object.getClass());
            }
//            System.out.println("handler for tree : " + handler);
            if (handler == null) {
                System.err.println("Handler for content " + object.getClass() + " not found");
            } else {
                handler.setWidget(this);
                handler.setContent(object);
//                System.err.println("handler content "+handler.getContent());
                this.contentHandler = new SwingTreeModel(handler);
                this.contentHandler.setWidget(this);
                this.contentHandler.setContent(object);
                if (getProperty("contentview") != null) {
                    Class contentView = ClassLoaderUtils.getClass(getProperty("contentview"));
                    this.contentHandler.setView(contentView);
                }
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
        if (getProperty("content") != null) {
            String c = getProperty("content");
            String cType = getProperty("content.type");
            if (cType == null || cType.equalsIgnoreCase("use")) {
              IMapping mapping = XuluxContext.getDictionary().getMapping(getPart().getBean());
              IField field = mapping.getField(c);
              setContent(field.getValue(getPart().getBean()));
            }
        } else if (getProperty("content.type") != null) {
            if ("bean".equalsIgnoreCase(getProperty("content.type"))) {
                setContent(getPart().getBean());
            }
        }
      if (contentHandler != null) {
          if (getProperty("contentview") != null) {
              //contentHandler.get
              contentHandler.getInnerContentHandler().setView(ClassLoaderUtils.getClass((String)getProperty("contentview")));
          }
          contentHandler.refresh();
      }
    }

    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(org.xulux.nyx.gui.Widget)
     */
    public void addToParent(Widget widget) {
        if (widget instanceof PopupMenu || widget instanceof MenuItem) {
            hasChildPopups = true;
        } else {
            cellRenderer.setChildWidget(widget);
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
                menu = WidgetFactory.getWidget("popupmenu", "PopupMenu:" + getName());
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
                    }
                }
            }
        }
        if (menu != null) {
            menu.setParent(this);
            menu.initialize();
            jtree.addMouseListener(new PopupListener(this.menu, this));
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (jtree != null) {
            if (jtree.getSelectionPath() != null) {
                Object obj = jtree.getSelectionPath().getLastPathComponent();
                if (obj instanceof ContentView) {
                    obj = ((ContentView)obj).getSource();
                } 
                return obj;
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
