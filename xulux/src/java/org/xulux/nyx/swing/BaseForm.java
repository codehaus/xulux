/*
 $Id: BaseForm.java,v 1.3.2.1 2003-04-29 16:52:44 mvdb Exp $

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

package org.xulux.nyx.swing;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.xulux.nyx.utils.Resources;

/**
 * The BaseForm 
 * 
 * @author <a href="martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BaseForm.java,v 1.3.2.1 2003-04-29 16:52:44 mvdb Exp $
 */
public abstract class BaseForm implements IComponent
{
    /**
     * Specifies wheather to use an internal form or not
     * Default is not to use it.
     */
    public static int USEINTERNALFORM = 1;

    /** 
     * The component
     * Allows for setting in a subclass
     */
    protected  JComponent component;
    /**
     * Holds the parent component when it is added
     */
    private Component parent;

    /**
     * Constructor for BaseForm.
     */
    public BaseForm()
    {
    }

    public BaseForm(JComponent component)
    {
        setComponent(component);
    }

    /**
     * @see org.xulux.nyx.swing.IComponent#addToComponent(JComponent)
     */
    public void addToComponent(JComponent component)
    {
        if (getComponent() != null)
        {
            component.add(getComponent());
        }
    }

    /**
     * Adds the current component to the specified window 
     * 
     * @see org.xulux.nyx.swing.IComponent#addToWindow(Window)
     * @throws UnsupportedOperationException - when the window is not of type 
     *          JWindow, JFrame or JDialog 
     */
    public void addToWindow(Window window)
    {
        if (getComponent() != null)
        {
            if (window instanceof JWindow)
            {
                ((JWindow) window).getContentPane().add(getComponent());
            }
            else if (window instanceof JFrame)
            {
                ((JFrame) window).getContentPane().add(getComponent());
            }
            else if (window instanceof JDialog)
            {
                ((JDialog) window).getContentPane().add(getComponent());
            }
            else
            {
                throw new UnsupportedOperationException(Resources.getResource(this, "INVALID_WINDOW")); //$NON-NLS-1$
            }
        }
    }

    /**
     * @see org.xulux.nyx.swing.IComponent#getComponent()
     */
    public JComponent getComponent()
    {
        if (this.component == null)
        {
            createComponent();
        }
        return this.component;
    }
    
    /**
     * Is called when the component is not yet created
     */
    protected void createComponent()
    {
        this.component = new JPanel();
    }

    /** 
     * Sets the Form to the specified component
     * 
     * TODO : See if private will work correctly.
     * @param component
     */
    private void setComponent(JComponent component)
    {
        this.component = component;
    }

    /**
     * Disposes the current component and removes all references 
     * to the parent.
     */
    public void dispose()
    {
        if (getComponent() != null)
        {
            if (parent instanceof Window)
            {
                ((Window) parent).remove(getComponent());
            }
            else if (parent instanceof JComponent)
            {
                ((JComponent) parent).remove(getComponent());
            }
        }
        this.parent = null;
    }
}
