/*
   $Id: SwingTreeModel.java,v 1.6 2004-07-07 17:43:42 mvdb Exp $
   
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
package org.xulux.swing.models;

import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.xulux.dataprovider.contenthandlers.IContentHandler;
import org.xulux.dataprovider.contenthandlers.TreeContentHandler;

/**
 * A cutom tree root, so we can do magic of our own
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingTreeModel.java,v 1.6 2004-07-07 17:43:42 mvdb Exp $
 */
public class SwingTreeModel extends TreeContentHandler implements TreeModel {

    /**
     * the contenthandler
     */
    private TreeContentHandler contentHandler;
    /**
     * the listenerlist
     */
    private ArrayList listenerList;
    /**
     * @param contentHandler the tree contentHandler to use..
     */
    public SwingTreeModel(TreeContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    /**
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener l) {
        if (listenerList == null) {
            listenerList = new ArrayList();
        }
        listenerList.add(l);
    }

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        return contentHandler.getChild(parent, index);
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (contentHandler == null) {
            return 0;
        }
        return contentHandler.getChildCount(parent);
    }

    /**
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        return contentHandler.getIndexOfChild(parent, child);
    }

    /**
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot() {
        if (contentHandler == null || contentHandler.getRoot() == null) {
            return "NULLROOT";
        }
        return contentHandler.getRoot();
    }

    /**
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        //System.out.println("contentHandler :" +contentHandler);
        if (contentHandler != null) {
            return contentHandler.isLeaf(node);
        }
        return false;
    }

    /**
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener l) {
        if (listenerList != null) {
            listenerList.remove(l);
        }
    }

    /**
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    /**
     * @see org.xulux.nyx.global.IContentHandler#getType()
     */
    public Class getType() {
        return null;
    }

    /**
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#refresh()
     */
    public void refresh() {
        if (listenerList == null) {
            return;
        }
        TreeModelEvent event = null;
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            Object listener = listenerList.get(i);
            if (listener instanceof TreeModelListener) {
                if (event == null) {
                    event = new TreeModelEvent(this, new TreePath(getRoot()));
                }
                ((TreeModelListener) listener).treeStructureChanged(event);
            }
        }
    }
    /**
     * @return the embedded contenthandler.
     */
    public IContentHandler getInnerContentHandler() {
        return this.contentHandler;
    }

}
