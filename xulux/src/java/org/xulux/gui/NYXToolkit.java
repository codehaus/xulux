/*
   $Id: NYXToolkit.java,v 1.3 2004-03-16 15:04:16 mvdb Exp $
   
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
package org.xulux.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.ApplicationContext;

/**
 * The toolkit class is a class that takes care
 * of minor things like beeping etc.
 * It will load the correct instance of the toolkit
 * eg for swing or swt.
 * (didn't check yet if swt uses something different though)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NYXToolkit.java,v 1.3 2004-03-16 15:04:16 mvdb Exp $
 */
public abstract class NYXToolkit {

    /**
     * The toolkit instance
     */
    private static NYXToolkit instance;
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(NYXToolkit.class);
    /**
     *
     */
    public NYXToolkit() {
        super();
    }

    /**
     * @return the NYXToolkit used for the gui
     */
    protected static NYXToolkit getInstance() {
        if (instance == null) {
            instance = ApplicationContext.getInstance().getNYXToolkit();
            if (instance == null) {
                if (log.isWarnEnabled()) {
                    log.warn("No toolkits present for nyx, please check your configuration or guidefaults xml file");
                }
            }

        }
        return instance;
    }

    /**
     * Make a noise.
     */
    public abstract void beep();

    /**
     * Initializes whatever may be needed.
     * Eg custom eventqueues for swing,etc..
     *
     */
    public abstract void initialize();

    /**
     * Destroys whatever was initialized..
     *
     */
    public abstract void destroy();

}
