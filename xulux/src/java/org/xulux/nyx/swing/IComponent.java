package org.xulux.nyx.swing;

import java.awt.Window;
import javax.swing.JComponent;

/**
 * This is the generic interface for components.
 * The goal of IComponent is that the exposure of methods
 * that swing is not there anymore.
 * The problem of this interface is, that it is very Swing oriented.
 * 
 * @author <a href="martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IComponent.java,v 1.1 2002-10-23 22:34:38 mvdb Exp $
 */
public interface IComponent
{
    /**
     * Returns the component
     */
    public JComponent getComponent();
    
    /**
     * Contains the logic for adding the component
     * to the component specified
     * @param component
     */ 
    public void addToComponent(JComponent component);
    
    /**
     * Contains the logic for adding the component
     * to the window specified.
     * For now the choice has been made to use java.awt.Window
     * Since JDialog as well as JWindow inherit from this
     * When the need is there, there will be more seperation
     * 
     * @param window the window to add the component to
     */
    public void addToWindow(Window window);

}
