/*
 $Id: TreeNodeContentHandlerTest.java,v 1.1 2003-12-18 00:17:31 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.global.contenthandlers;

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
 * @version $Id: TreeNodeContentHandlerTest.java,v 1.1 2003-12-18 00:17:31 mvdb Exp $
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
