package org.xulux.nyx.swing.factories;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.examples.datamodel.TestContained;
import org.xulux.nyx.guidefaults.GuiDefaults;

/**
 * Test the GuiField functionality
 * NOTE: Probably getting obsolete.. 
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiFieldTest.java,v 1.3.2.1 2003-04-29 16:52:46 mvdb Exp $
 */
public class GuiFieldTest extends TestCase
{
    
    private static String FIELD1VALUE = "Field1Value";
    private static String FIELD2VALUE = "Field2Value";
    private static String FIELD3VALUE = "Field3Value";
    private static String FIELD4VALUE = "Field4Value";
    private static String FIELD5VALUE = "Field5Value";
    private static String TESTFORM = "TestForm";
    private static String DEFAULT_FIELD = "Test.field1";

    /**
     * Constructor for GuiFieldTest.
     * @param arg0
     */
    public GuiFieldTest(String name)
    {
        super(name);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(GuiFieldTest.class);
        return suite;
    }
    
    /**
     * Checks the workings of the constructor
     * Only tests the introspection
     */
    public void testConstructor()
    {
        /*
        GuiField field = createGuiField();
        assertEquals(false, field.isRequired());
        */
    }
    
    public void testCurrentValue()
    {
        /*
        GuiField field = createGuiField("Test.field1");
        String value = field.getCurrentValue(createDefaultTestObject());
        assertEquals(FIELD1VALUE, value);
        */
    }
    
    private GuiField createGuiField(String field)
    {
       return new GuiField(TESTFORM, field, GuiDefaults.class);
    }
    
    private GuiField createGuiField()
    {
        return createGuiField(DEFAULT_FIELD);
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
