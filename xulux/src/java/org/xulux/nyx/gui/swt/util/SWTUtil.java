/*
 $Id: SWTUtil.java,v 1.2 2003-01-27 00:35:51 mvdb Exp $

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
package org.xulux.nyx.gui.swt.util;

import java.io.InputStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;
import org.xulux.nyx.gui.WidgetRectangle;
import org.xulux.nyx.gui.utils.ColorUtils;

/**
 * Utility class to make swt a bit easier accessable.
 * 
 * @task This probably can be made more generic and allow pluging in 
 *       specific implementation. This way you have only one API to talk
 *       to instead of multiple apis depending on extra support of gui
 *       types
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SWTUtil.java,v 1.2 2003-01-27 00:35:51 mvdb Exp $
 */
public class SWTUtil
{
    
    /**
     * The imageLoader instance we are reusing
     */
    private static ImageLoader imageLoader;

    /**
     * Constructor for SWTUtil.
     */
    public SWTUtil()
    {
    }
    
    /**
     * Returns a contructed color based on the color
     * specified
     * @param c - should be a String representation
     *                 of the html color encoding (#AABBBCC)
     * @param device - the device the color should be set for
     */
    public static Color getColor(String color, Device device)
    {
        Color result = null;
        RGB rgb = getRGB(ColorUtils.getRGBFromHex(color));
        result = new Color(device, rgb);
        return result;
    }
    
    /**
     * Returns an RGB from an array of ints
     * @param rgb an array of 3 ints containg rgb
     *         any extra values will be discarded
     */
    public static RGB getRGB(int rgb[])
    {
        return new RGB(rgb[0],rgb[1],rgb[2]);
    }
    
    
    /**
     * See the ImageLoader javadoc for the inner workings.
     * This will not support all imageTypes, but it will
     * support ICO files.
     * 
     * @param resource - the resource to load
     * @param object - the object to use the classloader from (cannot be null)
     * @return - the image 
     */ 
    public static Image getImage(String resource, Widget widget)
    {
        if (imageLoader == null)
        {
            imageLoader = new ImageLoader();
        }
        InputStream stream  = widget.getClass().getClassLoader().getResourceAsStream(resource);
        return new Image(widget.getDisplay() ,stream);
    }
    
    
    /**
     * Creates an swt from a WidgetRectangle.
     * 
     * @param rect - the widget rectangle that is created by nyx
     * @return an swt rectangle
     */
    public static Rectangle getRectangle(WidgetRectangle rect)
    {
        return new Rectangle(rect.getX(), rect.getY(), 
                              rect.getWidth(), rect.getHeight());
    }

}
