/*
 $Id: CheckBox.java,v 1.4 2003-07-16 14:34:17 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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

import javax.swing.JCheckBox;

import org.apache.commons.lang.BooleanUtils;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.swing.SwingWidget;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;

/**
 * The nyx to swing implementation of a checkbox
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CheckBox.java,v 1.4 2003-07-16 14:34:17 mvdb Exp $
 */
public class CheckBox extends SwingWidget {
    
    private JCheckBox checkBox;
    private PrePostFieldListener itemListener;
    /**
     * @param name
     */
    public CheckBox(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        if (checkBox != null)
        {
            Container container = checkBox.getParent();
            checkBox.setVisible(false);
            if (container != null)
            {
                container.remove(checkBox);
            }
            if (itemListener != null) {
                checkBox.removeItemListener(itemListener);
            }
            itemListener = null;
            checkBox = null;
        }
        removeAllRules();
        getPart().removeWidget(this,this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return checkBox;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (this.initialized)
        {
            return;
        }
        this.initialized = true;
        this.checkBox = new JCheckBox();
        this.itemListener = new PrePostFieldListener(this);
        // we always need to add a itemlistener to change
        // the value..
        checkBox.addItemListener(this.itemListener);
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        isRefreshing = true;
        initialize();
        if (getProperty("text") != null) {
            checkBox.setText(getProperty("text"));
        }
        if (getValue() instanceof Boolean) {
            checkBox.setSelected(BooleanUtils.toBoolean((Boolean)getValue()));
        }else if (getValue() instanceof String) {
            checkBox.setSelected(BooleanUtils.toBoolean((String)getValue()));
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getValue()
     */
    public Object getValue() {
        BeanMapping map = Dictionary.getInstance().getMapping(getPart().getBean().getClass());
        if (map != null)
        {
            return map.getField(getField()).getValue(getPart().getBean());
        }
        return super.getValue();
    }

}
