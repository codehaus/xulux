/*
   $Id: IconTitledBorder.java,v 1.2 2004-07-19 22:07:31 mvdb Exp $
   
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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;

/**
 * A border wich supports an icon as well as text.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IconTitledBorder.java,v 1.2 2004-07-19 22:07:31 mvdb Exp $
 */
public class IconTitledBorder extends AbstractBorder {

  public static final int EDGE_SPACING = 2;
  public static final int TITLE_SPACING = 5;
  public static final int TEXTICON_SPACING = 5;
  
  protected int thickness = 1;
  
  protected String title;
  protected ImageIcon image;

  /**
   * 
   */
  public IconTitledBorder() {
    super();
  }

  /**
   * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    int lc = (getLineCorrection(c)/2);
    Color tmpColor = g.getColor();
    g.setColor(Color.gray);
    for (int i = 0; i < getThickness(); i++) {
      g.drawRect(x+EDGE_SPACING+i, y+EDGE_SPACING+i+lc, width-EDGE_SPACING-i-i-1, height-EDGE_SPACING-i-i-1-lc);
    }
    g.setColor(tmpColor);
    clearLineForTitle(c, g, x, y, width, height);
    paintImage(c, g, x, y, width, height);
    paintTitle(c, g, x, y, width, height);
    //super.paintBorder(c, g, x+EDGE_SPACING, y, width, height-EDGE_SPACING);
  }
  
  protected void clearLineForTitle(Component c, Graphics g, int x, int y, int width, int height) {
    Rectangle rect = getTitleRectangle(c, x, y, width);
    Color tmpColor = g.getColor();
    g.setColor(c.getBackground());
    if (getImage() != null) {
      rect.width+=EDGE_SPACING;
      rect.width+=getImage().getIconWidth();
    }
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
    g.setColor(tmpColor);
  }

  /**
   * @param c the component
   * @param x the x position
   * @param y the y position
   * @param width the width of the paint area
   * @return a rectangle containing the dimensions of the title including image.
   */  
  protected Rectangle getTitleRectangle(Component c, int x, int y, int width) {
      Rectangle rectangle = new Rectangle();
      rectangle.x = x+EDGE_SPACING+TITLE_SPACING;
      rectangle.y = y+EDGE_SPACING;
      rectangle.width = getTitleWidth(c);
      rectangle.height = getMaxHeight(c);
      return rectangle;
  }

  protected void paintTitle(Component c, Graphics g, int x, int y, int width, int height) {
    if (getTitle() == null) {
      return;
    }
    FontMetrics metrics = c.getFontMetrics(UIManager.getFont("TitledBorder.font"));
    int metricHeight = metrics.getHeight();
    int strWidth = metrics.stringWidth(getTitle());
    int imageCorrection = 0;
    if (getImage() != null) {
      imageCorrection = EDGE_SPACING+getImage().getIconWidth();
    }
    Font tmpFont = g.getFont();
    Color tmpColor = g.getColor();
    g.setFont(UIManager.getFont("TitledBorder.font"));
    g.setColor(UIManager.getColor("TitledBorder.titleColor"));
    g.drawString(title, x+EDGE_SPACING+TITLE_SPACING+imageCorrection, y+EDGE_SPACING+getLineCorrection(c));
    g.setFont(tmpFont);
    g.setColor(tmpColor);
  }


  protected int getTitleWidth(Component c) {
    int titleWidth = 0;
    if (getImage() != null) {
      titleWidth+=getImage().getIconWidth();
    }
    if (getTitle() != null && getImage() != null) {
      titleWidth+=TEXTICON_SPACING;
    }
    if (getTitle() != null) {
      titleWidth = c.getFontMetrics(UIManager.getFont("TitledBorder.font")).stringWidth(getTitle());
    }
    return titleWidth;
  }

  protected void paintImage(Component c, Graphics g, int x, int y, int width, int height) {
    if (getImage() != null) {                 
      getImage().paintIcon(c, g, x+EDGE_SPACING+TITLE_SPACING, y);
    }
  }
  
  /**
   * @param c the component to get the metric from
   * @return the correction that needs to take place based on the icon and title for drawing
   * a line. It will return the y correction so the line can be drawn in the middle of those components
   */
  protected int getLineCorrection(Component c) {
    return getMaxHeight(c) / 2;
  }
  
  protected int getMaxHeight(Component c) {
    int textHeight = 0;
    int imageHeight = 0;
    if (getTitle() != null) {
        FontMetrics metrics = c.getFontMetrics(c.getFont());
        textHeight = metrics.getHeight();
    }
    if (getImage() != null) {
        imageHeight = getImage().getIconHeight();
    }
    return Math.max(textHeight, imageHeight);
  }

  /**
   * @return the image
   */
  public ImageIcon getImage() {
    return image;
  }

  /**
   * @return the thickness
   */
  public int getThickness() {
    return thickness;
  }

  /**
   * @return the title of the border
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the image of the border.
   *
   * @param image the image to use
   */
  public void setImage(Image image) {
    this.image = new ImageIcon(image);
  }

  /**
   * @param thickness
   */
  public void setThickness(int thickness) {
    this.thickness = thickness;
  }

  /**
   * @param string
   */
  public void setTitle(String string) {
    title = string;
  }

  /**
   * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
   */
  public Insets getBorderInsets(Component c) {
    return new Insets(EDGE_SPACING+getMaxHeight(c), EDGE_SPACING+thickness, EDGE_SPACING+thickness, EDGE_SPACING+thickness);
  }
  
  /**
   * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
   */
  public Insets getBorderInsets(Component c, Insets insets) {
    return super.getBorderInsets(c, insets);
  }

}
