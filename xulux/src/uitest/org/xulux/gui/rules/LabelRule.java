/*
   $Id: LabelRule.java,v 1.4 2004-05-10 14:21:25 mvdb Exp $
   
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
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LabelRule.java,v 1.4 2004-05-10 14:21:25 mvdb Exp $
 */
public class LabelRule extends Rule {

    /**
     *
     */
    public LabelRule() {
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
        if (request.getWidget().getName().equalsIgnoreCase("imageDisable")) {
            boolean disable = !request.getWidget("image").isEnabled();
            if (disable) {
                request.getWidget().setProperty("text", "Disable");
                request.getWidget("image").setProperty("text", "Enabled");
            } else {
                request.getWidget().setProperty("text", "Enable");
                request.getWidget("image").setProperty("text", "Disabled");
            }
            request.getWidget("image").setEnable(disable);
            return;
        }
        if (request.getWidget().equals("button")) {
            processButton(request);
        } else if (request.getWidget().equals("switch")) {
            processSwitch(request);
        } else if (request.getWidget().equals("hidelabel")) {
          processHide(request);
        }
    }

    /**
     * There are 2 ways to set the text of a label :
     * via setProperty() and via setValue()
     * @param request the request object
     */
    public void processButton(PartRequest request) {
        Object value = request.getPart().getWidget("Name").getValue();
        System.out.println("value : " + value);
        Widget var = request.getPart().getWidget("Label:Variable");
        var.setValue(value);
        System.out.println("Previous : " + var.getPreviousValue());
        System.out.println("Value :" + var.getValue());
    }

    /**
     *
     * @param request the request object
     */
    public void processSwitch(PartRequest request) {
        Widget button = request.getWidget();
        Widget fixedLabel = request.getPart().getWidget("Label:Fixed");
        fixedLabel.setEnable(!fixedLabel.isEnabled());
        if (fixedLabel.isEnabled()) {
            button.setProperty("text", "Disable");
        } else {
            button.setProperty("text", "Enable");
        }
    }

  /**
   *
   * @param request the request object
   */
  public void processHide(PartRequest request) {
      Widget button = request.getWidget();
      Widget hideLabel = request.getPart().getWidget("Label:Hide");
      hideLabel.setVisible(!hideLabel.isVisible());
      if (hideLabel.isVisible()) {
          button.setProperty("text", "Hide");
      } else {
          button.setProperty("text", "UnHide");
      }
  }

}
