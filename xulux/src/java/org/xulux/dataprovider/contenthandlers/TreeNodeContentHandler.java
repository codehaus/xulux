/*
   $Id: TreeNodeContentHandler.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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
package org.xulux.dataprovider.contenthandlers;

import javax.swing.tree.TreeNode;

import org.xulux.gui.IContentWidget;

/**
 * A tree content handler using a TreeNode.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeNodeContentHandler.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public class TreeNodeContentHandler extends TreeContentHandler {

    /**
     *
     */
    public TreeNodeContentHandler() {
        super();
    }

    /**
     * @param widget the widget to get the content from
     */
    public TreeNodeContentHandler(IContentWidget widget) {
        super(widget);
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        return ((TreeNode) parent).getChildAt(index);
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        return ((TreeNode) parent).getChildCount();
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        return ((TreeNode) parent).getIndex((TreeNode) child);
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getRoot()
     */
    public Object getRoot() {
        return (TreeNode) getWidget().getContent();
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        boolean isLeaf = ((TreeNode) node).isLeaf();
        if (!isLeaf) {
            if (((TreeNode) node).getChildCount() == 0) {
                isLeaf = true;
            }
        }
        return isLeaf;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getType()
     */
    public Class getType() {
        return TreeNode.class;
    }

}
