/*
   $Id: WidgetRectangle.java,v 1.3 2004-01-28 15:00:23 mvdb Exp $
   
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

import java.awt.Rectangle;

/**
 * Placeholder for position and for width and heigth
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRectangle.java,v 1.3 2004-01-28 15:00:23 mvdb Exp $
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
