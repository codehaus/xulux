/*
 $Id: Label.java,v 1.7 2002-11-28 14:05:24 mvdb Exp $

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
import javax.swing.JLabel;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Label.java,v 1.7 2002-11-28 14:05:24 mvdb Exp $
 */
public class Label extends Widget
{
    
    private JLabel label;
    
    public Label(String field)
    {
        super(field);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
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
    }
    /**
     * For now aligns to the right by default.
     * 
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        initialize();
        if (getValue()!=null)
        {
            label.setText(String.valueOf(getValue()));
        }
        else
        {
            label.setText("");
        }
        if (getProperties() == null)
        {
            label.setHorizontalAlignment(JLabel.RIGHT);
        }
        else
        {
            String ha = (String)getProperties().get("horizontalalignment");
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
        }
        label.repaint();
    }
}
