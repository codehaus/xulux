package org.xulux.nyx.swing.extensions;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: NyxTitledBorder.java,v 1.1 2003-09-11 12:20:57 mvdb Exp $
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
