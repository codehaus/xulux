/*
 $Id: SWTCombo.java,v 1.7 2003-08-09 00:09:56 mvdb Exp $

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
package org.xulux.nyx.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.xulux.nyx.gui.NyxCombo;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.utils.ComboContent;
import org.xulux.nyx.swt.SWTWidget;
import org.xulux.nyx.swt.listeners.ImmidiateListener;
import org.xulux.nyx.swt.listeners.PrePostFieldListener;
import org.xulux.nyx.swt.util.SWTUtil;

/**
 * Represents the swt combo and cCombo.
 * 
 * @author <a href="mailo:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SWTCombo.java,v 1.7 2003-08-09 00:09:56 mvdb Exp $
 */
public class SWTCombo extends NyxCombo
implements SWTWidget
{
    
    Combo combo;
    KeyListener keyListener;
    SelectionListener selectionListener;
    Composite parent;

    /**
     * Constructor for NyxCombo.
     * @param name
     */
    public SWTCombo(String name)
    {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        return combo;
    }
    
    /**
     * @see org.xulux.nyx.gui.swt.SWTWidget#getNativeObject(Composite)
     */
    public Object getNativeObject(Composite composite)
    {
        if (!initialized)
        {
            System.out.println("initializing : "+composite.getClass().getName());
            this.parent = composite;
            initialize();
        }
        return getNativeWidget();
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
        combo = new Combo(parent, SWT.DROP_DOWN);
        combo.setBounds(SWTUtil.getRectangle(getRectangle()));
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
            combo.setItems(ComboContent.getStringArray(getContent(), comboFields, this));
            if (this.selectionListener == null)
            {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null)
                {
                    this.selectionListener = (PrePostFieldListener
                    )listener;
                }
                else
                {
                    this.selectionListener = new PrePostFieldListener(this);
                }
                combo.addSelectionListener(this.selectionListener);
            }
        }
        if (content != null && value != null)
        {
            // TODO Improve in combocontent
            combo.select(content.indexOf(getValue()));
        }
        else if (content != null && value == null)
        {
            // if we don't have a value select
            // the first one in the list
            if (!content.isEmpty())
            {
                combo.select(0);
                this.value = content.get(combo.getSelectionIndex());
            }
        }
        if (contentChanged)
        {
            contentChanged = false;
            //combo.setItems()
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
            combo.setBackground(SWTUtil.getColor(backgroundColor, combo.getDisplay()));
        }
        combo.redraw();
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        combo.setFocus();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
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
        // TODO
    }

}
