/*
   $Id: ContentViewTest.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
   
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
 * @version $Id: ContentViewTest.java,v 1.1 2004-03-23 08:42:23 mvdb Exp $
 */
public class ContentViewTest extends TestCase {

    /**
     * Constructor for ContentViewTest.
     *
     * @param name the name of the test
     */
    public ContentViewTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ContentViewTest.class);
        return suite;
    }
    
    public void testConstructor() {
        System.out.println("testConstructor");
        ContentView view = new ContentViewImpl("Test");
        assertEquals("Test", view.getSource());
        view = new ContentViewImpl(null);
        assertNull(view.getSource());
    }
    
    public void testCreateView() {
        System.out.println("testCreateView");
        assertNull(ContentView.createView(null, null));
        assertNull(ContentView.createView(ContentViewImpl.class, null));
        ContentView view = ContentView.createView(ContentViewImpl.class, "Test");
        assertTrue("Not instanceof ContentViewImpl", view instanceof ContentViewImpl);
    }
    
    public class ContentViewImpl extends ContentView {

        /**
         * @param data
         */
        public ContentViewImpl(Object data) {
            super(data);
        }

        /**
         * @see org.xulux.dataprovider.contenthandlers.ContentView#toString()
         */
        public String toString() {
            return null;
        }
    }    
    
}
