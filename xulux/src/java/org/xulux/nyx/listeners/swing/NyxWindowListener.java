package org.xulux.nyx.listeners.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.listeners.NyxListener;

/**
 * A WindowListener to make sure we pass control 
 * back to the main application when someone hits
 * the X button.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxWindowListener.java,v 1.1 2003-01-25 23:17:57 mvdb Exp $
 */
public class NyxWindowListener extends NyxListener
implements WindowListener
{

    /**
     * Constructor for NyxWindowListener.
     */
    public NyxWindowListener()
    {
        super();
    }

    /**
     * Constructor for NyxWindowListener.
     * @param widget
     */
    public NyxWindowListener(Widget widget)
    {
        super(widget);
    }

    /**
     * @see java.awt.event.WindowListener#windowActivated(WindowEvent)
     */
    public void windowActivated(WindowEvent e)
    {
        System.out.println("Window activated : "+e);
    }

    /**
     * @see java.awt.event.WindowListener#windowClosed(WindowEvent)
     */
    public void windowClosed(WindowEvent e)
    {
        System.out.println("Window closed : "+e);
        if (getWidget().isRootWidget())
        {
            getWidget().destroy();
        }
    }

    /**
     * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
     */
    public void windowClosing(WindowEvent e)
    {
        System.out.println("Window closing : "+e);
    }

    /**
     * @see java.awt.event.WindowListener#windowDeactivated(WindowEvent)
     */
    public void windowDeactivated(WindowEvent e)
    {
        System.out.println("Window deactivated: "+e);
        if (getWidget().isRootWidget())
        {
            completed();
            getWidget().destroy();
        }
    }

    /**
     * @see java.awt.event.WindowListener#windowDeiconified(WindowEvent)
     */
    public void windowDeiconified(WindowEvent e)
    {
        System.out.println("Window deIconified : "+e);
    }

    /**
     * @see java.awt.event.WindowListener#windowIconified(WindowEvent)
     */
    public void windowIconified(WindowEvent e)
    {
        System.out.println("Window iconified: "+e);
    }

    /**
     * @see java.awt.event.WindowListener#windowOpened(WindowEvent)
     */
    public void windowOpened(WindowEvent e)
    {
        System.out.println("Window opened : "+e);
    }

}
