package org.xulux.gui.swing.layouts;

import java.io.InputStream;

import org.xulux.core.ApplicationPart;
import org.xulux.gui.PartCreator;

/**
 * A demo to show how the Swing borderlayout works in xulux
 *
 * @author <a href="mailto:martin@mvdb.net">Martin van den Bemt</a>
 * @version $Id: SwingBorderLayoutDemo.java,v 1.1 2004-10-18 14:10:48 mvdb Exp $
 */
public class SwingBorderLayoutDemo {

  /**
   * Shows the borderlayout window.
   */
  public void showWindow() {
    String xml = "org/xulux/gui/swing/layouts/SwingBorderLayout.xml";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(xml);
    ApplicationPart part = PartCreator.createPart(null, stream);
    part.activate();
  }
  
  public static void main(String[] args) {
    new SwingBorderLayoutDemo().showWindow();
  }
}
