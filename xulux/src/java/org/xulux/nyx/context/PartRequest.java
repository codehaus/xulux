/*
 $Id: PartRequest.java,v 1.11 2003-11-06 19:09:33 mvdb Exp $

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
package org.xulux.nyx.context;

import org.xulux.nyx.gui.Widget;

/**
 * The partRequest contains the direct connection to the current processed 
 * field or action (action not in the Swing sence)
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartRequest.java,v 1.11 2003-11-06 19:09:33 mvdb Exp $
 */
public interface PartRequest extends Cloneable {
    /**
     * No action, just felt the need to call you
     */
    public int NO_ACTION = 0;
    /**
     * The user pressed ok
     */
    public int ACTION_OK_REQUEST = 1;
    /**
     * The user pressed cancel
     */
    public int ACTION_CANCEL_REQUEST = 2;
    /**
     * Field changed/
     */
    public int ACTION_VALUE_CHANGED = 3;

    /**
     * Returns the current value of the part
     */
    public Object getValue();

    /**
     * THIS RETURNS THE GUI VALUE!!!
     * Returns the value of the specified field
     * Fields in format partname.fieldname
     * will return null when the part or field is null
     */
    public Object getValue(String field);

    /**
     * Sets the current value to a new value
     */
    public void setValue(Object value);
    /**
     * Returns the applicationPart
     */
    public ApplicationPart getPart();

    /**
     * Returns the requestType
     */
    public int getType();

    /**
     * Returns the name of the field
     */
    public String getName();

    /**
     * Returns the widget
     */
    public Widget getWidget();

    /**
     * 
     * @param name - the name of the widget to fetch.
     * @return the widget specified in the current part
     */
    public Widget getWidget(String name);

    public Object clone();

    /**
     * Returns the session of the originating part
     * (the part which you can acquire by using getPart()
     * @return the session.
     */
    public SessionPart getSession();
}
