/*
   $Id: RadioButtonTestRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
   
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
import org.xulux.gui.Widget;
import org.xulux.rules.Rule;

/**
 * This rule is used to show the real values of the widget
 * in a label
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButtonTestRule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
 */
public class RadioButtonTestRule extends Rule {

    /**
     *
     */
    public RadioButtonTestRule() {
        super();
    }

    /**
     * First the rule gets called with the the caller being itself.
     * @todo Figure out why the caller is first group1 and then group2, instead
     * of just group2 or group1..
     *
     * @see org.xulux.nyx.rules.IRule#pre(org.xulux.nyx.context.PartRequest)
     */
    public void pre(PartRequest request) {
        System.out.println("caller : " + request.getWidget().getName());
        if (request.getWidget().getName().equals("radio:foo") || request.getWidget().getName().equals("label:foo:value")) {
            Widget w = request.getWidget("radio:foo");
            request.getWidget("label:foo:value").setProperty("text", "getValue() : " + w.getValue());
        } else if (request.getWidget().getName().equals("radio:bar") || request.getWidget().getName().equals("label:bar:value")) {
            Widget w = request.getWidget("radio:bar");
            request.getWidget("label:bar:value").setProperty("text", "getValue() : " + w.getValue());
        } else if (
            request.getWidget().getName().equals("male")
                || request.getWidget().getName().equals("female")
                || request.getWidget().getName().equals("label:group:value")) {
            System.out.println("Setting value...");
            Widget w = request.getWidget("buttongroup");
            Widget male = request.getWidget("male");
            Widget female = request.getWidget("female");
            String text = "Male : " + male.getValue() + "      Female : " + female.getValue();
            request.getWidget("label:group:value").setProperty("text", text);
        }
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(org.xulux.nyx.context.PartRequest)
     */
    public void post(PartRequest request) {
    }

}
