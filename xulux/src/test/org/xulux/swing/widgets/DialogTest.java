package org.xulux.swing.widgets;/*
   $Id: DialogTest.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
   
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
import javax.swing.JDialog;

import junit.framework.TestCase;

import org.xulux.core.ApplicationPart;
import org.xulux.core.XuluxContext;
import org.xulux.gui.IXuluxLayout;
import org.xulux.guidriver.defaults.GuiDefaults;
import org.xulux.swing.layouts.MockLayout;

/**
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: DialogTest.java,v 1.1 2004-05-18 00:01:14 mvdb Exp $
 */
public class DialogTest extends TestCase {

    /**
     * Constructor for DialogTest.
     * @param name the name of the test
     */
    public DialogTest(String name) {
      super(name);
    }
  
    public void testLayout() {
        System.out.println("testLayout");
        GuiDefaults defaults = XuluxContext.getGuiDefaults();
        defaults.registerLayout("mocklayout", false, MockLayout.class.getName(), "swing");
        Dialog dialog = new Dialog("dialog");
        dialog.setPart(new ApplicationPart("part"));
        IXuluxLayout layout = XuluxContext.getGuiDefaults().getDefaultLayout();
        JDialog jDialog = (JDialog) dialog.getNativeWidget();
        assertEquals(layout.getClass(), jDialog.getContentPane().getLayout().getClass());
        IXuluxLayout xuluxLayout = (IXuluxLayout) jDialog.getContentPane().getLayout();
        assertEquals(dialog, xuluxLayout.getParent());
        assertEquals(false, MockLayout.class == xuluxLayout.getClass());
        jDialog.dispose();
        dialog = new Dialog("dialog");
        dialog.setProperty("layout", "mocklayout");
        dialog.setPart(new ApplicationPart("part"));
        jDialog = (JDialog) dialog.getNativeWidget();
        assertEquals(MockLayout.class, jDialog.getContentPane().getLayout().getClass());
        jDialog.dispose();
    }
    
    public void testTitle() {
        System.out.println("testTitle");
        Dialog dialog = new Dialog("dialog");
        dialog.setPart(new ApplicationPart("part"));
        dialog.setProperty("title", "title");
        JDialog jDialog = (JDialog) dialog.getNativeWidget();
        assertEquals("title", jDialog.getTitle());
        dialog.setProperty("title", "newTitle");
        assertEquals("newTitle", jDialog.getTitle());
        jDialog.dispose();
    }
}
