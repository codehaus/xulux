/*
   $Id: JdkLoggingTest.java,v 1.2 2005-04-30 10:05:25 mvdb Exp $
   
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
package org.xulux.logging;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: JdkLoggingTest.java,v 1.2 2005-04-30 10:05:25 mvdb Exp $
 */

public class JdkLoggingTest extends TestCase {

    /**
     * Constructor for JdkLoggingTest.
     * @param name the name of the test
     */
    public JdkLoggingTest(String name) {
        super(name);
    }

    public void testInitDestroy() {
        System.out.println("testInitDestroy");
        JdkLogging jl = new JdkLogging();
        jl.init();
        jl.destroy();
    }

    public void testLog() {
        JdkLogging log = new JdkLogging();
        log.log(0, null, null);
        log.log(0, "test.test", null);
    }
    
    public void testLogException() {
        JdkLogging log = new JdkLogging();
        log.log(0, null, null, null);
        log.log(0, "test" , null, null);
    }

}
