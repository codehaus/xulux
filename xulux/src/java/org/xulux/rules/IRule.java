/*
   $Id: IRule.java,v 1.2 2004-01-28 15:02:20 mvdb Exp $
   
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

import org.xulux.context.PartRequest;
import org.xulux.gui.Widget;

/**
 * All rules must implement this interfaces.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IRule.java,v 1.2 2004-01-28 15:02:20 mvdb Exp $
 */
public interface IRule {

    /**
     * Initializes the rule
     * This initialization is context wide.
     * So any processing done here, will
     * preserve state among all calls to the rule
     */
    void init();

    /**
     * Preprocessing of the rule.
     * This is mainly changes state of eg the current
     * component that is about to process
     * @param request the request object
     */
    void pre(PartRequest request);

    /**
     * The actual rule will be processed here.
     * @param request the request object
     */
    void execute(PartRequest request);

    /**
     * Post processing of the rule
     * This is mainly changing states in eg components
     * after the executer has been process.
     * @param request the request object
     */
    void post(PartRequest request);

    /**
     * Destroys the rule when removed from
     * the context
     */
    void destroy();

    /**
     * How many times is the current rule used
     * @return the usecount for this rule
     */
    int getUseCount();

    /**
     * Method registerPartName.
     * @param partName the name of the part
     */
    void registerPartName(String partName);

    /**
     * Method derigsterPartName.
     * @param partName the name of the part
     */
    void deregisterPartName(String partName);

    /**
     * Method isRegistered.
     * @param partName the name of the part
     * @return boolean if the part was registered
     */
    boolean isRegistered(String partName);

    /**
     * @return the owner of this rule
     */
    Widget getOwner();

}
