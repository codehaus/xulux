/*
 $Id: Widget.java,v 1.9 2002-11-11 09:49:22 mvdb Exp $

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

package org.xulux.nyx.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.rules.IRule;

/**
 * A widget is a independ way of representing gui objects. 
 * For now swing specific, we need an xml config somewhere
 * to say how we should initialize it, etc.
 * 
 * TODO: Use introspection, because this is getting to big and too
 * specific as a generic Widget... 
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Widget.java,v 1.9 2002-11-11 09:49:22 mvdb Exp $
 */
public abstract class Widget
{
    
    private String field;
    private boolean enabled = true;
    private boolean visible = true;
    private boolean immidiate = false;
    protected boolean initialized = false;
    private boolean skip = false;
    
    private ArrayList rules;
    private ApplicationPart part;
    
    private WidgetRectangle rectangle;
    
    private String name;
    
    private String text;
    
    protected HashMap properties;
    
    /**
     * Specfies if the widget is refreshing
     * so you call setter from refresh
     * without getting in an infinite loop 
     */
    protected boolean isRefreshing;
    
        
    public Widget(String name)
    {
        setName(name);
    }
    
    public void setPart(ApplicationPart part)
    {
        this.part = part;
    }
    
    public ApplicationPart getPart()
    {
        return this.part;
    }
    
    /**
     * No state change should take place if the widget is already in a certain state.
     * 
     * @param enable false disables it, true, enables it
     */
    public void setEnable(boolean enable)
    {
        if (this.enabled != enable)
        {
            this.enabled = enable;
            refresh();
        }
        this.enabled = enable;
    }
    
    /**
     * Is the widget disabled ? 
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * No state change should take place if the widget is already in a certain state.
     * @param show true - shows the widget
     */
    public void setVisible(boolean visible)
    {
        if (this.visible != visible)
        {
            this.visible = visible;
            refresh();
        }
        this.visible = visible;
    }
    
    /**
     * Is the widget hidden
     */
    public boolean isVisible()
    {
        return this.visible;
    }
    /**
     * Sets if the widget is immidiate 
     * Immidiate means that it fires actions on everything you do 
     * in the widget 
     * Eg : in an entry field typing a letter will automacially fire an event..
     */
    public void setImmidiate(boolean immidiate)
    {
        if (this.immidiate != immidiate)
        {
            this.immidiate = immidiate;
            refresh();
        }
        this.immidiate = immidiate;
    }

    /**
     * Is a widget immidiate  ? 
     */
    public boolean isImmidiate()
    {
        return this.immidiate;
    }
    
    /**
     * @return if this field should be skipped
     */
    public boolean isSkip()
    {
        return this.skip;
    }
    
    /**
     * When tabbing to the screen, don't give focus to this 
     * widget, only allow selection via mouse
     * @param skip
     */
    public void setSkip(boolean skip)
    {
        if (this.skip != skip)
        {
            this.skip = skip;
            refresh();
        }
        this.skip = skip;
    }
    
    /**
     * Destroy a widget. Frees up all resources taken by it.
     */
    public abstract void destroy();
    
    /**
     * Returns the "native" widget. could be a swing, swt or other type of component
     */
    public abstract Object getNativeWidget();
    
    /**
     * Initializes the widget, based on the current settings
     */
    public abstract void initialize();
    
    /**
     * sets the position of the current widget
     * (on the parent..)
     */
    public void setPosition(int x, int y)
    {
        if (rectangle == null)
        {
            rectangle = new WidgetRectangle();
        }
        rectangle.setPosition(x,y);
    }
    /**
     * Sets the size of the current widget
     */
    public void setSize(int width, int height)
    {
        if (rectangle == null)
        {
            rectangle = new WidgetRectangle();
        }
        rectangle.setSize(width, height);
    }
    
    public WidgetRectangle getRectangle()
    {
        if (this.rectangle == null)
        {
            this.rectangle = new WidgetRectangle();
        }
        return this.rectangle;
    }
    
    /**
     * refreshes the widget.
     */
    public abstract void refresh();

    /**
     * Returns the field.
     * @return String
     */
    public String getField()
    {
        return field;
    }
        
    public void registerRule(IRule rule)
    {
        if (rules == null)
        {
            rules = new ArrayList();
        }
        rules.add(rule);
    }
    
    public ArrayList getRules()
    {
        return rules;
    }
    
    /** 
     * To make equals checking a bit easier
     * @param object (normally a string)
     */
    public boolean equals(Object object)
    {
        if (object instanceof String)
        {
            return getName().equals(object.toString());
        }
        else
        {
            // if not a string, we want the exact same object given to us.
            return object == this;
        }
    }
    
    /**
     * Override this method when the widget can contains child widgets
     * @return
     */
    public boolean canContainChildren()
    {
        return false;
    }
    
    /** 
     * Doesn't do anything
     */
    public void addChildWidget(Widget widget)
    {
        return;
    }
    
    public ArrayList getChildWidgets()
    {
        return null;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public String getText()
    {
        return this.text;
    }
    
    public void setProperty(String key, String value)
    {
        if (properties == null)
        {
            properties = new HashMap();
        }
        properties.put(key, value);
        // refresh the widget when it is initialized
        if (initialized)
        {
            refresh();
        }
    }
    
    public HashMap getProperties()
    {
        return properties;
    }
    
    /**
     * Sets the field.
     * @param field The field to set
     */
    public void setField(String field)
    {
        this.field = field;
    }
    
    protected void removeAllRules()
    {
        if (rules != null)
        {
            rules.clear();
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }
    
    public Object getValue()
    {
        return null;
    }
    
    public void setValue(Object object)
    {
    }
    
    /**
     * Clears the field
     */
    public void clear()
    {
    }
        

}
