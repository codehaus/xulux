/*
   $Id: WidgetRegistryTest.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
   
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
package org.xulux.core;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRegistryTest.java,v 1.1 2004-12-04 19:06:40 mvdb Exp $
 */

public class WidgetRegistryTest extends TestCase {

    /**
     * Constructor for WidgetRegistryTest.
     * @param name the name of the test
     */
    public WidgetRegistryTest(String name) {
        super(name);
    }
    
    public void testConstructor() {
        System.out.println("testConstructor");
        WidgetRegistry wr = new WidgetRegistry();
    }

}
