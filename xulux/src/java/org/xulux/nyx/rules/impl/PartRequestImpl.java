/*
 $Id: PartRequestImpl.java,v 1.6 2003-12-15 23:38:49 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.rules.impl;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.context.SessionPart;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.rules.IRule;

/**
 * This class should not be used directly, it is only for internal use.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartRequestImpl.java,v 1.6 2003-12-15 23:38:49 mvdb Exp $
 */
public class PartRequestImpl implements PartRequest {
    /**
     * the part
     */
    private ApplicationPart part;
    /**
     * the action
     */
    private int action;
    /**
     * the caller
     */
    private IRule caller;

    /**
     * @param part the part
     * @param action the action
     */
    public PartRequestImpl(ApplicationPart part, int action) {
        setPart(part);
        setAction(action);
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getPart()
     */
    public ApplicationPart getPart() {
        return this.part;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getType()
     */
    public int getType() {
        return NO_ACTION;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getValue()
     * @return always returns null, since a part doesn't
     *          have a value.
     */
    public Object getValue() {
        return null;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getValue(String)
     */
    public Object getValue(String field) {
        int dotIndex = field.indexOf(".");
        if (dotIndex == -1) {
            return getPart().getGuiValue(field);
        } else {
            String partName = field.substring(0, dotIndex);
            ApplicationPart newPart = ApplicationContext.getInstance().getPart(partName);
            if (newPart != null) {
                String fieldName = field.substring(dotIndex + 1);
                return newPart.getGuiValue(fieldName);
            }
        }
        return null;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#setValue(Object)
     */
    public void setValue(Object value) {
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getName()
     */
    public String getName() {
        return part.getName();
    }

    /**
     * @param part the part
     */
    private void setPart(ApplicationPart part) {
        this.part = part;
    }

    /**
     * @param action the action
     */
    private void setAction(int action) {
        this.action = action;
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getWidget()
     * @return always null, since this a partrequest.
     */
    public Widget getWidget() {
        return null;
    }
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return new PartRequestImpl(getPart(), getType());
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getSession()
     */
    public SessionPart getSession() {
        return getPart().getSession();
    }

    /**
     * @see org.xulux.nyx.context.PartRequest#getWidget(java.lang.String)
     */
    public Widget getWidget(String name) {
        return getPart().getWidget(name);
    }

}
