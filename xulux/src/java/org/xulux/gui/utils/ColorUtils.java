/*
   $Id: ColorUtils.java,v 1.2 2004-01-28 15:00:22 mvdb Exp $
   
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

import java.awt.Color;
import java.util.Arrays;

/**
 * Color utils to make parsing easier
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ColorUtils.java,v 1.2 2004-01-28 15:00:22 mvdb Exp $
 */
public class ColorUtils
{

    /**
     * Constructor for ColorUtils.
     */
    protected ColorUtils()
    {
    }

    /**
     * The hex string can be six or three long
     * Six is the format AABBCC
     * Three is the format ABC, which resolves to AABBCC
     * If you pass in an invalid string, the rgb values will
     * all be zero
     * @param hex the hex string for the golor
     * @return an array with the rgb values.
     */
    public static int[] getRGBFromHex(String hex)
    {
        int[] result = new int[3];
        if (hex == null) {
            Arrays.fill(result, 0);
        } else {
            try {
                if (hex.length() == 6)  {
                    result[0] = Integer.parseInt(hex.substring(0, 2), 16);
                    result[1] = Integer.parseInt(hex.substring(2, 4), 16);
                    result[2] = Integer.parseInt(hex.substring(4, 6), 16);
                } else if (hex.length() == 3) {
                    result[0] = Integer.parseInt(hex.substring(0, 1) + hex.substring(0, 1), 16);
                    result[1] = Integer.parseInt(hex.substring(1, 2) + hex.substring(1, 2), 16);
                    result[2] = Integer.parseInt(hex.substring(2, 3) + hex.substring(2, 3), 16);
                }
            } catch (NumberFormatException nfe) {
                Arrays.fill(result, 0);
            }
        }
        return result;
    }

    /**
     * @param color eg COCOCO
     * @return the color object or when the color param is not parseable, Color.black
     */
    public static Color getSwingColor(String color) {
        Color result = Color.black;
        try {
            int rgb = Integer.parseInt(color, 16);
            result = new Color(rgb);
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

}
