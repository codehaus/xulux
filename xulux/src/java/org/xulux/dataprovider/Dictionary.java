/*
   $Id: Dictionary.java,v 1.7 2004-06-14 13:34:29 mvdb Exp $
   
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
package org.xulux.dataprovider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.bean.BeanDataProvider;
import org.xulux.dataprovider.bean.BeanMapping;
import org.xulux.dataprovider.converters.IConverter;
import org.xulux.utils.ClassLoaderUtils;

/**
 * The BeanDataProvider is the main entry point for the dataprovider.
 * It is the datasource 
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Dictionary.java,v 1.7 2004-06-14 13:34:29 mvdb Exp $
 */
public final class Dictionary {
    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(Dictionary.class);
    
    public static final String DEFAULT_PROVIDER = "bean";
    /**
     * The map containing all the mappings
     */
    private HashMap mappings;
    /**
     * the dictionary instance
     */
    private static Dictionary instance;
    /**
     * A map with the registered providers.
     * Contains instances.
     */
    private Map providers;
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
     * Constructor for BeanDataProvider.
     */
    public Dictionary() {
      registerProvider("bean", new BeanDataProvider());
    }

    /**
     * NOTE: this method will overwrite a provider with the same name!
     *
     * @param name the name of the provider
     * @param provider the provider instance. When provider is null, it will not register
     *        the provider (or reserve the name).
     */
    public void registerProvider(String name, IDataProvider provider) {
      if (provider == null) {
        return;
      }
      if (providers == null) {
        providers = new HashMap();
      }
      providers.put(name, provider);
    }

    /**
     * @param name the name of the providers.
     * @return the provider instance with the specified name
     */
    public IDataProvider getProvider(String name) {
      return (IDataProvider) providers.get(name);
    }
    /**
     * @return a map with the registered providers (key = providername, value is the provider instance)
     */
    public Map getProviders() {
      return providers;
    }

    /**
     * @return the default xulux dataproviders, which is bean.
     */
    public IDataProvider getDefaultProvider() {
      return (IDataProvider) providers.get(DEFAULT_PROVIDER);
    }

    /**
     * @return the one and only instance of the dictionary.
     * @deprecated
     */
    public static Dictionary getInstance() {
//        if (instance == null) {
//            instance = new Dictionary();
//        }
//        return instance;
        return XuluxContext.getDictionary();
    }

    /**
     * @param name the name of the mapping
     * @return the mapping
     */
    public BeanMapping getMapping(String name) {
        return (BeanMapping) getInstance().getProvider(DEFAULT_PROVIDER).getMapping(name);
//        if (maps != null) {
//            return (BeanMapping) mappings.get(name);
//        }
//        return null;
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
    public void addMapping(BeanMapping beanMapping) {
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
     * @param preferredName - the name to use for the mapping
     *                         if it needs to be created
     * @return the beanmapping found
     */
    public BeanMapping getMapping(Class clazz, String preferredName) {
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
     * A convenience method, so you can set values on the highest possible level
     *
     * @param provider the name of the provider to use
     * @param mapping the mapping to use (could be a string or the current part / field value)
     * @param field the field name to use
     * @param object the object to use. If null the provider will try to create the object
     * @param value the value to set in the object
     * @return the object in the changed form, or if object was null, the newly created object.
     *         if an error occured of somekind, it should be logged and the value null should be
     *         returned.
     */
    public Object setValue(String provider, Object mapping, String field, Object object, Object value) {
        IDataProvider iProvider = getProvider(provider);
        if (iProvider != null) {
            return iProvider.setValue(mapping,field, object, value);
        }
        return null;
    }
  /**
   * A convenience method, so you can get values on the highest possible level
   *
   * @param provider the name of the provider to use
   * @param mapping the mapping to use (could be a string or the current part / field value)
   * @param field the field name to use
   * @param object the object to use. If null the provider will try to create the object
   * @return the object, or nul when an error occurred or the value is null.
   */
    public Object getValue(String provider, Object mapping, String field, Object object) {
        IDataProvider iProvider = getProvider(provider);
        if (iProvider != null) {
            return iProvider.getValue(mapping, field, object);
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
        if (clazz != null && converters != null) {
            return (IConverter) converters.get(clazz);
        }
        return null;
    }

}
