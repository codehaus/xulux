/*
 $Id: ColorUtilsTest.java,v 1.1 2003-01-08 02:37:07 mvdb Exp $

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
package org.xulux.nyx.gui.utils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ColorUtilsTest.java,v 1.1 2003-01-08 02:37:07 mvdb Exp $
 */
public class ColorUtilsTest extends TestCase
{

    /**
     * Constructor for ColorUtilsTest.
     * @param arg0
     */
    public ColorUtilsTest(String name)
    {
        super(name);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(ColorUtilsTest.class);
        return suite;
    }
    
    /**
     * Test the rgb from hex method.
     * Seems more like a test of Integer.parseInt() though :)
     */
    public void testGetRGBFromHex()
    {
        System.out.println("testGetRGBFromHex");
        int result[] = ColorUtils.getRGBFromHex("AABBCC");
        assertEquals(170, result[0]);
        assertEquals(187, result[1]);
        assertEquals(204, result[2]);
        result = ColorUtils.getRGBFromHex("ABC");
        assertEquals(170, result[0]);
        assertEquals(187, result[1]);
        assertEquals(204, result[2]);
        result = ColorUtils.getRGBFromHex("012345");
        assertEquals(01, result[0]);
        assertEquals(35, result[1]);
        assertEquals(69, result[2]);
        result = ColorUtils.getRGBFromHex("FFFFFF");
        assertEquals(255, result[0]);
        assertEquals(255, result[1]);
        assertEquals(255, result[2]);
        result = ColorUtils.getRGBFromHex("FF00FF");
        assertEquals(255, result[0]);
        assertEquals(00, result[1]);
        assertEquals(255, result[2]);
        result = ColorUtils.getRGBFromHex("000");
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        assertEquals(0, result[2]);
        result = ColorUtils.getRGBFromHex("XXXXXX");
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        assertEquals(0, result[2]);
    }

}
