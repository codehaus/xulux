/*
   $Id: JdkLogging.java,v 1.1 2004-12-16 06:44:05 mvdb Exp $
   
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

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The JDK logging implementation.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: JdkLogging.java,v 1.1 2004-12-16 06:44:05 mvdb Exp $
 */

public class JdkLogging implements ILog {

    /**
     * 
     */
    public JdkLogging() {
        super();

    }

    /**
     * @see org.xulux.logging.ILog#init()
     */
    public void init() {
    }

    /**
     * @see org.xulux.logging.ILog#destroy()
     */
    public void destroy() {
    }

    /**
     * If the name is null, the jdk anonymous logger will be used.
     * @see org.xulux.logging.ILog#log(int, java.lang.String, java.lang.String)
     */
    public void log(int level, String name, String message) {
        Logger logger = null;
        if (name == null) {
            return;
        }
        logger = Logger.getLogger(name);
        logger.logp(Level.SEVERE, null, null, message);
    }

    /**
     * @see org.xulux.logging.ILog#log(int, java.lang.String, java.lang.String, java.lang.Throwable)
     */
    public void log(int level, String name, String message, Throwable t) {
        Logger logger = null;
        if (name == null) {
            return;
        }
        logger = Logger.getLogger(name);
        logger.logp(Level.SEVERE, null, null, message, t);
    }

}
