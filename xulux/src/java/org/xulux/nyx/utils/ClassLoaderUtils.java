/*
 $Id: ClassLoaderUtils.java,v 1.2 2003-07-14 01:39:40 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.
 
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
 package org.xulux.nyx.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This util class contains classloader utils
 * so we can do actual code reuse. 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ClassLoaderUtils.java,v 1.2 2003-07-14 01:39:40 mvdb Exp $
 */
public class ClassLoaderUtils {

    private static Log log = LogFactory.getLog(ClassLoaderUtils.class);
    /**
     * 
     */
    public ClassLoaderUtils() {
    }
    
    /**
     * 
     * @param classString
     * @return an object from the specified classString or null when errors occur
     */
    public static Object getObjectFromClassString(String classString) {
        System.out.println("classString : "+classString);
        Class clazz;
        try {
            clazz = Class.forName(classString);
            return getObjectFromClass(clazz);
        }
        catch (ClassNotFoundException e) {
            log.warn("Could not find class", e);
        }
        return null;
    }
    /**
     * 
     * @param clazz
     * @return an object from the specified class or null when errors occur
     */
    public static Object getObjectFromClass(Class clazz) {
        if (clazz == null) {
            return null;
        }
        Object object;
        try {
            object = clazz.newInstance();
            return object;
        }
        catch (InstantiationException e) {
            log.warn("Cannot instantiate class "+clazz.getName());
        }
        catch (IllegalAccessException e) {
            log.warn("Cannot access class "+clazz.getName());
        }
        return null;
        
    }

}
