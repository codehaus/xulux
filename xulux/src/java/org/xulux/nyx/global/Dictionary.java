/*
 $Id: Dictionary.java,v 1.5 2002-12-22 23:32:05 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A static applcation dictionary context
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Dictionary.java,v 1.5 2002-12-22 23:32:05 mvdb Exp $
 */
public class Dictionary
{
    private static Log log = LogFactory.getLog(Dictionary.class);
    private HashMap mappings;
    private static Dictionary instance;
    private Class baseClass;
    /**
     * A mappingcache is used temporarily to add
     * classes that have been discovered, this way
     * we can test for infinite loop troubles
     */
    private ArrayList mappingCache;
    private int mappingDepth = 0;

    /**
     * Constructor for Dictionary.
     */
    private Dictionary()
    {
    }

    public static Dictionary getInstance()
    {
        if (instance == null)
        {
            instance = new Dictionary();
        }
        return instance;
    }

    public BeanMapping getMapping(String name)
    {
        if (mappings != null)
        {
            return (BeanMapping) mappings.get(name);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns a clone of the original mapping
     * map, so alteration doesn't effect the
     * registry..
     * @return the mappings in an HashMap
     */
    public HashMap getMappings()
    {
        return (HashMap)mappings.clone();
    }

    public void addMapping(BeanMapping beanMapping)
    {
        if (mappings == null)
        {
            mappings = new HashMap();
        }
        if (mappingCache == null)
        {
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

    public BeanMapping getMapping(Class clazz)
    {
        //System.out.println("Trying to get mapping for class "+clazz.getName());
        return getMapping(clazz, false);
    }
    

    /**
     * @param bean
     * @param newMapping - if true it creates the mapping and adds
     *                      it to the  dictionary, if the name is not
     *                      yet known.
     */
    public BeanMapping getMapping(Class clazz, boolean newMapping)
    {
        String name = null;
        if (newMapping)
        {
            name = getPossibleMappingName(clazz);
        }
        else
        {
            name = getPlainBeanName(clazz);
        }
//        System.out.println("mapping name : "+name);
        return getMapping(clazz, name);
    }
    /**
     * Tries to get a mapping based on the specified bean
     * @param bean
     * @param preferredName - the name to use for the mapping 
     *                         if it needs to be created
     */
    public BeanMapping getMapping(Class clazz, String preferredName)
    {
        if (getBaseClass() == null)
        {
            log.warn("Base class is not set Nyx will possibly not be able to disover data beans correctly");
        }
        BeanMapping mapping = getMapping(preferredName);
        if (mapping == null)
        {
            mapping = new BeanMapping(preferredName);
            mapping.setBean(clazz);
            mapping.setDiscovery(true);
            addMapping(mapping);
        }
        return mapping;
    }

    /**
     * Creates a mapping from the specified with the specified name
     * @param bean
     * @param name
     */
    private BeanMapping createMapping(Object bean, String name)
    {
        // first
        BeanMapping mapping = new BeanMapping(name);
        return null;
    }

    public String getPlainBeanName(Class clazz)
    {
        int pLength = clazz.getPackage().getName().length();
        String mapName = clazz.getName().substring(pLength + 1);
        return mapName;
    }
    /**
     * Tries to find a possible name for the mapping
     * @param bean
     */
    public String getPossibleMappingName(Class clazz)
    {
        String mapName = getPlainBeanName(clazz);
        int i = 1;
        if (getMapping(mapName) != null)
        {
            while (true)
            {
                if (getMapping(mapName + i) == null)
                {
                    mapName+=i;
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
    
    public void clearMappings()
    {
        if (mappings!=null)
        {
            mappings.clear();
        }
    }

    /**
     * Checks to see if this class is currently being 
     * discovered. This is a nice way to prevent 
     * infinite loops.
     * 
     * @param clazz
     * @return boolean
     */
    public boolean isInCache(Class clazz)
    {
        if (mappingCache == null)
        {
            return false;
        }
        return (mappingCache.indexOf(clazz)==-1)?false:true;
    }

}
