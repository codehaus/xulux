package org.xulux.nyx.gui.swing.widgets;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: NativeJButton.java,v 1.1 2003-07-10 22:40:20 mvdb Exp $
 */
public class NativeJButton extends JButton {

    /**
     * 
     */
    public NativeJButton() {
        super();
        setPreferredSize(new Dimension(135,31));
        setText("Native JButton");
    }

    /**
     * @param icon
     */
    public NativeJButton(Icon icon) {
        super(icon);
    }

    /**
     * @param text
     */
    public NativeJButton(String text) {
        super(text);
    }

    /**
     * @param a
     */
    public NativeJButton(Action a) {
        super(a);
    }

    /**
     * @param text
     * @param icon
     */
    public NativeJButton(String text, Icon icon) {
        super(text, icon);
    }

}
