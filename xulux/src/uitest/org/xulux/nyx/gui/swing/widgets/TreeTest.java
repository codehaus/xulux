/*
 $Id: TreeTest.java,v 1.5 2003-12-15 19:36:49 mvdb Exp $

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
package org.xulux.nyx.gui.swing.widgets;

import java.io.InputStream;

import javax.swing.tree.TreeNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.WidgetConfig;
import org.xulux.nyx.global.contenthandlers.DOMTreeContentHandler;
import org.xulux.nyx.global.contenthandlers.TreeNodeContentHandler;
import org.xulux.nyx.gui.PartCreator;

/**
 * Testcase for the tree
 * We keep it simple for now.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeTest.java,v 1.5 2003-12-15 19:36:49 mvdb Exp $
 */
public class TreeTest extends TestCase {

    public static String xml = "org/xulux/nyx/gui/swing/widgets/TreeTest.xml";

    /**
     * Constructor for TableTest.
     * @param name
     */
    public TreeTest(String name) {
        super(name);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(TreeTest.class);
        return suite;
    }

    public static void main(String[] args) {
        new TreeTest("TreeTest").showSimpleTree();
    }
    
    
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
        root.insert(child10,0);
        ExampleTreeNode child20 = new ExampleTreeNode("Child20");
        ExampleTreeNode child21 = new ExampleTreeNode("Child21");
        ExampleTreeNode child22 = new ExampleTreeNode("Child22");
        child20.insert(child21, 0);
        child20.insert(child22, 1);
        root.insert(child20,1);
        ExampleTreeNode child30 = new ExampleTreeNode("Child30");
        ExampleTreeNode child31 = new ExampleTreeNode("Child31");
        ExampleTreeNode child32 = new ExampleTreeNode("Child32");
        child30.insert(child31, 0);
        child30.insert(child32, 1);
        root.insert(child30, 2);
        return root;
    }
    
    public static Document getDocument(Object caller) {
        // use the same classloader as the caller..
        InputStream stream = caller.getClass().getClassLoader().getResourceAsStream(xml);
        Document document = null;
        SAXReader reader = new SAXReader();
        try {
            document =  reader.read(stream);
        }catch(DocumentException de) {
            de.printStackTrace();
        }
        System.out.println("document : "+document);
        
        return document;
    }
}
