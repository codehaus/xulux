/*
   $Id: FieldRequestWrapperImpl.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
   
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
package org.xulux.rules;

import org.xulux.core.PartRequest;
import org.xulux.gui.Widget;

/**
 * The implementation of the fieldRequestwrapper.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: FieldRequestWrapperImpl.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
 */
public class FieldRequestWrapperImpl implements FieldRequestWrapper {

    /**
     * the widget
     */
    private Widget widget;
    /**
     * the request
     */
    private PartRequest partRequest;
    /**
     * the type
     */
    private int type;

    /**
     * the constructor
     */
    public FieldRequestWrapperImpl() {
        super();
    }

    /**
     * @see org.xulux.nyx.rules.FieldRequestWrapper#getWidget()
     */
    public Widget getWidget() {
        return this.widget;
    }

    /**
     * Set the caller widget
     * @param widget the originating widget
     */
    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    /**
     * @see org.xulux.nyx.rules.FieldRequestWrapper#getPartRequest()
     */
    public PartRequest getPartRequest() {
        return this.partRequest;
    }

    /**
     * Set the partRequest
     * @param partRequest the request
     */
    public void setPartRequest(PartRequest partRequest) {
        this.partRequest = partRequest;
    }

    /**
     * @see org.xulux.nyx.rules.FieldRequestWrapper#getType()
     */
    public int getType() {
        return this.type;
    }

    /**
     * Set the type of request (pre, execute or post)
     * @param type the type of request
     */
    public void setType(int type) {
        this.type = type;
    }

}
