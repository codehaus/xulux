/*
 $Id: ToggleButton.java,v 1.2 2003-11-13 15:20:57 mvdb Exp $

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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang.BooleanUtils;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;
import org.xulux.nyx.swing.util.SwingUtils;

/**
 * Represents a togglebutton in the gui.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ToggleButton.java,v 1.2 2003-11-13 15:20:57 mvdb Exp $
 */
public class ToggleButton extends Widget {

    protected JToggleButton toggleButton;
    protected PrePostFieldListener itemListener;
    protected FocusListener focusListener;

    /**
     * @param name
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
        getPart().removeWidget(this,this);

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
        if (getValue() instanceof Boolean) {
            toggleButton.setSelected(BooleanUtils.toBoolean((Boolean)getValue()));
        }else if (getValue() instanceof String) {
            toggleButton.setSelected(BooleanUtils.toBoolean((String)getValue()));
        } else {
            toggleButton.setSelected(BooleanUtils.toBoolean(getProperty("selected")));
        }
        toggleButton.setEnabled(isEnabled());
        String backgroundColor = null;
        // TODO: check to see the repainting problem nyx has with togglebuttons.
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
        if (image != null)
        {
            ImageIcon normalIcon = SwingUtils.getIcon(image,this);
            toggleButton.setIcon(normalIcon);
            toggleButton.setFocusPainted(true);
        }
        String disabledImage = getProperty("image-disabled");
        if (disabledImage != null)
        {
            ImageIcon icon = SwingUtils.getIcon(disabledImage, this);
            toggleButton.setDisabledIcon(icon);
            toggleButton.setDisabledSelectedIcon(icon);
        }
        String rolloverImage = getProperty("image-rollover");
        if (rolloverImage!=null)
        {
            ImageIcon icon = SwingUtils.getIcon(rolloverImage, this);
            toggleButton.setRolloverEnabled(true);
            toggleButton.setRolloverIcon(icon);
        }
        
        String selectedImage = getProperty("image-selected");
        if (selectedImage != null)
        {
            ImageIcon icon = SwingUtils.getIcon(selectedImage,this);
            toggleButton.setSelectedIcon(icon);
            toggleButton.setPressedIcon(icon);
            toggleButton.setRolloverSelectedIcon(icon);
            toggleButton.setRolloverEnabled(true);
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
                        System.out.println("Gained : "+e);
                        if (normalIcon == null) {
                            normalIcon = toggleButton.getIcon();
                        }
                        toggleButton.setIcon(toggleButton.getSelectedIcon());
                    }

                    /**
                     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
                     */
                    public void focusLost(FocusEvent e)
                    {
                        System.out.println("lost : "+e);
                        String image = getProperty("image");
                        if (image != null) {
                            ImageIcon normalIcon = SwingUtils.getIcon(image,this);
                            toggleButton.setIcon(normalIcon);
                            toggleButton.setFocusPainted(true);
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
            Object retValue =  getGuiValue();
            if (retValue == null) {
                retValue = super.getValue();
            }
            return retValue;
        }
        BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean().getClass());
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
        BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
        if (map != null) {
            if (getField() != null) {
                IField f = map.getField(getField());
                Class cClass = f.getReturnType();
                if (cClass == Boolean.class || cClass == Boolean.TYPE) {
                    if (value.getClass() == String.class) {
                        value = BooleanUtils.toBooleanObject((String)value);
                    }
                } else if (cClass == String.class) {
                    if (value.getClass() == Boolean.class) {
                        value = BooleanUtils.toStringTrueFalse((Boolean)value);
                    }
                }
                f.setValue(getPart().getBean(),value);
            }
        }
        this.value = value;
        if (initialized) {
            refresh();
        }
    }



}
