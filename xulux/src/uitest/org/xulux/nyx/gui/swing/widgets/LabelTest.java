/*
 $Id: LabelTest.java,v 1.2 2003-07-23 13:14:43 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.gui.swing.widgets;

import java.io.InputStream;

import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.gui.PartCreator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test for the label.
 * 
 * @author <a href="mailto:marti@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LabelTest.java,v 1.2 2003-07-23 13:14:43 mvdb Exp $
 */
public class LabelTest extends TestCase {

    /**
     * Constructor for LabelTest.
     * @param name
     */
    public LabelTest(String name) {
        super(name);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(LabelTest.class);
        return suite;
    }
    
    public void testLabel() {
        PersonBean bean = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/nyx/gui/swing/widgets/LabelTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(bean, stream);
        part.activate();
    }
    
    
    public static void main(String args[])
    {
        try
        {
            new LabelTest("LabelTest").testLabel();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }

}
