/*
   $Id: NyxTitledBorder.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
   
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
package org.xulux.swing.extensions;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Martin van den Bemt
 * @version $Id: NyxTitledBorder.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
 */
public class NyxTitledBorder extends TitledBorder {

    /**
     * @param title
     */
    public NyxTitledBorder(String title) {
        super(title);
    }

    /**
     * @param border
     */
    public NyxTitledBorder(Border border) {
        super(border);
    }

    /**
     * @param border
     * @param title
     */
    public NyxTitledBorder(Border border, String title) {
        super(border, title);
    }

    /**
     * @param border
     * @param title
     * @param titleJustification
     * @param titlePosition
     */
    public NyxTitledBorder(
        Border border,
        String title,
        int titleJustification,
        int titlePosition) {
        super(border, title, titleJustification, titlePosition);
    }

    /**
     * @param border
     * @param title
     * @param titleJustification
     * @param titlePosition
     * @param titleFont
     */
    public NyxTitledBorder(
        Border border,
        String title,
        int titleJustification,
        int titlePosition,
        Font titleFont) {
        super(border, title, titleJustification, titlePosition, titleFont);
    }

    /**
     * @param border
     * @param title
     * @param titleJustification
     * @param titlePosition
     * @param titleFont
     * @param titleColor
     */
    public NyxTitledBorder(
        Border border,
        String title,
        int titleJustification,
        int titlePosition,
        Font titleFont,
        Color titleColor) {
        super(
            border,
            title,
            titleJustification,
            titlePosition,
            titleFont,
            titleColor);
    }

}
