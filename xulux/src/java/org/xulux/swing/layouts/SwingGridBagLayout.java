/*
   $Id: SwingGridBagLayout.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
   
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager2;

import org.xulux.gui.Widget;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingGridBagLayout.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
 */
public class SwingGridBagLayout extends SwingLayoutAbstract implements LayoutManager2 {

    protected GridBagLayout layout;
    /**
     * We reuse the instance of gbc, since it is cloned when
     * passed as a parameter to the gridbaglayout
     */
    protected GridBagConstraints c = new GridBagConstraints();

    /**
     * 
     */
    public SwingGridBagLayout() {
        layout = new GridBagLayout();
    }
  
    /**
     * @see org.xulux.gui.IXuluxLayout#addWidget(org.xulux.gui.Widget)
     */
    public void addWidget(Widget widget) {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = widget.getRectangle().getX();
        c.gridy = widget.getRectangle().getY();
        c.gridwidth = 1;
        c.gridheight = 1;
        Component comp  = (Component) widget.getNativeWidget();
        layout.setConstraints(comp,c);
    }
  
    /**
     * @see org.xulux.gui.IXuluxLayout#removeWidget(org.xulux.gui.Widget)
     */
    public void removeWidget(Widget widget) {
    }
  
    /**
     * @see org.xulux.gui.IXuluxLayout#destroy()
     */
    public void destroy() {
      c = null;
      layout = null;
    }
  
    /**
     * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        addWidget((Widget) constraints);
    }
  
    /**
     * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
     */
    public Dimension maximumLayoutSize(Container target) {
        return layout.maximumLayoutSize(target);
    }
  
    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
     */
    public float getLayoutAlignmentX(Container target) {
        return layout.getLayoutAlignmentX(target);
    }
  
    /**
     * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
     */
    public float getLayoutAlignmentY(Container target) {
        return layout.getLayoutAlignmentY(target);
    }
  
    /**
     * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
     */
    public void invalidateLayout(Container target) {
        
    }
  
    /**
     * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
     */
    public void addLayoutComponent(String name, Component comp) {
        layout.addLayoutComponent(name, comp);
    }
  
    /**
     * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
     */
    public void removeLayoutComponent(Component comp) {
        layout.removeLayoutComponent(comp);
    }
  
    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    public Dimension preferredLayoutSize(Container parent) {
      return layout.preferredLayoutSize(parent);
    }
  
    /**
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    public Dimension minimumLayoutSize(Container parent) {
      return layout.minimumLayoutSize(parent);
    }
  
    /**
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    public void layoutContainer(Container parent) {
        layout.layoutContainer(parent);
    }

}
