/*
   $Id: ILog.java,v 1.1 2004-12-04 19:06:41 mvdb Exp $
   
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
 * The log interface. You need to implement this interface
 * if you want to support a logger (eg commons-logging, log4j, jdk logging);
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ILog.java,v 1.1 2004-12-04 19:06:41 mvdb Exp $
 */

public interface ILog {
    
    /**
     * Initialize the log
     */
    public void init();

    /**
     * destroy the log
     *
     */
    public void destroy();
    
    /**
     * Log a message
     *
     * @param level the level to use
     * @param name the name to use (used as eg a prefix in the logging output)
     * @param message the log message
     */
    public void log(String level, String name, String message);
    
    /**
     * Log a message
     *
     * @param level the level to use
     * @param name the name to use (used as eg a prefix in the logging output)
     * @param message the log message
     * @param t the exception to log
     */
    public void log(String level, String name, String message, Throwable t);
    

}
