/*
   $Id: XuluxGuiDriverTest.java,v 1.1 2004-04-15 00:05:04 mvdb Exp $
   
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
package org.xulux.guidriver;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.xulux.core.ApplicationPart;
import org.xulux.gui.Widget;

/**
 * Test the xulux guidriver
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: XuluxGuiDriverTest.java,v 1.1 2004-04-15 00:05:04 mvdb Exp $
 */
public class XuluxGuiDriverTest extends TestCase {

    /**
     * Constructor for XuluxGuiDriverTest.
     * @param name the name of the test
     */
    public XuluxGuiDriverTest(String name) {
        super(name);
    }
    /**
     * Test if the part gets the correct provider when used.
     * @throws Exception
     */
    public void testProviderPart() throws Exception {
        System.out.println("testProviderPart");
        XuluxGuiDriver driver = new XuluxGuiDriver();
        String xml = "<part name='TestPart'/>";
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        //InputStream stream = getClass().getClassLoader().getResourceAsStream("org/xulux/guidriver/providertest.xml");
        ApplicationPart part = driver.read(stream, null);
        assertNull(part.getProvider());
        assertEquals("TestPart", part.getName());
        stream.close();
        xml = "<part name='TestPart' provider='test'/>";
        stream = new ByteArrayInputStream(xml.getBytes());
        driver = new XuluxGuiDriver();
        part = driver.read(stream, null);
        assertEquals("test", part.getProvider());
    }

    /**
     * Test the provider attribute.
     * @throws Exception
     */
    public void testProviderWidget() throws Exception {
        System.out.println("testProviderWidget");
        XuluxGuiDriver driver = new XuluxGuiDriver();
        StringBuffer buffer = new StringBuffer();
        buffer.append("<part name='TestPart'>");
        buffer.append("<field type='label' name='Label'>");
        buffer.append("<text>SimpleTree</text>");
        buffer.append("<position>10,0</position>");
        buffer.append("<size>100,21</size>");
        buffer.append("</field>");
        buffer.append("</part>");
        String xml = buffer.toString();
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        //InputStream stream = getClass().getClassLoader().getResourceAsStream("org/xulux/guidriver/providertest.xml");
        ApplicationPart part = driver.read(stream, null);
        assertNull(part.getProvider());
        Widget w = part.getWidget("Label");
        assertEquals("Label", w.getName());
        assertNull(w.getProvider());
        stream.close();
        buffer = new StringBuffer();
        buffer.append("<part name='TestPart'>");
        buffer.append("<field type='label' name='Label' provider='test'>");
        buffer.append("<text>SimpleTree</text>");
        buffer.append("<position>10,0</position>");
        buffer.append("<size>100,21</size>");
        buffer.append("</field>");
        buffer.append("</part>");
        xml = buffer.toString();
        stream = new ByteArrayInputStream(xml.getBytes());
        driver = new XuluxGuiDriver();
        part = driver.read(stream, null);
        assertEquals("test", part.getWidget("Label").getProvider());
    }

}
