/*
 $Id: Label.java,v 1.19 2003-09-30 16:09:35 mvdb Exp $

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

import javax.swing.JLabel;

import org.apache.commons.lang.BooleanUtils;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IConverter;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.utils.ColorUtils;
import org.xulux.nyx.swing.SwingWidget;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Label.java,v 1.19 2003-09-30 16:09:35 mvdb Exp $
 */
public class Label extends SwingWidget
{
    
    private JLabel label;
    
    public Label(String name)
    {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        processDestroy();
        if (label != null)
        {
            Container container = label.getParent();
            label.setVisible(false);
            if (container != null)
            {
                container.remove(label);
            }
            label = null;
        }
        removeAllRules();
        getPart().removeWidget(this,this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return label;
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
        this.label = new JLabel();
        refresh();
        processInit();
    }
    /**
     * For now aligns to the right by default.
     * 
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        initialize();
        initializeValue();
        if (getProperty("text")!=null)
        {
            label.setText(getProperty("text"));
        }
        else
        {
            label.setText("");
        }
        String ha = getProperty("horizontalalignment");
        // we use the swing default..
        if (ha != null) {
            if (ha.equalsIgnoreCase("left"))
            {
                label.setHorizontalAlignment(JLabel.LEFT);
            }
            else if (ha.equalsIgnoreCase("center"))
            {
                label.setHorizontalAlignment(JLabel.CENTER);
            }
            else
            {
                label.setHorizontalAlignment(JLabel.RIGHT);
            }
        } else {
            label.setHorizontalAlignment(JLabel.RIGHT);
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
        if (getProperty("enabled.depends")!= null) {
            String value = getProperty("enabled.depends");
            Object depValue = getPart().getWidget(value).getValue();
            if (depValue != null) {
                setEnable(BooleanUtils.toBoolean(depValue.toString()));
            } else {
                setEnable(false);
            }
        }
        label.repaint();
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
            BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
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
        if (getProperty("text") == null ||
             getProperty("text").equals("")) {
                 return true;
        }
        return false;
    }

}
