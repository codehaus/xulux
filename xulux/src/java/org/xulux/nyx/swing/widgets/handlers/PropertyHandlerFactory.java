/*
 $Id: PropertyHandlerFactory.java,v 1.2 2003-11-06 19:53:11 mvdb Exp $

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
package org.xulux.nyx.swing.widgets.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.IPropertyHandler;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * A Handler for properties. Saves a lot of time duplicating
 * code.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PropertyHandlerFactory.java,v 1.2 2003-11-06 19:53:11 mvdb Exp $
 */
public class PropertyHandlerFactory {

    /**
     * The map that contains property handler objects.
     */
    protected static HashMap ph;

    /**
     * the log..
     */
    protected static Log log = LogFactory.getLog(PropertyHandlerFactory.class);

    /**
     * Allow overrides..
     */
    protected PropertyHandlerFactory() {
    }

    /**
     *
     * @param widget
     * @param property
     * @param properties
     * @return if the widget needs a refresh.
     */
    public static boolean handleProperty(Widget widget, String property, List properties) {
        if (ph == null) {
            return false;
        }
        IPropertyHandler h = (IPropertyHandler)ph.get(property);
        h.handleProperty(widget, property, properties);
        return false;
    }

    public static void registerPropertyHandler(String clazz, String property) {
        if (clazz != null) {
            registerPropertyHandler(ClassLoaderUtils.getClass(clazz), property);
        }
    }
    public static void registerPropertyHandler(Class clazz, String property) {
        if (ph == null) {
            ph = new HashMap();
        }
        if (clazz.isAssignableFrom(IPropertyHandler.class)) {
            ph.put(property, clazz);
        } else {
            if (log.isWarnEnabled()) {
                log.warn("PropertyHandler "+clazz.getName()+
                  " is not of type IPropertyHandler, not registring class");
            }
        }

    }

    public static void registerPropertyHandler(Object object, String property) {
        if (object != null) {
            registerPropertyHandler(object.getClass(), property);
        }
    }

    public static Map getPropertyHandlers() {
        return ph;
    }

}
