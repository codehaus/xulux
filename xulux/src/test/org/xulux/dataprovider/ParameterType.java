/*
   $Id: ParameterType.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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
 * A weird example. Pretty much strongly typed so to speak..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ParameterType.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class ParameterType {

    /**
     * The first
     */
    public static String FIRST = "first";
    /**
     * The second
     */
    public static String SECOND = "second";
    /**
     * The third
     */
    public static String THIRD = "third";
    /**
     * The fourth
     */
    public static String FOURTH = "fourth";

    /**
     * The fourth
     */
    public static String FIFTH = "fifth";

    /**
     * Placeholder for the type
     */
    public String type;
    /**
     * Make anothertype private, not used in the bean anywhere.
     */
    private String anotherType;

    /**
     * yuck :(
     * @param type the type of parameter
     */
    public ParameterType(String type) {
        if (type.equals(FIRST) || type.equals(SECOND) || type.equals(THIRD)) {
            setType(type);
        } else {
            throw new RuntimeException("Invalid type " + type);
        }

    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        if (object != null) {
            if (object instanceof ParameterType) {
                ParameterType p = (ParameterType) object;
                return p.getType().equals(getType());
            } else if (object instanceof String) {
                return ((String) object).equals(getType());
            }
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getType();
    }

}
