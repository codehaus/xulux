package org.xulux.gui.events;

import org.xulux.gui.Widget;
import org.xulux.gui.XuluxEvent;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SetValueEvent.java,v 1.1 2004-01-28 12:24:00 mvdb Exp $
 */
public class SetValueEvent implements XuluxEvent {

    /**
     * 
     */
    public SetValueEvent() {
        super();
    }

    /**
     * @see org.xulux.gui.XuluxEvent#getWidget()
     */
    public Widget getWidget() {
        return null;
    }

}
