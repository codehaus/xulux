/*
 $Id: NyxCollectionUtilsTest.java,v 1.3 2003-11-26 00:45:17 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.utils;

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
 * @version $Id: NyxCollectionUtilsTest.java,v 1.3 2003-11-26 00:45:17 mvdb Exp $
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
