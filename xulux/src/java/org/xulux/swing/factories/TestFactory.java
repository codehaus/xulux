/*
 $Id: TestFactory.java,v 1.1 2003-12-18 00:17:32 mvdb Exp $

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
package org.xulux.swing.factories;

import java.io.InputStream;
import java.util.Iterator;

import javax.swing.JPanel;

import org.xulux.context.ApplicationContext;
import org.xulux.context.ApplicationPart;
import org.xulux.context.ApplicationPartHandler;
import org.xulux.global.BeanMapping;
import org.xulux.global.Dictionary;
import org.xulux.global.IField;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.swing.layouts.XYLayout;

/**
 * A testFactory, which just contains some experimenting code, to end up with the
 * "ideal" situation. Will be deleted / moved to examples or test in
 * a later stage.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TestFactory.java,v 1.1 2003-12-18 00:17:32 mvdb Exp $
 */
public final class TestFactory
{

    /**
     * the testfactory instance
     */
    private static TestFactory instance;
    /**
     * Constructor for TestFactory.
     */
    private TestFactory()
    {
    }

    /**
     * @return an instance of testfactory
     */
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
     * @param form the form name
     * @param bean the bean to use
     * @return the applicationpart
     */
    public static ApplicationPart getForm(String form, Object bean)
    {
        ApplicationPart part = new ApplicationPart(bean);
        Dictionary d = Dictionary.getInstance();
        BeanMapping mapping = d.getMapping(bean.getClass());
        Iterator iterator = mapping.getFields().iterator();
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        part.setParentWidget(panel);
        while (iterator.hasNext())
        {
            IField field = (IField) iterator.next();
            Widget entry = WidgetFactory.getWidget("entry", field.getAlias());
            entry.setEnable(true);
            entry.setVisible(true);
            entry.initialize();
            entry.setValue(field.getValue(bean));
            part.addWidget(entry);
        }
        return part;
    }

    /**
     * Build the form from an xml definition
     * @param form the stream of the form
     * @param bean the bean to use in the form
     * @return the applicationpart
     */
    public static ApplicationPart getForm(InputStream form, Object bean)
    {
        ApplicationPartHandler handler = new ApplicationPartHandler();
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        ApplicationPart part = handler.read(form, bean);
        ApplicationContext.getInstance().registerPart(part);
        part.setParentWidget(panel);
        return part;
    }
    /**
     * Build the form from an xml definition and override
     * the name
     * @param form the stream containing the form
     * @param name the name of the form
     * @param bean the bean to use in the form
     * @return the applicationpart
     */
    public static ApplicationPart getForm(InputStream form, String name, Object bean)
    {
        ApplicationPartHandler handler = new ApplicationPartHandler();
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        ApplicationPart part = handler.read(form, bean);
        part.setName(name);
        ApplicationContext.getInstance().registerPart(part);
        part.setParentWidget(panel);
        return part;
    }
}
