/*
   $Id: DictionarySubBean.java,v 1.2 2004-04-14 14:16:10 mvdb Exp $
   
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
 * A "sub" base bean in DictioanryBean to test
 * the BeanDataProvider (mapping in specific..)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionarySubBean.java,v 1.2 2004-04-14 14:16:10 mvdb Exp $
 */
public class DictionarySubBean extends DictionaryBaseBean {

    /**
     * the phone
     */
    private String phone;
    /**
     * the fax
     */
    private String fax;
    /**
     * the mobile
     */
    private String mobile;
    /**
     * nice ?
     */
    private boolean nice;

    /**
     * Constructor for DictionarySubBean.
     */
    public DictionarySubBean() {
        super();
    }

    /**
     * Returns the fax.
     * @return String
     */
    public String getFax() {
        return fax;
    }

    /**
     * Returns the mobile.
     * @return String
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Returns the phone.
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the fax.
     * @param fax The fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Sets the mobile.
     * @param mobile The mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Sets the phone.
     * @param phone The phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return nice or not ?
     */
    public boolean isNice() {
        return nice;
    }

    /**
     * @param b true or false
     */
    public void setNice(boolean b) {
        nice = b;
    }

}
