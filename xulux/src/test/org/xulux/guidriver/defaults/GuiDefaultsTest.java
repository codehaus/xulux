/*
   $Id: GuiDefaultsTest.java,v 1.5 2004-04-14 14:16:12 mvdb Exp $
   
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
package org.xulux.guidriver.defaults;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dom4j.Document;
import org.xulux.core.XuluxContext;
import org.xulux.core.WidgetConfig;
import org.xulux.dataprovider.contenthandlers.IContentHandler;
import org.xulux.dataprovider.contenthandlers.SimpleDOMView;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;

/**
 * Tests processing of guiDefaults.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsTest.java,v 1.5 2004-04-14 14:16:12 mvdb Exp $
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
        HashMap map = XuluxContext.getInstance().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("combo");
        assertNotNull(config);
        assertEquals(Class.forName("org.xulux.swing.widgets.Combo"), config.get("swing"));
        assertEquals("swing", XuluxContext.getInstance().getDefaultWidgetType());
        assertNotNull(XuluxContext.getInstance().getParentWidgetHandler());
        assertNotNull(XuluxContext.getInstance().getParentWidgetHandler("swing"));
        assertNotNull(XuluxContext.getInstance().getNativeWidgetHandler());
        assertNotNull(XuluxContext.getInstance().getNativeWidgetHandler("swing"));
    }

    /**
     * Test the exceptions in read..
     */
    public void testRead() {
        System.out.println("testRead");
        GuiDefaultsHandler handler = new GuiDefaultsHandler();
        handler.read(null);
        //StringReader reader = new StringReader("<guidefaults defaultType=\"swing\">");
        handler.read(new ByteArrayInputStream("<guidefaults defaultType=\"swing\"/>".getBytes()));
    }
    /**
     * Test an xml interface without defaulttype
     */
    public void testWithoutDefaultType() {
        System.out.println("testWithoutDefaultType");
        GuiDefaultsHandler handler = new GuiDefaultsHandler();
        handler.read(getClass().getClassLoader().getResourceAsStream("org/xulux/guidriver/defaults/GuiDefaultsTest2.xml"));
        assertNotNull(XuluxContext.getInstance().getWidgetConfig("testbutton"));
        assertTrue(XuluxContext.getInstance().getParentWidgetHandler() instanceof BogusParentWidgetHandler);
    }

    /**
     * Test overriding the gui defaults with new defaults.
     */
    public void testCustomGuiDefaults() {
        System.out.println("testCustomGuiDefaults");
        XuluxContext.getInstance();
        XuluxContext.getInstance().initializeGuiDefaults("org/xulux/guidriver/defaults/GuiDefaultsTest.xml");
        HashMap map = XuluxContext.getInstance().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("window");
        List list = config.getWidgetInitializers("swing");
        assertEquals(1, list.size());
        config = XuluxContext.getInstance().getWidgetConfig("tree");
        IContentHandler handler = config.getContentHandler(Document.class);
        assertNotNull(handler);
        assertEquals(SimpleDOMView.class, handler.getViewClass());
        handler = config.getContentHandler(TreeNode.class);
        assertNotNull(handler);
        // overriding the view and setting it to bogus..
        assertEquals(BogusContentView.class, handler.getViewClass());
    }
    
    /**
     * Test setting default properties on the widget during config..
     */
    public void testWidgetDefaults() {
         System.out.println("testWidgetDefaults");
         XuluxContext.getInstance().initializeGuiDefaults("org/xulux/guidriver/defaults/WidgetDefaults.xml");
         Widget widget = WidgetFactory.getWidget("window", "window");
         Map map = widget.getProperties();
         Object test = map.get("test");
         assertNotNull(test);
         assertEquals("true", test);
         Object anotherTest = map.get("anothertest");
         assertNotNull(anotherTest);
         assertEquals("testvalue", anotherTest);
         Object element = map.get("element");
         assertEquals("elementValue", element);
         Object elementType = map.get("element.type");
         assertEquals("attribute", elementType);
    }
}
