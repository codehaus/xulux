/*
   $Id: PartRequestImpl.java,v 1.5 2004-10-11 19:14:21 mvdb Exp $
   
   Copyright 2002-2004 The Xulux Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.xulux.rules.impl;

import org.xulux.core.XuluxContext;
import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.core.SessionPart;
import org.xulux.gui.Widget;
import org.xulux.rules.IRule;

/**
 * This class should not be used directly, it is only for internal use.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartRequestImpl.java,v 1.5 2004-10-11 19:14:21 mvdb Exp $
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
        return action;
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
            ApplicationPart newPart = XuluxContext.getInstance().getPart(partName);
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
