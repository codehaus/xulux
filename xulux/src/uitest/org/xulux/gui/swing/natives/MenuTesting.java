/*
   $Id: MenuTesting.java,v 1.2 2004-01-28 15:40:10 mvdb Exp $
   
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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MenuTesting.java,v 1.2 2004-01-28 15:40:10 mvdb Exp $
 */
public class MenuTesting {

    /**
     * 
     */
    public MenuTesting() {
        super();
    }

    /**
     * Show a menu
     */
    public void showMenu() {
        JFrame frame = new JFrame("Frame");
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu();
        menu.setText("File");
        JMenuItem item = new JMenuItem();
        item.setText("Exit");
        menu.add(item);
        bar.add(menu);
        frame.getContentPane().add(bar);
        frame.show();
    }

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        new MenuTesting().showMenu();
    }

}
