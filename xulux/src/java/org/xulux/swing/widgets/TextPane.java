/*
   $Id: TextPane.java,v 1.1 2004-10-05 10:10:59 mvdb Exp $
   
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

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.gui.NyxListener;
import org.xulux.gui.utils.ColorUtils;
import org.xulux.swing.listeners.PrePostFieldListener;

/**
 * The swing textare widget.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TextPane.java,v 1.1 2004-10-05 10:10:59 mvdb Exp $
 */
public class TextPane extends Entry {

    /**
     * the focuslistener
     */
    private PrePostFieldListener focusListener;
    /**
     * the immidiatelistener
     */
    private PrePostFieldListener immidiateListener;
    /**
     * the size
     */
    private Dimension size;
    /**
     * the log instance
     */
    private Log log = LogFactory.getLog(TextPane.class);

    /**
     * The scrollpane for the textarea
     */
    private JScrollPane scrollPane;

    /**
     * @param name the textarea name
     */
    public TextPane(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy() {
        processDestroy();
        if (textComponent != null) {
            textComponent.removeAll();
            Container container = textComponent.getParent();
            if (container != null) {
                container.remove(textComponent);
            }
            textComponent = null;
            if (scrollPane != null) {
              scrollPane.removeAll();
              container = scrollPane.getParent();
              if (container != null) {
                container.remove(scrollPane);
              }
            }
        }
        removeAllRules();
        getPart().removeWidget(this, this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget() {
        if (!initialized) {
            initialize();
        }
        if (scrollPane != null) {
          return scrollPane;
        }
        return textComponent;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        this.setValueCalled = true;
        textComponent = new JTextPane();
        if (getProperty("scrollbar") != null) {
          scrollPane = new JScrollPane(textComponent);
          String scrollBar = getProperty("scrollbar");
          scrollBar = scrollBar.toLowerCase();
          if (scrollBar.equals("vertical") || scrollBar.equals("both")) {
            int policy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
            String state = getProperty("scrollbar.state");
            if ("never".equalsIgnoreCase(state)) {
              policy = JScrollPane.VERTICAL_SCROLLBAR_NEVER;
            } else if ("always".equalsIgnoreCase(state)) {
              policy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
            }
            scrollPane.setVerticalScrollBarPolicy(policy);
          }
          if (scrollBar.equals("horizontal") || scrollBar.equals("both")) {
            int policy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
            String state = getProperty("scrollbar.state");
            if ("never".equalsIgnoreCase(state)) {
              policy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
            } else if ("always".equalsIgnoreCase(state)) {
              policy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS;
            }
            scrollPane.setHorizontalScrollBarPolicy(policy);
          }
          scrollPane.setSize(getRectangle().getRectangle().getSize());
          scrollPane.setPreferredSize(getRectangle().getRectangle().getSize());
          scrollPane.setMinimumSize(new Dimension(10,10));
        }
        if (isImmidiate()) {
            if (this.immidiateListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null) {
                    this.immidiateListener = (PrePostFieldListener) listener;
                } else {
                    this.immidiateListener = new PrePostFieldListener(this);
                }
            }
        }
        if (isVisible()) {
            if (focusListener == null) {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener == null) {
                    focusListener = (PrePostFieldListener) listener;
                } else {
                    focusListener = new PrePostFieldListener(this);
                }
                textComponent.addFocusListener(focusListener);
            }
        }
        refresh();
        processInit();
        this.setValueCalled = false;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh() {
        isRefreshing = true;
        initialize();
        if (getProperty("content-type") != null) {
          	((JTextPane) textComponent).setContentType(getProperty("content-type"));
        }
        textComponent.setEnabled(isEnabled());
        textComponent.setVisible(isVisible());
        textComponent.setPreferredSize(this.size);
        initializeValue();
        String backgroundColor = null;
        if (isRequired() && isEnabled()) {
            backgroundColor = getProperty("required-background-color");
        } else if (!isEnabled()) {
            backgroundColor = getProperty("disabled-background-color");
        } else {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null) {
            textComponent.setBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        String foreGroundColor = null;
        if (isRequired() && isEnabled()) {
            foreGroundColor = getProperty("required-foreground-color");
        } else if (!isEnabled()) {
            foreGroundColor = getProperty("disabled-foreground-color");
            if (foreGroundColor != null) {
                textComponent.setDisabledTextColor(ColorUtils.getSwingColor(foreGroundColor));
                foreGroundColor = null;
            }
        }
        if (foreGroundColor == null) {
            foreGroundColor = getProperty("default-foreground-color");
        }
        if (foreGroundColor != null) {
            textComponent.setForeground(ColorUtils.getSwingColor(foreGroundColor));
        }
        String border = getProperty("border");
        if (border != null) {
            String borderThickness = getProperty("border-thickness");
            String borderColor = getProperty("border-color");
            int thickness = 1;
            if (borderThickness != null) {
                try {
                    thickness = Integer.parseInt(borderThickness);
                } catch (NumberFormatException nfe) {
                    if (log.isWarnEnabled()) {
                        log.warn("invalid borderthickness, value is " + borderThickness + ", but should be a number ");
                    }
                }
            }
            Color bColor = null;
            if (borderColor != null) {
                bColor = ColorUtils.getSwingColor(borderColor);
            } else {
                // we default to black border color
                if (getParent() != null) {
                    bColor = ((JComponent) getParent().getNativeWidget()).getForeground();
                } else {
                    bColor = Color.black;
                }
            }
            if (border.equalsIgnoreCase("line")) {
                textComponent.setBorder(new LineBorder(bColor, thickness));
            }
        }
        textComponent.repaint();
        textComponent.setCaretPosition(0);
        isRefreshing = false;
    }
}
