/*
   $Id: SwingUtilsTest.java,v 1.4 2004-01-28 15:22:08 mvdb Exp $
   
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
package org.xulux.swing.util;

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
import org.xulux.gui.WidgetRectangle;

/**
 * Test the SwingUtils class
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingUtilsTest.java,v 1.4 2004-01-28 15:22:08 mvdb Exp $
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
    public static final String RESOURCEROOT = "org/xulux/swing/util/";

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
    public static Test suite() {
        TestSuite suite = new TestSuite(SwingUtilsTest.class);
        return suite;
    }

    /**
     * Test an image loader that is not usable
     */
    public void testNotUsableImageLoader() {
        System.out.println("testNotUsableImageLoader");
        System.setProperty("xulux.swing.imageloader", "org.xulux.swing.util.SwingUtilsTest$TestImageLoader");
        SwingUtils.initializeImageLoader();
        assertNull(SwingUtils.getImageLoader());
    }

    /**
     * Test an image loader that is bogus
     */
    public void testBogusImageLoader() {
        System.out.println("testBogusImageLoader");
        System.setProperty("xulux.swing.imageloader", "bogus.class.name");
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
        System.setProperty("xulux.swing.imageloader", "org.xulux.swing.util.SwingUtilsTest$UsableImageLoader");
        SwingUtils.initializeImageLoader();
        assertTrue(SwingUtils.getImageLoader() instanceof UsableImageLoader);
        UsableImageLoader l = (UsableImageLoader) SwingUtils.getImageLoader();
        assertNull(SwingUtils.getImage(null, null));
        assertEquals(0, l.getImageCount);
        assertNull(SwingUtils.getImage("", null));
        Image image = SwingUtils.getImage(RESOURCEROOT + "Car.gif", this);
        assertNull(image);
        assertEquals(1, l.getImageCount);
        ImageIcon icon = SwingUtils.getIcon(null, null);
        assertNull(icon);
        icon = SwingUtils.getIcon(RESOURCEROOT + "Car.gif", this);
        assertNull(icon);
        assertEquals(1, l.getIconCount);
    }

    /**
     * A nullpointer exception occurs when we have an file that
     * does not exist
     */
    public void testSwingImageLoaderNPE() {
        System.out.println("testSwingImageLoaderNPE");
        System.setProperty("xulux.swing.imageloader", "bogus.class.name");
        SwingUtils.initializeImageLoader();
        Image image = SwingUtils.getImage("bogus/package/name/Car.gif", this);
        assertNull(image);
        // test without logging.
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        image = SwingUtils.getImage("bogus/package/name/Car.gif", this);
        assertNull(image);
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_ALL);
        
    }
    /**
     * Test the swing Image loader, by adding a bogus imageloader to the system properties.
     */
    public void testSwingImageLoader() {
        System.out.println("testSwingImageLoader");
        System.setProperty("xulux.swing.imageloader", "bogus.class.name");
        SwingUtils.initializeImageLoader();
        assertNull(SwingUtils.getImageLoader());
        Image image = SwingUtils.getImage(RESOURCEROOT + "Car.gif", this);
        assertNotNull(image);
        image = SwingUtils.getImage(RESOURCEROOT + "Car.ico", this);
        assertNull(image);
        ImageIcon icon = SwingUtils.getIcon(RESOURCEROOT + "Car.gif", this);
        assertNotNull(icon);
        icon = SwingUtils.getIcon(RESOURCEROOT + "Car.ico", this);
        assertNull(icon);
        ((SimpleLog) LogFactory.getLog(SwingUtils.class)).setLevel(SimpleLog.LOG_LEVEL_OFF);
        icon = SwingUtils.getIcon(RESOURCEROOT + "Car.ico", this);
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
     * Test the constructor
     */
    public void testConstructor() {
        System.out.println("testConstructor");
        new SwingUtils();
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

        /**
         * Constructor
         */
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
