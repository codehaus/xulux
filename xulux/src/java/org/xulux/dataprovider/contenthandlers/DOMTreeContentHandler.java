/*
   $Id: DOMTreeContentHandler.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
   
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
package org.xulux.dataprovider.contenthandlers;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Text;
import org.xulux.gui.IContentWidget;

/**
 * A dom contenthandler for a tree..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DOMTreeContentHandler.java,v 1.2 2004-03-23 08:42:23 mvdb Exp $
 */
public class DOMTreeContentHandler extends TreeContentHandler {

    /**
     * This constructor sets the default view
     * to the SimpleDOMView
     */
    public DOMTreeContentHandler() {
        super();
    }

    /**
     * @param widget the content widget
     */
    public DOMTreeContentHandler(IContentWidget widget) {
        super(widget);
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        if (parent instanceof ContentView) {
            parent = ((ContentView) parent).getSource();
        }
        if (parent instanceof Document) {
            List list = ((Document) parent).content();
            return ContentView.createView(view, list.get(index));
        } else if (parent instanceof Element) {
            Element element = (Element) parent;
            List list = getRealContent(element.content());
            if ("part".equals(element.getName())) {
                //System.err.println("Attributes : " + element.attributes());
            }
            list.addAll(element.attributes());
            return ContentView.createView(view, list.get(index));
        }
        return null;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (parent instanceof ContentView) {
            parent = ((ContentView) parent).getSource();
        }
        int children = 0;
        if (parent instanceof Document) {
            List list = ((Document) parent).content();
            if (list != null) {
                children = list.size();
            }
        } else if (parent instanceof Element) {
            Element element = (Element) parent;
            children =  getRealContent(element.content()).size();
            children += element.attributeCount();
        }
        //System.err.println("Children : " + children);
        return children;
    }

    /**
     * Removes all empty text.
     *
     * @param content the current content
     * @return a list with the real content
     */
    private List getRealContent(List content) {
        List result = new ArrayList();
        for (int i = 0; i < content.size(); i++) {
            Object object = content.get(i);
            if (object instanceof Text) {
                Text text = (Text) object;
                if ("".equals(text.getText().trim())) {
                    continue;
                }
            }
            result.add(object);
        }
        return result;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
//        System.out.println("getIndexOfChild");
//        System.out.println("parent : " + parent.getClass());
//        System.out.println("child : " + parent.getClass());
        return 0;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#getRoot()
     */
    public Object getRoot() {
        return ContentView.createView(getViewClass(), widget.getContent());
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.TreeContentHandler#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        if (node instanceof ContentView) {
            node = ((ContentView) node).getSource();
        }
        //System.out.println("Node : " + node.getClass());
        if (node instanceof Comment) {
            return true;
        } else if (node instanceof Element) {
            Element element = (Element) node;
            List list = getRealContent(element.content());
            return list == null || list.size() == 0;
        } else if (node instanceof Text) {
            return true;
        } else if (node instanceof Attribute) {
            return true;
        }
        return false;
    }

    /**
     * @see org.xulux.dataprovider.contenthandlers.IContentHandler#getType()
     */
    public Class getType() {
        return Document.class;
    }
}
