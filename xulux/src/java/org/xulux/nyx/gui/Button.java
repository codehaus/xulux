/*
 $Id: Button.java,v 1.10.2.2 2003-05-04 15:27:40 mvdb Exp $

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
package org.xulux.nyx.gui;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.xulux.nyx.swing.listeners.PrePostFieldListener;


/**
 * Represents a button in the gui
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Button.java,v 1.10.2.2 2003-05-04 15:27:40 mvdb Exp $
 */
public class Button extends Widget
{
    
    private JButton button;
    private PrePostFieldListener actionListener;
    private FocusListener focusListener;

    /**
     * Constructor for Button.
     */
    public Button(String field)
    {
        super(field);
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
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        if (getValue()!=null)
        {
            button.setText(getValue().toString());
        }
        if (actionListener == null && getRules()!=null)
        {
            actionListener = getPart().getFieldEventHandler();
            actionListener.setWidget(this);
            button.addActionListener(actionListener);
        }
        String defButton = getProperty("defaultbutton");
        if (defButton != null) {
            if (defButton.equalsIgnoreCase("true")){
                this.button.setDefaultCapable(true);
                JRootPane pane = SwingUtilities.getRootPane(this.button);
                if ( pane != null ) 
                {
                    System.out.println("Pane is not null");
                    pane.setDefaultButton(this.button);
                }
            }
        }
        
        String image = getProperty("image");
        if (image != null)
        {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(image));
            button.setIcon(icon);
            button.setFocusPainted(true);
        }
        String disabledImage = getProperty("image-disabled");
        if (disabledImage != null)
        {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(disabledImage));
            button.setDisabledIcon(icon);
            button.setDisabledSelectedIcon(icon);
        }
        String rolloverImage = getProperty("image-rollover");
        if (rolloverImage!=null)
        {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(rolloverImage));
            button.setRolloverIcon(icon);
            button.setRolloverEnabled(true);
        }
        String selectedImage = getProperty("image-selected");
        if (selectedImage != null)
        {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(selectedImage));
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
        button.setEnabled(isEnabled());
        isRefreshing = false;
    }
    
    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
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
     * @see org.xulux.nyx.gui.Widget#isSelectable()
     */
    public boolean isSelectable() {
        return true;
    }

}
