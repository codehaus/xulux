package org.xulux.nyx.utils;


/**
 * @author Martin van den Bemt
 * @version $Id: ResourceParser.java,v 1.1 2002-10-23 00:28:44 mvdb Exp $
 */
public class ResourceParser
{
    private String resource;
    private Class clazz;

    /**
     * Constructor for ResourceParser.
     */
    public ResourceParser(String resource, Class clazz)
    {
        this.resource = resource;
        this.clazz = clazz;
    }

}
