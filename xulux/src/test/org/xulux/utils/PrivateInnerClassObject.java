/*
   $Id: PrivateInnerClassObject.java,v 1.2 2004-01-28 15:22:02 mvdb Exp $
   
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
package org.xulux.utils;

/**
 * Test object to be able to return a parent as null when
 * testing innerclass instantiation
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PrivateInnerClassObject.java,v 1.2 2004-01-28 15:22:02 mvdb Exp $
 */
public final class PrivateInnerClassObject {

    /**
     * the constructor
     */
    private PrivateInnerClassObject() {
        super();
    }

    /**
     * Inner class
     */
    public class InnerClass {
    }

}
