/*
   $Id: CheckBoxTestRule.java,v 1.4 2004-05-24 18:12:34 mvdb Exp $
   
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
import org.xulux.gui.swing.widgets.CheckBoxBean;
import org.xulux.rules.Rule;

/**
 * A rule to test setting of checkboxes and stuff..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CheckBoxTestRule.java,v 1.4 2004-05-24 18:12:34 mvdb Exp $
 */
public class CheckBoxTestRule extends Rule {

    /**
     *
     */
    public CheckBoxTestRule() {
        super();
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(org.xulux.nyx.context.PartRequest)
     */
    public void pre(PartRequest request) {

    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(org.xulux.nyx.context.PartRequest)
     */
    public void post(PartRequest request) {
        if (request.getWidget().getName().equals("foo")) {
            Widget widget = request.getWidget("strfoo");
            request.getWidget("strfoo").setValue(request.getValue());
            request.getWidget("bar").setValue(request.getValue());
            System.out.println("Foo " + ((CheckBoxBean) request.getPart().getBean()).isFoo());
        }
        if (request.getWidget().getName().equals("strFoo")) {
            System.out.println("value strFoo: " + request.getValue());
        }
        if (request.getWidget().getName().equals("disableButton")) {
            Widget widget = request.getWidget("disabled");
            boolean enable = !widget.isEnabled();
            if (enable) {
                request.getWidget().setProperty("text", "Disable");
            } else {
                request.getWidget().setProperty("text", "Enable");
            }
            widget.setEnable(enable);
        }
        if (request.getWidget().getName().equals("hiddenButton")) {
            Widget widget = request.getWidget("hidden");
            boolean visible = !widget.isVisible();
            if (visible) {
                request.getWidget().setProperty("text", "Hide");
            } else {
                request.getWidget().setProperty("text", "UnHide");
            }
            widget.setVisible(visible);
        }
    }

}
