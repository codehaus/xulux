/*
   $Id: Rule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
   
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
package org.xulux.rules;

import java.util.ArrayList;
import java.util.Iterator;

import org.xulux.core.PartRequest;
import org.xulux.gui.Widget;

/**
 * A convenient abstract for the rule, which only
 * makes pre and post mandatory.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Rule.java,v 1.3 2004-03-16 15:04:14 mvdb Exp $
 */
public abstract class Rule implements IRule {

    /**
     * The partnames
     */
    private static ArrayList partNames;
    /**
     * the owner of the rule
     */
    private Widget owner;

    /**
     * Constructor for Rule.
     */
    public Rule() {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#init()
     */
    public void init() {
    }

    /**
     * Pre processing of the part.
     * @see org.xulux.nyx.rules.IRule#pre(org.xulux.nyx.context.PartRequest)
     */
    public abstract void pre(PartRequest request);

    /**
     * During processesing of the part
     * @see org.xulux.nyx.rules.IRule#execute(org.xulux.nyx.context.PartRequest)
     */
    public void execute(PartRequest request) {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(org.xulux.nyx.context.PartRequest)
     */
    public abstract void post(PartRequest request);

    /**
     * @see org.xulux.nyx.rules.IRule#destroy()
     */
    public void destroy() {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#getUseCount()
     */
    public int getUseCount() {
        if (partNames != null) {
            return partNames.size();
        }
        return 0;
    }

    /**
     * @see org.xulux.nyx.rules.IRule#registerPartName(java.lang.String)
     */
    public void registerPartName(String partName) {
        if (partNames == null) {
            partNames = new ArrayList();
            // let's initialize the object...
            this.init();
        }
        if (!partNames.contains(partName)) {
            partNames.add(partName);
        }
    }

    /**
     * @see org.xulux.nyx.rules.IRule#deregisterPartName(java.lang.String)
     */
    public void deregisterPartName(String partName) {
        if (partNames == null) {
            return;
        }
        int index = partNames.indexOf(partName);
        if (index != -1) {
            partNames.remove(index);
        }
        if (getUseCount() == 0) {
            // let's destroy the object..
            partNames = null;
            this.destroy();
        }
    }

    /**
     * @see org.xulux.nyx.rules.IRule#isRegistered(java.lang.String)
     */
    public boolean isRegistered(String partName) {
        if (partNames == null) {
            return false;
        }
        return (partNames.indexOf(partName) != -1);
    }

    /**
     * A bit of debug info.
     */
    public static void debug() {
        System.out.println("This rules is used by : ");
        if (partNames != null) {
            Iterator it = partNames.iterator();
            System.out.println(it.next());
        } else {
            System.out.println("Not used at all...");
        }
    }

    /**
     * @param owner the owner of this rule
     */
    public void setOwner(Widget owner) {
        this.owner = owner;
    }

    /**
     * @see org.xulux.nyx.rules.IRule#getOwner()
     */
    public Widget getOwner() {
        return this.owner;
    }
}
