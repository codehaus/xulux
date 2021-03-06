/*
   $Id: BeanDataProvider.java,v 1.6 2004-10-20 17:26:58 mvdb Exp $
   
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
package org.xulux.dataprovider.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.dataprovider.DictionaryHandler;
import org.xulux.dataprovider.IDataProvider;
import org.xulux.dataprovider.IMapping;
import org.xulux.dataprovider.converters.DoubleConverter;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.dataprovider.converters.IntegerConverter;
import org.xulux.utils.ClassLoaderUtils;

/**
 * The BeanDataProvider is the main entry point for the dataprovider.
 * It is the datasource 
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BeanDataProvider.java,v 1.6 2004-10-20 17:26:58 mvdb Exp $
 */
public final class BeanDataProvider implements IDataProvider {
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(BeanDataProvider.class);
    /**
     * The map containing all the mappings
     */
    private HashMap mappings;

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
    private HashMap converters;

    /**
     * Constructor for BeanDataProvider.
     */
    public BeanDataProvider() {
      addConverter(DoubleConverter.class);
      addConverter(IntegerConverter.class);
    }

    /**
     * @param name the name of the mapping
     * @return the mapping
     */
    public IMapping getMapping(String name) {
        IMapping mapping = null;
        if (mappings != null) {
            mapping = (BeanMapping) mappings.get(name);
        }
        if (mapping == null) {
            mapping = getMapping(ClassLoaderUtils.getClass(name));
        }
        return mapping;
    }

    /**
     * Returns a clone of the original mapping
     * map, so alteration doesn't effect the
     * registry..
     *
     * @return the mappings in a Map
     */
    public Map getMappings() {
        if (mappings == null) {
            return new HashMap();
        }
        return (HashMap) mappings.clone();
    }

    /**
     * @see org.xulux.dataprovider.IDataProvider#addMapping(org.xulux.dataprovider.IMapping)
     */
    public void addMapping(IMapping mapping) {
        if (mappings == null) {
            mappings = new HashMap();
        }
        if (mappingCache == null) {
            mappingCache = new ArrayList();
        }
        BeanMapping beanMapping = (BeanMapping) mapping;
        mappingCache.add(beanMapping.getBean());
        mappingDepth++;
        beanMapping.discover();
        mappings.put(beanMapping.getName(), beanMapping);
        mappingDepth--;
        if (mappingDepth == 0) {
            mappingCache = null;
        }
    }

    /**
     * @param clazz the class
     * @return the mapping of the specified clazz
     */
    public IMapping getMapping(Class clazz) {
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
    public IMapping getMapping(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
          return getMapping((String) object);
        } else if (object instanceof Class) {
          return getMapping((Class) object);
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
    public BeanMapping getMapping(Class clazz, boolean newMapping) {
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
     * @param preferredName the name to use for the mapping if it needs to be created
     * @return the beanmapping found
     */
    public BeanMapping getMapping(Class clazz, String preferredName) {
        if (getBaseClass() == null) {
            if (log.isDebugEnabled()) {
                log.debug("Base class is not set. Xulux will possibly not be able to disover data beans correctly");
            }
        }
        BeanMapping mapping = (BeanMapping) getMapping(preferredName);
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
    private BeanMapping createMapping(Object bean, String name) {
        // first
        BeanMapping mapping = new BeanMapping(name);
        return mapping;
    }

    /**
     * @param clazz the class
     * @return the plaing bean name for the specified class
     */
    public String getPlainBeanName(Class clazz) {
        int pLength = clazz.getPackage().getName().length();
        String mapName = clazz.getName().substring(pLength + 1);
        return mapName;
    }
    /**
     * Tries to find a possible name for the mapping
     * @param clazz the class to investigate
     * @return the possible mapping name
     */
    public String getPossibleMappingName(Class clazz) {
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
     * @see org.xulux.dataprovider.IDictionary#initialize(java.lang.Object)
     */
    public void initialize(Object object) {
        if (object instanceof InputStream) {
            initialize((InputStream) object);
        } else {
            System.out.println("Cannot initialize dictionary, object is not an InputStream");
        }
    }

    /**
     * Initializes the dictionary from a stream
     * The stream will be closed.
     * @param stream - a stream with the dictionary.xml
     */
    public void initialize(InputStream stream) {
        if (stream == null) {
            return;
        }
        new DictionaryHandler().read(stream);
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns the baseClass.
     * @return Class
     */
    public Class getBaseClass() {
        return baseClass;
    }

    /**
     * Sets the baseClass.
     * @param baseClass The baseClass to set
     */
    public void setBaseClass(Class baseClass) {
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
    public void reset() {
        clearMappings();
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
    public void addConverter(Class clazz) {
        if (converters == null) {
            converters = new HashMap();
        }
        Object object = ClassLoaderUtils.getObjectFromClass(clazz);
        if (object instanceof IConverter) {
            IConverter c = (IConverter) object;
            converters.put(c.getType(), c);
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
    public void addConverter(String clazz) {
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
    public IConverter getConverter(Object object) {
        if (object != null) {
            return getConverter(object.getClass());
        }
        return null;
    }
    /**
     *
     * @return all registered converters
     */
    public Map getConverters() {
        return converters;
    }
    /**
     *
     * @param clazz the class to get the convert for
     * @return the coverter for the clazz specified or null
     *          when no converter is present
     */
    public IConverter getConverter(Class clazz) {
        if (clazz != null && converters != null) {
            return (IConverter) converters.get(clazz);
        }
        return null;
    }

    /**
     * @see org.xulux.dataprovider.IDataProvider#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(String key, String value) {
    }

    /**
     * @see org.xulux.dataprovider.IDataProvider#initialize()
     */
    public void initialize() {
    }

    /**
     * @see org.xulux.dataprovider.IDataProvider#setValue(java.lang.Object, java.lang.String, java.lang.Object, java.lang.Object)
     */
    public Object setValue(Object mapping, String field, Object object, Object value) {
        IMapping iMapping = getMapping(mapping);
        if (iMapping != null) {
            return iMapping.setValue(field, object, value);
        }
        return null;
    }

    /**
     * @see org.xulux.dataprovider.IDataProvider#getValue(java.lang.Object, java.lang.String, java.lang.Object)
     */
    public Object getValue(Object mapping, String field, Object object) {
      return null;
    }

    /**
     * @see org.xulux.dataprovider.IDataProvider#needsPartValue()
     */
    public boolean needsPartValue() {
      	return false;
    }

}
