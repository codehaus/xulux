/*
 $Id: NyxLineBorder.java,v 1.1 2003-12-18 00:17:28 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.swing.extensions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.LineBorder;

/**
 *
 * @author Martin van den Bemt
 * @version $Id: NyxLineBorder.java,v 1.1 2003-12-18 00:17:28 mvdb Exp $
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
