/*
 $Id: Combo.java,v 1.6 2002-11-11 01:45:39 mvdb Exp $

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

import java.awt.Container;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JComboBox;

import org.xulux.nyx.swing.listeners.ImmidiateListener;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;
import org.xulux.nyx.swing.models.DefaultComboModel;

/**
 * The combo widget.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Combo.java,v 1.6 2002-11-11 01:45:39 mvdb Exp $
 */
public class Combo extends Widget
{
    
    private ArrayList content;
    private JComboBox combo;
    private String notSelectedValue;
    private KeyListener keyListener;
    private boolean contentChanged;
    private DefaultComboModel model;
    private PrePostFieldListener actionListener;

    /**
     * Constructor for Combo.
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
     * Sets the content of the combo.
     * The content should be all of the same type
     * If the content is a basetype, fields can be specified
     * to say which one is used, in any other situation, toString is used
     * to represent a field.
     */
    public void setContent(ArrayList list)
    {
        this.content = list;
        contentChanged = true;
        refresh();
    }
    
    /**
     * Sets the not selected value.
     * It will always be the first one in the list..
     * Does nothing when value is null
     * @param value - the text representing the notselected text.
     */
    public void setNotSelectedValue(String value)
    {
        if (value == null)
        {
            return;
        }
        if (this.content == null)
        {
            this.content = new ArrayList();
            this.content.add(value);
        }
        else
        {
            if (getNotSelectedValue() != null)
            {
                content.set(0,value);
            }
            else
            {
                ArrayList tmpContent = this.content;
                content = new ArrayList();
                content.add(value);
                content.addAll(tmpContent);
            }
        }
        this.notSelectedValue = value;
        this.contentChanged = true;
        refresh();
    }
    
    /**
     * @return the notSelectedValue
     */
    public String getNotSelectedValue()
    {
        return this.notSelectedValue;
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
        getPart().removeWidget(this, this);
        removeAllRules();
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
        combo = new JComboBox();
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        if (isImmidiate() && keyListener == null)
        {
            keyListener = new ImmidiateListener(this);
            combo.addKeyListener(keyListener);
        }
        else if (!isImmidiate() && keyListener != null)
        {
            combo.removeKeyListener(keyListener);
        }
        if (contentChanged)
        {
            contentChanged = false;
            this.model = new DefaultComboModel(content, null);
            combo.setModel(this.model);
            if (this.actionListener == null)
            {
                this.actionListener = new PrePostFieldListener(this);
                combo.addActionListener(this.actionListener);
            }
        }
            
    }
    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue()
    {
        return content.get(combo.getSelectedIndex());
    }

}
