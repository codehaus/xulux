/*
   $Id: NyxCollectionUtils.java,v 1.2 2004-01-28 15:10:40 mvdb Exp $
   
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
package org.xulux.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Collection utilities to do conversions for nyx.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxCollectionUtils.java,v 1.2 2004-01-28 15:10:40 mvdb Exp $
 */
public class NyxCollectionUtils {

    /**
     *
     */
    protected NyxCollectionUtils() {
    }

    /**
     * Tries to figure out the seperator used
     * @todo Make it automatic, now uses comma as seperator
     * @param values - the seper
     * @return a list from seperated values or null when values is null
     */
    public static List getListFromCSV(String values) {
        return getListFromCSV(values, ",");
    }

    /**
     *
     * @param values the values
     * @param delim seperator
     * @return a list from seperated values or null when values is null
     */
    public static List getListFromCSV(String values, String delim) {
        if (values == null || values.length() == 0) {
            return null;
        }
        ArrayList list = new ArrayList();
        StringTokenizer tokenizer = new StringTokenizer(values, delim);
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }
        return list;
    }

    /**
     * Currently supports lists, collections and arrays
     *
     * @param object the object to create the list from
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
            return (List) object;
        }
        if (object instanceof Collection) {
            return new ArrayList((Collection) object);
        }
        if (object.getClass().isArray()) {
            return Arrays.asList((Object[]) object);
        }
        // we just add the value to a new list..
        ArrayList list = new ArrayList();
        list.add(object);
        return list;
    }

}
