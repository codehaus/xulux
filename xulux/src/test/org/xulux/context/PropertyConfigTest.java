/*
   $Id: PropertyConfigTest.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
   
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
package org.xulux.context;

import org.xulux.gui.IPropertyHandler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the propertyConfig class.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PropertyConfigTest.java,v 1.1 2004-03-16 14:35:15 mvdb Exp $
 */
public class PropertyConfigTest extends TestCase {

    /**
     * Constructor for PropertyConfigTest.
     * @param name the name of the test
     */
    public PropertyConfigTest(String name) {
        super(name);
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(PropertyConfigTest.class);
        return suite;
    }

    public void testPropertyConfig() {
        System.out.println("testPropertyConfig");
        PropertyConfig config = new PropertyConfig(null, null, null);
        assertEquals(null, config.getClassName());
        assertEquals(null, config.getName());
        assertEquals(null, config.getUse());
        config = new PropertyConfig(Integer.class, "normal", "text");
        assertEquals(Integer.class, config.getClassName());
        assertEquals("text", config.getName());
        assertEquals("normal", config.getUse());
        // if the property is null, we should get refresh returned..
        config.setName(null);
        assertEquals(IPropertyHandler.REFRESH, config.getUse());
    }

}
