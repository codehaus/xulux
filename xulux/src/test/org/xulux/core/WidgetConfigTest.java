/*
   $Id: WidgetConfigTest.java,v 1.4 2004-03-25 00:48:09 mvdb Exp $
   
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
package org.xulux.core;

import javax.swing.tree.TreeNode;

import org.dom4j.Document;
import org.dom4j.tree.DefaultDocument;
import org.xulux.dataprovider.contenthandlers.IContentHandler;
import org.xulux.dataprovider.contenthandlers.SimpleDOMView;
import org.xulux.gui.IPropertyHandler;
import org.xulux.gui.IWidgetInitializer;
import org.xulux.gui.Widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the widgetConfig
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfigTest.java,v 1.4 2004-03-25 00:48:09 mvdb Exp $
 */
public class WidgetConfigTest extends TestCase {

    /**
     * Constructor for WidgetConfigTest.
     * @param name the name of the test
     */
    public WidgetConfigTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(WidgetConfigTest.class);
        return suite;
    }

    /**
     * Test the widgetconfig
     */
    public void testWidgetConfig() {
        System.out.println("testWidgetConfig");
        WidgetConfig config = new WidgetConfig();
        Class coreClass = Byte.class;
        Class swingClass = Long.class;
        Class swtClass = Integer.class;
        config.setCoreClass(coreClass);
        config.add("swing", swingClass);
        config.add("swt", swtClass);

        assertEquals(coreClass, config.getCoreClass());
        assertEquals(swingClass, config.get("swing"));
        assertEquals(swtClass, config.get("swt"));
    }

    /**
     * We don't want nullpointer exceptions
     */
    public void testEmptyWidgetConfig() {
        System.out.println("testEmptyWidgetConfig");
        WidgetConfig config = new WidgetConfig();
        assertNull(config.get("swt"));
        assertNull(config.get("swing"));
        assertNull(config.getCoreClass());
    }

    /**
     * Test the widgetInitializers
     */
    public void testWidgetInitializers() {
        System.out.println("testWidgetInitializers");
        WidgetConfig config = new WidgetConfig();
        assertNull(config.getWidgetInitializers("bogus"));
        config.add("swt", Integer.class);
        config.add("swing", Integer.class);
        config.addWidgetInitializer("swing", initOne.class);
        assertEquals(1, config.getWidgetInitializers("swing").size());
        config.addWidgetInitializer("swing", initOne.class);
        assertEquals(1, config.getWidgetInitializers("swing").size());
        config.addWidgetInitializer("swing", initTwo.class);
        assertEquals(2, config.getWidgetInitializers("swing").size());
        assertNull(config.getWidgetInitializers("swt"));
        config.addWidgetInitializer("swt", initTwo.class);
        assertEquals(1, config.getWidgetInitializers("swt").size());
    }

	/**
	 * Test the propertyhandlers
	 */
	public void testPropertyHandlers() {
		System.out.println("testPropertyHandlers");
		WidgetConfig config = new WidgetConfig();
        config.addPropertyHandler(null, null, null, null);
		config.addPropertyHandler("java.lang.Integer", IPropertyHandler.NORMAL,"bogus", "swing");
        assertEquals(null, config.getPropertyHandlers("swing"));
        config.addPropertyHandler(PropHandler.class.getName(), IPropertyHandler.NORMAL, "prop", "swing");
        assertEquals(1, config.getPropertyHandlers("swing").size());
        assertNull(null, config.getPropertyHandlers("swt"));
        config.addPropertyHandler(PropHandler2.class.getName(), IPropertyHandler.NORMAL, "prop2", "swing");
        assertEquals(2, config.getPropertyHandlers("swing").size());
        assertNull(null, config.getPropertyHandlers("swt"));
        
	}
    /**
     * Test the contenthandlers
     */
    public void testgetContentHandler() {
        System.out.println("testGetContentHandler");
        WidgetConfig config = new WidgetConfig();
        assertNull(config.getContentHandler(String.class));
        Content1 content1 = new Content1();
        config.addWidgetTool(null, content1.getClass());
        assertEquals(Content1.class, config.getContentHandler(Content1.class).getClass());
        // now test if we get content1 handler back when using content2 which extends from content1
        // (in this scenario content1 and 2 are used as handlers AS well AS the object to to handle :)
        assertEquals(Content1.class, config.getContentHandler(Content2.class).getClass());
        assertNull(config.getContentHandler(WidgetConfigTest.class));
        // check if bogus entries are also handled.
        config.addWidgetTool(null, "java.lang.String");
        assertNull(config.getContentHandler(String.class));
        config.addWidgetTool(null, (String) null);
        config.addWidgetTool(null, "bogus.class.name");
        config.addWidgetTool(null, "org.xulux.dataprovider.contenthandlers.TreeNodeContentHandler");
        assertNotNull(config.getContentHandler(TreeNode.class));
        config = new WidgetConfig();
        config.addContentHandler("org.xulux.dataprovider.contenthandlers.DOMTreeContentHandler", 
                                  "org.xulux.dataprovider.contenthandlers.SimpleDOMView");
        IContentHandler handler = config.getContentHandler(Document.class);
        assertNotNull(handler);
        assertEquals(SimpleDOMView.class, handler.getViewClass());
        // subclass of Document. It should also return the correct viewclass
        handler = config.getContentHandler(DefaultDocument.class);
        assertNotNull(handler);
        assertEquals(SimpleDOMView.class, handler.getViewClass());
        config.addContentHandler("org.xulux.dataprovider.contenthandlers.TreeNodeContentHandler", null);
        handler = config.getContentHandler(TreeNode.class);
        assertNotNull(handler);
        assertNull(handler.getViewClass());
        // see what happens when passing in nulls..
        config = new WidgetConfig();
        config.addContentHandler(null, null);
        // test a bogus class name..
        config.addContentHandler("bogus.contenthandler", null);
        // set a bogus view class...
        config.addContentHandler("org.xulux.dataprovider.contenthandlers.TreeNodeContentHandler", "bogus.view");
        assertNull(config.getContentHandler(TreeNode.class).getViewClass());
        config.addContentHandler("org.xulux.dataprovider.contenthandlers.TreeNodeContentHandler", "java.lang.String");
        assertNull(config.getContentHandler(TreeNode.class).getViewClass());
        config.addContentHandler("java.lang.String", "java.lang.String");
        // test where the contenthandler fails to instantiate the handler..
        config.addContentHandler(FailInstantiateHandler.class.getName(), "java.lang.String");
    }

    public class Content2 extends Content1 {

        public Content2() {
        }
    }

    public class Content1 implements IContentHandler {

        public Content1() {
        }
        /**
         * @see org.xulux.nyx.global.IContentHandler#getContent()
         */
        public Object getContent() {
            return null;
        }

        /**
         * @see org.xulux.nyx.global.IContentHandler#getType()
         */
        public Class getType() {
            return Content1.class;
        }

        /**
         * @see org.xulux.nyx.global.IContentHandler#refresh()
         */
        public void refresh() {

        }

        /**
         * @see org.xulux.nyx.global.IContentHandler#setContent(java.lang.Object)
         */
        public void setContent(Object content) {

        }
        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#setView(java.lang.Class)
         */
        public void setView(Class view) {
        }
        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getViewClass()
         */
        public Class getViewClass() {
            return null;
        }
    }

    public class initOne implements IWidgetInitializer {
        /**
        * @see org.xulux.nyx.gui.IWidgetInitializer#destroy(org.xulux.nyx.gui.Widget)
        */
        public void destroy(Widget widget) {

        }

        /**
         * @see org.xulux.nyx.gui.IWidgetInitializer#initialize(org.xulux.nyx.gui.Widget)
         */
        public void initialize(Widget widget) {

        }

    }
    public class initTwo implements IWidgetInitializer {
        /**
        * @see org.xulux.nyx.gui.IWidgetInitializer#destroy(org.xulux.nyx.gui.Widget)
        */
        public void destroy(Widget widget) {

        }

        /**
         * @see org.xulux.nyx.gui.IWidgetInitializer#initialize(org.xulux.nyx.gui.Widget)
         */
        public void initialize(Widget widget) {

        }

    }

    public class PropHandler implements IPropertyHandler {

        /**
         * @see org.xulux.gui.IPropertyHandler#init()
         */
        public void init() {
        }

        /**
         * @see org.xulux.gui.IPropertyHandler#handleProperty(org.xulux.gui.Widget, java.lang.String)
         */
        public boolean handleProperty(Widget widget, String property) {
            return false;
        }

        /**
         * @see org.xulux.gui.IPropertyHandler#destroy()
         */
        public void destroy() {
        }

        /**
         * @see org.xulux.gui.IPropertyHandler#getValue()
         */
        public Object getValue() {
            return null;
        }
    }
    
    public class PropHandler2 extends PropHandler {
    }
    
    public class FailInstantiateHandler implements IContentHandler {
        
        public FailInstantiateHandler() {
            throw new RuntimeException();
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#setContent(java.lang.Object)
         */
        public void setContent(Object content) {
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getContent()
         */
        public Object getContent() {
            return null;
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getType()
         */
        public Class getType() {
            return null;
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#refresh()
         */
        public void refresh() {
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#setView(java.lang.Class)
         */
        public void setView(Class view) {
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getViewClass()
         */
        public Class getViewClass() {
            return null;
        }

    }
}
