/*
   $Id: Entry.java,v 1.16 2004-06-29 13:09:34 mvdb Exp $
   
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
import java.awt.Dimension;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IDataProvider;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.InvalidValueException;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.gui.NyxListener;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.SwingWidget;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.utils.BooleanUtils;
import org.xulux.utils.ClassLoaderUtils;

/**
 * Represents an entry field
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Entry.java,v 1.16 2004-06-29 13:09:34 mvdb Exp $
 */
public class Entry extends SwingWidget {
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(Entry.class);
    /**
     * The dimensions
     */
    private Dimension size;
    /**
     * is setValue called ?
     */
    protected boolean setValueCalled = false;

    /**
     * A textcomponent allows overriding by
     * similar classes.
     */
    protected JTextComponent textComponent;

    /**
     * The focuslistener
     */
    protected PrePostFieldListener focusListener;
    /**
     * the immidiatelistener
     */
    protected PrePostFieldListener immidiateListener;
    /**
     * the value class
     */
    protected Class valueClass;

    /**
     * Constructor for Entry.
     * @param name the name of the entry
     */
    public Entry(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        if (textComponent != null) {
            textComponent.removeAll();
            Container container = textComponent.getParent();
            if (container != null) {
                container.remove(textComponent);
            }
            textComponent = null;
        }
        removeAllRules();
        if (getPart() != null) {
          getPart().removeWidget(this, this);
        }
        initialized = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        return textComponent;
    }

