/*
   $Id: CheckBoxBean.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

/**
 * A checkbox bean..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CheckBoxBean.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class CheckBoxBean {

    /**
     * foo
     */
    private boolean foo;
    /**
     * bar
     */
    private boolean bar;
    /**
     * strfoo
     */
    private String strFoo;
    /**
     * strbar
     */
    private String strBar;

    /**
     *
     */
    public CheckBoxBean() {
        super();
    }

    /**
     * @return boolean
     */
    public boolean isBar() {
        return bar;
    }

    /**
     * @return boolean
     */
    public boolean isFoo() {
        return foo;
    }

    /**
     * @return boolean
     */
    public boolean getBar() {
        return bar;
    }

    /**
     * @return boolean
     */
    public boolean getFoo() {
        return foo;
    }

    /**
     * @param b boolean
     */
    public void setBar(boolean b) {
        bar = b;
    }

    /**
     * @param b boolean
     */
    public void setFoo(boolean b) {
        foo = b;
    }

    /**
     * @return string
     */
    public String getStrBar() {
        return strBar;
    }

    /**
     * @return string
     */
    public String getStrFoo() {
        return strFoo;
    }

    /**
     * @param string string
     */
    public void setStrBar(String string) {
        strBar = string;
    }

    /**
     * @param string string
     */
    public void setStrFoo(String string) {
        strFoo = string;
    }

}
