/*
 $Id: ApplicationPart.java,v 1.10 2002-11-05 14:32:46 mvdb Exp $

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;

import org.xulux.nyx.context.impl.PartRequestImpl;
import org.xulux.nyx.global.BeanField;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.ValueWidget;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.rules.DefaultPartRule;
import org.xulux.nyx.rules.IRule;
import org.xulux.nyx.swing.factories.GuiField;

/**
 * An Application is a part of the application
 * which will contain a "block" of data/gui  that is in some 
 * way related with each other (eg a form, table, window)
 * 
 * It will maintain 2 beans for every bean : the original
 * and the copy on the screen. 
 * 
 * 
 * NOTE: Add refresh possibilities to the application parts,
 * so your are able to replace the original bean and determine if
 * the "bean refresh" changed the bean or not.
 * Esp. usefull when concurrent changes of beans occur by different
 * people (although this would be merely convenience, since other code
 * should handle these kind of situation..).
 *  
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ApplicationPart.java,v 1.10 2002-11-05 14:32:46 mvdb Exp $
 */
public class ApplicationPart
{
    
    /**
     * The original bean
     */
    private Object bean;
    
    private BeanMapping mapping;
    
    private Object parentWidget;
    
    private WidgetList widgets;
    
    private ArrayList partRules;
    
    private boolean activated;
    
    public static int runIndex = 0;
    
    private SessionPart session;
    
    /**
     * Constructor for GuiPart.
     */
    public ApplicationPart()
    {
    }
    
    /**
     * Used for single bean representation
     */
    public ApplicationPart(Object bean)
    {
        this();
        this.bean = bean;
        this.mapping = Dictionary.getInstance().getMapping(bean.getClass());
    }
        
    
    /** 
     * @return Are there any changes in the part
     */
    public boolean isDirty()
    {
        return isDirty();
    }
    
    /**
     * @return if the field specified has been changed..
     */
    public boolean isDirty(String field)
    {
        return false;
    }
    
    /**
     * Returns if the field specified is the one currently
     * being processed. This way you can quicly determine in
     * your code if the rule 
     */
    public boolean isCurrentField(String field)
    {
        return false;
    }
    
    /**
     * No primitive support yet.. sorry ;)
     * This is pretty ignoarant.. If a field does not exists 
     * in the apppart, it will do nothing
     * @param field
     * @param value
     */
    public void setBeanValue(String field, Object value)
    {
        IField bField = mapping.getField(field);
        if (bField.isReadOnly())
        {
            System.err.println("Cannot set value on a read only field");
            // we cannot change the value,
            // so let's not try it..
            return;
        }
        
        if (!bField.setValue(this.bean, value))
        {
            System.err.println("Could not set value");
        }
    }
    
    
    public void setGuiValue(String field, Object value)
    {
        Widget widget = (Widget) widgets.get(field);
        if (widget instanceof ValueWidget)
        {
            ((ValueWidget)widget).setValue(value);
        }
    }
    
    /** 
     * Returns the current value of the specified field
     * @param field
     */
    public Object getGuiValue(String field)
    {
        Widget widget = (Widget)widgets.get(field);
        if (widget instanceof ValueWidget)
        {
          return ((ValueWidget)widget).getValue();
        }
        return null;
    }
    
    /**
     * @return the current bean value
     */
    public Object getBeanValue(String field)
    {
        return mapping.getField(field).getValue(getBean());
    }
    
    public IField getField(String name)
    {
        return mapping.getField(name);
    }
    
    /**
     * Returns the widget that is connected to the field
     */
    public Widget getWidget(String field)
    {
        return (Widget)widgets.get(field);
    }
    
    /**
     * Returns the plain bean
     */
    public Object getBean()
    {
        return this.bean;
    }
    
    /** 
     * Returns the name of the part
     */
    public String getName()
    {
        return null;
    }
    
    /**
     * The parent widget for all child widgets to be added
     * @param widget 
     */
    public void setParentWidget(Object widget)
    {
        this.parentWidget = widget;
    }
    
    /**
     * Adds a widget to the parent
     * Also replaces it, but it is cleaner to have a seperate 
     * call for that (that is assuming the widgets are already there
     * by default..)
     * 
     * @param widget
     * @param field - the fieldAlias
     */
    public void addWidget(Object widget, String field)
    {
        if (widgets == null)
        {
            widgets = new WidgetList();
        }
        ((Widget)widget).setPart(this);
        widgets.add(widget);
    }
    
    /** 
     * Replaces the field with the specified widget
     * @param widget
     * @param field
     */
    public void setWidget(Object widget, String field)
    {
        int index = widgets.indexOf(field);
        if (index != -1)
        {
            widgets.set(index, widget);
        }
        else
        {
            widgets.add(widget);
        }
    }
    
    public Object getRootWidget()
    {
        return parentWidget;
    }
    
