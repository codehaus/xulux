/*
 $Id: DictionaryHandler.java,v 1.3 2002-11-03 11:56:56 mvdb Exp $

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

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The default dictionary.xml reader
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryHandler.java,v 1.3 2002-11-03 11:56:56 mvdb Exp $
 */
public class DictionaryHandler extends DefaultHandler
{

    private static String PREFIX_ELEMENT = "prefix";
    private static String BEAN_ELEMENT = "bean";
    private static String BASE_ELEMENT = "base";
    private static String FIELDS_ELEMENT = "fields";
    private static String FIELD_ELEMENT = "field";
    private static String NAME_ATTRIBUTE = "name";
    private static String DISCOVERY_ATTRIBUTE = "discovery";
    private static String ALIAS_ATTRIBUTE = "alias";
    
    private static Log log = LogFactory.getLog(DictionaryHandler.class);

    private BeanMapping currentMapping;
    private boolean inBeanElement;
    private boolean inBaseElement;

    /**
     * Constructor for DictionaryHandler.
     */
    public DictionaryHandler()
    {
    }

    public void read(InputStream stream)
    {
        SAXParser saxParser = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        try
        {
            saxParser = factory.newSAXParser();
            try
            {
                saxParser.parse(stream, this);
            }
            catch (SAXException se)
            {
                se.printStackTrace(System.err);
                se.getException().printStackTrace(System.err);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        // and clean up..
        saxParser = null;
        factory = null;
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        if (qName.toLowerCase().equals(PREFIX_ELEMENT))
        {
            Dictionary.getInstance().addMapping(currentMapping);
        }
        else if (qName.toLowerCase().equals(BEAN_ELEMENT))
        {
            inBeanElement = false;
        }
        else if (qName.toLowerCase().equals(BASE_ELEMENT))
        {
            inBaseElement = false;
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(
        String namespaceURI,
        String localName,
        String qName,
        Attributes atts)
        throws SAXException
    {
        if (qName.toLowerCase().equals(PREFIX_ELEMENT))
        {
            currentMapping = new BeanMapping(atts.getValue(NAME_ATTRIBUTE));
        }
        else if (qName.toLowerCase().equals(BEAN_ELEMENT))
        {
            inBeanElement = true;
            String discover = atts.getValue(DISCOVERY_ATTRIBUTE);
            if ("true".equalsIgnoreCase(discover) ||
                "yes".equalsIgnoreCase(discover) ||
                "on".equalsIgnoreCase(discover))
             {
                currentMapping.setDiscovery(true);
             }
        }
        else if (qName.toLowerCase().equals(BASE_ELEMENT))
        {
            inBaseElement = true;
        }
        else if (qName.toLowerCase().equals(FIELD_ELEMENT))
        {
            String name = atts.getValue(NAME_ATTRIBUTE);
            String alias = atts.getValue(ALIAS_ATTRIBUTE);
            if (name!=null)
            {
                BeanField field = currentMapping.createBeanField(name);
                if (field == null)
                {
                    log.warn("could not discover getter for field with name "+name);
                    return;
                }
                if (alias != null)
                {
                    field.setAlias(alias);
                }
                currentMapping.addField(field);
            }
            else
            {
                log.warn("Invalid field element encountered, name should not be null");
            }
            
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException
    {
        if (inBeanElement)
        {
            try
            {
                currentMapping.setBean(
                    Class.forName(new String(arg0, arg1, arg2)));
            }
            catch (ClassNotFoundException e)
            {
                log.error(
                    "Could not find bean "
                        + new String(arg0, arg1, arg2)
                        + " for beanMapping "
                        + currentMapping.getName());
            }
        }
        else if (inBaseElement)
        {
            Class clazz;
            try
            {
                clazz = Class.forName(new String(arg0, arg1, arg2));
                Dictionary.getInstance().setBaseClass(clazz);
                //System.err.println("base clazz ; "+clazz.getName());
            }
            catch (ClassNotFoundException e)
            {
                log.error(
                    "Could not find dictionary base "
                        + new String(arg0, arg1, arg2)+"\n"+
                    "Nyx will possibly not be able to disover data beasn correctly");
            }
        }
    }

}
