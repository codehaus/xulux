/*
   $Id: WidgetFocusListener.java,v 1.1 2004-07-19 22:07:34 mvdb Exp $
   
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
package org.xulux.swing.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import org.xulux.gui.NyxListener;
import org.xulux.gui.Widget;
import org.xulux.swing.util.NyxEventQueue;

/**
 * This adds a focuslistener for widgets that do not have a focus event by default
 * or widgets that just need to release the eventqueue when focus is gained.
 * (eg buttons have this behaviour)
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: WidgetFocusListener.java,v 1.1 2004-07-19 22:07:34 mvdb Exp $
 */
public class WidgetFocusListener extends NyxListener implements FocusListener {

  /**
   * 
   */
  public WidgetFocusListener(Widget widget) {
    super(widget);
  }

  /**
   * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
   */
  public void focusGained(FocusEvent e) {
      if (isProcessing()) {
          return;
      }
      if (e.getID() != FocusEvent.FOCUS_GAINED || e.isTemporary()) {
          return;
      }
      NyxEventQueue q = NyxEventQueue.getInstance();
      q.holdEvents(false);
  }

  /**
   * Free the event queue when the focus of a component is lost, so 
   * processing of the field will take place.
   *
   * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
   */
  public void focusLost(FocusEvent e) {
  }

}
