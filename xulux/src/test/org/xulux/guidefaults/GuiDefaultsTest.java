/*
   $Id: GuiDefaultsTest.java,v 1.4 2004-01-28 15:22:09 mvdb Exp $
   
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
package org.xulux.guidefaults;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import javax.swing.tree.TreeNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dom4j.Document;
import org.xulux.context.ApplicationContext;
import org.xulux.context.WidgetConfig;

/**
 * Tests processing of guiDefaults.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsTest.java,v 1.4 2004-01-28 15:22:09 mvdb Exp $
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
        assertEquals(Class.forName("org.xulux.swing.widgets.Combo"), config.get("swing"));
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
        handler.read(getClass().getClassLoader().getResourceAsStream("org/xulux/guidefaults/GuiDefaultsTest2.xml"));
        assertNotNull(ApplicationContext.getInstance().getWidgetConfig("testbutton"));
        assertTrue(ApplicationContext.getInstance().getParentWidgetHandler() instanceof BogusParentWidgetHandler);
    }

    /**
     * Test overriding the gui defaults with new defaults.
     */
    public void testCustomGuiDefaults() {
        System.out.println("testCustomGuiDefaults");
        ApplicationContext.getInstance();
        ApplicationContext.getInstance().initializeGuiDefaults("org/xulux/guidefaults/GuiDefaultsTest.xml");
        HashMap map = ApplicationContext.getInstance().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("window");
        List list = config.getWidgetInitializers("swing");
        assertEquals(1, list.size());
        config = ApplicationContext.getInstance().getWidgetConfig("tree");
        assertNull(config.getContentHandler(Document.class));
        assertNotNull(config.getContentHandler(TreeNode.class));
    }

}
