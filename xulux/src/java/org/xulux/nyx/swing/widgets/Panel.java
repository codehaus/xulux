/*
 $Id: Panel.java,v 1.23 2003-11-11 14:46:15 mvdb Exp $

 Copyright 2002-2003 (C) The Xulux Project. All Rights Reserved.

 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.

 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.

 3. The name "xulux" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Xulux Project.  For written permission,
    please contact martin@mvdb.net.

 4. Products derived from this Software may not be called "xulux"
    nor may "xulux" appear in their names without prior written
    permission of the Xulux Project. "xulux" is a registered
    trademark of the Xulux Project.

 5. Due credit should be given to the Xulux Project
    (http://xulux.org/).

 THIS SOFTWARE IS PROVIDED BY THE XULUX PROJECT AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.nyx.swing.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.xulux.nyx.gui.ContainerWidget;
import org.xulux.nyx.gui.IShowChildWidgets;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.utils.ColorUtils;
import org.xulux.nyx.swing.extensions.NyxTitledBorder;
import org.xulux.nyx.swing.layouts.XYLayout;

/**
 * A panel widget
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Panel.java,v 1.23 2003-11-11 14:46:15 mvdb Exp $
 */
public class Panel extends ContainerWidget
{

    private JPanel panel;

    /**
     * Constructor for Panel.
     * @param name
     */
    public Panel(String name)
    {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        processDestroy();
        super.destroy();
        if (panel == null) {
            return;
        }
        Container container = panel.getParent();
        panel.setVisible(false);
        panel.removeAll();
        if (container != null)
        {
            container.remove(panel);
        }
        panel = null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return panel;
    }

    /**
     * TODO: Make layouts flexible.
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        // we default to XYLayout for now..
        initialized = true;
        panel = new JPanel(new XYLayout(this));
        initializeChildren();
        refresh();
        processInit();
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        initialize();
        panel.setVisible(isVisible());
        String backgroundColor = getProperty("default-background-color");

        if (backgroundColor != null) {
            panel.setBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        /*
         * Fix for border issues. They tend to be too big or
         * too small..
         */
        String border = getProperty("border");
        if (border!=null)
        {
            Color color = panel.getForeground();
            String borderColor = getProperty("border-color");
            if (borderColor != null) {
                color = ColorUtils.getSwingColor(borderColor);
            }
            if (border.equalsIgnoreCase("bevel"))
            {
                String borderType = getProperty("border-type");
                // defaults to lowered.
                int bType = BevelBorder.LOWERED;
                if (borderType != null &&
                       borderType.equalsIgnoreCase("raised")) {
                    bType = BevelBorder.RAISED;
                }
                panel.setBorder(new BevelBorder(bType));
            }else if (border.equalsIgnoreCase("titled")) {
                String borderTitle = getProperty("border-title");
                if (borderTitle == null) {
                    borderTitle = "";
                }

                Color titleColor = color;
                if (getProperty("border-title-color") != null) {
                    titleColor = ColorUtils.getSwingColor(getProperty("border-title-color"));
                    System.out.println("title color : "+titleColor);
                }
                int borderSize = 1;
                if (getProperty("border-size") != null) {
                    try {
                        borderSize = Integer.parseInt(getProperty("border-size"));
                    } catch(NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
                //TitledBorder titledBorder = BorderFactory.createTitledBorder(new LineBorder(color,borderSize),borderTitle);
                TitledBorder titledBorder = new NyxTitledBorder(borderTitle);
                titledBorder.setTitleColor(titleColor);
                panel.setBorder(titledBorder);
            } else if (border.equalsIgnoreCase("line")) {
                int borderSize = 1;
                if (getProperty("border-size") != null) {
                    try {
                        borderSize = Integer.parseInt(getProperty("border-size"));
                    } catch(NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
                panel.setBorder( new LineBorder(color, borderSize));
            } else if (border.equalsIgnoreCase("etched")) {
                EtchedBorder etched = new EtchedBorder(EtchedBorder.RAISED);
                panel.setBorder( etched );

            }
//            System.out.println("Start : "+getName());
//            System.out.println("Bounds panel: "+panel.getBounds());
//            System.out.println("GetSize : "+panel.getSize());
//            System.out.println("GetSize Preferred : "+panel.getPreferredSize());
//            System.out.println("Insets panel : "+panel.getInsets());
//            System.out.println("Border insets : "+panel.getBorder().getBorderInsets(panel));
//            System.out.println("End : "+getName());
        }
        // TODO: Add tab widget instead of panel widget..
        String tabId = getProperty(TabPanel.TABID);
        if (tabId == null) {
            panel.setEnabled(isEnabled());
        } else {
            ((JTabbedPane)getParent().getNativeWidget()).setEnabledAt(Integer.parseInt(tabId), isEnabled());
        }
        panel.repaint();
    }


    /**
     * @see org.xulux.nyx.gui.Widget#canContainChildren()
     */
    public boolean canContainChildren()
    {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getChildWidgets()
     */
    public ArrayList getChildWidgets()
    {
        return widgets;
    }

    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(Widget)
     */
    public void addToParent(Widget widget) {
        if (widget instanceof IShowChildWidgets) {
            List children = widget.getChildWidgets();
            if (children != null && children.size() > 0) {
                Iterator it = children.iterator();
                while (it.hasNext()) {
                    Widget w = (Widget) it.next();
                    panel.add((JComponent)w.getNativeWidget(), w);
                }
            }
        } else {
            panel.add((Component)widget.getNativeWidget(), widget);
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#focus()
     */
    public void focus() {
        if (initialized) {
            panel.requestFocus();
            // if widget is not showing we have
            // to make it showing..
            if (!panel.isShowing() && getParent() != null) {
                getParent().focus();
                panel.requestFocus();
            }
        }
    }
    /**
     * @see org.xulux.nyx.gui.Widget#getGuiValue()
     */
    public Object getGuiValue() {
        return null;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#canContainValue()
     */
    public boolean canContainValue() {
        return false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#isValueEmpty()
     */
    public boolean isValueEmpty() {
        return true;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#addNyxListener(org.xulux.nyx.gui.NyxListener)
     */
    public void addNyxListener(NyxListener listener) {
        //TODO
    }

}
