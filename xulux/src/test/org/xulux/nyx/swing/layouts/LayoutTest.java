/*
 $Id: LayoutTest.java,v 1.3 2002-12-23 01:43:44 mvdb Exp $

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

package org.xulux.nyx.swing.layouts;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.xulux.nyx.gui.Label;


/**
 * A class to to test the layoutmanagers for swing
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: LayoutTest.java,v 1.3 2002-12-23 01:43:44 mvdb Exp $
 */
public class LayoutTest extends TestCase
{

    /**
     * Constructor for LayoutTest.
     */
    public LayoutTest(String name)
    {
        super(name);
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite(LayoutTest.class);
        return suite;
    }
    
    /**
     * This will only test things that are actually being used
     */
    public void testXYLayout()
    {
        System.out.println("testXYLayout");
        XYLayout xy = new XYLayout();
        JPanel panel = new JPanel(xy);
        Label label1 = new Label("label1");
        label1.setSize(10,10);
        label1.setPosition(10,10);
        Label label2 = new Label("label2");
        label2.setSize(10,10);
        label2.setPosition(10,30);
        JLabel jlabel1 = new JLabel("label1");
        JLabel jlabel2 = new JLabel("label2");
        panel.add(jlabel1, label1);
        panel.add(jlabel2, label2);
        assertEquals(label1,xy.map.get(jlabel1));
        assertEquals(label2,xy.map.get(jlabel2));
        JFrame frame = new JFrame("LayoutTest");
        frame.getContentPane().add(panel);
        frame.setSize(100,100);
        frame.pack();
        for (int i = 0; i < panel.getComponentCount(); i++)
        {
            int y = 10;
            if (i == 1) y = 30;
            Component component = panel.getComponent(i);
            assertEquals(new Dimension(10,10), component.getSize());
            Rectangle rect = component.getBounds();
            assertEquals(new Dimension(10,10),rect.getSize());
            assertEquals(10, (int) rect.getX());
            assertEquals(y, (int) rect.getY());
        }
        assertEquals(jlabel1,panel.getComponentAt(10,10));
        assertEquals(jlabel2,panel.getComponentAt(10,30));
        frame.dispose();
        panel.removeAll();
        assertTrue(xy.map.isEmpty());
    }
}
