/*
 $Id: PrePostFieldListener.java,v 1.15 2003-07-14 01:39:40 mvdb Exp $

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
package org.xulux.nyx.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.widgets.CheckBox;
import org.xulux.nyx.swing.widgets.Entry;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PrePostFieldListener.java,v 1.15 2003-07-14 01:39:40 mvdb Exp $
 */
public class PrePostFieldListener extends NyxListener
implements FocusListener, ActionListener, ItemListener
{
    
    private static Log log = LogFactory.getLog(PrePostFieldListener.class);
    
    public PrePostFieldListener() {
        super();
    }
    /**
     * Constructor for PrePostFieldListener.
     */
    public PrePostFieldListener(Widget widget)
    {
        super(widget);
    }

    /**
     * now call pre.. 
     * @see java.awt.event.FocusListener#focusGained(FocusEvent)
     */
    public void focusGained(FocusEvent e)
    {
        if (isProcessing())
        {
            return;
        }
        if (e.getID() != FocusEvent.FOCUS_GAINED || e.isTemporary())
        {
            return;
        }
        started();
    }

    /**
     * now call post..
     * @see java.awt.event.FocusListener#focusLost(FocusEvent)
     */
    public void focusLost(FocusEvent e)
    {
        if (isProcessing())
        {
            return;
        }
        if (e.getID() != FocusEvent.FOCUS_LOST || e.isTemporary())
        {
            return;
        }
        System.out.println("Widget clazz : "+widget.getClass().getName());
        if (widget instanceof Entry)
        {
            System.out.println("Widget value : "+widget.getValue());
        }
        completed();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        if (isProcessing())
        {
            return;
        }
        completed();
    }
    
    /**
     * Sets the correct value when a checkbox is 
     * clicked. It will call the post after the 
     * value is adjusted.
     * 
     * TODO: optimize this using native boolean ??
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent e) {
        // make sure we don't end up in a loop by checking
        // the fact if the widget is currently refreshing or not..
        if (widget instanceof CheckBox && !widget.isRefreshing()) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                widget.setValue("true");
            }else if(e.getStateChange() == ItemEvent.DESELECTED) {
                widget.setValue("false");
            }
            if (log.isDebugEnabled()) {
                log.debug("Checkbox clicked on Widget : "+
                   widget.getName()+" value: "+
                       widget.getValue());
            }
        }
        completed();
    }
}
