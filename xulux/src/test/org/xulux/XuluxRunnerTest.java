/*
   $Id: XuluxRunnerTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
   
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
package org.xulux;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The test for nyxrunner
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XuluxRunnerTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
 */
public class XuluxRunnerTest extends TestCase {

    /**
     * Constructor for XuluxRunnerTest.
     * @param name the name of the test
     */
    public XuluxRunnerTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(XuluxRunnerTest.class);
        return suite;
    }

    /**
     * Test the nyxrunner
     */
    public void testNyxRunner() {
        XuluxRunner runner = new XuluxRunner();
        XuluxRunner.main(null);
    }
}
