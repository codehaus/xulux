/*
   $Id: ApplicationContextTest.java,v 1.2 2004-01-28 15:22:05 mvdb Exp $
   
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The applicationContext test
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationContextTest.java,v 1.2 2004-01-28 15:22:05 mvdb Exp $
 */
public class ApplicationContextTest extends TestCase {

    /**
     * Constructor for ApplicationContextTest.
     * @param name the name of the test
     */
    public ApplicationContextTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ApplicationContextTest.class);
        return suite;
    }

    /**
     * Test the setting and getting of parts
     */
    public void testGetPart() {
        System.out.println("testGetPart");
        ApplicationPart part = new ApplicationPart("part");
        ApplicationPart part2 = new ApplicationPart("part2");
        ApplicationContext context = ApplicationContext.getInstance();
        context.registerPart(part);
        context.registerPart(part2);
        assertNotNull(context.getParts());
    }

}
