package org.xulux.gui.events;

import org.xulux.gui.Widget;
import org.xulux.gui.XuluxEvent;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SetValueWithoutRefreshEvent.java,v 1.1 2004-01-28 12:24:00 mvdb Exp $
 */
public class SetValueWithoutRefreshEvent implements XuluxEvent {

    /**
     * 
     */
    public SetValueWithoutRefreshEvent() {
        super();
    }

    /**
     * @see org.xulux.gui.XuluxEvent#getWidget()
     */
    public Widget getWidget() {
        return null;
    }

}
