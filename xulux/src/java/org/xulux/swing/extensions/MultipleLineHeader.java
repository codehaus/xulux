/*
$Id: MultipleLineHeader.java,v 1.1 2004-09-23 07:41:28 mvdb Exp $

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
package org.xulux.swing.extensions;

import javax.swing.JTextArea;
import javax.swing.LookAndFeel;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MultipleLineHeader.java,v 1.1 2004-09-23 07:41:28 mvdb Exp $
 */
public class MultipleLineHeader extends JTextArea {
  
  /**
   * The constructor for the multiple line header.
   * @param title the tile of the header.
   */
  public MultipleLineHeader(String title) {
    super(title);
  }
  
  public void updateUI() {
    super.updateUI();
    setLineWrap(false);
    setWrapStyleWord(false);
    setHighlighter(null);
    setEditable(false);
    // insert the gui specifics..
    LookAndFeel.installColorsAndFont(this, "TableHeader.background", "TableHeader.foreground", "TableHeader.font");
    LookAndFeel.installBorder(this, "TableHeader.cellBorder");
    setAlignmentX(JTextArea.CENTER_ALIGNMENT);
    setAlignmentY(JTextArea.CENTER_ALIGNMENT);
  }

}
