/*
   $Id: NyxCollectionUtilsTest.java,v 1.2 2004-01-28 15:22:02 mvdb Exp $
   
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the collection utils..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxCollectionUtilsTest.java,v 1.2 2004-01-28 15:22:02 mvdb Exp $
 */
public class NyxCollectionUtilsTest extends TestCase {

    /**
     * Constructor for NyxCollectionUtilsTest.
     * @param name the name of the test
     */
    public NyxCollectionUtilsTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(NyxCollectionUtilsTest.class);
        return suite;
    }

    /**
     * Test for List getListFromCSV(String)
     */
    public void testGetListFromCSVString() {
        assertNull(NyxCollectionUtils.getListFromCSV(null));
        String csv = "1,2,3,4,5";
        List list = NyxCollectionUtils.getListFromCSV(csv);
        assertEquals(5, list.size());
        assertEquals("5", list.get(4));
        String csv2 = "1:2:3:4:5";
        list = NyxCollectionUtils.getListFromCSV(csv2);
        assertEquals(list.size(), 1);
    }

    /**
     * Test for List getListFromCSV(String, String)
     */
    public void testGetListFromCSVStringString() {
        String csv2 = "1:2:3:4:5";
        List list = NyxCollectionUtils.getListFromCSV(csv2, ":");
        assertEquals(list.size(), 5);
    }

    /**
     * Test the getList.
     */
    public void testGetListList() {
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        assertEquals(list, NyxCollectionUtils.getList(list));
    }

    /**
     * Test the getList Collection
     */
    public void testGetListCollection() {
        HashMap map = new HashMap();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        List list = NyxCollectionUtils.getList(map.keySet());
        assertEquals(3, list.size());
        assertTrue(list.contains("1"));
        assertTrue(list.contains("2"));
        assertTrue(list.contains("3"));
    }

    /**
     * Test the getList Array
     */
    public void testGetListArray() {
        String[] string = new String[3];
        string[0] = "1";
        string[1] = "2";
        string[2] = "3";
        List list = NyxCollectionUtils.getList(string);
        assertEquals(3, list.size());
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));
    }

    /**
     * Test the null value
     */
    public void testNullList() {
        assertNull(NyxCollectionUtils.getList(null));
    }

    /**
     * Test the addition of the passed in objet when
     * it is no list or collection type object
     */
    public void testObjectGetList() {
        String test = "test";
        List list = NyxCollectionUtils.getList(test);
        assertTrue(list.contains(test));
    }
    /**
     * Test the constructor.
     */
    public void testConstructor() {
        new NyxCollectionUtils();
    }
}
