/*
 $Id: ApplicationPartHandler.java,v 1.32 2003-11-06 19:53:10 mvdb Exp $

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
package org.xulux.nyx.context;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
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
import org.xulux.nyx.gui.INativeWidgetHandler;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.WidgetFactory;
import org.xulux.nyx.rules.IRule;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;
import org.xulux.nyx.utils.Translator;

/**
 * Reads in a part definition and creates an ApplicationPart
 * from that..
 * TODO: Make sure when widgets skip, the properties are
 *        skipped too..
 * TODO: Move out "generic" code, so we can have a helper class to do all the nyx magic
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPartHandler.java,v 1.32 2003-11-06 19:53:10 mvdb Exp $
 */
public class ApplicationPartHandler extends DefaultHandler {

    private static Log log = LogFactory.getLog(ApplicationPartHandler.class);

    private static String PART_ELEMENT = "part";
    private static String FIELDS_ELEMENT = "fields";
    private static String FIELD_ELEMENT = "field";
    private static String VALUE_ELEMENT = "value";
    private static String SIZE_ELEMENT = "size";
    private static String POSITION_ELEMENT = "position";
    private static String RULES_ELEMENT = "rules";
    private static String RULE_ELEMENT = "rule";
    private static String LISTENER_ELEMENT = "listener";
    private static String TRANSLATION_ELEMENT = "translation";
    /**
     * Include another part in the current part
     */
    private static String INCLUDE_ELEMENT = "includepart";

    /**
     * The prefix of the current part
     * This is to prevent name clashes between fields
     */
    private static String PREFIX_ATTRIBUTE = "prefix";
    /**
     * If the type is native (so this name is a "reserved" keyword.
     * it will try to add the native widget to the applicationpart.
     * You need to make sure the native widgets is of a supported
     * type (eg inherits from Swing classes or SWT classes)
     */
    private static String TYPE_ATTRIBUTE = "type";
    private static String NAME_ATTRIBUTE = "name";
    private static String USE_ATTRIBUTE = "use";
    /**
     * You can specify a class to override the defaults
     * This must be a Widget when not using native.
     */
    private static String CLAZZ_ATTRIBUTE = "class";
    private static String APPLICATION_ATTRIBUTE = "application";
    private static String FIELDPREFIX_ATTRIBUTE = "fieldPrefix";

    private boolean processingField = false;

    private boolean isApplication = false;

    private ApplicationPart part;

    private Object bean;

    private boolean processPosition = false;
    private boolean processSize = false;
    private boolean processUse = false;
    private boolean processValue = false;
    private boolean processUnknown = false;
    private boolean rulesStarted = false;
    private boolean processRule = false;
    private boolean processListener = false;
    private boolean processingNative = false;
    private boolean processingTranslation = false;
    private boolean processIncludePart = false;

    /**
     * Specifies if normal handling should not be
     * done in case of including other xml documents
     * within the current one.
     */
    private boolean ignorePart = false;

    private String currentqName;
    private String currentValue;
    private HashMap currentAtts;

    private String lastField;
    /**
     * Contains the prefix to be used when
     * parsing use fields.
     */
    private String fieldPrefix;
    /**
     * The widget prefix
     */
    private String prefix;

    /**
     * Temporary holds the prefix
     * value untill done calling includePart
     */
    private String tempFieldPrefix;
    private String tempPrefix;

    private static SAXParserFactory factory;

    /**
     * Contains the fields if there are more than
     * where field.get(0) is the parent field definition
     */
    private Stack stack;

    /**
     * Constructor for ApplicationPartHandler.
     */
    public ApplicationPartHandler() {
        stack = new Stack();
        this.bean = null;
        this.part = null;
    }

    public ApplicationPartHandler(ApplicationPart part) {
        this();
        this.part = part;
    }

    public ApplicationPart read(InputStream stream, Object bean) {
        this.bean = bean;
        read(stream);
        return this.part;
    }

