/*
 $Id: ParameteredBean.java,v 1.4 2003-07-24 01:20:03 mvdb Exp $

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
 * @version $Id: ParameteredBean.java,v 1.4 2003-07-24 01:20:03 mvdb Exp $
 */
public class ParameteredBean extends DictionaryBaseBean {


    private String value;
    private String anotherValue;
    private ArrayList list;
    /** Definitiation for the double parameters */
    public static String NO1 = "NO1";
    public static String NO2 = "NO2";
    public static String NO3 = "NO3";
    private String NO1Value = NO1+"Value";
    private String NO2Value = NO2+"Value";
    private String NO3Value = NO3+"Value";
    
    
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
    
    public void setParameter(Parameter parameter) {
        System.out.println("list before : "+list);
        System.out.println("parameter : "+parameter);
        if (parameter.getType().equals(new ParameterType(ParameterType.FIRST))) {
            list.set(0,parameter);
        } else if (parameter.getType().equals(new ParameterType(ParameterType.SECOND))) {
            list.set(1,parameter);
        } else if (parameter.getType().equals(new ParameterType(ParameterType.THIRD))) {
            list.set(2,parameter);
        } else {
            list.add(parameter);
        }
        System.out.println("list after : "+list);
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
    
    public String getDouble(String No) {
        if (NO1.equals(No)) {
            return NO1Value;
        }else if (NO2.equals(No)) {
            return NO2Value;
        }else if (NO3.equals(No)) {
            return NO3Value;
        } 
        return null;
    }
    
    public void setDouble(String No, String value) {
        if (NO1.equals(No)) {
            NO1Value = value;
        }else if (NO2.equals(No)) {
            NO2Value = value;
        }else if (NO3.equals(No)) {
            NO3Value = value;
        } 
    }
    
    /**
     * Stub for setting a parameter. This way we can test
     * if the setMethod element in the dictionary is actually
     * working.
     * 
     * @param parameter
     */
    public void setSomethingDifferent(Parameter parameter) {
        setParameter(parameter);
    }

}
