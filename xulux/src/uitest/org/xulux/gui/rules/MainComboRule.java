/*
 $Id: MainComboRule.java,v 1.1 2003-12-18 00:17:23 mvdb Exp $

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
package org.xulux.gui.rules;

import java.util.ArrayList;

import org.xulux.context.PartRequest;
import org.xulux.gui.NyxCombo;
import org.xulux.gui.swing.widgets.PersonBean;
import org.xulux.rules.Rule;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MainComboRule.java,v 1.1 2003-12-18 00:17:23 mvdb Exp $
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