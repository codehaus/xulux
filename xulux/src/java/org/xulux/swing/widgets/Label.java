/*
   $Id: Label.java,v 1.7 2004-04-14 14:16:12 mvdb Exp $
   
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

import javax.swing.JLabel;

import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.bean.BeanMapping;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.SwingWidget;
import org.xulux.swing.util.SwingUtils;
import org.xulux.utils.BooleanUtils;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Label.java,v 1.7 2004-04-14 14:16:12 mvdb Exp $
 */
public class Label extends SwingWidget {

    /**
     * The native label
     */
    private JLabel label;

    /**
     *
     * @param name the name of the label
     */
    public Label(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        if (label != null) {
            Container container = label.getParent();
            label.setVisible(false);
            if (container != null) {
                container.remove(label);
            }
            label = null;
        }
        removeAllRules();
        getPart().removeWidget(this, this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return label;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.label = new JLabel();
        refresh();
        processInit();
    }
    /**
     * For now aligns to the right by default.
     *
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        initialize();
        initializeValue();
        if (getProperty("text") != null) {
            label.setText(getProperty("text"));
        } else {
            label.setText("");
        }
        if (getProperty("icon") != null) {
            label.setIcon(SwingUtils.getIcon(getProperty("icon"), this));
        }
        if (getProperty("icon-disabled") != null) {
            label.setDisabledIcon(SwingUtils.getIcon(getProperty("icon-disabled"), this));
        }
        String ha = getProperty("horizontalalignment");
        // we use the swing default..
        if (ha != null) {
            if (ha.equalsIgnoreCase("left")) {
                label.setHorizontalAlignment(JLabel.LEFT);
            } else if (ha.equalsIgnoreCase("center")) {
                label.setHorizontalAlignment(JLabel.CENTER);
            } else {
                label.setHorizontalAlignment(JLabel.RIGHT);
            }
        }
        String color = null;
        if (isEnabled()) {
            color = getProperty("foreground-color-enabled");
        } else {
            color = getProperty("foreground-color-disabled");
        }
        if (color != null) {
            label.setForeground(ColorUtils.getSwingColor(color));
        }
        String bgColor = null;
        if (isEnabled()) {
            bgColor = getProperty("background-color-enabled");
        } else {
            bgColor = getProperty("background-color-disabled");
        }
        if (bgColor != null) {
            label.setBackground(ColorUtils.getSwingColor(bgColor));
        }
        if (getProperty("enabled.depends") != null) {
            String value = getProperty("enabled.depends");
            Object depValue = getPart().getWidget(value).getValue();
            if (depValue != null) {
                setEnable(BooleanUtils.toBoolean(depValue.toString()));
            } else {
                setEnable(false);
            }
        }
        label.setEnabled(isEnabled());
    }

    /**
     * Initializes the value. This checks to see
     * if you need a .
     *
     */
    protected void initializeValue() {
        // only do a set text when there is a field,
        // else leave it alone..
        if (getField() != null) {
            IMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
            if (map == null) {
                return;
            }
            IField field = map.getField(getField());
            if (field != null) {
                Object value = field.getValue(getPart().getBean());
                if (value == null) {
                    value = "";
                }
                IConverter converter = Dictionary.getConverter(getValue());
                if (converter != null) {
                    value = converter.getGuiValue(value);
                }
                setProperty("text", String.valueOf(value));
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return getValue();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        if (value == null) {
            value = "";
        }
        String currentText = getProperty("text");
        if (currentText != null && currentText.equals(value)) {
            return;
        }
        this.previousValue = currentText;
        // no refresh needed, since it will
        // refresh on setProperty..
        setProperty("text", String.valueOf(value));
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        return getProperty("text");
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
        if (getProperty("text") == null || getProperty("text").equals("")) {
            return true;
        }
        return false;
    }

}
