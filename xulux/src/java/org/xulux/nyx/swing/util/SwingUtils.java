/*
 $Id: SwingUtils.java,v 1.4 2003-09-28 23:27:54 mvdb Exp $

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
package org.xulux.nyx.swing.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.WidgetRectangle;
import org.xulux.nyx.utils.NyxCollectionUtils;


/**
 * Contains several utilities to make life with swing easier.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingUtils.java,v 1.4 2003-09-28 23:27:54 mvdb Exp $
 */
public class SwingUtils
{
    
    /**
     * The log factory, so we can log the necssary information
     */
    private static Log log = LogFactory.getLog(SwingUtils.class);
    
    /** 
     * The default custom images loader (uses jimi)
     */
    private static String DEFAULT_CUSTOMIMAGELOADER = 
                    "org.xulux.nyx.swing.util.JimiImageLoader";
    
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
        String il = System.getProperty("nyx.swing.imageloader",DEFAULT_CUSTOMIMAGELOADER);
        try
        {
            imageLoader = (ImageLoaderInterface)Class.forName(il).newInstance();
        }
        catch (Exception e)
        {
            if (log.isWarnEnabled())
            {
                log.warn("Custom ImageLoader "+il+" could not be loaded." +
                         "using swing imageloader");
            }
        }
    }
    
    
    /**
     * Constructor for SwingUtils.
     */
    public SwingUtils()
    {
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
     */
    public static Image getImage(String resource, Object object)
    {
        return getIcon(resource,object).getImage();
    }
    
    public static ImageIcon getIcon(String resource, Object object)
    {
        URL imageURL = object.getClass().getClassLoader().getResource(resource);            
        if (imageLoader != null)
        {
            return imageLoader.getIcon(imageURL);
        }
        ImageIcon icon = new ImageIcon(imageURL);
        if (icon.getImageLoadStatus() == MediaTracker.ERRORED)
        {
            if (log.isWarnEnabled())
            {
                log.warn("Image type "+resource+" not supported by swing "
                     + "we advice you to add jimi to your classpath or convert your "
                     + "image to an image type supported by swing");
            }
        }
        return icon;
    }
    
    
    /**
     * Returns the dimensions for the rectangle specified
     * @param rectangle
     */
    public static Dimension getDimension(WidgetRectangle rectangle) {
        Dimension dim = new Dimension();
        dim.setSize(rectangle.getWidth(), rectangle.getHeight());
        return dim;
    }

    /**
     * Creates an insets object from a comma delimited string.
     * If the string is incomplete null will be returned.
     * @param margin - the margin in the format 
     * @return
     */
    public static Insets getInsets(String margin) {
        if (margin == null) {
            return null;
        }
        Object[] ins = NyxCollectionUtils.getListFromCSV(margin).toArray();
        if (ins != null && ins.length == 4 ) { 
            try {
                int top = Integer.parseInt((String)ins[0]); 
                int left = Integer.parseInt((String)ins[1]);
                int bottom = Integer.parseInt((String)ins[2]);
                int right = Integer.parseInt((String)ins[3]);
                return new Insets(top,left,bottom,right);
            } catch (Exception e) {
            } 
        }
        return null;
    }

}