    /**
     * @see org.xulux.gui.Widget#setProperty(java.lang.String, java.lang.Object)
     */
    public void setProperty(String key, Object value) {
      if (key != null) {
        key = key.toLowerCase();
      }
      if (key != null && key.equals("valueclass")) {
          setLazyProperty(key, value);
          if (value != null) {
            if (value instanceof String) {
              this.valueClass = ClassLoaderUtils.getClass((String) value);
            } else if (value instanceof Class) {
              this.valueClass = (Class) value;
            } else {
              this.valueClass = value.getClass();
            }
          } else {
            this.valueClass = null;
          }
      } else {
          super.setProperty(key, value);
      }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.setValueCalled = true;
        textComponent = new JTextField();
        if (isImmidiate()) {
            if (this.immidiateListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null) {
                    this.immidiateListener = (PrePostFieldListener) listener;
                } else {
                    this.immidiateListener = new PrePostFieldListener(this);
                }
            }
        }
        if (isVisible()) {
            if (focusListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener == null) {
                    focusListener = (PrePostFieldListener) listener;
                } else {
                    focusListener = new PrePostFieldListener(this);
                }
                textComponent.addFocusListener(focusListener);
            }
        }
        initInitialValue();
        initializeValue();
        refresh();
        processInit();
        this.setValueCalled = false;
    }

    /**
     * Initializes the initial value, if any.
     *
     */
    protected void initInitialValue() {
        String iv = getProperty("initialvalue");
        if (iv != null) {
            String ivType = getProperty("initialvalue.type");
            if (ivType != null && ivType.equals("field")) {
                IMapping mapping = Dictionary.getInstance().getMapping(getPart().getBean());
                if (mapping != null) {
                    IField field = mapping.getField(iv);
                    if (field != null) {
                        this.value = field.getValue(getPart().getBean());
                        textComponent.setText(String.valueOf(this.value));
                    }
                }
            } else if (ivType == null || ivType.equals("string")) {
                this.value = iv;
                textComponent.setText(iv);
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        isRefreshing = true;
        initialize();
        initializeValue();
        textComponent.setEnabled(isEnabled());
        textComponent.setVisible(isVisible());
        textComponent.setPreferredSize(this.size);
        String backgroundColor = null;
        if (isRequired() && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        }
        if (backgroundColor == null) {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            textComponent.setBackground(ColorUtils.getSwingColor(backgroundColor));
        }

        String foreGroundColor = null;
        if (isRequired() && isEnabled()) {
            foreGroundColor = getProperty("required-foreground-color");
        } else if (!isEnabled()) {
            foreGroundColor = getProperty("disabled-foreground-color");
            if (foreGroundColor != null) {
                textComponent.setDisabledTextColor(ColorUtils.getSwingColor(foreGroundColor));
                foreGroundColor = null;
            }
        }
        if (foreGroundColor == null) {
            foreGroundColor = getProperty("default-foreground-color");
        }
        if (foreGroundColor != null) {
            textComponent.setForeground(ColorUtils.getSwingColor(foreGroundColor));
        }
        if (getField() != null && getField().startsWith("?")) {
            initializeValue();
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
        textComponent.repaint();
        textComponent.setCaretPosition(0);
        isRefreshing = false;
    }

    /**
     *
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        if (getField() != null) {
          if (getProperty("converter.class") != null) {
              IConverter converter = (IConverter) ClassLoaderUtils.getObjectFromClassString(getProperty("converter.class"));
              if (converter != null) {
                  return converter.getBeanValue(this.value);
              }
          }
        }
        return this.value;
    }

    /**
     * Lets changes reflect onscreen.
     * @param value
     * @todo Make this all utility classes, since this is a lot of code reproducing !
    
     */
    protected void initializeValue() {
        if (getProvider() != null) {
            IDataProvider pr = XuluxContext.getDictionary().getProvider(getProvider());
            IMapping mapping = null;
            mapping = pr.getMapping(this.value);
            IField field = null;
            if (getField() != null) {
                field = mapping.getField(getField());
            } else {
                field = mapping.getField(this.value);
            }
            textComponent.setText((String) field.getValue(this.value));
            return;
        } else if (getField() == null) {
            if (getValue() != null) {
                if (getProperty("entryfields") != null) {
                    String entryFields = getProperty("entryfields");
                    String entryValue = "";
                    IMapping mapping = Dictionary.getInstance().getMapping(getValue());
                    StringTokenizer stn = new StringTokenizer(entryFields, ",");
                    while (stn.hasMoreTokens()) {
                        String token = stn.nextToken();
                        String strPart = "";
                        IField iField = mapping.getField(token);
                        if (iField != null) {
                            strPart = iField.getValue(getValue()).toString();
                        } else {
                            //allow seperators in the field list.
                            strPart = token;
                        }
                        entryValue += strPart;
                    }
                    textComponent.setText(String.valueOf(entryValue));
                    return;
                }
                IConverter converter = null;
                if (getProperty("converter.class") != null) {
                    converter = (IConverter) ClassLoaderUtils.getObjectFromClassString(getProperty("converter.class"));
                } else {
                    converter = Dictionary.getConverter(getValue());
                }
                if (converter != null) {
                    textComponent.setText((String) converter.getGuiValue(getValue()));
                } else {
                    textComponent.setText(String.valueOf(getValue()));
                }
            } else {
                textComponent.setText("");
            }
            return;
        }
        Object bean = null;
        String tmpField = null;
        if (getField().startsWith("?")) {
            int dotIndex = getField().indexOf('.');
            if (dotIndex != -1) {
                bean = getPart().getWidget(getField().substring(1, dotIndex)).getValue();
                if (bean == null) {
                    textComponent.setText("");
                    return;
                } else {
                  tmpField = getField().substring(dotIndex+1);
                }
            }
        }
        if (bean == null) {
            bean = getPart().getBean();
        }
        IMapping map = Dictionary.getInstance().getMapping(bean);
        if (map == null) {
            textComponent.setText("");
            return;
        }
        IField field = null;
        if (tmpField == null) {
          field = map.getField(getField());
        } else {
          field = map.getField(tmpField);
        }
        if (field == null) {
            if (log.isWarnEnabled()) {
                log.warn("Field " + getField() + " is not present in the dictionary");
                log.warn("Widget : " + getName());
            }
            textComponent.setText("");
            return;
        }
        Object beanValue = field.getValue(bean);
        this.value = beanValue;
        if (this.value == null) {
            textComponent.setText("");
        } else {
            // we assume toString method here.
            // Implement fields some more
            // so it can be a comination of fields
            // that turn up here.

            // If this is not an array, do a toString
            if (!this.value.getClass().isArray()) {
                IConverter converter = Dictionary.getConverter(this.value);
                if (converter != null) {
                    textComponent.setText((String) converter.getGuiValue(this.value));
                } else {
                    textComponent.setText(this.value.toString());
                }
            } else {
                textComponent.setText("Invalid : Array");
            }
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#setValue(Object)
     */
    public void setValue(Object object) {
        if (this.valueClass == null && getProperty("valueclass") != null) {
            this.valueClass = ClassLoaderUtils.getClass(getProperty("valueclass"));
        }
        // if there is not field present
        // we set the value passed in
        // and check to see if the field
        // needs updating or not.
        if (getProvider() != null) {
          IDataProvider pr = XuluxContext.getDictionary().getProvider(getProvider());
          IMapping mapping = null;
          IField field = null;
          if (!(object instanceof String) && this.value == null) {
            this.value = object;
          } else {
            System.err.println("This.value : " + this.value.getClass());
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
        } else if (getField() == null) {
            if (object != null) {
                if (valueClass != null && !valueClass.isAssignableFrom(object.getClass())) {
                    IConverter converter = null;
                    if (getProperty("converter.class") != null) {
                        converter = (IConverter) ClassLoaderUtils.getObjectFromClassString(getProperty("converter.class"));
                    } else {
                        converter = Dictionary.getConverter(valueClass);
                    }
                    if (converter != null) {
                        object = converter.getBeanValue(object);
                    }
                }
            }
            if (object != this.value) {
                this.previousValue = this.value;
            }
            this.value = object;
        } else {
            IMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
            if (map != null) {
                IField field = map.getField(getField());
                // set the previous value
                if (field != null) {
                    this.previousValue = field.getValue(getPart().getBean());
                    IConverter converter = Dictionary.getConverter(field.getType());
                    if (converter != null) {
                        object = converter.getBeanValue(object);
                    }
                    field.setValue(getPart().getBean(), object);
                }
            }
        }
        if (initialized) {
            initializeValue();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#clear()
     */
    public void clear() {
        this.value = null;
        if (textComponent != null) {
            textComponent.setText("");
        }
        if (initialized) {
            refresh();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (!initialized) {
            initialize();
        }
        return textComponent.getText();
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
        if (getGuiValue() == null || getGuiValue().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
    }

}
