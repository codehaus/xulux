/*
 $Id: Panel.java,v 1.2 2003-01-25 23:17:57 mvdb Exp $

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
package org.xulux.nyx.gui.swing;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.layouts.XYLayout;
import sun.security.action.GetPropertyAction;

/**
 * A panel widget
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Panel.java,v 1.2 2003-01-25 23:17:57 mvdb Exp $
 */
public class Panel extends ContainerWidget
{
    
    private JPanel panel;

    /**
     * Constructor for Panel.
     * @param field
     */
    public Panel(String field)
    {
        super(field);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        super.destroy();
        Container container = panel.getParent();
        panel.setVisible(false);
        panel.removeAll();
        if (container != null)
        {
            container.remove(panel);
        }
        panel = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return panel;
    }

    /**
     * TODO: Make layouts flexible.
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize()
    {
        if (initialized)
        {
            return;
        }
        // we default to XYLayout for now..
        initialized = true;
        panel = new JPanel(new XYLayout());
        initializeChildren();
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        initialize();
        String border = (String)getProperties().get("border");
        if (border!=null)
        {
            if (border.equalsIgnoreCase("bevel"))
            {
                panel.setBorder(new BevelBorder(BevelBorder.RAISED));
            }
        }
    }


    /**
     * @see org.xulux.nyx.gui.Widget#canContainChildren()
     */
    public boolean canContainChildren()
    {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getChildWidgets()
     */
    public ArrayList getChildWidgets()
    {
        return widgets;
    }

    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(Widget)
     */
    public void addToParent(Widget widget)
    {
        panel.add((Component)widget.getNativeWidget(), widget);
    }

}
