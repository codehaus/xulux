/*
   $Id: Combo.java,v 1.4 2004-05-12 12:27:48 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import org.xulux.gui.NyxCombo;
import org.xulux.gui.NyxListener;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.extensions.NyxJComboBox;
import org.xulux.swing.listeners.ImmidiateListener;
import org.xulux.swing.listeners.PrePostFieldListener;
import org.xulux.swing.models.DefaultComboModel;
import org.xulux.swing.util.NyxEventQueue;

/**
 * The swing combo widget.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Combo.java,v 1.4 2004-05-12 12:27:48 mvdb Exp $
 */
public class Combo extends NyxCombo {
    /**
     * The native combobox
     */
    private NyxJComboBox combo;
    /**
     * The key listener
     */
    private KeyListener keyListener;
    /**
     * the combo model
     */
    private DefaultComboModel model;
    /**
     * the actionlistener
     */
    private PrePostFieldListener actionListener;
    /**
     * The focus event listener. We cannot use
     * the prepostlistener, since that would trigger
     * field processing code, we just need to release
     * the queue when focus is gained.
     */
    private FocusEventListener focusEventListener;

    /**
     * Constructor for NyxCombo.
     * @param name the name of the combo
     */
    public Combo(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return combo;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        if (combo != null) {
            if (actionListener != null) {
                combo.removeActionListener(actionListener);
                actionListener = null;
            }
            if (keyListener != null) {
                combo.removeKeyListener(keyListener);
                keyListener = null;
            }
            if (focusEventListener != null) {
                combo.removeFocusListener(focusEventListener);
                focusEventListener = null;
            }
            combo.removeAll();
            Container container = combo.getParent();
            if (container != null) {
                container.remove(combo);
            }
            combo = null;
        }
        super.destroy();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {

        if (this.initialized) {
            return;
        }
        this.initialized = true;
        String nsv = getProperty("notselectedvalue");
        if (nsv != null) {
            this.notSelectedValue = nsv;
        }
        combo = new NyxJComboBox();
        // add a focuslistener to be able to free the eventqueue..
        this.focusEventListener = new FocusEventListener();
        combo.addFocusListener(this.focusEventListener);
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        if (isRefreshing()) {
            return;
        }
        isRefreshing = true;
        initialize();
        if (isImmidiate() && keyListener == null) {
            keyListener = new ImmidiateListener(this);
            combo.addKeyListener(keyListener);
        } else if (!isImmidiate() && keyListener != null) {
            combo.removeKeyListener(keyListener);
        }
        // for now commented out. This makes the combo editable, when the
        // table columns are editable.
        //combo.setEditable(BooleanUtils.toBoolean(getProperty("editable")));
        combo.setEnabled(isEnabled());
        combo.setVisible(isVisible());
        if (contentChanged) {
            initializeContent();
            initializeNotSelectedValue();
            if (this.model != null) {
                this.model.destroy();
            }
            if (content != null) {
                this.model = new DefaultComboModel(content, getProperty("combofields"), this);
            } else {
                this.model = new DefaultComboModel();
            }
            combo.setModel(this.model);
            if (this.actionListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null) {
                    this.actionListener = (PrePostFieldListener) listener;
                } else {
                    this.actionListener = new PrePostFieldListener(this);
                }
                combo.addActionListener(this.actionListener);
            }
            contentChanged = false;
        }
        if (getValue() instanceof DefaultComboModel.ComboShowable) {
            model.setSelectedItem(value);
        } else {
            if (content != null && getValue() != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Setting value to : " + getValue());
                }
                model.setRealSelectedValue(getValue());
            } else if (model != null && content != null && getValue() == null) {
                // if we don't have a value select
                // the first one in the list
                if (log.isTraceEnabled()) {
                    log.trace("Select the first one in the list");
                }
                if (!content.isEmpty()) {
                    model.setSelectedItem(0);
                    if (getField() == null) {
                        this.value = model.getRealSelectedValue();
                    } else {
                        setValue(model.getRealSelectedValue(), false);
                    }
                }
            }
            if (model != null && model.getSelectedIndex() == 0 && contentChanged) {
                this.value = model.getRealSelectedValue();
            }
        }
        String backgroundColor = null;
        if (isRequired() && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        } else {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            combo.setBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        isRefreshing = false;
    }
    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        combo.requestFocus();
    }
    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        if (this.model != null) {
          Object object = this.model.getRealSelectedValue();
          return this.model.getRealSelectedValue();
        } else {
          return null;
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        if (getGuiValue() == null || getGuiValue().equals("") || getGuiValue().equals(getNotSelectedValue())) {
            return true;
        }
        return false;
    }

    /**
     * This is a hack to get a focusevent for the combobox.
     * I need to handle post rules correctly..
     */
    public final class FocusEventListener extends NyxListener implements FocusListener {
        /**
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
        public void focusGained(FocusEvent e) {
            // free up the eventqueue when combo gains focus..
            NyxEventQueue q = NyxEventQueue.getInstance();
            if (q != null) {
                q.holdEvents(false);
            }
        }

        /**
         * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
         */
        public void focusLost(FocusEvent e) {
        }

    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#contentChanged()
     */
    public void contentChanged() {
        contentChanged = true;
    }

}
