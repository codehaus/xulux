/*
 $Id: Dictionary.java,v 1.1 2003-12-18 00:17:20 mvdb Exp $

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
package org.xulux.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.global.converters.DoubleConverter;
import org.xulux.global.converters.IntegerConverter;
import org.xulux.utils.ClassLoaderUtils;

/**
 * A static applcation dictionary context
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Dictionary.java,v 1.1 2003-12-18 00:17:20 mvdb Exp $
 */
public final class Dictionary {
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(Dictionary.class);
    /**
     * The map containing all the mappings
     */
    private HashMap mappings;
    /**
     * the dictionary instance
     */
    private static Dictionary instance;
    /**
     * the baseclass of all the mappings
     */
    private Class baseClass;
    /**
     * A mappingcache is used temporarily to add
     * classes that have been discovered, this way
     * we can test for infinite loop troubles
     */
    private ArrayList mappingCache;
    /**
     * the depth of the mappings.
     */
    private int mappingDepth = 0;

    /**
     * A map containg the converters
     */
    private static HashMap converters;
    /**
     * @todo Move this to xml!!
     */
    static {
        addConverter(DoubleConverter.class);
        addConverter(IntegerConverter.class);
    }


    /**
     * Constructor for Dictionary.
     */
    private Dictionary()
    {
    }

    /**
     * @return the one and only instance of the dictionary.
     */
    public static Dictionary getInstance()
    {
        if (instance == null)
        {
            instance = new Dictionary();
        }
        return instance;
    }

    /**
     * @param name the name of the mapping
     * @return the mapping
     */
    public BeanMapping getMapping(String name) {
        if (mappings != null) {
            return (BeanMapping) mappings.get(name);
        }
        return null;
    }

    /**
     * Returns a clone of the original mapping
     * map, so alteration doesn't effect the
     * registry..
     * @return the mappings in an HashMap
     */
    public HashMap getMappings() {
        if (mappings == null) {
            return new HashMap();
        }
        return (HashMap) mappings.clone();
    }

    /**
     * add the specified beanmapping
     * @param beanMapping the mapping
     */
    public void addMapping(BeanMapping beanMapping)
    {
        if (mappings == null) {
            mappings = new HashMap();
        }
        if (mappingCache == null) {
            mappingCache = new ArrayList();
        }
        mappingCache.add(beanMapping.getBean());
        mappingDepth++;
        beanMapping.discover();
        mappings.put(beanMapping.getName(), beanMapping);
        mappingDepth--;
        if (mappingDepth == 0)
        {
            mappingCache = null;
        }
    }

    /**
     * @param clazz the class
     * @return the mapping of the specified clazz
     */
    public BeanMapping getMapping(Class clazz) {
        if (clazz == null) {
            return null;
        }
        return getMapping(clazz, false);
    }

    /**
     * Convenience method. This calls the
     * getMapping(Class)
     *
     * @param object the object to get the mapping for
     * @return the beanmapping that is connected
     *          to the class of the specified instance
     *          or null when the object is null.
     */
    public BeanMapping getMapping(Object object) {
        if (object == null) {
            return null;
        }
        return getMapping(object.getClass());
    }


    /**
     * @param clazz the class to get the mapping for
     * @param newMapping if true it creates the mapping and adds
     *                      it to the  dictionary, if the name is not
     *                      yet known.
     * @return the beanmapping or null when no mapping could be created
     */
    public BeanMapping getMapping(Class clazz, boolean newMapping)
    {
        String name = null;
        if (newMapping) {
            name = getPossibleMappingName(clazz);
        } else {
            name = getPlainBeanName(clazz);
        }
        return getMapping(clazz, name);
    }
    /**
     * Tries to get a mapping based on the specified bean
     * @param clazz the class
     * @param preferredName - the name to use for the mapping
     *                         if it needs to be created
     * @return the beanmapping found
     */
    public BeanMapping getMapping(Class clazz, String preferredName)
    {
        if (getBaseClass() == null) {
            if (log.isInfoEnabled()) {
                log.info("Base class is not set Nyx will possibly not be able to disover data beans correctly");
            }
        }
        BeanMapping mapping = getMapping(preferredName);
        if (mapping == null) {
            mapping = new BeanMapping(preferredName);
            mapping.setBean(clazz);
            mapping.setDiscovery(true);
            addMapping(mapping);
        }
        return mapping;
    }

