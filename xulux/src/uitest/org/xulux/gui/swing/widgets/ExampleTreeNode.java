/*
   $Id: ExampleTreeNode.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * An example tree node.. Should turn out to be the big TreeNode in the end..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ExampleTreeNode.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class ExampleTreeNode implements MutableTreeNode {

    /**
     * the children
     */
    private List children;
    /**
     * the userobject
     */
    private Object userObject;
    /**
     * the parent
     */
    private MutableTreeNode parent;

    /**
     *
     */
    public ExampleTreeNode() {
        children = new ArrayList();
    }

    /**
     * @param userObject the userObject
     */
    public ExampleTreeNode(Object userObject) {
        this();
        this.userObject = userObject;
    }

    /**
     * @see javax.swing.tree.MutableTreeNode#insert(javax.swing.tree.MutableTreeNode, int)
     */
    public void insert(MutableTreeNode child, int index) {
        child.setParent(this);
        children.add(index, child);
    }

    /**
     * @see javax.swing.tree.MutableTreeNode#remove(int)
     */
    public void remove(int index) {
        children.remove(index);

    }

    /**
     * @see javax.swing.tree.MutableTreeNode#remove(javax.swing.tree.MutableTreeNode)
     */
    public void remove(MutableTreeNode node) {
        children.remove(node);

    }

    /**
     * @see javax.swing.tree.MutableTreeNode#setUserObject(java.lang.Object)
     */
    public void setUserObject(Object object) {
        this.userObject = object;
    }

    /**
     * @see javax.swing.tree.MutableTreeNode#removeFromParent()
     */
    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    /**
     * @see javax.swing.tree.MutableTreeNode#setParent(javax.swing.tree.MutableTreeNode)
     */
    public void setParent(MutableTreeNode newParent) {
        this.parent = newParent;
    }

    /**
     * @see javax.swing.tree.TreeNode#getChildAt(int)
     */
    public TreeNode getChildAt(int childIndex) {
        return (TreeNode) children.get(childIndex);
    }

    /**
     * @see javax.swing.tree.TreeNode#getChildCount()
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * @see javax.swing.tree.TreeNode#getParent()
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /**
     * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
     */
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    /**
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    public boolean isLeaf() {
        return false;
    }

    /**
     * @see javax.swing.tree.TreeNode#children()
     */
    public Enumeration children() {
        return Collections.enumeration(children);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.valueOf("toString() : " + userObject);
    }

    /**
     * @return the string
     */
    public String getString() {
        return String.valueOf(userObject);
    }

}
