/*
   $Id: Parameter.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Parameter.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class Parameter {

    /**
     * The type
     */
    private ParameterType type;
    /**
     * the value
     */
    private String value;

    /**
     *
     * @param type the type
     * @param value the value
     */
    public Parameter(ParameterType type, String value) {
        setType(type);
        setValue(value);
    }

    /**
     * @return the type
     */
    public ParameterType getType() {
        return type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param type the type
     */
    public void setType(ParameterType type) {
        this.type = type;
    }

    /**
     * @param string the value
     */
    public void setValue(String string) {
        value = string;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getValue() + " of type " + getType();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        if (object instanceof Parameter) {
            if (toString().equals(((Parameter) object).toString())) {
                return true;
            }
        }
        return false;
    }

}
