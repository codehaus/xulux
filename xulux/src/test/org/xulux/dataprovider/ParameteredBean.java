/*
   $Id: ParameteredBean.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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
package org.xulux.dataprovider;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This bean is for testing paremetered beans.
 * Nyx needs to be able to fetch data from strangely
 * constructed data structures.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ParameteredBean.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class ParameteredBean extends DictionaryBaseBean {

    /**
     * the value
     */
    private String value;
    /**
     * anothervalue
     */
    private String anotherValue;
    /**
     * a list
     */
    private ArrayList list;
    /** Definitiation for the double parameters */
    public static String NO1 = "NO1";
    /** Definitiation for the double parameters */
    public static String NO2 = "NO2";
    /** Definitiation for the double parameters */
    public static String NO3 = "NO3";
    /** Definitiation for the double parameters */
    private String NO1Value = NO1 + "Value";
    /** Definitiation for the double parameters */
    private String NO2Value = NO2 + "Value";
    /** Definitiation for the double parameters */
    private String NO3Value = NO3 + "Value";

    /**
     *
     */
    public ParameteredBean() {
        list = new ArrayList();
        list.add(new Parameter(new ParameterType(ParameterType.FIRST), "first parm"));
        list.add(new Parameter(new ParameterType(ParameterType.SECOND), "second parm"));
        list.add(new Parameter(new ParameterType(ParameterType.THIRD), "third parm"));
    }

    /**
     * @param parm the string parameter
     * @return the parameter
     */
    public Parameter getParameter(String parm) {
        for (Iterator it = list.iterator(); it.hasNext();) {
            Parameter p = (Parameter) it.next();
            if (p.getType().equals(parm)) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param parameter the parameter
     */
    public void setParameter(Parameter parameter) {
        System.out.println("list before : " + list);
        System.out.println("parameter : " + parameter);
        if (parameter.getType().equals(new ParameterType(ParameterType.FIRST))) {
            list.set(0, parameter);
        } else if (parameter.getType().equals(new ParameterType(ParameterType.SECOND))) {
            list.set(1, parameter);
        } else if (parameter.getType().equals(new ParameterType(ParameterType.THIRD))) {
            list.set(2, parameter);
        } else {
            list.add(parameter);
        }
        System.out.println("list after : " + list);
    }

    /**
     * @return anothervalue
     */
    public String getAnotherValue() {
        return anotherValue;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param string anothervalue
     */
    public void setAnotherValue(String string) {
        anotherValue = string;
    }

    /**
     * @param string the value
     */
    public void setValue(String string) {
        value = string;
    }

    /**
     * @param no the no
     * @return a string or null
     */
    public String getDouble(String no) {
        if (NO1.equals(no)) {
            return NO1Value;
        } else if (NO2.equals(no)) {
            return NO2Value;
        } else if (NO3.equals(no)) {
            return NO3Value;
        }
        return null;
    }

    /**
     * @param no the no
     * @param value the value
     */
    public void setDouble(String no, String value) {
        if (NO1.equals(no)) {
            NO1Value = value;
        } else if (NO2.equals(no)) {
            NO2Value = value;
        } else if (NO3.equals(no)) {
            NO3Value = value;
        }
    }

    /**
     * Stub for setting a parameter. This way we can test
     * if the setMethod element in the dictionary is actually
     * working.
     *
     * @param parameter the parameter
     */
    public void setSomethingDifferent(Parameter parameter) {
        setParameter(parameter);
    }

}
