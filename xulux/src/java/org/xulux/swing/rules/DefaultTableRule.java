/*
   $Id: DefaultTableRule.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
   
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
package org.xulux.swing.rules;

import org.xulux.core.PartRequest;
import org.xulux.rules.Rule;
import org.xulux.swing.widgets.Table;

/**
 * This is the rule for processing tables.
 * The rule is to stop editing after someone hits the save button..
 * Else the edited data will get lost..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultTableRule.java,v 1.4 2004-03-16 15:04:16 mvdb Exp $
 */
public class DefaultTableRule extends Rule {

    /**
     *
     */
    public DefaultTableRule() {
        super();
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(org.xulux.nyx.context.PartRequest)
     */
    public void pre(PartRequest request) {

        String action = request.getWidget().getProperty("defaultaction");
        if (action != null && action.equalsIgnoreCase("save")) {
            // get the owner of this rule..
            Table table = (Table) getOwner();
            table.stopEditing();
        }
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(org.xulux.nyx.context.PartRequest)
     */
    public void post(PartRequest request) {
    }

}
