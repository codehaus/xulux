/*
   $Id: NoInvalidValueStrategy.java,v 1.2 2004-07-12 13:00:38 mvdb Exp $
   
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

import org.xulux.core.ApplicationPart;
import org.xulux.dataprovider.InvalidValueException;
import org.xulux.gui.IInvalidValueStrategy;
import org.xulux.gui.Widget;

/**
 * Every value is ok for when you use this strategy..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NoInvalidValueStrategy.java,v 1.2 2004-07-12 13:00:38 mvdb Exp $
 */
public class NoInvalidValueStrategy implements IInvalidValueStrategy {

    /**
     * 
     */
    public NoInvalidValueStrategy() {
        super();
    }

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#checkForm(org.xulux.core.ApplicationPart)
     */
    public boolean checkForm(ApplicationPart part) {
        return true;
    }

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#checkWidget(org.xulux.gui.Widget)
     */
    public boolean checkWidget(Widget widget) {
        return true;
    }

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#handleInvalidValueExction(org.xulux.gui.Widget, org.xulux.dataprovider.InvalidValueException)
     */
    public boolean handleInvalidValueExction(Widget widget, InvalidValueException exception) {
      return true;
    }

}
