/*
   $Id: TreeContentRule.java,v 1.2 2004-01-28 15:40:09 mvdb Exp $
   
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
import org.xulux.gui.IContentWidget;
import org.xulux.gui.swing.widgets.TreeTest;
import org.xulux.rules.Rule;

/**
 * Sets the content from a rule..
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeContentRule.java,v 1.2 2004-01-28 15:40:09 mvdb Exp $
 */
public class TreeContentRule extends Rule {

    /**
     *
     */
    public TreeContentRule() {
        super();
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(org.xulux.nyx.context.PartRequest)
     */
    public void pre(PartRequest request) {
        System.out.println("TreeTest : " + TreeTest.getData());
        //((IContentWidget)request.getPart().getWidget("SimpleTree")).setContent(TreeTest.getData());
        ((IContentWidget) request.getPart().getWidget("DomTree")).setContent(TreeTest.getDocument(this));

    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(org.xulux.nyx.context.PartRequest)
     */
    public void post(PartRequest request) {

    }

    /**
     * @see org.xulux.nyx.rules.IRule#execute(org.xulux.nyx.context.PartRequest)
     */
    public void execute(PartRequest request) {
        System.out.println("value " + request.getValue().getClass());
        System.out.println("new value selected!!!");
    }

}
