/*
   $Id: XuluxContext.java,v 1.1 2004-12-04 19:06:41 mvdb Exp $
   
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
package org.xulux.core;

import org.xulux.logging.Logger;


/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XuluxContext.java,v 1.1 2004-12-04 19:06:41 mvdb Exp $
 */

public class XuluxContext {
    
    
    /**
     * The xulux context instance
     */
    private static XuluxContext xuluxContext;
    /**
     * The widget registry
     */
    private WidgetRegistry widgetRegistry;
    
    private static Logger logger;

    /**
     * The XuluxContext constructor
     */
    private XuluxContext() {
    }
    
    public static XuluxContext getInstance() {
        if (xuluxContext == null) {
            xuluxContext = new XuluxContext();
        }
        return xuluxContext;
    }
    
    /**
     * @return the widget registry.
     */
    public WidgetRegistry getWidgetRegistry() {
        if (widgetRegistry == null) {
            widgetRegistry = new WidgetRegistry();
        }
        return widgetRegistry;
    }
    
    /**
     * @return the xulux logger.
     */
    public static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
    
}
