package org.xulux.gui.strategies;

import java.util.Iterator;

import org.xulux.core.ApplicationPart;
import org.xulux.core.XuluxContext;
import org.xulux.gui.IInvalidValueStrategy;
import org.xulux.gui.Widget;

/**
 * The Xulux default strategy.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SaveActionInvalidValueStrategy.java,v 1.1 2004-06-30 11:59:01 mvdb Exp $
 */
public class SaveActionInvalidValueStrategy implements IInvalidValueStrategy {

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#checkForm(org.xulux.core.ApplicationPart)
     */
    public boolean checkForm(ApplicationPart part) {
        Iterator it = part.getWidgets().iterator();
        while (it.hasNext()) {
            Widget w = (Widget) it.next();
            boolean process = false;
            if (w.isRequired() && (w.canContainValue() && w.isValueEmpty()) || !w.isValidValue()) {
                XuluxContext.getGuiDefaults().getXuluxToolkit().beep();
                w.focus();
                return false;
            }
        }
        return true;
    }

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#checkWidget(org.xulux.gui.Widget)
     */
    public boolean checkWidget(Widget widget) {
        return true;
    }

}
