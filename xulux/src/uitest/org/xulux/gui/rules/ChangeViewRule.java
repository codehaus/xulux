/*
   $Id: ChangeViewRule.java,v 1.3 2004-10-20 17:30:20 mvdb Exp $
   
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
package org.xulux.gui.rules;

import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import org.xulux.core.PartRequest;
import org.xulux.dataprovider.contenthandlers.ContentView;
import org.xulux.dataprovider.contenthandlers.SimpleDOMView;
import org.xulux.dataprovider.contenthandlers.ToStringView;
import org.xulux.gui.IContentWidget;
import org.xulux.gui.Widget;
import org.xulux.rules.Rule;
import org.xulux.swing.models.SwingTreeModel;
import org.xulux.swing.widgets.Tree;

/**
 * Changes the view of a domtree to a toStringview and back
 * This is to test if dynamic viewchanges actually work.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ChangeViewRule.java,v 1.3 2004-10-20 17:30:20 mvdb Exp $
 */
public class ChangeViewRule extends Rule {

    /**
     * 
     */
    public ChangeViewRule() {
        super();
    }

    /**
     * @see org.xulux.rules.IRule#pre(org.xulux.core.PartRequest)
     */
    public void pre(PartRequest request) {

    }

    /**
     * @see org.xulux.rules.IRule#post(org.xulux.core.PartRequest)
     */
    public void post(PartRequest request) {
        if (request.getWidget().getName().equals("ChangeView")) {
            Widget w = request.getWidget("DomTree");
            String view = w.getProperty("contentview");
            if (view == null || view.endsWith("SimpleDOMView")) {
                request.getWidget().setProperty("text", "SimpleDOMView");
                w.setLazyProperty("contentview", ToStringView.class.getName());
                ((IContentWidget) w).contentChanged();
            } else {
                request.getWidget().setProperty("text", "ToStringView");
                w.setLazyProperty("contentview", SimpleDOMView.class.getName());
                ((IContentWidget) w).contentChanged();
            }
            w.refresh();
        } else if (request.getWidget().getName().equals("enabletree")) {
            Widget w = request.getWidget("DomTree");
            w.setEnable(!w.isEnabled());
            if (w.isEnabled()) {
                request.getWidget().setProperty("text", "Disable Tree");
            } else {
                request.getWidget().setProperty("text", "Enable Tree");
            }
        } else if (request.getWidget().getName().equals("expandall")) {
            Tree w = (Tree) request.getWidget("DomTree");
            System.out.println("w : " + w.getNativeWidget());
            JTree tree = (JTree) ((JScrollPane)w.getNativeWidget()).getViewport().getView();
            tree.addTreeExpansionListener(new TreeExpansionListener() {
              public void treeCollapsed(TreeExpansionEvent event) {
                System.out.println("Collapsed : " + event.getPath());
              }

              public void treeExpanded(TreeExpansionEvent event) {
                System.out.println("Expanded : " + event.getPath());
              }
            });
            SwingTreeModel model = w.getSwingModel();
            System.err.println("class : " + model.getRoot().getClass()); 
            expandAll(tree, new TreePath(((ContentView)model.getRoot()).getSource()), model, true);
            System.out.println("RowCount : " + tree.getRowCount());
//            tree.expandRow(3);
//            w.setLazyProperty("expand", "true");
//            w.setProperty("expand-untill", "all");
            System.out.println("SelectionPath : " + tree.getSelectionPath());
        }
    }
 
    private void expandAll(JTree tree, TreePath parent, SwingTreeModel model, boolean expand) {
        // Traverse children
        Object node = parent.getLastPathComponent();
        int childCount = model.getChildCount(node);
        if (childCount >= 0) {
            for (int i = 0; i < childCount;i++) {
              Object view =  model.getChild(node, i);
              // we don't want any leafs
              if (model.isLeaf(view)) {
                continue;
              }
              TreePath path = parent.pathByAddingChild(view);
              System.out.println("path : " + Arrays.asList(path.getPath()));
              tree.expandPath(path);
              expandAll(tree, path, model, expand);
            }
//            for (Enumeration e=model..children(); e.hasMoreElements(); ) {
//                TreeNode n = (TreeNode)e.nextElement();
//                TreePath path = parent.pathByAddingChild(n);
//                expandAll(tree, path, expand);
//            }
        }
//        System.out.println("Path : " + parent);
//        System.out.println("isLeaf ? : " + model.isLeaf(parent.getLastPathComponent()));
//    
//        // Expansion or collapse must be done bottom-up
//        if (expand) {
//            tree.expandPath(parent);
//        } else {
//            tree.collapsePath(parent);
//        }
    }    

}
