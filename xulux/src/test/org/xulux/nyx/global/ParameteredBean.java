/*
 $Id: ParameteredBean.java,v 1.2 2003-07-16 15:40:38 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.global;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This bean is for testing paremetered beans.
 * Nyx needs to be able to fetch data from strangely
 * constructed data structures.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ParameteredBean.java,v 1.2 2003-07-16 15:40:38 mvdb Exp $
 */
public class ParameteredBean extends DictionaryBaseBean {


    private String value;
    private String anotherValue;
    private ArrayList list;
    
    /**
     * 
     */
    public ParameteredBean() {
        list = new ArrayList();
        list.add(new Parameter(new ParameterType(ParameterType.FIRST), "first parm"));
        list.add(new Parameter(new ParameterType(ParameterType.SECOND), "second parm"));
        list.add(new Parameter(new ParameterType(ParameterType.THIRD), "third parm"));
    }
    
    
    
    public Parameter getParameter(String parm) {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Parameter p = (Parameter)it.next();
            if (p.getType().equals(parm)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * @return
     */
    public String getAnotherValue() {
        return anotherValue;
    }

    /**
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * @param string
     */
    public void setAnotherValue(String string) {
        anotherValue = string;
    }

    /**
     * @param string
     */
    public void setValue(String string) {
        value = string;
    }

}
