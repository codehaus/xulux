package org.xulux.swing.widgets;/*
   $Id: PanelTest.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
   
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
import javax.swing.JPanel;

import junit.framework.TestCase;

import org.xulux.core.ApplicationPart;
import org.xulux.core.XuluxContext;
import org.xulux.gui.IXuluxLayout;
import org.xulux.guidriver.defaults.GuiDefaults;
import org.xulux.swing.layouts.MockLayout;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PanelTest.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
 */
public class PanelTest extends TestCase {

    /**
     * Constructor for PanelTest.
     * @param name the name of the test
     */
    public PanelTest(String name) {
      super(name);
    }
  
    public void testLayout() {
        System.out.println("testLayout");
        GuiDefaults defaults = XuluxContext.getGuiDefaults();
        defaults.registerLayout("mocklayout", false, MockLayout.class.getName(), "swing");
        Panel panel = new Panel("panel");
        //window.setPart(new ApplicationPart("part"));
        IXuluxLayout layout = XuluxContext.getGuiDefaults().getDefaultLayout();
        JPanel jPanel = (JPanel) panel.getNativeWidget();
        assertEquals(layout.getClass(), jPanel.getLayout().getClass());
        IXuluxLayout xuluxLayout = (IXuluxLayout) jPanel.getLayout();
        assertEquals(false, MockLayout.class == xuluxLayout.getClass());
        assertEquals(panel, xuluxLayout.getParent());
        panel.destroy();
        panel = new Panel("panel");
        panel.setProperty("layout", "mocklayout");
        panel.setPart(new ApplicationPart("part"));
        jPanel = (JPanel) panel.getNativeWidget();
        xuluxLayout = (IXuluxLayout) jPanel.getLayout();
        assertEquals(MockLayout.class, xuluxLayout.getClass());
        panel.destroy();
    }
}
