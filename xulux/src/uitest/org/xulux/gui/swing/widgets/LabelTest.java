/*
   $Id: LabelTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

import java.io.InputStream;

import org.xulux.context.ApplicationPart;
import org.xulux.gui.PartCreator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test for the label.
 *
 * @author <a href="mailto:marti@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LabelTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
 */
public class LabelTest extends TestCase {

    /**
     * Constructor for LabelTest.
     * @param name the name of the test
     */
    public LabelTest(String name) {
        super(name);
    }

    /**
     * @return the test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(LabelTest.class);
        return suite;
    }

    /**
     * Test the label
     */
    public void testLabel() {
        PersonBean bean = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/gui/swing/widgets/LabelTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(bean, stream);
        part.activate();
    }

    /**
     * For now junit doesn't do very well..
     * @param args the args
     */
    public static void main(String[] args)
    {
        try {
            new LabelTest("LabelTest").testLabel();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