    /**
     * Initialize the part, which makes the gui visible,
     * processes fields, etc.
     * Can only be called from the DefaultPartRule
     * TODO: This is pretty horrible way of doing things
     *       Better ideas appreciated..
     * @param caller - the caller
     */
    public void initialize(Object caller)
    {
        System.err.println("Initialize is called..");
        if (!(caller instanceof DefaultPartRule))
        {
            return;
        }
        Iterator it = widgets.iterator();
        while (it.hasNext())
        {
            Widget widget = (Widget) it.next();
            System.out.println("Next ; "+widget.getField());
            // getting widget..
        }
        if (runIndex == 2) throw new RuntimeException("Hoi");
        runIndex++;
        
    }
    
    /**
     * Registers an ApplicationPart rule
     */
    public void registerRule(IRule rule)
    {
        // the defaultrule should stay last..
        partRules.add(partRules.size(), rule);
        rule.registerPartName(this.getName());
    }
    
    public void registerFieldRule(IRule rule, String field)
    {
        Object tmp = widgets.get(field);
        if (tmp instanceof Widget)
        {
            Widget widget = (Widget) tmp;
            widget.registerRule(rule);
        }
    }
    
    /**
     * Activates the part
     * Which means setting starting rule processing
     * The defaultrule should initialize the view.
     */
    public void activate()
    {
        if (activated)
        {
            return;
        }
        activated = true;
        if (getRules() == null)
        {
            System.err.println("No part rules to process");
        }
        Iterator it = widgets.iterator();
        System.out.println("Rules : "+getRules());
        while (it.hasNext())
        {
            Widget widget = (Widget) it.next();
            if (getRootWidget() instanceof JPanel)
            {
                ((JPanel)parentWidget).add((Component)widget.getNativeWidget());
                PartRequestImpl req = new PartRequestImpl(widget.getField(), this, PartRequest.NO_ACTION);
                ApplicationContext.fireFieldRequest(widget,req, ApplicationContext.PRE_REQUEST);
            }
        }
    }
    
    /**
     * Returns the part rules
     */
    public ArrayList getRules()
    {
        initializePartRules();
        return partRules;
    }
    
    /** 
     * Initializes the partrules
     * and adds the defaultrule.
     */
    private void initializePartRules()
    {
        if (partRules == null)
        {
            partRules = new ArrayList();
            // add the default rule..
            partRules.add(new DefaultPartRule());
        }
    }
        
    
    public ArrayList getRules(String field)
    {
        return null;
    }
    
    /**
     * Override arraylist so equals actually works..
     */
    public class WidgetList extends ArrayList
    {
        
            /**
         * @see java.util.List#indexOf(Object)
         */
        public int indexOf(Object elem)
        {
            if (elem == null)
            {
                return -1;
            }
            for (int i = 0; i < size(); i++)
            {
                Object data = get(i);
                if (data != null && data.equals(elem))
                {
                    return i;
                }
            }
            return -1;
            
        }
        
        public Widget get(String field)
        {
            int index = indexOf(field);
            if (index != -1)
            {
                return (Widget) get(index);
            }
            return null;
        }
    }
    
    public WidgetList getWidgets()
    {
        return widgets;
    }
    
    /**
     * Returns the session.
     * @return SessionPart
     */
    public SessionPart getSession()
    {
        if (session == null)
        {
            session = new SessionPart();
        }
        return session;
    }
    
    /**
     * Clears all fields
     */
    public void clear()
    {
        Iterator it = widgets.iterator();
        while (it.hasNext())
        {
            clear(((Widget)it.next()).getField());
        }
    }
    
    /**
     * Clears the specified field
     */
    public void clear(String field)
    {
        Widget widget = widgets.get(field);
        if (widget instanceof ValueWidget)
        {
            ((ValueWidget)widget).setValue(getBeanValue(widget.getField()));
        }
    }
    
    /** 
     * Resets all fields to the original value
     */
    public void reset()
    {
        Iterator it = widgets.iterator();
        while (it.hasNext())
        {
            reset(((Widget)it.next()).getField());
        }
    }
    
    /**
     * Resets the specified field to the original value
     */
    public void reset(String field)
    {
        Widget widget = widgets.get(field);
        if (widget instanceof ValueWidget)
        {
            ((ValueWidget)widget).setValue(null);
        }
    }
        
    
    /** 
     * Destroys the applicationPart
     * (does a lot of cleanups)
     * After the destroy the object is not usable anymore.
     * Maybe a lot of unecessary code, but you never know
     * when swing stuff still stays in memory..
     */
    public void destroy()
    {
        if (session != null)
        {
            getSession().clear();
            session = null;
        }
        mapping = null;
        bean = null;
        parentWidget = null;
        activated = false;
        Iterator it = widgets.iterator();
        while (it.hasNext())
        {
            ((Widget)it.next()).destroy();
            // and remove from list..
            it.remove();
        }
        widgets.clear();
        widgets = null;
        it = partRules.iterator();
        while (it.hasNext())
        {
            ((IRule)it.next()).destroy();
            it.remove();
        }
        partRules.clear();
        partRules = null;
    }
}
