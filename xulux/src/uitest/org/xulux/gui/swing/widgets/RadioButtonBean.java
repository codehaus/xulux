/*
   $Id: RadioButtonBean.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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
 * An example bean used to show / test the usage of the radio button.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButtonBean.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class RadioButtonBean {

    /**
     * bar
     */
    private boolean bar;
    /**
     * foo
     */
    private boolean foo;
    /**
     * group
     */
    private boolean group;
    /**
     * male
     */
    private boolean male;
    /**
     * female
     */
    private boolean female;

    /**
     *
     */
    public RadioButtonBean() {
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
    public boolean isFemale() {
        return female;
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
    public boolean isGroup() {
        return group;
    }

    /**
     * @return boolean
     */
    public boolean isMale() {
        return male;
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
    public void setFemale(boolean b) {
        female = b;
    }

    /**
     * @param b boolean
     */
    public void setFoo(boolean b) {
        foo = b;
    }

    /**
     * @param b boolean
     */
    public void setGroup(boolean b) {
        group = b;
    }

    /**
     * @param b boolean
     */
    public void setMale(boolean b) {
        male = b;
    }

}
