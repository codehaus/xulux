/*
   $Id: AnotherRecursiveBean.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
   
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
 * Used to test infiniteloop situations.
 *
 * @author <a href="mailto:martin@mvdb.net>Martin van den Bemt</a>
 * @version $Id: AnotherRecursiveBean.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
 */
public class AnotherRecursiveBean extends RecursiveBean {

    /**
     * Another
     */
    private RecursiveBean another;
    /**
     * AnotherRecursive
     */
    private RecursiveBean anotherRecursive;
    /**
     * Another recursive bean
     */
    private RecursiveBean anotherRecursiveBean;
    /**
     * The value
     */
    private String value;
    /**
     * Constructor for AnotherRecursiveBean.
     */
    public AnotherRecursiveBean() {
        super();
    }

    /**
     * Constructor for AnotherRecursiveBean.
     * @param value the value
     */
    public AnotherRecursiveBean(String value) {
        this.value = value;
    }

    /**
     * @return null
     */
    public RecursiveBean getAnother() {
        return another;
    }

    /**
     * @return null
     */
    public RecursiveBean getAnotherRecursive() {
        return anotherRecursive;
    }

    /**
     * @return null
     */
    public RecursiveBean getAnotherRecursiveBean() {
        return anotherRecursiveBean;
    }

    /**
     * @param bean the bean to set
     */
    public void setAnother(RecursiveBean bean) {
        another = bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setAnotherRecursive(RecursiveBean bean) {
        anotherRecursive = bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setAnotherRecursiveBean(RecursiveBean bean) {
        anotherRecursiveBean = bean;
    }

    /**
     * @param value the value
     */    
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }
}
