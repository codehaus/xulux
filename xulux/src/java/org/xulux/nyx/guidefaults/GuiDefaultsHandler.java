/*
 $Id: GuiDefaultsHandler.java,v 1.6 2003-01-26 01:35:10 mvdb Exp $

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
package org.xulux.nyx.guidefaults;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xulux.nyx.context.ApplicationContext;

/**
 * It should be case insensitive, but isn't really ;)
 * 
 * @author <a href="mailto;martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: GuiDefaultsHandler.java,v 1.6 2003-01-26 01:35:10 mvdb Exp $
 */
public class GuiDefaultsHandler extends DefaultHandler
{
    
    /*
     * Element and attribute statics
     */
    private static final String ELEMENT_WIDGETS = "widgets";
    private static final String ELEMENT_WIDGET = "widget";
    private static final String ELEMENT_GUI = "gui";
    private static final String ELEMENT_ROOT = "guidefaults";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_DEFAULT = "defaultType";
    
    private boolean widgetsStarted = false;

    /**
     * Constructor for GuiDefaultsHandler.
     */
    public GuiDefaultsHandler()
    {
    }
    
    /**
     * Reads the ApplicationPart from a stream and 
     * initializes it.
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
        if (qName.equals(ELEMENT_ROOT))
        {
            String defaultType = atts.getValue(ATTRIBUTE_DEFAULT);
            if (defaultType != null)
            {
                defaultType = defaultType.toLowerCase();
            }
            ApplicationContext.getInstance().setDefaultWidgetType(defaultType);
        }
        else if (qName.equals(ELEMENT_WIDGETS))
        {
            widgetsStarted = true;
        }
        else if (qName.equals(ELEMENT_WIDGET) && widgetsStarted)
        {
            String name = atts.getValue(ATTRIBUTE_NAME).toLowerCase();
            String clazz = atts.getValue(ATTRIBUTE_CLASS);
            String type = atts.getValue(ATTRIBUTE_TYPE);
            ApplicationContext.getInstance().registerWidget(name, clazz, type);
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        if (qName.equalsIgnoreCase(ELEMENT_WIDGETS))
        {
            widgetsStarted = false;
        }
    }

}
