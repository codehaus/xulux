/*
$Id: RefreshAllWidgetsRule.java,v 1.1 2004-10-28 20:15:45 mvdb Exp $

Copyright 2002-2004 mvdb.org

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

import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.xulux.core.PartRequest;
import org.xulux.gui.Widget;
import org.xulux.rules.Rule;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RefreshAllWidgetsRule.java,v 1.1 2004-10-28 20:15:45 mvdb Exp $
 */
public class RefreshAllWidgetsRule extends Rule {

    /**
     * @see org.xulux.rules.IRule#pre(org.xulux.core.PartRequest)
     */
    public void pre(PartRequest request) {
    }

    /**
     * @see org.xulux.rules.IRule#post(org.xulux.core.PartRequest)
     */
    public void post(PartRequest request) {
//        request.getPart().refreshAllWidgets();
        Widget widget = request.getWidget("TabPanel");
        widget.refresh();
        request.getWidget("Tab:1").refresh();
//        JTabbedPane pane = (JTabbedPane) widget.getNativeWidget();
//        System.out.println("Selected index : " + pane.getSelectedIndex());
//        pane.setSelectedIndex(0);
//        pane.setSelectedIndex(1);
//        List children =  Arrays.asList(((JComponent)pane.getComponent(pane.getSelectedIndex())).getComponents());
//        List list = Arrays.asList(pane.getComponents());
//        System.out.println("is textarea visible ? " + ((JComponent)children.get(0)).isShowing());
//        System.out.println("Tabs : " + list);
//        System.out.println("Children of selected Index \n"+children);
//        pane.setVisible(false);
//        pane.setVisible(true);
//        pane.validate();
//        pane.repaint();
    }
}
