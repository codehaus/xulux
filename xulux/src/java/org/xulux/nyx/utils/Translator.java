/*
 $Id: Translator.java,v 1.7 2003-11-25 16:25:10 mvdb Exp $

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
package org.xulux.nyx.utils;

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
 * @version $Id: Translator.java,v 1.7 2003-11-25 16:25:10 mvdb Exp $
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
