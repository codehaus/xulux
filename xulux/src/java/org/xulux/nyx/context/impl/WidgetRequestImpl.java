/*
 $Id: WidgetRequestImpl.java,v 1.6 2003-05-06 12:39:20 mvdb Exp $

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

package org.xulux.nyx.context.impl;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.context.SessionPart;
import org.xulux.nyx.gui.Widget;

/**
 * This class should not be used directly, it is only for internal use.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRequestImpl.java,v 1.6 2003-05-06 12:39:20 mvdb Exp $
 */
public class WidgetRequestImpl implements PartRequest
{
    private Widget widget;
    private int action;
    private ApplicationPart part;
    
    
    public WidgetRequestImpl(Widget widget, int action)
    {
        setWidget(widget);
        setAction(action);
    }
    
    /**
     * @see org.xulux.nyx.context.PartRequest#getApplicationPart()
     */
    public ApplicationPart getPart()
    {
        if (this.part != null)
        {
            return part;
        }
        return getWidget().getPart();
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getType()
     */
    public int getType()
    {
        return NO_ACTION;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getValue()
     */
    public Object getValue()
    {
        return widget.getValue();
    }
    
    /**
     * @see org.xulux.nyx.context.PartRequest#getValue(String)
     */
    public Object getValue(String field)
    {
        int dotIndex = field.indexOf(".");
        if (dotIndex == -1)
        {
            getPart().getGuiValue(field);
        }
        else
        {
            String partName = field.substring(0, dotIndex);
            System.out.println("partName " +partName);
            ApplicationPart newPart = ApplicationContext.getInstance().getPart(partName);
            if (newPart != null)
            {
                String fieldName = field.substring(dotIndex);
                System.out.println("new fieldName : "+fieldName);
                return newPart.getGuiValue(fieldName);
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#setValue(Object)
     */
    public void setValue(Object value)
    {
        getPart().setGuiValue(getName(), value);
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getName()
     */
    public String getName()
    {
        return widget.getName();
    }
    
    private void setWidget(Widget widget)
    {
        this.widget = widget;
    }
    
    private void setAction(int action)
    {
        this.action = action;
    }
    
    /**
     * @see org.xulux.nyx.context.PartRequest#getWidget()
     */
    public Widget getWidget()
    {
        return this.widget;
    }
    
    public void setPart(ApplicationPart part)
    {
        this.part = part;
    }


    /**
     * @see java.lang.Object#clone()
     */
    public Object clone()
    {
        return new WidgetRequestImpl(getWidget(), getType());
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getSession()
     */
    public SessionPart getSession()
    {
        return getPart().getSession();
    }

}
