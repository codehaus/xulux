/*
 $Id: WidgetConfigTest.java,v 1.3 2003-05-21 10:00:14 mvdb Exp $

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
package org.xulux.nyx.context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the widgetConfig
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfigTest.java,v 1.3 2003-05-21 10:00:14 mvdb Exp $
 */
public class WidgetConfigTest extends TestCase
{

    /**
     * Constructor for WidgetConfigTest.
     * @param name
     */
    public WidgetConfigTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(WidgetConfigTest.class);
        return suite;
    }
    
    public void testWidgetConfig()
    {
        System.out.println("testWidgetConfig");
        WidgetConfig config = new WidgetConfig();
        Class coreClass = Byte.class;
        Class swingClass = Long.class;
        Class swtClass = Integer.class;
        config.setCoreClass(coreClass);
        config.add("swing", swingClass);
        config.add("swt", swtClass);
        
        assertEquals(coreClass, config.getCoreClass());
        assertEquals(swingClass, config.get("swing"));
        assertEquals(swtClass, config.get("swt"));
    }
    
    /**
     * We don't want nullpointer exceptions
     */
    public void testEmptyWidgetConfig()
    {
        System.out.println("testEmptyWidgetConfig");
        WidgetConfig config = new WidgetConfig();
        assertNull(config.get("swt"));
        assertNull(config.get("swing"));
        assertNull(config.getCoreClass());
    }
}