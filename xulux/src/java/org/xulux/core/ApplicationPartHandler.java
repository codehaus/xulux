/*
   $Id: ApplicationPartHandler.java,v 1.1 2004-03-16 15:04:16 mvdb Exp $
   
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
package org.xulux.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xulux.gui.INativeWidgetHandler;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.rules.IRule;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.utils.BooleanUtils;
import org.xulux.utils.Translator;

/**
 * Reads in a part definition and creates an ApplicationPart
 * from that..
 * @todo Make sure when widgets skip, the properties are
 *        skipped too..
 * @todo Move out "generic" code, so we can have a helper class to do all the nyx magic
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPartHandler.java,v 1.1 2004-03-16 15:04:16 mvdb Exp $
 */
public class ApplicationPartHandler extends DefaultHandler {

    /**
     * The log instance for the handler
     */
    private static Log log = LogFactory.getLog(ApplicationPartHandler.class);

    /**
     * The part element
     */
    private static final String PART_ELEMENT = "part";
    /**
     * the fields element
     */
    private static final String FIELDS_ELEMENT = "fields";
    /**
     * the field element
     */
    private static final String FIELD_ELEMENT = "field";
    /**
     * the value element
     */
    private static final String VALUE_ELEMENT = "value";
    /**
     * the size element
     */
    private static final String SIZE_ELEMENT = "size";
    /**
     * the position element
     */
    private static final String POSITION_ELEMENT = "position";
    /**
     * the rules element
     */
    private static final String RULES_ELEMENT = "rules";
    /**
     * the rule element
     */
    private static final String RULE_ELEMENT = "rule";
    /**
     * the listener element
     */
    private static final String LISTENER_ELEMENT = "listener";
    /**
     * the translation element
     */
    private static final String TRANSLATION_ELEMENT = "translation";
    /**
     * Include another part in the current part
     */
    private static final String INCLUDE_ELEMENT = "includepart";

    /**
     * The prefix of the current part
     * This is to prevent name clashes between fields
     */
    private static final String PREFIX_ATTRIBUTE = "prefix";
    /**
     * If the type is native (so this name is a "reserved" keyword.
     * it will try to add the native widget to the applicationpart.
     * You need to make sure the native widgets is of a supported
     * type (eg inherits from Swing classes or SWT classes)
     */
    private static final String TYPE_ATTRIBUTE = "type";
    /**
     * The name attribute
     */
    private static final String NAME_ATTRIBUTE = "name";
    /**
     * the use attribute
     */
    private static final String USE_ATTRIBUTE = "use";
    /**
     * You can specify a class to override the defaults
     * This must be a Widget when not using native.
     */
    private static final String CLAZZ_ATTRIBUTE = "class";
    /**
     * The application attribute
     */
    private static final String APPLICATION_ATTRIBUTE = "application";
    /**
     * the fieldprefix attribute
     */
    private static final String FIELDPREFIX_ATTRIBUTE = "fieldPrefix";

    /**
     * is processing a field ?
     */
    private boolean processingField = false;

    /**
     * Is part an application ?
     */
    private boolean isApplication = false;

    /**
     * the created part
     */
    private ApplicationPart part;

    /**
     * the bean to use
     */
    private Object bean;

    /**
     * processing position
     */
    private boolean processPosition = false;
    /**
     * processing size
     */
    private boolean processSize = false;
    /**
     * processing use
     */
    private boolean processUse = false;
    /**
     * processing value
     */
    private boolean processValue = false;
    /**
     * processing unknown element
     */
    private boolean processUnknown = false;
    /**
     * Are we ready to add rules.
     */
    private boolean rulesStarted = false;
    /**
     * process a rule
     */
    private boolean processRule = false;
    /**
     * process a listener
     */
    private boolean processListener = false;
    /**
     * processing native
     */
    private boolean processingNative = false;
    /**
     * processing translation
     */
    private boolean processingTranslation = false;
    /**
     * processing includepart
     */
    private boolean processIncludePart = false;

    /**
     * Specifies if normal handling should not be
     * done in case of including other xml documents
     * within the current one.
     */
    private boolean ignorePart = false;

    /**
     * Current qualifiedname
     */
    private String currentqName;
    /**
     * current value
     */
    private String currentValue;
    /**
     * current attributes
     */
    private HashMap currentAtts;
    /**
     * the last field created
     */
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
    /**
     * temp prefix when processing includeparts
     */
    private String tempPrefix;

    /**
     * the SAXParserfactory to use
     */
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

    /**
     * Set the aplicationpart manually, so the new read
     * will use that one instead of create a new part.
     * @param part the applicationpart
     */
    public ApplicationPartHandler(ApplicationPart part) {
        this();
        this.part = part;
    }

    /**
     * Process the applicationpart xml
     * @param stream the stream containing the xml
     * @param bean the bean to use while processing
     * @return the newly created part
     */
    public ApplicationPart read(InputStream stream, Object bean) {
        this.bean = bean;
        read(stream);
        return this.part;
    }

    /**
     * Start processing the xml
     * @param stream the stream containing the xml
     */
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
                log.fatal("Exception during parsing of part xml", e);
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
                // a use on a part that cannot get a hold of other parts,
                // makes no sense, so not processing it here.
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

    /**
     * @param atts the attributes to get a map from
     * @return A map of the current attributes
     */
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
     * @todo Test rule registration
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
                } else {
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
            Widget widget = (Widget) stack.get(stack.size() - 1);
            if (processSize) {
                if (autoSize) {
                    widget.setProperty("autosize", "true");
                } else {
                    widget.setProperty("size", currentValue);
                }
                processSize = false;
            }
            else if (processPosition) {
                try {
                    if (processingNative) {
                        try {
                            if (!autoSize) {
                                StringTokenizer stn = new StringTokenizer(currentValue, ",");
                                String xStr = stn.nextToken().trim();
                                String yStr = stn.nextToken().trim();
                                x = Integer.parseInt(xStr);
                                y = Integer.parseInt(yStr);
                            }
                        } catch (Exception nse) {
                            if (log.isWarnEnabled()) {
                                log.warn("Parsing error with text : " + currentValue);
                                log.warn("with widget : " + ((Widget) stack.get(stack.size() - 1)).getName());
                            }
                        }
                        INativeWidgetHandler nativeHandler = ApplicationContext.getInstance().getNativeWidgetHandler();
                        nativeHandler.setLocationOnWidget((Widget) stack.peek(), x, y);
                    }
                    else {
                        //widget.setPosition(x, y);
                        widget.setProperty("position", currentValue);
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
                listener = null;
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

    /**
     * Set the fieldprefix
     * @param fieldPrefix the name of the fieldPrefix
     */
    public void setFieldPrefix(String fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
    }

    /**
     * Set the prefix
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Set the stack from the parent applicationpart
     * @param stack the stack with the widget tree.
     */
    public void setStack(Stack stack) {
        this.stack = stack;
    }

    /**
     * Ignore the part creation
     * @param ignore ignore it or not (defaults to false)
     */
    public void ignorePart(boolean ignore) {
        this.ignorePart = true;
    }

    /**
     * Creates the objects and pushes it on the stack
     * @param atts the attributes to process for the field
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

    /**
     * Add a rule to the specified widget
     * @param widget the widget
     * @param ruleClass the rule class to add
     */
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
     * @throws SAXException when ending the document fails
     */
    public void endDocument() throws SAXException {
        if (!ignorePart) {
            ApplicationContext.getInstance().register(this.part, isApplication);
        }
    }

}
