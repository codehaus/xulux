/*
   $Id: TreeTest.java,v 1.5 2004-03-16 15:04:15 mvdb Exp $
   
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

import java.io.InputStream;

import javax.swing.tree.TreeNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xulux.core.ApplicationContext;
import org.xulux.core.ApplicationPart;
import org.xulux.core.WidgetConfig;
import org.xulux.dataprovider.contenthandlers.DOMTreeContentHandler;
import org.xulux.dataprovider.contenthandlers.TreeNodeContentHandler;
import org.xulux.gui.PartCreator;

/**
 * Testcase for the tree
 * We keep it simple for now.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeTest.java,v 1.5 2004-03-16 15:04:15 mvdb Exp $
 */
public class TreeTest extends TestCase {

    /**
     * the xml
     */
    public static String xml = "org/xulux/gui/swing/widgets/TreeTest.xml";

    /**
     * Constructor for TableTest.
     * @param name the name of the test
     */
    public TreeTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TreeTest.class);
        return suite;
    }

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        new TreeTest("TreeTest").showSimpleTree();
    }

    /**
     * Show the tree
     */
    public void showSimpleTree() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        WidgetConfig config = ApplicationContext.getInstance().getWidgetConfig("tree");
        config.addWidgetTool("swing", TreeNodeContentHandler.class);
        config.addWidgetTool(null, DOMTreeContentHandler.class);
        ApplicationPart part = PartCreator.createPart(null, stream);
        part.activate();
    }

    /**
     * Creates a simple dataset (treenode that is..
     * @return the treenode..
     */
    public static TreeNode getData() {
        TreeTest tt = new TreeTest("TreeTest");
        ExampleTreeNode root = new ExampleTreeNode("Root");
        ExampleTreeNode child10 = new ExampleTreeNode("Child10");
        ExampleTreeNode child11 = new ExampleTreeNode("Child11");
        ExampleTreeNode child12 = new ExampleTreeNode("Child12");
        child10.insert(child11, 0);
        child10.insert(child12, 1);
        root.insert(child10, 0);
        ExampleTreeNode child20 = new ExampleTreeNode("Child20");
        ExampleTreeNode child21 = new ExampleTreeNode("Child21");
        ExampleTreeNode child22 = new ExampleTreeNode("Child22");
        child20.insert(child21, 0);
        child20.insert(child22, 1);
        root.insert(child20, 1);
        ExampleTreeNode child30 = new ExampleTreeNode("Child30");
        ExampleTreeNode child31 = new ExampleTreeNode("Child31");
        ExampleTreeNode child32 = new ExampleTreeNode("Child32");
        child30.insert(child31, 0);
        child30.insert(child32, 1);
        root.insert(child30, 2);
        return root;
    }

    /**
     * @param caller the caller
     * @return the document
     */
    public static Document getDocument(Object caller) {
        // use the same classloader as the caller..
        InputStream stream = caller.getClass().getClassLoader().getResourceAsStream(xml);
        Document document = null;
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(stream);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
        System.out.println("document : " + document);
        return document;
    }
}
