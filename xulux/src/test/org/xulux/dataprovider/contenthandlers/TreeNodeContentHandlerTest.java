/*
   $Id: TreeNodeContentHandlerTest.java,v 1.1 2004-03-16 14:35:12 mvdb Exp $
   
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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.xulux.gui.IContentWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the javax.swing.TreeNode contenthandler
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeNodeContentHandlerTest.java,v 1.1 2004-03-16 14:35:12 mvdb Exp $
 */
public class TreeNodeContentHandlerTest extends TestCase {

    /**
     * Constructor for TreeNodeContentHandlerTest.
     * @param name the name of the test
     */
    public TreeNodeContentHandlerTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TreeNodeContentHandlerTest.class);
        return suite;
    }

    /**
     * Test the constructor
     */
    public void testConstructor() {
        System.out.println("testConstructor");
        TreeNodeContentHandler handler = new TreeNodeContentHandler();
        assertEquals(TreeNode.class, handler.getType());
        assertNull(handler.getContent());
        assertNull(handler.getWidget());
        handler = new TreeNodeContentHandler(null);
        assertNull(handler.getWidget());
    }

    /**
     * Test the treenode implementation in nyx
     * For now the model is exactly the same as the treemodel.
     */
    public void testTreeNode() {
        IContentWidget w = getTree();
        TreeNodeContentHandler handler = new TreeNodeContentHandler(w);
        assertEquals(w, handler.getWidget());
        assertEquals(w.getContent(), handler.getContent());
        assertEquals(w.getContent(), handler.getRoot());
        assertEquals(2, handler.getChildCount(w.getContent()));
        Object child1 = handler.getChild(w.getContent(), 0);
        assertEquals("child1", child1.toString());
        assertEquals(3, handler.getChildCount(handler.getChild(w.getContent(), 0)));
        Object child11 = handler.getChild(child1, 0);
        assertEquals("child11", child11.toString());
        Object child12 = handler.getChild(child1, 1);
        assertEquals("child12", child12.toString());
        // no children present
        assertTrue(handler.isLeaf(handler.getChild(child1, 0)));
        assertTrue(handler.isLeaf(handler.getChild(child1, 1)));
        assertFalse(handler.isLeaf(child1));
        Object child13 = handler.getChild(child1, 2);
        assertTrue(handler.isLeaf(child13));
        assertEquals(0, handler.getIndexOfChild(child1, child11));
        assertEquals(1, handler.getIndexOfChild(child1, child12));
    }
    /**
     * @return a contentwidget with a tree of treenodes.
     */
    public IContentWidget getTree() {
        IContentWidget w = new ContentHandlerWidget();
        InternalTreeNode tree = new InternalTreeNode();
        tree.setUserObject("ROOT");
        InternalTreeNode child1 = new InternalTreeNode("child1");
        tree.insert(child1, tree.getChildCount());
        child1.setParent(tree);
        InternalTreeNode child11 = new InternalTreeNode("child11");
        child1.insert(child11, child1.getChildCount());
        child11.setParent(child1);
        InternalTreeNode child12 = new InternalTreeNode("child12");
        child1.insert(child12, child1.getChildCount());
        child12.setParent(child1);
        child12.setLeaf(false);
        InternalTreeNode child13 = new InternalTreeNode("child13");
        child1.insert(child13, child1.getChildCount());
        child13.setParent(child1);
        child13.setLeaf(true);
        InternalTreeNode child2 = new InternalTreeNode("child2");
        tree.insert(child2, tree.getChildCount());
        child1.setParent(tree);
        System.out.println("tree : " + tree.getChildCount());
        w.setContent(tree);
        return w;
    }

    /**
     * Override isLeaf...
     */
    public class InternalTreeNode extends DefaultMutableTreeNode {

        /**
         * leaf is false
         */
        private boolean leaf = false;

        /**
         *
         */
        public InternalTreeNode() {
            super();
        }

        /**
         * @param userObject the userObject
         */
        public InternalTreeNode(Object userObject) {
            super(userObject);
        }

        /**
         * @param userObject the userObject
         * @param allowsChildren does it allowchildren
         */
        public InternalTreeNode(Object userObject, boolean allowsChildren) {
            super(userObject, allowsChildren);
        }

        /**
         * @see javax.swing.tree.TreeNode#isLeaf()
         */
        public boolean isLeaf() {
            return leaf;
        }

        /**
         * @param leaf true if a leaf or false if not
         */
        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }
    }
}
