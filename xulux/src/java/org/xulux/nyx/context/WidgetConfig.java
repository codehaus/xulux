/*
 $Id: WidgetConfig.java,v 1.11 2003-12-01 02:08:21 mvdb Exp $

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
package org.xulux.nyx.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xulux.nyx.global.IContentHandler;
import org.xulux.nyx.utils.ClassLoaderUtils;

/**
 * The widget config contains the main class
 * of the widget (the abstract) and the
 * core sytem specific declerations
 * (eg swt, swing)
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetConfig.java,v 1.11 2003-12-01 02:08:21 mvdb Exp $
 */
public class WidgetConfig {

    /**
     * The map containg gui layer implementations of the widgettype
     */
    private HashMap map;
    /**
     * The initializers
     */
    private HashMap initializers;
    /**
     * The coreclass
     */
    private Class coreClass;
    /**
     * the map of contenthandlers
     */
    private HashMap contentHandlers;

    /**
     * Constructor for WidgetConfig.
     */
    public WidgetConfig() {
    }

    /**
     * Adds a specific implementation to the widgetConfig
     * @param type the type of gui layer
     * @param widgetClass the widget class
     */
    public void add(String type, Class widgetClass) {
        if (map == null) {
            map = new HashMap();
        }
        map.put(type, widgetClass);
    }

    /**
     *
     * @return the coreClass of the widget
     */
    public Class getCoreClass() {
        return coreClass;
    }

    /**
     * Set the coreClass of the widget
     *
     * @param coreClass the coreclass of the widget
     */
    public void setCoreClass(Class coreClass) {
        this.coreClass = coreClass;
    }
    /**
     *
     * @param type the type of gui layer
     * @return the Class of the specified widget type
     */
    public Class get(String type) {
        if (map == null) {
            return null;
        }
        return (Class) map.get(type);
    }

    /**
     * Adds a widgetInitializer.
     * It checks it the same initializer already exist or
     * not.
     *
     * @param type the type of gui layer
     * @param initializerClass the initializer class
     * @deprecated Please use addWidgetTool
     */
    public void addWidgetInitializer(String type, Class initializerClass) {
        if (initializers == null) {
            initializers = new HashMap();
        }
        ArrayList inits = (ArrayList) initializers.get(type);
        if (inits == null) {
            inits = new ArrayList();
        }
        if (!inits.contains(initializerClass)) {
            inits.add(initializerClass);
        }
        initializers.put(type, inits);
    }

    /**
     *
     * @param type - eg swing, swt, awt, html, etc.
     * @return the widgetHandler classlist for the specified type
     *
     */
    public List getWidgetInitializers(String type) {
        if (initializers == null) {
            return null;
        }
        return (List) initializers.get(type);
    }

    /**
     * Adds a widgettool to the widgetconfig
     * @param type the type of gui layer
     * @param clazzName the widgettools class
     */
    public void addWidgetTool(String type, String clazzName) {
        Class clazz = ClassLoaderUtils.getClass(clazzName);
        addWidgetTool(type, clazz);
    }

    /**
     * Adds a widgettool to the widgetconfig
     * @param type the type of gui layer
     * @param clazz the widgettools class
     */
    public void addWidgetTool(String type, Class clazz) {
        if (clazz == null) {
            return;
        }
        if (getContentHandlers() == null) {
            contentHandlers = new HashMap();
        }
        if (IContentHandler.class.isAssignableFrom(clazz)) {
            Class clzType = ((IContentHandler) ClassLoaderUtils.getObjectFromClass(clazz)).getType();
            getContentHandlers().put(clzType, clazz);
        }
    }

    /**
     * @param clazz the className to get the contenthandler for.
     * @return the contenthandler found.
     */
    public IContentHandler getContentHandler(Class clazz) {
        if (getContentHandlers() != null) {
            Class clz = (Class) getContentHandlers().get(clazz);
            if (clz == null) {
                // try to find a contenthandler that is of the same basetype (except Object).
                Iterator it = getContentHandlers().keySet().iterator();
                while (it.hasNext()) {
                    Class tmpClass = (Class) it.next();
                    if (tmpClass.isAssignableFrom(clazz)) {
                        clz = (Class) getContentHandlers().get(tmpClass);
                        break;
                    }
                }
            }
            return (IContentHandler) ClassLoaderUtils.getObjectFromClass(clz);
        }
        return null;
    }

    /**
     * @return the contenthandlers map or null if no contenthandlers are present 
     */
    public Map getContentHandlers() {
        return contentHandlers;
    }
}
