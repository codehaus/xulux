/*
   $Id: PartCreator.java,v 1.2 2004-01-28 15:00:23 mvdb Exp $
   
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
 * @version $Id: PartCreator.java,v 1.2 2004-01-28 15:00:23 mvdb Exp $
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
