package org.xulux.gui.strategies;

import java.util.Iterator;

import org.xulux.core.ApplicationPart;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.InvalidValueException;
import org.xulux.gui.IInvalidValueStrategy;
import org.xulux.gui.Widget;

/**
 * The Xulux default strategy.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SaveActionInvalidValueStrategy.java,v 1.3 2004-07-12 13:02:41 mvdb Exp $
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

    /**
     * @see org.xulux.gui.IInvalidValueStrategy#handleInvalidValueException(org.xulux.gui.Widget, org.xulux.dataprovider.InvalidValueException)
     */
    public boolean handleInvalidValueException(Widget widget, InvalidValueException exception) {
      return true;
    }

}
