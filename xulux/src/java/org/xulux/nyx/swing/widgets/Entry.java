/*
 $Id: Entry.java,v 1.10 2003-07-17 06:29:24 mvdb Exp $

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

import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.swing.SwingWidget;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;

/**
 * Represents an entry field
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Entry.java,v 1.10 2003-07-17 06:29:24 mvdb Exp $
 */
public class Entry 
extends SwingWidget
{
    private static Log log = LogFactory.getLog(Entry.class);
    private Dimension size;
    private String text;
    private boolean setValueCalled = false;
    
    /** 
     * For now internally very swing specific
     */
    JTextField textField;
    
    PrePostFieldListener focusListener;
    PrePostFieldListener immidiateListener;
    
    
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
        if (textField != null)
        {
            textField.removeAll();
            Container container = textField.getParent();
            if (container != null)
            {
                container.remove(textField);
            }
            textField = null;
        }
        removeAllRules();
        getPart().removeWidget(this,this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        if (!initialized)
        {
            initialize();
        }
        return textField;
    }
    
    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize()
    {
        if (this.initialized)
        {
            return;
        }
        this.initialized = true;
        this.setValueCalled = true;
        textField = new JTextField();
        if (isImmidiate())
        {
            if (this.immidiateListener == null)
            {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null)
                {
                    this.immidiateListener = (PrePostFieldListener)listener;
                }
                else
                {
                    this.immidiateListener = new PrePostFieldListener(this);
                }
            }
        }
        if (isVisible())
        {
            if (focusListener == null)
            {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener == null)
                {
                    focusListener = (PrePostFieldListener)listener;
                }
                else
                {
                    focusListener = new PrePostFieldListener(this);
                }
                textField.addFocusListener(focusListener);
            }
        }
        initializeValue();
        refresh();
        this.setValueCalled = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        textField.setEnabled(isEnabled());
        textField.setVisible(isVisible());
        textField.setPreferredSize(this.size);
        String backgroundColor = null;
        if (isRequired()  && isEnabled())
        {
            backgroundColor = getProperty("required-background-color");
        }
        else if (!isEnabled())
        {
            backgroundColor = getProperty("disabled-background-color");
        }
        else
        {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null)
        {
            textField.setBackground(new Color(Integer.parseInt(backgroundColor,16)));
        }
        textField.repaint();
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.ValueWidget#getValue()
     */
    public Object getValue()
    {
        return this.value;
    }
    
    /**
     * Lets changes reflect onscreen.
     * @param value
     */
    private void initializeValue()
    {
        // TODO: Make sure when there is no field, it still works!!
        if (getField() == null) {
            return;
        }
        BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
        if (map == null) {
            textField.setText("");
            return;
        }
        IField field = map.getField(getField());
        if (field == null) {
            if (log.isWarnEnabled()) {
                log.warn("Field "+getField()+" is not present in the dictionary");
            }
            textField.setText("");
            return;
        }
        Object beanValue = field.getValue(getPart().getBean());
        this.value = beanValue;
        if (this.value == null) {
            textField.setText("");
        } else {
            // we assume toString method here.
            // TODO: Implement fields some more
            // so it can be a comination of fields
            // that turn up here.
            
            // If this is not an array, do a toString
            if (!this.value.getClass().isArray()) { 
                textField.setText(this.value.toString());
            } else {
                textField.setText("Invalid : Array");
            }
        }
    }
    /**
     * @see org.xulux.nyx.gui.ValueWidget#setValue(Object)
     * @param val
     */
    public void setValue(Object object)
    {
        // if there is not field present
        // we set the value passed in
        // and check to see if the field
        // needs updating or not.
        if (getField() == null) {
            if (getValue() != null) {
                if (getValue() != object) {
                    // nothing changed, so return
                    this.previousValue = getValue();
                    return;
                }
            } else {
                this.value = object;
            }
        } else {
            BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
            IField field = map.getField(getField());
            field.setValue(getPart().getBean(), object);
        }
        if (initialized)
        {
            initializeValue();
        }
    }
    /**
     * @see org.xulux.nyx.gui.Widget#clear()
     */
    public void clear()
    {
        if (textField == null)
        {
            this.value = null;
        }
        else
        {
            this.value = null;
            textField.setText("");
        }
        if (initialized)
        {
            refresh();
        }
    }

    /**
     * Returns the text.
     * @return String
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets the text.
     * @param text The text to set
     */
    public void setText(String text)
    {
        this.text = text;
    }
    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return textField.getText();
    }
    
}
