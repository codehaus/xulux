/*
 $Id: ApplicationContext.java,v 1.18.2.1 2003-02-20 13:31:28 mvdb Exp $

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
package org.xulux.nyx.context;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.guidefaults.GuiDefaultsHandler;
import org.xulux.nyx.rules.IRule;

/**
 * The context contains all the components currently
 * known to the system.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationContext.java,v 1.18.2.1 2003-02-20 13:31:28 mvdb Exp $
 */
public class ApplicationContext
{
    
    private static String GUIDEFAULTS_XML = "org/xulux/nyx/guidefaults/GuiDefaults.xml";
    private static ApplicationContext instance;
    private static Log log = LogFactory.getLog(ApplicationContext.class);
    
    /** 
     * This is the component registery
     * Which contains all components in the application
     * that aren't disposed
     */
    private ArrayList registry;
    
    /**
     * placeholder to know if we are in testmode
     * so exceptions shouldn't be caught
     */
    private static boolean test = false;

    /** 
     * The listeners that are added to components
     */
    private ArrayList listeners;
    
    private HashMap parts;
    
    private HashMap widgets;

    /** 
     * The currently registered rules
     */
    private ArrayList rules;

    /**
     * Request types..
     */
    public static final int PRE_REQUEST = 0;
    public static final int EXECUTE_REQUEST = 1;
    public static final int POST_REQUEST = 2;
    public static final int INIT_REQUEST = 3;
    public static final int DESTROY_REQUEST = 4;

    /**
     * Constructor for GuiContext.
     */
    public ApplicationContext()
    {
        super();
    }

    public static ApplicationContext getInstance()
    {
        if (instance == null)
        {
            instance = new ApplicationContext();
            instance.initializeGuiDefaults();
        }
        return instance;
    }

    /** 
     * Register applicationpart
     */
    public void register(ApplicationPart part)
    {
        if (registry == null)
        {
            registry = new ArrayList();
        }
        registry.add(part);
    }

    /** 
     * Register a certain rule to a certain part.
     * If the rulecount is zero, it will add it by default..
     * TODO : Use th
     * 
     * @param partName can be eg TestForm or TestForm.fieldname If the emapping already exists,
     *         this will add it to the new mapping. Need to do some work here, since 2 identical
     *         rules will be called on processing.
     * @param rule - the rule to register. If there is already an instance of a rule present,
     *                the new rule will be ignored. 
     */
    public void register(String partName, IRule rule)
    {
        if (rules == null)
        {
            rules = new ArrayList();
        }
        if (rule.getUseCount() == 0)
        {
            // we need to register it..
            rules.add(rule);
            // and ad that we have a "user"
            rule.registerPartName(partName);
        }
    }

    /**
     * Deregister everything connected to the partname.
     * It will remove the rule when the useCount is 0.
     * NOTE: We probably should add some kind of cacheSetting 
     *       so heavily used rules will never be deregistered.when they are zero.
     * @param partName
     */
    public void deregister(String partName)
    {
        Iterator it = rules.iterator();
        while (it.hasNext())
        {
            IRule rule = (IRule) it.next();
            rule.deregisterPartName(partName);
        }
    }

    /**
     * Adds default listeners to a certain component
     */
    public void addApplicationListeners(Component component)
    {
        initializeListeners();
        // TODO ;((
    }

    /**
     * Initializes the default listeners
     */
    private void initializeListeners()
    {
        if (listeners != null)
        {
            return;
        }
        listeners = new ArrayList();
    }

    /** 
     * Fires a request of a certain type.
     */
    public static void fireRequest(PartRequest request, int type)
    {
        ApplicationPart part = request.getPart();
        ArrayList rules = part.getRules();
        if (rules == null || rules.size() == 0)
        {
            return;
        }
        if (log.isDebugEnabled())
        {
            log.debug("Rules : " + rules);
        }
        ArrayList currentRules = (ArrayList)rules.clone();
        Iterator it = currentRules.iterator();
        fireRequests(it, request, type);
    }
    
    /**
     * Fires the rules on the specified field
     * @param widget
     * @param request
     * @param type
     */
    public static void fireFieldRequest(
        Widget widget,
        PartRequest request,
        int type)
    {
        ArrayList rules = widget.getRules();
        if (rules == null || rules.size() == 0)
        {
            return;
        }
        ArrayList currentRules = (ArrayList)rules.clone();
        Iterator it = currentRules.iterator();
        fireRequests(it, request, type);
        currentRules.clear();
        currentRules = null;
    }

