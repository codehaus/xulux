/*
 $Id: Button.java,v 1.17 2003-11-06 19:53:13 mvdb Exp $

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
package org.xulux.nyx.swing.widgets;

import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.swing.SwingWidget;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;
import org.xulux.nyx.swing.util.SwingUtils;


/**
 * Represents a button in the gui
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Button.java,v 1.17 2003-11-06 19:53:13 mvdb Exp $
 */
public class Button extends SwingWidget
{

    private JButton button;
    private PrePostFieldListener actionListener;
    private FocusListener focusListener;
    private List listenerList;

    /**
     * Constructor for Button.
     */
    public Button(String name)
    {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return button;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize()
    {
        if (initialized)
        {
            return;
        }
        button = new JButton();
        initialized = true;
        if (!isRefreshing())
        {
            refresh();
        }
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        String image = getProperty("image");
        if (image != null)
        {
            ImageIcon icon = SwingUtils.getIcon(image,this);
            button.setIcon(icon);
            button.setFocusPainted(true);
        }
        String disabledImage = getProperty("image-disabled");
        if (disabledImage != null)
        {
            ImageIcon icon = SwingUtils.getIcon(disabledImage, this);
            button.setDisabledIcon(icon);
            button.setDisabledSelectedIcon(icon);
        }
        String rolloverImage = getProperty("image-rollover");
        if (rolloverImage!=null)
        {
            ImageIcon icon = SwingUtils.getIcon(rolloverImage, this);
            button.setRolloverIcon(icon);
            button.setRolloverEnabled(true);
        }
        String selectedImage = getProperty("image-selected");
        if (selectedImage != null)
        {
            ImageIcon icon = SwingUtils.getIcon(selectedImage,this);
            button.setSelectedIcon(icon);
            button.setPressedIcon(icon);
            button.setRolloverSelectedIcon(icon);
            button.setRolloverEnabled(true);
            // we need to add a focuslistener to set the image
            // when focus is there to the selected image
            if (this.focusListener == null)
            {
                this.focusListener = new FocusListener()
                {
                    Icon normalIcon;
                    /**
                     * @see java.awt.event.FocusListener#focusGained(FocusEvent)
                     */
                    public void focusGained(FocusEvent e)
                    {
                        normalIcon = button.getIcon();
                        button.setIcon(button.getSelectedIcon());
                    }

                    /**
                     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
                     */
                    public void focusLost(FocusEvent e)
                    {
                        if (normalIcon != null)
                        {
                            button.setIcon(normalIcon);
                            normalIcon = null;
                        }
                    }
                };
                button.addFocusListener(focusListener);
            }

        }
        if (getProperty("text")!=null)
        {
            button.setText(getProperty("text"));
        }
        if (actionListener == null)
        {
            NyxListener listener = getPart().getFieldEventHandler(this);
            if (listener == null)
            {
                actionListener = new PrePostFieldListener(this);
            }
            else
            {
                actionListener = (PrePostFieldListener)listener;
            }
            button.addActionListener(actionListener);
        }
        String alignment = getProperty("alignment");
        if (alignment != null) {
            // defaults to center..
            int align = JButton.CENTER;;
            if (alignment.equalsIgnoreCase("left")) {
                align = JButton.LEFT;
            } else if (alignment.equalsIgnoreCase("right")) {
                align = JButton.RIGHT;
            }
            button.setHorizontalAlignment(align);
        }
        String margin = getProperty("margin");
        if (margin != null) {
            Insets insets = SwingUtils.getInsets(margin);
            if (insets != null) {
                button.setMargin(insets);
            }
        }
        String tooltip = getProperty("tooltip");
        if (tooltip != null) {
            button.setToolTipText(tooltip);
        }
        button.setEnabled(isEnabled());
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        processDestroy();
        if (button == null) {
            getPart().removeWidget(this,this);
            return;
        }
        Container container = button.getParent();
        if (actionListener != null)
        {
            button.removeActionListener(actionListener);
        }
        actionListener = null;
        if (focusListener != null)
        {
            button.removeFocusListener(focusListener);
        }
        focusListener = null;
        if (listenerList != null) {
            for (Iterator it = listenerList.iterator(); it.hasNext();) {
                Object l = it.next();
                // weird swing stuff. Why not have an addListener and
                // just a removelistener ???
                if (l instanceof ActionListener) {
                    button.removeActionListener((ActionListener)l);
                } else if (l instanceof FocusListener) {
                    button.removeFocusListener((FocusListener)l);
                }
            }
        }
        removeAllRules();
        button.setVisible(false);
        if (container != null)
        {
            container.remove(button);
        }
        getPart().removeWidget(this,this);
        button = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }


    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
        if (listener instanceof ActionListener) {
            if (listenerList == null) {
                listenerList = new ArrayList();
            }
            listenerList.add(listener);
            initialize();
            if (listener instanceof ActionListener) {
                this.button.addActionListener((ActionListener)listener);
            } else if (listener instanceof FocusListener) {
                button.addFocusListener((FocusListener)listener);
            }
        }
    }

}
