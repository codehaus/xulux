/*
 $Id: TextArea.java,v 1.7 2003-11-06 19:53:12 mvdb Exp $

 Copyright 2003 (C) The Xulux Project. All Rights Reserved.

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
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xulux.nyx.gui.NyxListener;
import org.xulux.nyx.gui.utils.ColorUtils;
import org.xulux.nyx.swing.listeners.PrePostFieldListener;

/**
 * The swing textare widget.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TextArea.java,v 1.7 2003-11-06 19:53:12 mvdb Exp $
 */
public class TextArea extends Entry {

    private PrePostFieldListener focusListener;
    private PrePostFieldListener immidiateListener;
    private Dimension size;
    private Log log = LogFactory.getLog(TextArea.class);


    /**
     * @param name
     */
    public TextArea(String name) {
        super(name);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        processDestroy();
        if (textComponent != null)
        {
            textComponent.removeAll();
            Container container = textComponent.getParent();
            if (container != null)
            {
                container.remove(textComponent);
            }
            textComponent = null;
        }
        removeAllRules();
        getPart().removeWidget(this,this);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        if (!initialized)
        {
            initialize();
        }
        return textComponent;
    }

    /**
     * @see org.xulux.nyx.gui.Widget#initialize()
     */
    public void initialize()
    {
        if (this.initialized)
        {
            return;
        }
        this.initialized = true;
        this.setValueCalled = true;
        textComponent = new JTextArea();
        if (isImmidiate())
        {
            if (this.immidiateListener == null)
            {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener != null)
                {
                    this.immidiateListener = (PrePostFieldListener)listener;
                }
                else
                {
                    this.immidiateListener = new PrePostFieldListener(this);
                }
            }
        }
        if (isVisible())
        {
            if (focusListener == null)
            {
                NyxListener listener = getPart().getFieldEventHandler(this);
                if (listener == null)
                {
                    focusListener = (PrePostFieldListener)listener;
                }
                else
                {
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
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        textComponent.setEnabled(isEnabled());
        textComponent.setVisible(isVisible());
        textComponent.setPreferredSize(this.size);
        initializeValue();
        String backgroundColor = null;
        if (isRequired()  && isEnabled())
        {
            backgroundColor = getProperty("required-background-color");
        }
        else if (!isEnabled())
        {
            backgroundColor = getProperty("disabled-background-color");
        }
        else
        {
            backgroundColor = getProperty("default-background-color");
        }
        if (backgroundColor != null)
        {
            textComponent.setBackground(ColorUtils.getSwingColor(backgroundColor));
        }
        String border = getProperty("border");
        if (border != null) {
            String borderThickness = getProperty("border-thickness");
            String borderColor = getProperty("border-color");
            int thickness = 1;
            if (borderThickness != null) {
                try {
                    thickness = Integer.parseInt(borderThickness);
                }catch(NumberFormatException nfe) {
                    if (log.isWarnEnabled()) {
                        log.warn("invalid borderthickness, value is "+
                          borderThickness+", but should be a number ");
                    }
                }
            }
            Color bColor = null;
            if (borderColor != null) {
                bColor = ColorUtils.getSwingColor(borderColor);
            }else {
                // we default to black border color
                if (getParent() != null) {
                    bColor = ((JComponent)getParent().getNativeWidget()).getForeground();
                }else {
                    bColor = Color.black;
                }
            }
            if (border.equalsIgnoreCase("line")) {
                textComponent.setBorder(new LineBorder(bColor,thickness));
            }
        }
        ((JTextArea)textComponent).setLineWrap(BooleanUtils.toBoolean(getProperty("linewrap")));
        textComponent.repaint();
        isRefreshing = false;
    }
}
