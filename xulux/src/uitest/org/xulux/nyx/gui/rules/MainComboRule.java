/*
 $Id: MainComboRule.java,v 1.3 2002-12-06 00:27:02 mvdb Exp $

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
package org.xulux.nyx.gui.rules;

import java.util.ArrayList;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.Combo;
import org.xulux.nyx.gui.PersonBean;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: MainComboRule.java,v 1.3 2002-12-06 00:27:02 mvdb Exp $
 */
public class MainComboRule extends Rule
{

    /**
     * Constructor for MainComboRule.
     */
    public MainComboRule()
    {
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(PartRequest)
     */
    public void pre(PartRequest request)
    {
        setup3Persons(request);
        setup3PersonsWithNotSelectedValue(request);
        setup1Person(request);
        setup1PersonWithNotSelectedValue(request);
    }
    
    public void setup3Persons(PartRequest request)
    {
        Combo combo = (Combo)request.getPart().getWidget("3PersonList");
        ArrayList threePersonContent = createContent();
        combo.setValue(threePersonContent.get(2));
        combo.setContent(threePersonContent);
    }
    
    public void setup3PersonsWithNotSelectedValue(PartRequest request)
    {
        Combo combo = (Combo)request.getPart().getWidget("3PersonListWithNotSelectedValue");
        ArrayList list = createContent();
        combo.setValue(list.get(2));
        combo.setContent(list);
        combo.setNotSelectedValue("<nothing selected>");
    }
    
    public void setup1Person(PartRequest request)
    {
        Combo combo = (Combo)request.getPart().getWidget("1PersonList");
        ArrayList list = createSinglePerson();
        combo.setContent(list);
        combo.setValue(list.get(0));
    }

    public void setup1PersonWithNotSelectedValue(PartRequest request)
    {
        Combo combo = (Combo)request.getPart().getWidget("1PersonListWithNotSelectedValue");
        ArrayList list = createSinglePerson();
        combo.setContent(list);
        combo.setValue(list.get(0));
        combo.setNotSelectedValue("<nothing selected>");
    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(PartRequest)
     */
    public void post(PartRequest request)
    {
    }
    
    public ArrayList createContent()
    {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("John", "Doe"));
        list.add(new PersonBean("Jane", "Tarzan"));
        list.add(new PersonBean("Martin", "van den Bemt"));
        return list;
    }
    
    public ArrayList createSinglePerson()
    {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("Martin", "van den Bemt"));
        return list;
    }

}
