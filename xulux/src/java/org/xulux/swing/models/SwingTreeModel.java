/*
 $Id: SwingTreeModel.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $

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

import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.xulux.global.contenthandlers.TreeContentHandler;

/**
 * A cutom tree root, so we can do magic of our own
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingTreeModel.java,v 1.2 2003-12-18 01:18:05 mvdb Exp $
 */
public class SwingTreeModel extends TreeContentHandler implements TreeModel {

    /**
     * the contenthandler
     */
    private TreeContentHandler contentHandler;
    /**
     * the listenerlist
     */
    private ArrayList listenerList;
    /**
     * @param contentHandler the tree contentHandler to use..
     */
    public SwingTreeModel(TreeContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    /**
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener l) {
        if (listenerList == null) {
            listenerList = new ArrayList();
        }
        listenerList.add(l);
    }

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        return contentHandler.getChild(parent, index);
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (contentHandler == null) {
            return 0;
        }
        return contentHandler.getChildCount(parent);
    }

    /**
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        return contentHandler.getIndexOfChild(parent, child);
    }

    /**
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot() {
        if (contentHandler == null || contentHandler.getRoot() == null) {
            return "NULLROOT";
        }
        return contentHandler.getRoot();
    }

    /**
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        //System.out.println("contentHandler :" +contentHandler);
        if (contentHandler != null) {
            return contentHandler.isLeaf(node);
        }
        return false;
    }

    /**
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener l) {
        if (listenerList != null) {
            listenerList.remove(l);
        }
    }

    /**
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    /**
     * @see org.xulux.nyx.global.IContentHandler#getType()
     */
    public Class getType() {
        return null;
    }

    /**
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#refresh()
     */
    public void refresh() {
        System.err.println("REFRESH MODEL");
        if (listenerList == null) {
            return;
        }
        TreeModelEvent event = null;
        for (int i = listenerList.size() - 1; i >= 0; i--) {
            Object listener = listenerList.get(i);
            System.err.println("Listener : " + listener);
            if (listener instanceof TreeModelListener) {
                if (event == null) {
                    event = new TreeModelEvent(this, new TreePath(getRoot()));
                }
                ((TreeModelListener) listener).treeStructureChanged(event);
            }
        }
    }

}
