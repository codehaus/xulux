/*
   $Id: NativeJButton.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeJButton.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class NativeJButton extends JButton {

    /**
     *
     */
    public NativeJButton() {
        super();
        setPreferredSize(new Dimension(135, 31));
        setText("Native JButton");
    }

    /**
     * @param icon icon
     */
    public NativeJButton(Icon icon) {
        super(icon);
    }

    /**
     * @param text text
     */
    public NativeJButton(String text) {
        super(text);
    }

    /**
     * @param a action
     */
    public NativeJButton(Action a) {
        super(a);
    }

    /**
     * @param text text
     * @param icon icon
     */
    public NativeJButton(String text, Icon icon) {
        super(text, icon);
    }

}
