/*
 $Id: PartCreator.java,v 1.3 2002-12-23 01:48:38 mvdb Exp $

 Copyright 2002 (C) The Xulux Project. All Rights Reserved.
 
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
package org.xulux.nyx.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import org.xulux.nyx.context.ApplicationContext;
import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.global.Dictionary;

/**
 * Creates a gui representation of a part
 * 
 * @author Martin van den Bemt
 * @version $Id: PartCreator.java,v 1.3 2002-12-23 01:48:38 mvdb Exp $
 */
public class PartCreator
{
    
    PartCreator instance = new PartCreator();

    /**
     * Constructor for PartCreator.
     */
    public PartCreator()
    {
        super();
    }
    
    public static ApplicationPart createPart(Object object, InputStream stream)
    {
        // this is bad, but for now mandatory...
        ApplicationContext.getInstance();
        return null;
        // first initialize the dictionary (not really necessary though
    }
    
    public static void addToFrame(ApplicationPart part)
    {
        JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
        Dimension dim = ((Component) part.getRootWidget()).getPreferredSize();
        dim.width += frame.getBounds().getWidth();
        dim.height += frame.getBounds().getHeight();
        frame.setSize(dim.width, dim.height);
        frame.getContentPane().add((Component) part.getRootWidget());
        frame.pack();        
    }
    

}
