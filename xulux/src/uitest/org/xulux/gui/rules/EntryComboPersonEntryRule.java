/*
   $Id: EntryComboPersonEntryRule.java,v 1.2 2004-01-28 15:40:09 mvdb Exp $
   
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

import org.xulux.context.PartRequest;
import org.xulux.gui.swing.widgets.PersonBean;
import org.xulux.rules.Rule;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: EntryComboPersonEntryRule.java,v 1.2 2004-01-28 15:40:09 mvdb Exp $
 */
public class EntryComboPersonEntryRule extends Rule {

    /**
     * Constructor for EntryComboPersonEntryRule.
     */
    public EntryComboPersonEntryRule() {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request) {
        PersonBean bean = (PersonBean) request.getPart().getGuiValue("PersonList");
        System.err.println("Setting value to " + bean);
        request.getPart().setGuiValue("PersonEntry", bean);

    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request) {
    }

}
