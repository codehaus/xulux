/*
$Id: ILogger.java,v 1.1 2005-04-30 10:05:25 mvdb Exp $

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

/**
 * The ILogger interface.
 * It serves as the interface for xulux logger wrapper implementations.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ILogger.java,v 1.1 2005-04-30 10:05:25 mvdb Exp $
 */
public interface ILogger {

    /**
     * @return the log level used. If not set, it will return DEFAULTLEVEL 
     */
    int getLevel();

    /**
     * If the level is null, it will start using the default log level
     * again.
     *
     * @param level the log level
     */
    void setLevel(int level);

    /**
     * Log a message
     *
     * @param level the level to use
     * @param name the name to use (used as eg a prefix in the logging output)
     * @param message the log message
     */
    void log(int level, String name, String message);
    
    /**
     * Log a message
     *
     * @param level the level to use
     * @param name the name to use (used as eg a prefix in the logging output)
     * @param message the log message
     * @param t the exception to log
     */
    void log(int level, String name, String message, Throwable t);
    
    /**
     * @return the currently used logger instance 
     */
    ILog getCurrentLog();

    /**
     * Set the default logger
     *
     * @param name the name of the logger
     */
    void setDefaultLogger(String name);

    /**
     * @param name the name of the logger
     * @return the log instance or null if it doesn't exist.
     */
    ILog getLogger(String name);
    
    /**
     * @return the default log
     */
    ILog getDefaultLogger();
    
    /**
     * Sets the current logger.
     * @param name the name of the logger
     */
    void setCurrentLogger(String name);
    
    /**
     * Clears the logging cache
     */
    void clearCache();

    /**
     * Start using the default logger
     */
    void useDefaultLogger();

}
