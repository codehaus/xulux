/*
 $Id: WidgetRectangle.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $

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
package org.xulux.gui;

import java.awt.Rectangle;

/**
 * Placeholder for position and for width and heigth
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRectangle.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $
 */
public class WidgetRectangle {
    /**
     * the x position
     */
    private int x = 0;
    /**
     * the y position
     */
    private int y = 0;
    /**
     * the width
     */
    private int width = 0;
    /**
     * the height
     */
    private int height = 0;

    /**
     * Constructor for WidgetRectangle.
     */
    public WidgetRectangle() {
    }

    /**
     * Constructor for WidgetRectangle.
     *
     * @param x the x position
     * @param y the y position
     * @param width the width of the widget
     * @param height the height of the widget
     */
    public WidgetRectangle(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the height.
     * @param height The height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the width.
     * @param width The width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the x.
     * @param x The x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the size.
     * @param width the widh
     * @param height the height
     */
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * Set the position
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Sets the y.
     * @param y The y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the awt rectangle
     * @todo probably move this to another class, since it could be that this is not present
     *       in all jvm's.
     */
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}