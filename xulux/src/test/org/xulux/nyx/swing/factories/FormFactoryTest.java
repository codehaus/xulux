/*
 $Id: FormFactoryTest.java,v 1.3.2.1 2003-04-29 16:52:46 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
    permission of The Xulux Project.  For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */
package org.xulux.nyx.swing.factories;

import javax.swing.JFrame;

import org.xulux.nyx.swing.SimpleForm;
import org.xulux.nyx.examples.datamodel.TestContained;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: FormFactoryTest.java,v 1.3.2.1 2003-04-29 16:52:46 mvdb Exp $
 */
public class FormFactoryTest extends TestCase
{

    private static String FIELD1VALUE = "Field1Value";
    private static String FIELD2VALUE = "Field2Value";
    private static String FIELD3VALUE = "Field3Value";
    private static String FIELD4VALUE = "Field4Value";
    private static String FIELD5VALUE = "Field5Value";
    private static String FORM_NAME = "TestForm";

    /**
     * Constructor for FormFactoryTest.
     * @param arg0
     */
    public FormFactoryTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(FormFactoryTest.class);
        return suite;
    }

    /*
     * Test for BaseForm getForm(DefaultBase, String, Class)
     */
    public void testFormFactory()
    {
        /*SimpleForm form =
            (SimpleForm) FormFactory.getForm(
                createDefaultTestObject(),
                FORM_NAME);
        JFrame frame = new JFrame("testFormFactoryTest");
        form.addToWindow(frame);
        frame.show();*/
        System.out.println("testFormFactory");
    }

    private org.xulux.nyx.examples.datamodel.Test createDefaultTestObject()
    {
        org.xulux.nyx.examples.datamodel.Test test = new org.xulux.nyx.examples.datamodel.Test();
        test.setField1(FIELD1VALUE);
        test.setField2(FIELD2VALUE);
        test.setField3(FIELD3VALUE);
        test.setField4(FIELD4VALUE);
        TestContained c = new TestContained();
        c.setField11(FIELD1VALUE);
        c.setField12(FIELD2VALUE);
        c.setField13(FIELD3VALUE);
        test.setContained(c);
        return test;
    }

}
