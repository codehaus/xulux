package org.xulux.swing.widgets;

import org.xulux.dataprovider.converters.IConverter;

/**
 * A mock converter for testing purposes..
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: MockConverter.java,v 1.1 2004-06-29 12:00:52 mvdb Exp $
 */
public class MockConverter implements IConverter {

  public static final String GUIVALUE = "converted";
  public static final String BEANVALUE = "notconverted";
  /**
   * 
   */
  public MockConverter() {
    super();
  }

  /**
   * @see org.xulux.dataprovider.converters.IConverter#getBeanValue(java.lang.Object)
   */
  public Object getBeanValue(Object object) {
    return BEANVALUE;
  }

  /**
   * @see org.xulux.dataprovider.converters.IConverter#getGuiValue(java.lang.Object)
   */
  public Object getGuiValue(Object object) {
    return GUIVALUE;
  }

  /**
   * @see org.xulux.dataprovider.converters.IConverter#getType()
   */
  public Class getType() {
    return null;
  }

  public static void main(String[] args) {
  }
}
