/*
 $Id: SessionPart.java,v 1.1 2003-12-18 00:17:28 mvdb Exp $

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
package org.xulux.context;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Contains application wide part data.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SessionPart.java,v 1.1 2003-12-18 00:17:28 mvdb Exp $
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