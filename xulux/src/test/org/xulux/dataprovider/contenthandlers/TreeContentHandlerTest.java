/*
   $Id: TreeContentHandlerTest.java,v 1.1 2004-03-16 14:35:12 mvdb Exp $
   
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

import org.xulux.gui.IContentWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test of the abstract treeContenthandler
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeContentHandlerTest.java,v 1.1 2004-03-16 14:35:12 mvdb Exp $
 */
public class TreeContentHandlerTest extends TestCase {

    /**
     * Constructor for TreeContentHandlerTest.
     * @param name the name of the test
     */
    public TreeContentHandlerTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TreeContentHandlerTest.class);
        return suite;
    }

    /**
     * Test the complete abstract
     */
    public void testAll() {
        System.out.println("testAll");
        InnerTreeContentHandler handler = new InnerTreeContentHandler();
        assertNull(handler.getType());
        assertNull(handler.getContent());
        handler.setContent("one");
        assertEquals("one", handler.getContent());
        IContentWidget w = new ContentHandlerWidget();
        handler = new InnerTreeContentHandler(w);
        assertEquals(w, handler.getWidget());
        w.setContent("one");
        assertEquals("one", handler.getContent());
    }

    /**
     * A clean implementation of the TreeContentHandler
     */
    public class InnerTreeContentHandler extends TreeContentHandler {

        /**
         *
         */
        public InnerTreeContentHandler() {
            super();
        }

        /**
         * @param widget the content widget
         */
        public InnerTreeContentHandler(IContentWidget widget) {
            super(widget);
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getChild(java.lang.Object, int)
         */
        public Object getChild(Object parent, int index) {
            return null;
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getChildCount(java.lang.Object)
         */
        public int getChildCount(Object parent) {
            return 0;
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getIndexOfChild(java.lang.Object, java.lang.Object)
         */
        public int getIndexOfChild(Object parent, Object child) {
            return 0;
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getRoot()
         */
        public Object getRoot() {
            return null;
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#isLeaf(java.lang.Object)
         */
        public boolean isLeaf(Object node) {
            return false;
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.ContentHandlerAbstract#getType()
         */
        public Class getType() {
            return null;
        }
    }
}
