/*
   $Id: NyxTreeCellRenderer.java,v 1.4 2004-03-16 14:35:13 mvdb Exp $
   
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

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.xulux.dataprovider.BeanMapping;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.swing.widgets.Tree;
import org.xulux.utils.ClassLoaderUtils;

/**
 * For now extends the defaultreeCellRenderer.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTreeCellRenderer.java,v 1.4 2004-03-16 14:35:13 mvdb Exp $
 */
public class NyxTreeCellRenderer extends DefaultTreeCellRenderer {

    /**
     * the tree widget
     */
    protected Tree widget;
    /**
     * @param tree the tree
     */
    public NyxTreeCellRenderer(Tree tree) {
        super();
        this.widget = tree;
    }

    /**
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object,
     *             boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {
        String clazz = widget.getProperty("treefield.class");
        if (value != null && clazz != null) {
            if (ClassLoaderUtils.getClass(clazz) == value.getClass()) {
                BeanMapping mapping = Dictionary.getInstance().getMapping(value);
                String use = widget.getProperty("treefield.use");
                if (use != null) {
                    IField field = mapping.getField(use);
                    if (field != null) {
                        value = field.getValue(value);
                    }
                }
            }
        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }

}
