/*
 $Id: TestFactory.java,v 1.7 2002-11-12 11:10:20 mvdb Exp $

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
package org.xulux.nyx.swing.factories;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.InputStream;
import java.util.Iterator;

import javax.swing.JPanel;

import org.xulux.nyx.context.ApplicationPart;
import org.xulux.nyx.context.ApplicationPartHandler;
import org.xulux.nyx.global.BeanField;
import org.xulux.nyx.global.BeanMapping;
import org.xulux.nyx.global.Dictionary;
import org.xulux.nyx.global.IField;
import org.xulux.nyx.gui.Entry;
import org.xulux.nyx.gui.Widget;
import org.xulux.nyx.gui.WidgetFactory;
import org.xulux.nyx.rules.DefaultPartRule;
import org.xulux.nyx.swing.BaseForm;
import org.xulux.nyx.swing.NewForm;
import org.xulux.nyx.swing.layouts.XYLayout;

/**
 * A testFactory, which just contains some experimenting code, to end up with the
 * "ideal" situation. Will be deleted / moved to examples or test in
 * a later stage.
 * 
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TestFactory.java,v 1.7 2002-11-12 11:10:20 mvdb Exp $
 */
public class TestFactory
{

    private static TestFactory instance;
    /**
     * Constructor for TestFactory.
     */
    public TestFactory()
    {
    }
    
    public static TestFactory getInstance()
    {
        if (instance == null)
        {
            instance = new TestFactory();
        }
        return instance;
    }
    
    
    /** 
     * Creates a ApplicationPart form
     */
    public static ApplicationPart getForm(String form, Object bean)
    {
        ApplicationPart part = new ApplicationPart(bean);
        Dictionary d = Dictionary.getInstance();
        BeanMapping mapping = d.getMapping(bean.getClass());
        System.out.println("fields : "+mapping.getFields());
        Iterator iterator = mapping.getFields().iterator();
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        part.setLayoutManager(layout);
        part.setParentWidget(panel);
        while (iterator.hasNext())
        {
            IField field = (IField) iterator.next();
            Widget entry = WidgetFactory.getWidget("entry", field.getAlias());
            entry.setEnable(true);
            entry.setVisible(true);
            entry.initialize();
            entry.setValue(field.getValue(bean));
            part.addWidget(entry, field.getName());
        }
        return part;
    }
    
    /**
     * Build the form from an xml definition
     */
    public static ApplicationPart getForm(InputStream form, Object bean)
    {
        ApplicationPartHandler handler = new ApplicationPartHandler();
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        ApplicationPart part = handler.read(form, bean);
        part.setLayoutManager(layout);
        part.setParentWidget(panel);
        return part;
    }
            

}
