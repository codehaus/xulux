/*
$Id: MultipleLineHeaderRenderer.java,v 1.2 2004-09-30 21:25:39 mvdb Exp $

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
package org.xulux.swing.models;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.xulux.swing.extensions.MultipleLineHeader;
import org.xulux.utils.StringUtils;

/**
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MultipleLineHeaderRenderer.java,v 1.2 2004-09-30 21:25:39 mvdb Exp $
 */
public class MultipleLineHeaderRenderer implements TableCellRenderer {

  MultipleLineHeader mlh;
  JScrollPane scrollPane;
  
  public MultipleLineHeaderRenderer(String title) {
    mlh = new MultipleLineHeader(title);
//    scrollPane = new JScrollPane(mlh);
//    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
  }
  /**
   * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
   */
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    mlh.setText(StringUtils.replace((String) value, "\\n", '\n'));
    return mlh;
  }

}
