/*
   $Id: NyxLineBorder.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
   
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
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.LineBorder;

/**
 *
 * @author Martin van den Bemt
 * @version $Id: NyxLineBorder.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
 */
public class NyxLineBorder extends LineBorder {

    /**
     * @param color
     */
    public NyxLineBorder(Color color) {
        super(color);
    }

    /**
     * @param color
     * @param thickness
     */
    public NyxLineBorder(Color color, int thickness) {
        super(color, thickness);
    }

    /**
     * @param color
     * @param thickness
     * @param roundedCorners
     */
    public NyxLineBorder(Color color, int thickness, boolean roundedCorners) {
        super(color, thickness, roundedCorners);
    }

    /**
     * Paints the border for the specified component with the
     * specified position and size.
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color oldColor = g.getColor();
        int i;

    /// PENDING(klobad) How/should do we support Roundtangles?
        g.setColor(lineColor);
        for (i = 0; i < thickness; i++)  {
            if (!roundedCorners) {
                System.out.println("Without rounded corners");
                System.out.println("x+i = " + (x + i));
                System.out.println("y+i = " + (y + i));
                System.out.println("width-i-i-1 = " + (width - i - i - 1));
                System.out.println("height-i-i-1 = " + (height - i - i - 1));
                g.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
            } else {
                g.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, thickness, thickness);
            }
        }
        g.setColor(oldColor);
    }


}
