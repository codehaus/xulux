/*
 $Id: ApplicationPartHandler.java,v 1.15 2003-01-26 00:41:45 mvdb Exp $

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
package org.xulux.nyx.context;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.WidgetFactory;
import org.xulux.nyx.rules.IRule;
import org.xulux.nyx.listeners.swing.PrePostFieldListener;

/**
 * Reads in a part definition and creates an ApplicationPart
 * from that..
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPartHandler.java,v 1.15 2003-01-26 00:41:45 mvdb Exp $
 */
public class ApplicationPartHandler extends DefaultHandler
{
    
    private static Log log = LogFactory.getLog(ApplicationPartHandler.class);

    private static String PART_ELEMENT = "part";
    private static String FIELDS_ELEMENT = "fields";
    private static String FIELD_ELEMENT = "field";
    private static String VALUE_ELEMENT = "value";
    private static String SIZE_ELEMENT = "size";
    private static String POSITION_ELEMENT = "position";
    private static String TEXT_ELEMENT = "text";
    private static String RULES_ELEMENT = "rules";
    private static String RULE_ELEMENT = "rule";
    private static String LISTENER_ELEMENT = "listener";
    private static String TYPE_ATTRIBUTE = "type";
    private static String NAME_ATTRIBUTE = "name";
    private static String USE_ATTRIBUTE = "use";
    private static String APPLICATION_ATTRIBUTE = "application";
    
    private boolean processingField = false;
    
    private boolean isApplication = false;

    private ApplicationPart part;

    private Object bean;

    private boolean processText = false;
    private boolean processPosition = false;
    private boolean processSize = false;
    private boolean processUse = false;
    private boolean processValue = false;
    private boolean processUnknown = false;
    private boolean rulesStarted = false;
    private boolean processRule = false;
    private boolean processListener = false;
    
    private String currentqName;
    
    private String currentValue;

    /**
     * Contains the fields if there are more than 
     * where field.get(0) is the parent field definition
     */
    Stack stack;

    /**
     * Constructor for ApplicationPartHandler.
     */
    public ApplicationPartHandler()
    {
        stack = new Stack();
        this.bean = null;
        this.part = null;
    }
    
    public ApplicationPartHandler(ApplicationPart part)
    {
        this();
        this.part = part;
    }

