/*
 $Id: Tree.java,v 1.2 2003-09-17 11:49:31 mvdb Exp $

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
package org.xulux.nyx.swing.widgets;

import javax.swing.JTree;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.WidgetConfig;
import org.xulux.nyx.global.contenthandlers.TreeContentHandler;
import org.xulux.nyx.gui.IContentWidget;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swing.models.SwingTreeModel;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Tree.java,v 1.2 2003-09-17 11:49:31 mvdb Exp $
 */
public class Tree extends Widget implements IContentWidget {
    
    protected JTree jtree;
    protected Object content;
    protected boolean contentChanged;
    protected SwingTreeModel model;
    protected TreeContentHandler contentHandler;
    
    /**
     * @param name
     */
    public Tree(String name) {
        super(name);

    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {

    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        initialize();
        return this.jtree;
    }

    /**
     * We need to set the model on initialization and replace the root
     * node on refresh. A setmodel doesn't use the new model (???) Weird..
     * 
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        this.model = new SwingTreeModel(contentHandler);
        this.jtree = new JTree(model);
        
        refresh();
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
        /* Works only for Metal L&F */
        String lineStyle = getProperty("linestyle");
        if (lineStyle == null) {
            // default to none..
            lineStyle = "None";
        }
        jtree.putClientProperty("JTree.lineStyle", lineStyle);
        
        //jtree.set
        if (contentChanged) {
            jtree.setModel(new SwingTreeModel(contentHandler));
            contentChanged= false;
        }
        isRefreshing = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {

    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#setContent(java.util.List)
     */
    public void setContent(Object object) {
        this.content = object;
        if (object != null) {
            System.out.println("Content object : "+object.getClass());
            WidgetConfig config = ApplicationContext.getInstance().getWidgetConfig(getWidgetType());
            this.contentHandler = (TreeContentHandler)config.getContentHandler(object.getClass());
            this.contentHandler.setWidget(this);
            this.contentHandler.setContent(object);
        }
        
        contentChanged = true;
        refresh();
    }

    /**
     * @see org.xulux.nyx.gui.IContentWidget#getContent()
     */
    public Object getContent() {
        return this.content;
    }

}