    public static void fireFieldRequests(PartRequest request, int type)
    {
        ArrayList widgets = request.getPart().getWidgets();
        if (widgets == null)
        {
            return;
        }
        ArrayList currentWidgets = (ArrayList)widgets.clone();
        Iterator wit = currentWidgets.iterator();
        boolean stopAllRules = false;
        while (wit.hasNext() && !stopAllRules)
        {
            Widget widget = (Widget) wit.next();
            if (request.getWidget()!=null && widget.equals(request.getWidget()))
            {
                // don't process the caller again..
                continue;
            }
            stopAllRules = request.getPart().needToStopAllRules(getInstance());
            if (stopAllRules)
            {
                return;
            }
            ArrayList rules = widget.getRules();
            if (rules == null || rules.size() == 0)
            {
                continue;
            }
            ArrayList currentRules = (ArrayList)rules.clone();
            Iterator it = currentRules.iterator();
            stopAllRules = fireRequests(it, request, type);
        }
    }
    
    /**
     * Convenience method so I don't have to replicate
     * code
     * @return true if all rules need to be stopped..
     */
    private static boolean fireRequests(Iterator it, PartRequest request, int type)
    {
//        Widget widget = request.getWidget();        
        boolean stopAllRules = false;
        while (it.hasNext() && !stopAllRules)
        {
            IRule rule = (IRule) it.next();
            stopAllRules = request.getPart().needToStopAllRules(getInstance());
            if (stopAllRules)
            {
                return true;
            }
            try
            {
                switch (type)
                {
                    case PRE_REQUEST :
                        if (log.isTraceEnabled())
                        {
                            log.trace("Processing pre rule : " + rule.getClass().getName());
                        }
                        rule.pre(request);
                        continue;
                    case EXECUTE_REQUEST :
                        if (log.isTraceEnabled())
                        {
                            log.trace("Processing execute rule : " + rule.getClass().getName());
                        }
                        rule.execute(request);
                        continue;
                    case POST_REQUEST :
                        if (log.isTraceEnabled())
                        {
                            log.trace("Processing post rule : " + rule.getClass().getName());
                        }
                        rule.post(request);
                        continue;
                }
            }
            catch(Exception e)
            {
                log.warn("Exception during Processing of rule : " + rule.getClass().getName()+"\n",e);
            }
        }
        return false;
    }
    
    public void registerWidget(String name, String clazz)
    {
        if (this.widgets == null)
        {
            widgets = new HashMap();
        }
        try
        {
            Class widgetClass = Class.forName(clazz);
            widgets.put(name, widgetClass);
        }
        catch (ClassNotFoundException e)
        {
            log.warn("Could not find "+clazz+" for widget named "+name);
        }
    }
    
    /**
     * Initializes the default GuiDefaults in the systme
     * @see org.xulux.nyx.guidefaults.GuiDefaultsHandler#read for more info.
     * to override the current guidefaults..
     */
    private void initializeGuiDefaults()
    {
        GuiDefaultsHandler handler = new GuiDefaultsHandler();
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(GUIDEFAULTS_XML);
        handler.read(stream);
    }
    
    /**
     * Returns the class that corresponds to the name or null
     * when not found
     * See GuiDefaults.xml for more info on defining widgets or 
     * call registerWidget(name, clazz) to register one yourself..
     * 
     * @param name - the widget
     */
    public Class getWidget(String name)
    {
        name = name.toLowerCase();
        return (Class)widgets.get(name);
    }
    
    public void registerPart(ApplicationPart part)
    {
        if (parts == null)
        {
            parts = new HashMap();
        }
        
        parts.put(part.getName(), part);
    }
    
    public ApplicationPart getPart(String name)
    {
        if (parts == null)
        {
            return null;
        }
        ApplicationPart part = (ApplicationPart)parts.get(name);
        return part;
    }
    
    /** 
     * Removes parts from the context
     */
    public void removePart(String name)
    {
        if (parts != null)
        {
            parts.remove(name);
        }
    }
            
    
    public Collection getParts()
    {
        if (parts == null)
        {
            return null;
        }
        return parts.values();
    }
    
    /** 
     * Enable test mode
     */
    public static void setTest(boolean testMode)
    {
        test = testMode;
    }
}
