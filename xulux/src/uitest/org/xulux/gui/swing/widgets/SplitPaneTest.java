/*
   $Id: SplitPaneTest.java,v 1.6 2004-04-14 14:16:11 mvdb Exp $
   
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
package org.xulux.gui.swing.widgets;

import java.io.InputStream;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xulux.core.XuluxContext;
import org.xulux.core.ApplicationPart;
import org.xulux.core.WidgetConfig;
import org.xulux.dataprovider.contenthandlers.DOMTreeContentHandler;
import org.xulux.dataprovider.contenthandlers.TreeNodeContentHandler;
import org.xulux.gui.PartCreator;

import junit.framework.TestCase;

/**
 * A testcase for splitpanes.
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SplitPaneTest.java,v 1.6 2004-04-14 14:16:11 mvdb Exp $
 */
public class SplitPaneTest extends TestCase {

    /**
     * Constructor for SplitPaneTest.
     * @param name the name of the test
     */
    public SplitPaneTest(String name) {
        super(name);
    }

    /**
     * Shows the splitpane xml.
     *
     */
    public void show() {
        String xml = "org/xulux/gui/swing/widgets/SplitPaneTest.xml";
        WidgetConfig config = XuluxContext.getInstance().getWidgetConfig("tree");
        config.addWidgetTool("swing", TreeNodeContentHandler.class);
        config.addWidgetTool(null, DOMTreeContentHandler.class);
        PersonCollection persons = new PersonCollection();
        persons.setPersonList(getData());
        InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
        ApplicationPart part = PartCreator.createPart(persons, stream);
        part.activate();
    }

    /**
     * @param args the arguments
     */
    public static void main(String[] args) {
        new SplitPaneTest("SplitPaneTest").show();
    }

    /**
     * @return data
     */
    public ArrayList getData() {
        ArrayList list = new ArrayList();
        list.add(new PersonBean("Martin", "van den Bemt"));
        list.add(new PersonBean("Misja", "Alma"));
        list.add(new PersonBean("Arthur", "Scrhijer"));
        list.add(new PersonBean("Remko", "Nienhuis"));
        list.add(new PersonBean("Maarten", "Spook"));
        return list;
    }

    /**
     * @param caller the caller
     * @param xml the xml docment
     * @return the document
     */
    public static Document getDocument(Object caller, String xml) {
        // use the same classloader as the caller..
        InputStream stream = caller.getClass().getClassLoader().getResourceAsStream(xml);
        Document document = null;
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(stream);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
        System.out.println("document : " + document);
        return document;
    }

}
