/*
 $Id: IRule.java,v 1.9 2003-12-15 23:38:49 mvdb Exp $

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
package org.xulux.nyx.rules;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.Widget;

/**
 * All rules must implement this interfaces.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IRule.java,v 1.9 2003-12-15 23:38:49 mvdb Exp $
 */
public interface IRule {

    /**
     * Initializes the rule
     * This initialization is context wide.
     * So any processing done here, will
     * preserve state among all calls to the rule
     */
    void init();

    /**
     * Preprocessing of the rule.
     * This is mainly changes state of eg the current
     * component that is about to process
     * @param request the request object
     */
    void pre(PartRequest request);

    /**
     * The actual rule will be processed here.
     * @param request the request object
     */
    void execute(PartRequest request);

    /**
     * Post processing of the rule
     * This is mainly changing states in eg components
     * after the executer has been process.
     * @param request the request object
     */
    void post(PartRequest request);

    /**
     * Destroys the rule when removed from
     * the context
     */
    void destroy();

    /**
     * How many times is the current rule used
     * @return the usecount for this rule
     */
    int getUseCount();

    /**
     * Method registerPartName.
     * @param partName the name of the part
     */
    void registerPartName(String partName);

    /**
     * Method derigsterPartName.
     * @param partName the name of the part
     */
    void deregisterPartName(String partName);

    /**
     * Method isRegistered.
     * @param partName the name of the part
     * @return boolean if the part was registered
     */
    boolean isRegistered(String partName);

    /**
     * @return the owner of this rule
     */
    Widget getOwner();

}
