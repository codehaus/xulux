/*
   $Id: NativeJCombo.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
   
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
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * An example of use of a native swing combo
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NativeJCombo.java,v 1.2 2004-01-28 15:40:08 mvdb Exp $
 */
public class NativeJCombo extends JComboBox {

    /**
     * @param aModel the model
     */
    public NativeJCombo(ComboBoxModel aModel) {
        super(aModel);
    }

    /**
     * @param items the items
     */
    public NativeJCombo(Object[] items) {
        super(items);
    }

    /**
     * @param items the items
     */
    public NativeJCombo(Vector items) {
        super(items);
    }

    /**
     *
     */
    public NativeJCombo() {
        super();
        setPreferredSize(new Dimension(135, 21));
        Vector vector = new Vector();
        vector.add("1");
        vector.add("2");
        vector.add("3");
        vector.add("4");
        vector.add("5");
        setModel(new DefaultComboBoxModel(vector));
    }
}