    public ApplicationPart read(InputStream stream, Object bean)
    {
        this.bean = bean;
        read(stream);
        return this.part;
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
                stream.close();
            }
            catch (SAXException se)
            {
                if (log.isFatalEnabled())
                {
                    log.fatal("Exception during parsing of part xml",se);
                    log.fatal("Exception during parsing of part xml",se.getException());
                }
            }
        }
        catch (Exception e)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Exception during paring of part xml",e);
            }
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
        if (qName.equals(PART_ELEMENT))
        {
            if (this.part == null)
            {
                this.part = new ApplicationPart(this.bean);
                part.setName(atts.getValue(NAME_ATTRIBUTE));
                isApplication = BooleanUtils.toBoolean(atts.getValue(APPLICATION_ATTRIBUTE));
            }
        }
        else if (qName.equals(FIELD_ELEMENT))
        {
            processField(atts);
        }
        else if (qName.equals(RULES_ELEMENT))
        {
            rulesStarted = true;
        }
        else if (qName.equals(RULE_ELEMENT))
        {
            processRule = true;
        }
        else if (qName.equals(TEXT_ELEMENT))
        {
            processText = true;
        }
        else if (qName.equals(POSITION_ELEMENT))
        {
            processPosition = true;
        }
        else if (qName.equals(SIZE_ELEMENT))
        {
            processSize = true;
        }
        else if (qName.equals(VALUE_ELEMENT))
        {
            processValue = true;
        }
        else if (qName.equals(LISTENER_ELEMENT))
        {
            processListener = true;
        }
        else
        {
            currentqName = qName;
            processUnknown = true;
        }
    }

    /**
     * TODO: Now only real support for 1 deep nesting
     * (eg &lt;field&gt;&lt;field&gt;&lt;/field&gt;&lt;/field&gt;)
     * Not deeper yet..
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        qName = qName.toLowerCase();
        if (qName.equals(FIELD_ELEMENT))
        {
            if (stack.size() > 1)
            {
                Widget parentWidget = (Widget) stack.get(0);
                Widget widget = (Widget) stack.pop();
                if (parentWidget.canContainChildren())
                {
                    parentWidget.addChildWidget(widget);
                    part.addWidget(widget);
                }
            }
            else if (stack.size() == 1)
            {
                part.addWidget((Widget) stack.pop());
            }
        }
        else if (qName.equals(TEXT_ELEMENT))
        {
            processText = false;
            ((Widget) stack.get(stack.size()-1)).setValue(currentValue);
            currentValue = null;
        }
        else if (qName.equals(RULES_ELEMENT))
        {
            rulesStarted = false;
        }
        else if (qName.equals(RULE_ELEMENT))
        {
            processRule = false;
            try
            {
                Widget widget = (Widget) stack.get(stack.size()-1);
                addRule(widget, currentValue);
            }
            catch(ArrayIndexOutOfBoundsException aioobe)
            {
                // it's a part rule
                addRule(null, currentValue);
            }
            currentValue = null;
        }
        else if (qName.equals(POSITION_ELEMENT) || qName.equals(SIZE_ELEMENT))
        {
            int x = 0;
            int y = 0;
            try
            {
                StringTokenizer stn =
                new StringTokenizer(currentValue, ",");
                x = Integer.parseInt(stn.nextToken());
                y = Integer.parseInt(stn.nextToken());
            }
            catch(NoSuchElementException nse)
            {
                if (log.isWarnEnabled())
                {
                    log.warn("Parsing error with text : "+currentValue);
                    log.warn("with widget : "+((Widget) stack.get(stack.size()-1)).getName());
                }
            }
            Widget widget = (Widget) stack.get(stack.size()-1);
            if (processSize)
            {
                widget.setSize(x, y);
                processSize = false;
            }
            else if (processPosition)
            {
                widget.setPosition(x, y);
                processPosition = false;
            }
            currentValue = null;
        }
        else if (qName.equals(VALUE_ELEMENT))
        {
            processValue = false;
            ((Widget) stack.get(stack.size())).setValue(currentValue);
            currentValue = null;
        }
        else if (qName.equals(LISTENER_ELEMENT))
        {
            processListener = false;
            PrePostFieldListener listener = null;
            try
            {
                Class clazz = Class.forName(currentValue);
                listener = (PrePostFieldListener)clazz.newInstance();
            }
            catch(Exception e)
            {
            }
            if (listener != null)
            {
                part.setFieldEventHandler(listener);
            }
            else
            {
                if (log.isWarnEnabled())
                {
                    log.warn("Listener "+currentValue+" not found");
                }
            }
            currentValue = null;
        }
        else if (processUnknown && currentqName != null)
        {
            processUnknown = false;
            Widget widget = (Widget) stack.get(stack.size()-1);
            widget.setProperty(currentqName, currentValue);
            currentqName = null;
            currentValue = null;
        }
    }

    /**
     * Creates the objects and pushes it on the stack
     * @param atts
     */
    private void processField(Attributes atts)
    {

        String type = atts.getValue(TYPE_ATTRIBUTE);
        String name = atts.getValue(NAME_ATTRIBUTE);
        String use = atts.getValue(USE_ATTRIBUTE);
        Widget widget = WidgetFactory.getWidget(type, name);
        if (widget != null)
        {
            widget.setField(use);
            stack.push(widget);
            if (log.isTraceEnabled())
            {
                log.trace("Adding widget "+name+" of type "+type);
            }
        }
        else
        {
            if (log.isWarnEnabled())
            {
                log.warn("Skipping widget "+name+" since type "+
                           type+" is not available");
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException
    {
        if (processText || (processPosition || processSize) || 
            processValue || (processUnknown && currentqName!=null) ||
            processRule || processListener)
        {
            if (currentValue == null)
            {
                currentValue = new String(arg0, arg1, arg2);
            }
            else
            {
                currentValue+=new String(arg0, arg1, arg2);
            }
        }
    }
    
    private void addRule(Widget widget, String ruleClass)
    {
        try
        {
            Class clazz = Class.forName(ruleClass);
            IRule rule = (IRule)clazz.newInstance();
            if (widget != null)
            {
                widget.registerRule(rule);
            }
            else
            {
                part.registerRule(rule);
            }
        }
        catch(Exception e)
        {
            if (widget!=null)
            {
                if (log.isWarnEnabled())
                {
                    log.warn("rule class "+ruleClass+" for widget "+widget.getName()+" not found",e);
                }
            }
            else
            {
                if (log.isWarnEnabled())
                {
                    log.warn("rule class "+ruleClass+" for part "+part.getName()+" not found",e);
                }
            }
        }
    }
    /**
     * The enddocument registers the part in the ApplicationContext.
     */
    public void endDocument() throws SAXException
    {
        ApplicationContext.getInstance().register(this.part, isApplication);
    }

}
