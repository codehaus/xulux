/*
 $Id: GuiDefaultsTest.java,v 1.7 2003-12-01 02:08:21 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.guidefaults;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import javax.swing.tree.TreeNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dom4j.Document;
import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.WidgetConfig;
import org.xulux.nyx.global.contenthandlers.TreeNodeContentHandler;

/**
 * Tests processing of guiDefaults.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsTest.java,v 1.7 2003-12-01 02:08:21 mvdb Exp $
 */
public class GuiDefaultsTest extends TestCase {

    /**
     * Constructor for GuiDefaultsTest.
     * @param name the name of the test
     */
    public GuiDefaultsTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(GuiDefaultsTest.class);
        return suite;
    }

    /**
     * Test some basic things about gui defaults.
     * @throws Exception just in case
     * @todo make everything bogus, also the widgets!
     */
    public void testGuiDefaults() throws Exception {
        GuiDefaults defaults = new GuiDefaults();
        System.out.println("testGuiDefaults");
        HashMap map = ApplicationContext.getInstance().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("combo");
        assertNotNull(config);
        assertEquals(Class.forName("org.xulux.nyx.swing.widgets.Combo"), config.get("swing"));
        assertEquals("swing", ApplicationContext.getInstance().getDefaultWidgetType());
        assertNotNull(ApplicationContext.getInstance().getParentWidgetHandler());
        assertNotNull(ApplicationContext.getInstance().getParentWidgetHandler("swing"));
        assertNotNull(ApplicationContext.getInstance().getNativeWidgetHandler());
        assertNotNull(ApplicationContext.getInstance().getNativeWidgetHandler("swing"));
    }

    /**
     * Test the exceptions in read..
     */
    public void testRead() {
        System.out.println("testRead");
        GuiDefaultsHandler handler = new GuiDefaultsHandler();
        handler.read(null);
        StringReader reader = new StringReader("<guidefaults defaultType=\"swing\">");
        handler.read(new ByteArrayInputStream("<guidefaults defaultType=\"swing\">".getBytes()));
    }
    /**
     * Test an xml interface without defaulttype
     */
    public void testWithoutDefaultType() {
        System.out.println("testWithoutDefaultType");
        GuiDefaultsHandler handler = new GuiDefaultsHandler();
        handler.read(getClass().getClassLoader().getResourceAsStream("org/xulux/nyx/guidefaults/GuiDefaultsTest2.xml"));
        assertNotNull(ApplicationContext.getInstance().getWidgetConfig("testbutton"));
        assertTrue( ApplicationContext.getInstance().getParentWidgetHandler() instanceof BogusParentWidgetHandler);
    }

    /**
     * Test overriding the gui defaults with new defaults.
     */
    public void testCustomGuiDefaults() {
        System.out.println("testCustomGuiDefaults");
        ApplicationContext.getInstance();
        ApplicationContext.getInstance().initializeGuiDefaults("org/xulux/nyx/guidefaults/GuiDefaultsTest.xml");
        HashMap map = ApplicationContext.getInstance().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("window");
        List list = config.getWidgetInitializers("swing");
        assertEquals(1, list.size());
        config = ApplicationContext.getInstance().getWidgetConfig("tree");
        assertNull(config.getContentHandler(Document.class));
        assertNotNull(config.getContentHandler(TreeNode.class));
    }

}
