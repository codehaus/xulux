package org.xulux.nyx.gui;

/**
 * This interface can be used to clear or initialize 
 * parent widgets. This way no specific gui code
 * when eg destroying a part will be necessary.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: IParentWidgetHandler.java,v 1.1 2003-06-17 17:02:30 mvdb Exp $
 */
public interface IParentWidgetHandler {
    
    /**
     * Initializes the parent widget
     * 
     * @param parent
     */
    public void initialize(Object parent);
    
    /**
     * Destroys the parent
     * 
     * @param parent
     */
    public void destroy(Object parent);

}
