/*
   $Id: CellPartRequest.java,v 1.1 2004-07-14 15:05:31 mvdb Exp $
   
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
package org.xulux.swing.models;

import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;
import org.xulux.core.SessionPart;
import org.xulux.gui.Widget;

/**
 * The cellpartrequest. Used for calling rules in models and renderers.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CellPartRequest.java,v 1.1 2004-07-14 15:05:31 mvdb Exp $
 */
public class CellPartRequest implements PartRequest {

    /**
     * the widget
     */
    private Widget widget;
    /**
     * the value
     */
    private Object value;
  
    /**
     * @param widget the widget
     */
    public CellPartRequest(Widget widget) {
        this.widget = widget;
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getName()
     */
    public String getName() {
        return widget.getName();
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getPart()
     */
    public ApplicationPart getPart() {
        return widget.getPart();
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getSession()
     */
    public SessionPart getSession() {
        return getPart().getSession();
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getType()
     */
    public int getType() {
        return PartRequest.NO_ACTION;
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getValue()
     */
    public Object getValue() {
        return value;
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getValue(java.lang.String)
     */
    public Object getValue(String field) {
        return getPart().getWidget(field).getValue();
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getWidget()
     */
    public Widget getWidget() {
        return widget;
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#getWidget(java.lang.String)
     */
    public Widget getWidget(String name) {
        return getPart().getWidget(name);
    }
  
    /**
     * @see org.xulux.nyx.context.PartRequest#setValue(java.lang.Object)
     */
    public void setValue(Object value) {
        this.value = value;
    }
  
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return null;
    }

}
