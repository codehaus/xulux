package org.xulux.nyx.swing;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;

import org.xulux.nyx.utils.Resources;

/**
 * The BaseForm 
 * 
 * @author <a href="martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BaseForm.java,v 1.2 2002-10-23 22:34:38 mvdb Exp $
 */
public abstract class BaseForm implements IComponent
{

    /** 
     * The component
     */
    private JComponent component;
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
        return this.component;
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
