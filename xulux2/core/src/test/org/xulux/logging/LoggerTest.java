/*
   $Id: LoggerTest.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
   
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

import java.util.HashMap;

import org.xulux.logging.ILog;
import org.xulux.logging.Logger;

import junit.framework.TestCase;

/**
 * Testcase for the logger..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LoggerTest.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
 */

public class LoggerTest extends TestCase {

    /**
     * Constructor for LoggerTest.
     * @param name the name of the test
     */
    public LoggerTest(String name) {
        super(name);
    }
    
    public void testConstructor() {
        System.out.println("testConstructor");
        Logger logger = new Logger();
    }
    
    public void testRegisterLogger() {
        System.out.println("TestRegisterLogger");
        Logger logger = new Logger();
        logger.registerLogger(null, null);
        assertEquals(null, logger.loggers);
        logger.registerLogger("", null);
        assertEquals(null, logger.loggers);
        logger.registerLogger(null, "");
        assertEquals(null, logger.loggers);
        logger.registerLogger("test", TstLogger.class.getName());
        ILog log = logger.getLogger("test");
        assertEquals(1, ((TstLogger) log).initCount);
        logger.clearCache();
        assertEquals(1, ((TstLogger) log).destroyCount);
        assertEquals(true, log instanceof ILog);
        logger.registerLogger("nonexisting", "nonexisting.class.name");
        assertEquals(null, logger.getLogger("nontexisting"));
        logger.registerLogger("test2", TstLogger.class.getName());
        log = logger.getLogger("test2");
        assertEquals(true, log instanceof ILog);
        logger.registerLogger("notalogger", BogusLogger.class.getName());
        log = logger.getLogger("notalogger");
        assertEquals(null, log);
    }
    
    public void testGetLog() {
        System.out.println("testGetLog");
        Logger logger = new Logger();
        assertEquals(null, logger.getCurrentLog());
        assertEquals(null, logger.getLogger(null));
        assertEquals(null, logger.getLogger("i am not there"));
        logger.registerLogger("test", TstLogger.class.getName());
        ILog log = logger.getLogger("test");
        assertEquals(true, log instanceof TstLogger);
        // should be same instance...
        assertEquals(log, logger.getLogger("test"));
        logger.clearCache();
        // should be new instance..
        assertNotSame(log, logger.getLogger("test"));
    }
    
    public void testClearCache() {
        System.out.println("testClearCache");
        Logger logger = new Logger();
        assertEquals(null, logger.loggersCache);
        logger.clearCache();
        // should still be null..
        assertEquals(null, logger.loggersCache);
        logger.loggersCache = new HashMap();
        logger.loggersCache.put("test", new TstLogger());
        logger.clearCache();
        // should be null again..
        assertEquals(null, logger.loggersCache);
    }
    
    public void testCurrentLogger() {
        System.out.println("testCurrentLogger");
        Logger logger = new Logger();
        assertEquals(null, logger.getCurrentLog());
        logger.setCurrentLogger("test");
        assertEquals(null, logger.getCurrentLog());
        logger.registerLogger("test", TstLogger.class.getName());
        logger.setCurrentLogger("test");
        assertEquals(true, logger.getCurrentLog() instanceof TstLogger);
    }
    
    public void testDefaultLog() {
        System.out.println("testDefaultLog");
        Logger logger = new Logger();
        assertEquals(null, logger.getDefaultLogger());
        logger.setDefaultLogger("test");
        assertEquals(null, logger.getDefaultLogger());
        logger.registerLogger("test", TstLogger.class.getName());
        logger.setDefaultLogger("test");
        assertEquals(true, logger.getDefaultLogger() instanceof TstLogger);
    }
    
    public void testUseDefault() {
        System.out.println("testUseDefault");
        Logger logger = new Logger();
        logger.registerLogger("test", TstLogger.class.getName());
        logger.registerLogger("test1", TstLogger.class.getName());
        ILog test = logger.getLogger("test");
        ILog test1 = logger.getLogger("test1");
        logger.setDefaultLogger("test");
        logger.setCurrentLogger("test1");
        assertEquals(test1, logger.getCurrentLog());
        logger.useDefaultLogger();
        assertEquals(test, logger.getCurrentLog());
    }
    
    public void testDestroyInit() {
        System.out.println("testDestroyInit");
        Logger logger = new Logger();
        logger.registerLogger("test", TstLogger.class.getName());
        ILog log = logger.createLogger("test");
        assertNotNull(log);
        assertEquals(1, ((TstLogger) log).initCount);
        logger.clearCache();
        assertEquals(1, ((TstLogger) log).initCount);
        assertEquals(1, ((TstLogger) log).destroyCount);
        logger.registerLogger("test", TstLoggerWE.class.getName());
        log = logger.createLogger("test");
        assertNotNull(log);
        logger.clearCache();
    }
    
    public void testLogMessage() {
        System.out.println("testLogMessage");
        String msg = "level:name:message";
        Logger logger = new Logger();
        // should not crash
        logger.log(null, null, null);
        logger.log(null, null, null, null);
        logger.registerLogger("test", TstListLogger.class.getName());
        logger.setDefaultLogger("test");
        logger.useDefaultLogger();
        TstListLogger log = (TstListLogger) logger.getCurrentLog();
        assertNotNull(log.log);
        logger.log("level", "name", "message");
        assertEquals(msg, log.log.get(0));
        logger.log("level", "name", "message", new Throwable());
        assertEquals(true, ((String)log.log.get(1)).startsWith(msg));
        assertEquals(true, ((String) log.log.get(1)).length() > msg.length());
        logger.clearCache();
        assertNull(log.log);
        
    }

    public class TstLogger implements ILog {

        public int initCount;
        public int destroyCount;
        
        /**
         * @see org.xulux.logging.ILog#init()
         */
        public void init() {
            initCount++;
        }

        /**
         * @see org.xulux.logging.ILog#destroy()
         */
        public void destroy() {
            destroyCount++;
        }

        /**
         * @see org.xulux.logging.ILog#log(java.lang.String, java.lang.String, java.lang.String)
         */
        public void log(String level, String name, String message) {
        }

        /**
         * @see org.xulux.logging.ILog#log(java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable)
         */
        public void log(String level, String name, String message, Throwable t) {
        }
    }
    
    /**
     * logger class that throws exceptions on init and destroy.
     */
    public class TstLoggerWE implements ILog {
	        
	    /**
	     * @see org.xulux.logging.ILog#init()
	     */
	    public void init() {
	        throw new RuntimeException("Should see a stacktrace");
	    }
	    /**
	     * @see org.xulux.logging.ILog#destroy()
	     */
	    public void destroy() {
	        throw new RuntimeException("Should see a stacktrace");
	    }
        /**
         * @see org.xulux.logging.ILog#log(java.lang.String, java.lang.String, java.lang.String)
         */
        public void log(String level, String name, String message) {
        }
        /**
         * @see org.xulux.logging.ILog#log(java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable)
         */
        public void log(String level, String name, String message, Throwable t) {
        }
    }
    
    /**
     * I am not a logger
     */
    public class BogusLogger {
    }

}
