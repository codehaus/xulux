/*
 $Id: LockedColumnSelectionListener.java,v 1.1 2003-11-12 02:53:34 mvdb Exp $

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
package org.xulux.nyx.swing.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.xulux.nyx.swing.widgets.Table;

/**
 * This class takes care of selecting the same row in the lockedTable as 
 * in the normal table. Also works the other wat around.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LockedColumnSelectionListener.java,v 1.1 2003-11-12 02:53:34 mvdb Exp $
 */
public class LockedColumnSelectionListener implements ListSelectionListener {
    
    /**
     * the initializer of the listener
     */
    protected Table table;
    /**
     * is the component in a refresh or not
     */
    protected boolean refreshing;
    
    /**
     * 
     */
    public LockedColumnSelectionListener(Table table) {
        this.table = table;
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @todo Also support more than one line selections and also row selections. 
     */
    public void valueChanged(ListSelectionEvent e) {
        if (isRefreshing()) {
            refreshing = false;
            return;
        }
        Object source = e.getSource();
        //System.out.println("Source : "+source.hashCode());
        if (source == table.getJTable().getSelectionModel()) {
            int selectedRow = table.getJTable().getSelectedRow();
            refreshing = true;
            table.getLockedJTable().setRowSelectionInterval(selectedRow, selectedRow);
        } else if (source == table.getLockedJTable().getSelectionModel()) {
            int selectedRow = table.getLockedJTable().getSelectedRow();
            refreshing = true;
            table.getJTable().setRowSelectionInterval(selectedRow,selectedRow);
        }
    }
    
    /**
     * 
     * @return if the valuechanged has been called already, so we shouldn't 
     *         process anything when the value changes on the target table.
     */
    protected boolean isRefreshing() {
        return refreshing;
    }

}
