/*
   $Id: SwingUtils.java,v 1.7 2004-01-28 15:09:24 mvdb Exp $
   
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
import java.awt.MediaTracker;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.WidgetRectangle;
import org.xulux.utils.ClassLoaderUtils;
import org.xulux.utils.NyxCollectionUtils;

/**
 * Contains several utilities to make life with swing easier.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingUtils.java,v 1.7 2004-01-28 15:09:24 mvdb Exp $
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
        ImageIcon icon = null;
        URL imageURL = object.getClass().getClassLoader().getResource(resource);
        if (imageLoader != null) {
            icon =  imageLoader.getIcon(imageURL);
        } else {
            if (imageURL != null) {
                icon = new ImageIcon(imageURL);
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
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Image " + resource + " cannot be found");
                }
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
}
