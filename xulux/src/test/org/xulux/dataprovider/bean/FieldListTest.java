/*
   $Id: FieldListTest.java,v 1.1 2004-04-14 14:16:11 mvdb Exp $
   
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
package org.xulux.dataprovider.bean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: FieldListTest.java,v 1.1 2004-04-14 14:16:11 mvdb Exp $
 */
public class FieldListTest extends TestCase {

    /**
     * Constructor for FieldListTest.
     * @param name the name of the test
     */
    public FieldListTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(FieldListTest.class);
        return suite;
    }

    /**
     * The inner class FieldList
     */
    public void testFieldList() {
        System.out.println("testFieldList");
        BeanMapping.FieldList list = new BeanMapping().new FieldList();
        list.add("string1");
        list.add("string2");
        list.add("string3");
        assertEquals(3, list.size());
        assertEquals(-1, list.indexOf(null));
        assertEquals(0, list.indexOf("string1"));
        assertEquals(2, list.indexOf("string3"));
        assertEquals(-1, list.indexOf("string4"));
        assertEquals(-1, list.indexOf(new Object()));
    }

}