    public void read(InputStream stream) {
        if (factory == null) {
            factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
        }
        try {
            SAXParser saxParser = factory.newSAXParser();
            try {
                saxParser.parse(stream, this);
                stream.close();
            }
            catch (SAXException se) {
                if (log.isFatalEnabled()) {
                    log.fatal("Exception during parsing of part xml", se);
                    log.fatal("Exception during parsing of part xml", se.getException());
                }
            }
            finally {
                // and clean up..
                saxParser = null;
            }
        }
        catch (Exception e) {
            if (log.isFatalEnabled()) {
                log.fatal("Exception during paring of part xml", e);
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        qName = qName.toLowerCase();
        if (qName.equals(PART_ELEMENT)) {
            if (this.part == null) {
                this.part = new ApplicationPart(this.bean);
                part.setName(atts.getValue(NAME_ATTRIBUTE));
                isApplication = BooleanUtils.toBoolean(atts.getValue(APPLICATION_ATTRIBUTE));
            }
        }
        else if (qName.equals(TRANSLATION_ELEMENT)) {
            processingTranslation = true;
        }
        else if (qName.equals(FIELD_ELEMENT)) {
            processField(atts);
        }
        else if (qName.equals(RULES_ELEMENT)) {
            rulesStarted = true;
        }
        else if (qName.equals(RULE_ELEMENT)) {
            processRule = true;
        }
        else if (qName.equals(POSITION_ELEMENT)) {
            processPosition = true;
        }
        else if (qName.equals(SIZE_ELEMENT)) {
            processSize = true;
        }
        else if (qName.equals(VALUE_ELEMENT)) {
            processValue = true;
        }
        else if (qName.equals(LISTENER_ELEMENT)) {
            processListener = true;
        }
        else if (qName.equals(INCLUDE_ELEMENT)) {
            this.tempFieldPrefix = this.fieldPrefix;
            this.tempPrefix = this.prefix;
            this.fieldPrefix = atts.getValue(FIELDPREFIX_ATTRIBUTE);
            this.prefix = atts.getValue(PREFIX_ATTRIBUTE);
            processIncludePart = true;
        }
        else {
            currentqName = qName;
            currentAtts = getAttributeMap(atts);
            processUnknown = true;
        }
    }

    private HashMap getAttributeMap(Attributes atts) {
        if (atts == null || atts.getLength() == 0) {
            return null;
        }
        HashMap map = new HashMap();
        for (int i = 0; i < atts.getLength(); i++) {
            map.put(currentqName + "." + atts.getQName(i), atts.getValue(i));
        }
        return map;
    }

    /**
     * (eg &lt;field&gt;&lt;field&gt;&lt;/field&gt;&lt;/field&gt;)
     * Not deeper yet..
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (currentValue != null) {
            // trim it..
            currentValue = currentValue.trim();
            currentValue = Translator.translate(part.getTranslationList(), currentValue);
        }
        qName = qName.toLowerCase();
        if (qName.equals(FIELD_ELEMENT)) {
            if (processingNative) {
                processingNative = false;
            }
            else {
                if (stack.size() > 1) {
                    int item = stack.size() - 2;
                    Widget parentWidget = (Widget) stack.get(item);
                    Widget widget = (Widget) stack.pop();
                    if (parentWidget.canContainChildren()) {
                        parentWidget.addChildWidget(widget);
                        widget.setParent(parentWidget);
                        widget.setPrefix(this.prefix);
                        part.addWidget(widget);
                    }
                }
                else if (stack.size() == 1) {
                    part.addWidget((Widget) stack.pop());
                }
            }
            processingField = false;
        }
        else if (qName.equals(TRANSLATION_ELEMENT)) {
            processingTranslation = false;
            part.addTranslation(currentValue, null);
            currentValue = null;
        }
        else if (qName.equals(RULES_ELEMENT)) {
            rulesStarted = false;
        }
        else if (qName.equals(RULE_ELEMENT)) {
            processRule = false;
            try {
                Widget widget = (Widget) stack.get(stack.size() - 1);
                if (processingField) {
                    addRule(widget, currentValue);
                }
                else {
                    // partrule.. TODO: Test rule registration
                    addRule(null, currentValue);
                }
            }
            catch (ArrayIndexOutOfBoundsException aioobe) {
                // it's a part rule
                addRule(null, currentValue);
            }
            currentValue = null;
        }
        else if (qName.equals(POSITION_ELEMENT) || qName.equals(SIZE_ELEMENT)) {
            if (lastField == null) {
                return;
            }
            int x = 0;
            int y = 0;
            boolean autoSize = false;
            if (currentValue != null && currentValue.equalsIgnoreCase("auto")) {
                // we seem to want to autosize this widget
                autoSize = true;
            }
            try {
                if (!autoSize) {
                    StringTokenizer stn = new StringTokenizer(currentValue, ",");
                    String xStr = stn.nextToken().trim();
                    String yStr = stn.nextToken().trim();
                    x = Integer.parseInt(xStr);
                    y = Integer.parseInt(yStr);
                }
            }
            catch (Exception nse) {
                if (log.isWarnEnabled()) {
                    log.warn("Parsing error with text : " + currentValue);
                    log.warn("with widget : " + ((Widget) stack.get(stack.size() - 1)).getName());
                }
            }
            Widget widget = (Widget) stack.get(stack.size() - 1);
            if (processSize) {
                if (autoSize) {
                    widget.setProperty("autosize", "true");
                }
                else {
                    widget.setSize(x, y);
                }
                processSize = false;
            }
            else if (processPosition) {
                try {
                    if (processingNative) {
                        INativeWidgetHandler nativeHandler = ApplicationContext.getInstance().getNativeWidgetHandler();
                        nativeHandler.setLocationOnWidget((Widget) stack.peek(), x, y);
                    }
                    else {
                        widget.setPosition(x, y);
                        processPosition = false;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
            currentValue = null;
        }
        else if (qName.equals(VALUE_ELEMENT)) {
            processValue = false;
            ((Widget) stack.get(stack.size())).setValue(currentValue);
            currentValue = null;
        }
        else if (qName.equals(LISTENER_ELEMENT)) {
            processListener = false;
            PrePostFieldListener listener = null;
            try {
                Class clazz = Class.forName(currentValue);
                listener = (PrePostFieldListener) clazz.newInstance();
            }
            catch (Exception e) {
            }
            if (listener != null) {
                part.setFieldEventHandler(listener);
            }
            else {
                if (log.isWarnEnabled()) {
                    log.warn("Listener " + currentValue + " not found");
                }
            }
            currentValue = null;
        }
        else if (qName.equals(INCLUDE_ELEMENT)) {
            // start a new ApplicationPartHandler
            // to parse the new part..
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Starting processing of include " + currentValue);
                }
                ApplicationPartHandler handler = new ApplicationPartHandler(this.part);
                handler.setFieldPrefix(this.fieldPrefix);
                handler.setPrefix(this.prefix);
                // set back to original value (could be null)
                this.fieldPrefix = this.tempFieldPrefix;
                this.prefix = this.tempPrefix;
                InputStream stream = getClass().getClassLoader().getResourceAsStream(currentValue.trim());
                if (stream == null) {
                    if (log.isWarnEnabled()) {
                        log.warn("IncludePart: Cannot find " + currentValue.trim());
                    }
                }
                handler.setStack(this.stack);
                handler.read(stream);
            }
            catch (Exception e) {
                log.warn("Exception during processing of includePart ", e);
            }
            currentValue = null;

        }
        else if (processUnknown && currentqName != null) {
            processUnknown = false;
            if (stack.size() > 0) {
                Widget widget = (Widget) stack.get(stack.size() - 1);
                widget.setProperty(currentqName, currentValue);
                if (currentAtts != null) {
                    for (Iterator it = currentAtts.keySet().iterator(); it.hasNext();) {
                        String key = (String) it.next();
                        widget.setProperty(key, (String) currentAtts.get(key));
                    }
                }
            }
            currentqName = null;
            currentValue = null;
            currentAtts = null;
        }
    }

    public void setFieldPrefix(String fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public void ignorePart(boolean ignore) {
        this.ignorePart = true;
    }

    /**
     * Creates the objects and pushes it on the stack
     * @param atts
     */
    private void processField(Attributes atts) {

        processingField = true;
        String type = atts.getValue(TYPE_ATTRIBUTE);
        String name = atts.getValue(NAME_ATTRIBUTE);
        String use = atts.getValue(USE_ATTRIBUTE);
        if (this.fieldPrefix != null) {
            if (use != null) {
                use = fieldPrefix + "." + use;
            }
        }
        //
        // TODO : Move the logic for native handling somewhere else
        if (type.equalsIgnoreCase("native")) {
            processingNative = true;
            INativeWidgetHandler handler = ApplicationContext.getInstance().getNativeWidgetHandler();
            String clazz = atts.getValue(CLAZZ_ATTRIBUTE);
            Widget wg = handler.getWidget(clazz, (Widget) stack.pop());
            stack.push(wg);
            return;
        }
        Widget widget = WidgetFactory.getWidget(type, name);
        if (widget != null) {
            widget.setField(use);
            stack.push(widget);
            this.lastField = name;
            if (log.isTraceEnabled()) {
                log.trace("Adding widget " + name + " of type " + type);
            }
        }
        else {
            if (log.isWarnEnabled()) {
                log.warn("Skipping widget " + name + " since type " + type + " is not available");
            }
            this.lastField = null;
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        if ((processPosition || processSize)
            || processValue
            || (processUnknown && currentqName != null)
            || processRule
            || processListener
            || processingTranslation
            || processIncludePart) {
            if (currentValue == null) {
                currentValue = new String(arg0, arg1, arg2);
            }
            else {
                currentValue += new String(arg0, arg1, arg2);
            }
        }
    }

    private void addRule(Widget widget, String ruleClass) {
        try {
            Class clazz = Class.forName(ruleClass);
            IRule rule = (IRule) clazz.newInstance();
            if (widget != null) {
                widget.registerRule(rule);
            }
            else {
                part.registerRule(rule);
            }
        }
        catch (Exception e) {
            if (widget != null) {
                if (log.isWarnEnabled()) {
                    log.warn("rule class " + ruleClass + " for widget " + widget.getName() + " not found");
                }
            }
            else {
                if (log.isWarnEnabled()) {
                    log.warn("rule class " + ruleClass + " for part " + part.getName() + " not found");
                }
            }
        }
    }
    /**
     * The enddocument registers the part in the ApplicationContext.
     */
    public void endDocument() throws SAXException {
        if (!ignorePart) {
            ApplicationContext.getInstance().register(this.part, isApplication);
        }
    }

}
