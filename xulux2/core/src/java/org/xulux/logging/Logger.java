/*
   $Id: Logger.java,v 1.2 2004-12-16 06:44:05 mvdb Exp $
   
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
import java.util.Iterator;
import java.util.Map;

import org.xulux.utils.ClassLoaderUtils;

/**
 * Not using any logger as the default, since we want to minimize dependencies.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Logger.java,v 1.2 2004-12-16 06:44:05 mvdb Exp $
 */

public class Logger {

    /**
     * The list with registered loggers
     */
    Map loggers;

    /**
     * The logging instance cache
     */
    Map loggersCache;

    /**
     * The default logger for the application
     */
    ILog defaultLog;
    /**
     * The current logger for the application
     */
    ILog currentLog;

    /**
     * The log level
     */
    int level = ILog.ERROR;

    /**
     * The logger constructor
     */
    public Logger() {
    }
    

    /**
     * @return the log level used. If not set, it will return DEFAULTLEVEL 
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * If the level is null, it will start using the default log level
     * again.
     *
     * @param level the log level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Register a logger.
     *
     * @param name the name of the logger
     * @param clz the class of the logger interface.
     */
    public void registerLogger(String name, String clz) {
        if (name == null || clz == null) {
            return;
        }
        Class logClass = ClassLoaderUtils.getClass(clz);
        if (logClass == null) {
            return;
        }
        // only accept logger which implement ILog
        if (!ILog.class.isAssignableFrom(logClass)) {
            return;
        }
        if (loggers == null) {
            loggers = new HashMap();
        }
        loggers.put(name, logClass);
    }
    
    /**
     * Log a message
     *
     * @param level the level to use
     * @param name the name to use (used as eg a prefix in the logging output)
     * @param message the log message
     */
    public void log(int level, String name, String message) {
        if (getCurrentLog() != null) {
            if (level <= getLevel()) {
                getCurrentLog().log(level, name, message);
            }
        }
    }
    
    /**
     * Log a message
     *
     * @param level the level to use
     * @param name the name to use (used as eg a prefix in the logging output)
     * @param message the log message
     * @param t the exception to log
     */
    public void log(int level, String name, String message, Throwable t) {
        if (getCurrentLog() != null) {
            if (level <= getLevel()) { 
                getCurrentLog().log(level, name, message, t);
            }
        }
    }
    
    /**
     * @return the currently used logger instance 
     */
    public ILog getCurrentLog() {
        return this.currentLog;
    }

    /**
     * Set the default logger
     *
     * @param name the name of the logger
     */
    public void setDefaultLogger(String name) {
        this.defaultLog = createLogger(name);
    }

    /**
     * @param name the name of the logger
     * @return the log instance or null if it doesn't exist.
     */
    public ILog getLogger(String name) {
        return createLogger(name);
    }
    
    /**
     * @return the default log
     */
    public ILog getDefaultLogger() {
        return this.defaultLog;
    }
    
    /**
     * Sets the current logger.
     * @param name the name of the logger
     */
    public void setCurrentLogger(String name) {
        this.currentLog = createLogger(name);
    }
    
    /**
     * Creates a logger instance.
     * @param name the name of the logger
     */
    protected ILog createLogger(String name) {
        ILog retValue = null;
        if (loggersCache != null) {
            retValue = (ILog) loggersCache.get(name);
        }
        if (retValue == null &&  loggers != null) {
            retValue = (ILog) ClassLoaderUtils.getObjectFromClass((Class) loggers.get(name));
            if (retValue != null) {
	            try {
	                retValue.init();
	            } catch(Throwable t) {
	                t.printStackTrace();
	            }
            }
            if (loggersCache == null) {
                loggersCache = new HashMap();
            }
            loggersCache.put(name, retValue);
        }
        return retValue;
    }
    
    /**
     * Clears the logging cache
     */
    public void clearCache() {
        if (loggersCache != null) {
            Iterator it = loggersCache.values().iterator();
            while (it.hasNext()) {
                try {
                    ((ILog) it.next()).destroy();
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
            loggersCache.clear();
            loggersCache = null;
        }
    }
    
    /**
     * Start using the default logger
     */
    public void useDefaultLogger() {
        this.currentLog = defaultLog;
    }
    

}
