/*
 $Id: CheckBox.java,v 1.2 2003-12-23 02:00:06 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.swing.widgets;

import java.awt.Container;

import javax.swing.Icon;
import javax.swing.UIManager;

import org.xulux.global.BeanMapping;
import org.xulux.global.Dictionary;
import org.xulux.global.IField;
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
 * @version $Id: CheckBox.java,v 1.2 2003-12-23 02:00:06 mvdb Exp $
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
