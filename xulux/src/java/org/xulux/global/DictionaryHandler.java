/*
 $Id: DictionaryHandler.java,v 1.1 2003-12-18 00:17:20 mvdb Exp $

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
 * @version $Id: DictionaryHandler.java,v 1.1 2003-12-18 00:17:20 mvdb Exp $
 */
public class DictionaryHandler extends DefaultHandler
{

    /**
     * The log instance
     */
    private static Log log = LogFactory.getLog(DictionaryHandler.class);

    /**
     * the prefix element
     */
    private static final String PREFIX_ELEMENT = "prefix";
    /**
     * the bean element
     */
    private static final String BEAN_ELEMENT = "bean";
    /**
     * the base element
     */
    private static final String BASE_ELEMENT = "base";
    /**
     * the fields element
     */
    private static final String FIELDS_ELEMENT = "fields";
    /**
     * the field element
     */
    private static final String FIELD_ELEMENT = "field";
    /**
     * the parameter element
     */
    private static final String PARAMETER_ELEMENT = "parameter";
    /**
     * the setmethod element
     */
    private static final String SETMETHOD_ELEMENT = "setmethod";
    /**
     * the converters element
     */
    private static final String CONVERTERS_ELEMENT = "converters";
    /**
     * the converter element
     */
    private static final String CONVERTER_ELEMENT = "converter";
    /**
     * the name attribute
     */
    private static final String NAME_ATTRIBUTE = "name";
    /**
     * the discovery attribute
     */
    private static final String DISCOVERY_ATTRIBUTE = "discovery";
    /**
     * the alias attribute
     */
    private static final String ALIAS_ATTRIBUTE = "alias";
    /**
     * the type attribute
     */
    private static final String TYPE_ATTRIBUTE = "type";
    /**
     * the class attribute
     */
    private static final String CLASS_ATTRIBUTE = "class";
    /**
     * the value attribute
     */
    private static final String VALUE_ATTRIBUTE = "value";

    /**
     * the current mapping
     */
    private BeanMapping currentMapping;
    /**
     * in bean element ?
     */
    private boolean inBeanElement;
    /**
     * in base element ?
     */
    private boolean inBaseElement;
    /**
     * in parameter element ?
     */
    private boolean inParameterElement;
    /**
     * in coverters element ?
     */
    private boolean inConvertersElement;
    /**
     * the currentvalue
     */
    private String currentValue;
    /**
     * the current field
     */
    private BeanField currentField;
    /**
     * A list of parameters
     */
    private ArrayList parameters;

    /**
     * Constructor for DictionaryHandler.
     */
    public DictionaryHandler()
    {
    }

    /**
     * Read the dictionary from an inputstream
     * @param stream the stream with the dictionary xml
     */
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
        qName = qName.toLowerCase();
        if (currentValue != null) {
            currentValue = currentValue.trim();
        }
        if (qName.equals(PREFIX_ELEMENT))
        {
            Dictionary.getInstance().addMapping(currentMapping);
        }
        else if (qName.equals(CONVERTERS_ELEMENT)) {
            inConvertersElement = false;
        }
        else if (qName.equals(BEAN_ELEMENT))
        {
            try
            {
                currentMapping.setBean(
                    Class.forName(currentValue));
            }
            catch (ClassNotFoundException e)
            {
                log.error(
                    "Could not find bean "
                        + currentValue
                        + " for beanMapping "
                        + currentMapping.getName());
            }
            currentValue = null;
            inBeanElement = false;
        }
        else if (qName.equals(BASE_ELEMENT))
        {
            Class clazz;
            try
            {
                clazz = Class.forName(currentValue);
                Dictionary.getInstance().setBaseClass(clazz);
            }
            catch (ClassNotFoundException e)
            {
                log.error(
                    "Could not find dictionary base "
                        + currentValue + "\n"
                    + "Nyx will possibly not be able to disover data beans correctly");
            }
            currentValue = null;
            inBaseElement = false;
        }
        else if (qName.equals(FIELD_ELEMENT)) {
            if (parameters != null) {
                this.currentField.setParameters(parameters);
            }
            parameters = null;
            currentMapping.addField(this.currentField);
            this.currentField = null;
        }
        else if (qName.equals(PARAMETER_ELEMENT)) {
            currentValue = null;
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
        qName = qName.toLowerCase();
        if (qName.equals(PREFIX_ELEMENT))
        {
            currentMapping = new BeanMapping(atts.getValue(NAME_ATTRIBUTE));
        }
        else if (qName.equals(CONVERTERS_ELEMENT)) {
            inConvertersElement = true;
        }
        else if (qName.equals(CONVERTER_ELEMENT)) {
            if (inConvertersElement) {
                String clazz = atts.getValue(CLASS_ATTRIBUTE);
                Dictionary.addConverter(clazz);
            }
        }
        else if (qName.equals(BEAN_ELEMENT))
        {
            inBeanElement = true;
            String discover = atts.getValue(DISCOVERY_ATTRIBUTE);
            if ("true".equalsIgnoreCase(discover)
                || "yes".equalsIgnoreCase(discover)
                || "on".equalsIgnoreCase(discover))
             {
                currentMapping.setDiscovery(true);
             }
        }
        else if (qName.equals(BASE_ELEMENT))
        {
            inBaseElement = true;
        }
        else if (qName.equals(FIELD_ELEMENT))
        {
            String name = atts.getValue(NAME_ATTRIBUTE);
            String alias = atts.getValue(ALIAS_ATTRIBUTE);
            if (name != null)
            {
                this.currentField = currentMapping.createBeanField(name);
                if (this.currentField == null) {
                    log.warn("could not discover getter for field with name " + name);
                    return;
                }
                if (alias != null) {
                    this.currentField.setAlias(alias);
                }
            }
            else
            {
                log.warn("Invalid field element encountered, name should not be null");
            }

        }
        else if (qName.equals(PARAMETER_ELEMENT)) {
            if (parameters == null) {
                parameters = new ArrayList();
            }
            String type = atts.getValue(TYPE_ATTRIBUTE);
            String value = atts.getValue(VALUE_ATTRIBUTE);
            parameters.add(new BeanParameter(type, value));
        }
        else if (qName.equals(SETMETHOD_ELEMENT)) {
            if (currentField != null) {
                this.currentField.setChangeMethod(currentMapping.getBean() , atts.getValue(NAME_ATTRIBUTE));
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException
    {
        if (inBeanElement || inBaseElement) {
            if (currentValue == null)
            {
                currentValue = new String(arg0, arg1, arg2);
            }
            else
            {
                currentValue += new String(arg0, arg1, arg2);
            }
        }
    }

}
