/*
   $Id: XuluxContextTest.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
   
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

import org.xulux.logging.Logger;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XuluxContextTest.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
 */

public class XuluxContextTest extends TestCase {

    /**
     * Constructor for XuluxContextTest.
     * @param name the name of the test
     */
    public XuluxContextTest(String name) {
        super(name);
    }
    
    public void testGetInstance() {
        System.out.println("testGetInstance");
        XuluxContext context = XuluxContext.getInstance();
        assertNotNull(context);
        assertEquals(context, XuluxContext.getInstance());
    }
    
    public void testLogger() {
        System.out.println("testLogger");
        Logger logger = XuluxContext.getLogger();
        assertNotNull(logger);
        assertEquals(logger, XuluxContext.getLogger());
    }

    public void testWidgetRegistry() {
        System.out.println("testWidgetRegistry");
        XuluxContext context = XuluxContext.getInstance();
        WidgetRegistry wr = context.getWidgetRegistry();
        assertNotNull(wr);
        assertEquals(wr, context.getWidgetRegistry());
    }
    

}
