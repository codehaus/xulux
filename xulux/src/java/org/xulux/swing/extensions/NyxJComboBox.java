/*
   $Id: NyxJComboBox.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
   
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
package org.xulux.swing.extensions;

import java.awt.Component;
import java.awt.event.FocusListener;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.xulux.gui.NyxListener;

/**
 * This overrides the default JComboBox.
 * The problem it solves is that when setting
 * a new model in the NyxCombo class, it would fire
 * an action event, which would trigger another
 * firing of all pre requests (and possibly nulling values!)
 * This prevents that situation.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxJComboBox.java,v 1.2 2004-01-28 15:09:25 mvdb Exp $
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
