/*
   $Id: DisableTabRule.java,v 1.4 2004-10-14 09:56:07 mvdb Exp $
   
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

import javax.swing.JTabbedPane;

import org.xulux.core.PartRequest;
import org.xulux.gui.Widget;
import org.xulux.rules.Rule;

/**
 * This tab disables or enables tabs based on the rule..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DisableTabRule.java,v 1.4 2004-10-14 09:56:07 mvdb Exp $
 */
public class DisableTabRule extends Rule {

    /**
     *
     */
    public DisableTabRule() {
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
      System.out.println("widget name : " + request.getWidget().getName());
      if ("DisableTab2".equalsIgnoreCase(request.getWidget().getName())) {
        Widget widget = request.getWidget("Tab:2");
        widget.setEnable(!widget.isEnabled());
        String text = "Enable tab 2";
        if (widget.isEnabled()) {
          text = "Disable tab 2";
        }
        System.out.println("widget isEnabled 1 : " + widget.isEnabled());
        System.out.println("Setting text 1 : "+ text);
        request.getWidget().setProperty("text", text);
        if (widget.isEnabled()) {
          widget.focus();
        }
        System.out.println("selected index 1 : " + ((JTabbedPane)widget.getParent().getNativeWidget()).getSelectedIndex());
      } else if ("DisableTab1".equalsIgnoreCase(request.getWidget().getName())) {
        Widget widget = request.getWidget("Tab:1");
        widget.setEnable(!widget.isEnabled());
        String text = "Enable tab 1";
        if (widget.isEnabled()) {
          text = "Disable tab 1";
        }
        System.out.println("widget isEnabled 2 : " + widget.isEnabled());
        System.out.println("Setting text 2 : "+ text);
        request.getWidget().setProperty("text", text);
        if (widget.isEnabled()) {
          widget.focus();
        }
        System.out.println("selected index 2 : " + ((JTabbedPane)widget.getParent().getNativeWidget()).getSelectedIndex());
      }
    }

}
