package org.xulux.nyx.gui.rules;

import org.xulux.nyx.context.PartRequest;
import org.xulux.nyx.gui.IContentWidget;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.swing.widgets.PersonCollection;
import org.xulux.nyx.rules.Rule;

/**
 * 
 * @author Martin van den Bemt
 * @version $Id: TableContentRule.java,v 1.1 2003-11-20 11:48:50 mvdb Exp $
 */
public class TableContentRule extends Rule {

    /**
     * 
     */
    public TableContentRule() {
        super();
    }

    /**
     * @see org.xulux.nyx.rules.IRule#pre(org.xulux.nyx.context.PartRequest)
     */
    public void pre(PartRequest request) {

    }

    /**
     * @see org.xulux.nyx.rules.IRule#post(org.xulux.nyx.context.PartRequest)
     */
    public void post(PartRequest request) {
        IContentWidget w = (IContentWidget)request.getWidget("SimpleTable");
        Widget widget = request.getWidget("ContentButton");
        Object content = w.getContent();
        if (content == null) {
            // set the content..
            w.setContent(((PersonCollection)request.getPart().getBean()).getPersons());
            widget.setProperty("text", "Clear Content");
        } else {
            w.setContent(null);
            widget.setProperty("text", "Set Content");
        }
        w.contentChanged();

    }

}
