/*
 $Id: Window.java,v 1.2 2003-05-21 11:27:46 mvdb Exp $

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
package org.xulux.nyx.swt;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.swt.util.SWTUtil;

/**
 * The SWT window
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: Window.java,v 1.2 2003-05-21 11:27:46 mvdb Exp $
 */
public class Window extends org.xulux.nyx.gui.NyxWindow
implements SWTWidget
{
    
    Shell window;
    Display display;

    /**
     * Constructor for NyxWindow.
     * @param field
     */
    public Window(String field)
    {
        super(field);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#getNativeWidget()
     */
    public Object getNativeWidget()
    {
        initialize();
        return window;
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
        initialized = true;
        Object object = this;
        if (Display.getCurrent() == null)
        {
            // we are the first, so we need to parent all others.
            display = new Display();
            window = new Shell(display);
        }
        else
        {
            window = new Shell();
        }
        String windowType = getProperty("window-type");
        // don't have a clue yet what to use here
        // for swing to work correctlly
        if (StringUtils.equalsIgnoreCase("model",windowType))
        {
        }
        else if (StringUtils.equalsIgnoreCase("toolbox",windowType))
        {
        }
        else
        {
            // mdi is the default. 
        }
        initializeChildren();
        boolean autoSize = BooleanUtils.toBoolean(getProperty("autosize"));
        if (autoSize)
        {
//            Dimension dim = window.getContentPane().getLayout().preferredLayoutSize(window.getContentPane());
//            window.pack();
        }
        else
        {
            window.setSize(getRectangle().getWidth(), 
                            getRectangle().getHeight());
        }
        
        if (!isRefreshing())
        {
            refresh();
        }
    }

    /**
     * @see org.xulux.nyx.gui.Widget#refresh()
     */
    public void refresh()
    {
        isRefreshing = true;
        initialize();
        String title = getProperty("title");
        window.setText(title);
        String image = getProperty("icon");
        if (image != null)
        {
            // we need to dispose the icon, so it is an instance
            if (window.getImage() != null)
            {
                // dispse the old image first
                window.getImage().dispose();
            }
            window.setImage(SWTUtil.getImage(image, window));
//            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(image));
//            window.setIconImage(SwingUtils.getImage(image, this));
            //button.setFocusPainted(true);
        }
        window.setEnabled(isEnabled());
        window.setVisible(isVisible());
        window.open();
        while (!window.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }
        isRefreshing = false;
    }
    
    /**
     * @see org.xulux.nyx.gui.ContainerWidget#addToParent(Widget)
     */
    public void addToParent(Widget widget)
    {
        ((SWTWidget)widget).getNativeObject(window);
    }

    /**
     * @see org.xulux.nyx.gui.Widget#destroy()
     */
    public void destroy()
    {
        // dispose the image
        if (window.getImage() != null)
        {
            window.getImage().dispose();
        }
        window.dispose();
        display.dispose();
        window = null;
        display = null;
        super.destroy();
    }

    /**
     * If this method is called, there seems to be a parent
     * window..
     * 
     * @see org.xulux.nyx.gui.swt.SWTWidget#getNativeObject(Composite)
     */
    public Object getNativeObject(Composite composite)
    {
        return null;
    }

}
