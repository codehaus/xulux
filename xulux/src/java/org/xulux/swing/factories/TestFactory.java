/*
   $Id: TestFactory.java,v 1.6 2004-03-16 15:09:39 mvdb Exp $
   
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
package org.xulux.swing.factories;

import java.io.InputStream;
import java.util.Iterator;

import javax.swing.JPanel;

import org.xulux.core.ApplicationContext;
import org.xulux.core.ApplicationPart;
import org.xulux.dataprovider.BeanMapping;
import org.xulux.dataprovider.Dictionary;
import org.xulux.dataprovider.IField;
import org.xulux.gui.Widget;
import org.xulux.gui.WidgetFactory;
import org.xulux.guidriver.XuluxGuiDriver;
import org.xulux.swing.layouts.XYLayout;

/**
 * A testFactory, which just contains some experimenting code, to end up with the
 * "ideal" situation. Will be deleted / moved to examples or test in
 * a later stage.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: TestFactory.java,v 1.6 2004-03-16 15:09:39 mvdb Exp $
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
        XuluxGuiDriver handler = new XuluxGuiDriver();
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
        XuluxGuiDriver handler = new XuluxGuiDriver();
        XYLayout layout = new XYLayout();
        JPanel panel = new JPanel(layout);
        ApplicationPart part = handler.read(form, bean);
        part.setName(name);
        ApplicationContext.getInstance().registerPart(part);
        part.setParentWidget(panel);
        return part;
    }
}
