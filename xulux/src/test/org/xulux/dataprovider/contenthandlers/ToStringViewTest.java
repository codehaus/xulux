/*
   $Id: ToStringViewTest.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
   
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
package org.xulux.dataprovider.contenthandlers;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ToStringViewTest.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
 */
public class ToStringViewTest extends TestCase {

    /**
     * Constructor for ToStringViewTest.
     * @param name the name of the test
     */
    public ToStringViewTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ToStringViewTest.class);
        return suite;
    }

    /**
     * Test the ToStringView innerClass
     */
    public void testToStringView() {
        System.out.println("testToStringView");
        ContentView view = new ToStringView("Hi");
        assertEquals("Hi", view.toString());
        view = new ToStringView(null);
        assertNull(view.toString());
        view = ContentView.createView(null, "Test");
        assertNotNull(view);
        assertTrue("Should be instanceof ToStringView", view instanceof ToStringView);
    }
}
