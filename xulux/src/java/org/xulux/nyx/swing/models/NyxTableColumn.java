/*
 $Id: NyxTableColumn.java,v 1.1 2003-11-12 02:53:34 mvdb Exp $

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
package org.xulux.nyx.swing.models;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.BooleanUtils;
import org.xulux.nyx.gui.NyxCombo;
import org.xulux.nyx.gui.Widget;

/**
 * Override the standard TableColumn, so we can use instances of widgets to set columns
 * , instead of dynamically creating it them all the time.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTableColumn.java,v 1.1 2003-11-12 02:53:34 mvdb Exp $
 */
public class NyxTableColumn extends TableColumn {
    
    protected Widget widget;
    
    /**
     * 
     */
    public NyxTableColumn() {
        super();
    }
    
    public NyxTableColumn(Widget widget) {
        this.widget = widget;
        setHeaderValue(widget.getProperty("text"));
        setPreferredWidth(widget.getRectangle().getWidth());
        setWidth(widget.getRectangle().getWidth());
        if (widget instanceof NyxCombo) {
            setCellEditor(new NyxTableCellEditor(widget));
        }
    }

    /**
     * @param modelIndex
     */
    public NyxTableColumn(int modelIndex) {
        super(modelIndex);
    }

    /**
     * @param modelIndex
     * @param width
     */
    public NyxTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
    }

    /**
     * @param modelIndex
     * @param width
     * @param cellRenderer
     * @param cellEditor
     */
    public NyxTableColumn(
        int modelIndex,
        int width,
        TableCellRenderer cellRenderer,
        TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
    }
    
    /** 
     * 
     * @return is the column is locked or not.
     */
    public boolean isLocked() {
        return BooleanUtils.toBoolean(widget.getProperty("locked"));
    }

}
