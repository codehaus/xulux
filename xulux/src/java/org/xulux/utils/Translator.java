/*
   $Id: Translator.java,v 1.2 2004-01-28 15:10:40 mvdb Exp $
   
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

import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Retrievs the specified internationalized text
 * @todo add translation fix method, so translations not
 *       yet present will get added to the property file
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Translator.java,v 1.2 2004-01-28 15:10:40 mvdb Exp $
 */
public class Translator {

    /**
     * the log instance
     */
    private static Log log = LogFactory.getLog(Translator.class);
    /**
     * the translator instance
     */
    private static Translator instance;

    /**
     *
     */
    protected Translator() {
    }

    /**
     * @return the instance of the translator
     */
    protected static Translator getInstance() {
        if (instance == null) {
            instance = new Translator();
        }
        return instance;
    }

    /**
     * For now only support for full i18n (so the first
     * entry in the key must be the percent sign
     * to get translated.
     * @todo  Make it more flexible
     * @todo Make it read comma delimeted entries..
     * @param list the list of urls
     * @param key the key to translate
     * @return the key or the found value if
     */
    public static String translate(List list, String key) {
        int listCount = 0;
        boolean problem = false;
        if (list != null && key != null && key.startsWith("%")) {
            if (key.startsWith("%%")) {
                // return the key with one less percent sign
                return key.substring(1);
            }
            for (Iterator it = list.iterator(); it.hasNext();) {
                try {
                    Translation translation = (Translation) it.next();
                    ResourceBundle bundle = ResourceBundle.getBundle(translation.getUrl());
                    return bundle.getString(key.substring(1));
                } catch (MissingResourceException mre) {
                    problem = true;
                }
            }
        }
        if (log.isWarnEnabled() && problem) {
            log.warn("Resource " + key + " not found in " + list);
        }
        return key;
    }

}
