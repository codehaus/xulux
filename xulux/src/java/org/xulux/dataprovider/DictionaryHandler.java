/*
   $Id: DictionaryHandler.java,v 1.4 2004-10-14 13:01:16 mvdb Exp $
   
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

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.bean.*;

/**
 * The default dictionary.xml reader
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DictionaryHandler.java,v 1.4 2004-10-14 13:01:16 mvdb Exp $
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
            XuluxContext.getDictionary().getDefaultProvider().addMapping(currentMapping);
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
                ((BeanDataProvider)XuluxContext.getDictionary().getDefaultProvider()).setBaseClass(clazz);
            }
            catch (ClassNotFoundException e)
            {
                log.error(
                    "Could not find dictionary base "
                        + currentValue + "\n"
                    + "Xulux will possibly not be able to disover data beans correctly");
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
