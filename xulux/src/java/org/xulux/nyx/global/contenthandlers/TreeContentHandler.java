/*
 $Id: TreeContentHandler.java,v 1.2 2003-09-17 11:49:31 mvdb Exp $

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
package org.xulux.nyx.global.contenthandlers;

import org.xulux.nyx.gui.IContentWidget;

/**
 * A contenthandler for the tree. Sicne it is the main interface for having
 * trees at all in default java, it will be located in the global package.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeContentHandler.java,v 1.2 2003-09-17 11:49:31 mvdb Exp $
 */
public abstract class TreeContentHandler extends ContentHandlerAbstract
{
    
    protected IContentWidget widget;
    /**
     * 
     */
    public TreeContentHandler() {
        super();
    }
    
    public TreeContentHandler(IContentWidget widget) {
        setWidget(widget);
    }

    /**
     * Just returns the content. No conversions yet.
     * 
     * @see org.xulux.nyx.global.IContentHandler#getContent()
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * 
     * @return the widget
     */
    public IContentWidget getWidget() {
        return widget;
    }
    
    /**
     * Set the widget.
     * @param widget
     */
    public void setWidget(IContentWidget widget) {
        this.widget = widget;
    }

    /**
     * Returns the child at the index of the parent
     * 
     * @param parent
     * @param index
     */
    public abstract Object getChild(Object parent, int index);
    
    /**
     * Returns the number of children the parent has
     * @param parent
     * @return
     */
    public abstract int getChildCount(Object parent);
    
    /**
     * Return the index of the specified child.
     * 
     * @param parent
     * @param child
     * @return
     */
    public abstract int getIndexOfChild(Object parent, Object child);
    
    /**
     * 
     * @return the root of the tree.
     */
    public abstract Object getRoot();
    
    /**
     * 
     * @param node
     * @return checks if the node is a leaf or not.
     */
    public abstract boolean isLeaf(Object node);

}
