/*
 $Id: PartCreator.java,v 1.1 2003-12-18 00:17:21 mvdb Exp $

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
    permission of The Xulux Project. For written permission,
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
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE XULUX PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.

 */
package org.xulux.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.InputStream;

import javax.swing.JFrame;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.context.ApplicationPartHandler;

/**
 * Creates a gui representation of a part
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: PartCreator.java,v 1.1 2003-12-18 00:17:21 mvdb Exp $
 */
public class PartCreator {

    /**
     * The partcreator instance
     */
    private PartCreator instance = new PartCreator();

    /**
     * Constructor for PartCreator.
     */
    protected PartCreator() {
    }

    /**
     * Create a part
     * @param object the bean
     * @param stream the stream to create the part from
     * @return the generated applicationpart
     */
    public static ApplicationPart createPart(Object object, InputStream stream) {
        // this is bad, but for now mandatory...
        ApplicationContext.getInstance();
        return new ApplicationPartHandler().read(stream, object);
        // first initialize the dictionary (not really necessary though)
    }

    /**
     * @deprecated not used anymore (we support windows ourselves now..)
     * , just convenience that I'll leave it here.
     * @param part the part
     */
    public static void addToFrame(ApplicationPart part) {
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