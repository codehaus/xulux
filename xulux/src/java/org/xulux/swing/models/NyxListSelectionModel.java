/*
 $Id: NyxListSelectionModel.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $

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
package org.xulux.swing.models;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxListSelectionModel.java,v 1.2 2003-12-18 01:18:06 mvdb Exp $
 */
public class NyxListSelectionModel implements ListSelectionModel {

    /**
     *
     */
    public NyxListSelectionModel() {
        super();
    }

    /**
     * @see javax.swing.ListSelectionModel#setSelectionInterval(int, int)
     */
    public void setSelectionInterval(int index0, int index1) {

    }

    /**
     * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
     */
    public void addSelectionInterval(int index0, int index1) {

    }

    /**
     * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
     */
    public void removeSelectionInterval(int index0, int index1) {

    }

    /**
     * @see javax.swing.ListSelectionModel#getMinSelectionIndex()
     */
    public int getMinSelectionIndex() {
        return 0;
    }

    /**
     * @see javax.swing.ListSelectionModel#getMaxSelectionIndex()
     */
    public int getMaxSelectionIndex() {
        return 0;
    }

    /**
     * @see javax.swing.ListSelectionModel#isSelectedIndex(int)
     */
    public boolean isSelectedIndex(int index) {
        return false;
    }

    /**
     * @see javax.swing.ListSelectionModel#getAnchorSelectionIndex()
     */
    public int getAnchorSelectionIndex() {
        return 0;
    }

    /**
     * @see javax.swing.ListSelectionModel#setAnchorSelectionIndex(int)
     */
    public void setAnchorSelectionIndex(int index) {

    }

    /**
     * @see javax.swing.ListSelectionModel#getLeadSelectionIndex()
     */
    public int getLeadSelectionIndex() {
        return 0;
    }

    /**
     * @see javax.swing.ListSelectionModel#setLeadSelectionIndex(int)
     */
    public void setLeadSelectionIndex(int index) {

    }

    /**
     * @see javax.swing.ListSelectionModel#clearSelection()
     */
    public void clearSelection() {

    }

    /**
     * @see javax.swing.ListSelectionModel#isSelectionEmpty()
     */
    public boolean isSelectionEmpty() {
        return false;
    }

    /**
     * @see javax.swing.ListSelectionModel#insertIndexInterval(int, int, boolean)
     */
    public void insertIndexInterval(int index, int length, boolean before) {

    }

    /**
     * @see javax.swing.ListSelectionModel#removeIndexInterval(int, int)
     */
    public void removeIndexInterval(int index0, int index1) {

    }

    /**
     * @see javax.swing.ListSelectionModel#setValueIsAdjusting(boolean)
     */
    public void setValueIsAdjusting(boolean valueIsAdjusting) {

    }

    /**
     * @see javax.swing.ListSelectionModel#getValueIsAdjusting()
     */
    public boolean getValueIsAdjusting() {
        return false;
    }

    /**
     * @see javax.swing.ListSelectionModel#setSelectionMode(int)
     */
    public void setSelectionMode(int selectionMode) {

    }

    /**
     * @see javax.swing.ListSelectionModel#getSelectionMode()
     */
    public int getSelectionMode() {
        return 0;
    }

    /**
     * @see javax.swing.ListSelectionModel#addListSelectionListener(javax.swing.event.ListSelectionListener)
     */
    public void addListSelectionListener(ListSelectionListener x) {

    }

    /**
     * @see javax.swing.ListSelectionModel#removeListSelectionListener(javax.swing.event.ListSelectionListener)
     */
    public void removeListSelectionListener(ListSelectionListener x) {

    }

}