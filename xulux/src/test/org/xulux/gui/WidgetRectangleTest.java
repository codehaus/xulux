/*
   $Id: WidgetRectangleTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.gui;

import java.awt.Rectangle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test the widgetrectangle.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetRectangleTest.java,v 1.2 2004-01-28 15:22:08 mvdb Exp $
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
