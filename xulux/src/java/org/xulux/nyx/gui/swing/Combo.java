/*
 $Id: Combo.java,v 1.3 2003-05-21 11:22:52 mvdb Exp $

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
package org.xulux.nyx.gui.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyListener;

import org.xulux.nyx.listeners.NyxListener;
import org.xulux.nyx.listeners.swing.ImmidiateListener;
import org.xulux.nyx.listeners.swing.PrePostFieldListener;
import org.xulux.nyx.swing.NyxComboBox;
import org.xulux.nyx.swing.models.DefaultComboModel;

/**
 * The swing combo widget.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Combo.java,v 1.3 2003-05-21 11:22:52 mvdb Exp $
 */
public class Combo extends org.xulux.nyx.gui.NyxCombo
{
    private NyxComboBox combo;
    private KeyListener keyListener;
    private DefaultComboModel model;
    private PrePostFieldListener actionListener;

    /**
     * Constructor for NyxCombo.
     */
    public Combo(String field)
    {
        super(field);
    }
    
    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return combo;
    }
    
    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        if (combo != null)
        {
            if (actionListener != null)
            {
                combo.removeActionListener(actionListener);
                actionListener = null;
            }
            if (keyListener != null)
            {
                combo.removeKeyListener(keyListener);
                keyListener = null;
            }
            combo.removeAll();
            Container container = combo.getParent();
            if (container != null)
            {
                container.remove(combo);
            }
            combo = null;
        }
        super.destroy();
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
        String nsv = getProperty("notselectedvalue");
        if (nsv != null)
        {
            this.notSelectedValue = nsv;
        }
        combo = new NyxComboBox();
        if (!isRefreshing())
        {
            refresh();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        if (isImmidiate() && keyListener == null)
        {
            keyListener = new ImmidiateListener(this);
            combo.addKeyListener(keyListener);
        }
        else if (!isImmidiate() && keyListener != null)
        {
            combo.removeKeyListener(keyListener);
        }
        combo.setEnabled(isEnabled());
        combo.setVisible(isVisible());
        if (contentChanged)
        {
            initializeNotSelectedValue();
            String comboFields = getProperty("combofields");
            if (this.model != null)
            {
                this.model.destroy();
            }
            if (content != null)
            {
                this.model = new DefaultComboModel(content, comboFields,this);
            }
            else
            {
                this.model = new DefaultComboModel();
            }
            combo.setModel(this.model);
            if (this.actionListener == null)
            {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null)
                {
                    this.actionListener = (PrePostFieldListener)listener;
                }
                else
                {
                    this.actionListener = new PrePostFieldListener(this);
                }
                combo.addActionListener(this.actionListener);
            }
        }
        if (getValue() instanceof DefaultComboModel.ComboShowable)
        {
            
            model.setSelectedItem(value);
        }
        else
        {
            if (content != null && value != null)
            {
                model.setRealSelectedValue(getValue());
            }
            else if (model!=null && content != null &&
                       value == null)
            {
                // if we don't have a value select
                // the first one in the list
                if (!content.isEmpty())
                {
                    model.setSelectedItem(0);
                    this.value = model.getRealSelectedValue();
                }
            }
            if (model != null && model.getSelectedIndex() == 0
                && contentChanged)
            {
                this.value = model.getRealSelectedValue();
            }
        }
        if (contentChanged)
        {
            contentChanged = false;
        }
        String backgroundColor = null;
        if (isRequired() && isEnabled())
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
            combo.setBackground(new Color(Integer.parseInt(backgroundColor,16)));
        }
        combo.repaint();
        isRefreshing = false;
    }
}
