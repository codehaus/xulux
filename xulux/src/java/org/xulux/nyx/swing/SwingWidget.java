/*
 $Id: SwingWidget.java,v 1.3 2003-11-06 19:53:11 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project.  For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.swing;

import javax.swing.JComponent;

import org.xulux.nyx.gui.Widget;

/**
 * A convenience class for swing widgets to override..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingWidget.java,v 1.3 2003-11-06 19:53:11 mvdb Exp $
 */
public abstract class SwingWidget extends Widget {

    /**
     * @param name
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
     * TODO: Add to programmers manual for widget programmers
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        JComponent j = (JComponent)getNativeWidget();
        j.requestFocus();
        // if widget is not showing we have
        // to make it showing..
        if (!j.isShowing() && getParent() != null) {
            // set the session variable, so controls
            // can look who requested focus..
            getPart().getSession().setValue("nyx.focusrequest", this);
            getParent().focus();
        }
        // remove session variable again.
        getPart().getSession().remove("nyx.focusrequest");
        j.requestFocus();
    }

}
