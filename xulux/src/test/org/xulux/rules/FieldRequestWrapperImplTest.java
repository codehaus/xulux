/*
   $Id: FieldRequestWrapperImplTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
   
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
package org.xulux.rules;

import org.xulux.context.ApplicationPart;
import org.xulux.context.PartRequest;
import org.xulux.rules.impl.PartRequestImpl;
import org.xulux.swing.layouts.MockWidget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the request wrapper
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: FieldRequestWrapperImplTest.java,v 1.2 2004-01-28 15:22:09 mvdb Exp $
 */
public class FieldRequestWrapperImplTest extends TestCase {

    /**
     * Constructor for FieldRequestWrapperImplTest.
     * @param name the name of the test
     */
    public FieldRequestWrapperImplTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(FieldRequestWrapperImplTest.class);
        return suite;
    }

    /**
     * Test the fieldrequestwrapperimpl
     */
    public void testWrapper() {
        System.out.println("testWrapper");
        FieldRequestWrapperImpl impl = new FieldRequestWrapperImpl();
        ApplicationPart part = new ApplicationPart("name");
        MockWidget widget = new MockWidget("mock");
        PartRequestImpl pimpl = new PartRequestImpl(part, PartRequest.NO_ACTION);
        impl.setPartRequest(pimpl);
        impl.setType(5);
        impl.setWidget(widget);
        assertEquals(pimpl, impl.getPartRequest());
        assertEquals(5, impl.getType());
        assertEquals(widget, impl.getWidget());
    }
}
