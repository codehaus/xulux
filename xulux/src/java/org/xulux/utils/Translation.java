/*
   $Id: Translation.java,v 1.2 2004-01-28 15:10:40 mvdb Exp $
   
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

/**
 * The translation object is a holder of translation
 * information (url to get names from)
 * It defaults to classloader loading.
 *
 * @todo Automaticaly figure out what to do!
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Translation.java,v 1.2 2004-01-28 15:10:40 mvdb Exp $
 */
public class Translation {

    /**
     * the url
     */
    private String url;
    /**
     * the type
     */
    private String type;

    /**
     *
     */
    public Translation() {
        super();
    }

    /**
     * Convenient constructor instead of calling
     * the setters..
     *
     * @param url the url
     * @param type the type
     */
    public Translation(String url, String type) {
        this();
        setUrl(url);
        setType(type);
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param string the type
     */
    public void setType(String string) {
        type = string;
    }

    /**
     * @param string the type
     */
    public void setUrl(String string) {
        url = string;
    }

    /**
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return this.url;
    }

    /**
     * Case sensitive comparison of urls.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        if (object instanceof Translation) {
            Translation trans = (Translation) object;
            if (getUrl() != null) {
                if (getUrl().equals(trans.getUrl())) {
                    if (getType() != null) {
                        return getType().equals(trans.getType());
                    } else {
                        return trans.getType() == null;
                    }
                }
            }
        }
        return false;
    }
}
