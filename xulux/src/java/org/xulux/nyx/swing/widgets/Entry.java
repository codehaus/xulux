/*
 $Id: Entry.java,v 1.33 2003-10-23 13:42:09 mvdb Exp $

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

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IConverter;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.swing.SwingWidget;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;
import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * Represents an entry field
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Entry.java,v 1.33 2003-10-23 13:42:09 mvdb Exp $
 */
public class Entry 
extends SwingWidget
{
    private static Log log = LogFactory.getLog(Entry.class);
    private Dimension size;
    protected boolean setValueCalled = false;
    
    /** 
     * A textcomponentallows overriding by
     * similar classes.
     */
    protected JTextComponent textComponent;
    
    protected PrePostFieldListener focusListener;
    protected PrePostFieldListener immidiateListener;
    
    protected Class valueClass;
    
    
    /**
     * Constructor for Entry.
     */
    public Entry(String name)
    {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        processDestroy();
        if (textComponent != null)
        {
            textComponent.removeAll();
            Container container = textComponent.getParent();
            if (container != null)
            {
                container.remove(textComponent);
            }
            textComponent = null;
        }
        removeAllRules();
        getPart().removeWidget(this,this);
        initialized = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        if (!initialized) {
            initialize();
        }
        return textComponent;
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
        if (getProperty("valueclass") != null) {
            this.valueClass = ClassLoaderUtils.getClass(getProperty("valueclass"));
        }
        if (isImmidiate()) {
            if (this.immidiateListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null) {
                    this.immidiateListener = (PrePostFieldListener)listener;
                } else {
                    this.immidiateListener = new PrePostFieldListener(this);
                }
            }
        }
        if (isVisible()) {
            if (focusListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener == null) {
                    focusListener = (PrePostFieldListener)listener;
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
                BeanMapping mapping = Dictionary.getInstance().getMapping(getPart().getBean());
                if (mapping != null) {
                    IField field = mapping.getField(iv);
                    if (field != null) {
                        this.value = field.getValue(getPart().getBean());
                        textComponent.setText(String.valueOf(this.value));
                    }
                }
            }else if (ivType == null || ivType.equals("string")) {
                this.value = iv;
                textComponent.setText(iv);
            } 
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        textComponent.setEnabled(isEnabled());
        textComponent.setVisible(isVisible());
        textComponent.setPreferredSize(this.size);
        String backgroundColor = null;
        if (isRequired()  && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        } else  {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            textComponent.setBackground(new Color(Integer.parseInt(backgroundColor,16)));
        }
        if (getProperty("enabled.depends")!= null) {
            String value = getProperty("enabled.depends");
            Object depValue = getPart().getWidget(value).getValue();
            if (depValue != null) {
                setEnable(BooleanUtils.toBoolean(depValue.toString()));
            } else {
                setEnable(false);
            }
        }
        textComponent.repaint();
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.ValueWidget#getValue()
     */
    public Object getValue()
    {
        return this.value;
//        Object object = null;
//        IConverter converter = Dictionary.getConverter(this.value);
//        System.err.println("converter : "+converter);
//        if (converter != null) {
//            object = converter.getBeanValue(this.value);
//        } else {
//            object = this.value;
//        }
//        System.err.println("Object : "+object);
//        return object;
    }
    
    /**
     * Lets changes reflect onscreen.
     * @param value
     */
    protected void initializeValue()
    {
        if (getField() == null) {
            if (getValue() != null) {
                if (getProperty("entryfields") != null) {
                    String entryFields = getProperty("entryfields");
                    String entryValue = "";
                    BeanMapping mapping = Dictionary.getInstance().getMapping(getValue());
                    StringTokenizer stn = new StringTokenizer(entryFields, ",");
                        while (stn.hasMoreTokens())
                        {
                            String token = stn.nextToken();
                            String strPart = "";
                            IField iField = mapping.getField(token);
                            if (iField != null)
                            {
                                strPart = iField.getValue(getValue()).toString();
                            }
                            else
                            {
                                //allow seperators in the field list.
                                strPart = token;
                            }
                            entryValue+=strPart;
                        }
                    textComponent.setText(String.valueOf(entryValue));
                    return;
                }
                IConverter converter = Dictionary.getConverter(getValue());
                if (converter != null) {
                    textComponent.setText((String)converter.getGuiValue(getValue()));
                } else {
                    textComponent.setText(String.valueOf(getValue()));
                }                    
            } else {
                textComponent.setText("");
            }
            return;
        }
        BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
        if (map == null) {
            textComponent.setText("");
            return;
        }
        IField field = map.getField(getField());
        if (field == null) {
            if (log.isWarnEnabled()) {
                log.warn("Field "+getField()+" is not present in the dictionary");
                log.warn("Widget : "+getName());
            }
            textComponent.setText("");
            return;
        }
        Object beanValue = field.getValue(getPart().getBean());
        this.value = beanValue;
        if (this.value == null) {
            textComponent.setText("");
        } else {
            // we assume toString method here.
            // TODO: Implement fields some more
            // so it can be a comination of fields
            // that turn up here.
            
            // If this is not an array, do a toString
            if (!this.value.getClass().isArray()) {
                IConverter converter = Dictionary.getConverter(this.value);
                if (converter != null) {
                    textComponent.setText((String)converter.getGuiValue(this.value));
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
     * @param object
     */
    public void setValue(Object object)
    {
        // if there is not field present
        // we set the value passed in
        // and check to see if the field
        // needs updating or not.
        if (getField() == null) {
            if (object != null) {
                if (valueClass != null && 
                   !valueClass.isAssignableFrom(object.getClass())) {
                    IConverter converter = Dictionary.getConverter(valueClass);
                    if (converter != null) {
                        object = converter.getBeanValue(object);
                    }
                }
            }
            if (object != this.value) {
                this.previousValue = this.value;
            }
            this.value = object;
//            if (getValue() != null) {
//                if (valueClass != null) {
//                    if (valueClass.isAssignableFrom(getValue())) {
//                        IConverter converter =  Dictionary.getConverter(valueClass);
//                        if (converter != null) {
//                            this.previousValue = getValue();
//                            this.value = converter.getBeanValue(object);
//                        }
//                    }
//                if (getValue() != object) {
//                    this.previousValue = getValue();
//                    this.value = object;
//                } else {
//                    // nothing changed, so return
//                    return;
//                }
//            } else {
//                this.value = object;
//            }
        } else {
            BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
            if (map != null) {
                IField field = map.getField(getField());
                // set the previous value
                if (field != null) {
                    this.previousValue = field.getValue(getPart().getBean());
                    IConverter converter = Dictionary.getConverter(field.getReturnType());
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
    public void clear()
    {
        if (textComponent == null)
        {
            this.value = null;
        }
        else
        {
            this.value = null;
            textComponent.setText("");
        }
        if (initialized)
        {
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
        if (getGuiValue() == null ||
            getGuiValue().equals("")) {
                return true;
        }
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
        // TODO
    }

}
