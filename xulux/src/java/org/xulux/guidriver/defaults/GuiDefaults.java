/*
   $Id: GuiDefaults.java,v 1.2 2004-05-10 15:03:56 mvdb Exp $
   
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
package org.xulux.guidriver.defaults;

import java.util.Map;

import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.NYXToolkit;
import org.xulux.gui.Widget;

/**
 * Just a placeholer for the default gui
 * properties.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaults.java,v 1.2 2004-05-10 15:03:56 mvdb Exp $
 */
public class GuiDefaults {

    /**
     * The default widgetType for the system
     * (eg swt, swing)
     */
    private String defaultType;

    /**
     * Constructor for GuiDefaults.
     */
    public GuiDefaults() {
    }

    /**
     * @return the default widget type.
     */
    public String getDefaultWidgetType() {
        return this.defaultType;
    }

    /**
     * Sets the application wide default widget type
     * (eg. swt, core, swing)
     *
     * @param type the default gui type for this application
     */
    public void setDefaultWidgetType(String type) {
        this.defaultType = type;
    }
      
    /**
     * @return the NYX toolkit of the default type or null
     *          when not present
     * @deprecated Use XuluxContext.getGuiDefaults.getNyxToolkit()
     */
//    public NYXToolkit getNYXToolkit() {
//        return getNYXToolkit(getDefaultWidgetType());
//    }
  
    /**
     *
     * @param type the toolkit type (eg swt, swing)
     * @return the NYX toolkit type specified or null
     *          when not present
     * @deprecated Use XuluxContext.getGuiDefaults.getNyxToolkit(type)
     */
//    public NYXToolkit getNYXToolkit(String type) {
//        if (this.nyxToolkits != null) {
//            return (NYXToolkit) this.nyxToolkits.get(type);
//        }
//        return null;
//    }
}
