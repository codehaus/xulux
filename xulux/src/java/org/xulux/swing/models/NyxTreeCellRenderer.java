/*
   $Id: NyxTreeCellRenderer.java,v 1.15 2005-01-12 18:39:31 mvdb Exp $
   
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
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.xulux.core.PartRequest;
import org.xulux.core.XuluxContext;
import org.xulux.dataprovider.IField;
import org.xulux.dataprovider.IMapping;
import org.xulux.gui.Widget;
import org.xulux.swing.util.SwingUtils;
import org.xulux.swing.widgets.Tree;
import org.xulux.utils.ClassLoaderUtils;

/**
 * For now extends the defaultreeCellRenderer.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: NyxTreeCellRenderer.java,v 1.15 2005-01-12 18:39:31 mvdb Exp $
 */
public class NyxTreeCellRenderer extends DefaultTreeCellRenderer {

    /**
     * the tree widget
     */
    protected Tree widget;
    
    /**
     * The childwidget
     */
    protected Widget childWidget;

    /**
     * the request
     */
    protected PartRequest request;
    
    /**
     * Store a temp font in case java suddenly doesn't know what font use.
     */
    protected Font tmpFont;

    /**
     * @param tree the tree
     */
    public NyxTreeCellRenderer(Tree tree) {
        super();
        this.widget = tree;
    }

    /**
     * Set the childwidget of the tree
     *
     * @param widget the widget to use for formatting. Should be a label.
     */
    public void setChildWidget(Widget widget) {
        this.childWidget = widget;
        request = new CellPartRequest(widget);
    }

    /**
     * @return the childwidget
     */    
    public Widget getChildWidget() {
        return this.childWidget;
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
                IMapping mapping = XuluxContext.getDictionary().getMapping(value);
                String use = widget.getProperty("treefield.use");
                if (use != null) {
                    IField field = mapping.getField(use);
                    if (field != null) {
                        value = field.getValue(value);
                    }
                }
            }
        }
        if (getChildWidget() != null) {
            request.setValue(value);
            XuluxContext.fireFieldRequest(getChildWidget(), request, XuluxContext.PRE_REQUEST);
        }
        try {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (getChildWidget() != null) {
                Widget w = getChildWidget();
                String icon = w.getProperty("icon");
                if (icon != null) {
                    Icon iconImage = SwingUtils.getIcon(icon, getChildWidget());
                    setIcon(iconImage);
                    // if we don't expand the preferred size with the iconwidth
                    // it will result in dots in the node name.
                    Dimension dim = ui.getPreferredSize(this);
                    int width = 4;
                    if (getFont() != null) {
                      if (this.tmpFont == null) {
                      	this.tmpFont = getFont();
                      }
                      width += getFontMetrics(getFont()).stringWidth(String.valueOf(value));
                    } else if (tmpFont != null) {
                    	width += getFontMetrics(tmpFont).stringWidth(String.valueOf(value));
                    }
                    if (iconImage != null) {
                      width+=iconImage.getIconWidth();
                    }
                    dim.width=width;
                    setPreferredSize(dim);
                }
            }
        } catch (Exception e) {
            super.getTreeCellRendererComponent(tree, e.toString(), sel, expanded, leaf, row, hasFocus);
        }
        return this;
    }

}
