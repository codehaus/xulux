package org.xulux.swing.util;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

/**
 * An image cache to make loading of pages a lot quicker.
 * At destroy of a part if will empty the cache, so the image
 * resources are freed.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ImageCache.java,v 1.1 2005-01-12 18:39:30 mvdb Exp $
 */
public class ImageCache {

    private HashMap images;

    /**
     * 
     */
    public ImageCache() {
        images = new HashMap();
    }
    
    public void addImage(String name, Image image) {
        images.put(name, image);
    }
    
    public void addImage(String name, ImageIcon image) {
        images.put(name, image);
    }
    
    public Image getImage(String name) {
        Object img = images.get(name);
        if (img instanceof ImageIcon) {
            return ((ImageIcon) img).getImage();
        } else {
            return (Image) img;
        }
    }
    
    public ImageIcon getImageIcon(String name) {
        Object img = images.get(name);
        if (img instanceof Image) {
            return new ImageIcon((Image)img);
        } else {
            return (ImageIcon) img;
        }
    }
    
    public void clear() {
        images.clear();
        images = null;
    }

}
