/*
 $Id: NyxRuleEngine.java,v 1.1 2003-12-18 00:17:32 mvdb Exp $

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
package org.xulux.rules;

import java.util.ArrayList;
import java.util.Iterator;

import org.xulux.context.IRuleEngine;
import org.xulux.context.PartRequest;
import org.xulux.gui.Widget;

/**
 * The internal nyx rule engine. It will still stay in core, even though
 * you can actually plugin other rule engines. You are able to connect
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxRuleEngine.java,v 1.1 2003-12-18 00:17:32 mvdb Exp $
 */
public class NyxRuleEngine implements IRuleEngine {

    /**
     *
     */
    public NyxRuleEngine() {
        super();
    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#fireFieldRequest(org.xulux.nyx.gui.Widget, org.xulux.nyx.context.PartRequest, int)
     */
    public void fireFieldRequest(Widget widget, PartRequest request, int type) {
        ArrayList rules = widget.getRules();
        if (rules == null || rules.size() == 0) {
            return;
        }
        ArrayList currentRules = (ArrayList) rules.clone();
        Iterator it = currentRules.iterator();
        //fireRequests(it, request, type);
        currentRules.clear();
        currentRules = null;

    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#fireFieldRequests(org.xulux.nyx.context.PartRequest, int)
     */
    public void fireFieldRequests(PartRequest request, int type) {

    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#fireFieldRequest(org.xulux.nyx.context.PartRequest, int)
     */
    public void fireFieldRequest(PartRequest request, int type) {

    }

    /**
     * @see org.xulux.nyx.context.IRuleEngine#stopAllRules()
     */
    public void stopAllRules() {

    }

}
