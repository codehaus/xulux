/*
   $Id: CheckBox.java,v 1.10 2004-12-01 11:37:04 mvdb Exp $
   
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
import javax.swing.JCheckBox;
import javax.swing.UIManager;

import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.IDataProvider;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.InvalidValueException;
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
 * @version $Id: CheckBox.java,v 1.10 2004-12-01 11:37:04 mvdb Exp $
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
     * @see org.xulux.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        removeAllRules();
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
     * @see org.xulux.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return checkBox;
    }

    /**
     * @see org.xulux.gui.Widget#initialize()
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
        checkBox.setVisible(isVisible());
        if (getProperty("text") != null) {
            checkBox.setText(getProperty("text"));
        }
        if (getValue() instanceof Boolean) {
            checkBox.setSelected(BooleanUtils.toBoolean((Boolean) getValue()));
        } else if (getValue() instanceof String) {
            checkBox.setSelected(BooleanUtils.toBoolean((String) getValue()));
        }
        ((JCheckBox)getNativeWidget()).setEnabled(isEnabled());
        String backgroundColor = null;
        if (isRequired() && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        } else {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            ((NyxJCheckBox) getNativeWidget()).setRealBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (getProvider() != null) {
          return this.value;
        } else  if (getPart().getBean() == null || getField() == null) {
            return super.getValue();
        }
        IMapping map = XuluxContext.getDictionary().getMapping(getPart().getBean().getClass());
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
    public void setValue(Object object) {
        if (getProvider() != null) {
          IDataProvider pr = XuluxContext.getDictionary().getProvider(getProvider());
          IMapping mapping = null;
          IField field = null;
          if (!(object instanceof String) && this.value == null) {
            this.value = object;
          } else {
            //new Exception().printStackTrace();
            System.err.println("This.value : " + this.value+"<=>"+this.value.getClass());
            mapping = pr.getMapping(this.value);
            if (getField() != null) {
                field = mapping.getField(getField());
            } else {
                field = mapping.getField(this.value);
            }
            try {
              field.setValue(this.value, object);
              setValidValue(true);
            } catch(InvalidValueException ive) {
              setValidValue(false);
            }
          }
        } else {
            if (this.value == null) {
                this.value = "false";
            }
            this.previousValue = this.value;
            IMapping map = XuluxContext.getDictionary().getDefaultProvider().getMapping(getPart().getBean());
            if (map != null) {
                if (getField() != null) {
                    IField f = map.getField(getField());
                    Class cClass = f.getType();
                    if (cClass == Boolean.class || cClass == Boolean.TYPE) {
                        if (object.getClass() == String.class) {
                            object = BooleanUtils.toBooleanObject((String) object);
                        }
                    } else if (cClass == String.class) {
                        if (object.getClass() == Boolean.class) {
                            object = BooleanUtils.toStringTrueFalse((Boolean) object);
                        }
                    }
                    f.setValue(getPart().getBean(), object);
                }
            }
            this.value = object;
        }
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
