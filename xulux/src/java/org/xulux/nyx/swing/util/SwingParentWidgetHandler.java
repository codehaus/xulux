package org.xulux.nyx.swing.util;

import java.awt.Component;
import java.awt.Container;

import org.xulux.nyx.gui.IParentWidgetHandler;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingParentWidgetHandler.java,v 1.1 2003-06-17 17:02:30 mvdb Exp $
 */
public class SwingParentWidgetHandler implements IParentWidgetHandler {

    /**
     * 
     */
    public SwingParentWidgetHandler() {
    }

    /**
     * @see org.xulux.nyx.gui.ParentWidgetHandler#initialize(java.lang.Object)
     */
    public void initialize(Object parent) {

    }

    /**
     * @see org.xulux.nyx.gui.ParentWidgetHandler#destroy(java.lang.Object)
     */
    public void destroy(Object parent) {
        Container container = ((Component)parent).getParent();
        container.removeAll();
        container.remove((Component)parent);
    }

}
