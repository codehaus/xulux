/*
   $Id: NyxRuleEngine.java,v 1.2 2004-01-28 15:02:20 mvdb Exp $
   
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
package org.xulux.rules;

import java.util.ArrayList;
import java.util.Iterator;

import org.xulux.context.IRuleEngine;
import org.xulux.context.PartRequest;
import org.xulux.gui.Widget;

/**
 * The internal nyx rule engine. It will still stay in core, even though
 * you can actually plugin other rule engines. You are able to connect
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxRuleEngine.java,v 1.2 2004-01-28 15:02:20 mvdb Exp $
 */
public class NyxRuleEngine implements IRuleEngine {

    /**
     *
     */
    public NyxRuleEngine() {
        super();
    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#fireFieldRequest(org.xulux.nyx.gui.Widget, org.xulux.nyx.context.PartRequest, int)
     */
    public void fireFieldRequest(Widget widget, PartRequest request, int type) {
        ArrayList rules = widget.getRules();
        if (rules == null || rules.size() == 0) {
            return;
        }
        ArrayList currentRules = (ArrayList) rules.clone();
        Iterator it = currentRules.iterator();
        //fireRequests(it, request, type);
        currentRules.clear();
        currentRules = null;

    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#fireFieldRequests(org.xulux.nyx.context.PartRequest, int)
     */
    public void fireFieldRequests(PartRequest request, int type) {

    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#fireFieldRequest(org.xulux.nyx.context.PartRequest, int)
     */
    public void fireFieldRequest(PartRequest request, int type) {

    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#stopAllRules()
     */
    public void stopAllRules() {

    }

}
