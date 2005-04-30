/*
   $Id: Log4jLoggerTest.java,v 1.2 2005-04-30 10:05:25 mvdb Exp $
   
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

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Log4jLoggerTest.java,v 1.2 2005-04-30 10:05:25 mvdb Exp $
 */

public class Log4jLoggerTest extends TestCase {

    static Log4jAppender appender = new Log4jAppender();
    
    static {
        Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
    }
    
    /**
     * Constructor for Log4jLoggerTest.
     * @param name the name of the test
     */
    public Log4jLoggerTest(String name) {
        super(name);
    }
    
    public void testInitDestroy() {
        System.out.println("testInitDestroy");
        Log4jLogger ll = new Log4jLogger();
        ll.init();
        ll.destroy();
    }

    public void testLog() {
        System.out.println("testLog");
        Log4jLogger log = new Log4jLogger();
        log.log(0, null, null);
        log.log(0, "test", null);
        assertEquals(1,appender.getLogList().size());
    }
    
    public void testLogException() {
        System.out.println("testLogException");
        Log4jLogger log = new Log4jLogger();
        log.log(0, null, null, null);
        log.log(0, "test", null, null);
        assertEquals(1, appender.getLogList().size());
    }

    protected void tearDown() throws Exception {
        // reset the logging data..
        appender.close();
    }
}
