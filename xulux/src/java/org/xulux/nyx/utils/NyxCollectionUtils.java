/*
 $Id: NyxCollectionUtils.java,v 1.2 2003-08-20 13:56:33 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Collection utilities to do conversions for nyx.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxCollectionUtils.java,v 1.2 2003-08-20 13:56:33 mvdb Exp $
 */
public class NyxCollectionUtils {

    /**
     * 
     */
    public NyxCollectionUtils() {
    }
    
    /**
     * Tries to figure out the seperator used
     * TODO: Make it automatic, now uses comma as seperator
     * @param values - the seper
     * @return a list from seperated values or null when values is null
     */
    public static List getListFromCSV(String values) {
        return getListFromCSV(values, ",");
    }
    
    /**
     * 
     * @param value
     * @param delim - seperator
     * @return a list from seperated values or null when values is null
     */
    public static List getListFromCSV(String values, String delim) {
        if (values == null || values.length()==0) {
            return null;
        }
        ArrayList list = new ArrayList();
        StringTokenizer tokenizer = new StringTokenizer(values,delim);
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }    
        return list;
    }
    
    /**
     * Currently supports lists, collections and arrays
     * 
     * @param object
     * @return A list from the specified object. If the object
     *          is already a list, it will return with the list passed in.
     *          If it is not a collection, array or list, it will just
     *          create a new list and add the passed in object to that list
     *          resulting in one value in the list content.
     */
    public static List getList(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof List) {
            return (List)object;
        }
        if (object instanceof Collection) {
            return new ArrayList((Collection)object);
        }
        if (object.getClass().isArray()) {
            return Arrays.asList((Object[])object);
        }
        // we just add the value to a new list..
        ArrayList list = new ArrayList();
        list.add(object);
        return list;
    }

}
