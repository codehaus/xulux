/*
 $Id: NyxJCheckBox.java,v 1.3 2003-11-06 19:53:11 mvdb Exp $

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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

/**
 * Overriding JCheckBox so the background is painted correctly
 * based on the color set in the widget.
 * If the size is bigger than the image that is being shown as the
 * checkbox, it otherwise would paint the whole area in the background
 * color.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJCheckBox.java,v 1.3 2003-11-06 19:53:11 mvdb Exp $
 */
public class NyxJCheckBox extends JCheckBox {

    private Color realBg;
    private boolean returnRealBackground;

    /**
     *
     */
    public NyxJCheckBox() {
        super();
    }

    /**
     * @param icon
     */
    public NyxJCheckBox(Icon icon) {
        super(icon);
    }

    /**
     * @param icon
     * @param selected
     */
    public NyxJCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * @param text
     */
    public NyxJCheckBox(String text) {
        super(text);
    }

    /**
     * @param a
     */
    public NyxJCheckBox(Action a) {
        super(a);
    }

    /**
     * @param text
     * @param selected
     */
    public NyxJCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * @param text
     * @param icon
     */
    public NyxJCheckBox(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * @param text
     * @param icon
     * @param selected
     */
    public NyxJCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    /**
     * Set the real background for the icon.
     * @param color
     */
    public void setRealBackground(Color color) {
        this.realBg = color;
    }

    /**
     *
     * @return the real background color for the icon
     */
    public Color getRealBackground() {
        return this.realBg;
    }

    /**
     * Embed the icon in an iconstub
     * @see javax.swing.AbstractButton#setIcon(javax.swing.Icon)
     */
    public void setIcon(Icon defaultIcon) {
        super.setIcon(new IconStub(defaultIcon));
    }

    /**
     * embed the icon in an icon stub
     * @see javax.swing.AbstractButton#setSelectedIcon(javax.swing.Icon)
     */
    public void setSelectedIcon(Icon selectedIcon) {
        super.setSelectedIcon(new IconStub(selectedIcon));
    }


    /**
     * A stub to surround an icon in, so they get painted
     * with the correct background set in the widget
     */
    public class IconStub implements Icon {
        private Icon icon;

        public IconStub(Icon icon) {
            this.icon = icon;
        }

        /**
         * @see javax.swing.Icon#getIconHeight()
         */
        public int getIconHeight() {
            return icon.getIconHeight();
        }

        /**
         * @see javax.swing.Icon#getIconWidth()
         */
        public int getIconWidth() {
            return icon.getIconWidth();
        }

        /**
         * Fill a rectangle of the size of the icon with the real
         * background color.
         * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (NyxJCheckBox.this.getRealBackground() != null) {
                g.setColor(getRealBackground());
            }
            g.fillRect(x,y,getIconWidth(), getIconHeight());
            icon.paintIcon(c, g, x, y);
        }
    }
}
