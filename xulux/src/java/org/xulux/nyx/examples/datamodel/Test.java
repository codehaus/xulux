/*
 $Id: Test.java,v 1.1.2.1 2003-04-29 16:52:43 mvdb Exp $

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
package org.xulux.nyx.examples.datamodel;


/**
 * A simple tcBase object to use as an example 
 * of generating models
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Test.java,v 1.1.2.1 2003-04-29 16:52:43 mvdb Exp $
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
