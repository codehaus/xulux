/*
 $Id: WidgetRectangleTest.java,v 1.2 2003-12-15 23:37:45 mvdb Exp $

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
package org.xulux.nyx.gui;

import java.awt.Rectangle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the widgetrectangle.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRectangleTest.java,v 1.2 2003-12-15 23:37:45 mvdb Exp $
 */
public class WidgetRectangleTest extends TestCase {

    /**
     * Constructor for WidgetRectangleTest.
     * @param name the name of the test
     */
    public WidgetRectangleTest(String name) {
        super(name);
    }

    /**
     * @return the testsuite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(WidgetRectangleTest.class);
        return suite;
    }

    /**
     * Test the constructors and the basic setters
     */
    public void testConstructors() {
        System.out.println("testConstructors");
        WidgetRectangle r = new WidgetRectangle();
        assertEquals(0, r.getHeight());
        assertEquals(0, r.getWidth());
        assertEquals(0, r.getX());
        assertEquals(0, r.getY());
        r.setX(101);
        r.setY(102);
        r.setWidth(103);
        r.setHeight(104);
        assertEquals(101, r.getX());
        assertEquals(102, r.getY());
        assertEquals(103, r.getWidth());
        assertEquals(104, r.getHeight());
        r = new WidgetRectangle(101, 102, 103, 104);
        assertEquals(101, r.getX());
        assertEquals(102, r.getY());
        assertEquals(103, r.getWidth());
        assertEquals(104, r.getHeight());
    }

    /**
     * Test the setSize and setPosition
     *
     */
    public void testSetSizeAndPosition() {
        System.out.println("testSetSizeAndPosition");
        WidgetRectangle r = new WidgetRectangle();
        r.setPosition(101, 102);
        r.setSize(103, 104);
        assertEquals(101, r.getX());
        assertEquals(102, r.getY());
        assertEquals(103, r.getWidth());
        assertEquals(104, r.getHeight());
    }

    /**
     * Test the getRectangle method
     */
    public void testGetRectangle() {
        System.out.println("testGetRectangle");
        WidgetRectangle r = new WidgetRectangle(101, 102, 103, 104);
        Rectangle rect = r.getRectangle();
        assertEquals(101, rect.x);
        assertEquals(102, rect.y);
        assertEquals(103, rect.width);
        assertEquals(104, rect.height);
    }
}
