/*
 $Id: DefaultComboModel.java,v 1.30 2003-12-17 00:59:55 mvdb Exp $

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
package org.xulux.nyx.swing.models;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.swing.widgets.Combo;

/**
 * The default combobox model.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultComboModel.java,v 1.30 2003-12-17 00:59:55 mvdb Exp $
 */
public class DefaultComboModel extends AbstractListModel implements ComboBoxModel {
    /**
     * The original list
     */
    private List original;
    /**
     * the used list
     */
    private ArrayList list;
    /**
     * The selected item
     */
    private ComboShowable selectedItem;
    /**
     * the field to use
     */
    private String field;
    /**
     * the beanmapping
     */
    private BeanMapping mapping;
    /**
     * the combo where this model is used for
     */
    private Combo combo;
    /**
     * If the model has been initialized
     */
    private boolean initialized;

    /**
     * don't do anything, since we don't have any data..
     * It will create an empty list however
     */
    public DefaultComboModel() {
        this.list = new ArrayList();
    }

    /**
     * Constructor for DefaultComboModel.
     *
     * @param list the list with data
     * @param field the field to use
     * @param combo the combo used
     */
    public DefaultComboModel(List list, String field, Combo combo) {
        this.field = field;
        this.original = list;
        this.combo = combo;
        initialize();
    }

    /**
     * When using the arrow keys to select another
     * item from the combo, we have to fire an event
     * with the index0 and the index1 with the value -1.
     * This way you can actually see the selectedItem change
     * on screen. If you don't fire this event, the selectedItem
     * (onscreen!) will stay on the selected item on creation
     * of the combo.
     *
     * @see javax.swing.ComboBoxModel#setSelectedItem(Object)
     */
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof ComboShowable) {
            this.selectedItem = (ComboShowable) anItem;
            if (initialized && !combo.isRefreshing()) {
                combo.setLazyValue(getRealSelectedValue());
            }
        } else {
            setRealSelectedValue(anItem);
        }
        fireContentsChanged(this, -1, -1);
    }

    /**
     * Set the selected item to the specified index
     * @param index the index to select
     */
    public void setSelectedItem(int index) {
        if (list.size() > 0) {
            if (!(index >= list.size())) {
                this.selectedItem = (ComboShowable) list.get(index);
            }
        }
    }

    /**
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     */
    public Object getSelectedItem() {
        return selectedItem;
    }

    /**
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /**
     * The comboshowable which is requested by the JCombo
     * object.
     *
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
        return list.get(index);
    }

    /**
     * @return the real selected value in the combo, so not a comboshowable
     */
    public Object getRealSelectedValue() {
        Object retValue = null;
        if (original != null && selectedItem != null) {
            retValue = original.get(selectedItem.getIndex());
        } else if (original != null && original.size() > 0) {
            retValue = original.get(0);
        }
        return retValue;
    }

    /**
     * @return the selected index in the model
     *          If the selection is null, it will return -1
     */
    public int getSelectedIndex() {
        if (selectedItem == null) {
            return -1;
        } else {
            return selectedItem.getIndex();
        }
    }

    /**
     * Set the real selected item (so not the comboshowable.
     *
     * @param selectedItem the real selected item
     */
    public void setRealSelectedValue(Object selectedItem) {
        if (original == null) {
            return;
        }
        int index = original.indexOf(selectedItem);
        //        System.err.println("index : "+index);
        //        System.err.println("selectedItem : "+selectedItem.getClass());
        //        System.err.println("selectedItem : "+selectedItem);
        //        System.err.println("original : "+original);
        if (index != -1) {
            setSelectedItem(list.get(index));
        } else {
            setSelectedItem(0);
            combo.setLazyValue(getRealSelectedValue());
        }
    }

    /**
     * Initialize the model and the data
     */
    protected void initialize() {
        if (field != null && original.size() > 0) {
            int startMappingAt = 0;
            if (original.get(0).equals(combo.getNotSelectedValue())) {
                System.out.println("StartMapping...");
                startMappingAt = 1;
            }
            if ((startMappingAt == 1 && original.size() > 1) || startMappingAt == 0) {
                Class tmpClazz = original.get(startMappingAt).getClass();
                mapping = Dictionary.getInstance().getMapping(tmpClazz);
            }
        }
        list = new ArrayList();
        for (int i = 0; i < original.size(); i++) {
            Object object = original.get(i);
            if (object != null && (i == 0 && object.equals(combo.getNotSelectedValue())) || this.field == null) {
                list.add(new ComboShowable(i, object.toString()));
            } else {
                String value = "";
                StringTokenizer stn = new StringTokenizer(field, ",");
                while (stn.hasMoreTokens()) {
                    String token = stn.nextToken();
                    String strPart = "";
                    IField iField = mapping.getField(token);
                    if (iField != null) {
                        strPart = iField.getValue(object).toString();
                    } else {
                        //allow seperators in the field list.
                        strPart = token;
                    }
                    value += strPart;
                }
                list.add(new ComboShowable(i, value));
            }
        }
        initialized = true;
    }

    /**
     * Clean up combomodel (if at all needed)
     */
    public void destroy() {
        this.combo = null;
        this.list = null;
        this.mapping = null;
        this.original = null;
        this.field = null;
        this.selectedItem = null;
    }

    protected List getOriginal() {
        return this.original;
    }
    
    protected List getList() {
        return this.list;
    }
    /**
     * Holds all items in a list
     */
    public class ComboShowable {
        /**
         * the index
         */
        private int index;
        /**
         * the value
         */
        private String value;

        /**
         * The main constructor
         * @param index the index in the original data
         * @param value the value that needs to be shown
         */
        public ComboShowable(int index, String value) {
            this.index = index;
            this.value = value;
        }

        /**
         * Since a combo is using the toString value of an object,
         * this will return the value set in the constructor
         *
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return this.value;
        }

        /**
         * @return the index in the original data
         */
        public int getIndex() {
            return index;
        }
    }
}
