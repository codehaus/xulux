/*
   $Id: RadioButton.java,v 1.4 2004-03-16 14:35:14 mvdb Exp $
   
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

import javax.swing.Icon;
import javax.swing.UIManager;

import org.xulux.dataprovider.BeanMapping;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.gui.Widget;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.extensions.NyxJRadioButton;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.utils.BooleanUtils;

/**
 * Represents a radiobutton in the gui.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButton.java,v 1.4 2004-03-16 14:35:14 mvdb Exp $
 */
public class RadioButton extends Widget {

    /**
     * the native radiobutton
     */
    protected NyxJRadioButton radioButton;
    /**
     * the itemlistener
     */
    protected PrePostFieldListener itemListener;

    /**
     * @param name the name of the radiobutton
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
        getPart().removeWidget(this, this);

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
        radioButton.setIcon((Icon) UIManager.get("RadioButton.icon"));
        radioButton.setSelectedIcon((Icon) UIManager.get("RadioButton.icon"));
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
            radioButton.setSelected(BooleanUtils.toBoolean((Boolean) getValue()));
        } else if (getValue() instanceof String) {
            radioButton.setSelected(BooleanUtils.toBoolean((String) getValue()));
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
            Object retValue = getGuiValue();
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
