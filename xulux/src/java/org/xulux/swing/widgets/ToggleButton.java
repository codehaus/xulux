/*
   $Id: ToggleButton.java,v 1.5 2004-04-01 16:15:08 mvdb Exp $
   
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.gui.Widget;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.swing.util.SwingUtils;
import org.xulux.utils.BooleanUtils;

/**
 * Represents a togglebutton in the g

/**
 * Represents a togglebutton in the gui.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ToggleButton.java,v 1.5 2004-04-01 16:15:08 mvdb Exp $
 */
public class ToggleButton extends Widget {

    /**
     * The native togglebutton
     */
    protected JToggleButton toggleButton;
    /**
     * the itemlistner
     */
    protected PrePostFieldListener itemListener;
    /**
     * the focuslistener
     */
    protected FocusListener focusListener;

    /**
     * @param name the name of the togglebutton
     */
    public ToggleButton(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        if (!initialized) {
            return;
        }
        processDestroy();
        if (toggleButton != null) {
            if (itemListener != null) {
                toggleButton.removeItemListener(itemListener);
            }
            toggleButton.removeAll();
            Container container = toggleButton.getParent();
            if (container != null) {
                container.remove(toggleButton);
            }
            toggleButton = null;
        }
        removeAllRules();
        getPart().removeWidget(this, this);

    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return this.toggleButton;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        toggleButton = new JToggleButton();
        itemListener = new PrePostFieldListener(this);
        toggleButton.addItemListener(this.itemListener);
        initialized = true;
        refresh();
        processInit();

    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
            return;
        }
        isRefreshing = true;
        initialize();
        if (getProperty("text") != null) {
            toggleButton.setText(getProperty("text"));
        }
        if (getProperty("selected") != null) {
            setValue(BooleanUtils.toBooleanObject(getProperty("selected")));
            setProperty("selected", null);
        }
        if (getValue() instanceof Boolean) {
            toggleButton.setSelected(BooleanUtils.toBoolean((Boolean) getValue()));
        } else if (getValue() instanceof String) {
            toggleButton.setSelected(BooleanUtils.toBoolean((String) getValue()));
        }
        toggleButton.setEnabled(isEnabled());
        String backgroundColor = null;
        // @todo check to see the repainting problem nyx has with togglebuttons.
        // currently I got to the point when the rollover image was still active
        // when leaving the button with the mouse, no events were fired or eaten?
        // need to investigate further.
        //        toggleButton.getModel().addChangeListener(new ChangeListener() {
        //
        //            public void stateChanged(ChangeEvent e) {
        //                System.out.println("statechanged..."+e);
        //                toggleButton.invalidate();
        //                toggleButton.repaint();
        //            }
        //
        //        });
        String image = getProperty("image");
        if (image != null) {
            ImageIcon normalIcon = SwingUtils.getIcon(image, this);
            toggleButton.setIcon(normalIcon);
            toggleButton.setFocusPainted(true);
        }
        String disabledImage = getProperty("image-disabled");
        if (disabledImage != null) {
            ImageIcon icon = SwingUtils.getIcon(disabledImage, this);
            toggleButton.setDisabledIcon(icon);
            toggleButton.setDisabledSelectedIcon(icon);
        }
        String rolloverImage = getProperty("image-rollover");
        if (rolloverImage != null) {
            ImageIcon icon = SwingUtils.getIcon(rolloverImage, this);
            toggleButton.setRolloverEnabled(true);
            toggleButton.setRolloverIcon(icon);
        }

        String selectedImage = getProperty("image-selected");
        if (selectedImage != null) {
            ImageIcon icon = SwingUtils.getIcon(selectedImage, this);
            toggleButton.setSelectedIcon(icon);
            toggleButton.setPressedIcon(icon);
            toggleButton.setRolloverSelectedIcon(icon);
            toggleButton.setRolloverEnabled(true);
            // we need to add a focuslistener to set the image
            // when focus is there to the selected image
            if (this.focusListener == null) {
                this.focusListener = new FocusListener() {
                    Icon normalIcon;
                    /**
                     * @see java.awt.event.FocusListener#focusGained(FocusEvent)
                     */
                    public void focusGained(FocusEvent e) {
                        if (normalIcon == null) {
                            normalIcon = toggleButton.getIcon();
                        }
                        toggleButton.setIcon(toggleButton.getSelectedIcon());
                    }

                    /**
                     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
                     */
                    public void focusLost(FocusEvent e) {
                        String image = getProperty("image");
                        if (image != null) {
                            ImageIcon normalIcon = SwingUtils.getIcon(image, this);
                            if (toggleButton != null) {
                                toggleButton.setIcon(normalIcon);
                                toggleButton.setFocusPainted(true);
                            }
                        }
                    }
                };
                toggleButton.addFocusListener(focusListener);
            }

        }
        toggleButton.repaint();
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (initialized) {
            return BooleanUtils.toBooleanObject(toggleButton.isSelected());
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {

    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (getPart().getBean() == null || getField() == null) {
            Object retValue = super.getValue();
            if (retValue == null) {
                retValue = getGuiValue();
            }
            return retValue;
        }
        IMapping map = Dictionary.getInstance().getMapping(getPart().getBean().getClass());
        if (map != null) {
            IField field = map.getField(getField());
            if (field != null) {
                return field.getValue(getPart().getBean());
            }
        }
        return super.getValue();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        if (this.value == null) {
            this.value = "false";
        }
        this.previousValue = this.value;
        IMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
        if (map != null) {
            if (getField() != null) {
                IField f = map.getField(getField());
                Class cClass = f.getReturnType();
                if (cClass == Boolean.class || cClass == Boolean.TYPE) {
                    if (value.getClass() == String.class) {
                        value = BooleanUtils.toBooleanObject((String) value);
                    }
                } else if (cClass == String.class) {
                    if (value.getClass() == Boolean.class) {
                        value = BooleanUtils.toStringTrueFalse((Boolean) value);
                    }
                }
                f.setValue(getPart().getBean(), value);
            }
        }
        this.value = value;
        if (initialized) {
            refresh();
        }
    }
}
