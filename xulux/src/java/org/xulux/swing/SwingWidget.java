/*
   $Id: SwingWidget.java,v 1.4 2004-07-19 22:07:32 mvdb Exp $
   
   Copyright 2002-2004 The Xulux Project

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.xulux.swing;

import javax.swing.JComponent;

import org.xulux.gui.Widget;

/**
 * A convenience class for swing widgets to override..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingWidget.java,v 1.4 2004-07-19 22:07:32 mvdb Exp $
 */
public abstract class SwingWidget extends Widget {

    /**
     * @param name the swingwidget name
     */
    public SwingWidget(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public abstract void destroy();

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public abstract Object getNativeWidget();

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public abstract void initialize();

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public abstract void refresh();

    /**
     * @todo Add to programmers manual for widget programmers
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        JComponent j = (JComponent) getNativeWidget();
        isRefreshing = true;
        j.requestFocus();
        isRefreshing = false;
        // if widget is not showing we have
        // to make it showing..
        if (!j.isShowing() && getParent() != null) {
            // set the session variable, so controls
            // can look who requested focus..
            getPart().getSession().setValue("nyx.focusrequest", this);
            getParent().focus();
            // remove session variable again.
            getPart().getSession().remove("nyx.focusrequest");
            j.requestFocus();
        }
    }

}
