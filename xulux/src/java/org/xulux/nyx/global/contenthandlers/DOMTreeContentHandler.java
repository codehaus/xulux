/*
 $Id: DOMTreeContentHandler.java,v 1.1 2003-10-28 20:10:07 mvdb Exp $

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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Text;
import org.xulux.nyx.gui.IContentWidget;

/**
 * A dom contenthandler for a tree..  
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DOMTreeContentHandler.java,v 1.1 2003-10-28 20:10:07 mvdb Exp $
 */
public class DOMTreeContentHandler extends TreeContentHandler {
    
    /**
     * 
     */
    public DOMTreeContentHandler() {
        super();
    }

    /**
     * @param widget
     */
    public DOMTreeContentHandler(IContentWidget widget) {
        super(widget);
    }

    /**
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        if (parent instanceof DOMWrapper) {
            parent = ((DOMWrapper)parent).getSource();
        }
        if (parent instanceof Document) {
            List list = ((Document)parent).content();
            return new DOMWrapper(list.get(index));
        } else if (parent instanceof Element) {
            Element element = (Element) parent;
            List list = getRealContent(element.content());
            if ("part".equals(element.getName())) {
                System.err.println("Attributes : "+element.attributes());
            }
            list.addAll(element.attributes());
            return new DOMWrapper(list.get(index));
        } 
        return null;
    }

    /**
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (parent instanceof DOMWrapper) {
            parent = ((DOMWrapper)parent).getSource();
        }
        int children = 0;
        if (parent instanceof Document) {
            List list = ((Document)parent).content();
            if (list != null) {
                children = list.size();
            }
        } else if (parent instanceof Element) {
            Element element = (Element)parent;
            children =  getRealContent(element.content()).size();
            children+=element.attributeCount();
        }
        System.err.println("Children : "+children);
        return children;
    }
    
    /**
     * Removes all empty text.
     * 
     * @param content
     * @return
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
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object parent, Object child) {
        System.out.println("getIndexOfChild");
        System.out.println("parent : "+parent.getClass());
        System.out.println("child : "+parent.getClass());
        return 0;
    }

    /**
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#getRoot()
     */
    public Object getRoot() {
        return new DOMWrapper(widget.getContent());
    }

    /**
     * @see org.xulux.nyx.global.contenthandlers.TreeContentHandler#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object node) {
        if (node instanceof DOMWrapper) {
            node = ((DOMWrapper)node).getSource();
        }
        System.out.println("Node : "+node.getClass());
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
     * @see org.xulux.nyx.global.IContentHandler#getType()
     */
    public Class getType() {
        return Document.class;
    }
    
    /**
     * The domwrapper takes care of showing the correct toString 
     * to the tree..
     * 
     */
    public class DOMWrapper {
        private Object object;
        
        public DOMWrapper(Object object) {
            this.object = object;
        }
        
        public Object getSource() {
            return this.object;
        }
        
        public String toString() {
            if (object instanceof Element) {
                return ((Element)object).getName();
            } else if (object instanceof Document) {
                String name = ((Document)object).getName();
                if (name == null || "null".equals(name)) {
                    return "Unkown document type";
                }
            } else if (object instanceof Comment) {
                return "Comment: "+((Comment)object).getText();
                //return ((Comment)object).getName();
            } else if (object instanceof Text) {
                return "Text: "+((Text)object).getText();
            } else if (object instanceof Attribute) {
                Attribute attribute = (Attribute)object;
                return attribute.getName()+"="+attribute.getValue();
            }
            return object.toString();
        }
    }

}
