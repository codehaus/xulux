/*
   $Id: BoxLayoutGui.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
   
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
package org.xulux.gui.swing.natives;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: BoxLayoutGui.java,v 1.1 2004-05-24 18:12:34 mvdb Exp $
 */
public class BoxLayoutGui {

    /**
     *
     */
    public BoxLayoutGui() {
      JPanel panel = new JPanel();
      JLabel label1 = new JLabel("label1");
      JLabel label2 = new JLabel("label2");
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
      frame.getContentPane().add(label1);
      frame.getContentPane().add(label2);
      frame.show();
      frame.pack();
    }
    
    public static void main(String[] args) {
        new BoxLayoutGui();
    }

}
