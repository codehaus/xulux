/*
 $Id: WidgetConfigTest.java,v 1.5 2003-10-27 15:30:14 mvdb Exp $

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
package org.xulux.nyx.context;

import org.xulux.nyx.global.IContentHandler;
import org.xulux.nyx.gui.IWidgetInitializer;
import org.xulux.nyx.gui.Widget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the widgetConfig
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfigTest.java,v 1.5 2003-10-27 15:30:14 mvdb Exp $
 */
public class WidgetConfigTest extends TestCase
{

    /**
     * Constructor for WidgetConfigTest.
     * @param name
     */
    public WidgetConfigTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(WidgetConfigTest.class);
        return suite;
    }
    
    public void testWidgetConfig()
    {
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
    public void testEmptyWidgetConfig()
    {
        System.out.println("testEmptyWidgetConfig");
        WidgetConfig config = new WidgetConfig();
        assertNull(config.get("swt"));
        assertNull(config.get("swing"));
        assertNull(config.getCoreClass());
    }
    
    public void testWidgetInitializers() {
        System.out.println("testWidgetInitializers");
        WidgetConfig config = new WidgetConfig();
        config.add("swt", Integer.class);
        config.add("swing", Integer.class);
        config.addWidgetInitializer("swing", initOne.class);
        assertEquals(1,config.getWidgetInitializers("swing").size());
        config.addWidgetInitializer("swing", initTwo.class);
        assertEquals(2,config.getWidgetInitializers("swing").size());
        assertNull(config.getWidgetInitializers("swt"));
        config.addWidgetInitializer("swt", initTwo.class);
        assertEquals(1, config.getWidgetInitializers("swt").size());
    }
    
    public void testgetContentHandler() {
        System.out.println("testGetContentHandler");
        WidgetConfig config = new WidgetConfig();
        Content1 content1 = new Content1();
        config.addWidgetTool(null,content1.getClass());
        assertEquals(Content1.class, config.getContentHandler(Content1.class).getClass());
        // now test if we get content1 handler back when using content2 which extends from content1 
        // (in this scenario content1 and 2 are used as handlers AS well AS the object to to handle :)
        assertEquals(Content1.class, config.getContentHandler(Content2.class).getClass());
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
