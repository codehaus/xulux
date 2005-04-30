/*
   $Id: CommonsLoggingTest.java,v 1.2 2005-04-30 10:05:25 mvdb Exp $
   
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

import org.apache.commons.logging.impl.SimpleLog;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CommonsLoggingTest.java,v 1.2 2005-04-30 10:05:25 mvdb Exp $
 */

public class CommonsLoggingTest extends TestCase {

    static {
        System.setProperty("org.apache.commons.logging.Log", SimpleLog.class.getName());
//        System.setProperty("org.apache.commons.logging.simplelog.defaultlog", )
    }
    /**
     * Constructor for CommonsLoggingTest.
     * @param name the name of the test
     */
    public CommonsLoggingTest(String name) {
        super(name);
    }
    
    public void testInitDestroy() {
        System.out.println("testInitDestroy");
        CommonsLogging cl = new CommonsLogging();
        cl.init();
        cl.destroy();
    }
    
    public void testLog() {
        System.out.println("testLog");
        CommonsLogging cl = new CommonsLogging();
        // don't crash on this..
        cl.log(0, null, null);
        cl.log(0, "test", null);
    }
    
    public void testLogException() {
        System.out.println("testLogException");
        CommonsLogging cl = new CommonsLogging();
        cl.log(0, null, null, null);
        cl.log(0, "test", null, null);
    }

}
