/*
 $Id: Rule.java,v 1.3 2002-11-03 13:31:02 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
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

import java.util.ArrayList;
import java.util.Iterator;

import org.xulux.nyx.context.PartRequest;

/**
 * A convenient abstract for the rule, which only
 * makes executer mandatory.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Rule.java,v 1.3 2002-11-03 13:31:02 mvdb Exp $
 */
public abstract class Rule implements IRule
{
    
    private static ArrayList partNames;

    /**
     * Constructor for Rule.
     */
    public Rule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#init()
     */
    public void init()
    {
    }

    /**
     * Pre processing of the part.
     * @see org.xulux.nyx.rules.IRule#pre()
     */
    public abstract void pre(PartRequest request);

    /**
     * During processesing of the part 
     * @see org.xulux.nyx.rules.IRule#execute()
     */
    public void execute(PartRequest request)
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post()
     */
    public abstract void post(PartRequest part);

    /**
     * @see org.xulux.nyx.rules.IRule#destroy()
     */
    public void destroy()
    {
    }
    
    public int getUseCount()
    {
        if (partNames != null)
        {
            return partNames.size();
        }
        return 0;
    }
    
    public void registerPartName(String partName)
    {
        if (partNames == null)
        {
            partNames = new ArrayList();
            // let's initialize the object...
            this.init();
        }
        if (!partNames.contains(partName))
        {
            partNames.add(partName);
        }
    }
    
    public void deregisterPartName(String partName)
    {
        if (partNames == null)
        {
            return;
        }
        int index = partNames.indexOf(partName);
        if (index != -1)
        {
            partNames.remove(index);
        }
        if (getUseCount() == 0)
        {
            // let's destroy the object..
            partNames = null;
            this.destroy();
        }
    }
    
    public boolean isRegistered(String partName)
    {
        return (partNames.indexOf(partName)!=-1);
    }
    
    /** 
     * A bit of debug info.
     */
    public static void debug()
    {
        System.out.println("This rules is used by : ");
        if (partNames != null)
        {
            Iterator it = partNames.iterator();
            System.out.println(it.next());
        }
        else
        {
            System.out.println("Not used at all...");
        }
    }
}
