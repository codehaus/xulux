/*
 $Id: DefaultComboModel.java,v 1.4 2002-11-12 12:36:05 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.swing.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;

/**
 * The default combobox model.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultComboModel.java,v 1.4 2002-11-12 12:36:05 mvdb Exp $
 */
public class DefaultComboModel implements ComboBoxModel
{
    private ArrayList original;
    private ArrayList list;
    private ArrayList listeners;
    private ComboShowable selectedItem;
    private String field;
    private BeanMapping mapping;

    /**
     * Constructor for DefaultComboModel.
     */
    public DefaultComboModel(ArrayList list, String field)
    {
        this.field = field;
        this.original = list;
        initialize();
    }

    /**
     * @see javax.swing.ComboBoxModel#setSelectedItem(Object)
     */
    public void setSelectedItem(Object anItem)
    {
        this.selectedItem = (ComboShowable)anItem;
        
    }
    
    public void setSelectedItem(int index)
    {
        this.selectedItem = (ComboShowable) list.get(index);
    }

    /**
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     */
    public Object getSelectedItem()
    {
        return selectedItem;
    }

    /**
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize()
    {
        return list.size();
    }

    /**
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index)
    {
        return list.get(index);
    }

    /**
     * @see javax.swing.ListModel#addListDataListener(ListDataListener)
     */
    public void addListDataListener(ListDataListener l)
    {
        if (listeners == null)
        {
            listeners = new ArrayList();
        }
        listeners.add(l);
    }

    /**
     * @see javax.swing.ListModel#removeListDataListener(ListDataListener)
     */
    public void removeListDataListener(ListDataListener l)
    {
        if (listeners != null)
        {
            listeners.remove(l);
        }
    }
    
    public Object getRealSelectedValue()
    {
        return original.get(selectedItem.getIndex());
    }
    
    public Object getComboObject(int index)
    {
        return list.get(index);
    }
        
    
    public void setRealSelectedValue(Object selectedItem)
    {
        int index = original.indexOf(selectedItem);
        if (index != -1)
        {
            setSelectedItem(list.get(index));
            // probably need to fire some events here !
        }
    }
    
    private void initialize()
    {
        
        if (field != null && original.size() > 1)
        {
            mapping = Dictionary.getInstance().getMapping(original.get(2).getClass());
        }
        list = new ArrayList();
        for (int i=0; i < original.size(); i++)
        {
            Object object = original.get(i);
            if (field == null || mapping == null || i == 0)
            {
                list.add(new ComboShowable(i, object.toString()));
            }
            else
            {
                StringTokenizer stn = new StringTokenizer(field, ",");
                String value = "";
                while (stn.hasMoreTokens())
                {
                    String token = stn.nextToken();
                    String strPart = "";
                    if (!token.trim().equals(""))
                    {
                        strPart = mapping.getField(token).getValue(object).toString();
                    }
                    else
                    {
                        //allow seperators in the field list.
                        strPart = token;
                    }
                    value+=strPart;
                }
                list.add(new ComboShowable(i, value));
            }
        }
    }
    
    /**
     * Holds all items in a list
     */
    public class ComboShowable
    {
        private int index;
        private String value;
        
        public ComboShowable(int index, String value)
        {
            this.index = index;
            this.value = value;
        }
        
        public String toString()
        {
            return this.value;
        }
        
        public int getIndex()
        {
            return index;
        }
    }
    
}
