/*
   $Id: SessionPart.java,v 1.1 2004-03-16 15:04:16 mvdb Exp $
   
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
package org.xulux.core;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Contains application wide part data.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SessionPart.java,v 1.1 2004-03-16 15:04:16 mvdb Exp $
 */
public class SessionPart {

    /**
     * The map containing the session values
     */

    private HashMap map;

    /**
     * Constructor for SessionPart.
     */
    public SessionPart() {
    }

    /**
     * gets a session value
     * @param key the value to get
     * @return null if not found, the stored object otherwise
     */
    public Object getValue(Object key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * Sets a session value
     * if either of the parameters are null the
     * value will not be stored
     *
     * @param key the object of the session value
     * @param value the value of the session key
     */
    public void setValue(Object key, Object value) {
        if (map == null && key != null & value != null) {
            map = new HashMap();
        }
        if (key != null && value != null) {
            map.put(key, value);
        }
    }

    /**
     * Removes the object from the session
     * and returns the removed value
     * @param key the object to remove from the session
     * @return null if the object was not in the session
     */
    public Object remove(Object key) {
        if (map != null) {
            return map.remove(key);
        }
        return null;
    }

    /**
     * Clears all values in the session
     *
     */
    public void clear() {
        if (map != null) {
            map.clear();
        }
        map = null;
    }

    /**
     * Sample code :
     * <code>
     *
     * Iterator it = part.iterator();
     * while (it.hasNext()) {
     *   Object value = part.getValue(it.next());
     * }
     *
     * @return an iterator containing keys
     */
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    /**
     *
     * @return the size of the current session
     */
    public int size() {
        if (map == null) {
            return 0;
        }
        else {
            return map.size();
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (map != null) {
            return map.toString();
        }
        else {
            return "session is empty";
        }
    }

}
