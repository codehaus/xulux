/*
 $Id: GuiDefaultsHandler.java,v 1.1 2003-12-18 00:17:35 mvdb Exp $

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
package org.xulux.guidefaults;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xulux.context.ApplicationContext;

/**
 * Case insensitive processing of the guidefaults.
 *
 * @author <a href="mailto;martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsHandler.java,v 1.1 2003-12-18 00:17:35 mvdb Exp $
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
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_DEFAULT = "defaultType";
    private static boolean superDefaultsProcessed = false;

    private boolean widgetsStarted = false;
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
        } else if (qName.equals(ELEMENT_INITIALIZER)) {
            this.initType = atts.getValue(ATTRIBUTE_TYPE);
            this.initClass = atts.getValue(ATTRIBUTE_CLASS);
        } else if (qName.equals(ELEMENT_CONTENTHANDLERS)) {
            this.contentHandlersStarted = true;
        } else if (qName.equals(ELEMENT_CONTENTHANDLER)) {
            if (this.contentHandlersStarted && widgetsStarted) {
                String clazz = atts.getValue(ATTRIBUTE_CLASS);
                // @todo better logging and error trapping!
                if (clazz == null) {
                    System.out.println("Clazz attribute should not be null");
                    return;
                }
                ApplicationContext.getInstance().registerWidgetTool(clazz, widgetName);
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
        }
    }

}