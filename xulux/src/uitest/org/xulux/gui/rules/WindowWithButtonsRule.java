package org.xulux.gui.rules;

import org.xulux.context.PartRequest;
import org.xulux.rules.Rule;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WindowWithButtonsRule.java,v 1.1 2003-12-29 14:26:42 mvdb Exp $
 */
public class WindowWithButtonsRule extends Rule {

    /**
     * 
     */
    public WindowWithButtonsRule() {
        super();
    }

    /**
     * @see org.xulux.rules.IRule#pre(org.xulux.context.PartRequest)
     */
    public void pre(PartRequest request) {

    }

    /**
     * @see org.xulux.rules.IRule#post(org.xulux.context.PartRequest)
     */
    public void post(PartRequest request) {
        System.err.println("Executing post rule when action is save");

    }

}
