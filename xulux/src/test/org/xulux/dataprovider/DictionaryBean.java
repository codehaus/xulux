/*
   $Id: DictionaryBean.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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
package org.xulux.dataprovider;

/**
 * DictionaryBean for testing the dictionary
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryBean.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class DictionaryBean extends DictionaryBaseBean {

    /**
     * the name
     */
    private String name;
    /**
     * the street
     */
    private String street;
    /**
     * the city
     */
    private String city;
    /**
     * ignore private
     */
    private String ignorePrivate;
    /**
     * ignore protected
     */
    private String ignoreProtected;
    /**
     * the subbean
     */
    private DictionarySubSubBean subBean;

    /**
     * Constructor for DictionaryBean.
     */
    public DictionaryBean() {
        super();
    }

    /**
     * Returns the city.
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the street.
     * @return String
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the city.
     * @param city The city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the street.
     * @param street The street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns the ignore.
     * @return String
     */
    private String getIgnorePrivate() {
        return ignorePrivate;
    }

    /**
     * Returns the ignoreProtected.
     * @return String
     */
    protected String getIgnoreProtected() {
        return ignoreProtected;
    }

    /**
     * Sets the ignorePrivate.
     * @param ignorePrivate The ignorePrivate to set
     */
    public void setIgnorePrivate(String ignorePrivate) {
        this.ignorePrivate = ignorePrivate;
    }

    /**
     * Sets the ignoreProtected.
     * @param ignoreProtected The ignoreProtected to set
     */
    public void setIgnoreProtected(String ignoreProtected) {
        this.ignoreProtected = ignoreProtected;
    }

    /**
     * Returns the subBean.
     * @return DictionarySubBean
     */
    public DictionarySubSubBean getSubBean() {
        return subBean;
    }

    /**
     * Sets the subBean.
     * @param subBean The subBean to set
     */
    public void setSubBean(DictionarySubSubBean subBean) {
        this.subBean = subBean;
    }

}
