/*
   $Id: ContentHandlerAbstractTest.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ContentHandlerAbstractTest.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public class ContentHandlerAbstractTest extends TestCase {

    /**
     * Constructor for ContentHandlerAbstractTest.
     * @param name the name of the test
     */
    public ContentHandlerAbstractTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ContentHandlerAbstractTest.class);
        return suite;
    }

    /**
     * Test all things in the abstract
     */
    public void testAll() {
        System.out.println("testAll");
        BasicContentHandler basic = new BasicContentHandler();
        basic.setContent(null);
        assertNull(basic.getContent());
        basic.setContent("content");
        assertEquals("content", basic.getContent());
        assertNull(basic.getType());
        // doesn't do anything and should'nt probably.
        basic.refresh();
        // check if setting the view works
        basic.setView(String.class);
        assertEquals(String.class, basic.view);

    }
    /**
     * an naked implementation of the contenthandlerabstract
     */
    public class BasicContentHandler extends ContentHandlerAbstract {

        /**
         * @see org.xulux.nyx.global.contenthandlers.ContentHandlerAbstract#getContent()
         */
        public Object getContent() {
            return this.content;
        }

        /**
         * @see org.xulux.nyx.global.contenthandlers.ContentHandlerAbstract#getType()
         */
        public Class getType() {
            return null;
        }
    }
}
