/*
   $Id: NyxJRadioButton.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
   
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

import javax.swing.Icon;
import javax.swing.JRadioButton;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJRadioButton.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
 */
public class NyxJRadioButton extends JRadioButton {

    /**
     * The real background
     */
    private Color realBg;
    /**
     * should we return the real background ?
     */
    private boolean returnRealBackground;

    /**
     *
     */
    public NyxJRadioButton() {
        super();
    }

    /**
     * Set the real background for the icon.
     * @param color the real background color
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
        /**
         * the icon
         */
        private Icon icon;

        /**
         *
         * @param icon the icon
         */
        public IconStub(Icon icon) {
            this.icon = icon;
        }

        /**
         * @see javax.swing.Icon#getIconHeight()
         */
        public int getIconHeight() {
            if (icon == null) {
                return 0;
            }
            return icon.getIconHeight();
        }

        /**
         * @see javax.swing.Icon#getIconWidth()
         */
        public int getIconWidth() {
            if (icon == null) {
                return 0;
            }
            return icon.getIconWidth();
        }

        /**
         * Set the color of the component just for radiobutton..
         * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JRadioButton cc = new JRadioButton();
            cc.setSelected(isSelected());
            if (NyxJRadioButton.this.getRealBackground() != null) {
                cc.setBackground(getRealBackground());
            } else {
                cc.setBackground(getBackground());
            }
            icon.paintIcon(cc, g, x, y);
            cc = null;
        }
    }


}
