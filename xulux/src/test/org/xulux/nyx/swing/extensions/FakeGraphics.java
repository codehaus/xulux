/*
 $Id: FakeGraphics.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $

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
package org.xulux.nyx.swing.extensions;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * A fake graphics object, so we can do painting without
 * actually painting.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: FakeGraphics.java,v 1.1 2003-11-27 19:39:18 mvdb Exp $
 */
public class FakeGraphics extends Graphics {

    /**
     * The color
     */
    private Color color;
    /**
     * The main constructor
     */
    public FakeGraphics() {
        //super();
    }

    /**
     * @see java.awt.Graphics#create()
     */
    public Graphics create() {
        return null;
    }

    /**
     * @see java.awt.Graphics#translate(int, int)
     */
    public void translate(int x, int y) {

    }

    /**
     * @see java.awt.Graphics#getColor()
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @see java.awt.Graphics#setColor(java.awt.Color)
     */
    public void setColor(Color c) {
        this.color = c;

    }

    /**
     * @see java.awt.Graphics#setPaintMode()
     */
    public void setPaintMode() {

    }

    /**
     * @see java.awt.Graphics#setXORMode(java.awt.Color)
     */
    public void setXORMode(Color c1) {

    }

    /**
     * @see java.awt.Graphics#getFont()
     */
    public Font getFont() {
        return null;
    }

    /**
     * @see java.awt.Graphics#setFont(java.awt.Font)
     */
    public void setFont(Font font) {

    }

    /**
     * @see java.awt.Graphics#getFontMetrics(java.awt.Font)
     */
    public FontMetrics getFontMetrics(Font f) {
        return null;
    }

    /**
     * @see java.awt.Graphics#getClipBounds()
     */
    public Rectangle getClipBounds() {
        return null;
    }

    /**
     * @see java.awt.Graphics#clipRect(int, int, int, int)
     */
    public void clipRect(int x, int y, int width, int height) {

    }

    /**
     * @see java.awt.Graphics#setClip(int, int, int, int)
     */
    public void setClip(int x, int y, int width, int height) {

    }

    /**
     * @see java.awt.Graphics#getClip()
     */
    public Shape getClip() {
        return null;
    }

    /**
     * @see java.awt.Graphics#setClip(java.awt.Shape)
     */
    public void setClip(Shape clip) {

    }

    /**
     * @see java.awt.Graphics#copyArea(int, int, int, int, int, int)
     */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {

    }

    /**
     * @see java.awt.Graphics#drawLine(int, int, int, int)
     */
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    /**
     * @see java.awt.Graphics#fillRect(int, int, int, int)
     */
    public void fillRect(int x, int y, int width, int height) {

    }

    /**
     * @see java.awt.Graphics#clearRect(int, int, int, int)
     */
    public void clearRect(int x, int y, int width, int height) {

    }

    /**
     * @see java.awt.Graphics#drawRoundRect(int, int, int, int, int, int)
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    /**
     * @see java.awt.Graphics#fillRoundRect(int, int, int, int, int, int)
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    /**
     * @see java.awt.Graphics#drawOval(int, int, int, int)
     */
    public void drawOval(int x, int y, int width, int height) {

    }

    /**
     * @see java.awt.Graphics#fillOval(int, int, int, int)
     */
    public void fillOval(int x, int y, int width, int height) {

    }

    /**
     * @see java.awt.Graphics#drawArc(int, int, int, int, int, int)
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    /**
     * @see java.awt.Graphics#fillArc(int, int, int, int, int, int)
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    /**
     * @see java.awt.Graphics#drawPolyline(int[], int[], int)
     */
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {

    }

    /**
     * @see java.awt.Graphics#drawPolygon(int[], int[], int)
     */
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    /**
     * @see java.awt.Graphics#fillPolygon(int[], int[], int)
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    /**
     * @see java.awt.Graphics#drawString(java.lang.String, int, int)
     */
    public void drawString(String str, int x, int y) {

    }

    /**
     * @see java.awt.Graphics#drawString(java.text.AttributedCharacterIterator, int, int)
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {

    }

    /**
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return false;
    }

    /**
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return false;
    }

    /**
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, java.awt.Color, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return false;
    }

    /**
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return false;
    }

    /**
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int, int, int, int, int, java.awt.image.ImageObserver)
     */
    public boolean drawImage(
        Image img,
        int dx1,
        int dy1,
        int dx2,
        int dy2,
        int sx1,
        int sy1,
        int sx2,
        int sy2,
        ImageObserver observer) {
        return false;
    }

    /**
     * @see java.awt.Graphics#drawImage(java.awt.Image, int, int, int, int,
     *       int, int, int, int, java.awt.Color, java.awt.image.ImageObserver)
     */
    public boolean drawImage(
        Image img,
        int dx1,
        int dy1,
        int dx2,
        int dy2,
        int sx1,
        int sy1,
        int sx2,
        int sy2,
        Color bgcolor,
        ImageObserver observer) {
        return false;
    }

    /**
     * @see java.awt.Graphics#dispose()
     */
    public void dispose() {

    }

}
