/*
 $Id: NyxCombo.java,v 1.4 2003-07-23 13:18:43 mvdb Exp $

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
package org.xulux.nyx.gui;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;

/**
 * The combo abstract. This will contain the combo generics
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxCombo.java,v 1.4 2003-07-23 13:18:43 mvdb Exp $
 */
public abstract class NyxCombo extends Widget
{
    
    protected ArrayList content;
    protected String notSelectedValue;
    protected boolean contentChanged;
    protected boolean notSelectedValueSet;
    protected static Log log = LogFactory.getLog(NyxCombo.class);

    /**
     * Constructor for NyxCombo.
     * @param name
     */
    public NyxCombo(String name)
    {
        super(name);
    }

    /**
     * This just disposes of references to combo
     * and doesn't get rid of native resource
     * allocated. You have to override this
     * and call super for this to work
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        getPart().removeWidget(this, this);
        removeAllRules();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public abstract Object getNativeWidget();

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public abstract void initialize();

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public abstract void refresh();
    
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
        if (initialized)
        {
            refresh();
        }
    }
    
    protected void initializeNotSelectedValue()
    {
        String nsv = getNotSelectedValue();
        if (nsv== null)
        {
            if (notSelectedValueSet)
            {
                this.content.remove(0);
                notSelectedValueSet = false;
                contentChanged = true;
            }
            return;
        }
        if (this.content == null ||  content.size() == 0)
        {
            if (this.content == null)
            {
                this.content = new ArrayList();
            }
            this.content.add(nsv);
        }
        else
        {
            boolean replaceFirstEntry = false;
            if (notSelectedValueSet)
            {
                Object firstValue = content.get(0);
                if (firstValue instanceof String)
                {
                    if (firstValue.equals(getNotSelectedValue()))
                    {
                        return;
                    }
                    replaceFirstEntry = true;
                }
            }
            if (replaceFirstEntry)
            {
                content.set(0,nsv);
            }
            else
            {
                ArrayList tmpContent = this.content;
                content = new ArrayList();
                content.add(nsv);
                content.addAll(tmpContent);
            }
        }
        this.notSelectedValue = nsv;
        this.notSelectedValueSet = true;
        this.contentChanged = true;
    }
    /**
     * Sets the not selected value.
     * It will always be the first one in the list..
     * Does nothing when value is null
     * @param nsv - the text representing the notselected text.
     */
    public void setNotSelectedValue(String notSelectedValue)
    {
        if (notSelectedValueSet)
        {
            // we don't want to do anything when 
            // the not selected value is the same
            if (this.notSelectedValue.equals(notSelectedValue))
            {
                return;
            }
        }
        this.notSelectedValue = notSelectedValue;
        if (initialized)
        {
            refresh();
        }
    }
    
    /**
     * @return the notSelectedValue
     */
    public String getNotSelectedValue()
    {
        return this.notSelectedValue;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue()
    {
        if (getField() == null) {
            return this.value;
        } else {
            BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
            IField field = map.getField(getField());
            return field.getValue(getPart().getBean());
        }
    }
    
    public void setValue(Object object)
    {
        setValue(object, true);
    }
    
    public void setValue(Object object, boolean refresh) {
        if (getField() == null) {
            this.previousValue = this.value;
            this.value = object;
        } else {
            BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean());
            IField field = map.getField(getField());
            Object currentValue =  field.getValue(getPart().getBean());
            if (currentValue != null && currentValue != object) {
                this.previousValue = currentValue;
                field.setValue(getPart().getBean(),object);
                this.value = object;
            }
            if (this.value == null) {
                this.value = object;
            }
        }
        if (initialized && refresh)
        {
            refresh();
        }
    }
    
    /**
     * Sets the value without updating the screen
     * Normally only called by the classes that handle
     * the combo box, like combo listeners.
     * @param object
     */
    public void setLazyValue(Object object)
    {
        setValue(object, false);
    }
    
    /**
     * @see org.xulux.nyx.gui.Widget#clear()
     */
    public void clear()
    {
        setValue(null);
        if (initialized)
        {
            refresh();
        }
    }

    /**
     * Returns the content.
     * @return ArrayList
     */
    public ArrayList getContent()
    {
        return content;
    }

    

}
