/*
 $Id: RadioButton.java,v 1.5 2003-11-06 19:53:12 mvdb Exp $

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

import javax.swing.Icon;
import javax.swing.UIManager;

import org.apache.commons.lang.BooleanUtils;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.utils.ColorUtils;
import org.xulux.nyx.swing.extensions.NyxJRadioButton;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;

/**
 * Represents a radiobutton in the gui.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButton.java,v 1.5 2003-11-06 19:53:12 mvdb Exp $
 */
public class RadioButton extends Widget {

    protected NyxJRadioButton radioButton;
    protected PrePostFieldListener itemListener;

    /**
     * @param name
     */
    public RadioButton(String name) {
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
        if (radioButton != null) {
            if (itemListener != null) {
                radioButton.removeItemListener(itemListener);
            }
            radioButton.removeAll();
            Container container = radioButton.getParent();
            if (container != null) {
                container.remove(radioButton);
            }
            radioButton = null;
        }
        removeAllRules();
        getPart().removeWidget(this,this);

    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return this.radioButton;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        radioButton = new NyxJRadioButton();
        // set the icon to what is default in Swing..
        radioButton.setIcon((Icon)UIManager.get("RadioButton.icon"));
        radioButton.setSelectedIcon((Icon)UIManager.get("RadioButton.icon"));
        itemListener = new PrePostFieldListener(this);
        radioButton.addItemListener(this.itemListener);
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
            radioButton.setText(getProperty("text"));
        }
        if (getValue() instanceof Boolean) {
            radioButton.setSelected(BooleanUtils.toBoolean((Boolean)getValue()));
        }else if (getValue() instanceof String) {
            radioButton.setSelected(BooleanUtils.toBoolean((String)getValue()));
        } else {
            radioButton.setSelected(BooleanUtils.toBoolean(getProperty("selected")));
        }
        radioButton.setEnabled(isEnabled());
        String backgroundColor = null;
        if (isRequired() && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        } else {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            radioButton.setRealBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (initialized) {
            return BooleanUtils.toBooleanObject(radioButton.isSelected());
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
