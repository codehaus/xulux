/*
   $Id: CheckBoxTest.java,v 1.1 2004-05-24 15:19:59 mvdb Exp $
   
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
package org.xulux.swing.widgets;

import javax.swing.JComponent;

import org.xulux.core.ApplicationPart;
import org.xulux.gui.WidgetFactory;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: CheckBoxTest.java,v 1.1 2004-05-24 15:19:59 mvdb Exp $
 */
public class CheckBoxTest extends TestCase {

    /**
     * Constructor for CheckBoxTest.
     * @param name the name of the test
     */
    public CheckBoxTest(String name) {
        super(name);
    }
    
    public void testProperties() {
        System.out.println("testProperties");
        CheckBox widget = (CheckBox) WidgetFactory.getWidget("checkbox", "checkbox");
        ApplicationPart part = new ApplicationPart();
        widget.setPart(part);
        assertEquals(true, widget.isVisible());
        JComponent component = (JComponent) widget.getNativeWidget();
        assertEquals(true, component.isVisible());
        widget.setVisible(false);
        assertEquals(false, widget.isVisible());
        assertEquals(false, component.isVisible());
        
    }

}
