/*
   $Id: BeanMappingTest.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
   
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the BeanMapping.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanMappingTest.java,v 1.1 2004-03-16 14:35:13 mvdb Exp $
 */
public class BeanMappingTest extends TestCase {

    /**
     * Constructor for BeanMappingTest.
     * @param name the name of the test
     */
    public BeanMappingTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(BeanMappingTest.class);
        return suite;
    }


    /**
     * Test the getField(String) method
     * We need to test different variations :
     * 1) A normal field name (foo)
     * 2) A field with dots in the prefix (foo.bar.code)
     *    This should resolve to getField(foo.bar) and a getCode() on the result
     * 3) A field can contain a pointer to another field.
     *    Eg ?WidgetName.foo.bar should return getField(foo).getBar()
     */
    public void testGetField() {
        System.out.println("testGetField");
        BeanMapping mapping = new BeanMapping("test");
        assertNull(mapping.getField(null));
        mapping.setBean(AnotherRecursiveBean.class);
        mapping.setDiscovery(true);
        mapping.discover();
        System.out.println("Fields : " + mapping.getFields());
        BeanField field = new BeanField();
        field.setAlias("pre.postalias");
        //field.setChangeMethod()
    }

    public AnotherRecursiveBean getBean() {
        AnotherRecursiveBean parent = new AnotherRecursiveBean("parent");
        AnotherRecursiveBean child1 = new AnotherRecursiveBean("child1");
        parent.setAnother(child1);
        AnotherRecursiveBean child11 = new AnotherRecursiveBean("child11");
        child1.setAnother(child11);
        AnotherRecursiveBean child111 = new AnotherRecursiveBean("child111");
        return parent;
    }
}
