/*
 $Id: SWTComboTest.java,v 1.1 2003-12-18 00:37:13 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.swt;

import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.gui.PartCreator;
import org.xulux.gui.swing.widgets.PersonBean;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SWTComboTest.java,v 1.1 2003-12-18 00:37:13 mvdb Exp $
 */
public class SWTComboTest extends TestCase {

    /**
     * Constructor for ComboTest.
     * @param name the name of the test
     */
    public SWTComboTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(SWTComboTest.class);
        return suite;
    }

    /**
     * test a simple swt combo
     */
    public void testSimpleComboSwt() {
        ApplicationContext.getInstance();
        ApplicationContext.getInstance().setDefaultWidgetType("swt");
        PersonBean person = new PersonBean("Martin", "van den Bemt");
        String xml = "org/xulux/nyx/gui/ComboTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(person, stream);
        part.activate();
    }

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            new SWTComboTest("SWTComboTest").testSimpleComboSwt();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }
}
