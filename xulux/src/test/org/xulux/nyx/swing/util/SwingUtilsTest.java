/*
 $Id: SwingUtilsTest.java,v 1.2 2003-12-15 03:28:53 mvdb Exp $

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
package org.xulux.nyx.swing.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.SimpleLog;
import org.xulux.nyx.gui.WidgetRectangle;

/**
 * Test the SwingUtils class
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingUtilsTest.java,v 1.2 2003-12-15 03:28:53 mvdb Exp $
 */
public class SwingUtilsTest extends TestCase {

    /**
     * Set the logging property, so we can test if no logging is turned on
     */
    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_ALL);
    }

    /**
     * The resource root.. Saves us typing it..
     */
    public static final String RESOURCEROOT = "org/xulux/nyx/swing/util/";

    /**
     * Constructor for SwingUtilsTest.
     * @param name the name of the test
     */
    public SwingUtilsTest(String name) {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite(){
        TestSuite suite = new TestSuite(SwingUtilsTest.class);
        return suite;
    }

    /**
     * Test an image loader that is not usable
     */
    public void testNotUsableImageLoader() {
        System.out.println("testNotUsableImageLoader");
        System.setProperty("nyx.swing.imageloader", "org.xulux.nyx.swing.util.SwingUtilsTest$TestImageLoader");
        SwingUtils.initializeImageLoader();
        assertNull(SwingUtils.getImageLoader());
    }

    /**
     * Test an image loader that is bogus
     */
    public void testBogusImageLoader() {
        System.out.println("testBogusImageLoader");
        System.setProperty("nyx.swing.imageloader", "bogus.class.name");
        SwingUtils.initializeImageLoader();
        assertNull(SwingUtils.getImageLoader());
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        SwingUtils.initializeImageLoader();
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_ALL);
    }

    /**
     * Test a usable imageloader
     */
    public void testUsableImageLoader() {
        System.out.println("testUsableImageLoader");
        System.setProperty("nyx.swing.imageloader", "org.xulux.nyx.swing.util.SwingUtilsTest$UsableImageLoader");
        SwingUtils.initializeImageLoader();
        assertTrue(SwingUtils.getImageLoader() instanceof UsableImageLoader);
        UsableImageLoader l = (UsableImageLoader) SwingUtils.getImageLoader();
        assertNull(SwingUtils.getImage(null, null));
        assertEquals(0, l.getImageCount);
        assertNull(SwingUtils.getImage("", null));
        Image image = SwingUtils.getImage(RESOURCEROOT+"Car.gif", this);
        assertNull(image);
        assertEquals(1, l.getImageCount);
        ImageIcon icon = SwingUtils.getIcon(null, null);
        assertNull(icon);
        icon = SwingUtils.getIcon(RESOURCEROOT+"Car.gif", this);
        assertNull(icon);
        assertEquals(1, l.getIconCount);
    }

    /**
     * Test the swing Image loader, by adding a bogus imageloader to the system properties.
     */
    public void testSwingImageLoader() {
        System.out.println("testSwingImageLoader");
        System.setProperty("nyx.swing.imageloader", "bogus.class.name");
        SwingUtils.initializeImageLoader();
        assertNull(SwingUtils.getImageLoader());
        Image image = SwingUtils.getImage(RESOURCEROOT+"Car.gif", this);
        assertNotNull(image);
        image = SwingUtils.getImage(RESOURCEROOT+"Car.ico", this);
        assertNull(image);
        ImageIcon icon = SwingUtils.getIcon(RESOURCEROOT+"Car.gif", this);
        assertNotNull(icon);
        icon = SwingUtils.getIcon(RESOURCEROOT+"Car.ico", this);
        assertNull(icon);
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        icon = SwingUtils.getIcon(RESOURCEROOT+"Car.ico", this);
        assertNull(icon);
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_ALL);
    }

    /**
     * Test the swing components for accepting null values in images and imageIcons.
     */
    public void testSwingComponents() {
        System.out.println("testSwingComponents");
        JLabel label = new JLabel((Icon) null);
        label.setIcon(null);
        JButton button = new JButton((Icon) null);
        button.setIcon(null);
    }
    /**
     * test the insets
     */
    public void testGetInsets() {
        System.out.println("testGetInsets");
        assertNull(SwingUtils.getInsets(null));
        assertNull(SwingUtils.getInsets(""));
        assertNull(SwingUtils.getInsets("1,2,3"));
        Insets insets = SwingUtils.getInsets("1,2,3,4");
        assertNotNull(insets);
        assertEquals(1, insets.top);
        assertEquals(2, insets.left);
        assertEquals(3, insets.bottom);
        assertEquals(4, insets.right);
        insets = SwingUtils.getInsets("1,2,3,4xx");
        assertNull(insets);
        insets = SwingUtils.getInsets("1 , 2, 3 , 4 ");
        assertNotNull(insets);
        assertEquals(1, insets.top);
        assertEquals(2, insets.left);
        assertEquals(3, insets.bottom);
        assertEquals(4, insets.right);
    }

    /**
     * test getting the dimensions
     *
     */
    public void testGetDimensions() {
        System.out.println("testGetDimensions");
        assertNull(SwingUtils.getDimension(null));
        WidgetRectangle r = new WidgetRectangle(10, 11, 12, 13);
        Dimension dim = SwingUtils.getDimension(r);
        assertEquals(r.getHeight(), dim.height);
        assertEquals(r.getWidth(), dim.width);
    }

    /**
     * An ImageLoader that is not usable
     */
    public class TestImageLoader implements ImageLoaderInterface {
        /**
         * The getImage count
         */
        protected int getImageCount = 0;
        /**
         * The getIcon count
         */
        protected int getIconCount = 0;
         
        public TestImageLoader() {
        }

        /**
         * @see org.xulux.nyx.swing.util.ImageLoaderInterface#getImage(java.net.URL)
         */
        public Image getImage(URL url) {
            getImageCount++;
            return null;
        }

        /**
         * @see org.xulux.nyx.swing.util.ImageLoaderInterface#getIcon(java.net.URL)
         */
        public ImageIcon getIcon(URL url) {
            getIconCount++;
            return null;
        }

        /**
         * @see org.xulux.nyx.swing.util.ImageLoaderInterface#isUsable()
         */
        public boolean isUsable() {
            return false;
        }
    }
    /**
     * Extending the test image loader, since it is not usable by default.
     */
    public class UsableImageLoader extends TestImageLoader {
        /**
         * @see org.xulux.nyx.swing.util.ImageLoaderInterface#isUsable()
         */
        public boolean isUsable() {
            return true;
        }
    }
}
