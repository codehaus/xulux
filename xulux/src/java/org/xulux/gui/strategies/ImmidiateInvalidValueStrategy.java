/*
   $Id: ImmidiateInvalidValueStrategy.java,v 1.1 2004-06-30 11:59:01 mvdb Exp $
   
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
package org.xulux.gui.strategies;

import org.xulux.core.XuluxContext;
import org.xulux.gui.Widget;

/**
 * This checks individual widgets on leaving the field. Since it overrides
 * SaveActionInvalidValueStrategy, it will also check valid values on form exit.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImmidiateInvalidValueStrategy.java,v 1.1 2004-06-30 11:59:01 mvdb Exp $
 */
public class ImmidiateInvalidValueStrategy extends SaveActionInvalidValueStrategy {

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#checkWidget(org.xulux.gui.Widget)
     */
    public boolean checkWidget(Widget widget) {
        if (widget.isRequired()) {
            Object guiValue = widget.getGuiValue();
            if (guiValue instanceof String || guiValue == null) {
                if (guiValue == null || ((String)guiValue).trim().equals("")) {
                    XuluxContext.getGuiDefaults().getXuluxToolkit().beep();
                    widget.focus();
                    return false;
                }
            }
        }
        return true;
    }

}
