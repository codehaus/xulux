/*
 $Id: Entry.java,v 1.17 2002-11-27 02:33:44 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JTextField;

import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.swing.listeners.ImmidiateListener;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;

/**
 * Represents an entry field
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Entry.java,v 1.17 2002-11-27 02:33:44 mvdb Exp $
 */
public class Entry 
extends Widget
{
    private Dimension size;
    
    /** 
     * For now internally very swing specific
     */
    JTextField textField;
    
    PrePostFieldListener focusListener;
    PrePostFieldListener immidiateListener;
    
    
    /**
     * Constructor for Entry.
     */
    public Entry(String field)
    {
        super(field);
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
        textField = new JTextField();
        if (value != null)
        {
            textField.setText(value.toString());
        }
        if (isImmidiate())
        {
            if (this.immidiateListener == null)
            {
                this.immidiateListener = getPart().getFieldEventHandler();
                this.immidiateListener.setWidget(this);
             //   textField.addKeyListener(this.immidiateListener);
            }
        }
        if (isVisible())
        {
            if (focusListener == null)
            {
                focusListener = getPart().getFieldEventHandler();
                focusListener.setWidget(this);
                textField.addFocusListener(focusListener);
            }
        }
        refresh();
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
        textField.setText(getText());
        String enabled = getProperty("enabled");
        if (enabled != null)
        {
            setEnable((enabled.equalsIgnoreCase("true")?true:false));
            textField.setEditable(isEnabled());
        }
        String backgroundColor = null;
        if (isRequired())
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
        String text = null;
        if (textField != null)
        {
            //System.out.println("returning text value :"+textField.getText());
            text = textField.getText();
        }
        else if (this.value != null)
        {
            return this.value;
        }
        if (text!=null)
        {
            if (getField()!= null && this.value != null)
            {
                String text2 = null;
                // we ignore multiple values for now.. 
                BeanMapping map = Dictionary.getInstance().getMapping(this.value.getClass());
                if (map != null)
                {
                    text2 = (String)map.getField(getField()).getValue(this.value);
                }
                if (text.equals(text2))
                {
                    return this.value;
                }
            }
            else
            {
                return text;
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.ValueWidget#setValue(Object)
     * @param val
     */
    public void setValue(Object val)
    {
        boolean update = true;
        String text = null;
        if (val!=null && val.equals(getValue()))
        {
            return;
        }
        this.value = val;
        if (getField()!= null && val != null)
        {
            // we ignore multiple values for now.. 
            BeanMapping map = Dictionary.getInstance().getMapping(val.getClass());
            if (map != null)
            {
                val = map.getField(getField()).getValue(this.value);
            }
        }
        if (val != null)
        {
            text = val.toString();
            setText(text);
        }
        if (update && initialized)
        {
            refresh();
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
            setText("");
        }
        if (initialized)
        {
            refresh();
        }
    }

}
