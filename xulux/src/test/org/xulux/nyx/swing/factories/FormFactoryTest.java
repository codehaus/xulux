package org.xulux.nyx.swing.factories;

import org.xulux.nyx.swing.SimpleForm;
import org.xulux.nyx.examples.datamodel.TestContained;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: FormFactoryTest.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
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
    public void testGetFormDefaultBaseStringClass()
    {
        SimpleForm form =
            (SimpleForm) FormFactory.getForm(
                createDefaultTestObject(),
                FORM_NAME);
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
