/*
   $Id: SwingLayoutAbstract.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
   
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
package org.xulux.swing.layouts;

import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.Widget;

/**
 * An abstract convenience class, so you don't have to reimplement
 * setParent etc all the time.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingLayoutAbstract.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
 */
public abstract class SwingLayoutAbstract implements IXuluxLayout {

    /**
     * This is the widget that actually created the layout..
     */
    protected Widget parentWidget;
  
    /**
     * 
     */
    public SwingLayoutAbstract() {
    }
  
    /**
     * @see org.xulux.gui.IXuluxLayout#setParent(org.xulux.gui.Widget)
     */
    public void setParent(Widget widget) {
        this.parentWidget = widget;
    }
  
    /**
     * @see org.xulux.gui.IXuluxLayout#getParent()
     */
    public Widget getParent() {
        return this.parentWidget;
    }
  
    /**
     * @see org.xulux.gui.IXuluxLayout#addWidget(org.xulux.gui.Widget)
     */
    public abstract void addWidget(Widget widget);
  
    /**
     * @see org.xulux.gui.IXuluxLayout#removeWidget(org.xulux.gui.Widget)
     */
    public abstract void removeWidget(Widget widget);
  
    /**
     * @see org.xulux.gui.IXuluxLayout#destroy()
     */
    public abstract void destroy();
}
