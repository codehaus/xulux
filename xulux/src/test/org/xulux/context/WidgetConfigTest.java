/*
   $Id: WidgetConfigTest.java,v 1.4 2004-01-28 15:22:05 mvdb Exp $
   
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
package org.xulux.context;

import javax.swing.tree.TreeNode;

import org.xulux.global.IContentHandler;
import org.xulux.gui.IWidgetInitializer;
import org.xulux.gui.Widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the widgetConfig
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfigTest.java,v 1.4 2004-01-28 15:22:05 mvdb Exp $
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
        config.addWidgetTool(null, "org.xulux.global.contenthandlers.TreeNodeContentHandler");
        assertNotNull(config.getContentHandler(TreeNode.class));
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
}
