/*
   $Id: DOMTreeContentHandlerTest.java,v 1.1 2004-03-16 14:35:12 mvdb Exp $
   
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
 * @version $Id: DOMTreeContentHandlerTest.java,v 1.1 2004-03-16 14:35:12 mvdb Exp $
 */
public class DOMTreeContentHandlerTest extends TestCase {

    /**
     * Constructor for DOMTreeContentHandlerTest.
     * @param name the name of the test
     */
    public DOMTreeContentHandlerTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DOMTreeContentHandlerTest.class);
        return suite;
    }

}
