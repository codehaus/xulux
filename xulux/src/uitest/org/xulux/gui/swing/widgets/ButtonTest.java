/*
   $Id: ButtonTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
   
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

import java.io.InputStream;

import org.xulux.context.ApplicationPart;
import org.xulux.gui.PartCreator;

import junit.framework.TestCase;

/**
 * A testcase for buttons.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: ButtonTest.java,v 1.3 2004-01-28 15:40:08 mvdb Exp $
 */
public class ButtonTest extends TestCase {

    /**
     * Constructor for ButtonTest.
     * @param name the name of the test
     */
    public ButtonTest(String name) {
        super(name);
    }

    /**
     * Shows the button xml.
     *
     */
    public void show() {
        String xml = "org/xulux/gui/swing/widgets/ButtonTest.xml";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(null, stream);
        part.activate();
    }

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        new ButtonTest("ButtonTest").show();
    }

}
