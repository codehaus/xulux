/*
 $Id: Resources.java,v 1.1.2.1 2003-04-29 16:52:46 mvdb Exp $

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
package org.xulux.nyx.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

/**
 * This class is a stub for getting resources from property
 * files
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Resources.java,v 1.1.2.1 2003-04-29 16:52:46 mvdb Exp $
 */
public class Resources
{
    /** This will serve as a cache for all image icons */
    private static HashMap imageTable = new HashMap(250);
    
    private static Class defaultResourceClass = Resources.class;
    
    public static Locale locale = Locale.getDefault();
    
    /** 
     * Should only be used while developing, this will
     * not work in a jar
     */
    public static boolean fixMissingResources=true;
    
    /**
     * placeholder constructor.
     */
    private Resources()
    {
    }
    
    public static Resources newInstance()
    {
        return new Resources();
    }
    
    public static void setDefaultResourceClass(Class clazz)
    {
        defaultResourceClass = clazz;
    }
    
    public static Class getDefaultResourceClass()
    {
        return defaultResourceClass;
    }
    
    public static String getResource(Object object, String resource)
    {
        return getResource(object.getClass(), resource);
    }
    
    public static String getResource(Class className, String resource)
    {
        String resourceURL = getResourceURL(className);
        
        String value = "";
        try
        {
            value = ResourceBundle.getBundle(resourceURL, locale, className.getClassLoader()).getString(resource);
        }
        catch(MissingResourceException mre)
        {
            // try to get the default resource
            try
            {
                value = ResourceBundle.getBundle(getResourceURL(newInstance()), locale, getDefaultResourceClass().getClassLoader()).getString(resource);
            }
            catch(MissingResourceException e)
            {
                System.err.println("missing resource : "+resource);
            }
        }
            
        return value;
        
    }
    
    public static String getResourceURL(Object object)
    {
        return getResourceURL(object.getClass());
    }
    public static String getResourceURL(Class className)
    {
        String resourceURL = className.getName();
        return resourceURL.replace('.', '/');
    }
    
    /**
     * Returns the icon that is loaded from the specified 
     * resource (not from a properties file!!
     * use getResource() to get the value from the property
     * file.
     * It first tries to see if it can open the resource as is
     * , then with an added documentbase and after that as an
     * url via getting the classloader.getResource.
     * it will then try to retrieve the resource from
     * the tcResource.properties file.
     * 
     * @param resource
     * @return the image Icon 
     */
    public static ImageIcon getImage(String resource)
    {
        return getImage(getDefaultResourceClass(), resource);
    }
    /**
     * Returns the icon that is loaded from the specified 
     * resource (not from a properties file!!
     * use getResource() to get the value from the property
     * file.
     * It first tries to see if it can open the resource as is
     * , then with an added documentbase and after that as an
     * url via getting the classloader.getResource.
     * 
     * @param object
     * @param resource
     * @return the image Icon 
     */
    
    public static ImageIcon getImage(Object object, String resource)
    {
        return getImage(object.getClass(), resource);
    }

    public static ImageIcon getImage(Class clazz, String resource)
    {
        if(imageTable.containsKey(resource.toLowerCase())) return (ImageIcon)imageTable.get(resource.toLowerCase());

        ImageIcon result = null;
        
        try 
        {
            // first check to see if the resource exists as a
            // file
            if (new File(resource).exists())
            {
                result = new ImageIcon(resource);
            }
            else
            {
                URL imageURL = clazz.getClassLoader().getResource(resource);
                if (imageURL == null) 
                {
                    // try to get a resource from a property file
                    imageURL = clazz.getClassLoader().getResource(getResource(clazz, resource));   
                }
                if (imageURL != null) 
                {
                    result = new ImageIcon(imageURL);
                }
                else
                {
                    System.out.println("Missing image resource : "+resource);
                    // and return an empty imageIcon.
                    result = new ImageIcon();
                }
                    
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        
        if(result!=null) imageTable.put(resource.toLowerCase(), result);
        return result;
    }
    
    /**
     * Loads all images into memory. Doesn't hold any objects
     * Just to speed up the initialization process
     * 
     * @param resource - the resource to load the images from
     */
    public static void loadAllImages(String resource)
    {
        ClassLoader loader = newInstance().getClass().getClassLoader();
        try
        {
            Enumeration enum = loader.getResources(resource);
            
            while(enum.hasMoreElements())
            {
                URL url = (URL) enum.nextElement();
                File imageDir = new File(url.getFile());
                File[] images = imageDir.listFiles();
                if (images != null)
                {
                    for (int i=0; i < images.length; i++)
                    {
                        String img = images[i].toString();
                        getImage(img.substring(img.indexOf(resource)).replace('\\','/'));
                    }
                }
                else
                {
                    System.out.println("Images directory "+imageDir+" not found");
                }
            }
            
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace(System.out);   
        }
        return;
    }
    
    public static void main(String args[])
    {
        loadAllImages("Images");
        System.out.println("loaded all images...");
        System.exit(0);
    }
}
