/*
   $Id: GuiDefaultsTest.java,v 1.8 2004-05-11 12:56:57 mvdb Exp $
   
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
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dom4j.Document;
import org.xulux.core.WidgetConfig;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.contenthandlers.IContentHandler;
import org.xulux.dataprovider.contenthandlers.SimpleDOMView;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.swing.util.NativeWidgetHandler;
import org.xulux.swing.widgets.Combo;

/**
 * Tests processing of guiDefaults.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsTest.java,v 1.8 2004-05-11 12:56:57 mvdb Exp $
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
        System.out.println("testGuiDefaults");
        Map map = XuluxContext.getGuiDefaults().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("combo");
        assertNotNull(config);
        assertEquals(Class.forName("org.xulux.swing.widgets.Combo"), config.get("swing"));
        assertEquals("swing", XuluxContext.getGuiDefaults().getDefaultWidgetType());
        assertNotNull(XuluxContext.getGuiDefaults().getParentWidgetHandler());
        assertNotNull(XuluxContext.getGuiDefaults().getParentWidgetHandler("swing"));
        assertNotNull(XuluxContext.getGuiDefaults().getNativeWidgetHandler());
        assertNotNull(XuluxContext.getGuiDefaults().getNativeWidgetHandler("swing"));
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
        assertNotNull(XuluxContext.getGuiDefaults().getWidgetConfig("testbutton"));
        assertTrue(XuluxContext.getGuiDefaults().getParentWidgetHandler() instanceof BogusParentWidgetHandler);
    }

    /**
     * Test overriding the gui defaults with new defaults.
     */
    public void testCustomGuiDefaults() {
        System.out.println("testCustomGuiDefaults");
        XuluxContext.getInstance();
        XuluxContext.getInstance().initializeGuiDefaults("org/xulux/guidriver/defaults/GuiDefaultsTest.xml");
        Map map = XuluxContext.getGuiDefaults().getWidgets();
        WidgetConfig config = (WidgetConfig) map.get("window");
        List list = config.getWidgetInitializers("swing");
        assertEquals(1, list.size());
        config = XuluxContext.getGuiDefaults().getWidgetConfig("tree");
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
    
    public void testDefaultWidgetType() {
        System.out.println("testDefaultWidgetType");
        GuiDefaults defaults = new GuiDefaults();
        assertEquals(null, defaults.getDefaultWidgetType());
        defaults.setDefaultWidgetType("default");
        assertEquals("default", defaults.getDefaultWidgetType());
    }
    
    public void testXuluxToolkit() {
        System.out.println("testXuluxToolkit");
        GuiDefaults defaults = new GuiDefaults();
        assertEquals(null, defaults.getXuluxToolkit());
        defaults.registerXuluxToolkit(null, null);
        defaults.registerXuluxToolkit(null, null);
        assertEquals(null, defaults.xuluxToolkits);
        assertEquals(null, defaults.getXuluxToolkit());
        defaults.registerXuluxToolkit(TempXuluxToolkit.class.getName(), "temp");
        assertEquals(TempXuluxToolkit.class, defaults.getXuluxToolkit("temp").getClass());
        defaults.setDefaultWidgetType("temp");
        assertEquals(TempXuluxToolkit.class, defaults.getXuluxToolkit().getClass());
        defaults.registerXuluxToolkit(Temp2XuluxToolkit.class.getName(), "temp2");
        assertEquals(Temp2XuluxToolkit.class, defaults.getXuluxToolkit("temp2").getClass());
        assertEquals(null, defaults.getXuluxToolkit("bogus"));
    }

    public void testWidgets() {
        System.out.println("testWidgets");
        GuiDefaults defaults = new GuiDefaults();
        assertEquals(null, defaults.getWidgetConfig(null));
        assertEquals(null, defaults.getWidget(null));
        assertEquals(null, defaults.getWidget(null, null));
        assertEquals(null, defaults.getWidgets());
        defaults.registerWidget("combo", Combo.class.getName(), "swing");
        assertNull(defaults.getWidget("combo"));
        assertNotNull(defaults.getWidget("combo", "swing"));
        defaults.setDefaultWidgetType("swing");
        assertNotNull(defaults.getWidget("combo"));
        defaults.registerWidget("combo", Combo.class.getName(), "alsoswing");
        assertNotNull(defaults.getWidget("combo", "alsoswing"));
        assertNull(defaults.getWidget("test", null));
    }

    public void testFieldEventHandler() {
        System.out.println("testFieldEventHandler");
        GuiDefaults defaults = new GuiDefaults();
        defaults.registerFieldEventHandler(null, null);
        assertEquals(null, defaults.fieldEventHandlerMap);
        assertEquals(null, defaults.getFieldEventHandler(null));
        TempNyxListener listener = new TempNyxListener();
        defaults.registerFieldEventHandler("swing", TempNyxListener.class.getName());
        assertNotNull(defaults.getFieldEventHandler("swing"));
        defaults.setDefaultWidgetType("swing");
        assertNotNull(defaults.getFieldEventHandler(null));
        defaults.registerFieldEventHandler("swing", getClass().getName());
        assertEquals(1, defaults.fieldEventHandlerMap.size());
        defaults.registerFieldEventHandler("swt", TempNyxListener.class.getName());
        assertNotNull(defaults.getFieldEventHandler("swt"));
        assertEquals(2, defaults.fieldEventHandlerMap.size());
    }

    public void testNativeWidgetHandler() {
        System.out.println("testNativeWidgetHandler");
        GuiDefaults defaults = new GuiDefaults();
        assertEquals(null, defaults.nativeWidgetHandlerMap);
        assertEquals(null, defaults.getNativeWidgetHandler());
        defaults.registerNativeWidgetHandler("swing", NativeWidgetHandler.class.getName());
        assertEquals(null, defaults.getNativeWidgetHandler());
        assertNotNull(defaults.getNativeWidgetHandler("swing"));
        defaults.setDefaultWidgetType("swing");
        assertNotNull(defaults.getNativeWidgetHandler());
        assertEquals(1, defaults.nativeWidgetHandlerMap.size());
        defaults.registerNativeWidgetHandler("swt", NativeWidgetHandler.class.getName());
        assertEquals(2, defaults.nativeWidgetHandlerMap.size());
        // do nothing for these.
        defaults.registerNativeWidgetHandler(null, null);
        defaults.registerNativeWidgetHandler("banana", this.getClass().getName());
        assertEquals(2, defaults.nativeWidgetHandlerMap.size());
        defaults.registerNativeWidgetHandler("temp", Temp2NativeWidgetHandler.class.getName());
    }

    public void testParentWidgetHandler() {
        System.out.println("testParentWidgetHandler");
        GuiDefaults defaults = new GuiDefaults();
        assertEquals(null, defaults.parentWidgetHandlerMap);
        defaults.registerParentWidgetHandler(null, null);
        assertEquals(null, defaults.parentWidgetHandlerMap);
        assertEquals(null, defaults.getParentWidgetHandler());
        assertEquals(null, defaults.getParentWidgetHandler(null));
        defaults.registerParentWidgetHandler("swing", BogusParentWidgetHandler.class.getName());
        assertEquals(null, defaults.getParentWidgetHandler());
        assertNotNull(defaults.getParentWidgetHandler("swing"));
        defaults.setDefaultWidgetType("swing");
        assertNotNull(defaults.getParentWidgetHandler());
        defaults.registerParentWidgetHandler("swt", this.getClass().getName());
        assertEquals(null, defaults.getParentWidgetHandler("swt"));
    }

    public void testLayouts() {
        System.out.println("testLayouts");
        GuiDefaults defaults = new GuiDefaults();
        //defaults.get
    }

    public class Temp2XuluxToolkit extends TempXuluxToolkit {
    }
    
    public class Temp2NativeWidgetHandler extends NativeWidgetHandler {
        private Temp2NativeWidgetHandler() {
        }
    }

}
