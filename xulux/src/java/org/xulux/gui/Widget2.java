/*
   $Id: Widget2.java,v 1.2 2004-07-25 20:20:48 mvdb Exp $
   
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
package org.xulux.gui;

import java.io.Serializable;

/**
 * The new Widget structure.
 * Since we extend widget, this makes sure current code will continue to work
 * When this refactor is finished, it should replace the current widget class though.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Widget2.java,v 1.2 2004-07-25 20:20:48 mvdb Exp $
 */
public class Widget2 extends Widget implements Serializable {

  public Widget2(String name) {
      super(name);
  }

  /**
   * @see org.xulux.gui.Widget#destroy()
   */
  public void destroy() {
  }

  /**
   * @see org.xulux.gui.Widget#getNativeWidget()
   */
  public Object getNativeWidget() {
    return null;
  }

  /**
   * @see org.xulux.gui.Widget#initialize()
   */
  public void initialize() {
  }

  /**
   * @see org.xulux.gui.Widget#getGuiValue()
   */
  public Object getGuiValue() {
    return null;
  }

  /**
   * @see org.xulux.gui.Widget#focus()
   */
  public void focus() {
  }

  /**
   * @see org.xulux.gui.Widget#isValueEmpty()
   */
  public boolean isValueEmpty() {
    return false;
  }

  /**
   * @see org.xulux.gui.Widget#canContainValue()
   */
  public boolean canContainValue() {
    return false;
  }

}
