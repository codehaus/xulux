package org.xulux.nyx.examples.datamodel;


/**
 * A simple tcBase object to use as an example 
 * of generating models
 * 
 * @author Martin van den Bemt
 * @version $Id: Test.java,v 1.1 2002-10-23 00:28:43 mvdb Exp $
 */
public class Test extends DefaultBase
{
    
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private TestContained contained;

    /**
     * Constructor for Test.
     */
    public Test()
    {
        super();
    }
    
    

    /**
     * Returns the contained.
     * @return TestContained
     */
    public TestContained getContained()
    {
        return contained;
    }

    /**
     * Returns the field1.
     * @return String
     */
    public String getField1()
    {
        return field1;
    }

    /**
     * Returns the field2.
     * @return String
     */
    public String getField2()
    {
        return field2;
    }

    /**
     * Returns the field3.
     * @return String
     */
    public String getField3()
    {
        return field3;
    }

    /**
     * Returns the field4.
     * @return String
     */
    public String getField4()
    {
        return field4;
    }

    /**
     * Sets the contained.
     * @param contained The contained to set
     */
    public void setContained(TestContained contained)
    {
        this.contained = contained;
    }

    /**
     * Sets the field1.
     * @param field1 The field1 to set
     */
    public void setField1(String field1)
    {
        this.field1 = field1;
    }

    /**
     * Sets the field2.
     * @param field2 The field2 to set
     */
    public void setField2(String field2)
    {
        this.field2 = field2;
    }

    /**
     * Sets the field3.
     * @param field3 The field3 to set
     */
    public void setField3(String field3)
    {
        this.field3 = field3;
    }

    /**
     * Sets the field4.
     * @param field4 The field4 to set
     */
    public void setField4(String field4)
    {
        this.field4 = field4;
    }

}
