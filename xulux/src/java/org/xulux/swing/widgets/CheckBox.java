/*
   $Id: CheckBox.java,v 1.7 2004-04-27 11:01:36 mvdb Exp $
   
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

import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.gui.NyxListener;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.SwingWidget;
import org.xulux.swing.extensions.NyxJCheckBox;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.utils.BooleanUtils;

/**
 * The nyx to swing implementation of a checkbox
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CheckBox.java,v 1.7 2004-04-27 11:01:36 mvdb Exp $
 */
public class CheckBox extends SwingWidget {

    /**
     * The native checkbox
     */
    private NyxJCheckBox checkBox;

    /**
     * The checkbox listener
     */
    private PrePostFieldListener itemListener;

    /**
     * @param name the name of the checkbox
     */
    public CheckBox(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        if (checkBox != null) {
            Container container = checkBox.getParent();
            checkBox.setVisible(false);
            if (container != null) {
                container.remove(checkBox);
            }
            if (itemListener != null) {
                checkBox.removeItemListener(itemListener);
                checkBox.removeFocusListener(itemListener);
            }
            itemListener = null;
            checkBox = null;
        }
        removeAllRules();
        getPart().removeWidget(this, this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return checkBox;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.checkBox = new NyxJCheckBox();
        // set the icon to what is default in Swing..
        this.checkBox.setIcon((Icon) UIManager.get("CheckBox.icon"));
        this.checkBox.setSelectedIcon((Icon) UIManager.get("CheckBox.icon"));
        this.itemListener = new PrePostFieldListener(this);
        // we always need to add a itemlistener to change
        // the value..
        checkBox.addItemListener(this.itemListener);
        checkBox.addFocusListener(this.itemListener);
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        isRefreshing = true;
        initialize();
        if (getProperty("text") != null) {
            checkBox.setText(getProperty("text"));
        }
        if (getValue() instanceof Boolean) {
            checkBox.setSelected(BooleanUtils.toBoolean((Boolean) getValue()));
        } else if (getValue() instanceof String) {
            checkBox.setSelected(BooleanUtils.toBoolean((String) getValue()));
        }
        checkBox.setEnabled(isEnabled());
        String backgroundColor = null;
        if (isRequired() && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        } else {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            checkBox.setRealBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (getPart().getBean() == null || getField() == null) {
            return super.getValue();
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
                Class cClass = f.getType();
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

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return BooleanUtils.toBooleanObject(checkBox.isSelected());
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
    }

}
