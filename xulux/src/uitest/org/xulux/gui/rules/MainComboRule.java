/*
   $Id: MainComboRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
   
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
package org.xulux.gui.rules;

import java.util.ArrayList;

import org.xulux.core.PartRequest;
import org.xulux.gui.NyxCombo;
import org.xulux.gui.swing.widgets.PersonBean;
import org.xulux.rules.Rule;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MainComboRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
 */
public class MainComboRule extends Rule {

    /**
     * Constructor for MainComboRule.
     */
    public MainComboRule() {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request) {
        setup3Persons(request);
        setup3PersonsWithNotSelectedValue(request);
        setup1Person(request);
        setup1PersonWithNotSelectedValue(request);
    }

    /**
     * Setup 3 persons
     * @param request the request
     */
    public void setup3Persons(PartRequest request) {
        NyxCombo combo = (NyxCombo) request.getPart().getWidget("3PersonList");
        ArrayList threePersonContent = createContent();
        combo.setValue(threePersonContent.get(2));
        combo.setContent(threePersonContent);
    }

    /**
     * Setup 3 perons with notselectedvalues
     * @param request the request
     */
    public void setup3PersonsWithNotSelectedValue(PartRequest request) {
        NyxCombo combo = (NyxCombo) request.getPart().getWidget("3PersonListWithNotSelectedValue");
        ArrayList list = createContent();
        combo.setValue(list.get(2));
        combo.setContent(list);
        combo.setNotSelectedValue("<nothing selected>");
    }

    /**
     * Setup 1 person
     * @param request the request
     */
    public void setup1Person(PartRequest request) {
        NyxCombo combo = (NyxCombo) request.getPart().getWidget("1PersonList");
        ArrayList list = createSinglePerson();
        combo.setContent(list);
        combo.setValue(list.get(0));
    }

    /**
     * Setup 1 person with a not selected value
     * @param request the request
     */
    public void setup1PersonWithNotSelectedValue(PartRequest request) {
        NyxCombo combo = (NyxCombo) request.getPart().getWidget("1PersonListWithNotSelectedValue");
        ArrayList list = createSinglePerson();
        combo.setContent(list);
        combo.setValue(list.get(0));
        combo.setNotSelectedValue("<nothing selected>");
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request) {
    }

    /**
     * @return the content
     */
    public ArrayList createContent() {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("John", "Doe"));
        list.add(new PersonBean("Jane", "Tarzan"));
        list.add(new PersonBean("Martin", "van den Bemt"));
        return list;
    }

    /**
     * @return the singleperson
     */
    public ArrayList createSinglePerson() {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("Martin", "van den Bemt"));
        return list;
    }

}
