/*
   $Id: GuiDefaultsHandler.java,v 1.3 2004-03-23 08:42:23 mvdb Exp $
   
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
package org.xulux.guidriver.defaults;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xulux.core.ApplicationContext;
import org.xulux.core.WidgetConfig;

/**
 * Case insensitive processing of the guidefaults.
 *
 * @todo move the contenthandlers to the dataprovider API.
 * @author <a href="mailto;martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsHandler.java,v 1.3 2004-03-23 08:42:23 mvdb Exp $
 */
public class GuiDefaultsHandler extends DefaultHandler {

    /*
     * Element and attribute statics
     */
    private static final String ELEMENT_WIDGETS = "widgets";
    private static final String ELEMENT_PARENTWIDGETHANDLER = "parentwidgethandler";
    private static final String ELEMENT_NATIVEWIDGETHANDLER = "nativewidgethandler";
    private static final String ELEMENT_FIELDEVENTHANDLER = "fieldeventhandler";
    private static final String ELEMENT_NYXTOOLKIT = "nyxtoolkit";
    private static final String ELEMENT_WIDGET = "widget";
    private static final String ELEMENT_GUI = "gui";
    private static final String ELEMENT_ROOT = "guidefaults";
    private static final String ELEMENT_INITIALIZER = "widgetinitializer";
    private static final String ELEMENT_CONTENTHANDLERS = "contenthandlers";
    private static final String ELEMENT_CONTENTHANDLER = "contenthandler";
    private static final String ELEMENT_PROPERTIES="properties";
    private static final String ELEMENT_PROPERTY="property";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_DEFAULT = "defaultType";
    private static final String ATTRIBUTE_DEFAULTVIEW = "defaultView";
    private static final String ATTRIBUTE_USE = "use";
    private static boolean superDefaultsProcessed = false;

    private boolean widgetsStarted = false;
    private boolean propertiesStarted = false;
    private boolean contentHandlersStarted = false;

    private String initClass;
    private String initType;
    private String widgetName;

    /**
     * Constructor for GuiDefaultsHandler.
     */
    public GuiDefaultsHandler() {
    }

    /**
     * Reads the ApplicationPart from a stream and
     * initializes it.
     *
     * @param stream - the inputstream to read from
     */
    public void read(InputStream stream) {
        SAXParser saxParser = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        try {
            saxParser = factory.newSAXParser();
            try {
                saxParser.parse(stream, this);
            } catch (SAXException se) {
                se.printStackTrace(System.out);
                se.getException().printStackTrace(System.out);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        // and clean up..
        saxParser = null;
        factory = null;
        superDefaultsProcessed = true;
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        qName = qName.toLowerCase();
        if (qName.equals(ELEMENT_ROOT)) {
            String defaultType = atts.getValue(ATTRIBUTE_DEFAULT);
            if (defaultType != null) {
                defaultType = defaultType.toLowerCase();
            }
            ApplicationContext.getInstance().setDefaultWidgetType(defaultType);
        } else if (qName.equals(ELEMENT_PARENTWIDGETHANDLER)) {
            String type = getType(atts);
            if (type == null) {
                type = ApplicationContext.getInstance().getDefaultWidgetType();
            }
            String clazz = atts.getValue(ATTRIBUTE_CLASS);
            ApplicationContext.getInstance().registerParentWidgetHandler(type, clazz);
        } else if (qName.equals(ELEMENT_NATIVEWIDGETHANDLER)) {
            String type = getType(atts);
            String clazz = atts.getValue(ATTRIBUTE_CLASS);
            ApplicationContext.getInstance().registerNativeWidgetHandler(type, clazz);
        } else if (qName.equals(ELEMENT_FIELDEVENTHANDLER)) {
            String type = getType(atts);
            String clazz = atts.getValue(ATTRIBUTE_CLASS);
            ApplicationContext.getInstance().registerFieldEventHandler(type, clazz);
        } else if (qName.equals(ELEMENT_NYXTOOLKIT)) {
            String type = getType(atts);
            String clazz = atts.getValue(ATTRIBUTE_CLASS);
            ApplicationContext.getInstance().registerNYXToolkit(clazz, type);
        } else if (qName.equals(ELEMENT_WIDGETS)) {
            widgetsStarted = true;
        } else if (qName.equals(ELEMENT_WIDGET) && widgetsStarted) {
            String name = atts.getValue(ATTRIBUTE_NAME);
            if (name != null) {
                name = name.toLowerCase();
            }
            String clazz = atts.getValue(ATTRIBUTE_CLASS);
            String type = getType(atts);
            if (clazz != null) {
                ApplicationContext.getInstance().registerWidget(name, clazz, type);
            }
            this.widgetName = name;
        } else if (qName.equals(ELEMENT_PROPERTIES) && widgetsStarted && widgetName != null) {
        	propertiesStarted = true;
        } else if (qName.equals(ELEMENT_PROPERTY) && propertiesStarted) {
        	WidgetConfig config = ApplicationContext.getInstance().getWidgetConfig(widgetName);
        	String use = atts.getValue(ATTRIBUTE_USE);
        	String clz = atts.getValue(ATTRIBUTE_CLASS);
        	String name = atts.getValue(ATTRIBUTE_NAME);
        	String type = getType(atts);
        	config.addPropertyHandler(clz, use, name, type);
        } else if (qName.equals(ELEMENT_INITIALIZER)) {
            this.initType = atts.getValue(ATTRIBUTE_TYPE);
            this.initClass = atts.getValue(ATTRIBUTE_CLASS);
        } else if (qName.equals(ELEMENT_CONTENTHANDLERS)) {
            this.contentHandlersStarted = true;
        } else if (qName.equals(ELEMENT_CONTENTHANDLER)) {
            if (this.contentHandlersStarted && widgetsStarted) {
                String clazz = atts.getValue(ATTRIBUTE_CLASS);
                String defaultView = atts.getValue(ATTRIBUTE_DEFAULTVIEW);
                // @todo better logging and error trapping!
                if (clazz == null) {
                    System.out.println("Clazz attribute should not be null");
                    return;
                }
                WidgetConfig config = ApplicationContext.getInstance().getWidgetConfig(widgetName);
                config.addContentHandler(clazz, defaultView); 
            }
        }
    }

    /**
     * Convenient method
     *
     * @param atts the attributes
     * @return the type or the default type
     */
    private String getType(Attributes atts) {
        String type = atts.getValue(ATTRIBUTE_TYPE);
        if (type == null) {
            type = ApplicationContext.getInstance().getDefaultWidgetType();
        }
        return type;
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        qName = qName.toLowerCase();
        if (qName.equals(ELEMENT_WIDGET)) {
            widgetName = null;
        } else if (qName.equals(ELEMENT_INITIALIZER)) {
            if (widgetName != null) {
                ApplicationContext.getInstance().registerWidgetInitializer(initClass, widgetName, initType);
                this.initType = null;
                this.initClass = null;
            }
        } else if (qName.equalsIgnoreCase(ELEMENT_WIDGETS)) {
            widgetsStarted = false;
        } else if (qName.equalsIgnoreCase(ELEMENT_CONTENTHANDLERS)) {
            contentHandlersStarted = false;
        } else if (qName.equalsIgnoreCase(ELEMENT_PROPERTIES)) {
            propertiesStarted = false;
        }
    }

}
