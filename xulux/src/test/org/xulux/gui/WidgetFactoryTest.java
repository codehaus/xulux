package org.xulux.gui;

import org.xulux.core.XuluxContext;
import org.xulux.core.WidgetConfig;

import junit.framework.TestCase;

/**
 *
 * @author Martin van den Bemt
 * @version $Id: WidgetFactoryTest.java,v 1.3 2004-05-11 11:50:00 mvdb Exp $
 */
public class WidgetFactoryTest extends TestCase {

    /**
     * Constructor for WidgetFactoryTest.
     * @param name the name of the test
     */
    public WidgetFactoryTest(String name) {
      super(name);
    }

    /**
     * Test the constructor
     */
    public void testConstructor() {
      System.out.println("testConstructor");
      new WidgetFactory();
    }

    /**
     * Test the getWidget method
     */
    public void testGetWidget() {
      System.out.println("testGetWidget");
      assertNull(WidgetFactory.getWidget(null, null));
      WidgetConfig config = XuluxContext.getGuiDefaults().getWidgetConfig("button");
      config.registerWidgetDefault("test", "value");
      Object value2 = new Integer(1);
      config.registerWidgetDefault("test1", value2);
      Widget widget = WidgetFactory.getWidget("button", "Button");
      assertEquals(config.getWidgetDefaults(), widget.getProperties());
      
      
    }

}
