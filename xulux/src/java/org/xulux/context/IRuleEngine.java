/*
   $Id: IRuleEngine.java,v 1.3 2004-01-28 14:57:03 mvdb Exp $
   
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
package org.xulux.context;

import org.xulux.gui.Widget;

/**
 * The main rule engine interface.
 * This is the way to plugin your own custom rule engine, or eg
 * drools. The internal nyx rule engine will be an implementation
 * that is always available in the core system and can be used
 * in combination with any ruleengine. The custom rule engine
 * however will take precedence over the internal one.
 * So in case of drools, first all drools rules will be fired and
 * after that the internal rule system.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IRuleEngine.java,v 1.3 2004-01-28 14:57:03 mvdb Exp $
 */
public interface IRuleEngine {

    /**
     * Fire requests for the specified field.
     *
     * @param widget the widget the request is for
     * @param request the request object
     * @param type the type of request
     */

    void fireFieldRequest(Widget widget, PartRequest request, int type);

    /**
     * Fire request on the specfied part
     *
     * @param request the request object
     * @param type the type of request
     */
    void fireFieldRequests(PartRequest request, int type);

    /**
     * Fire a single request.
     *
     * @param request the request object
     * @param type the type of request
     */
    void fireFieldRequest(PartRequest request, int type);

    /**
     * Do not continue any rules left in the rule queue.
     * It should act as if all rules are actually processed.
     *
     */
    void stopAllRules();

}
