/*
   $Id: ScrollPanePanel.java,v 1.3 2004-06-28 13:11:07 mvdb Exp $
   
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

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.xulux.swing.layouts.XYLayout;

/**
 * A sample to see how a scrollpane and a panel cooperate together..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ScrollPanePanel.java,v 1.3 2004-06-28 13:11:07 mvdb Exp $
 */
public class ScrollPanePanel {

    /**
     *
     */
    public ScrollPanePanel() {
        super();
    }

    /**
     * Start the panel
     */
    public void start() {
        JFrame frame = new JFrame("ScrollPanePanelDemo");
        frame.setSize(400, 400);
        frame.getContentPane().setLayout(new XYLayout());
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 700));
        panel.setBackground(Color.blue);
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(panel);
        pane.setPreferredSize(new Dimension(100, 100));
        frame.getContentPane().add(pane);
        frame.show();
    }

    /**
     * The main entry
     * @param args the arguments
     */
    public static void main(String[] args) {
        new ScrollPanePanel().start();
    }
}
