/*
   $Id: PersonEntryRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
   
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

import org.xulux.core.PartRequest;
import org.xulux.gui.swing.widgets.PersonBean;
import org.xulux.rules.Rule;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PersonEntryRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
 */
public class PersonEntryRule extends Rule {

    /**
     * Constructor for PersonEntryRule.
     */
    public PersonEntryRule() {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request) {
        Object personBeanValue = request.getPart().getWidget("PersonBeanEntry").getValue();
        System.out.println("personBeanValue : " + personBeanValue);
        if (!(personBeanValue instanceof PersonBean)) {
            System.out.println("Invalid object returned");
        }
        Object stringValue = request.getPart().getWidget("String").getValue();
        System.out.println("stringValue : " + stringValue);
        if (!(stringValue instanceof String)) {
            System.out.println("invalid object returned");
        }
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request) {
        if (request.getWidget().getName().equals("PersonBeanEntry")) {
            request.getPart().getWidget("String").setValue("HiHi");
        }

    }

}
