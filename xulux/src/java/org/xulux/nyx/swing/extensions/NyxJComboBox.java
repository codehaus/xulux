/*
 $Id: NyxJComboBox.java,v 1.2 2003-11-27 19:39:19 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.swing.extensions;

import java.awt.Component;
import java.awt.event.FocusListener;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.xulux.nyx.gui.NyxListener;

/**
 * This overrides the default JComboBox.
 * The problem it solves is that when setting
 * a new model in the NyxCombo class, it would fire
 * an action event, which would trigger another
 * firing of all pre requests (and possibly nulling values!)
 * This prevents that situation.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJComboBox.java,v 1.2 2003-11-27 19:39:19 mvdb Exp $
 */
public class NyxJComboBox extends JComboBox {

    /**
     * is a new model set or not ?
     */
    private boolean newModelIsSet = false;

    /**
     * Constructor for NyxComboBox.
     */
    public NyxJComboBox() {
        super();
    }

    /**
     * Set a setting so that we now a new model is set
     * and no action events should be fired
     * @param model - the model
     */
    public void setModel(ComboBoxModel model) {
        newModelIsSet = true;
        super.setModel(model);
        newModelIsSet = false;
    }
    /**
     * Only calls the selectedItem when we are not
     * setting a new model
     * @param object - the selectedItem
     */
    public void setSelectedItem(Object object) {
        if (!newModelIsSet) {
            super.setSelectedItem(object);
        }
    }

    /**
     * Adds a nyx focuslistener to the currently known children
     *
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void addFocusListener(FocusListener l) {
        if (l instanceof NyxListener) {
            int childCount = getComponentCount();
            for (int i = 0; i < childCount; i++) {
                Component comp = getComponent(i);
                comp.addFocusListener(l);
            }
        }
        super.addFocusListener(l);
    }

    /**
     * Removes a nyx focuslistener from the currently known children
     * @see java.awt.Component#removeFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void removeFocusListener(FocusListener l) {
        if (l instanceof NyxListener) {
            int childCount = getComponentCount();
            for (int i = 0; i < childCount; i++) {
                Component comp = getComponent(i);
                comp.removeFocusListener(l);
            }
        }
        super.removeFocusListener(l);
    }

    /**
     * @return if the new model is set
     */
    public boolean isNewModelIsSet() {
        return newModelIsSet;
    }

}
