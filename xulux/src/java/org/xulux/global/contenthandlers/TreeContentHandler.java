/*
   $Id: TreeContentHandler.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
   
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
package org.xulux.global.contenthandlers;

import org.xulux.gui.IContentWidget;

/**
 * A contenthandler for the tree. Since it is the main interface for having
 * trees at all in default java, it will be located in the global package.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TreeContentHandler.java,v 1.2 2004-01-28 14:55:54 mvdb Exp $
 */
public abstract class TreeContentHandler extends ContentHandlerAbstract
{

    /**
     * The widget to get the content from
     */
    protected IContentWidget widget;

    /**
     *
     */
    public TreeContentHandler() {
        super();
    }

    /**
     * @param widget the widget to get the content from
     */
    public TreeContentHandler(IContentWidget widget) {
        setWidget(widget);
    }

    /**
     * @see org.xulux.nyx.global.IContentHandler#getContent()
     * @return the content of the widget passed in and if not not set, it will return
     *         the content set by setContent.
     */
    public Object getContent() {
        if (widget != null) {
            return widget.getContent();
        }
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
     * @param widget the widget to get the content from
     */
    public void setWidget(IContentWidget widget) {
        this.widget = widget;
    }

    /**
     * @param parent the parent of the child
     * @param index the index of the child
     * @return the child at the index of the parent
     */
    public abstract Object getChild(Object parent, int index);

    /**
     * @param parent the parent of the children
     * @return the number of children the parent has
     */
    public abstract int getChildCount(Object parent);

    /**
     *
     * @param parent the parent of the children
     * @param child the child to get the index from
     * @return the index of the specified child.
     */
    public abstract int getIndexOfChild(Object parent, Object child);

    /**
     * @return the root of the tree.
     */
    public abstract Object getRoot();

    /**
     * @param node the node to check for
     * @return checks if the node is a leaf or not.
     */
    public abstract boolean isLeaf(Object node);

}
