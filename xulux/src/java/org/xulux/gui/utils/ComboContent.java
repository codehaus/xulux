/*
   $Id: ComboContent.java,v 1.2 2004-01-28 15:00:22 mvdb Exp $
   
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
package org.xulux.gui.utils;

import java.util.ArrayList;
import java.util.List;

import org.xulux.gui.NyxCombo;

/**
 * Creates the content needed for the combo box.
 * This way we have one single entry point for
 * creating combo data.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ComboContent.java,v 1.2 2004-01-28 15:00:22 mvdb Exp $
 */
public class ComboContent
{
    /**
     * allow override
     */
    protected ComboContent() {
    }
    /**
     *
     * @param content the content
     * @param comboFields the fields
     * @param combo the combo
     * @return the array containing values
     */
    public static String[] getStringArray(List content, String comboFields, NyxCombo combo) {
        return null;
    }

    /**
     *
     * @param content the content
     * @param comboFields the fields
     * @param combo the combo
     * @return the list containg the value
     */
    public static List getArrayList(ArrayList content, String comboFields, NyxCombo combo) {
        return null;
    }

}
