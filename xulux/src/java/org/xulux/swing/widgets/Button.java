/*
   $Id: Button.java,v 1.13 2004-12-01 11:37:04 mvdb Exp $
   
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
package org.xulux.swing.widgets;

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
import javax.swing.JRootPane;

import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.SwingWidget;
import org.xulux.swing.listeners.DefaultButtonChangeListener;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.swing.listeners.WidgetFocusListener;
import org.xulux.swing.util.SwingUtils;
import org.xulux.utils.BooleanUtils;

/**
 * Represents a button in the gui
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Button.java,v 1.13 2004-12-01 11:37:04 mvdb Exp $
 */
public class Button extends SwingWidget {

    /**
     * the native button
     */
    private JButton button;
    /**
     * the actionlistener
     */
    private PrePostFieldListener actionListener;
    /**
     * the image focuslistener
     */
    private FocusListener imageFocusListener;
    /**
     * The focuslistener. This way we receive focusevents
     * when someone has selected the button by leaving another field
     * by way of pressing tab.
     */
    private FocusListener focusListener;
    
    private DefaultButtonChangeListener changeListener;
    /**
     * the nyx listeners
     */
    private List listenerList;

    /**
     * Constructor for Button.
     * @param name the name of the button
     */
    public Button(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return button;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        button = new JButton();
        initialized = true;
        if ("cancel".equalsIgnoreCase(getProperty("defaultaction"))) {
            Widget p = getParent();
            while (p.getParent() != null) {
                p = p.getParent();
            }
        }
        if (!isRefreshing()) {
            refresh();
        }
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        isRefreshing = true;
        initialize();
        button.setVisible(isVisible());
        String image = getProperty("image");
        if (image != null) {
            ImageIcon icon = SwingUtils.getIcon(image, this);
            button.setIcon(icon);
            button.setFocusPainted(true);
        }
        String disabledImage = getProperty("image-disabled");
        if (disabledImage != null) {
            ImageIcon icon = SwingUtils.getIcon(disabledImage, this);
            button.setDisabledIcon(icon);
            button.setDisabledSelectedIcon(icon);
        }
        String rolloverImage = getProperty("image-rollover");
        if (rolloverImage != null) {
            ImageIcon icon = SwingUtils.getIcon(rolloverImage, this);
            button.setRolloverIcon(icon);
            button.setRolloverEnabled(true);
        }
        String selectedImage = getProperty("image-selected");
        if (selectedImage != null) {
            ImageIcon icon = SwingUtils.getIcon(selectedImage, this);
            button.setSelectedIcon(icon);
            button.setPressedIcon(icon);
            button.setRolloverSelectedIcon(icon);
            button.setRolloverEnabled(true);
            // we need to add a focuslistener to set the image
            // when focus is there to the selected image
            if (this.imageFocusListener == null) {
                this.imageFocusListener = new FocusListener() {
                    private Icon normalIcon;
                    /**
                     * @see java.awt.event.FocusListener#focusGained(FocusEvent)
                     */
                    public void focusGained(FocusEvent e) {
                        normalIcon = button.getIcon();
                        button.setIcon(button.getSelectedIcon());
                    }

                    /**
                     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
                     */
                    public void focusLost(FocusEvent e) {
                        if (normalIcon != null) {
                            button.setIcon(normalIcon);
                            normalIcon = null;
                        }
                    }
                };
                button.addFocusListener(imageFocusListener);
            }

        }
        if (getProperty("text") != null) {
            button.setText(getProperty("text"));
        }
        if (actionListener == null) {
            NyxListener listener = getPart().getFieldEventHandler(this);
            if (listener == null) {
                actionListener = new PrePostFieldListener(this);
            } else {
                actionListener = (PrePostFieldListener) listener;
            }
            button.addActionListener(actionListener);
        }
        if (focusListener == null) {
          focusListener = new WidgetFocusListener(this);
          button.addFocusListener(focusListener);
        }
        String alignment = getProperty("alignment");
        if (alignment != null) {
            // defaults to center..
            int align = JButton.CENTER;
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
        button.setToolTipText(getProperty("tooltip"));
        if (BooleanUtils.toBoolean(getProperty("defaultbutton"))) {
            button.setDefaultCapable(true);
            JRootPane pane = button.getRootPane();
            if (pane != null) {
                pane.setDefaultButton(button);
                if (changeListener == null) {
                  changeListener = new DefaultButtonChangeListener();
                }
                button.addChangeListener(changeListener);
            }
        } else {
          // remove the changelistener, if we are not using it.
          button.removeChangeListener(changeListener);
          changeListener = null;
        }
        button.setEnabled(isEnabled());
        if (getRectangle().getX() > 0 && getRectangle().getY() > 0) {
	        button.setPreferredSize(getRectangle().getRectangle().getSize());
	        button.setMinimumSize(getRectangle().getRectangle().getSize());
	        button.setMaximumSize(getRectangle().getRectangle().getSize());
	        button.setSize(getRectangle().getRectangle().getSize());
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        removeAllRules();
        if (button == null) {
            getPart().removeWidget(this, this);
            return;
        }
        Container container = button.getParent();
        if (actionListener != null) {
            button.removeActionListener(actionListener);
            actionListener = null;
        }
        if (imageFocusListener != null) {
            button.removeFocusListener(imageFocusListener);
        }
        if (changeListener != null) {
          button.removeChangeListener(changeListener);
          changeListener = null;
        }
        imageFocusListener = null;
        if (focusListener != null) {
          button.removeFocusListener(focusListener);
          focusListener = null;
        }
        if (listenerList != null) {
            for (Iterator it = listenerList.iterator(); it.hasNext();) {
                Object l = it.next();
                if (l instanceof ActionListener) {
                    button.removeActionListener((ActionListener) l);
                } else if (l instanceof FocusListener) {
                    button.removeFocusListener((FocusListener) l);
                }
            }
        }
        button.setVisible(false);
        if (container != null) {
            container.remove(button);
        }
        getPart().removeWidget(this, this);
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
                this.button.addActionListener((ActionListener) listener);
            } else if (listener instanceof FocusListener) {
                button.addFocusListener((FocusListener) listener);
            }
        }
    }

}
