/*
   $Id: XuluxToolkit.java,v 1.1 2004-05-11 11:50:00 mvdb Exp $
   
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
import org.xulux.core.XuluxContext;

/**
 * The toolkit class is a class that takes care
 * of minor things like beeping etc.
 * It will load the correct instance of the toolkit
 * eg for swing or swt.
 * (didn't check yet if swt uses something different though)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XuluxToolkit.java,v 1.1 2004-05-11 11:50:00 mvdb Exp $
 */
public abstract class XuluxToolkit {

    /**
     * The toolkit instance
     */
    private static XuluxToolkit instance;
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(XuluxToolkit.class);
    /**
     *
     */
    public XuluxToolkit() {
        super();
    }

    /**
     * @return the XuluxToolkit used for the gui
     */
    protected static XuluxToolkit getInstance() {
        if (instance == null) {
            instance = XuluxContext.getGuiDefaults().getXuluxToolkit();
            if (instance == null) {
                if (log.isWarnEnabled()) {
                    log.warn("No toolkits present for xulux, please check your configuration or guidefaults xml file");
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
