/*
   $Id: MockLayout.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
   
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
import java.awt.LayoutManager;

import org.xulux.gui.IXuluxLayout;
import org.xulux.gui.Widget;

/**
 * A mock layoutmanager.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MockLayout.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
 */
public class MockLayout implements IXuluxLayout, LayoutManager {

  /**
   * 
   */
  public MockLayout() {
    super();
  }

  /**
   * @see org.xulux.gui.IXuluxLayout#setParent(org.xulux.gui.Widget)
   */
  public void setParent(Widget widget) {

  }

  /**
   * @see org.xulux.gui.IXuluxLayout#getParent()
   */
  public Widget getParent() {
    return null;
  }

  /**
   * @see org.xulux.gui.IXuluxLayout#addWidget(org.xulux.gui.Widget)
   */
  public void addWidget(Widget widget) {

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

  }

  /**
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
   */
  public void addLayoutComponent(String name, Component comp) {

  }

  /**
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  public void removeLayoutComponent(Component comp) {

  }

  /**
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  public Dimension preferredLayoutSize(Container parent) {
    return null;
  }

  /**
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  public Dimension minimumLayoutSize(Container parent) {
    return null;
  }

  /**
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  public void layoutContainer(Container parent) {

  }

}
