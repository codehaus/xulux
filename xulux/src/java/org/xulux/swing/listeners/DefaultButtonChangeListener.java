/*
   $Id: DefaultButtonChangeListener.java,v 1.1 2004-05-04 12:04:42 mvdb Exp $
   
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

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This listeners detects when the defaultbutton is used.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DefaultButtonChangeListener.java,v 1.1 2004-05-04 12:04:42 mvdb Exp $
 */
public class DefaultButtonChangeListener implements ChangeListener {

  /**
   * 
   */
  public DefaultButtonChangeListener() {
    super();
  }

  /**
   * Mainly used to release any pending events when the defaultbutton is used by
   * eg hitting the enter key.
   *
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  public void stateChanged(ChangeEvent e) {
      if (e.getSource() instanceof JButton) {
          JButton button = (JButton) e.getSource();
          if (button.getModel().isArmed() && button.getModel().isPressed()) {
              button.requestFocus();
          }
      }
  }

}
