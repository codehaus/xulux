/*
   $Id: IInvalidValueStrategy.java,v 1.4 2004-07-12 13:02:42 mvdb Exp $
   
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

import org.xulux.core.ApplicationPart;
import org.xulux.dataprovider.InvalidValueException;

/**
 * This interface is used to determine how to handle invalid values
 * entered by the user.
 * An implementation of this interface will be called on leaving a field
 * or when the Ok button (with defaultaction set to save) is clicked.
 * You can only set one strategy per part.
 * Both methods are always called, so if you don't want to use
 * a checking on a form level, just return true form that method.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IInvalidValueStrategy.java,v 1.4 2004-07-12 13:02:42 mvdb Exp $
 */
public interface IInvalidValueStrategy {
    
    /**
     * Check the specified form for invalid values.
     *
     * @param part the applicationpart to check
     * @return true if all fields in the part have valid values, false if it doesn't
     */
    boolean checkForm(ApplicationPart part);
    
    /**
     * Checks the specified widget for invalid values.
     *
     * @param widget the widge to check
     * @return true if the widget contains a valid value, false it it doesn't
     */
    boolean checkWidget(Widget widget);
    
    /**
     * Handles an invalidvalueexception, so we can eg display a message to the user
     *
     * @param widget the widget with the exception
     * @param exception the exception
     * @return
     */
    boolean handleInvalidValueException(Widget widget, InvalidValueException exception);

}
