/*
   $Id: JimiImageLoader.java,v 1.3 2004-01-28 15:09:24 mvdb Exp $
   
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

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.sun.jimi.core.Jimi;

/**
 * Loads images using jimi, so also ico, xpm, etc is supported
 * by NYX. This is wrapped up in an interface so you don't
 * get any strange exceptions when jimi is not there.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: JimiImageLoader.java,v 1.3 2004-01-28 15:09:24 mvdb Exp $
 */
public class JimiImageLoader implements ImageLoaderInterface {

    /**
     * The JIMI Main class
     */
    private static String JIMI_MAIN_CLASS = "com.sun.jimi.core.Jimi";

    /**
     * Constructor for JimiImageLoader.
     */
    public JimiImageLoader() {
    }

    /**
     * For now we don't provide any exceptions or logging in case a
     * resrouce is null.
     *
     * @see org.xulux.nyx.swing.util.ImageLoaderInterface#getImage(java.net.URL)
     */
    public Image getImage(URL url) {
        try {
            return Jimi.getImage(url);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * @see org.xulux.nyx.swing.util.ImageLoaderInterface#getIcon(java.net.URL)
     */
    public ImageIcon getIcon(URL url) {
        Image image = getImage(url);
        if (image != null) {
            return new ImageIcon(image);
        } else {
            return null;
        }
    }

    /**
     * @see org.xulux.nyx.swing.util.ImageLoaderInterface#isUsable()
     */
    public boolean isUsable() {
        try {
            Class.forName(JIMI_MAIN_CLASS);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}
