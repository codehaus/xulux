/*
   $Id: PartRequest.java,v 1.2 2004-01-28 12:22:45 mvdb Exp $
   
   Copyright 2002-2003 The Xulux Project

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
package org.xulux.context;

import org.xulux.gui.Widget;

/**
 * The partRequest contains the direct connection to the current processed
 * field or action (action not in the Swing sence)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartRequest.java,v 1.2 2004-01-28 12:22:45 mvdb Exp $
 */
public interface PartRequest extends Cloneable {
    /**
     * No action, just felt the need to call you
     */
    int NO_ACTION = 0;
    /**
     * The user pressed ok
     */
    int ACTION_OK_REQUEST = 1;
    /**
     * The user pressed cancel
     */
    int ACTION_CANCEL_REQUEST = 2;
    /**
     * Field changed
     */
    int ACTION_VALUE_CHANGED = 3;

    /**
     * Returns the current value of the part
     *
     * @return the value of the requesting widget
     */
    Object getValue();

    /**
     * THIS RETURNS THE GUI VALUE!!!
     * Returns the value of the specified field
     * Fields in format partname.fieldname
     * will return null when the part or field is null
     *
     * @param field the name of the field
     * @return the value of the field requested
     */
    Object getValue(String field);

    /**
     * Sets the current value to a new value
     *
     * @param value set the value of the requesting widget
     */
    void setValue(Object value);

    /**
     * @return the applicationPart of the requesting widget
     */
    ApplicationPart getPart();

    /**
     * @return the requestType of the requesting widget
     */
    int getType();

    /**
     * @return the name of the requesting widget
     */
    String getName();

    /**
     * @return the requesting widget
     */
    Widget getWidget();

    /**
     *
     * @param name - the name of the widget to fetch.
     * @return the widget specified in the current part
     */
    Widget getWidget(String name);

    /**
     * @return the clone of the request object
     */
    Object clone();

    /**
     * Returns the session of the originating part
     * (the part which you can acquire by using getPart()
     * @return the session of the part
     */
    SessionPart getSession();
}