    /**
     * Creates a mapping from the specified with the specified name
     * Do not use, since it doesn't do much...
     * @param bean the bean to create a mapping for
     * @param name the name of the mapping
     * @return the newly creating mapping.
     */
    private BeanMapping createMapping(Object bean, String name)
    {
        // first
        BeanMapping mapping = new BeanMapping(name);
        return mapping;
    }

    /**
     * @param clazz the class
     * @return the plaing bean name for the specified class
     */
    public String getPlainBeanName(Class clazz)
    {
        int pLength = clazz.getPackage().getName().length();
        String mapName = clazz.getName().substring(pLength + 1);
        return mapName;
    }
    /**
     * Tries to find a possible name for the mapping
     * @param clazz the class to investigate
     * @return the possible mapping name
     */
    public String getPossibleMappingName(Class clazz)
    {
        String mapName = getPlainBeanName(clazz);
        int i = 1;
        if (getMapping(mapName) != null) {
            while (true) {
                if (getMapping(mapName + i) == null) {
                    mapName += i;
                    break;
                }
                i++;
            }
        }
        return mapName;
    }

    /**
     * Initializes the dictionary from a stream
     * The stream will be closed.
     * @param stream - a stream with the dictionary.xml
     */
    public void initialize(InputStream stream)
    {
        if (stream == null)
        {
//            System.out.println("Stream is null");
            return;
        }
        new DictionaryHandler().read(stream);
        try
        {
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Returns the baseClass.
     * @return Class
     */
    public Class getBaseClass()
    {
        return baseClass;
    }

    /**
     * Sets the baseClass.
     * @param baseClass The baseClass to set
     */
    public void setBaseClass(Class baseClass)
    {
        this.baseClass = baseClass;
    }

    /**
     * Clears all the mappings currently available
     */
    public void clearMappings() {
        if (mappings != null) {
            mappings.clear();
        }
    }

    /**
     * Reset all dictionary settings..
     *
     */
    public static void reset() {
        Dictionary d = Dictionary.getInstance();
        d.clearMappings();
        if (converters != null) {
            converters.clear();
        }
    }

    /**
     * Checks to see if this class is currently being
     * discovered. This is a nice way to prevent
     * infinite loops.
     *
     * @param clazz the class to look for
     * @return boolean if the class is already in the cache
     */
    public boolean isInCache(Class clazz) {
        if (mappingCache == null) {
            return false;
        }
        return mappingCache.indexOf(clazz) != -1;
    }

    /**
     *
     * @return a clone of the current cache.
     */
    public List getCache() {
        if (mappingCache != null) {
            return (List) mappingCache.clone();
        }
        return null;
    }

    /**
     * @param clazz - the class of the converter.
     */
    public static void addConverter(Class clazz) {
        if (converters == null) {
            converters = new HashMap();
        }
        Object object = ClassLoaderUtils.getObjectFromClass(clazz);
        if (object instanceof IConverter) {
            IConverter c = (IConverter) object;
            converters.put(c.getType() , c);
        } else {
            if (log.isWarnEnabled()) {
                String clazzName = "null";
                if (clazz != null) {
                    clazzName = clazz.getName();
                }
                log.warn(clazzName + " class does not implement IConverter, not registering object");
            }
        }
    }

    /**
     * Convenience method. see addConverter(Class) for more
     * details.
     *
     * @param clazz the clazz to add the converter for
     */
    public static void addConverter(String clazz) {
        if (clazz == null) {
            return;
        }
        Class clz = ClassLoaderUtils.getClass(clazz);
        if (clz == null) {
            if (log.isWarnEnabled()) {
                log.warn(clz + " does not exists or could not be loaded");
            }
        } else {
            addConverter(clz);
        }

        addConverter(ClassLoaderUtils.getClass(clazz));
    }

    /**
     *
     * @param object the object to get the convert for
     * @return the converter for the object specified or
     *          null when no converter is present
     */
    public static IConverter getConverter(Object object) {
        if (object != null) {
            return getConverter(object.getClass());
        }
        return null;
    }
    /**
     *
     * @return all registered converters
     */
    public static Map getConverters() {
        return converters;
    }
    /**
     *
     * @param clazz the class to get the convert for
     * @return the coverter for the clazz specified or null
     *          when no converter is present
     */
    public static IConverter getConverter(Class clazz) {
        if (clazz != null && converters != null)  {
            return (IConverter) converters.get(clazz);
        }
        return null;
    }



}
