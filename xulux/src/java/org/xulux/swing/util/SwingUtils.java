/*
 $Id: SwingUtils.java,v 1.4 2003-12-23 01:21:17 mvdb Exp $

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
package org.xulux.swing.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JRootPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.WidgetRectangle;
import org.xulux.utils.ClassLoaderUtils;
import org.xulux.utils.NyxCollectionUtils;

/**
 * Contains several utilities to make life with swing easier.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingUtils.java,v 1.4 2003-12-23 01:21:17 mvdb Exp $
 */
public class SwingUtils {

    /**
     * The log factory, so we can log the necssary information
     */
    private static Log log = LogFactory.getLog(SwingUtils.class);

    /**
     * The default custom images loader (uses jimi)
     */
    private static final String DEFAULT_CUSTOMIMAGELOADER = "org.xulux.swing.util.JimiImageLoader";

    /**
     * Contains the custom image loader
     */
    private static ImageLoaderInterface imageLoader = null;

    /**
     * Static initializer to check if jimi is present
     * or not. If it is present if will be used for icon
     * and image processing
     */
    static {
        initializeImageLoader();
    }

    /**
     * Override constructor.
     */
    protected SwingUtils() {
    }

    /**
     * Initializes the imageloader from system properties.
     */
    protected static void initializeImageLoader() {
        String il = System.getProperty("xulux.swing.imageloader", DEFAULT_CUSTOMIMAGELOADER);
        try {
            imageLoader = (ImageLoaderInterface) ClassLoaderUtils.getObjectFromClassString(il);
            //imageLoader = (ImageLoaderInterface) Class.forName(il).newInstance();
            if (!imageLoader.isUsable()) {
                imageLoader = null;
                throw new Exception();
            }
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Custom ImageLoader " + il + " could not be loaded. Using Swing imageloader");
            }
        }
    }

    /**
     *
     * @return the imagelaoder that was initialized. If the default is used (swing) than null
     *         is returned
     */
    protected static ImageLoaderInterface getImageLoader() {
        return imageLoader;
    }

    /**
     * Returns the image based on the resource.
     * The resource should be available to the classloader,
     * otherwize it will fail loading.
     * It will use the ImageInterface when jimi is supported
     *
     * @param resource - the resource from the classpath
     * @param object - the object to get the classLoader from.
     *                  At this time it cannot be null
     * @return the Image retrieved
     */
    public static Image getImage(String resource, Object object) {
        if (resource == null || object == null) {
            return null;
        }
        if (imageLoader != null) {
            URL imageURL = object.getClass().getClassLoader().getResource(resource);
            return imageLoader.getImage(imageURL);
        } else {
            ImageIcon icon = getIcon(resource, object);
            if (icon != null) {
                return icon.getImage();
            }
        }
        return null;
    }

    /**
     *
     * @param resource the resource of the image
     * @param object the object to get the classloader from
     * @return the imageIcon found or null if not found
     */
    public static ImageIcon getIcon(String resource, Object object) {
        if (object == null) {
            return null;
        }
        URL imageURL = object.getClass().getClassLoader().getResource(resource);
        if (imageLoader != null) {
            return imageLoader.getIcon(imageURL);
        }
        ImageIcon icon = new ImageIcon(imageURL);
        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
            icon = null;
            if (log.isWarnEnabled()) {
                log.warn(
                    "Image type "
                        + resource
                        + " not supported by swing "
                        + "we advice you to add jimi to your classpath or convert your "
                        + "image to an image type supported by swing");
            }
        }
        return icon;
    }

    /**
     * @param rectangle the rectangle to get the dimensions for
     * @return the dimensions for the rectangle specified
     */
    public static Dimension getDimension(WidgetRectangle rectangle) {
        if (rectangle == null) {
            return null;
        }
        Dimension dim = new Dimension();
        dim.setSize(rectangle.getWidth(), rectangle.getHeight());
        return dim;
    }

    /**
     * Creates an insets object from a comma delimited string.
     * If the string is incomplete null will be returned.
     *
     * @param margin - the margin in the format top,left,bottom,right
     * @return the insets depending on the margin
     */
    public static Insets getInsets(String margin) {
        if (margin == null || "".equals(margin.trim())) {
            return null;
        }
        Object[] ins = NyxCollectionUtils.getListFromCSV(margin).toArray();
        if (ins.length == 4) {
            try {
                int top = Integer.parseInt(((String) ins[0]).trim());
                int left = Integer.parseInt(((String) ins[1]).trim());
                int bottom = Integer.parseInt(((String) ins[2]).trim());
                int right = Integer.parseInt(((String) ins[3]).trim());
                return new Insets(top, left, bottom, right);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * @param comp the component
     * @return the rootpane of the specified object. If the object has no
     *          rootpane, it will try it's parents.
     */
    public static JRootPane getRootPane(JComponent comp) {
        if (comp.getRootPane() != null) {
            return comp.getRootPane();
        }
        JComponent parent = comp;
        while (parent.getParent() != null) {
            if (parent.getRootPane() != null) {
                return parent.getRootPane();
            }
        }
        return null;
    }
}
