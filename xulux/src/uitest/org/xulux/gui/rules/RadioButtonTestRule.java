/*
 $Id: RadioButtonTestRule.java,v 1.1 2003-12-18 00:17:23 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project. For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.gui.rules;

import org.xulux.context.PartRequest;
import org.xulux.gui.Widget;
import org.xulux.rules.Rule;

/**
 * This rule is used to show the real values of the widget
 * in a label
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: RadioButtonTestRule.java,v 1.1 2003-12-18 00:17:23 mvdb Exp $
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
