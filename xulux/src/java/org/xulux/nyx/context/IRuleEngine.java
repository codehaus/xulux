/*
 $Id: IRuleEngine.java,v 1.4 2003-11-06 19:53:10 mvdb Exp $

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
package org.xulux.nyx.context;

import org.xulux.nyx.gui.Widget;

/**
 * The main rule engine interface.
 * This is the way to plugin your own custom rule engine, or eg
 * drools. The internal nyx rule engine will be an implementation
 * that is always available in the core system and can be used
 * in combination with any ruleengine. The custom rule engine
 * however will take precedence over the internal one.
 * So in case of drools, first all drools rules will be fired and
 * after that the internal rule system.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IRuleEngine.java,v 1.4 2003-11-06 19:53:10 mvdb Exp $
 */
public interface IRuleEngine {

    /**
     * Fire requests for the specified field.
     * @param widget
     * @param request
     * @param type
     */

    public void fireFieldRequest(Widget widget, PartRequest request, int type);

    /**
     * Fire request on the specfied part
     * @param request
     * @param type
     */
    public void fireFieldRequests(PartRequest request, int type);

    /**
     * Fire a single request.
     *
     * @param request
     * @param type
     */
    public void fireFieldRequest(PartRequest request, int type);

    /**
     * Do not continue any rules left in the rule queue.
     * It should act as if all rules are actually processed.
     *
     */
    public void stopAllRules();

}
