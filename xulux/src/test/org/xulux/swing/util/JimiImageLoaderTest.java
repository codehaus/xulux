/*
   $Id: JimiImageLoaderTest.java,v 1.4 2004-01-28 15:22:08 mvdb Exp $
   
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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.StringTokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: JimiImageLoaderTest.java,v 1.4 2004-01-28 15:22:08 mvdb Exp $
 */
public class JimiImageLoaderTest extends TestCase {

    /**
     * The resource root.. Saves us typing it..
     */
    public static final String RESOURCEROOT = "org/xulux/swing/util/";

    /**
     * Constructor for JimiImageLoaderTest.
     * @param name the name of the test
     */
    public JimiImageLoaderTest(String name) {
        super(name);
    }

    /**
     * @return The test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(JimiImageLoaderTest.class);
        return suite;
    }
    /**
     * Test if it usable
     *
     * @throws Exception just in case
     */
    public void testIsUsable() throws Exception {
        System.out.println("testIsUsable");
        TestClassLoader loader = new TestClassLoader(getJimiUrl());
        URL jimUrl = loader.getResource(JimiImageLoader.class.getName());
        Class clz = loader.loadClass("org.xulux.swing.util.JimiImageLoader");
        Object obj = clz.newInstance();
        ImageLoaderInterface ili = (ImageLoaderInterface) obj;
        assertFalse(ili.isUsable());
    }
    /**
     * Test the getImage
     */
    public void testGetImage() {
        System.out.println("testGetImage");
        JimiImageLoader l = new JimiImageLoader();
        assertNull(l.getImage(null));
        URL imageURL = getClass().getClassLoader().getResource(RESOURCEROOT + "Car.gif");
        assertNotNull(imageURL);
        assertNotNull(l.getImage(imageURL));
        imageURL = getClass().getClassLoader().getResource(RESOURCEROOT + "Car.ico");
        assertNotNull(imageURL);
        assertNotNull(l.getImage(imageURL));
    }

    /**
     * Test the getIcon
     */
    public void testGetIcon() {
        System.out.println("testGetIcon");
        JimiImageLoader l = new JimiImageLoader();
        assertNull(l.getIcon(null));
        URL iconURL = getClass().getClassLoader().getResource(RESOURCEROOT + "Car.gif");
        assertNotNull(iconURL);
        assertNotNull(l.getIcon(iconURL));
        iconURL = getClass().getClassLoader().getResource(RESOURCEROOT + "Car.ico");
        assertNotNull(iconURL);
        assertNotNull(l.getIcon(iconURL));
    }

    /**
     * @return urls from the classpath.
     */
    public URL[] getJimiUrl() {
        String jcp = System.getProperty("java.class.path");
        StringTokenizer stn = new StringTokenizer(jcp, File.pathSeparator);
        URL[] urls = new URL[stn.countTokens()];
        int i = 0;
        while (stn.hasMoreTokens()) {
            try {
                urls[i] = new URL("file", null, -1, stn.nextToken());
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    /**
     * A testclassloader, so we can get exception that are otherwize
     * impossible.. Would be a nice extension to junit :)
     */
    public class TestClassLoader extends URLClassLoader {
        /**
         * the urls to investigate
         */
        private URL[] urls;
        /**
         * Set the current classloader to a null url
         * @param urls the urls to get the classpath from
         */
        public TestClassLoader(URL[] urls) {
            super(new URL[0]);
            this.urls = urls;
        }

        /**
         * @see java.lang.ClassLoader#loadClass(java.lang.String)
         */
        public Class loadClass(String name) throws ClassNotFoundException {
            if (name.indexOf("com.sun.jimi") != -1) {
                throw new ClassNotFoundException("FAKING JIMI CANNOT BE FOUND");
            } else if (name.indexOf("JimiImage") != -1) {
                String r = name.replace('.', '/').concat(".class");
                for (int i = 0; i < urls.length; i++) {
                    URL url = urls[i];
                    if (!url.toExternalForm().endsWith(".jar")) {
                        String str = url.toExternalForm();
                        str = str.concat(File.separator).concat(r);
                        try {
                            URL f = new URL(str);
                            InputStream stream = f.openStream();
                            stream.available();
                            byte[] theClass = new byte[stream.available()];
                            stream.read(theClass);
                            Class clz = defineClass(name, theClass, 0, theClass.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return super.loadClass(name);
        }
    }
}
