/*
   $Id: DefaultPartRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
   
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

import org.xulux.core.ApplicationPart;
import org.xulux.core.PartRequest;

/**
 * The default part rule, initializes the the part , and makes it visible.
 * It is always last in line when registring the part.
 * @todo Figure out how to be always last....
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultPartRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
 */
public class DefaultPartRule extends Rule {

    /**
     * Constructor for DefaultPartRule.
     */
    public DefaultPartRule() {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request) {
        ApplicationPart part = request.getPart();
        part.initialize(this);
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request) {
    }

}
